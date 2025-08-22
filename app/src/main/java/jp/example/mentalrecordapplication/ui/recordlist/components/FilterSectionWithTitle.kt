package jp.example.mentalrecordapplication.ui.recordlist.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import jp.example.mentalrecordapplication.ui.common.DefaultMoodSelector
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType

@Composable
fun FilterSectionWithTitle(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        content()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FilterSectionWithTitlePreview() {
    MentalRecordAppTheme {
        Surface {
            FilterSectionWithTitle(title = "Mood") {
                DefaultMoodSelector(
                    selectedMood = DefaultMoodType.FUN,
                    onSelectedMood = {}
                )
            }
        }
    }
}