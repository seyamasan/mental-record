package jp.example.mentalrecordapplication.navigator.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Create
import jp.example.mentalrecordapplication.R
import kotlinx.serialization.Serializable

object Screens {
    val recordListView = RecordListView()

    val screenList = listOf(
        RecordMoodView(),
        recordListView
    )

    val iconList = listOf(
        Icons.Filled.Create,
        Icons.AutoMirrored.Filled.List
    )

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