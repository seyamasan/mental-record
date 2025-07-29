package jp.example.mentalrecordapplication.ui.recordlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
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
import jp.example.mentalrecordapplication.ui.recordlist.components.FilterSortButton
import jp.example.mentalrecordapplication.ui.recordlist.components.FilterSortDialog
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
            NoRecordView()
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                FilterSortButton(
                    onTapped = { viewModel.updateShowFilterSortDialog(newState = true) }
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.listItem?.let { list ->
                        items(list) { item ->
                            RecordedMoodCard(
                                moodEntity = item,
                                onDeleteButtonTap = { viewModel.deleteById(item.id) }
                            )
                        }
                    }
                }
            }
        }

        if (uiState.deleteByIdResult == false) {
            OkOnlyAlertDialog(
                dialogTitle = stringResource(id = R.string.failure),
                dialogText = stringResource(id = R.string.failed_to_delete_record_msg),
                icon = Icons.Default.ErrorOutline,
                onDismissRequest = {
                    viewModel.updateDeleteByIdResult(newState = null)
                },
                onConfirmation = {
                    viewModel.updateDeleteByIdResult(newState = null)
                }
            )
        }

        if (uiState.showFilterSortDialog) {
            FilterSortDialog(
                isAscending = uiState.isNewestFirst,
                onToggleSortOrder = {
                    viewModel.toggleIsNewestFirst()
                    viewModel.sortAndUpdateResult(uiState.listItem)
                },
                onDismissRequest = { viewModel.updateShowFilterSortDialog(newState = false) }
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