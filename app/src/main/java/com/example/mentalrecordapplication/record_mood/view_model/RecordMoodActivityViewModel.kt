package com.example.mentalrecordapplication.record_mood.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class RecordMoodActivityViewModel(application: Application) : AndroidViewModel(application) {
    init {
        // ここでapplication使うと思う
    }

    fun saveBtnTapped() {
        print("saved!!!")
    }
}