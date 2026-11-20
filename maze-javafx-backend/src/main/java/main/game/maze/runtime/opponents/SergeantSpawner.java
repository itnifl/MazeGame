package main.game.maze.runtime.opponents;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import main.game.maze.App;
import main.game.maze.characters.SergeantCharacter;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupField;
import main.game.maze.game.level.DifficultyTier;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.SergeantPosts;
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
    /** Posts keep at least this far from each other (two sergeant sizes). */
    private static final double MIN_POST_SPACING = StageConstants.ZombieCharacterXYSize * 2.0;
    /** Danger of a sergeant, for the kill points. */
    private static final double SERGEANT_THREAT = 5.0;

    private SergeantSpawner() {
    }

    public static int spawn(EnemyRegistrar registrar, LevelSpec level, Difficulty difficulty, GameMazeWorld world) {
        if (registrar == null || level == null || !level.hasSergeants() || world == null) {
            return 0;
        }
        SergeantSpec spec = level.sergeant();
        DifficultyTier tier = DifficultyTier.fromDifficultyName(difficulty == null ? null : difficulty.eClass().getName());
        List<Point2D> corridor = new PickupField(PickupCatalogLoader.loadDefault(), new java.util.Random()).spawnPointsOf(world);
        List<Point2D> posts = SergeantPosts.choose(corridor, App.getBoardMaxX() / 2.0, App.getBoardMaxY() / 2.0,
                spec.postDistanceFromHeart(), level.sergeantCount(tier), MIN_POST_SPACING);
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
