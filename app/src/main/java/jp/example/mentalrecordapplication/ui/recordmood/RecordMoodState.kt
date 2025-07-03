package jp.example.mentalrecordapplication.ui.recordmood

data class RecordMoodState(
    val selectedMood: String = "",
    val selectedTimeOfDate: String = "",
    val selectedDate: String = "",
    val enteredMemo: String = "",
    val selectedMoodIndex: Int = -1,
    val isDatePickerVisible: Boolean = false,
    val timeOfDayState: List<Boolean> = listOf(false, false, false),
    val isMemoSheetVisible: Boolean = false,
    val saveResult: Int? = null
)
