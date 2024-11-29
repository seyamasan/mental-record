package jp.example.mentalrecordapplication.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import jp.example.mentalrecordapplication.screens.Screens
import jp.example.mentalrecordapplication.ui.RecordMoodView
import jp.example.mentalrecordapplication.ui.RecordListView

class AppNavigatorImpl (private val navController: NavHostController) : AppNavigator {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun NavigateTo() {
        var selectedTab by rememberSaveable { mutableIntStateOf(0) }
        NavHost(
            navController = navController, Screens.screenList.first() // 初期表示画面
        ) {
            // RecordMoodView
            composable<Screens.RecordMoodView> { backStackEntry ->
                val moodRecordView: Screens.RecordMoodView = backStackEntry.toRoute()
                RecordMoodView(
                    navController = navController,
                    screenTitle = stringResource(id = moodRecordView.screenTitleResId),
                    selectedTab = selectedTab,
                    onSelectedTab = { selectedTab = it }
                )
            }

            // RecordListView
            composable<Screens.RecordListView> { backStackEntry ->
                val recordListView: Screens.RecordListView = backStackEntry.toRoute()
                RecordListView(
                    navController = navController,
                    screenTitle = stringResource(id = recordListView.screenTitleResId),
                    selectedTab = selectedTab,
                    onSelectedTab = { selectedTab = it }
                )
            }
        }
    }
}