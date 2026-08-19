package pastimegames.colorzone.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.GameColor

@Composable
fun ColoredCircle(
    size: Dp,
    color: GameColor,
    icon: BodyIcon?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color.composeColor),
        contentAlignment = Alignment.Center,
    ) {
        if (icon != null) {
            Image(
                painter = painterResource(icon.drawableRes),
                contentDescription = icon.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(size * 0.15f),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
