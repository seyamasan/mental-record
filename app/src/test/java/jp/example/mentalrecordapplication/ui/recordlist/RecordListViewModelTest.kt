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
import java.time.LocalDate
import java.time.ZoneId

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
    fun applyListItemFilter_should_sort_by_newest_first() = runTest {
        // Given
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.toggleIsNewestFirst()
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem?.first()).isEqualTo(dummyEntity5)
        assertThat(state.filteredListItem?.last()).isEqualTo(dummyEntity6)
    }

    @Test
    fun should_filter_only_morning_items_when_selectedTimeOfDay_is_MORNING() = runTest {
        // Given
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.updateSelectedTimeOfDay(TimeOfDayType.MORNING)
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem).containsExactly(dummyEntity2, dummyEntity4)
    }

    @Test
    fun should_filter_only_morning_items_when_selectedTimeOfDay_is_NOON() = runTest {
        // Given
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.updateSelectedTimeOfDay(TimeOfDayType.NOON)
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem).containsExactly(dummyEntity1, dummyEntity6)
    }

    @Test
    fun should_filter_only_morning_items_when_selectedTimeOfDay_is_NIGHT() = runTest {
        // Given
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.updateSelectedTimeOfDay(TimeOfDayType.NIGHT)
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem).containsExactly(dummyEntity3, dummyEntity5)
    }

    @Test
    fun applyListItemFilter_filters_items_from_start_date_range() = runTest {
        // Given
        val startDate = LocalDate.parse("2025-06-01")
        val startMillis = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.updateSelectedDateRange(Pair(startMillis, null)) // 2025/06/01〜の範囲
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem).containsExactly(dummyEntity6, dummyEntity2, dummyEntity1, dummyEntity4)
    }

    @Test
    fun applyListItemFilter_should_filter_by_date_range() = runTest {
        // Given
        val startDate = LocalDate.parse("2025-06-01")
        val endDate = LocalDate.parse("2025-08-01")
        val startMillis = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endMillis = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.updateSelectedDateRange(Pair(startMillis, endMillis)) // 2025/06/01〜2025/08/01の範囲
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem).containsExactly(dummyEntity2, dummyEntity1, dummyEntity4)
    }

    @Test
    fun applyListItemFilter_should_return_empty_when_start_date_is_after_end_date() = runTest {
        // Given
        val startDate = LocalDate.parse("2025-09-01")
        val endDate = LocalDate.parse("2025-08-01")
        val startMillis = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endMillis = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.updateSelectedDateRange(Pair(startMillis, endMillis)) // 開始日 > 終了日
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem).isEmpty()
    }

    @Test
    fun applyListItemFilter_should_filter_by_all_pattern() = runTest {
        // Given
        val startDate = LocalDate.parse("2025-06-01")
        val endDate = LocalDate.parse("2025-09-01")
        val startMillis = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endMillis = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        viewModel.toggleIsNewestFirst()
        viewModel.updateSelectedTimeOfDay(TimeOfDayType.MORNING)
        viewModel.updateSelectedDateRange(Pair(startMillis, endMillis))
        viewModel.applyListItemFilter()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem).containsExactly(dummyEntity4, dummyEntity2)
    }

    @Test
    fun fetchAllItems_should_update_listItem_sorted() = runTest {
        // Given
        coEvery { repository.selectAll() } returns dummyList

        // When
        viewModel.fetchAllItems()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.filteredListItem?.first()).isEqualTo(dummyEntity6)
        assertThat(state.filteredListItem?.last()).isEqualTo(dummyEntity5)
        assertThat(state.filteredListItem?.count()).isEqualTo(dummyList.count())
        coVerify { repository.selectAll() }
    }

    @Test
    fun deleteById_success_should_update_deleteByIdResult_and_refresh_list() = runTest {
        // Given
        val deletedList = listOf(dummyEntity6, dummyEntity2, dummyEntity4, dummyEntity3, dummyEntity5)
        coEvery { repository.deleteById(DUMMY_ID) } returns true
        coEvery { repository.selectAll() } returns deletedList

        // When
        viewModel.deleteById(DUMMY_ID)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.deleteByIdResult).isTrue()
        state.filteredListItem?.forEachIndexed { index, filteredEntity ->
            assertThat(filteredEntity.id).isEqualTo(deletedList[index].id)
        }

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
            date = "2025/7/16",
            timeOfDay = TimeOfDayType.NOON,
            memo = "Memo"
        )

        private val dummyEntity2 = MoodEntity(
            id = 2,
            mood = DefaultMoodType.FUN,
            date = "2025/7/17",
            timeOfDay = TimeOfDayType.MORNING,
            memo = "Memo"
        )

        private val dummyEntity3: MoodEntity = MoodEntity(
            id = 3,
            mood = DefaultMoodType.SAD,
            date = "2025/5/16",
            timeOfDay = TimeOfDayType.NIGHT,
            memo = null
        )

        private val dummyEntity4 = MoodEntity(
            id = 4,
            mood = DefaultMoodType.ANGER,
            date = "2025/6/17",
            timeOfDay = TimeOfDayType.MORNING,
            memo = null
        )

        private val dummyEntity5: MoodEntity = MoodEntity(
            id = 5,
            mood = DefaultMoodType.Normal,
            date = "2024/11/16",
            timeOfDay = TimeOfDayType.NIGHT,
            memo = null
        )

        private val dummyEntity6 = MoodEntity(
            id = 6,
            mood = DefaultMoodType.FUN,
            date = "2025/8/17",
            timeOfDay = TimeOfDayType.NOON,
            memo = null
        )

        private val dummyList = listOf(dummyEntity1, dummyEntity2, dummyEntity3, dummyEntity4, dummyEntity5, dummyEntity6)
    }
}