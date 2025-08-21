package jp.example.mentalrecordapplication.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType

@Composable
fun DefaultMoodSelector(
    selectedMood: DefaultMoodType?,
    onSelectedMood: (DefaultMoodType) -> Unit
) {
    val defaultMoodList = listOf(
        DefaultMoodType.HAPPY,
        DefaultMoodType.ANGER,
        DefaultMoodType.SAD,
        DefaultMoodType.FUN,
        DefaultMoodType.NORMAL
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        defaultMoodList.forEach { mood ->
            item {
                val moodName = stringResource(id = mood.nameResId)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .then(
                            if (selectedMood == mood) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                            } else {
                                Modifier
                            }
                        )
                        .padding(16.dp)
                        .clickable { onSelectedMood(mood) }
                ) {
                    Icon(
                        painter = painterResource(id = mood.iconResId),
                        contentDescription = "Mood Icon",
                        tint = colorResource(id = mood.colorResId)
                    )
                    Text(text = moodName)
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DefaultMoodSelectorPreview() {
    MentalRecordAppTheme {
        Surface {
            DefaultMoodSelector(
                selectedMood = DefaultMoodType.HAPPY,
                onSelectedMood = {}
            )
        }
    }
}