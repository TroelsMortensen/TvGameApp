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
import pastimegames.colorzone.settings.SettingsUiState
import pastimegames.colorzone.ui.theme.ColorZoneTheme

@OptIn(ExperimentalTvMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorZoneTheme {
                var settingsState by remember { mutableStateOf(SettingsDefaults.initial) }
                var gameConfig by remember { mutableStateOf<GameConfig?>(null) }

                if (gameConfig != null) {
                    GameScreen(
                        config = gameConfig!!,
                        onExit = { gameConfig = null },
                    )
                } else {
                    SettingsScreen(
                        state = settingsState,
                        onStateChange = { settingsState = it },
                        onStartGame = { gameConfig = GameConfig.from(settingsState) },
                    )
                }
            }
        }
    }
}
