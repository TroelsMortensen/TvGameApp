package pastimegames.colorzone.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import pastimegames.colorzone.settings.components.SelectableSquare
import pastimegames.colorzone.settings.components.SettingsGridRow
import pastimegames.colorzone.settings.components.StartGameButton
import pastimegames.colorzone.settings.model.BodyIcon
import pastimegames.colorzone.settings.model.DurationSeconds
import pastimegames.colorzone.settings.model.GameColor

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onStateChange: (SettingsUiState) -> Unit,
    onAddColor: () -> Unit,
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorCells: List<GameColor?> = state.palette + listOf(null)
    val colorRows = colorCells.chunked(4)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
    ) {
        item {
            StartGameButton(
                enabled = state.canStart,
                onClick = onStartGame,
            )
        }

        item {
            SettingsSectionTitle(text = "Colors")
        }

        colorRows.forEachIndexed { index, rowColors ->
            item {
                SettingsGridRow(
                    modifier = Modifier.padding(bottom = if (index == colorRows.lastIndex) 24.dp else 16.dp),
                ) {
                    rowColors.forEach { color ->
                        if (color == null) {
                            AddColorSquare(onClick = onAddColor)
                        } else {
                            ColorSquare(
                                color = color,
                                selected = color in state.selectedColors,
                                onClick = { onStateChange(state.toggleColor(color)) },
                            )
                        }
                    }
                }
            }
        }

        item {
            SettingsSectionTitle(text = "Hands & Feet")
        }

        item {
            SettingsGridRow(modifier = Modifier.padding(bottom = 16.dp)) {
                BodyIcon.entries.take(3).forEach { icon ->
                    IconSquare(
                        icon = icon,
                        selected = icon in state.selectedIcons,
                        onClick = { onStateChange(state.toggleIcon(icon)) },
                    )
                }
            }
        }

        item {
            SettingsGridRow(modifier = Modifier.padding(bottom = 24.dp)) {
                BodyIcon.entries.drop(3).forEach { icon ->
                    IconSquare(
                        icon = icon,
                        selected = icon in state.selectedIcons,
                        onClick = { onStateChange(state.toggleIcon(icon)) },
                    )
                }
            }
        }

        item {
            SettingsSectionTitle(text = "Seconds")
        }

        item {
            SettingsGridRow(modifier = Modifier.padding(bottom = 24.dp)) {
                DurationSeconds.entries.forEach { duration ->
                    DurationSquare(
                        duration = duration,
                        selected = state.selectedDuration == duration,
                        onClick = { onStateChange(state.selectDuration(duration)) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun SettingsSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
    )
}

@Composable
private fun ColorSquare(
    color: GameColor,
    selected: Boolean,
    onClick: () -> Unit,
) {
    SelectableSquare(
        selected = selected,
        onClick = onClick,
        backgroundColor = color.composeColor,
    ) {}
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun AddColorSquare(onClick: () -> Unit) {
    SelectableSquare(
        selected = false,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            text = "+",
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun IconSquare(
    icon: BodyIcon,
    selected: Boolean,
    onClick: () -> Unit,
) {
    SelectableSquare(
        selected = selected,
        onClick = onClick,
        backgroundColor = Color.White,
    ) {
        Image(
            painter = painterResource(icon.drawableRes),
            contentDescription = icon.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentScale = ContentScale.Fit,
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun DurationSquare(
    duration: DurationSeconds,
    selected: Boolean,
    onClick: () -> Unit,
) {
    SelectableSquare(
        selected = selected,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            text = duration.seconds.toString(),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
