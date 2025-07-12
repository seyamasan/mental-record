package jp.example.mentalrecordapplication.ui.recordmood.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TimeOfDayButtonGroup(
    selectedTimeOfDay: TimeOfDayType?,
    onItemSelected: (TimeOfDayType) -> Unit
) {
    val timeOfDayList: List<TimeOfDayType> = listOf(
        TimeOfDayType.MORNING,
        TimeOfDayType.NOON,
        TimeOfDayType.NIGHT
    )

    FlowRow(
        Modifier.fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        timeOfDayList.forEach { timeOdDay ->
            val timeOdDayString = stringResource(id = timeOdDay.stringResId)

            ToggleButton(
                checked = selectedTimeOfDay == timeOdDay,
                onCheckedChange = {
                    onItemSelected(timeOdDay)
                },
                shapes =
                    when (timeOdDay) {
                        TimeOfDayType.MORNING -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        TimeOfDayType.NIGHT -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                colors = ToggleButtonDefaults.toggleButtonColors(
                    containerColor = if (selectedTimeOfDay == timeOdDay) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.semantics { role = Role.RadioButton }
            ) {
                Icon(
                    if (selectedTimeOfDay == timeOdDay) timeOdDay.checkedIcon else timeOdDay.unCheckedIcon,
                    contentDescription = "Time Of Day Icon",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                Text(timeOdDayString)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TimeOfDayButtonGroupPreview() {
    MentalRecordAppTheme {
        TimeOfDayButtonGroup(
            selectedTimeOfDay = null,
            onItemSelected = {}
        )
    }
}