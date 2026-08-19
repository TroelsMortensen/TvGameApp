package pastimegames.colorzone.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pastimegames.colorzone.game.components.ColoredCircle
import pastimegames.colorzone.game.components.CountdownRing

@Composable
fun GameScreen(
    config: GameConfig,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember(config) { mutableStateOf(GameRandomizer.initialState(config)) }

    BackHandler(onBack = onExit)

    LaunchedEffect(config) {
        state = GameRandomizer.initialState(config)
        while (true) {
            delay(1_000)
            val ticked = GameRandomizer.tick(state)
            state = if (ticked.secondsRemaining <= 0) {
                GameRandomizer.nextState(config, state)
            } else {
                ticked
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        BoxWithConstraints(contentAlignment = Alignment.Center) {
            val circleDiameter = maxHeight * 0.7f
            val ringDiameter = circleDiameter * 1.15f

            CountdownRing(
                size = ringDiameter,
                sectorCount = config.durationSeconds,
                visibleCount = state.secondsRemaining,
                color = state.currentColor.composeColor,
            )

            ColoredCircle(
                size = circleDiameter,
                color = state.currentColor,
                icon = state.currentIcon,
            )
        }
    }
}
