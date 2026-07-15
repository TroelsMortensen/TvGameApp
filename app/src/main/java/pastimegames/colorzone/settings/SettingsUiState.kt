package pastimegames.colorzone.settings

import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.DurationSeconds
import pastimegames.colorzone.settings.model.GameColor

data class SettingsUiState(
    val selectedColors: Set<GameColor>,
    val selectedIcons: Set<BodyIcon>,
    val selectedDuration: DurationSeconds,
) {
    val canStart: Boolean
        get() = selectedColors.isNotEmpty()

    fun toggleColor(color: GameColor): SettingsUiState {
        val updated = selectedColors.toMutableSet()
        if (color in updated) {
            updated.remove(color)
        } else {
            updated.add(color)
        }
        return copy(selectedColors = updated)
    }

    fun toggleIcon(icon: BodyIcon): SettingsUiState {
        val updated = selectedIcons.toMutableSet()
        if (icon in updated) {
            updated.remove(icon)
        } else {
            updated.add(icon)
        }
        return copy(selectedIcons = updated)
    }

    fun selectDuration(duration: DurationSeconds): SettingsUiState {
        return copy(selectedDuration = duration)
    }
}
