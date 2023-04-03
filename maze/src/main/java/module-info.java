module main.game.maze {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens main.game.maze to javafx.fxml;
    exports main.game.maze;
}
