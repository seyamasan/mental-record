package jp.example.mentalrecordapplication.ui.recordlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.data.repository.MoodRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(private val repository: MoodRepositoryInterface) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordListState())
    val uiState: StateFlow<RecordListState> = _uiState.asStateFlow()

    fun updateDeleteByIdResult(newState: Int?) {
        _uiState.update { state ->
            state.copy(
                deleteByIdResult = newState
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch {
            val result = repository.selectAll()
            val sortedResult = result?.sortedBy { it.localDate } // 年月日をもとにソート
            updateListItem(newState = sortedResult)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteById(id: Int) {
        viewModelScope.launch {
            val result = repository.deleteById(id = id)
            if (result) {
                fetchAllItems()
            } else {
                updateDeleteByIdResult(newState = -1) // -1は失敗
            }
        }
    }

    private fun updateListItem(newState: List<MoodEntity>?) {
        _uiState.update { state ->
            state.copy(
                listItem = newState
            )
        }
    }
}