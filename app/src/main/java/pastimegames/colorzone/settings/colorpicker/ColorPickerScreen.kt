package pastimegames.colorzone.settings.colorpicker

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import pastimegames.colorzone.settings.model.GameColor
import pastimegames.colorzone.settings.colorpicker.components.HsvBar
import kotlin.math.roundToInt

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ColorPickerScreen(
    onAddColor: (GameColor) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var hue by remember { mutableFloatStateOf(100f) }
    var saturation by remember { mutableFloatStateOf(0.85f) }
    var value by remember { mutableFloatStateOf(0.9f) }

    val hueFocus = remember { FocusRequester() }
    BackHandler(onBack = onBack)

    LaunchedEffect(Unit) {
        hueFocus.requestFocus()
    }

    val currentColor = remember(hue, saturation, value) {
        GameColor(hsvToRgb(hue = hue, saturation = saturation, value = value))
    }

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 48.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(36.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .clip(CircleShape)
                    .background(currentColor.composeColor),
            )

            Text(
                text = "#${currentColor.toHex()}",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        HsvBar(
            label = "H",
            valueLabel = hue.roundToInt().toString(),
            valueFraction = hue / 360f,
            gradientColors = rememberHueGradient(),
            onIncrease = { hue = (hue + 2f) % 360f },
            onDecrease = { hue = (hue - 2f).let { if (it < 0f) it + 360f else it } },
            focusRequester = hueFocus,
            modifier = Modifier.weight(0.35f),
        )

        HsvBar(
            label = "S",
            valueLabel = "${(saturation * 100f).roundToInt()}%",
            valueFraction = saturation,
            gradientColors = rememberSaturationGradient(hue = hue, value = value),
            onIncrease = { saturation = (saturation + 0.02f).coerceAtMost(1f) },
            onDecrease = { saturation = (saturation - 0.02f).coerceAtLeast(0f) },
            modifier = Modifier.weight(0.35f),
        )

        HsvBar(
            label = "V",
            valueLabel = "${(value * 100f).roundToInt()}%",
            valueFraction = value,
            gradientColors = rememberValueGradient(hue = hue, saturation = saturation),
            onIncrease = { value = (value + 0.02f).coerceAtMost(1f) },
            onDecrease = { value = (value - 0.02f).coerceAtLeast(0f) },
            modifier = Modifier.weight(0.35f),
        )

        Column(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { onAddColor(currentColor) },
                colors = ButtonDefaults.colors(
                    containerColor = Color(0xFF1E5721),
                    focusedContainerColor = Color(0xFF4CAF50),
                    contentColor = Color.White,
                    focusedContentColor = Color.White,
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Add", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun hsvToRgb(hue: Float, saturation: Float, value: Float): Int {
    val argb = android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, value))
    return argb and 0x00FFFFFF
}

@Composable
private fun rememberHueGradient(sampleCount: Int = 60): List<Color> {
    return remember(sampleCount) {
        buildHsvGradient(sampleCount) { fraction ->
            floatArrayOf(fraction * 360f, 1f, 1f)
        }
    }
}

@Composable
private fun rememberSaturationGradient(hue: Float, value: Float, sampleCount: Int = 60): List<Color> {
    return remember(hue, value, sampleCount) {
        buildHsvGradient(sampleCount) { fraction ->
            floatArrayOf(hue, fraction, value)
        }
    }
}

@Composable
private fun rememberValueGradient(hue: Float, saturation: Float, sampleCount: Int = 60): List<Color> {
    return remember(hue, saturation, sampleCount) {
        buildHsvGradient(sampleCount) { fraction ->
            floatArrayOf(hue, saturation, fraction)
        }
    }
}

private fun buildHsvGradient(sampleCount: Int, hsvForFraction: (Float) -> FloatArray): List<Color> {
    val count = sampleCount.coerceAtLeast(2)
    return List(count) { index ->
        val fraction = index.toFloat() / (count - 1).toFloat()
        Color(android.graphics.Color.HSVToColor(hsvForFraction(fraction)))
    }
}
