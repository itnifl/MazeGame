package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import main.game.maze.App;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Zombie sergeant (JavaFX): stands still, fires on sight")
class SergeantCharacterTest {

    private static final SergeantSpec SPEC = LevelCatalogLoader.loadDefault().level(2).sergeant();

    @BeforeAll
    static void startToolkit() {
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException alreadyStarted) {
            // fine
        }
    }

    @AfterEach
    void cleanup() {
        App.gameController = null;
    }

    /** A range with an optional wall, recording rounds and tracers. */
    private static class RecordingRange implements SergeantCharacter.Range {
        boolean wall;
        final List<Integer> hits = new ArrayList<>();
        int tracers;

        @Override public boolean wallBetween(double fromX, double fromY, double toX, double toY) { return wall; }
        @Override public void hitPlayer(int damage) { hits.add(damage); }
        @Override public void tracer(double fromX, double fromY, double toX, double toY) { tracers++; }
    }

    private static SergeantCharacter sergeant() {
        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setHealth(SPEC.health());
        model.setSpeed(1.0);
        model.setBehavior(BehaviorType.PASSIVE);
        model.setAttackDamage(0);
        ImageView graphics = new ImageView();
        graphics.setFitWidth(40);
        graphics.setFitHeight(40);
        graphics.setLayoutX(180);
        graphics.setLayoutY(180);
        return new SergeantCharacter(graphics, 180, 180, model, SPEC);
    }

    @Test
    @DisplayName("never moves")
    void neverMoves() {
        SergeantCharacter sergeant = sergeant();
        double x = sergeant.getCharacterPosition().getX();
        double y = sergeant.getCharacterPosition().getY();
        assertFalse(sergeant.move(false));
        assertFalse(sergeant.move(true));
        assertEquals(x, sergeant.getCharacterPosition().getX(), 1e-9);
        assertEquals(y, sergeant.getCharacterPosition().getY(), 1e-9);
    }

    @Test
    @DisplayName("fires a round at once at a player in sight, each round with a tracer and the round damage")
    void firesOnSight() {
        SergeantCharacter sergeant = sergeant();
        RecordingRange range = new RecordingRange();

        int rounds = sergeant.tickGun(300, 200, 1_000_000_000L, range);

        assertEquals(1, rounds);
        assertEquals(List.of(SPEC.roundDamage()), range.hits);
        assertEquals(1, range.tracers);
    }

    @Test
    @DisplayName("a whole burst follows over the next fraction of a second")
    void fullBurst() {
        SergeantCharacter sergeant = sergeant();
        RecordingRange range = new RecordingRange();
        long now = 1_000_000_000L;
        int total = sergeant.tickGun(300, 200, now, range);
        for (int i = 0; i < 100; i++) {
            now += 10_000_000L;   // 10 ms
            total += sergeant.tickGun(300, 200, now, range);
            if (now > 1_700_000_000L) {
                break;
            }
        }
        assertEquals(SPEC.roundsPerBurst(), total, "one burst, then the gun rests");
    }

    @Test
    @DisplayName("holds fire when a wall is in between, or the player is out of range")
    void holdsFire() {
        SergeantCharacter sergeant = sergeant();
        RecordingRange range = new RecordingRange();
        range.wall = true;
        assertEquals(0, sergeant.tickGun(300, 200, 1_000_000_000L, range));

        range.wall = false;
        assertEquals(0, sergeant.tickGun(200 + SPEC.sightRange() + 100, 200, 2_000_000_000L, range));
        assertTrue(range.hits.isEmpty());
    }

    @Test
    @DisplayName("a dead sergeant does not fire")
    void deadSergeantHoldsFire() {
        SergeantCharacter sergeant = sergeant();
        sergeant.subtractHitPoints(SPEC.health() + 10);
        RecordingRange range = new RecordingRange();
        assertEquals(0, sergeant.tickGun(300, 200, 1_000_000_000L, range));
    }

    @Test
    @DisplayName("fires at a soldier of the infantry strike in sight before the player (SR-400)")
    void firesAtSoldiersFirst() {
        SergeantCharacter sergeant = sergeant();
        List<Integer> soldierHits = new ArrayList<>();
        main.game.maze.mazeworld.Point2D soldier = new main.game.maze.mazeworld.Point2D(260, 200);
        RecordingRange range = new RecordingRange() {
            @Override public main.game.maze.game.targeting.SoldierTargets soldiers() {
                return main.game.maze.game.targeting.SoldierTargets.of(() -> List.of(soldier),
                        (at, damage) -> soldierHits.add(damage));
            }
        };

        int rounds = sergeant.tickGun(300, 200, 1_000_000_000L, range);

        assertEquals(1, rounds);
        assertEquals(List.of(SPEC.roundDamage()), soldierHits, "the soldier took the round");
        assertTrue(range.hits.isEmpty(), "not the player");
    }
}
