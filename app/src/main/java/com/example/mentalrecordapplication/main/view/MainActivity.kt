package com.example.mentalrecordapplication.main.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mentalrecordapplication.databinding.ActivityMainBinding
import com.example.mentalrecordapplication.record_mood.view.RecordMoodActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed( {
            val intent = Intent(this,RecordMoodActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}