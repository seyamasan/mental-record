package jp.example.mentalrecordapplication.utils.types

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.ui.graphics.vector.ImageVector
import jp.example.mentalrecordapplication.R

enum class RecordMoodSaveResultType {
    SUCCESS,
    INVALID_MOOD,
    INVALID_TIME_OF_DAY,
    INVALID_DATE,
    FAILURE;

    fun getDialogTitleStringResourceId(): Int {
        return when (this) {
            SUCCESS -> R.string.success
            INVALID_MOOD -> R.string.null_mood_dialog_title
            INVALID_TIME_OF_DAY -> R.string.null_time_zone_dialog_title
            INVALID_DATE -> R.string.null_date_dialog_title
            FAILURE -> R.string.failure
        }
    }

    fun getDialogMessageStringResourceId(): Int {
        return when (this) {
            SUCCESS -> R.string.success_save_dialog_msg
            INVALID_MOOD -> R.string.null_mood_dialog_msg
            INVALID_TIME_OF_DAY -> R.string.null_time_zone_dialog_msg
            INVALID_DATE -> R.string.null_date_dialog_msg
            FAILURE -> R.string.failure_save_dialog_msg
        }
    }

    fun getIcon(): ImageVector {
        return when (this) {
            SUCCESS -> Icons.Default.Check
            else -> Icons.Default.ErrorOutline
        }
    }
}