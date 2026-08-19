package pastimegames.colorzone.settings.model

import androidx.compose.ui.graphics.Color

@JvmInline
value class GameColor(val rgb: Int) {
    val composeColor: Color
        get() = Color(rgb or 0xFF000000.toInt())

    fun toHex(): String = "%06X".format(rgb and 0xFFFFFF)

    companion object {
        fun fromHex(hex: String): GameColor? {
            val parsed = hex.trim().removePrefix("#").toIntOrNull(16) ?: return null
            return GameColor(parsed and 0xFFFFFF)
        }
    }
}
