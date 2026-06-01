package main.game.maze;

/**
 * Thin launcher shim required to start the JavaFX application from the
 * maze assembly module. JavaFX mandates that the class containing
 * {@code main()} is not a subclass of {@code javafx.application.Application}
 * when the app is run from a fat-jar or unnamed module. This class delegates
 * straight to {@link App#launch}.
 */
public final class Launcher {
    private Launcher() {}

    public static void main(String[] args) {
        App.launch(App.class, args);
    }
}
