package jp.example.mentalrecordapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.data.DefaultMood
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@Composable
fun MoodRecordView(
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    val defaultMoodList = listOf(
        DefaultMood.HAPPY,
        DefaultMood.ANGER,
        DefaultMood.SAD,
        DefaultMood.FUN,
        DefaultMood.Normal
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBarView(screenTitle) },
        bottomBar = {
            BottomNavBarView(
                navController = navController,
                selectedTab = selectedTab,
                onSelectedTab = { onSelectedTab(it) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.support_message),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .padding(8.dp)
            )

            MoodSection(defaultMoodList)
            AddMoodSection()
        }
    }
}

@Composable
private fun MoodSection(defaultMoodList: List<DefaultMood>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        defaultMoodList.forEach {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = it.getIcon()),
                        contentDescription = "Mood Icon",
                        tint = colorResource(id = it.getColor())
                    )
                    Text(
                        text = stringResource(id = it.getName())
                    )
                }
            }
        }
    }
}

@Composable
private fun AddMoodSection() {
    OutlinedButton(
        onClick = {print("")},
        shape = CircleShape,
        modifier = Modifier
            .size(80.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add icon.",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp) // アイコンサイズを調整
            )
            Text(
                text = "追加",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp) // アイコンとテキストの間にスペースを追加
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordViewPreview() {
    MentalRecordAppTheme {
        MoodRecordView(
            navController = null,
            screenTitle = stringResource(id = R.string.mood_record_screen_title),
            selectedTab = 0,
            onSelectedTab = {}
        )
    }
}