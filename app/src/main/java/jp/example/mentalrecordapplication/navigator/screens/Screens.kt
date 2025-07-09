package jp.example.mentalrecordapplication.navigator.screens

import jp.example.mentalrecordapplication.R
import kotlinx.serialization.Serializable

object Screens {
    val recordMoodView = RecordMoodView()
    val recordListView = RecordListView()

    @Serializable
    data class RecordMoodView (
        override val screenTitleResId: Int = R.string.mood_record_screen_title,
        val navBarIndex: Int = 0
    ): ScreenData

    @Serializable
    data class RecordListView (
        override val screenTitleResId: Int = R.string.record_list_screen_title,
        val navBarIndex: Int = 1
    ): ScreenData
}