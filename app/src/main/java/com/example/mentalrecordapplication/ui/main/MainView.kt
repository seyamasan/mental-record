package com.example.mentalrecordapplication.ui.main

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
import com.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import com.example.mentalrecordapplication.utils.TextUtil

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