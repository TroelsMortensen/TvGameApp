package pastimegames.colorzone.settings

import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.DurationSeconds
import pastimegames.colorzone.settings.model.GameColor

object SettingsDefaults {
    val defaultPalette: List<GameColor> = listOf(
        GameColor(0xFF0000),
        GameColor(0x00FF00),
        GameColor(0x0000FF),
        GameColor(0xFFFF00),
    )

    fun initial(palette: List<GameColor>): SettingsUiState {
        val seedPalette = palette.ifEmpty { defaultPalette }
        return SettingsUiState(
            palette = seedPalette,
            selectedColors = seedPalette.take(4).toSet(),
            selectedIcons = emptySet(),
            selectedDuration = DurationSeconds.Seven,
        )
    }
}
