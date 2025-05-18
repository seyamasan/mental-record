package jp.example.mentalrecordapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

    private var _selectedMood by mutableStateOf("")
    private var _selectedTimeOfDate by mutableStateOf("")
    private var _selectedDate by mutableStateOf("")

    private var _enteredMemo by mutableStateOf("")
    val enteredMemo get() = _enteredMemo

    private var _selectedMoodIndex by mutableIntStateOf(-1)
    val selectedMoodIndex get() = _selectedMoodIndex

    private val _saveResult = MutableLiveData<Int?>()
    val saveResult: LiveData<Int?> get() = _saveResult

    fun updateMood(mood: String) {
        _selectedMood = mood
    }

    fun updateSelectedMoodIndex(index: Int) {
        _selectedMoodIndex = index
    }

    fun updateTimeOfDate(timeOdDate: String) {
        _selectedTimeOfDate = timeOdDate
    }

    fun updateDate(date: String) {
        _selectedDate = date
    }

    fun updateMemo(memo: String) {
        _enteredMemo = memo
    }

    fun saveMoodDetail() {
        if (_selectedMood.isEmpty()) {
            _saveResult.value = 1
            return
        }
        if (_selectedTimeOfDate.isEmpty()) {
            _saveResult.value = 2
            return
        }
        if (_selectedDate.isEmpty()) {
            _saveResult.value = 3
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insert(
                mood = _selectedMood,
                date = _selectedDate,
                timeZone = _selectedTimeOfDate,
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