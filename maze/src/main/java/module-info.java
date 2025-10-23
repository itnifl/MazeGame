module main.game.maze {
    requires main.game.maze.opponents;
    //requires main.game.maze.runtime.difficulty.DifficultyRuntime;
    // EMF generated types from your difficulty-module
    //requires main.game.maze.difficulty.Difficulty;
    //requires main.game.maze.difficulty.DifficultyGameData;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.media;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.ecore.xmi;
    requires org.eclipse.emf.common;
    requires java.logging;
    requires java.desktop;

    opens main.game.maze to javafx.fxml;
    exports main.game.maze;
    exports main.game.maze.dto;
}