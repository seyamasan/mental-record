package com.example.mentalrecordapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.mentalrecordapplication.record_mood.view.RecordMoodActivity
import com.example.mentalrecordapplication.ui.bar.TopBar
import com.example.mentalrecordapplication.ui.main.MainView
import com.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

/*
* タイトルだけ表示している
*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MentalRecordAppTheme {
//                MainView(modifier = Modifier.fillMaxSize())
                TopBar()
            }
        }
    }

    override fun onResume() {
        super.onResume()

//        Handler().postDelayed( {
//            val intent = Intent(this,RecordMoodActivity::class.java)
//            startActivity(intent)
//        }, 3000)
    }
}