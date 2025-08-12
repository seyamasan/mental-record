package jp.example.mentalrecordapplication.ui.recordmood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.navigator.screens.Screens
import jp.example.mentalrecordapplication.ui.common.BottomNavBarView
import jp.example.mentalrecordapplication.ui.common.OkOnlyAlertDialog
import jp.example.mentalrecordapplication.ui.common.TopBarView
import jp.example.mentalrecordapplication.ui.recordmood.components.DefaultMoodSelector
import jp.example.mentalrecordapplication.ui.recordmood.components.RecordMoodInputSectionsCard
import jp.example.mentalrecordapplication.ui.recordmood.components.SupportMessage
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.NavigationUtil
import jp.example.mentalrecordapplication.utils.types.RecordMoodSaveResultType

@Composable
fun RecordMoodView(
    viewModel: RecordMoodViewModel = hiltViewModel(),
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val datePickerState = rememberDatePickerState()

    val handleResult: (RecordMoodSaveResultType) -> Unit = {
        if (it == RecordMoodSaveResultType.SUCCESS) {
            datePickerState.selectedDateMillis = null
            onSelectedTab(Screens.recordListView.navBarIndex)
            NavigationUtil.navigate(
                navController = navController,
                screen = Screens.recordListView
            )
        }
        viewModel.updateSaveResult(null)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBarView(screenTitle) },
        bottomBar = {
            BottomNavBarView(
                navController = navController,
                selectedTab = selectedTab,
                onSelectedTab = { onSelectedTab(it) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState()), // 縦スクロールを可能にしてる
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            SupportMessage()

            DefaultMoodSelector(
                selectedMood = uiState.selectedMood,
                onSelectedMood = { viewModel.updateMood(it) }
            )

            RecordMoodInputSectionsCard(
                viewModel = viewModel,
                uiState = uiState,
                datePickerState = datePickerState
            )

            uiState.saveResult?.let {
                OkOnlyAlertDialog(
                    dialogTitle = stringResource(id = it.getDialogTitleStringResourceId()),
                    dialogText = stringResource(id = it.getDialogMessageStringResourceId()),
                    icon = it.getIcon(),
                    onDismissRequest = { handleResult(it) },
                    onConfirmation = { handleResult(it) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordViewPreview() {
    MentalRecordAppTheme {
        RecordMoodView(
            navController = null,
            screenTitle = stringResource(id = R.string.mood_record_screen_title),
            selectedTab = 0,
            onSelectedTab = {}
        )
    }
}