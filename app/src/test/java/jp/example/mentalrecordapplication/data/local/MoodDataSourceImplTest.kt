package jp.example.mentalrecordapplication.data.local

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.example.mentalrecordapplication.data.local.room.MoodDao
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import kotlinx.coroutines.test.runTest
import org.junit.BeforeClass
import org.junit.Test

class MoodDataSourceImplTest {

    companion object {
        private const val DUMMY_ID = 1

        private val dummyEntity: MoodEntity = MoodEntity(
            id = 0,
            mood = DefaultMoodType.HAPPY,
            date = "2024/11/11",
            timeOfDay = TimeOfDayType.MORNING,
            memo = "memo"
        )

        private lateinit var dao: MoodDao
        private lateinit var dataSource: MoodDataSourceImpl

        @JvmStatic
        @BeforeClass
        fun setup() {
            dao = mockk(relaxed = true)
            dataSource = MoodDataSourceImpl(dao)
        }
    }

    @Test
    fun insert_succeeds() = runTest {
        // Given
        coEvery { dao.insert(dummyEntity) } returns Unit

        // When
        val result = dataSource.insert(dummyEntity)

        // Then
        assertThat(result).isTrue()
        coVerify { dao.insert(dummyEntity) }
    }

    @Test
    fun insert_should_fail_for_unknown_reason() = runTest {
        // Given
        coEvery { dao.insert(dummyEntity) } throws Exception("DB error")

        // When
        val result = dataSource.insert(dummyEntity)

        // Then
        assertThat(result).isFalse()
        coVerify { dao.insert(dummyEntity) }
    }

    @Test
    fun deleteById_succeeds() = runTest {
        // Given
        coEvery { dao.deleteById(DUMMY_ID) } returns Unit

        // When
        val result = dataSource.deleteById(DUMMY_ID)

        // Then
        assertThat(result).isTrue()
        coVerify { dao.deleteById(DUMMY_ID) }
    }

    @Test
    fun deleteById_should_fail_for_unknown_reason() = runTest {
        // Given
        coEvery { dao.deleteById(DUMMY_ID) } throws Exception("DB error")

        // When
        val result = dataSource.deleteById(DUMMY_ID)

        // Then
        assertThat(result).isFalse()
        coVerify { dao.deleteById(DUMMY_ID) }
    }

    @Test
    fun selectAll_succeeds() = runTest {
        // Given
        coEvery { dao.selectAll() } returns listOf(dummyEntity)

        // When
        val result = dataSource.selectAll()

        // Then
        assertThat(result).isNotEmpty()
        coVerify { dao.selectAll() }
    }

    @Test
    fun deleteAll_succeeds() = runTest {
        // Given
        coEvery { dao.deleteAll() } returns Unit

        // When
        val result = dataSource.deleteAll()

        // Then
        assertThat(result).isTrue()
        coVerify { dao.deleteAll() }
    }

    @Test
    fun deleteAll_should_fail_for_unknown_reason() = runTest {
        // Given
        coEvery { dao.deleteAll() } throws Exception("DB error")

        // When
        val result = dataSource.deleteAll()

        // Then
        assertThat(result).isFalse()
        coVerify { dao.deleteAll() }
    }
}