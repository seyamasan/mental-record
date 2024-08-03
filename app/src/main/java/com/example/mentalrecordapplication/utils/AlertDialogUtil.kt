package com.example.mentalrecordapplication.utils

import android.app.AlertDialog
import android.content.Context

object AlertDialogUtil {
    fun showOkDialog(title: String, msg: String, context: Context) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}