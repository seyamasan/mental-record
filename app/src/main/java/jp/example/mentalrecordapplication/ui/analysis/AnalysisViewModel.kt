package jp.example.mentalrecordapplication.ui.analysis

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.data.repository.MoodRepository
import jp.example.mentalrecordapplication.utils.DateUtil
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(private val repository: MoodRepository) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch {
            val result = repository.selectAll()
            result?.let {
                val item = filterThisWeek(it)
                print(item)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterThisWeek(targetItem: List<MoodEntity>): List<MoodEntity>? {
        val zone = ZoneId.systemDefault()
        val now = LocalDate.now(zone)

        // 今週の日曜 00:00:00
        val startOfWeek = now
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        // 今週の土曜 23:59:59.999
        val endOfWeek = now
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
            .atTime(LocalTime.MAX)
            .atZone(zone)
            .toInstant()
            .toEpochMilli()

        val dateRange = Pair(startOfWeek, endOfWeek)
        val from = DateUtil.convertMillisToDate(dateRange.first)
        val to = DateUtil.convertMillisToDate(dateRange.second)

        return targetItem.filter { entity ->
            val entityLocalDate = DateUtil.stringToLocalDate(entity.date)
            val fromLocalDate = DateUtil.stringToLocalDate(from)
            val toLocalDate = DateUtil.stringToLocalDate(to)

            entityLocalDate in fromLocalDate..toLocalDate
        }
    }
}