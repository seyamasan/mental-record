package jp.example.mentalrecordapplication.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.screens.Screens
import jp.example.mentalrecordapplication.ui.RecordView

class AppNavigatorImpl (private val navController: NavHostController) : AppNavigator {

    @Composable
    override fun NavigateTo() {
        NavHost(
            navController = navController, Screens.recordView // 初期表示画面
        ) {
            // RecordView
            composable<Screens.RecordView> { backStackEntry ->
                val recordView: Screens.RecordView = backStackEntry.toRoute()
                RecordView(
                    navController = navController,
                    screenTitle = stringResource(id = recordView.screenTitleResId)
                )
            }
        }
    }
}