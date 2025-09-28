module main.game.maze {
    requires main.game.maze.opponents;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.media;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.ecore.xmi;
    requires org.eclipse.emf.common;
    requires java.logging;

    opens main.game.maze to javafx.fxml;
    exports main.game.maze;
    exports main.game.maze.dto;
}