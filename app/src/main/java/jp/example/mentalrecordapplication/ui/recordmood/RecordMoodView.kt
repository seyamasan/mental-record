package jp.example.mentalrecordapplication.ui.recordmood

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SmsFailed
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.utils.DefaultMoodType
import jp.example.mentalrecordapplication.screens.Screens
import jp.example.mentalrecordapplication.ui.common.BottomNavBarView
import jp.example.mentalrecordapplication.ui.common.OkOnlyAlertDialogExample
import jp.example.mentalrecordapplication.ui.common.TopBarView
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.DateUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    val timeOfDayList = listOf(
        stringResource(id = R.string.time_of_day_morning),
        stringResource(id = R.string.time_of_day_noon),
        stringResource(id = R.string.time_of_day_night)
    )

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
            SupportMessageSection()
            MoodSection(viewModel = viewModel, uiState = uiState)
            InputSections(
                viewModel = viewModel,
                uiState = uiState,
                timeOfDayList = timeOfDayList,
                datePickerState = datePickerState
            )

            if (uiState.saveResult != null) {
                var title = ""
                var msg = ""
                var icon = Icons.Default.SmsFailed
                when (uiState.saveResult) {
                    0 -> {
                        title = stringResource(id = R.string.success)
                        msg = stringResource(id = R.string.success_save_dialog_msg)
                        icon = Icons.Default.Check
                    }
                    1 -> {
                        title = stringResource(id = R.string.null_mood_dialog_title)
                        msg = stringResource(id = R.string.null_mood_dialog_msg)
                        icon = Icons.Default.ErrorOutline
                    }
                    2 -> {
                        title = stringResource(id = R.string.null_time_zone_dialog_title)
                        msg = stringResource(id = R.string.null_time_zone_dialog_msg)
                        icon = Icons.Default.ErrorOutline
                    }
                    3 -> {
                        title = stringResource(id = R.string.null_date_dialog_title)
                        msg = stringResource(id = R.string.null_date_dialog_msg)
                        icon = Icons.Default.ErrorOutline
                    }
                    -1 -> {
                        title = stringResource(id = R.string.failure)
                        msg = stringResource(id = R.string.failure_save_dialog_msg)
                        icon = Icons.Default.ErrorOutline
                    }
                }

                OkOnlyAlertDialogExample(
                    onDismissRequest = {
                        if (uiState.saveResult == 0) {
                            onSelectedTab(Screens.recordListView.navBarIndex)
                            navController?.navigate(Screens.recordListView)
                        }
                        viewModel.updateSaveResult(null)
                    },
                    onConfirmation = {
                        if (uiState.saveResult == 0) {
                            onSelectedTab(Screens.recordListView.navBarIndex)
                            navController?.navigate(Screens.recordListView)
                        }
                        viewModel.updateSaveResult(null)
                    },
                    dialogTitle = title,
                    dialogText = msg,
                    icon = icon
                )
            }
        }
    }
}

@Composable
private fun SupportMessageSection() {
    Text(
        text = stringResource(id = R.string.support_message),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomEnd = 8.dp
                )
            )
            .padding(8.dp)
    )
}

