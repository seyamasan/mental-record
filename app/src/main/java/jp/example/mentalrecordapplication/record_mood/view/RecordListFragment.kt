package jp.example.mentalrecordapplication.record_mood.view

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
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.databinding.FragmentRecordListBinding
import jp.example.mentalrecordapplication.record_mood.param.Mood
import jp.example.mentalrecordapplication.record_mood.viewmodel.RecordListFragmentViewModel
import jp.example.mentalrecordapplication.room.MoodEntity

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
            context?.let { Mood.HAPPY.getMood(context = it) } -> {
                icon.setImageResource(R.drawable.icon_happy)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.salmon_pink))
            }
            context?.let { Mood.ANGER.getMood(context = it) } -> {
                icon.setImageResource(R.drawable.icon_anger)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.dahlia_purple))
            }
            context?.let { Mood.SAD.getMood(context = it) } -> {
                icon.setImageResource(R.drawable.icon_sad)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.cerulean_blue))
            }
            context?.let { Mood.FUN.getMood(context = it) } -> {
                icon.setImageResource(R.drawable.icon_fun)
                icon.imageTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chartreuse_yellow))
            }
        }

        date.text = item.date
        timeZone.text = item.timeZone
        memo.text = item.memo

//        AlertDialogUtil.showOkCustomDialog(dialogView, requireContext())
    }

    fun setMoodDetailsList(moodDetailsList: List<MoodEntity>?) {
        _moodDetailsList = moodDetailsList
    }
}