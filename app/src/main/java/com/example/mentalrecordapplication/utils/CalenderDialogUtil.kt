package com.example.mentalrecordapplication.utils

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

object CalenderDialogUtil {
    interface DatePickerCallback {
        fun onDateSelected(year: Int, month: Int, day: Int)
    }

    fun showDatePickerDialog(context: Context, callback: DatePickerCallback) {
        // 現在の日付を取得
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // コールバック関数を呼び出して選択された日付を渡す
                // `selectedMonth` は 0 から 11 の範囲なので、これをユーザー向けには 1 から 12 に変換
                callback.onDateSelected(selectedYear, selectedMonth + 1, selectedDay)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}