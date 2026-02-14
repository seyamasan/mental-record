package jp.example.mentalrecordapplication.ui.analysis

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.analysis.components.MentalLineGraph
import jp.example.mentalrecordapplication.ui.common.bar.BottomNavBarView
import jp.example.mentalrecordapplication.ui.common.bar.TopBarView
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@Composable
fun AnalysisView(
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    MentalRecordAppTheme {
        AnalysisView(
            navController = null,
            screenTitle = stringResource(id = R.string.analysis_screen_title),
            selectedTab = 2,
            onSelectedTab = {}
        )
    }
}