package jp.example.mentalrecordapplication.ui.recordmood

import jp.example.mentalrecordapplication.utils.types.RecordMoodSaveResultType

data class RecordMoodState(
    val selectedMood: String = "",
    val selectedTimeOfDay: String = "",
    val selectedDate: String = "",
    val enteredMemo: String = "",
    val selectedMoodIndex: Int = -1,
    val isDatePickerVisible: Boolean = false,
    val selectedTimeOfDayIndex: Int? = null,
    val isMemoSheetVisible: Boolean = false,
    val saveResult: RecordMoodSaveResultType? = null
)
