package jp.example.mentalrecordapplication.ui.recordlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.data.repository.MoodRepository
import jp.example.mentalrecordapplication.utils.DateUtil
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(private val repository: MoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordListState())
    val uiState: StateFlow<RecordListState> = _uiState.asStateFlow()

    fun updateDeleteByIdResult(newState: Boolean?) = _uiState.update { state -> state.copy(deleteByIdResult = newState) }

    fun updateOpenFilterSortSheet(newState: Boolean) = _uiState.update { state -> state.copy(openFilterSortSheet = newState) }

    fun toggleIsNewestFirst() = _uiState.update { state -> state.copy(isNewestFirst = !state.isNewestFirst) }

    fun updateSelectedDefaultMood(newState: DefaultMoodType?) = _uiState.update { state -> state.copy(selectedDefaultMood = newState) }

    fun updateSelectedTimeOfDay(newState: TimeOfDayType) = _uiState.update { state -> state.copy(selectedTimeOfDay = newState) }

    fun updateShowDateRangePicker(newState: Boolean) = _uiState.update { state -> state.copy(showDateRangePicker = newState) }

    fun updateSelectedDateRange(newState: Pair<Long?, Long?>) = _uiState.update { state -> state.copy(selectedDateRange = newState) }

    @RequiresApi(Build.VERSION_CODES.O)
    fun applyListItemFilter() {
        val firstFilteredItem = applyDefaultMoodFilter(_uiState.value.allListItem)
        val secondFilteredItem = applyTimeOfDayFilter(firstFilteredItem)
        val thirdFilteredItem = applyDateRangeFilter(secondFilteredItem)
        val fourthFilteredItem = applySortOrder(thirdFilteredItem)
        _uiState.update { state -> state.copy(filteredListItem = fourthFilteredItem) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch {
            val result = repository.selectAll()
            _uiState.update { state -> state.copy(allListItem = result, filteredListItem = result) }
            applyListItemFilter()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteById(id: Int) {
        viewModelScope.launch {
            val result = repository.deleteById(id = id)
            if (result) { fetchAllItems() }
            updateDeleteByIdResult(newState = result)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun applySortOrder(targetItem: List<MoodEntity>?): List<MoodEntity>? {
        return if (_uiState.value.isNewestFirst) {
            targetItem?.sortedByDescending { it.localDate }
        } else {
            targetItem?.sortedBy { it.localDate }
        }
    }

    private fun applyDefaultMoodFilter(targetItem: List<MoodEntity>?): List<MoodEntity>? {
        if (_uiState.value.selectedDefaultMood == null) { return targetItem }
        return targetItem?.filter { it.mood == _uiState.value.selectedDefaultMood }
    }

    private fun applyTimeOfDayFilter(targetItem: List<MoodEntity>?): List<MoodEntity>? {
        if (_uiState.value.selectedTimeOfDay == null) { return targetItem }
        return targetItem?.filter { it.timeOfDay == _uiState.value.selectedTimeOfDay }
    }

    private fun applyDateRangeFilter(targetItem: List<MoodEntity>?): List<MoodEntity>? {
        val dateRange = _uiState.value.selectedDateRange
        val from = dateRange.first?.let { DateUtil.convertMillisToDate(it) }
        val to = dateRange.second?.let { DateUtil.convertMillisToDate(it) }

        // 両方nullならフィルターしない
        if (from == null && to == null) return targetItem

        return targetItem?.filter { entity ->
            when {
                from != null && to != null -> entity.date in from..to
                from != null -> entity.date >= from
                else -> true // この分岐は発生しない
            }
        }
    }
}