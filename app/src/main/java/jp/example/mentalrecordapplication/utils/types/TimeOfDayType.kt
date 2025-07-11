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

enum class TimeOfDayType(
    val typeNumber: Int,
    val stringResId: Int,
    val checkedIcon: ImageVector,
    val unCheckedIcon: ImageVector
) {
    MORNING(0, R.string.time_of_day_morning, Icons.Filled.WbTwilight, Icons.Outlined.WbTwilight),
    NOON(1, R.string.time_of_day_noon, Icons.Filled.WbSunny, Icons.Outlined.WbSunny),
    NIGHT(2, R.string.time_of_day_night, Icons.Filled.DarkMode, Icons.Outlined.DarkMode);
}