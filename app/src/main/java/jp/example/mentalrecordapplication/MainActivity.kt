package jp.example.mentalrecordapplication

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.example.mentalrecordapplication.navigator.AppNavigatorImpl
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import androidx.core.net.toUri

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MentalRecordAppTheme {
                val navController = rememberNavController()
                AppNavigatorImpl(navController).NavigateTo()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }

        if (!permissionToRecordAccepted) {
            showAlertDialog(
                title = getString(R.string.record_audio_permission_dialog_title),
                message = getString(R.string.record_audio_permission_dialog_message),
                positiveButtonLabel = getString(R.string.permission_dialog_positive),
                negativeButtonLabel = getString(R.string.permission_dialog_negative),
                onPositiveButtonTapped = {
                    // アプリの権限設定画面を開く
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = "package:$packageName".toUri()
                    }
                    startActivity(intent)
                }
            )
        }
    }

    fun requestRecordAudioPermission() = ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveButtonLabel: String,
        negativeButtonLabel: String,
        onPositiveButtonTapped: () -> Unit = {},
        onNegativeButtonTapped: () -> Unit = {}
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonLabel) { _, _ ->
                onPositiveButtonTapped()
            }
            .setNegativeButton(negativeButtonLabel) { _, _ ->
                onNegativeButtonTapped()
            }
            .show()
    }

    companion object {
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}

