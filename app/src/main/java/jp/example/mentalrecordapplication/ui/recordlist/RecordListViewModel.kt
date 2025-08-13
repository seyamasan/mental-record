package jp.example.mentalrecordapplication.ui.recordlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.data.repository.MoodRepository
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

    fun updateSelectedTimeOfDay(newState: TimeOfDayType) = _uiState.update { state -> state.copy(selectedTimeOfDay = newState) }

    fun updateShowDateRangePicker(newState: Boolean) = _uiState.update { state -> state.copy(showDateRangePicker = newState) }

    @RequiresApi(Build.VERSION_CODES.O)
    fun applyListItemFilter() {
        val filteredItem1 = applyTimeOfDayFilter(_uiState.value.allListItem)
        val filteredItem2 = applySortOrder(filteredItem1)
        _uiState.update { state -> state.copy(filteredListItem = filteredItem2) }
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

    private fun applyTimeOfDayFilter(targetItem: List<MoodEntity>?): List<MoodEntity>? {
        if (_uiState.value.selectedTimeOfDay == null) { return targetItem }
        return targetItem?.filter { it.timeOfDay == _uiState.value.selectedTimeOfDay }
    }
}