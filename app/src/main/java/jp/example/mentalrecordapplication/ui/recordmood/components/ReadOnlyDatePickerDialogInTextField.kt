package jp.example.mentalrecordapplication.ui.recordmood.components

import android.content.res.Configuration
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadOnlyDatePickerDialogInTextField(
    showDatePicker: Boolean,
    datePickerState: DatePickerState,
    onTextFieldClick: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val selectedDate = datePickerState.selectedDateMillis?.let {
        DateUtil.convertMillisToDate(it)
    } ?: ""

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text(stringResource(id = R.string.select_date_title)) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "DateRange icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        onTextFieldClick()
                    }
                }
            }
    )

    if (showDatePicker) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DatePickerDialog(
                onDismissRequest = {}, // 他要素タップしても閉じさせない
                confirmButton = {
                    TextButton(onClick = { onDateSelected(selectedDate) }) {
                        Text("OK")
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    DatePicker(
                        showModeToggle = false, // カレンダーのみで入力モードなし
                        state = datePickerState
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReadOnlyDatePickerDialogInTextFieldPreview() {
    val datePickerState = rememberDatePickerState()

    MentalRecordAppTheme {
        ReadOnlyDatePickerDialogInTextField(
            showDatePicker = false,
            datePickerState = datePickerState,
            onTextFieldClick = {},
            onDateSelected = {}
        )
    }
}