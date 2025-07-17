package jp.example.mentalrecordapplication.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.core.net.toUri
import jp.example.mentalrecordapplication.R

object OkHttpUtil {
    fun openWebPage(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            val resolveActivity = intent.resolveActivity(context.packageManager)
            if (resolveActivity != null) {
                context.startActivity(intent)
            } else {
                if (context is Activity) {
                    Snackbar.make(
                        context.findViewById(android.R.id.content),
                        context.getString(R.string.opening_the_link_failed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            if (context is Activity) {
                Toast.makeText(context, context.getString(R.string.error_opening_link), Toast.LENGTH_SHORT).show()
            }
        }
    }
}