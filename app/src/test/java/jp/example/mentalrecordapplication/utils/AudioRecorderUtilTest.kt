package jp.example.mentalrecordapplication.utils

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException

class AudioRecorderUtilTest {

    private lateinit var context: Context
    private lateinit var util: AudioRecorderUtil

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        util = spyk(AudioRecorderUtil()) // 一部だけモック化したいのでSpyを使う

        // Logをモック化
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun start_recording_success_returns_true() {
        // Given
        val fileName = "test.3gp"
        val file = File("/tmp", fileName)
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
        val file = File("/tmp", fileName)
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
}