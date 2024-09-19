package com.example.mentalrecordapplication.main.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.record_mood.view.RecordMoodActivity
import com.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import com.example.mentalrecordapplication.utils.TextUtil

/*
* 一番最初に使うActivity
* タイトルだけ表示している
*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MentalRecordAppTheme {
                MainView(modifier = Modifier.fillMaxSize())
            }
        }
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed( {
            val intent = Intent(this,RecordMoodActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}

@Composable
fun MainView(modifier: Modifier = Modifier) {
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.mental_name),
                fontSize = TextUtil.dpToSp(dp = 32.dp)
            )

            Text(
                text = stringResource(id = R.string.record_name),
                fontSize = TextUtil.dpToSp(dp = 32.dp)
            )
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MentalRecordAppTheme {
        MainView(modifier = Modifier.fillMaxSize())
    }
}