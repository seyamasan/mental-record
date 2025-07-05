package jp.example.mentalrecordapplication.utils

import jp.example.mentalrecordapplication.R

enum class DefaultMoodType(
    private val nameResId: Int,
    private val iconResId: Int,
    private val colorResId: Int
) {
    HAPPY(R.string.mood_happy, R.drawable.icon_happy, R.color.magenta),
    ANGER(R.string.mood_anger, R.drawable.icon_anger, R.color.orange_red),
    SAD(R.string.mood_sad, R.drawable.icon_sad2, R.color.mediumSlateBlue),
    FUN(R.string.mood_fun, R.drawable.icon_fun, R.color.gold),
    Normal(R.string.mood_normal, R.drawable.icon_normal, R.color.mist);

    fun getName(): Int {
        return nameResId
    }

    fun getIcon(): Int {
        return iconResId
    }

    fun getColor(): Int {
        return colorResId
    }
}