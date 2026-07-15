package pastimegames.colorzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.tv.material3.Text
import androidx.tv.material3.ExperimentalTvMaterial3Api
import pastimegames.colorzone.settings.SettingsScreen
import pastimegames.colorzone.ui.theme.ColorZoneTheme

@OptIn(ExperimentalTvMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorZoneTheme {
                var isPlaying by remember { mutableStateOf(false) }

                if (isPlaying) {
                    GameScreen(onExit = { isPlaying = false })
                } else {
                    SettingsScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun GameScreen(onExit: () -> Unit) {
    Text("Game: Random colors showing here")
}
