package com.example.mentalrecordapplication.ui.record_mood

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mentalrecordapplication.ui.bar.TopBar
import com.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@Composable
fun RecordMoodView(modifier: Modifier = Modifier) {
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            TopBar()
        }
    }
}

@Preview
@Composable
fun RecordMoodViewPreview() {
    MentalRecordAppTheme {
        RecordMoodView(modifier = Modifier.fillMaxSize())
    }
}