package main.game.maze.runtime.opponents;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import main.game.maze.App;
import main.game.maze.characters.WallBuilderCharacter;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.level.DifficultyTier;
import main.game.maze.game.level.LevelPlacement;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;

/**
 * Places the level's wall builders (JavaFX): as many as the difficulty says, in the area round the heart. A builder
 * is a zombie model with the values of the levels model, so it is drawn, hurt, killed and scored like every other enemy.
 */
public final class WallBuilderSpawner {

    private static final Logger LOGGER = Logger.getLogger(WallBuilderSpawner.class.getName());
    /** Side of a builder sprite on the board, in pixels (like the other zombies). */
    public static final int BUILDER_SIZE = StageConstants.ZombieCharacterXYSize;
    /** Danger of a builder, for the kill points: it cannot hurt anyone. */
    private static final double BUILDER_THREAT = 1.0;

    private WallBuilderSpawner() {
    }

    public static List<Point2D> spotsFor(LevelSpec level, Difficulty difficulty, GameMazeWorld world) {
        if (level == null || !level.hasBuilders() || world == null) {
            return List.of();
        }
        return LevelPlacement.builderSpots(level, DifficultyTier.fromDifficultyName(
                difficulty == null ? null : difficulty.eClass().getName()), world,
                App.getBoardMaxX() / 2.0, App.getBoardMaxY() / 2.0);
    }

    public static int spawn(EnemyRegistrar registrar, LevelSpec level, Difficulty difficulty, GameMazeWorld world) {
        if (registrar == null || level == null || !level.hasBuilders() || world == null) {
            return 0;
        }
        WallBuilderSpec spec = level.wallBuilder();
        List<Point2D> spots = spotsFor(level, difficulty, world);
        int index = 0;
        for (Point2D spot : spots) {
            register(registrar, spec, spot.getX(), spot.getY(), WallBuilderSpec.SPAWN_ID_PREFIX + "_" + index++);
        }
        LOGGER.log(Level.INFO, "Placed {0} wall builders", spots.size());
        return spots.size();
    }

    /** The zombie model of a builder: the levels model values, no touch damage, never resurrecting. */
    static Zombie modelOf(WallBuilderSpec spec, String id) {
        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setId(id);
        model.setDisplayName(spec.displayName());
        model.setHealth(spec.health());
        model.setSpeed(1.0);
        model.setThreatLevel(BUILDER_THREAT);
        model.setBehavior(BehaviorType.PASSIVE);
        model.setAttackDamage(0);
        model.setInfectionLevel(0);
        model.setResurrectionTime(0);
        model.setDamageStatesEnabled(false);
        model.setImageBase(spec.sprite());
        model.setImageTurnLeft(spec.sprite());
        model.setImageTurnRight(spec.sprite());
        model.setImageTurnUp(spec.sprite());
        model.setImageTurnDown(spec.sprite());
        return model;
    }

    private static void register(EnemyRegistrar registrar, WallBuilderSpec spec, double x, double y, String id) {
        Platform.runLater(() -> {
            try {
                Zombie model = modelOf(spec, id);
                Node graphics = OpponentRuntimeFactory.createCharacterGraphics(model, BUILDER_SIZE);
                graphics.setLayoutX(x - BUILDER_SIZE / 2.0);
                graphics.setLayoutY(y - BUILDER_SIZE / 2.0);
                WallBuilderCharacter builder = new WallBuilderCharacter(graphics, x - BUILDER_SIZE / 2.0,
                        y - BUILDER_SIZE / 2.0, BUILDER_SIZE, model, spec);
                registrar.registerComputerCharacter(builder, graphics);
                if (App.gameController != null) {
                    App.gameController.registerWallBuilder(id, builder);
                }
            } catch (Exception fxException) {
                LOGGER.log(Level.SEVERE, "Failed to create or register a wall builder.", fxException);
            }
        });
    }
}
