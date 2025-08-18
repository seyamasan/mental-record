package jp.example.mentalrecordapplication.utils.types

import jp.example.mentalrecordapplication.R

enum class DefaultMoodType(
    val typeNumber: Int,
    val nameResId: Int,
    val iconResId: Int,
    val colorResId: Int
) {
    HAPPY(0, R.string.mood_happy, R.drawable.icon_happy, R.color.magenta),
    ANGER(1, R.string.mood_anger, R.drawable.icon_anger, R.color.orange_red),
    SAD(2, R.string.mood_sad, R.drawable.icon_sad2, R.color.mediumSlateBlue),
    FUN(3, R.string.mood_fun, R.drawable.icon_fun, R.color.gold),
    NORMAL(4, R.string.mood_normal, R.drawable.icon_normal, R.color.mist);

    companion object {
        fun fromInt(value: Int): DefaultMoodType = entries.firstOrNull { it.typeNumber == value } ?: HAPPY
    }
}