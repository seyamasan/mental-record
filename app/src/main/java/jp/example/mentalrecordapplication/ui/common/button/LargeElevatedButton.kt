package jp.example.mentalrecordapplication.ui.common.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LargeElevatedButton(
    text: String,
    onClick: () -> Unit
) {
    val size = ButtonDefaults.LargeContainerHeight
    ElevatedButton(
        modifier = Modifier.heightIn(size),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        contentPadding = ButtonDefaults.contentPaddingFor(size),
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
        onClick = onClick
    ) {
        Text(text = text, style = ButtonDefaults.textStyleFor(size))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LargeElevatedButtonPreview() {
    MentalRecordAppTheme {
        LargeElevatedButton(
            text = "Button",
            onClick = {}
        )
    }
}