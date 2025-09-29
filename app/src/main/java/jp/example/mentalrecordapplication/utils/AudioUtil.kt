package jp.example.mentalrecordapplication.utils

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import java.io.IOException

class AudioUtil {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    // 録音を開始
    fun startRecording(
        context: Context,
        fileName: String
    ): Boolean {
        val dir = File(context.filesDir, FOLDER_NAME)
        if (!dir.exists()) {
            // なければディレクトリを作成
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

    // 音声ファイルを再生
    private fun startPlaying(
        context: Context,
        fileName: String
    ): Boolean {
        val dir = File(context.filesDir, FOLDER_NAME)
        val file = File(dir, fileName)

        if (file.exists()) {
            val filePath = file.absolutePath

            mediaPlayer = MediaPlayer().apply {
                try {
                    setDataSource(filePath)
                    prepare()
                    start()
                } catch (e: IOException) {
                    Log.e(TAG, "prepare() failed")
                    return false
                }
            }

            return true
        } else {
            return false
        }
    }

    // 再生中の音声ファイルを停止
    private fun stopPlaying(): Boolean {
        return mediaPlayer?.let {
            it.release()
            mediaPlayer = null

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