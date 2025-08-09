package jp.example.mentalrecordapplication.ui.recordlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.data.repository.MoodRepository
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun sortAndUpdateResult(listItem: List<MoodEntity>?) {
        var tmpResults = listItem

        tmpResults = if (_uiState.value.isNewestFirst) {
            tmpResults?.sortedByDescending { it.localDate }
        } else {
            tmpResults?.sortedBy { it.localDate }
        }

        _uiState.update { state -> state.copy(listItem = tmpResults) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch {
            val result = repository.selectAll()
            sortAndUpdateResult(result)
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