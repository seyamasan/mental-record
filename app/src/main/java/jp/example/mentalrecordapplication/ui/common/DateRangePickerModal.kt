package jp.example.mentalrecordapplication.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.DateUtil

@Composable
fun DateRangePickerModal(
    showDateRangePicker: Boolean,
    state: DateRangePickerState,
    onTextFieldTap: () -> Unit,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
) {
    // Start
    val selectedStartDateMillis = state.selectedStartDateMillis?.let {
        DateUtil.convertMillisToDate(it)
    } ?: ""

    // End
    val selectedEndDateMillis = state.selectedEndDateMillis?.let {
        DateUtil.convertMillisToDate(it)
    } ?: ""

    val selectedDateRange = if (selectedStartDateMillis != "" ||  selectedEndDateMillis != "") {
        "$selectedStartDateMillis〜$selectedEndDateMillis"
    } else { "" }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDateRange,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.select_date_range)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = onTextFieldTap) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "DateRange icon"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )
    }

    if (showDateRangePicker) {
        DatePickerDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateRangeSelected(
                            Pair(
                                state.selectedStartDateMillis,
                                state.selectedEndDateMillis
                            )
                        )
                    }
                ) {
                    Text("OK")
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false) // 範囲外タップで消えないように
        ) {
            DateRangePicker(
                state = state,
                title = {
                    Text(
                        text = stringResource(id = R.string.select_range)
                    )
                },
                showModeToggle = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(8.dp)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DateRangePickerModalPreview() {
    MentalRecordAppTheme {
        val dummyState = rememberDateRangePickerState()

        DateRangePickerModal(
            showDateRangePicker = false,
            state = dummyState,
            onTextFieldTap = {},
            onDateRangeSelected = {}
        )
    }
}