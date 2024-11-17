package jp.example.mentalrecordapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavHostController
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.data.DefaultMood
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodRecordView(
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val defaultMoodList = listOf(
        DefaultMood.HAPPY,
        DefaultMood.ANGER,
        DefaultMood.SAD,
        DefaultMood.FUN,
        DefaultMood.Normal
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
                .verticalScroll(rememberScrollState()), // 縦スクロールを可能に
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            SupportMessageSection()
            MoodSection(defaultMoodList)
            AddMoodSection()
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
                    ReadOnlyDatePickerDialog(
                        showDatePicker = showDatePicker,
                        datePickerState = datePickerState,
                        onTextFieldClick = { showDatePicker = true },
                        onDateSelected = {
                            showDatePicker = false
                            val newDate = it
                        }
                    )

                    RecordSaveButton()
                }
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
private fun MoodSection(defaultMoodList: List<DefaultMood>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        defaultMoodList.forEach {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = it.getIcon()),
                        contentDescription = "Mood Icon",
                        tint = colorResource(id = it.getColor())
                    )
                    Text(
                        text = stringResource(id = it.getName())
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
                .height(64.dp)
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

@Composable
private fun RecordSaveButton() {
    OutlinedButton(onClick = {  }) {
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
        MoodRecordView(
            navController = null,
            screenTitle = stringResource(id = R.string.mood_record_screen_title),
            selectedTab = 0,
            onSelectedTab = {}
        )
    }
}