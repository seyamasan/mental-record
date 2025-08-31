package jp.example.mentalrecordapplication.ui.common.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediumIconElevatedButton(
    icon: ImageVector,
    text: String,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ),
    onTap: () -> Unit
) {
    val size = ButtonDefaults.MediumContainerHeight
    ElevatedButton(
        modifier = Modifier.heightIn(size),
        colors = colors,
        contentPadding = ButtonDefaults.contentPaddingFor(size),
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
        onClick = onTap
    ) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = "Medium Icon ElevatedButton"
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = text, style = ButtonDefaults.textStyleFor(size))
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MediumIconElevatedButtonPreview() {
    MentalRecordAppTheme {
        MediumIconElevatedButton(
            icon = Icons.Default.Add,
            text = "Button",
            onTap = {}
        )
    }
}