@Composable
private fun MoodSection(
    viewModel: RecordMoodViewModel,
    uiState: RecordMoodState
) {
    val defaultMoodList = listOf(
        DefaultMoodType.HAPPY,
        DefaultMoodType.ANGER,
        DefaultMoodType.SAD,
        DefaultMoodType.FUN,
        DefaultMoodType.Normal
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        defaultMoodList.forEachIndexed { index, mood ->
            item {
                val moodName = stringResource(id = mood.getName())
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .then(
                            if (uiState.selectedMoodIndex == index) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                            } else {
                                Modifier
                            }
                        )
                        .padding(16.dp)
                        .clickable {
                            viewModel.updateMood(moodName)
                            viewModel.updateSelectedMoodIndex(index)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = mood.getIcon()),
                        contentDescription = "Mood Icon",
                        tint = colorResource(id = mood.getColor())
                    )
                    Text(
                        text = moodName
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputSections(
    viewModel: RecordMoodViewModel,
    uiState: RecordMoodState,
    timeOfDayList: List<String>,
    datePickerState: DatePickerState
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeOfDayButtonGroup(
                timeOfDayList,
                uiState.timeOfDayState,
                timeOfDaySelectedIndex = {
                    val newState = List(uiState.timeOfDayState.size) { i -> i == it }
                    viewModel.updateTimeOfDayState(newState = newState)
                    viewModel.updateTimeOfDate(timeOfDayList[it])
                },
                onItemSelected = { print(it) }
            )

            ReadOnlyDatePickerDialog(
                showDatePicker = uiState.isDatePickerVisible,
                datePickerState = datePickerState,
                onTextFieldClick = { viewModel.updateIsDatePickerVisible(newState = true) },
                onDateSelected = {
                    viewModel.updateDate(it)
                    viewModel.updateIsDatePickerVisible(newState = false)
                }
            )
            
            MemoTextFieldSheet(
                enteredMemo = uiState.enteredMemo,
                showSheet = uiState.isMemoSheetVisible,
                onTextFieldClick = { viewModel.updateIsMemoSheetVisible(newState = true) },
                onDismissRequest = { viewModel.updateIsMemoSheetVisible(newState = false) },
                onChangeTextField = { viewModel.updateMemo(it) }
            )

            RecordSaveButton(onSaveClick = { viewModel.saveMoodDetail() })
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TimeOfDayButtonGroup(
    timeOfDayList: List<String>,
    timeOfDayState: List<Boolean>,
    timeOfDaySelectedIndex: (Int) -> Unit,
    onItemSelected: (String) -> Unit
) {
    FlowRow(
        Modifier.fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally) // これでFlowRow自体を中央に寄せる
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        timeOfDayList.forEachIndexed { index, timeOdDay ->
            val isSelected = timeOfDayState[index]
            ToggleButton(
                checked = isSelected,
                onCheckedChange = {
                    timeOfDaySelectedIndex(index)
                    onItemSelected(timeOdDay)
                },
                shapes =
                    when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        timeOfDayList.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                colors = ToggleButtonDefaults.toggleButtonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.semantics { role = Role.RadioButton }
            ) {
                when (index) {
                    0 -> {
                        Icon(
                            Icons.Filled.WbTwilight,
                            contentDescription = "WbTwilight icon",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                    1 -> {
                        Icon(
                            Icons.Filled.WbSunny,
                            contentDescription = "WbSunny icon",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                    2 -> {
                        Icon(
                            Icons.Filled.DarkMode,
                            contentDescription = "DarkMode icon",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                }
                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                Text(timeOdDay)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadOnlyDatePickerDialog(
    showDatePicker: Boolean,
    datePickerState: DatePickerState,
    onTextFieldClick: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val selectedDate = datePickerState.selectedDateMillis?.let {
        DateUtil.convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.select_date_title)) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "DateRange icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .pointerInput(selectedDate) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            onTextFieldClick()
                        }
                    }
                }
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {}, // 他要素タップしても閉じさせない
                confirmButton = {
                    TextButton(onClick = { onDateSelected(selectedDate) }) {
                        Text("OK")
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    DatePicker(
                        showModeToggle = false, // カレンダーのみで入力モードなし
                        state = datePickerState
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MemoTextFieldSheet(
    enteredMemo: String,
    showSheet: Boolean,
    onTextFieldClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onChangeTextField: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    OutlinedTextField(
        value = enteredMemo,
        onValueChange = {},
        label = { Text(stringResource(id = R.string.memo_title)) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Create icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .pointerInput(enteredMemo) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        onTextFieldClick()
                    }
                }
            }
    )

    if (showSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = stringResource(id = R.string.memo_support_message))
                TextField(
                    value = enteredMemo,
                    onValueChange = { onChangeTextField(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(id = R.string.memo_placeholder)) }
                )
                Button(
                    onClick = {
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissRequest()
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("OK")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun RecordSaveButton(onSaveClick: () -> Unit) {
    val size = ButtonDefaults.LargeContainerHeight
    ElevatedButton(
        modifier = Modifier.heightIn(size),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        contentPadding = ButtonDefaults.contentPaddingFor(size),
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
        onClick = onSaveClick
    ) {
        Text(text = stringResource(id = R.string.save_button_text), style = ButtonDefaults.textStyleFor(size))
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