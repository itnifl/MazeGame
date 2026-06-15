package main.game.maze.runtime.opponents;

import javafx.scene.Node;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.Point2D;

/**
 * Neutral callback interface used by {@link OpponentRuntimeFactory} to register
 * spawned enemies without a direct dependency on {@code GameController}.
 *
 * <p>This interface is the ISP boundary that allows {@link main.game.maze.javafx.lifecycle.FxGameSessionBootstrapper}
 * to trigger opponent instantiation while keeping the factory decoupled from
 * the FXML coordinator.</p>
 */
public interface EnemyRegistrar {

    void registerComputerCharacter(IMovingComputerCharacter character, Node node);

    Point2D resolveEnemySpawnPosition(double desiredX, double desiredY, double enemySize);
}
