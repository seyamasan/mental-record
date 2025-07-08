package jp.example.mentalrecordapplication.ui.recordlist.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordedMoodCard(
    moodEntity: MoodEntity,
    onDeleteButtonTap: (Int) -> Unit
) {
    val morning = stringResource(id = R.string.time_of_day_morning)
    val noon = stringResource(id = R.string.time_of_day_noon)
    val night = stringResource(id = R.string.time_of_day_night)

    var iconName = stringResource(id = DefaultMoodType.Normal.getName())
    var icon = painterResource(id = DefaultMoodType.Normal.getIcon())
    var iconColor = colorResource(id = DefaultMoodType.Normal.getColor())

    when (moodEntity.mood) {
        stringResource(id = R.string.mood_happy) -> {
            iconName = stringResource(id = DefaultMoodType.HAPPY.getName())
            icon = painterResource(id = DefaultMoodType.HAPPY.getIcon())
            iconColor = colorResource(id = DefaultMoodType.HAPPY.getColor())
        }
        stringResource(id = R.string.mood_anger) -> {
            iconName = stringResource(id = DefaultMoodType.ANGER.getName())
            icon = painterResource(id = DefaultMoodType.ANGER.getIcon())
            iconColor = colorResource(id = DefaultMoodType.ANGER.getColor())
        }
        stringResource(id = R.string.mood_sad) -> {
            iconName = stringResource(id = DefaultMoodType.SAD.getName())
            icon = painterResource(id = DefaultMoodType.SAD.getIcon())
            iconColor = colorResource(id = DefaultMoodType.SAD.getColor())
        }
        stringResource(id = R.string.mood_fun) -> {
            iconName = stringResource(id = DefaultMoodType.FUN.getName())
            icon = painterResource(id = DefaultMoodType.FUN.getIcon())
            iconColor = colorResource(id = DefaultMoodType.FUN.getColor())
        }
    }

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
                IconButton( onClick = { onDeleteButtonTap(moodEntity.id)} ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 時間帯
            AssistChip(
                onClick = {},
                label = { Text(text = moodEntity.timeZone) },
                leadingIcon = {
                    when (moodEntity.timeZone) {
                        morning -> {
                            Icon(
                                Icons.Filled.WbTwilight,
                                contentDescription = "WbTwilight icon",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                        noon -> {
                            Icon(
                                Icons.Filled.WbSunny,
                                contentDescription = "WbSunny icon",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                        night -> {
                            Icon(
                                Icons.Filled.DarkMode,
                                contentDescription = "DarkMode icon",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    }
                }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "Mood Icon",
                    tint = iconColor
                )
                Text(
                    text = iconName
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

            // メモ
            Text(
                text = moodEntity.memo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
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
                mood = "Happy",
                date = "2025/7/6",
                timeZone = "Morning",
                memo = "Hello!"
            ),
            onDeleteButtonTap = {}
        )
    }
}