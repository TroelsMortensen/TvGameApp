I am building an app for android tv. This workspace contains the initial project for the app. I think the only current code exists in the MainActivity.kt file. There is a tiny foundation of code in the app. This may be used. Or you may decide to start from scratch.

It is based on a series of youtube videos called Tapete do Movimento.

Programming language is kotlin.

The app will consist of two views.

1) Settings view, shown when the app is launched.
   1) There is a grid of colored squares, included colors:
      1) blue
      2) pale green
      3) red
      4) pale yellow
      5) lavender
      6) orange
      7) pink
   2) There are also squares with hands or feet icons. Icons are found in ./Icons directory.
      1) Right hand
      2) Left hand 
      3) Right foot
      4) Left foot
      5) Both hands
      6) Both feet
   3) There is are four squares with numbers for seconds: 5, 7, 10, 15.
   4) The user can navigate the grid using the remote control.
   5) The user can select a square by pressing the remote control's enter button.
   6) When a square is selected, it will be clear that it is selected, e.g. with a checkmark at the top right cornor of the square.
   7) Multiple squares can be selected 
   8) There is a button at the top to start the game. The game will start when the user presses the button.
   9) By default, no hands or feet icons are selected.
   10) By default, the following colors are selected: blue, pale green, red, pale yellow
   11) By default, the following seconds are selected: 7
   12) Only one kind of number square can be selected at a time. If a number square is already selected, and the user selects another number square, the first number square will be deselected.
   13) Exactly one number square must be selected, otherwise the start button will be disabled.
   14) At least one color square must be selected, otherwise the start button will be disabled.
   15) Zero or more hands or feet icons can be selected, this will not affect the start button.
   16) The user can select or deselect colors, and hands or feet icons, and number squares.
2)  The Game view, shown when the user presses the start button.
   1) The game view will show a single circle on a black background. The circle fill 80% of the screen hight.
   2) At intervals of the selected number on the previous screen, the circle will be colored by selecting a random color from the selected colors. A color cannot be selected more than once in a row.
   3) Whenever the circle color changes, there is a 50% chance that the circle will be filled with a hand or foot icon, if any was selected on the previous screen.
   4) The shown hand or foot icon is selected at random from the selected icons on the previous screen. The same icon can be shown more than once in a row, there is no limit here.
   5) This loop will continue until the player presses the remote control's back button, which will return to the main view.
      