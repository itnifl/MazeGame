module main.game.maze {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.media;

    opens main.game.maze to javafx.fxml;

    exports main.game.maze;
    exports main.game.maze.dto;
}
