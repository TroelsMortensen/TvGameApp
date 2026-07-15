package pastimegames.colorzone.settings

import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.DurationSeconds
import pastimegames.colorzone.settings.model.GameColor

object SettingsDefaults {
    val initial = SettingsUiState(
        selectedColors = setOf(
            GameColor.Blue,
            GameColor.PaleGreen,
            GameColor.Red,
            GameColor.PaleYellow,
        ),
        selectedIcons = emptySet(),
        selectedDuration = DurationSeconds.Seven,
    )
}
