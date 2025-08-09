package jp.example.mentalrecordapplication.ui.recordlist.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.common.TimeOfDayButtonGroup
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.types.TimeOfDayType

@Composable
fun FilterSortSheetComponents(
    isNewestFirst: Boolean,
    selectedTimeOfDay: TimeOfDayType?,
    onToggleSortOrder: () -> Unit,
    onTimeOfDaySelected: (TimeOfDayType) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.filter),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.secondary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.newest_first)
                )
            }

            Spacer(Modifier.weight(1f))

            Switch(
                checked = isNewestFirst,
                onCheckedChange = { onToggleSortOrder() }
            )
        }

        TimeOfDayButtonGroup(
            selectedTimeOfDay = selectedTimeOfDay,
            onItemSelected = onTimeOfDaySelected
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FilterSortDialogPreview() {
    MentalRecordAppTheme {
        Surface {
            FilterSortSheetComponents(
                isNewestFirst = true,
                selectedTimeOfDay = null,
                onToggleSortOrder = {},
                onTimeOfDaySelected = {}
            )
        }
    }
}