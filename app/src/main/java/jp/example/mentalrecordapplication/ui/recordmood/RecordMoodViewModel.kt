package jp.example.mentalrecordapplication.ui.recordmood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.room.MoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordMoodViewModel @Inject constructor(private val repository: MoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordMoodState())
    val uiState: StateFlow<RecordMoodState> = _uiState.asStateFlow()

    fun updateIsDatePickerVisible(newState: Boolean) {
        _uiState.update { state ->
            state.copy(
                isDatePickerVisible = newState
            )
        }
    }

    fun updateTimeOfDayState(newState: List<Boolean>) {
        _uiState.update { state ->
            state.copy(
                timeOfDayState = newState
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

    fun updateTimeOfDate(newState: String) {
        _uiState.update { state ->
            state.copy(
                selectedTimeOfDate = newState
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

    fun updateSaveResult(newState: Int?) {
        _uiState.update { state ->
            state.copy(
                saveResult = newState
            )
        }
    }

    fun saveMoodDetail() {
        if (_uiState.value.selectedMood.isEmpty()) {
            updateSaveResult(newState = 1)
            return
        }
        if (_uiState.value.selectedTimeOfDate.isEmpty()) {
            updateSaveResult(newState = 2)
            return
        }
        if (_uiState.value.selectedDate.isEmpty()) {
            updateSaveResult(newState = 3)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insert(
                mood = _uiState.value.selectedMood,
                date = _uiState.value.selectedDate,
                timeZone = _uiState.value.selectedTimeOfDate,
                memo = _uiState.value.enteredMemo
            )

            if (result) {
                updateSaveResult(newState = 0)
            } else {
                updateSaveResult(newState = -1) // -1は失敗を表す値
            }
        }
    }
}