package jp.example.mentalrecordapplication.data.local.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.example.mentalrecordapplication.utils.DateUtil
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import java.time.LocalDate

@Entity (tableName = "mood_table")
data class MoodEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "mood") val mood: DefaultMoodType,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time_of_day") val timeOfDay: TimeOfDayType,
    @ColumnInfo(name = "memo") val memo: String?
) {
    // dateをLocalDateに変換するプロパティ
    val localDate: LocalDate
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            return DateUtil.stringToLocalDate(date)
        }
}