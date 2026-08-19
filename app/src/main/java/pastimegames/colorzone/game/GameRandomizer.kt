package pastimegames.colorzone.game

import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.GameColor
import kotlin.random.Random

object GameRandomizer {

    fun initialState(config: GameConfig): GameState {
        val color = pickColor(config.colors, exclude = null)
        return GameState(
            currentColor = color,
            currentIcon = null,
            secondsRemaining = config.durationSeconds,
        )
    }

    fun nextState(config: GameConfig, previous: GameState): GameState {
        val color = pickColor(config.colors, exclude = previous.currentColor)
        return GameState(
            currentColor = color,
            currentIcon = pickIcon(config.icons),
            secondsRemaining = config.durationSeconds,
        )
    }

    fun tick(previous: GameState): GameState {
        return previous.copy(secondsRemaining = previous.secondsRemaining - 1)
    }

    private fun pickColor(colors: Set<GameColor>, exclude: GameColor?): GameColor {
        val candidates = if (exclude != null && colors.size > 1) {
            colors.filter { it != exclude }
        } else {
            colors.toList()
        }
        return candidates.random()
    }

    private fun pickIcon(icons: Set<BodyIcon>): BodyIcon? {
        if (icons.isEmpty() || !Random.nextBoolean()) {
            return null
        }
        return icons.random()
    }
}
