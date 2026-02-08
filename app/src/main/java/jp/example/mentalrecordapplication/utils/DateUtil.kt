package jp.example.mentalrecordapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateUtil {
     fun convertMillisToDate(millis: Long): String {
        // yyyy/M/dのフォーマットに変換
        val formatter = SimpleDateFormat("yyyy/M/d", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToLocalDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy/M/d")
        return LocalDate.parse(date, formatter)
    }

    fun getStringNow(): String {
        val millis = System.currentTimeMillis()
        return convertMillisToDate(millis)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToTimeOfDay(millis: Long): TimeOfDayType {
        val zoneId = ZoneId.systemDefault()

        val localTime = Instant.ofEpochMilli(millis)
            .atZone(zoneId)
            .toLocalTime()

        val hour = localTime.hour // 0〜23

        return when (hour) {
            in 5..10 -> TimeOfDayType.MORNING     // 5:00〜10:59
            in 11..17 -> TimeOfDayType.NOON  // 11:00〜17:59
            else -> TimeOfDayType.NIGHT           // それ以外
        }
    }
}