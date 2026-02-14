package jp.example.mentalrecordapplication.ui.analysis.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import jp.example.mentalrecordapplication.ui.theme.MentalRecordAppTheme
import kotlinx.coroutines.runBlocking

@Composable
fun MentalLineGraph(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries { series(100, 0, 25, 50, 75, 50, 100, 25, 0, 100, 25, 50, 0, 100, 50, 50) }
        }
    }
    MentalLineGraph(modelProducer, modifier)
}

@Composable
private fun MentalLineGraph(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
            ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val modelProducer = remember { CartesianChartModelProducer() }

    // `runBlocking` は、非同期実行をサポートしないプレビューでのみ使用してください。
    runBlocking {
        modelProducer.runTransaction {
            lineSeries { series(100, 0, 25, 50, 75, 50, 100, 25, 0, 100, 25, 50, 0, 100, 50, 50) }
        }
    }

    MentalRecordAppTheme {
        MentalLineGraph(modelProducer)
    }
}