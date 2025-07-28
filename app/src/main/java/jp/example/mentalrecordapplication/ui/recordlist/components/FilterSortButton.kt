package jp.example.mentalrecordapplication.ui.recordlist.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@Composable
fun FilterSortButton(onTapped: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onTapped) {
            Icon(
                Icons.Default.FilterList,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "Sort and filter button"
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FilterSortButtonPreview() {
    MentalRecordAppTheme {
        FilterSortButton(onTapped = {})
    }
}