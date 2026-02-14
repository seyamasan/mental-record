package jp.example.mentalrecordapplication.ui.analysis

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.ui.analysis.components.MentalLineGraph
import jp.example.mentalrecordapplication.ui.common.bar.BottomNavBarView
import jp.example.mentalrecordapplication.ui.common.bar.TopBarView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalysisView(
    viewModel: AnalysisViewModel = hiltViewModel(),
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
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
        Column(modifier = Modifier.padding(innerPadding)) {
            MentalLineGraph()
        }
    }
}