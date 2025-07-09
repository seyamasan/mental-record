package jp.example.mentalrecordapplication.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.navigator.screens.ScreenData

object NavigationUtil {
    fun navigate(navController: NavHostController?, screen: ScreenData) {
        navController?.navigate(screen) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}