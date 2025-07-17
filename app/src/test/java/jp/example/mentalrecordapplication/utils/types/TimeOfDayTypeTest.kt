package jp.example.mentalrecordapplication.utils.types

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TimeOfDayTypeTest {

    @Test
    fun fromInt_returns_correct_enum_for_each_typeNumber() {
        for (type in TimeOfDayType.entries) {
            // When
            val timeOfDay = TimeOfDayType.fromInt(type.typeNumber)

            // Then
            assertThat(timeOfDay).isEqualTo(type)
        }
    }

    @Test
    fun fromInt_returns_MORNING_if_no_matching_time_of_day() {
        // Given
        val dummyTypeNumber: Int = -1

        // When
        val timeOfDay = TimeOfDayType.fromInt(dummyTypeNumber)

        // Then
        assertThat(timeOfDay).isEqualTo(TimeOfDayType.MORNING)
    }
}