package main.game.maze.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public final class Dialogs {
    private Dialogs() {}

    public static void showError(String title, String header, String message, Throwable ex) {
        Runnable ui = () -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(message);

            if (ex != null) {
                String stack = getStackTrace(ex);

                TextArea area = new TextArea(stack);
                area.setEditable(false);
                area.setWrapText(true);
                area.setMaxWidth(Double.MAX_VALUE);
                area.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(area, Priority.ALWAYS);
                GridPane.setHgrow(area, Priority.ALWAYS);

                GridPane content = new GridPane();
                content.setMaxWidth(Double.MAX_VALUE);
                content.add(area, 0, 0);

                alert.getDialogPane().setExpandableContent(content);
                alert.getDialogPane().setExpanded(false); // collapsed by default
            }
            alert.showAndWait();
        };

        if (Platform.isFxApplicationThread()) ui.run();
        else Platform.runLater(ui);
    }

    private static String getStackTrace(Throwable t) {
        java.io.StringWriter sw = new java.io.StringWriter();
        t.printStackTrace(new java.io.PrintWriter(sw));
        return sw.toString();
    }
}
