package jp.example.mentalrecordapplication.ui.recordmood.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.example.mentalrecordapplication.R
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AudioRecorderSheetComponents(
    isRecording: Boolean,
    elapsedTime: Int,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit
) {
    val size = ButtonDefaults.LargeContainerHeight

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 経過時間
        Text(
            text = formatElapsedTime(elapsedTime),
            style = MaterialTheme.typography.headlineSmall,
            color = if (isRecording) Color.Green else Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (isRecording) {
            WaveformAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(bottom = 24.dp)
            )
        } else {
            Text(
                text = stringResource(id = R.string.audio_record_start_hint),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // 録音ボタン
        Button(
            modifier = Modifier
                .size(size)
                .border(
                    width = 2.dp,
                    color = if (isRecording) Color.Green else Color.Gray,
                    shape = CircleShape
                ),
            onClick = {
                if (isRecording) {
                    onStopRecording()
                } else {
                    onStartRecording()
                }
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRecording) Color.Green else Color.White
            )
        ) {
            Icon(
                imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                contentDescription = null,
                tint = if (isRecording) Color.White else Color.Red,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun WaveformAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val barCount = 20

    val phases = List(barCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, delayMillis = index * 50, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar$index"
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        phases.forEach { anim ->
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight(anim.value)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.Green.copy(alpha = 0.8f))
            )
        }
    }
}

private fun formatElapsedTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AudioRecorderSheetComponentsPreview() {
    MentalRecordAppTheme {
        Surface {
            AudioRecorderSheetComponents(
                isRecording = false,
                elapsedTime = 0,
                onStartRecording = {},
                onStopRecording = {}
            )
        }
    }
}