package jp.example.mentalrecordapplication.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import jp.example.mentalrecordapplication.R
import kotlinx.serialization.Serializable

class Screens {
    companion object {
        val screenList = listOf(
            RecordMoodView(),
            RecordListView()
        )

        val iconList = listOf(
            Icons.Filled.Create,
            Icons.Filled.List
        )
    }

    @Serializable
    data class RecordMoodView (
        override val screenTitleResId: Int = R.string.mood_record_screen_title
    ):ScreenData

    @Serializable
    data class RecordListView (
        override val screenTitleResId: Int = R.string.record_list_screen_title
    ):ScreenData
}