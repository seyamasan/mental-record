package com.example.mentalrecordapplication.record_mood.viewmodel

import android.app.Application
import android.view.MenuItem
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mentalrecordapplication.record_mood.param.Mood
import com.example.mentalrecordapplication.record_mood.view.RecordListFragment
import com.example.mentalrecordapplication.repository.MoodRepository
import com.example.mentalrecordapplication.room.MoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordMoodActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _repo: MoodRepository = MoodRepository(application)

    private var _recordListFragment: RecordListFragment? = null
    val recordListFragment: RecordListFragment?
        get() = _recordListFragment

    private var _selectedMood: String? = null
    val selectedMood: String?
        get() = _selectedMood
    private var _selectedDate: String? = null
    val selectedDate: String?
        get() = _selectedDate
    private var _selectedTimeZone: String? = null
    private var _enteredMemo: String = ""
    val enteredMemo: String
        get() = _enteredMemo

    private var _visibleFlg: Boolean = false
    val visibleFlg: Boolean
        get() = _visibleFlg

    private var _saveResult = MutableLiveData<Int>()
    val saveResult: LiveData<Int>
        get() = _saveResult

    private var _recordDetailsList = MutableLiveData<List<MoodEntity>?>()
    val recordDetailsList: LiveData<List<MoodEntity>?>
        get() = _recordDetailsList

    fun setRecordListFragment(fragment: RecordListFragment?) {
        _recordListFragment = fragment
    }

    fun setHappy() {
        _selectedMood = Mood.HAPPY.getMood()
    }

    fun setAnger() {
        _selectedMood = Mood.ANGER.getMood()
    }

    fun setSad() {
        _selectedMood = Mood.SAD.getMood()
    }

    fun setFun() {
        _selectedMood = Mood.FUN.getMood()
    }

    fun setDate(date: String) {
        _selectedDate = date
    }

    fun setTimeZone(timeZone: String) {
        _selectedTimeZone = timeZone
    }

    fun setMemo(memo: String) {
        _enteredMemo = memo
    }

    fun setVisibleFlg(flg: Boolean) {
        _visibleFlg = flg
    }

    fun saveMoodDetail() {
        if (_selectedMood.isNullOrEmpty()) {
            _saveResult.value = 1
            return
        }
        if (_selectedTimeZone.isNullOrEmpty()) {
            _saveResult.value = 2
            return
        }
        if (_selectedDate.isNullOrEmpty()) {
            _saveResult.value = 3
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = _repo.insert(
                mood = _selectedMood!!,
                date = _selectedDate!!,
                timeZone = _selectedTimeZone!!,
                memo = _enteredMemo
            )
            // メインスレッドで更新
            withContext(Dispatchers.Main) {
                _saveResult.value = if (result) 0 else -1 // -1は失敗を表す値
            }
        }
    }

    fun getMoodDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = _repo.selectAll()
            withContext(Dispatchers.Main) {
                _recordDetailsList.value = result
            }
        }
    }
}