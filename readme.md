# Instructions
- Reach the heart with the lowest possible moves and highest possible character life left.
- Your score will lower for each move you make and the more life you loose.
- You will get extra 4 000 in score for reaching the heart.
- You will loose 4 000 in score for dying.
- Hit the H key to show high scores.
- You will get the chance to save your scores when you die or win.
- try the game a few times to get to know it.

# Bugs
- It happens that you can trick walls
- Ghosts are supposed to pulsate in opacity, but this seems to not work.
- Ghosts are not designed to walk through walls, but because they are ghosts I have let this bug become a feature.
- If you run several runs at the same time of this game, then the media files in the target directory (target\classes\main\game\maze) will corrupt. They then must be deleted at target\classes\main\game\maze and copied again from resources\main\game\maze to target\classes\main\game\maze.
- Application system is not very testable, unit tests should have been written first
- The Action Screens (Win and Game Over) some times bug up or don't show after I added the flash effect on the player when the player gets hurt. This is rare.

# Missing implementations
- More music and game sounds
- Animations must be created (DieAction / HappyAction)
- More Different characters and more levels
- Read a maze from SVG for player to play on
- Automatically create a maze by random hitting a button.
- Implement a ghost factory that outputs a n numer of ghosts depending of level hardness
- Highscore screen, Win screen and game over screens need better design.
- Generally more design
- A Menu with instructions and setup
- GameLogic must be separated in GameController
- Score is kept in base class CharacterActionScreens, this should be refactored. 
- Implement proper algorithms for gameplay and movement.
- We need a logger instead of System.out.println

# Sources:
- Background music, western game soundtracks: https://www.youtube.com/watch?v=ccvpPJv9J3E
- Player Scream sounds: https://www.youtube.com/watch?v=3rlV-whFgXQ
- Game over sounds: https://www.youtube.com/watch?v=bug1b0fQS8Y
- Win game music: https://www.youtube.com/watch?v=tEFU-oqSNjE
- Vector math: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
- Images used in game: https://opengameart.org/

# How to install prerequisites and run this game.
The code is only tested for running from Visual Studio Code. Download it!

Visual Studio Code is free and can be found here: https://code.visualstudio.com/download
Extensions are found and installed in the "Extensions" submenu, download and imstall the ones mentioned below.

This game is developed in Visual Studio Code with the following extensions:
- ⬇️ "Extension Pack for Java" 
- ⬇️ "Maven for Java" 
- ⬇️ "Debugger for java"
- ⬇️ "Test runner for Java"

Download Java 24 SDK to run the game:
- ⬇️ https://www.oracle.com/java/technologies/downloads/#java24


Download Java FX 21 SDK was used during the development of this game and it is needed to run it:
- ⬇️ https://gluonhq.com/products/javafx/

Remember set JAVA_HOME, PATH_TO_FX and PATH environment variables, for instance:
- 🛠️ JAVA_HOME=C:\Program Files\Java\jdk-24
- 🛠️ PATH_TO_FX=C:\Program Files\Java\javafx-sdk-21
- 🛠️ PATH+=C:\Program Files\Java\jdk-24\bin

``Then:
***⚡ Finally: In Visual Studio Code select the App.java file and run it.***``
