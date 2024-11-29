package jp.example.mentalrecordapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.room.MoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecordMoodViewModel @Inject constructor(
    private val repository: MoodRepository
) : ViewModel() {

    private var selectedMood by mutableStateOf("")
    private var selectedTimeOfDate by mutableStateOf("")
    private var selectedDate by mutableStateOf("")
    var enteredMemo by mutableStateOf("")
        private set
    private val _saveResult = MutableLiveData<Int?>()
    val saveResult: LiveData<Int?> get() = _saveResult

    fun updateMood(mood: String) {
        selectedMood = mood
    }

    fun updateTimeOfDate(timeOdDate: String) {
        selectedTimeOfDate = timeOdDate
    }

    fun updateDate(date: String) {
        selectedDate = date
    }

    fun updateMemo(memo: String) {
        enteredMemo = memo
    }

    fun saveMoodDetail() {
        if (selectedMood.isEmpty()) {
            _saveResult.value = 1
            return
        }
        if (selectedTimeOfDate.isEmpty()) {
            _saveResult.value = 2
            return
        }
        if (selectedDate.isEmpty()) {
            _saveResult.value = 3
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insert(
                mood = selectedMood,
                date = selectedDate,
                timeZone = selectedTimeOfDate,
                memo = enteredMemo
            )
            // メインスレッドで更新
            withContext(Dispatchers.Main) {
                _saveResult.value = if (result) 0 else -1 // -1は失敗を表す値
            }
        }
    }

    fun resetResult() {
        _saveResult.value = null
    }
}