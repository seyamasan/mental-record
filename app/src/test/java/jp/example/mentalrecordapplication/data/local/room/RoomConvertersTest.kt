package jp.example.mentalrecordapplication.data.local.room

import com.google.common.truth.Truth.assertThat
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import org.junit.Test

class RoomConvertersTest {

    private val converters = RoomConverters()

    @Test
    fun fromDefaultMoodType_and_toDefaultMoodType_should_be_inverse() {
        for (mood in DefaultMoodType.entries) {
            // When
            val intValue = converters.fromDefaultMoodType(mood)
            val defaultMood = converters.toDefaultMoodType(intValue)

            // Then
            assertThat(defaultMood).isEqualTo(mood)
        }
    }

    @Test
    fun fromTimeOfDayType_and_toTimeOfDayType_should_be_inverse() {
        for (type in TimeOfDayType.entries) {
            // When
            val intValue = converters.fromTimeOfDayType(type)
            val timeOfDay = converters.toTimeOfDayType(intValue)

            // Then
            assertThat(timeOfDay).isEqualTo(type)
        }
    }

    @Test
    fun toDefaultMoodType_returns_HAPPY_if_invalid_value() {
        // When
        val mood = converters.toDefaultMoodType(-1)

        // Then
        assertThat(mood).isEqualTo(DefaultMoodType.HAPPY)
    }

    @Test
    fun toTimeOfDayType_returns_MORNING_if_invalid_value() {
        // When
        val timeOfDay = converters.toTimeOfDayType(-1)

        // Then
        assertThat(timeOfDay).isEqualTo(TimeOfDayType.MORNING)
    }
}