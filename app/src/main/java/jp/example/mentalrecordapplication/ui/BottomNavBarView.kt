package jp.example.mentalrecordapplication.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.screens.Screens
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@Composable
fun BottomNavBarView(
    navController: NavHostController?,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    NavigationBar {
        Screens.screenList.forEachIndexed { index, screenData ->
            NavigationBarItem(
                icon = { Icon(Screens.iconList[index], contentDescription = "Bottom nav bar icon.") },
                label = { Text(stringResource(id = screenData.screenTitleResId)) },
                selected = index == selectedTab,
                onClick = {
                    onSelectedTab(index)
                    navController?.navigate(screenData)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarViewPreview() {
    MentalRecordAppTheme {
        BottomNavBarView(
            navController = null,
            selectedTab = 0,
            onSelectedTab = {}
        )
    }
}