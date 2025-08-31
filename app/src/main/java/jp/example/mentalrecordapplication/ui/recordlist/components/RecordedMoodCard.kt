package jp.example.mentalrecordapplication.ui.recordlist.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordedMoodCard(
    moodEntity: MoodEntity,
    onDeleteButtonTap: (Int) -> Unit
) {
    val timeOfDayType = TimeOfDayType.fromInt(moodEntity.timeOfDay.typeNumber)
    val moodType = DefaultMoodType.fromInt(moodEntity.mood.typeNumber)
    val deleteButtonShape = RoundedCornerShape(12.dp)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 日付
                Text(
                    text = moodEntity.date,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // 削除ボタン
                Box(
                    modifier = Modifier
                        .clip(deleteButtonShape)
                        .background(Color.Red.copy(alpha = 0.1f))
                ) {
                    IconButton(
                        onClick = { onDeleteButtonTap(moodEntity.id)},
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 時間帯
            AssistChip(
                onClick = {},
                label = { Text(stringResource(id = timeOfDayType.stringResId)) },
                leadingIcon = {
                    Icon(
                        timeOfDayType.checkedIcon,
                        contentDescription = "Time of day icon",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = moodType.iconResId),
                    contentDescription = "Mood Icon",
                    tint = colorResource(id = moodType.colorResId)
                )
                Text(
                    text = stringResource(id = moodType.nameResId)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.memo_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            moodEntity.memo?.let {
                // メモ
                Text(
                    text = moodEntity.memo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RecordedMoodCardPreview() {
    MentalRecordAppTheme {
        RecordedMoodCard(
            moodEntity = MoodEntity(
                id = 0,
                mood = DefaultMoodType.HAPPY,
                date = "2025/7/6",
                timeOfDay = TimeOfDayType.MORNING,
                memo = "Hello!"
            ),
            onDeleteButtonTap = {}
        )
    }
}