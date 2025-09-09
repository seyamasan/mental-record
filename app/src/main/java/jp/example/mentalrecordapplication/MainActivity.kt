package jp.example.mentalrecordapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.example.mentalrecordapplication.navigator.AppNavigatorImpl
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

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

        viewModel.updatePermissionResult(permissionToRecordAccepted)
    }

    fun getPermissionToRecordAccepted() = viewModel.permissionToRecordAccepted

    companion object {
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}

