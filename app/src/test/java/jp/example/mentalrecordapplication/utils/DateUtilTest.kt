package jp.example.mentalrecordapplication.utils

import com.google.common.truth.Truth.assertThat
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
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
        val millis = 1752710400000L // 2025年7月17日 09:00 Japan のミリ秒
        val expect = "2025/7/17"

        // When
        val result = DateUtil.convertMillisToDate(millis)

        // Then
        assertThat(result).isEqualTo(expect)
    }

    @Test
    fun stringToLocalDate_returns_correct_LocalDate() {
        // Given
        val dateString = "2025/7/17"
        val expect = LocalDate.of(2025, 7, 17)

        // When
        val localDate = DateUtil.stringToLocalDate(dateString)

        // Then
        assertThat(localDate).isEqualTo(expect)
    }

    @Test
    fun convertMillisToTimeOfDay_returns_morning_at_morning_start() {
        // Given
        val millis = 1770494400000L // 2026年2月8日 05:00 Japan のミリ秒
        val expect = TimeOfDayType.MORNING

        // When
        val timeOfDay = DateUtil.convertMillisToTimeOfDay(millis)

        assertThat(timeOfDay).isEqualTo(expect)
    }

    @Test
    fun convertMillisToTimeOfDay_returns_morning_at_morning_end() {
        // Given
        val millis = 1770515940000L // 2026年2月8日 10:59 Japan のミリ秒
        val expect = TimeOfDayType.MORNING

        // When
        val timeOfDay = DateUtil.convertMillisToTimeOfDay(millis)

        assertThat(timeOfDay).isEqualTo(expect)
    }

    @Test
    fun convertMillisToTimeOfDay_returns_noon_at_noon_start() {
        // Given
        val millis = 1770516000000L // 2026年2月8日 11:00 Japan のミリ秒
        val expect = TimeOfDayType.NOON

        // When
        val timeOfDay = DateUtil.convertMillisToTimeOfDay(millis)

        assertThat(timeOfDay).isEqualTo(expect)
    }

    @Test
    fun convertMillisToTimeOfDay_returns_noon_at_noon_end() {
        // Given
        val millis = 1770541140000L // 2026年2月8日 17:59 Japan のミリ秒
        val expect = TimeOfDayType.NOON

        // When
        val timeOfDay = DateUtil.convertMillisToTimeOfDay(millis)

        assertThat(timeOfDay).isEqualTo(expect)
    }

    @Test
    fun convertMillisToTimeOfDay_returns_night_at_night_start() {
        // Given
        val millis = 1770541200000L // 2026年2月8日 18:00 Japan のミリ秒
        val expect = TimeOfDayType.NIGHT

        // When
        val timeOfDay = DateUtil.convertMillisToTimeOfDay(millis)

        assertThat(timeOfDay).isEqualTo(expect)
    }

    @Test
    fun convertMillisToTimeOfDay_returns_night_at_night_end() {
        // Given
        val millis = 1770494340000L // 2026年2月8日 04:59 Japan のミリ秒
        val expect = TimeOfDayType.NIGHT

        // When
        val timeOfDay = DateUtil.convertMillisToTimeOfDay(millis)

        assertThat(timeOfDay).isEqualTo(expect)
    }
}