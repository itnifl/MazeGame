module main.game.maze {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens main.game.maze to javafx.fxml;

    exports main.game.maze;
}
