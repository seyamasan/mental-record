package jp.example.mentalrecordapplication.ui.recordlist

import jp.example.mentalrecordapplication.room.MoodEntity

data class RecordListState(
    val listItem: List<MoodEntity>? = null,
    val deleteByIdResult: Int? = null
)
