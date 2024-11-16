package jp.example.mentalrecordapplication.screens

import jp.example.mentalrecordapplication.R
import kotlinx.serialization.Serializable

class Screens {
    companion object {
        val recordView = RecordView()
    }

    @Serializable
    data class RecordView (
        override val screenTitleResId: Int = R.string.record_screen_title
    ):ScreenData
}