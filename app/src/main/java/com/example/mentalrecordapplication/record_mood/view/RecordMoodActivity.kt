package com.example.mentalrecordapplication.record_mood.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.databinding.ActivityRecordMoodBinding

class RecordMoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordMoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_record_mood_activity, menu)
        return true
    }
}