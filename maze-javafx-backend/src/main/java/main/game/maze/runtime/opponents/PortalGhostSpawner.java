package main.game.maze.runtime.opponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import main.game.maze.characters.PortalGhostCharacter;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.level.DifficultyTier;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.TeleportGhostSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsFactory;

/**
 * Places the level's teleporting ghosts (JavaFX): as many as the difficulty says, at random free corridor
 * points away from where the player starts. A portal ghost is a ghost model with the values of the levels
 * model, so it is drawn, hurt, killed and scored like every other enemy.
 */
public final class PortalGhostSpawner {

    private static final Logger LOGGER = Logger.getLogger(PortalGhostSpawner.class.getName());
    private static final double MIN_START_DISTANCE = 200.0;
    private static final double GHOST_THREAT = 3.0;
    private static final long SEED = 4242L;

    private PortalGhostSpawner() {
    }

    /**
     * @param startX,startY where the player starts (the ghosts keep their distance)
     * @return how many ghosts were placed
     */
    public static int spawn(EnemyRegistrar registrar, LevelSpec level, Difficulty difficulty, GameMazeWorld world,
                            double startX, double startY) {
        if (registrar == null || level == null || !level.hasTeleportGhosts() || world == null) {
            return 0;
        }
        TeleportGhostSpec spec = level.teleportGhost();
        DifficultyTier tier = DifficultyTier.fromDifficultyName(difficulty == null ? null : difficulty.eClass().getName());
        List<Point2D> corridor = new ArrayList<>(MazeHazards.freePoints(world));
        corridor.removeIf(point -> Math.hypot(point.getX() - startX, point.getY() - startY) < MIN_START_DISTANCE);
        Collections.shuffle(corridor, new Random(SEED));
        int count = Math.min(level.ghostCount(tier), corridor.size());
        double half = StageConstants.GhostCharacterXYSize / 2.0;
        for (int i = 0; i < count; i++) {
            register(registrar, spec, corridor.get(i).getX() - half, corridor.get(i).getY() - half);
        }
        LOGGER.log(Level.INFO, "Placed {0} of {1} portal ghosts ({2})", new Object[] {count, level.ghostCount(tier), tier});
        return count;
    }

    /** The ghost model of a portal ghost: the levels model's values, solid (it does not phase) and harmless. */
    public static Ghost modelOf(TeleportGhostSpec spec) {
        Ghost model = OpponentsFactory.eINSTANCE.createGhost();
        model.setId("portalghost");
        model.setDisplayName(spec.displayName());
        model.setHealth(spec.health());
        model.setSpeed(spec.speed());
        model.setThreatLevel(GHOST_THREAT);
        model.setBehavior(BehaviorType.WANDER);
        model.setAttackDamage(0);
        model.setNonTangibilityEnergy(0);
        model.setVisibilityLevel(100);
        model.setDamageStatesEnabled(false);
        model.setImageBase(spec.sprite());
        model.setImageTurnLeft(spec.sprite());
        model.setImageTurnRight(spec.sprite());
        model.setImageTurnUp(spec.sprite());
        model.setImageTurnDown(spec.sprite());
        return model;
    }

    private static void register(EnemyRegistrar registrar, TeleportGhostSpec spec, double x, double y) {
        Platform.runLater(() -> {
            try {
                Ghost model = modelOf(spec);
                Node graphics = OpponentRuntimeFactory.createCharacterGraphics(model, StageConstants.GhostCharacterXYSize);
                graphics.setLayoutX(x);
                graphics.setLayoutY(y);
                registrar.registerComputerCharacter(new PortalGhostCharacter(graphics, x, y, model, spec), graphics);
            } catch (Exception fxException) {
                LOGGER.log(Level.SEVERE, "Failed to create or register a portal ghost.", fxException);
            }
        });
    }
}
