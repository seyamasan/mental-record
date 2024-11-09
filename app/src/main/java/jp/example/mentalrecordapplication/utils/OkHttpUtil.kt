package jp.example.mentalrecordapplication.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object OkHttpUtil {
    fun openWebPage(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            // インテントを解決できるアクティビティがあるか確認
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // アクティビティが見つからない場合の処理（例えば、Snackbarで通知）
                Snackbar.make((context as Activity).findViewById(android.R.id.content), "No browser found", Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // 例外が発生した場合の処理（エラーメッセージをログに出力）
            Log.e("OpenWebPageError", "Error opening web page", e)
            // 必要に応じてユーザーに通知することもできます
            Toast.makeText(context, "Failed to open web page", Toast.LENGTH_SHORT).show()
        }
    }
}