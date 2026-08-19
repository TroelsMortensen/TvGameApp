# ColorZone

An Android TV game app inspired by the YouTube series *Tapete do Movimento*. Built with Kotlin and Jetpack Compose for TV.

## Screens

### 1. Settings Screen
Shown on launch. The user configures the game before starting:
- **Colour palette** — a grid of user-defined colours. Colours can be added via a colour picker and deleted via a delete-mode toggle. Colours are persisted across sessions.
- **Colour selection** — tap colours to select/deselect which ones are used in the game.
- **Hands & feet icons** — select which body-part icons appear during gameplay.
- **Duration** — choose the game timer duration in seconds.
- **Start button** — launches the game. Disabled when no colours are selected.

### 2. Colour Picker Screen
Accessed from the "+" tile on the settings screen. Allows the user to pick a custom colour (HSV-based) and add it to the palette.

### 3. Game Screen
The active gameplay view. Displays randomised colour/icon prompts with a countdown timer. The user exits back to settings when the game ends or is manually exited.

## Backlog

### Persist recent selections
Persist the most recent selection of colours, duration, and hand/feet icons so they are restored on next launch.
