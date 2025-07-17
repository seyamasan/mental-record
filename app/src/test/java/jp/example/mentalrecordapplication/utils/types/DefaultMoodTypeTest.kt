package jp.example.mentalrecordapplication.utils.types

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DefaultMoodTypeTest {

    @Test
    fun fromInt_returns_correct_enum_for_each_typeNumber() {
        for (mood in DefaultMoodType.entries) {
            // When
            val defaultMood = DefaultMoodType.fromInt(mood.typeNumber)

            // Then
            assertThat(defaultMood).isEqualTo(mood)
        }
    }

    @Test
    fun fromInt_returns_HAPPY_if_no_matching_mood() {
        // Given
        val dummyTypeNumber: Int = -1

        // When
        val mood = DefaultMoodType.fromInt(dummyTypeNumber)

        // Then
        assertThat(mood).isEqualTo(DefaultMoodType.HAPPY)
    }
}