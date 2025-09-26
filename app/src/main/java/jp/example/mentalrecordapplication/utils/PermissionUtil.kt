package jp.example.mentalrecordapplication.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import jp.example.mentalrecordapplication.MainActivity

object PermissionUtil {
    fun checkAndRequestRecordAudioPermission(context: Context): Boolean {
        val permission = Manifest.permission.RECORD_AUDIO
        val hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            return true
        } else {
            val activity = context as? MainActivity
            activity?.requestRecordAudioPermission()

            return false
        }
    }
}