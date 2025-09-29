package jp.example.mentalrecordapplication.utils

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException
import kotlin.io.path.createTempDirectory

class AudioUtilTest {

    private lateinit var context: Context
    private lateinit var util: AudioUtil
    private lateinit var tempDir: File
    private val folderName = "MentalRecordAudio"

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        util = spyk(AudioUtil()) // 一部だけモック化したいのでSpyを使う

        // 一時ディレクトリ作成
        tempDir = createTempDirectory("dummyTmpDir").toFile()
        every { context.filesDir } returns tempDir

        // Logをモック化
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        // モック解除
        unmockkAll()

        // 一時ディレクトリ削除
        tempDir.delete()
    }

    @Test
    fun start_recording_success_returns_true() {
        // Given
        val fileName = "test.3gp"
        val dir = File("/tmp", folderName)
        val file = File(dir, fileName)
        every { context.filesDir } returns File("/tmp")
        val mediaRecorder = mockk<MediaRecorder>(relaxed = true)
        // createMediaRecorderをモック
        every { util["createMediaRecorder"](context) } returns mediaRecorder

        // When
        val result = util.startRecording(context, fileName)

        // Then
        assertThat(result).isTrue()
        // 下記の順番で呼び出されたことを確認
        verifyOrder {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setOutputFile(file.absolutePath)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder.prepare()
            mediaRecorder.start()
        }
    }

    @Test
    fun start_recording_prepare_throws_exception_returns_false() {
        // Given
        val fileName = "test.3gp"
        val dir = File("/tmp", folderName)
        val file = File(dir, fileName)
        every { context.filesDir } returns File("/tmp")
        val mediaRecorder = mockk<MediaRecorder>(relaxed = true)
        every { util["createMediaRecorder"](context) } returns mediaRecorder
        every { mediaRecorder.prepare() } throws IOException("prepare failed")

        // When
        val result = util.startRecording(context, fileName)

        // Then
        assertThat(result).isFalse()
        // start()は呼び出されていないはず
        verify {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setOutputFile(file.absolutePath)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder.prepare()
        }
        verify(exactly = 0) { mediaRecorder.start() }
    }

    @Test
    fun stop_recording_when_mediaRecorder_is_not_null_should_return_true() {
        // Given
        val fileName = "test.3gp"
        every { context.filesDir } returns File("/tmp")
        val mediaRecorder = mockk<MediaRecorder>(relaxed = true)
        every { util["createMediaRecorder"](context) } returns mediaRecorder
        util.startRecording(context, fileName) // スタートしておく

        // When
        val result = util.stopRecording()

        // Then
        assertThat(result).isTrue()
        verifyOrder {
            mediaRecorder.stop()
            mediaRecorder.release()
        }
    }

    @Test
    fun stop_recording_when_mediaRecorder_is_null_should_return_false() {
        // Given
        val mediaRecorder = mockk<MediaRecorder>(relaxed = true)

        // When
        val result = util.stopRecording()

        // Then
        assertThat(result).isFalse()
        verify(exactly = 0) {
            mediaRecorder.stop()
            mediaRecorder.release()
        }
    }

    @Test
    fun start_playing_success_returns_true_and_calls_in_order() {
        // Given
        val fileName = "test.3gp"
        val dir = File(tempDir, folderName).apply { mkdirs() }
        val file = File(dir, fileName).apply {
            createNewFile()
            deleteOnExit()
        }
        val expectedPath = file.absolutePath

        // MediaPlayer コンストラクタをモック
        mockkConstructor(MediaPlayer::class)
        every { anyConstructed<MediaPlayer>().setDataSource(expectedPath) } returns Unit
        every { anyConstructed<MediaPlayer>().prepare() } returns Unit
        every { anyConstructed<MediaPlayer>().start() } returns Unit

        // When
        val result = util.startPlaying(context, fileName)

        // Then
        assertThat(result).isTrue()
        verifyOrder {
            anyConstructed<MediaPlayer>().setDataSource(expectedPath)
            anyConstructed<MediaPlayer>().prepare()
            anyConstructed<MediaPlayer>().start()
        }
    }

    @Test
    fun start_playing_prepare_throws_exception_returns_false_and_does_not_start() {
        // Given
        val fileName = "bad.3gp"
        val dir = File(tempDir, folderName).apply { mkdirs() }
        val file = File(dir, fileName).apply {
            createNewFile()
            deleteOnExit()
        }
        val expectedPath = file.absolutePath

        mockkConstructor(MediaPlayer::class)
        every { anyConstructed<MediaPlayer>().setDataSource(expectedPath) } returns Unit
        every { anyConstructed<MediaPlayer>().prepare() } throws IOException("prepare failed")

        // When
        val result = util.startPlaying(context, fileName)

        // Then
        assertThat(result).isFalse()
        verify {
            anyConstructed<MediaPlayer>().setDataSource(expectedPath)
            anyConstructed<MediaPlayer>().prepare()
        }
        verify(exactly = 0) { anyConstructed<MediaPlayer>().start() }
    }

    @Test
    fun start_playing_when_file_not_exists_returns_false() {
        // Given
        val fileName = "missing.3gp"
        mockkConstructor(MediaPlayer::class)

        // When
        val result = util.startPlaying(context, fileName)

        // Then
        assertThat(result).isFalse()
        verify(exactly = 0) {
            anyConstructed<MediaPlayer>().setDataSource("dummy")
            anyConstructed<MediaPlayer>().prepare()
            anyConstructed<MediaPlayer>().start()
        }
    }

    @Test
    fun stop_playing_when_mediaPlayer_is_set_release_called_and_returns_true() {
        // Given
        val fileName = "to_stop.3gp"
        val dir = File(tempDir, folderName).apply { mkdirs() }
        val file = File(dir, fileName).apply {
            createNewFile()
            deleteOnExit()
        }
        val expectedPath = file.absolutePath

        mockkConstructor(MediaPlayer::class)
        every { anyConstructed<MediaPlayer>().setDataSource(expectedPath) } returns Unit
        every { anyConstructed<MediaPlayer>().prepare() } returns Unit
        every { anyConstructed<MediaPlayer>().start() } returns Unit
        every { anyConstructed<MediaPlayer>().release() } returns Unit

        assertThat(util.startPlaying(context, fileName)).isTrue()

        // When
        val result = util.stopPlaying()

        // Then
        assertThat(result).isTrue()
        verify { anyConstructed<MediaPlayer>().release() }
    }

    @Test
    fun stop_playing_when_mediaPlayer_is_null_returns_false() {
        val result = util.stopPlaying()
        assertThat(result).isFalse()
    }
}