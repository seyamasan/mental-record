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
    fun toggleIsNewestFirst_shouldToggleIsNewestFirstValue() = runTest {
        // When
        viewModel.toggleIsNewestFirst()
        val state1 = viewModel.uiState.first()

        // Then
        assertThat(state1.isNewestFirst).isFalse()

        // When
        viewModel.toggleIsNewestFirst()
        val state2 = viewModel.uiState.first()

        // Then
        assertThat(state2.isNewestFirst).isTrue()
    }

    @Test
    fun sortAndUpdateResult_should_sort_by_newest_first_when_isNewestFirst_true() = runTest {
        // Given
        val list = listOf(dummyEntity1, dummyEntity2)

        // When
        viewModel.toggleIsNewestFirst()
        viewModel.toggleIsNewestFirst()
        viewModel.sortAndUpdateResult(list)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.listItem?.first()).isEqualTo(dummyEntity2)
        assertThat(state.listItem?.last()).isEqualTo(dummyEntity1)
    }

    @Test
    fun sortAndUpdateResult_should_sort_by_oldest_first_when_isNewestFirst_false() = runTest {
        // Given
        val list = listOf(dummyEntity2, dummyEntity1)

        // When
        viewModel.toggleIsNewestFirst()
        viewModel.sortAndUpdateResult(list)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.listItem?.first()).isEqualTo(dummyEntity1)
        assertThat(state.listItem?.last()).isEqualTo(dummyEntity2)
    }

    @Test
    fun sortAndUpdateResult_should_handle_null_listItem() = runTest {
        // When
        viewModel.sortAndUpdateResult(null)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.listItem).isNull()
    }

    @Test
    fun fetchAllItems_should_update_listItem_sorted() = runTest {
        // Given
        coEvery { repository.selectAll() } returns listOf(dummyEntity2, dummyEntity1)

        // When
        viewModel.fetchAllItems()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.listItem?.first()).isEqualTo(dummyEntity2)
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