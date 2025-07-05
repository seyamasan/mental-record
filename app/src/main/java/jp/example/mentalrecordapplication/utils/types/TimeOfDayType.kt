package jp.example.mentalrecordapplication.utils.types

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.ui.graphics.vector.ImageVector
import jp.example.mentalrecordapplication.R

enum class TimeOfDayType(val stringResourceId: Int) {
    MORNING(R.string.time_of_day_morning),
    NOON(R.string.time_of_day_noon),
    NIGHT(R.string.time_of_day_night);

    fun getCheckedIcon(): ImageVector {
        return when (this) {
            MORNING -> Icons.Filled.WbTwilight
            NOON -> Icons.Filled.WbSunny
            NIGHT -> Icons.Filled.DarkMode
        }
    }

    fun getUnCheckedIcon(): ImageVector {
        return when (this) {
            MORNING -> Icons.Outlined.WbTwilight
            NOON -> Icons.Outlined.WbSunny
            NIGHT -> Icons.Outlined.DarkMode
        }
    }
}