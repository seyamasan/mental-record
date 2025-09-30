package jp.example.mentalrecordapplication.utils

object TimeUtil {
    fun formatElapsedTime(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "%02d:%02d".format(m, s)
    }
}