package com.example.mentalrecordapplication.record_mood.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.databinding.ActivityRecordMoodBinding
import com.example.mentalrecordapplication.record_mood.viewmodel.RecordMoodActivityViewModel
import com.example.mentalrecordapplication.utils.AlertDialogUtil
import com.example.mentalrecordapplication.utils.ButtonScaleAnimationUtil
import com.example.mentalrecordapplication.utils.CalenderDialogUtil
import java.util.Calendar

/*
* メンタルを記録するActivity
* 情報はRoomでローカルDBに保存している
*/

class RecordMoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordMoodBinding

    private val _recordMoodActivityViewModel: RecordMoodActivityViewModel by viewModels()
    private val _timeZoneArray = arrayOf("morning", "noon", "night")
    private var _recordListFragment: RecordListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)// タイトルを非表示にする
        binding.viewModel = _recordMoodActivityViewModel

        setupToday()
        setupListener()
        setupObserve()
        setupTimeZoneSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_record_mood_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // メニュー項目のクリックイベントを処理する
        return when (item.itemId) {
            R.id.listButton -> {
                hiddenRecordView()
                _recordListFragment = RecordListFragment()
                binding.viewModel?.getMoodDetails()
                true
            }
            R.id.graphButton -> {
                // 後々グラフのview入れるかも
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToday() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 0から始まるので
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.dateButton.text = "${year}/${month}/${day}"
        binding.viewModel?.setDate("${year}/${month}/${day}")
    }

    private fun setupListener() {
        // 感情ボタン
        binding.happyButton.setOnClickListener {
            if (binding.detailLayout.visibility == View.INVISIBLE) { visibleTapTheMoodBtn() }
            binding.viewModel?.setHappy()
            clearMoodButtonBackGround()
            binding.happyButton.background = ContextCompat.getDrawable(this, R.drawable.rounded_border_white)
        }
        binding.angerButton.setOnClickListener {
            if (binding.detailLayout.visibility == View.INVISIBLE) { visibleTapTheMoodBtn() }
            binding.viewModel?.setAnger()
            clearMoodButtonBackGround()
            binding.angerButton.background = ContextCompat.getDrawable(this, R.drawable.rounded_border_white)
        }
        binding.sadButton.setOnClickListener {
            if (binding.detailLayout.visibility == View.INVISIBLE) { visibleTapTheMoodBtn() }
            binding.viewModel?.setSad()
            clearMoodButtonBackGround()
            binding.sadButton.background = ContextCompat.getDrawable(this, R.drawable.rounded_border_white)
        }
        binding.funButton.setOnClickListener {
            if (binding.detailLayout.visibility == View.INVISIBLE) { visibleTapTheMoodBtn() }
            binding.viewModel?.setFun()
            clearMoodButtonBackGround()
            binding.funButton.background = ContextCompat.getDrawable(this, R.drawable.rounded_border_white)
        }

        // 日付選択ボタン
        binding.dateButton.setOnClickListener {
            CalenderDialogUtil.showDatePickerDialog(this, object : CalenderDialogUtil.DatePickerCallback {
                @SuppressLint("SetTextI18n")
                override fun onDateSelected(year: Int, month: Int, day: Int) {
                    binding.dateButton.text = "${year}/${month}/${day}"
                    binding.viewModel?.setDate("${year}/${month}/${day}")
                }
            })
        }

        binding.memoEditText.addTextChangedListener {
            binding.viewModel?.setMemo(it.toString())
        }

        // 保存ボタン
        binding.saveButton.setOnClickListener {
            ButtonScaleAnimationUtil.simpleScaleAnimation(binding.saveButton)
            binding.viewModel?.saveMoodDetail()
        }
    }

    private fun setupObserve() {
        // viewModelの変数を監視して各々処理する

        binding.viewModel?.saveResult?.observe(this) { result ->
            sortingSavedResults(result)
        }

        binding.viewModel?.recordDetailsList?.observe(this) { result ->
            if (result.isNullOrEmpty()) {
                AlertDialogUtil.showOkDialog(
                    title = getString(R.string.not_data_dialog_title),
                    msg = getString(R.string.not_data_dialog_msg),
                    context = this
                )
            } else {
                if (_recordListFragment != null) {
                    _recordListFragment?.setMoodDetailsList(result)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, _recordListFragment!!)
                        .commit()
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

    private fun sortingSavedResults(result: Int) {
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
            3 -> {
                AlertDialogUtil.showOkDialog(
                    title = getString(R.string.null_date_dialog_title),
                    msg = getString(R.string.null_date_dialog_msg),
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

    private fun visibleTapTheMoodBtn() {
        binding.detailLayout.visibility = View.VISIBLE
        binding.saveButton.visibility = View.VISIBLE
    }

    private fun clearMoodButtonBackGround() {
        // 選択解除
        binding.happyButton.background = null
        binding.angerButton.background = null
        binding.sadButton.background = null
        binding.funButton.background = null
    }

    private fun hiddenRecordView() {
        // fragment表示のためにactivityのパーツを非表示
        binding.moodButtonLayout.visibility = View.INVISIBLE
        binding.detailLayout.visibility = View.INVISIBLE
        binding.saveButton.visibility = View.INVISIBLE
    }
}