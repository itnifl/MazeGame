package main.game.maze.runtime.opponents;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import main.game.maze.App;
import main.game.maze.characters.GuardTowerCharacter;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.level.DifficultyTier;
import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.game.level.LevelPlacement;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;

/**
 * Places the level's guard towers (JavaFX): as many as the difficulty says, on free corridor points in the outer
 * part of the map. A tower is an enemy model with the values of the levels model (its damage-state sprites on), so it
 * is drawn, hurt, killed and scored like every other enemy.
 */
public final class GuardTowerSpawner {

    private static final Logger LOGGER = Logger.getLogger(GuardTowerSpawner.class.getName());
    /** Side of a tower sprite on the board, in pixels: bigger than any walking enemy. */
    public static final int TOWER_SIZE = 56;
    /** Danger of a tower, for the kill points. */
    private static final double TOWER_THREAT = 12.0;

    private GuardTowerSpawner() {
    }

    /** Where this level's towers stand on this difficulty (world space, the centres). */
    public static List<Point2D> postsFor(LevelSpec level, Difficulty difficulty, GameMazeWorld world,
                                         double startX, double startY) {
        if (level == null || !level.hasTowers() || world == null) {
            return List.of();
        }
        return LevelPlacement.towerPosts(level, DifficultyTier.fromDifficultyName(
                difficulty == null ? null : difficulty.eClass().getName()), world, startX, startY);
    }

    public static int spawn(EnemyRegistrar registrar, LevelSpec level, Difficulty difficulty, GameMazeWorld world,
                            double startX, double startY) {
        if (registrar == null || level == null || !level.hasTowers() || world == null) {
            return 0;
        }
        GuardTowerSpec spec = level.guardTower();
        List<Point2D> posts = postsFor(level, difficulty, world, startX, startY);
        int index = 0;
        for (Point2D post : posts) {
            register(registrar, spec, post.getX(), post.getY(), GuardTowerSpec.SPAWN_ID_PREFIX + "_" + index++);
        }
        LOGGER.log(Level.INFO, "Placed {0} guard towers", posts.size());
        return posts.size();
    }

    /** The enemy model of a tower: the levels model's values, never moving, with its damaged looks on. */
    static Zombie modelOf(GuardTowerSpec spec, String id) {
        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setId(id);
        model.setDisplayName(spec.displayName());
        model.setHealth(spec.health());
        model.setSpeed(1.0);
        model.setThreatLevel(TOWER_THREAT);
        model.setBehavior(BehaviorType.PASSIVE);
        model.setAttackDamage(0);
        model.setInfectionLevel(0);
        model.setResurrectionTime(0);
        model.setDamageStatesEnabled(true);
        model.setImageBase(spec.sprite());
        model.setImageTurnLeft(spec.sprite());
        model.setImageTurnRight(spec.sprite());
        model.setImageTurnUp(spec.sprite());
        model.setImageTurnDown(spec.sprite());
        return model;
    }

    private static void register(EnemyRegistrar registrar, GuardTowerSpec spec, double x, double y, String id) {
        Platform.runLater(() -> {
            try {
                Zombie model = modelOf(spec, id);
                Node graphics = OpponentRuntimeFactory.createCharacterGraphics(model, TOWER_SIZE);
                graphics.setLayoutX(x - TOWER_SIZE / 2.0);
                graphics.setLayoutY(y - TOWER_SIZE / 2.0);
                GuardTowerCharacter tower = new GuardTowerCharacter(graphics, x, y, TOWER_SIZE, model, spec);
                registrar.registerComputerCharacter(tower, graphics);
                if (App.gameController != null) {
                    App.gameController.registerGuardTower(id, tower);
                }
            } catch (Exception fxException) {
                LOGGER.log(Level.SEVERE, "Failed to create or register a guard tower.", fxException);
            }
        });
    }
}
