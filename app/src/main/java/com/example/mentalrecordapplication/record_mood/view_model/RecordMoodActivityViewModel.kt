package com.example.mentalrecordapplication.record_mood.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class RecordMoodActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var selectedMood: String? = null
    private var timeZone: String? = null

    init {
        // ここでapplication使うと思う
    }

    enum class Mood(private val mood: String) {
        HAPPY("Happy"),
        ANGER("Anger"),
        SAD("Sad"),
        FUN("Fun");

        fun getMood(): String {
            return mood
        }
    }

    fun happyBtnTapped() {
        selectedMood = Mood.HAPPY.getMood()
    }

    fun angerBtnTapped() {
        selectedMood = Mood.ANGER.getMood()
    }

    fun sadBtnTapped() {
        selectedMood = Mood.SAD.getMood()
    }

    fun funBtnTapped() {
        selectedMood = Mood.FUN.getMood()
    }

    fun setTimeZone(timeZone: String) {
        this.timeZone = timeZone
    }

    fun saveMoodDetail(date: String, memo: String): Int {
        if (selectedMood.isNullOrEmpty()) { return 1 }
        if (timeZone.isNullOrEmpty()) { return 2 }
        return 0
    }
}