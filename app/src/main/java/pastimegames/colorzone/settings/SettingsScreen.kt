package pastimegames.colorzone.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import pastimegames.colorzone.R
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
    onDeleteColor: (GameColor) -> Unit,
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var deleteMode by remember { mutableStateOf(false) }

    LaunchedEffect(state.palette) {
        if (state.palette.isEmpty()) deleteMode = false
    }

    val colorCells: List<Any?> = state.palette + listOf("add", "delete")
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
                    rowColors.forEach { cell ->
                        when (cell) {
                            "add" -> AddColorSquare(onClick = onAddColor)
                            "delete" -> DeleteModeSquare(
                                active = deleteMode,
                                onClick = { deleteMode = !deleteMode },
                            )
                            is GameColor -> ColorSquare(
                                color = cell,
                                selected = cell in state.selectedColors,
                                deleteMode = deleteMode,
                                onClick = { onStateChange(state.toggleColor(cell)) },
                                onDelete = { onDeleteColor(cell) },
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
    deleteMode: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    SelectableSquare(
        selected = selected,
        onClick = if (deleteMode) onDelete else onClick,
        backgroundColor = color.composeColor,
    ) {
        if (deleteMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.garbage_can),
                    contentDescription = "Delete",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun DeleteModeSquare(active: Boolean, onClick: () -> Unit) {
    SelectableSquare(
        selected = false,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.garbage_can),
                contentDescription = "Delete mode",
                modifier = Modifier.weight(1f).fillMaxWidth().padding(4.dp),
                contentScale = ContentScale.Fit,
            )
            Text(
                text = if (active) "DONE" else "DELETE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
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
