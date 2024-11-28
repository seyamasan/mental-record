package jp.example.mentalrecordapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.SmsFailed
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.data.DefaultMood
import jp.example.mentalrecordapplication.ui.common.BottomNavBarView
import jp.example.mentalrecordapplication.ui.common.OkOnlyAlertDialogExample
import jp.example.mentalrecordapplication.ui.common.TopBarView
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.viewmodel.RecordMoodViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordMoodView(
    viewModel: RecordMoodViewModel = hiltViewModel(),
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var timeOfDayState by rememberSaveable { mutableStateOf(listOf(false, false, false)) }
    val datePickerState = rememberDatePickerState()
    var showMemoSheet by rememberSaveable { mutableStateOf(false) }
    val enteredMemo = viewModel.enteredMemo
    val saveResult by viewModel.saveResult.observeAsState(null)

    val defaultMoodList = listOf(
        DefaultMood.HAPPY,
        DefaultMood.ANGER,
        DefaultMood.SAD,
        DefaultMood.FUN,
        DefaultMood.Normal
    )

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
            MoodSection(defaultMoodList, onMoodClick = { viewModel.updateMood(it) })
            AddMoodSection()
            InputSections(
                showDatePicker = showDatePicker,
                timeOfDayList = timeOfDayList,
                timeOfDayState = timeOfDayState,
                datePickerState = datePickerState,
                showMemoSheet = showMemoSheet,
                enteredMemo = enteredMemo,
                timeOfDaySelectedIndex = {
                    timeOfDayState = List(timeOfDayState.size) { i -> i == it }
                    viewModel.updateTimeOfDate(timeOfDayList[it])
                },
                onShowDatePicker = { showDatePicker = true },
                onDateSelected = {
                    viewModel.updateDate(it)
                    showDatePicker = false
                },
                onShowMemoSheet = {
                    showMemoSheet = it
                },
                onChangeMemoTextField = {
                    viewModel.updateMemo(it)
                },
                onSaveClick = {
                    viewModel.saveMoodDetail()
                }
            )

            if (saveResult != null) {
                var title = ""
                var msg = ""
                var icon = Icons.Default.SmsFailed
                when (saveResult) {
                    0 -> {
                        title = stringResource(id = R.string.success_save_dialog_title)
                        msg = stringResource(id = R.string.success_save_dialog_msg)
                        icon = Icons.Default.Check
                    }
                    1 -> {
                        title = stringResource(id = R.string.null_mood_dialog_title)
                        msg = stringResource(id = R.string.null_mood_dialog_msg)
                    }
                    2 -> {
                        title = stringResource(id = R.string.null_time_zone_dialog_title)
                        msg = stringResource(id = R.string.null_time_zone_dialog_msg)
                    }
                    3 -> {
                        title = stringResource(id = R.string.null_date_dialog_title)
                        msg = stringResource(id = R.string.null_date_dialog_msg)
                    }
                    -1 -> {
                        title = stringResource(id = R.string.failure_save_dialog_title)
                        msg = stringResource(id = R.string.failure_save_dialog_msg)
                    }
                }

                OkOnlyAlertDialogExample(
                    onDismissRequest = { viewModel.resetResult() },
                    onConfirmation = { viewModel.resetResult() },
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
private fun MoodSection(defaultMoodList: List<DefaultMood>, onMoodClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        defaultMoodList.forEach {
            item {
                val moodName = stringResource(id = it.getName())
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { onMoodClick(moodName) }
                ) {
                    Icon(
                        painter = painterResource(id = it.getIcon()),
                        contentDescription = "Mood Icon",
                        tint = colorResource(id = it.getColor())
                    )
                    Text(
                        text = moodName
                    )
                }
            }
        }
    }
}

@Composable
private fun AddMoodSection() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(
            onClick = { print("") },
            shape = CircleShape,
            modifier = Modifier
                .size(100.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add icon.",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = stringResource(id = R.string.add_custom_mood),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputSections(
    showDatePicker: Boolean,
    timeOfDayList: List<String>,
    timeOfDayState: List<Boolean>,
    datePickerState: DatePickerState,
    showMemoSheet: Boolean,
    enteredMemo: String,
    timeOfDaySelectedIndex: (Int) -> Unit,
    onShowDatePicker: () -> Unit,
    onDateSelected: (String) -> Unit,
    onShowMemoSheet: (Boolean) -> Unit,
    onChangeMemoTextField: (String) -> Unit,
    onSaveClick: () -> Unit
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
            TimeOfDayChip(
                timeOfDayList,
                timeOfDayState,
                timeOfDaySelectedIndex = { timeOfDaySelectedIndex(it) },
                onItemSelected = { print(it) }
            )

            ReadOnlyDatePickerDialog(
                showDatePicker = showDatePicker,
                datePickerState = datePickerState,
                onTextFieldClick = onShowDatePicker,
                onDateSelected = {
                    onDateSelected(it)
                }
            )
            
            MemoTextFieldSheet(
                enteredMemo = enteredMemo,
                showSheet = showMemoSheet,
                onTextFieldClick = { onShowMemoSheet(true) },
                onDismissRequest = { onShowMemoSheet(false) },
                onChangeTextField = { onChangeMemoTextField(it) }
            )

            RecordSaveButton(onSaveClick = onSaveClick)
        }
    }
}

@Composable
private fun TimeOfDayChip(
    timeOfDayList: List<String>,
    timeOfDayState: List<Boolean>,
    timeOfDaySelectedIndex: (Int) -> Unit,
    onItemSelected: (String) -> Unit
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
    ) {
        timeOfDayList.forEachIndexed { index, item ->
            val isSelected = timeOfDayState[index]
            item {
                AssistChip(
                    onClick = {
                        timeOfDaySelectedIndex(index)
                        onItemSelected(item)
                    },
                    label = { Text(text = item) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    ),
                    leadingIcon = {
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
                    }
                )
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
        convertMillisToDate(it)
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

@Composable
private fun RecordSaveButton(onSaveClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
        onClick = onSaveClick
    ) {
        Text(text = stringResource(id = R.string.save_button_text))
    }
}

private fun convertMillisToDate(millis: Long): String {
    // yyyy/MM/ddのフォーマットに変換
    val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return formatter.format(Date(millis))
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