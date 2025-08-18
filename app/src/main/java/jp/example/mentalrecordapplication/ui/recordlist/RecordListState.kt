package jp.example.mentalrecordapplication.ui.recordlist

import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType

data class RecordListState(
    val allListItem: List<MoodEntity>? = null,
    val filteredListItem: List<MoodEntity>? = null,
    val deleteByIdResult: Boolean? = null,
    val openFilterSortSheet: Boolean = false,
    val isNewestFirst: Boolean = true,
    val selectedTimeOfDay: TimeOfDayType? = null,
    val showDateRangePicker: Boolean = false,
    val selectedDateRange: Pair<Long?, Long?> = Pair(null, null)
)
