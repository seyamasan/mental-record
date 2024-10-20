package jp.example.mentalrecordapplication.record_mood.viewmodel

import androidx.lifecycle.ViewModel
import jp.example.mentalrecordapplication.room.MoodEntity

class RecordListFragmentViewModel : ViewModel() {
    private var _moodDetailsList: List<MoodEntity>? = null
    val moodDetailsList: List<MoodEntity>?
        get() = _moodDetailsList

    fun setMoodDetailsList(list: List<MoodEntity>?) {
        _moodDetailsList = list
    }
}