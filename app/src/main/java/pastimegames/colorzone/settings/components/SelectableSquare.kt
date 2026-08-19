package pastimegames.colorzone.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SelectableSquare(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 130.dp,
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)
    var isFocused by remember { mutableStateOf(false) }

    val focusBorderModifier = if (isFocused) {
        Modifier.border(
            width = 3.dp,
            brush = Brush.linearGradient(
                colors = listOf(Color.White, Color.Black),
                start = Offset.Zero,
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
            ),
            shape = shape,
        )
    } else {
        Modifier
    }

    Surface(
        onClick = onClick,
        modifier = modifier
            .size(size)
            .onFocusChanged { isFocused = it.isFocused },
        shape = ClickableSurfaceDefaults.shape(shape),
        colors = ClickableSurfaceDefaults.colors(
            containerColor = backgroundColor,
            focusedContainerColor = backgroundColor,
        ),
        scale = ClickableSurfaceDefaults.scale(focusedScale = 1.15f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(backgroundColor)
                .then(focusBorderModifier),
            contentAlignment = Alignment.Center,
        ) {
            content()

            if (selected) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp),
                )
            }
        }
    }
}

@Composable
fun SettingsGridRow(
    modifier: Modifier = Modifier,
    spacing: Dp = 16.dp,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}
