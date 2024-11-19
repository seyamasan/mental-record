package jp.example.mentalrecordapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import jp.example.mentalrecordapplication.navigator.AppNavigatorImpl
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val intent = Intent(this,RecordMoodActivity::class.java)
//        startActivity(intent)
        enableEdgeToEdge()
        setContent {
            MentalRecordAppTheme {
                val navController = rememberNavController()
                AppNavigatorImpl(navController).NavigateTo()
            }
        }
    }
}

