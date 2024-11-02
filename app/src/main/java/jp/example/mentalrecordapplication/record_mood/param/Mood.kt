package jp.example.mentalrecordapplication.record_mood.param

import android.content.Context
import jp.example.mentalrecordapplication.R

enum class Mood(private val mood: Int) {
    HAPPY(R.string.happy_button_text),
    ANGER(R.string.anger_button_text),
    SAD(R.string.sad_button_text),
    FUN(R.string.fun_button_text);

    fun getMood(context: Context): String {
        return mood.let { context.getString(it) }
    }
}