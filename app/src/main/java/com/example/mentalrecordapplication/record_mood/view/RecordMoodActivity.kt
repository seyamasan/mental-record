package com.example.mentalrecordapplication.record_mood.view

import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.databinding.ActivityRecordMoodBinding
import com.example.mentalrecordapplication.record_mood.view_model.RecordMoodActivityViewModel

class RecordMoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordMoodBinding

    private val _timeZoneArray = arrayOf("未選択", "morning", "noon", "night")
    private val _recordMoodActivityViewModel: RecordMoodActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.viewModel = _recordMoodActivityViewModel
        setupTimeZoneSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_record_mood_activity, menu)
        return true
    }

    private fun setupTimeZoneSpinner() {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, _timeZoneArray)
        binding.timeZoneSpinner.adapter = arrayAdapter
    }
}