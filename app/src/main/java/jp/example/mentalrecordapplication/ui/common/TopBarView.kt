package jp.example.mentalrecordapplication.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import jp.example.mentalrecordapplication.utils.OkHttpUtil

/*
* CommonTopAppBar（共通のトップバー）
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(title: String) {
    val context = LocalContext.current
    val url = stringResource(id = R.string.privacy_policy_url)

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    OkHttpUtil.openWebPage(context, url)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info icon",
                    tint = MaterialTheme.colorScheme.primary // アイコンの色
                )
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarViewPreview() {
    MentalRecordAppTheme {
        TopBarView(title = "Test View")
    }
}