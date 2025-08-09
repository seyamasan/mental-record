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

    @RequiresApi(Build.VERSION_CODES.O)
    fun sortAndUpdateResult(listItem: List<MoodEntity>?) {
        var tmpResults = listItem

        tmpResults = if (_uiState.value.isNewestFirst) {
            tmpResults?.sortedByDescending { it.localDate }
        } else {
            tmpResults?.sortedBy { it.localDate }
        }

        _uiState.update { state -> state.copy(allListItem = tmpResults, filteredListItem = tmpResults) }
    }

    fun applyTimeOfDayFilter() {
        if (_uiState.value.selectedTimeOfDay == null) { return }

        val filteredResult = _uiState.value.allListItem?.filter { it.timeOfDay == _uiState.value.selectedTimeOfDay }
        _uiState.update { state -> state.copy(filteredListItem = filteredResult) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch {
            val result = repository.selectAll()
            sortAndUpdateResult(result)
            applyTimeOfDayFilter()
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
}