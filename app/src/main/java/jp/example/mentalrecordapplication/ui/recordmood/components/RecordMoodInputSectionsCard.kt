package jp.example.mentalrecordapplication.ui.recordmood.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.common.button.LargeElevatedButton
import jp.example.mentalrecordapplication.ui.common.button.TimeOfDayButtonGroup
import jp.example.mentalrecordapplication.ui.recordmood.RecordMoodState
import jp.example.mentalrecordapplication.ui.recordmood.RecordMoodViewModel

@Composable
fun RecordMoodInputSectionsCard(
    viewModel: RecordMoodViewModel,
    uiState: RecordMoodState,
    datePickerState: DatePickerState
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeOfDayButtonGroup(
                uiState.selectedTimeOfDay,
                onItemSelected = { viewModel.updateTimeOfDay(it) }
            )

            ReadOnlyDatePickerDialogInTextField(
                showDatePicker = uiState.isDatePickerVisible,
                datePickerState = datePickerState,
                onTextFieldClick = { viewModel.updateIsDatePickerVisible(newState = true) },
                onDateSelected = {
                    viewModel.updateDate(it)
                    viewModel.updateIsDatePickerVisible(newState = false)
                }
            )

            MemoTextFieldSheet(
                enteredMemo = uiState.enteredMemo,
                showSheet = uiState.isMemoSheetVisible,
                onTextFieldClick = { viewModel.updateIsMemoSheetVisible(newState = true) },
                onDismissRequest = { viewModel.updateIsMemoSheetVisible(newState = false) },
                onChangeTextField = { viewModel.updateMemo(it) }
            )

            IconButton(
                onClick = { viewModel.updateIsAudioRecordSheetVisible(true) },
                modifier = Modifier
                    .size(64.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    Icons.Default.Mic,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Audio Record Sheet Button",
                    modifier = Modifier.size(32.dp)
                )
            }

            LargeElevatedButton(
                text = stringResource(id = R.string.save_button_text),
                onClick = { viewModel.saveMoodDetail() }
            )
        }
    }
}