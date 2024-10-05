package com.example.mentalrecordapplication.ui.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.record_mood_activity_title),
                    color = Color.White
                )
            },
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            navigationIcon = {
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Open Dropdown Menu",
                        tint = Color.White
                    )
                }
            },
        )

        DropdownMenu(
            modifier = modifier
                .background(Color.White)
                .clip(RoundedCornerShape(16.dp)),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                },
                text = { Text("メニューアイテム1") }
            )
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    MentalRecordAppTheme {
        TopBar(modifier = Modifier)
    }
}