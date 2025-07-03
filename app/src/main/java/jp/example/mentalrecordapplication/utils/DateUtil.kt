package jp.example.mentalrecordapplication.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
     fun convertMillisToDate(millis: Long): String {
        // yyyy/MM/ddのフォーマットに変換
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}