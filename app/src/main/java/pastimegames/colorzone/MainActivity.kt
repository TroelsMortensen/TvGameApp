package pastimegames.colorzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import pastimegames.colorzone.game.GameConfig
import pastimegames.colorzone.game.GameScreen
import pastimegames.colorzone.settings.SettingsDefaults
import pastimegames.colorzone.settings.SettingsScreen
import pastimegames.colorzone.settings.colorpicker.ColorPickerScreen
import pastimegames.colorzone.settings.data.ColorPaletteStore
import pastimegames.colorzone.ui.theme.ColorZoneTheme

@OptIn(ExperimentalTvMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorZoneTheme {
                val paletteStore = remember { ColorPaletteStore(applicationContext) }
                var settingsState by remember {
                    mutableStateOf(SettingsDefaults.initial(paletteStore.load()))
                }
                var screen by remember { mutableStateOf<Screen>(Screen.Settings) }

                when (val current = screen) {
                    is Screen.Settings -> {
                        SettingsScreen(
                            state = settingsState,
                            onStateChange = { settingsState = it },
                            onAddColor = { screen = Screen.ColorPicker },
                            onDeleteColor = { color ->
                                settingsState = settingsState.deleteColor(color)
                                paletteStore.save(settingsState.palette)
                            },
                            onStartGame = { screen = Screen.Game(GameConfig.from(settingsState)) },
                        )
                    }

                    is Screen.ColorPicker -> {
                        ColorPickerScreen(
                            onAddColor = { color ->
                                settingsState = settingsState.addColor(color)
                                paletteStore.save(settingsState.palette)
                                screen = Screen.Settings
                            },
                            onBack = { screen = Screen.Settings },
                        )
                    }

                    is Screen.Game -> {
                        GameScreen(
                            config = current.config,
                            onExit = { screen = Screen.Settings },
                        )
                    }
                }
            }
        }
    }
}

private sealed interface Screen {
    data object Settings : Screen
    data object ColorPicker : Screen
    data class Game(val config: GameConfig) : Screen
}
