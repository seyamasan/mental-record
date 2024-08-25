package com.example.mentalrecordapplication.record_mood.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.databinding.FragmentRecordListBinding
import com.example.mentalrecordapplication.record_mood.param.Mood
import com.example.mentalrecordapplication.record_mood.viewmodel.RecordListFragmentViewModel
import com.example.mentalrecordapplication.room.MoodEntity
import com.example.mentalrecordapplication.utils.AlertDialogUtil

/*
* 記録されているデータをリストで表示するFragment
*/

class RecordListFragment : Fragment() {

    private var _binding: FragmentRecordListBinding? = null
    private val binding get() = _binding!!

    private lateinit var _recordListFragmentViewModel: RecordListFragmentViewModel
    private var _moodDetailsList: List<MoodEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordListBinding.inflate(inflater, container, false)
        val view = binding.root

        setupViewModel()
        setupRecyclerView()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        _recordListFragmentViewModel = ViewModelProvider(this).get(RecordListFragmentViewModel::class.java)
        if (_moodDetailsList != null) {
            _recordListFragmentViewModel.setMoodDetailsList(_moodDetailsList)
        }
    }

    private fun setupRecyclerView() {
        binding.recordListView.layoutManager = LinearLayoutManager(requireContext())

        binding.recordListView.adapter = RecordListAdapter(context, _recordListFragmentViewModel.moodDetailsList!!) { item ->
            showMoodDetailDialog(item)
        }
    }

    @SuppressLint("InflateParams")
    private fun showMoodDetailDialog(item: MoodEntity) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_mood_detail, null)// ダイアログのカスタムレイアウトをインフレート

        val icon = dialogView.findViewById<ImageView>(R.id.dialogIcon)
        val date = dialogView.findViewById<TextView>(R.id.dialogDate)
        val timeZone = dialogView.findViewById<TextView>(R.id.dialogTimeZone)
        val memo = dialogView.findViewById<TextView>(R.id.dialogMemo)
        when (item.mood) {
            Mood.HAPPY.getMood() -> {
                icon.setImageResource(R.drawable.icon_happy)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.salmon_pink))
            }
            Mood.ANGER.getMood() -> {
                icon.setImageResource(R.drawable.icon_anger)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.dahlia_purple))
            }
            Mood.SAD.getMood() -> {
                icon.setImageResource(R.drawable.icon_sad)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.cerulean_blue))
            }
            Mood.FUN.getMood() -> {
                icon.setImageResource(R.drawable.icon_fun)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chartreuse_yellow))
            }
        }

        date.text = item.date
        timeZone.text = item.timeZone
        memo.text = item.memo

        AlertDialogUtil.showOkCustomDialog(dialogView, requireContext())
    }

    fun setMoodDetailsList(moodDetailsList: List<MoodEntity>?) {
        _moodDetailsList = moodDetailsList
    }
}