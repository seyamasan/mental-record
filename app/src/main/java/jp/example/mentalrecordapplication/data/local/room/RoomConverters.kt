package jp.example.mentalrecordapplication.data.local.room

import androidx.room.TypeConverter
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType

class RoomConverters {
    @TypeConverter
    fun fromDefaultMoodType(defaultMoodType: DefaultMoodType): Int = defaultMoodType.typeNumber

    @TypeConverter
    fun toDefaultMoodType(value: Int): DefaultMoodType = DefaultMoodType.fromInt(value)

    @TypeConverter
    fun fromTimeOfDayType(timeOfDayType: TimeOfDayType): Int = timeOfDayType.typeNumber

    @TypeConverter
    fun toTimeOfDayType(value: Int): TimeOfDayType = TimeOfDayType.fromInt(value)
}