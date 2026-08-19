package pastimegames.colorzone.game

import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.GameColor

data class GameState(
    val currentColor: GameColor,
    val currentIcon: BodyIcon?,
    val secondsRemaining: Int,
)
