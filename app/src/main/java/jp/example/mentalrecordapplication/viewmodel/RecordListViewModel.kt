package jp.example.mentalrecordapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.example.mentalrecordapplication.room.MoodEntity
import jp.example.mentalrecordapplication.room.MoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(
    private val repository: MoodRepository
) : ViewModel() {

    private val _listItem = MutableStateFlow<List<MoodEntity>?>(emptyList())
    val listItem: StateFlow<List<MoodEntity>?> get() = _listItem

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.selectAll()
            val sortedResult = result?.sortedBy { it.localDate } // 年月日をもとにソート
            withContext(Dispatchers.Main) {
                _listItem.value = sortedResult
            }
        }
    }
}