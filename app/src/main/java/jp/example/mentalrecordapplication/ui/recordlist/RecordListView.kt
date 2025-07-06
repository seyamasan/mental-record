package jp.example.mentalrecordapplication.ui.recordlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SmsFailed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.common.BottomNavBarView
import jp.example.mentalrecordapplication.ui.common.NoRecordView
import jp.example.mentalrecordapplication.ui.common.OkOnlyAlertDialog
import jp.example.mentalrecordapplication.ui.common.TopBarView
import jp.example.mentalrecordapplication.ui.recordlist.components.RecordedMoodCard
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordListView(
    viewModel: RecordListViewModel = hiltViewModel(),
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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

        if (uiState.listItem.isNullOrEmpty()) {
            NoRecordView(padding = innerPadding)
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.listItem?.forEach {
                    item {
                        RecordedMoodCard(
                            moodEntity = it,
                            onDeleteButtonTap = { viewModel.deleteById(it) }
                        )
                    }
                }
            }
        }

        if (uiState.deleteByIdResult != null) {
            var title = ""
            var msg = ""
            var icon = Icons.Default.SmsFailed
            when (uiState.deleteByIdResult) {
                -1 -> {
                    title = stringResource(id = R.string.failure)
                    msg = stringResource(id = R.string.failed_to_delete_record_msg)
                    icon = Icons.Default.ErrorOutline
                }
            }

            OkOnlyAlertDialog(
                dialogTitle = title,
                dialogText = msg,
                icon = icon,
                onDismissRequest = {
                    viewModel.updateDeleteByIdResult(newState = null)
                },
                onConfirmation = {
                    viewModel.updateDeleteByIdResult(newState = null)
                }
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