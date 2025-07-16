package jp.example.mentalrecordapplication.ui.recordlist

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import jp.example.mentalrecordapplication.data.repository.MoodRepository
import jp.example.mentalrecordapplication.rule.MainDispatcherRule
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecordListViewModelTest {

    private lateinit var repository: MoodRepository
    private lateinit var viewModel: RecordListViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = RecordListViewModel(repository)
    }

    @Test
    fun deleteByIdResult_state_should_be_updated() = runTest {
        // When
        viewModel.updateDeleteByIdResult(true)
        val stateTrue = viewModel.uiState.first()

        // Then
        assertThat(stateTrue.deleteByIdResult).isTrue()
    }

    @Test
    fun fetchAllItems_should_update_listItem_sorted() = runTest {
        // Given
        coEvery { repository.selectAll() } returns listOf(dummyEntity2, dummyEntity1)

        // When
        viewModel.fetchAllItems()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.listItem?.first()).isEqualTo(dummyEntity1)
        assertThat(state.listItem?.count()).isEqualTo(2)
        coVerify { repository.selectAll() }
    }

    @Test
    fun deleteById_success_should_update_deleteByIdResult_and_refresh_list() = runTest {
        // Given
        coEvery { repository.deleteById(1) } returns true
        coEvery { repository.selectAll() } returns listOf(dummyEntity1)

        // When
        viewModel.deleteById(DUMMY_ID)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.deleteByIdResult).isTrue()
        assertThat(state.listItem).containsExactly(dummyEntity1)
        coVerify { repository.deleteById(DUMMY_ID) }
        coVerify { repository.selectAll() }
    }

    @Test
    fun deleteById_failure_should_update_deleteByIdResult_and_not_refresh_list() = runTest {
        // Given
        coEvery { repository.deleteById(DUMMY_ID) } returns false

        // When
        viewModel.deleteById(DUMMY_ID)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.deleteByIdResult).isFalse()
        coVerify { repository.deleteById(DUMMY_ID) }
        coVerify(exactly = 0) { repository.selectAll() }
    }

    companion object {
        private const val DUMMY_ID = 1

        private val dummyEntity1: MoodEntity = MoodEntity(
            id = DUMMY_ID,
            mood = DefaultMoodType.HAPPY,
            date = "2025/07/16",
            timeOfDay = TimeOfDayType.MORNING,
            memo = "Memo"
        )

        private val dummyEntity2 = MoodEntity(
            id = 2,
            mood = DefaultMoodType.FUN,
            date = "2025/07/17",
            timeOfDay = TimeOfDayType.MORNING,
            memo = "Memo"
        )
    }
}