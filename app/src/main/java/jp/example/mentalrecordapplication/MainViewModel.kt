package jp.example.mentalrecordapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _permissionToRecordAccepted = MutableStateFlow(false)
    val permissionToRecordAccepted: StateFlow<Boolean> = _permissionToRecordAccepted

    fun updatePermissionResult(newState: Boolean) { _permissionToRecordAccepted.value = newState }
}