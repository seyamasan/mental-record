package jp.example.mentalrecordapplication.ui.recordmood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.data.repository.MoodRepositoryInterface
import jp.example.mentalrecordapplication.utils.types.RecordMoodSaveResultType
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordMoodViewModel @Inject constructor(private val repository: MoodRepositoryInterface) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordMoodState())
    val uiState: StateFlow<RecordMoodState> = _uiState.asStateFlow()

    fun updateIsDatePickerVisible(newState: Boolean) {
        _uiState.update { state ->
            state.copy(
                isDatePickerVisible = newState
            )
        }
    }

    fun updateSelectedTimeOfDayIndex(newState: Int) {
        _uiState.update { state ->
            state.copy(
                selectedTimeOfDayIndex = newState
            )
        }
    }

    fun updateIsMemoSheetVisible(newState: Boolean) {
        _uiState.update { state ->
            state.copy(
                isMemoSheetVisible = newState
            )
        }
    }

    fun updateMood(newState: String) {
        _uiState.update { state ->
            state.copy(
                selectedMood = newState
            )
        }
    }

    fun updateSelectedMoodIndex(newState: Int) {
        _uiState.update { state ->
            state.copy(
                selectedMoodIndex = newState
            )
        }
    }

    fun updateTimeOfDay(newState: String) {
        _uiState.update { state ->
            state.copy(
                selectedTimeOfDay = newState
            )
        }
    }

    fun updateDate(newState: String) {
        _uiState.update { state ->
            state.copy(
                selectedDate = newState
            )
        }
    }

    fun updateMemo(newState: String) {
        _uiState.update { state ->
            state.copy(
                enteredMemo = newState
            )
        }
    }

    fun updateSaveResult(newState: RecordMoodSaveResultType?) {
        _uiState.update { state ->
            state.copy(
                saveResult = newState
            )
        }
    }

    fun saveMoodDetail() {
        viewModelScope.launch {
            if (!validateInputState()) { return@launch }

            val result = repository.insert(
                mood = _uiState.value.selectedMood,
                date = _uiState.value.selectedDate,
                timeZone = _uiState.value.selectedTimeOfDay,
                memo = _uiState.value.enteredMemo
            )

            if (result) {
                updateSaveResult(newState = RecordMoodSaveResultType.SUCCESS)
            } else {
                updateSaveResult(newState = RecordMoodSaveResultType.FAILURE)
            }
        }
    }

    private fun validateInputState(): Boolean {
        if (_uiState.value.selectedMood.isEmpty()) {
            updateSaveResult(newState = RecordMoodSaveResultType.INVALID_MOOD)
            return false
        }
        if (_uiState.value.selectedTimeOfDay.isEmpty()) {
            updateSaveResult(newState = RecordMoodSaveResultType.INVALID_TIME_OF_DAY)
            return false
        }
        if (_uiState.value.selectedDate.isEmpty()) {
            updateSaveResult(newState = RecordMoodSaveResultType.INVALID_DATE)
            return false
        }
        return true
    }
}