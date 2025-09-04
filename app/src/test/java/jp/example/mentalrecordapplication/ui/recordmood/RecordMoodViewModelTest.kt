package jp.example.mentalrecordapplication.ui.recordmood

import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.example.mentalrecordapplication.data.repository.MoodRepository
import jp.example.mentalrecordapplication.rule.MainDispatcherRule
import jp.example.mentalrecordapplication.utils.AudioRecorderUtil
import jp.example.mentalrecordapplication.utils.types.AudioRecordResultType
import jp.example.mentalrecordapplication.utils.types.DefaultMoodType
import jp.example.mentalrecordapplication.utils.types.RecordMoodSaveResultType
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecordMoodViewModelTest {

    private lateinit var repository: MoodRepository
    private lateinit var audioRecorder: AudioRecorderUtil
    private lateinit var viewModel: RecordMoodViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        repository = mockk()
        audioRecorder = mockk()

        viewModel = RecordMoodViewModel(repository, audioRecorder)
    }

    @Test
    fun selectedMood_state_should_be_updated() = runTest {
        // When
        viewModel.updateMood(DefaultMoodType.HAPPY)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.selectedMood).isEqualTo(DefaultMoodType.HAPPY)
    }

    @Test
    fun updateTimeOfDay_state_should_be_updated() = runTest {
        // When
        viewModel.updateTimeOfDay(TimeOfDayType.NIGHT)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.selectedTimeOfDay).isEqualTo(TimeOfDayType.NIGHT)
    }

    @Test
    fun selectedDate_state_should_be_updated() = runTest {
        // When
        viewModel.updateDate(DUMMY_DATE)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.selectedDate).isEqualTo(DUMMY_DATE)
    }

    @Test
    fun enteredMemo_state_should_be_updated() = runTest {
        // When
        viewModel.updateMemo(DUMMY_MEMO)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.enteredMemo).isEqualTo(DUMMY_MEMO)
    }

    @Test
    fun isDatePickerVisible_state_should_be_updated() = runTest {
        // When
        viewModel.updateIsDatePickerVisible(true)
        val stateTrue = viewModel.uiState.first()

        // Then
        assertThat(stateTrue.isDatePickerVisible).isTrue()

        // When
        viewModel.updateIsDatePickerVisible(false)
        val stateFalse = viewModel.uiState.first()

        // Then
        assertThat(stateFalse.isDatePickerVisible).isFalse()
    }

    @Test
    fun isMemoSheetVisible_state_should_be_updated() = runTest {
        // When
        viewModel.updateIsMemoSheetVisible(true)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.isMemoSheetVisible).isTrue()

        // When
        viewModel.updateIsMemoSheetVisible(false)
        val stateFalse = viewModel.uiState.first()

        // Then
        assertThat(stateFalse.isMemoSheetVisible).isFalse()
    }

    @Test
    fun saveResult_state_should_be_updated() = runTest {
        // When
        viewModel.updateSaveResult(RecordMoodSaveResultType.SUCCESS)
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.saveResult).isEqualTo(RecordMoodSaveResultType.SUCCESS)
    }

    @Test
    fun success_updates_saveResult_to_SUCCESS_and_clears_input() = runTest {
        // Given
        coEvery { repository.insert(any()) } returns true

        // When
        viewModel.updateMood(DefaultMoodType.HAPPY)
        viewModel.updateTimeOfDay(TimeOfDayType.MORNING)
        viewModel.updateDate(DUMMY_DATE)
        viewModel.updateMemo(DUMMY_MEMO)
        viewModel.saveMoodDetail()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.saveResult).isEqualTo(RecordMoodSaveResultType.SUCCESS)
        assertThat(state.selectedMood).isNull()
        assertThat(state.selectedTimeOfDay).isNull()
        assertThat(state.selectedDate).isNull()
        assertThat(state.enteredMemo).isNull()
        coVerify { repository.insert(any()) }
    }

    @Test
    fun failure_updates_saveResult_to_FAILURE() = runTest {
        // Given
        coEvery { repository.insert(any()) } returns false

        // When
        viewModel.updateMood(DefaultMoodType.HAPPY)
        viewModel.updateTimeOfDay(TimeOfDayType.MORNING)
        viewModel.updateDate(DUMMY_DATE)
        viewModel.saveMoodDetail()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.saveResult).isEqualTo(RecordMoodSaveResultType.FAILURE)
        coVerify { repository.insert(any()) }
    }

    @Test
    fun sets_saveResult_to_INVALID_MOOD_when_selectedMood_is_null() = runTest {
        // When
        viewModel.updateTimeOfDay(TimeOfDayType.MORNING)
        viewModel.updateDate(DUMMY_DATE)
        viewModel.saveMoodDetail()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.saveResult).isEqualTo(RecordMoodSaveResultType.INVALID_MOOD)
    }

    @Test
    fun sets_saveResult_to_INVALID_TIME_OF_DAY_when_selectedTimeOfDay_is_null() = runTest {
        // When
        viewModel.updateMood(DefaultMoodType.HAPPY)
        viewModel.updateDate(DUMMY_DATE)
        viewModel.saveMoodDetail()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.saveResult).isEqualTo(RecordMoodSaveResultType.INVALID_TIME_OF_DAY)
    }

    @Test
    fun sets_saveResult_to_INVALID_DATE_when_selectedDate_is_null() = runTest {
        // Given
        viewModel.updateMood(DefaultMoodType.HAPPY)
        viewModel.updateTimeOfDay(TimeOfDayType.MORNING)
        viewModel.saveMoodDetail()
        val state = viewModel.uiState.first()

        // Then
        assertThat(state.saveResult).isEqualTo(RecordMoodSaveResultType.INVALID_DATE)
    }

    @Test
    fun startAudioRecord_alreadyRecording_should_do_nothing() = runTest {
        // Given
        val context = mockk<Context>(relaxed = true)
        every { audioRecorder.startRecording(any()) } returns true
        viewModel.startAudioRecord(context) // スタートしている前提

        // When
        viewModel.startAudioRecord(context)

        // Then
        verify(exactly = 1) { audioRecorder.startRecording(any()) }
        val state = viewModel.uiState.first()
        assertThat(state.isAudioRecording).isTrue()
        assertThat(state.audioRecordResultType).isNull()
    }

    @Test
    fun startAudioRecord_success_should_update_isAudioRecording_true() = runTest {
        // Given
        val context = mockk<Context>(relaxed = true)
        every { audioRecorder.startRecording(any()) } returns true

        // When
        viewModel.startAudioRecord(context)

        // Then
        verify(exactly = 1) { audioRecorder.startRecording(context) }
        val state = viewModel.uiState.first()
        assertThat(state.isAudioRecording).isTrue()
        assertThat(state.audioRecordResultType).isNull()
    }

    @Test
    fun startAudioRecord_failure_should_update_resultType() = runTest {
        // Given
        val context = mockk<Context>(relaxed = true)
        every { audioRecorder.startRecording(any()) } returns false

        // When
        viewModel.startAudioRecord(context)

        // Then
        verify { audioRecorder.startRecording(context) }
        val state = viewModel.uiState.first()
        assertThat(state.isAudioRecording).isFalse()
        assertThat(state.audioRecordResultType).isEqualTo(AudioRecordResultType.START_FAILURE)
    }

    @Test
    fun stopAudioRecord_notRecording_should_do_nothing() = runTest {
        // When
        viewModel.stopAudioRecord()

        // Then
        verify(exactly = 0) { audioRecorder.stopRecording() }
        val state = viewModel.uiState.first()
        assertThat(state.isAudioRecording).isFalse()
        assertThat(state.audioRecordResultType).isNull()
    }

    @Test
    fun stopAudioRecord_success_should_update_isAudioRecording_false() = runTest {
        // Given
        val context = mockk<Context>(relaxed = true)
        every { audioRecorder.startRecording(context) } returns true
        every { audioRecorder.stopRecording() } returns true
        viewModel.startAudioRecord(context) // スタートしている前提

        // When
        viewModel.stopAudioRecord()

        // Then
        verify { audioRecorder.stopRecording() }
        val state = viewModel.uiState.first()
        assertThat(state.isAudioRecording).isFalse()
        assertThat(state.audioRecordResultType).isNull()
    }

    @Test
    fun stopAudioRecord_failure_should_update_resultType() = runTest {
        // Given
        val context = mockk<Context>(relaxed = true)
        every { audioRecorder.startRecording(context) } returns true
        every { audioRecorder.stopRecording() } returns false
        viewModel.startAudioRecord(context)


        // When
        viewModel.stopAudioRecord()

        // Then
        verify { audioRecorder.stopRecording() }
        val state = viewModel.uiState.first()
        assertThat(state.isAudioRecording).isTrue()
        assertThat(state.audioRecordResultType).isEqualTo(AudioRecordResultType.STOP_FAILURE)
    }

    companion object {
        private const val DUMMY_DATE = "2025/07/16"
        private const val DUMMY_MEMO = "Memo"
    }
}