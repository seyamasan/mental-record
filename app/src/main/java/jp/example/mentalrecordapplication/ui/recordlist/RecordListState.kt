package jp.example.mentalrecordapplication.ui.recordlist

import jp.example.mentalrecordapplication.data.local.room.MoodEntity

data class RecordListState(
    val listItem: List<MoodEntity>? = null,
    val deleteByIdResult: Boolean? = null,
    val openFilterSortSheet: Boolean = false,
    val isNewestFirst: Boolean = true
)
