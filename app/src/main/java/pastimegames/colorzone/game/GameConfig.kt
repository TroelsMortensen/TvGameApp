package pastimegames.colorzone.game

import pastimegames.colorzone.settings.SettingsUiState
import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.GameColor

data class GameConfig(
    val colors: Set<GameColor>,
    val icons: Set<BodyIcon>,
    val durationSeconds: Int,
) {
    companion object {
        fun from(settings: SettingsUiState) = GameConfig(
            colors = settings.selectedColors,
            icons = settings.selectedIcons,
            durationSeconds = settings.selectedDuration.seconds,
        )
    }
}
