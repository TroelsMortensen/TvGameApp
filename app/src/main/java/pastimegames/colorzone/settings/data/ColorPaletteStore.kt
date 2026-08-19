package pastimegames.colorzone.settings.data

import android.content.Context
import pastimegames.colorzone.settings.SettingsDefaults
import pastimegames.colorzone.settings.model.GameColor

class ColorPaletteStore(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): List<GameColor> {
        val raw = preferences.getString(KEY_PALETTE, null) ?: return SettingsDefaults.defaultPalette
        val parsed = raw
            .split(",")
            .mapNotNull { GameColor.fromHex(it) }
            .distinct()
        return parsed.ifEmpty { SettingsDefaults.defaultPalette }
    }

    fun save(palette: List<GameColor>) {
        val serialized = palette
            .distinct()
            .joinToString(",") { it.toHex() }
        preferences.edit().putString(KEY_PALETTE, serialized).apply()
    }

    companion object {
        private const val PREFS_NAME = "colorzone"
        private const val KEY_PALETTE = "palette"
    }
}
