package com.example.mentalrecordapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

object TextUtil {
    @Composable
    fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }
}