package jp.example.mentalrecordapplication

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.example.mentalrecordapplication.navigator.AppNavigatorImpl
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var permissionToRecordAccepted = false

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

        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }

        if (!permissionToRecordAccepted) finish() // 録音の権限が付与されていなければActivityを終了
    }
}

