package jp.example.mentalrecordapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateUtil {
     fun convertMillisToDate(millis: Long): String {
        // yyyy/MM/ddのフォーマットに変換
        val formatter = SimpleDateFormat("yyyy/M/dd", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToLocalDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy/M/d")
        return LocalDate.parse(date, formatter)
    }
}