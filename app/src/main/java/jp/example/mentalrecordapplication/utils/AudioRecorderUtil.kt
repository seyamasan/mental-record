package jp.example.mentalrecordapplication.utils

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import java.io.IOException

class AudioRecorderUtil {

    private var mediaRecorder: MediaRecorder? = null

    // 録音を開始
    fun startRecording(
        context: Context,
        fileName: String = "test.3gp"
    ): Boolean {
        // フォルダ作成
        val dir = File(context.filesDir, FOLDER_NAME)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val filePath = File(dir, fileName).absolutePath

        mediaRecorder = createMediaRecorder(context).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(filePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(TAG, "prepare() failed")
                return false
            }

            start()
        }

        return true
    }

    // 録音を停止
    fun stopRecording(): Boolean {
        return mediaRecorder?.let {
            it.apply {
                stop()
                release()
            }

            mediaRecorder = null
            true
        } ?: run {
            false
        }
    }

    private fun createMediaRecorder(context: Context): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // API 31以上は新コンストラクタ
            MediaRecorder(context)
        } else {
            // API 30以下は旧コンストラクタ（非推奨）
            MediaRecorder()
        }
    }

    companion object {
        private const val TAG = "AudioRecorderUtil"
        private const val FOLDER_NAME = "MentalRecordAudio"
    }
}