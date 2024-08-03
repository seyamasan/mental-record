package com.example.mentalrecordapplication.record_mood.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.databinding.ActivityRecordMoodBinding
import com.example.mentalrecordapplication.record_mood.view_model.RecordMoodActivityViewModel
import com.example.mentalrecordapplication.utils.AlertDialogUtil
import com.example.mentalrecordapplication.utils.CalenderDialogUtil
import java.util.Calendar

class RecordMoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordMoodBinding

    private val _timeZoneArray = arrayOf("morning", "noon", "night")
    private val _recordMoodActivityViewModel: RecordMoodActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.viewModel = _recordMoodActivityViewModel

        setupToday()
        setupListener()
        setupTimeZoneSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_record_mood_activity, menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun setupToday() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 0から始まるので
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.dateButton.text = "${year}/${month}/${day}"
    }

    private fun setupListener() {
        // 日付選択ボタン
        binding.dateButton.setOnClickListener {
            CalenderDialogUtil.showDatePickerDialog(this, object : CalenderDialogUtil.DatePickerCallback {
                @SuppressLint("SetTextI18n")
                override fun onDateSelected(year: Int, month: Int, day: Int) {
                    binding.dateButton.text = "${year}/${month}/${day}"
                }
            })
        }

        // 保存ボタン
        binding.saveButton.setOnClickListener {
            if (binding.dateButton.text.toString().isEmpty()) {
                AlertDialogUtil.showOkDialog(
                    title = getString(R.string.empty_mood_dialog_title),
                    msg = getString(R.string.empty_mood_dialog_msg),
                    context = this
                )
                return@setOnClickListener
            }
            val result = binding.viewModel?.saveMoodDetail(
                date = binding.dateButton.text.toString(),
                memo = binding.memoEditText.text.toString()
            )
            when (result) {
                0 -> {
                    AlertDialogUtil.showOkDialog(
                        title = getString(R.string.success_save_dialog_title),
                        msg = getString(R.string.success_save_dialog_msg),
                        context = this
                    )
                }
                1 -> {
                    AlertDialogUtil.showOkDialog(
                        title = getString(R.string.null_mood_dialog_title),
                        msg = getString(R.string.null_mood_dialog_msg),
                        context = this
                    )
                }
                2 -> {
                    AlertDialogUtil.showOkDialog(
                        title = getString(R.string.null_time_zone_dialog_title),
                        msg = getString(R.string.null_time_zone_dialog_msg),
                        context = this
                    )
                }
                else -> {
                    AlertDialogUtil.showOkDialog(
                        title = getString(R.string.failure_save_dialog_title),
                        msg = getString(R.string.failure_save_dialog_msg),
                        context = this
                    )
                }
            }
        }
    }

    private fun setupTimeZoneSpinner() {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, _timeZoneArray)
        binding.timeZoneSpinner.adapter = arrayAdapter
        // 選択されたアイテムを取得するリスナー
        binding.timeZoneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                binding.viewModel?.setTimeZone(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 何も選択されていない場合の処理
            }
        }
    }
}