package jp.example.mentalrecordapplication.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.example.mentalrecordapplication.data.local.MoodDataSource
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import kotlinx.coroutines.test.runTest
import org.junit.BeforeClass
import org.junit.Test

class MoodRepositoryImplTest {

    companion object {
        private const val DUMMY_ID = 1

        private val dummyEntity: MoodEntity = MoodEntity(
            id = 0,
            mood = DefaultMoodType.HAPPY,
            date = "2024/11/11",
            timeOfDay = TimeOfDayType.MORNING,
            memo = "memo"
        )

        private lateinit var dataSource: MoodDataSource
        private lateinit var repository: MoodRepositoryImpl

        @JvmStatic
        @BeforeClass
        fun setup() {
            dataSource = mockk(relaxed = true)
            repository = MoodRepositoryImpl(dataSource)
        }
    }

    @Test
    fun insert_returns_true() = runTest {
        // Given
        coEvery { repository.insert(dummyEntity) } returns true

        // When
        val result = repository.insert(dummyEntity)

        // Then
        assertThat(result).isTrue()
        coVerify { repository.insert(dummyEntity) }
    }

    @Test
    fun insert_returns_false() = runTest {
        // Given
        coEvery { repository.insert(dummyEntity) } returns false

        // When
        val result = repository.insert(dummyEntity)

        // Then
        assertThat(result).isFalse()
        coVerify { repository.insert(dummyEntity) }
    }

    @Test
    fun deleteById_returns_true() = runTest {
        // Given
        coEvery { repository.deleteById(DUMMY_ID) } returns true

        // When
        val result = repository.deleteById(DUMMY_ID)

        // Then
        assertThat(result).isTrue()
        coVerify { repository.deleteById(DUMMY_ID) }
    }

    @Test
    fun deleteById_returns_false() = runTest {
        // Given
        coEvery { repository.deleteById(DUMMY_ID) } returns false

        // When
        val result = repository.deleteById(DUMMY_ID)

        // Then
        assertThat(result).isFalse()
        coVerify { repository.deleteById(DUMMY_ID) }
    }

    @Test
    fun selectAll_returns_list() = runTest {
        // Given
        coEvery { repository.selectAll() } returns listOf(dummyEntity)

        // When
        val result = repository.selectAll()

        // Then
        assertThat(result).isNotEmpty()
        coVerify { repository.selectAll() }
    }

    @Test
    fun deleteAll_returns_true() = runTest {
        // Given
        coEvery { repository.deleteAll() } returns true

        // When
        val result = repository.deleteAll()

        // Then
        assertThat(result).isTrue()
        coVerify { repository.deleteAll() }
    }

    @Test
    fun deleteAll_returns_false() = runTest {
        // Given
        coEvery { repository.deleteAll() } returns false

        // When
        val result = repository.deleteAll()

        // Then
        assertThat(result).isFalse()
        coVerify { repository.deleteAll() }
    }
}