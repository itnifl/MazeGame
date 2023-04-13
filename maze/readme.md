# Instructions
- Reach the heart with the lowest possible moves and highest possible character life left.
- Hit the H key to show high scores.

# Bugs
- It happens that you can trick walls
- Ghosts are supposed to pulsate in opacity, but this seems to not work.
- Ghosts are not designed to walk through walls, but because they are ghosts I have let this bug become a feature.
- If you run several runs at the same time of this game, then the media files in the target directory (target\classes\main\game\maze) will corrupt. They then must be deleted at target\classes\main\game\maze and copied again from resources\main\game\maze to target\classes\main\game\maze.

# Missing implementations
- Music and game sounds
- Animations must be created (HappyAction / HappyAction)
- Different characters
- Read a maze from SVG for player to play on
- Automatically create a maze by random hitting a button.
- Implement a ghost factory that outputs a n numer of ghosts depending of level hardness
- Highscore screen, Win screen and game over screens need better design.

# Sources:
- Background music, western game soundtracks: https://www.youtube.com/watch?v=ccvpPJv9J3E
- Player Scream: https://www.youtube.com/watch?v=3rlV-whFgXQ
- Game over: https://www.youtube.com/watch?v=bug1b0fQS8Y
- Win game: https://www.youtube.com/watch?v=tEFU-oqSNjE
- Vector math: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

# How to install prerequisites and run this game.
This game is developed in Visual Studio Code with the following extensions:
- "Extension Pack for Java" 
- "Maven for Java" 
- "Debugger for java"
- "Test runner for Java"

Visual Studio Code is free and can be found here: https://code.visualstudio.com/download
Extensions are found and installed in the "Extensions" submenu.

Java 20 SDK was used during the development of this game and is needed to run it:
- https://www.oracle.com/java/technologies/downloads/#java20


Java FX 20 SDK was used during the development of this game and it is needed to run it:
- https://gluonhq.com/products/javafx/

Remember set JAVA_HOME, PATH_TO_FX and PATH environment variables, for instance:
JAVA_HOME=C:\Program Files\Java\jdk-20
PATH_TO_FX=C:\Program Files\Java\javafx-sdk-20
PATH+=C:\Program Files\Java\jdk-20\bin

In Visual Studio Code select the App.java file and run it.
