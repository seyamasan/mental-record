package jp.example.mentalrecordapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.util.Locale

class DateUtilTest {

    private lateinit var originalLocale: Locale

    @Before
    fun setUp() {
        originalLocale = Locale.getDefault()
        Locale.setDefault(Locale.JAPAN) // Set locale to Locale.JAPAN (ロケールをJAPANに固定)
    }

    @After
    fun tearDown() {
        // Restore original locale (元に戻す)
        Locale.setDefault(originalLocale)
    }

    @Test
    fun convertMillisToDate_returns_formatted_date_string() {
        // Given
        // Timestamp in milliseconds for 00:00:00 UTC on July 17, 2025 (2025年7月17日 00:00:00 UTC のミリ秒)
        val millis = 1752710400000L
        val formattedDateString = "2025/7/17"

        // When
        val result = DateUtil.convertMillisToDate(millis)

        // Then
        assertThat(result).isEqualTo(formattedDateString)
    }

    @Test
    fun stringToLocalDate_returns_correct_LocalDate() {
        // Given
        val dateString = "2025/7/17"

        // When
        val localDate = DateUtil.stringToLocalDate(dateString)

        // Then
        assertThat(localDate).isEqualTo(LocalDate.of(2025, 7, 17))
    }
}