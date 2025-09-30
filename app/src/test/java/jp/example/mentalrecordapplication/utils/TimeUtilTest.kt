package jp.example.mentalrecordapplication.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TimeUtilTest {
    @Test
    fun formatElapsedTime_should_return_zero_time() {
        // Given
        val seconds = 0
        val formattedTime = "00:00"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }

    @Test
    fun formatElapsedTime_should_return_seconds_only() {
        // Given
        val seconds = 7
        val formattedTime = "00:07"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }

    @Test
    fun formatElapsedTime_should_return_seconds_with_double_digits() {
        // Given
        val seconds = 59
        val formattedTime = "00:59"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }

    @Test
    fun formatElapsedTime_should_return_exactly_one_minute() {
        // Given
        val seconds = 60
        val formattedTime = "01:00"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }

    @Test
    fun formatElapsedTime_should_return_minutes_and_seconds() {
        // Given
        val seconds = 125
        val formattedTime = "02:05"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }

    @Test
    fun formatElapsedTime_should_return_maximum_under_an_hour() {
        // Given
        val seconds = 3599 // 59分59秒
        val formattedTime = "59:59"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }

    @Test
    fun formatElapsedTime_should_return_exactly_one_hour() {
        // Given
        val seconds = 3600 // 60分00秒
        val formattedTime = "60:00"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }

    @Test
    fun formatElapsedTime_should_return_large_minutes_and_seconds() {
        // Given
        val seconds = 7265 // 121分5秒
        val formattedTime = "121:05"

        // When
        val result = TimeUtil.formatElapsedTime(seconds)

        // Then
        assertThat(result).isEqualTo(formattedTime)
    }
}