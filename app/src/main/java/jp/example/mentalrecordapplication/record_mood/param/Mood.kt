package jp.example.mentalrecordapplication.record_mood.param

import android.content.Context
import jp.example.mentalrecordapplication.R

enum class Mood(private val mood: Int) {
    HAPPY(R.string.mood_happy),
    ANGER(R.string.mood_anger),
    SAD(R.string.mood_sad),
    FUN(R.string.mood_fun);

    fun getMood(context: Context): String {
        return mood.let { context.getString(it) }
    }
}