package com.example.mentalrecordapplication.record_mood.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.databinding.FragmentRecordMoodBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class RecordMoodFragment : Fragment() {

    private var _binding: FragmentRecordMoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordMoodBinding.inflate(inflater, container, false)
        val view = binding.root

        setupBarChart()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // bindingをクリアする
    }

    private fun setupBarChart() {
        // 朝、昼、夜の各時間帯のデータを準備
        val dataEntries = mutableListOf<BarEntry>()
        dataEntries.add(BarEntry(0f, floatArrayOf(50f, 50f))) // 朝のデータ（happy50%、fun50%）
        dataEntries.add(BarEntry(1f, floatArrayOf(80f, 20f))) // 昼のデータ（happy80%、fun20%）
        dataEntries.add(BarEntry(2f, floatArrayOf(100f))) // 夜のデータ（happy60%）

        // 各データセットを作成
        val dataSet = BarDataSet(dataEntries, "")
        dataSet.colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.salmon_pink),
            ContextCompat.getColor(requireContext(), R.color.chartreuse_yellow)
        )

        // ラベルを設定
        val labels = arrayOf("happy", "fun")
        dataSet.stackLabels = labels

        // グラフにデータセットを設定して表示
        val barData = BarData(dataSet)
        binding.barChart.data = barData
        binding.barChart.setFitBars(true) // グループに合わせて棒の幅を自動調整

        // X軸の設定（朝、昼、夜のラベル）
        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("朝", "昼", "夜"))
        xAxis.position = XAxis.XAxisPosition.BOTTOM // X軸の位置を下に設定
        xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        // Legendの設定
        val legend = binding.barChart.legend
        legend.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        binding.barChart.invalidate() // グラフを更新
    }
}