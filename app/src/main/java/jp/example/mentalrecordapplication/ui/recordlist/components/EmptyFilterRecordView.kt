package jp.example.mentalrecordapplication.ui.recordlist.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.common.button.MediumIconElevatedButton
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@Composable
fun EmptyFilterRecordView(
    onFilterSortButtonTap: () -> Unit,
    onResetFilterButtonTap: () -> Unit
) {
    FilterSortButton(
        onTapped = onFilterSortButtonTap
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty_filter_record_msg),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        MediumIconElevatedButton(
            icon = Icons.Default.FilterListOff,
            text = stringResource(id = R.string.reset_filter),
            onTap = onResetFilterButtonTap
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun EmptyFilterRecordViewPreview() {
    MentalRecordAppTheme {
        Surface {
            EmptyFilterRecordView(
                onFilterSortButtonTap = {},
                onResetFilterButtonTap = {}
            )
        }
    }
}