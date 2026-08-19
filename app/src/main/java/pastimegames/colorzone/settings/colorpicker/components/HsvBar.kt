package pastimegames.colorzone.settings.colorpicker.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.key
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun HsvBar(
    label: String,
    valueLabel: String,
    valueFraction: Float,
    gradientColors: List<Color>,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
) {
    var hasFocus by remember { mutableStateOf(false) }
    val thumbColor = if (hasFocus) Color.White else Color.White.copy(alpha = 0.5f)

    Box(
        modifier = modifier
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
            .onFocusChanged { hasFocus = it.hasFocus }
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyDown) {
                    return@onKeyEvent false
                }
                when (event.key) {
                    Key.DirectionUp -> {
                        onDecrease()
                        true
                    }

                    Key.DirectionDown -> {
                        onIncrease()
                        true
                    }

                    else -> false
                }
            }
            .focusable(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = label,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
            )
            Box(
                modifier = Modifier
                    .width(72.dp)
                    .height(320.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF161616))
                    .border(
                        width = if (hasFocus) 6.dp else 2.dp,
                        color = if (hasFocus) Color.White else Color(0xFF222222),
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(6.dp),
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    drawRect(
                        brush = Brush.verticalGradient(gradientColors),
                    )

                    val clamped = valueFraction.coerceIn(0f, 1f)
                    val y = size.height * clamped
                    drawLine(
                        color = thumbColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = if (hasFocus) 9f else 6f,
                    )
                }
            }
            Text(
                text = valueLabel,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
