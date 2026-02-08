package jp.example.mentalrecordapplication.ui.recordmood

import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.RecordMoodSaveResultType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType

data class RecordMoodState(
    val selectedMood: DefaultMoodType? = null,
    val selectedTimeOfDay: TimeOfDayType? = null,
    val selectedDate: String? = null,
    val enteredMemo: String? = null,
    val isDatePickerVisible: Boolean = false,
    val isMemoSheetVisible: Boolean = false,
    val saveResult: RecordMoodSaveResultType? = null
)
