package jp.example.mentalrecordapplication.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.data.DefaultMood
import jp.example.mentalrecordapplication.room.MoodEntity
import jp.example.mentalrecordapplication.ui.common.BottomNavBarView
import jp.example.mentalrecordapplication.ui.common.TopBarView
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.viewmodel.RecordListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordListView(
    viewModel: RecordListViewModel = hiltViewModel(),
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    val listItem by viewModel.listItem.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllItems()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarView(screenTitle)
        },
        bottomBar = {
            BottomNavBarView(
                navController = navController,
                selectedTab = selectedTab,
                onSelectedTab = { onSelectedTab(it) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listItem?.forEach {
                item {
                    MoodCard(moodEntity = it)
                }
            }
        }
    }
}

@Composable
private fun MoodCard(
    moodEntity: MoodEntity
) {
    val morning = stringResource(id = R.string.time_of_day_morning)
    val noon = stringResource(id = R.string.time_of_day_noon)
    val night = stringResource(id = R.string.time_of_day_night)

    var iconName = stringResource(id = DefaultMood.Normal.getName())
    var icon = painterResource(id = DefaultMood.Normal.getIcon())
    var iconColor = colorResource(id = DefaultMood.Normal.getColor())

    when (moodEntity.mood) {
        stringResource(id = R.string.mood_happy) -> {
            iconName = stringResource(id = DefaultMood.HAPPY.getName())
            icon = painterResource(id = DefaultMood.HAPPY.getIcon())
            iconColor = colorResource(id = DefaultMood.HAPPY.getColor())
        }
        stringResource(id = R.string.mood_anger) -> {
            iconName = stringResource(id = DefaultMood.ANGER.getName())
            icon = painterResource(id = DefaultMood.ANGER.getIcon())
            iconColor = colorResource(id = DefaultMood.ANGER.getColor())
        }
        stringResource(id = R.string.mood_sad) -> {
            iconName = stringResource(id = DefaultMood.SAD.getName())
            icon = painterResource(id = DefaultMood.SAD.getIcon())
            iconColor = colorResource(id = DefaultMood.SAD.getColor())
        }
        stringResource(id = R.string.mood_fun) -> {
            iconName = stringResource(id = DefaultMood.FUN.getName())
            icon = painterResource(id = DefaultMood.FUN.getIcon())
            iconColor = colorResource(id = DefaultMood.FUN.getColor())
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 日付
            Text(
                text = moodEntity.date,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 時間帯
            AssistChip(
                onClick = {},
                label = { Text(text = moodEntity.timeZone) },
                leadingIcon = {
                    when (moodEntity.timeZone) {
                        morning -> {
                            Icon(
                                Icons.Filled.WbTwilight,
                                contentDescription = "WbTwilight icon",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                        noon -> {
                            Icon(
                                Icons.Filled.WbSunny,
                                contentDescription = "WbSunny icon",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                        night -> {
                            Icon(
                                Icons.Filled.DarkMode,
                                contentDescription = "DarkMode icon",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    }
                }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "Mood Icon",
                    tint = iconColor
                )
                Text(
                    text = iconName
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.memo_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // メモ
            Text(
                text = moodEntity.memo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RecordListViewPreview() {
    MentalRecordAppTheme {
        RecordListView(
            navController = null,
            screenTitle = stringResource(id = R.string.record_list_screen_title),
            selectedTab = 1,
            onSelectedTab = {}
        )
    }
}