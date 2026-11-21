package main.game.maze.runtime.opponents;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import main.game.maze.App;
import main.game.maze.characters.SergeantCharacter;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.level.DifficultyTier;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.LevelPlacement;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;

/**
 * Places the level's zombie sergeants (JavaFX): as many as the difficulty says, standing on free
 * corridor points near the heart. A sergeant is a zombie model with the values of the levels model,
 * so it is drawn, hurt, killed and scored like every other enemy.
 */
public final class SergeantSpawner {

    private static final Logger LOGGER = Logger.getLogger(SergeantSpawner.class.getName());
    /** Danger of a sergeant, for the kill points. */
    private static final double SERGEANT_THREAT = 5.0;

    private SergeantSpawner() {
    }

    /**
     * Where this level's sergeants stand on this difficulty: free corridor points near the heart (world space,
     * the centres). The same every time for a maze, so the mines round them can find them again.
     */
    public static List<Point2D> postsFor(LevelSpec level, Difficulty difficulty, GameMazeWorld world) {
        if (level == null || !level.hasSergeants() || world == null) {
            return List.of();
        }
        return LevelPlacement.sergeantPosts(level, DifficultyTier.fromDifficultyName(
                difficulty == null ? null : difficulty.eClass().getName()), world,
                App.getBoardMaxX() / 2.0, App.getBoardMaxY() / 2.0);
    }

    public static int spawn(EnemyRegistrar registrar, LevelSpec level, Difficulty difficulty, GameMazeWorld world) {
        if (registrar == null || level == null || !level.hasSergeants() || world == null) {
            return 0;
        }
        SergeantSpec spec = level.sergeant();
        DifficultyTier tier = DifficultyTier.fromDifficultyName(difficulty == null ? null : difficulty.eClass().getName());
        List<Point2D> posts = postsFor(level, difficulty, world);
        double half = StageConstants.ZombieCharacterXYSize / 2.0;
        for (Point2D post : posts) {
            register(registrar, spec, post.getX() - half, post.getY() - half);
        }
        LOGGER.log(Level.INFO, "Placed {0} of {1} sergeants ({2})", new Object[] {posts.size(), level.sergeantCount(tier), tier});
        return posts.size();
    }

    /** The zombie model of a sergeant: the levels model's values, never moving (speed is unused). */
    static Zombie modelOf(SergeantSpec spec) {
        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setId("sergeant");
        model.setDisplayName(spec.displayName());
        model.setHealth(spec.health());
        model.setSpeed(1.0);
        model.setThreatLevel(SERGEANT_THREAT);
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

    private static void register(EnemyRegistrar registrar, SergeantSpec spec, double x, double y) {
        Platform.runLater(() -> {
            try {
                Zombie model = modelOf(spec);
                Node graphics = OpponentRuntimeFactory.createCharacterGraphics(model, StageConstants.ZombieCharacterXYSize);
                graphics.setLayoutX(x);
                graphics.setLayoutY(y);
                registrar.registerComputerCharacter(new SergeantCharacter(graphics, x, y, model, spec), graphics);
            } catch (Exception fxException) {
                LOGGER.log(Level.SEVERE, "Failed to create or register a sergeant.", fxException);
            }
        });
    }
}
