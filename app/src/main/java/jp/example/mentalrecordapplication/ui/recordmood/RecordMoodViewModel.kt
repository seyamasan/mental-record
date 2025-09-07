package jp.example.mentalrecordapplication.ui.recordmood

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.data.repository.MoodRepository
import jp.example.mentalrecordapplication.utils.AudioRecorderUtil
import jp.example.mentalrecordapplication.utils.types.AudioRecordResultType
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.RecordMoodSaveResultType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordMoodViewModel @Inject constructor(
    private val repository: MoodRepository,
    private val audioRecorder: AudioRecorderUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordMoodState())
    val uiState: StateFlow<RecordMoodState> = _uiState.asStateFlow()

    fun updateMood(newState: DefaultMoodType?) = _uiState.update { state -> state.copy( selectedMood = newState) }

    fun updateTimeOfDay(newState: TimeOfDayType?) = _uiState.update { state -> state.copy(selectedTimeOfDay = newState) }

    fun updateDate(newState: String) = _uiState.update { state -> state.copy(selectedDate = newState) }

    fun updateMemo(newState: String?) = _uiState.update { state -> state.copy(enteredMemo = newState) }

    fun updateIsDatePickerVisible(newState: Boolean) = _uiState.update { state -> state.copy(isDatePickerVisible = newState) }

    fun updateIsMemoSheetVisible(newState: Boolean) = _uiState.update { state -> state.copy(isMemoSheetVisible = newState) }

    fun updateIsAudioRecordSheetVisible(newState: Boolean) = _uiState.update { state -> state.copy(isAudioRecordSheetVisible = newState) }

    fun updateAudioRecordResultType(newState: AudioRecordResultType?) = _uiState.update { state -> state.copy(audioRecordResultType = newState) }

    fun updateSaveResult(newState: RecordMoodSaveResultType?) = _uiState.update { state -> state.copy(saveResult = newState) }

    fun startAudioRecord(context: Context) {
        if (_uiState.value.isAudioRecording) { return }

        val isStarted = audioRecorder.startRecording(context = context)

        if (isStarted) {
            _uiState.update { state -> state.copy(isAudioRecording = true) }
        } else {
            updateAudioRecordResultType(AudioRecordResultType.START_FAILURE)
        }
    }

    fun stopAudioRecord() {
        if (!_uiState.value.isAudioRecording) { return }

        val isStopped = audioRecorder.stopRecording()

        if (isStopped) {
            _uiState.update { state -> state.copy(isAudioRecording = false) }
        } else {
            updateAudioRecordResultType(AudioRecordResultType.STOP_FAILURE)
        }
    }

    fun saveMoodDetail() {
        viewModelScope.launch {
            if (!validateInputState()) { return@launch }

            val result = repository.insert(
                MoodEntity(
                    id = 0, // 自動的にIDを入れるときは0を入れる
                    mood = _uiState.value.selectedMood ?: DefaultMoodType.HAPPY,
                    date = _uiState.value.selectedDate ?: DEFAULT_DATE,
                    timeOfDay = _uiState.value.selectedTimeOfDay ?: TimeOfDayType.MORNING,
                    memo = _uiState.value.enteredMemo
                )
            )

            if (result) {
                clearInputState() // After successful saving, the input is cleared. (保存に成功したら入力内容はクリアする。)
                updateSaveResult(newState = RecordMoodSaveResultType.SUCCESS)
            } else {
                updateSaveResult(newState = RecordMoodSaveResultType.FAILURE)
            }
        }
    }

    private fun validateInputState(): Boolean {
        if (_uiState.value.selectedMood == null) {
            updateSaveResult(newState = RecordMoodSaveResultType.INVALID_MOOD)
            return false
        }
        if (_uiState.value.selectedTimeOfDay == null) {
            updateSaveResult(newState = RecordMoodSaveResultType.INVALID_TIME_OF_DAY)
            return false
        }
        if (_uiState.value.selectedDate.isNullOrEmpty()) {
            updateSaveResult(newState = RecordMoodSaveResultType.INVALID_DATE)
            return false
        }
        return true
    }

    private fun clearInputState() {
        _uiState.update { state ->
            state.copy(
                selectedMood = null,
                selectedTimeOfDay = null,
                selectedDate = null,
                enteredMemo = null
            )
        }
    }

    companion object {
        private const val DEFAULT_DATE = "2024/11/11"
    }
}