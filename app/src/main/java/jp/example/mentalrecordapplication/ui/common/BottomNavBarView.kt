package jp.example.mentalrecordapplication.ui.common

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.navigator.screens.ScreenData
import jp.example.mentalrecordapplication.navigator.screens.Screens
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.NavigationUtil

@Composable
fun BottomNavBarView(
    navController: NavHostController?,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    val screenList: List<Pair<ScreenData, ImageVector>> = listOf(
        Pair(Screens.recordMoodView, Icons.Filled.Create),
        Pair(Screens.recordListView, Icons.AutoMirrored.Filled.List)
    )

    NavigationBar {
        screenList.forEachIndexed { index, screenPair ->
            val screenData = screenPair.first
            val icon = screenPair.second

            NavigationBarItem(
                icon = { Icon(icon, contentDescription = "Bottom nav bar icon.") },
                label = { Text(stringResource(id = screenData.screenTitleResId)) },
                selected = index == selectedTab,
                onClick = {
                    onSelectedTab(index)
                    NavigationUtil.navigate(
                        navController = navController,
                        screen = screenData
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomNavBarViewPreview() {
    MentalRecordAppTheme {
        BottomNavBarView(
            navController = null,
            selectedTab = Screens.recordMoodView.navBarIndex,
            onSelectedTab = {}
        )
    }
}