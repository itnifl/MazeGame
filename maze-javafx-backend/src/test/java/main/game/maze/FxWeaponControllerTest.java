package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.ZombieCharacter;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.Pickup;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupField;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PowerShield;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.score.KillScoring;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** {@link FxWeaponController} against a recording host: no FXML screen and no board needed. */
@DisplayName("JavaFX weapon controller")
class FxWeaponControllerTest {

    private static final double HALF = StageConstants.PlayerCharacterXYSize * 0.5d;

    /** Records what the controller asks the game screen to do. */
    private static final class RecordingHost implements FxWeaponController.Host {
        PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        final List<double[]> bombs = new ArrayList<>();
        final List<String> messages = new ArrayList<>();
        int arsenalChanges;


        @Override public PlayerCharacter player() { return player; }
        @Override public Pane board() { return null; }
        @Override public FxEnemyCoordinator enemies() { return null; }
        @Override public int boardWidth() { return 800; }
        @Override public void plantBomb(double x, double y) { bombs.add(new double[] {x, y}); }
        @Override public void redrawMaze() { }
        @Override public void showMessage(String text) { messages.add(text); }
        @Override public void arsenalChanged() { arsenalChanges++; }
    }

    private RecordingHost host;
    private FxWeaponController weapons;

    @BeforeEach
    void setUp() {
        host = new RecordingHost();
        weapons = new FxWeaponController(host,
                new PickupField(PickupCatalogLoader.loadDefault(), new Random(7)), new PowerShield());
        weapons.startGame(host.player, new GameMazeWorld());
    }

    @Test
    @DisplayName("a new game starts with the player's bombs only and refreshes the HUD")
    void startGame() {
        int bombs = host.player.getStartingBombs();
        assertEquals(bombs, weapons.arsenal().count(Weapon.BOMB));
        assertEquals(0, weapons.arsenal().count(Weapon.LASER_RIFLE));
        assertEquals(0, weapons.arsenal().count(Weapon.SLEDGEHAMMER));
        assertTrue(host.arsenalChanges > 0);
        assertTrue(weapons.hudText().contains("Bombs " + bombs));
    }

    @Test
    @DisplayName("the attack key plants bombs at the player's centre until they run out, then says so")
    void bombsThenOutOfAmmo() {
        host.player.teleportTo(100, 200);
        int bombs = host.player.getStartingBombs();
        for (int i = 0; i < bombs; i++) {
            weapons.useWeapon();
        }
        assertEquals(bombs, host.bombs.size());
        assertEquals(100 + HALF, host.bombs.get(0)[0], 1e-9);
        assertEquals(200 + HALF, host.bombs.get(0)[1], 1e-9);
        assertTrue(host.messages.isEmpty());

        weapons.useWeapon();
        assertEquals(bombs, host.bombs.size(), "no bomb without stock");
        assertEquals(List.of(FxWeaponController.OUT_OF_AMMO_MESSAGE), host.messages);
    }

    @Test
    @DisplayName("Tab switches to a weapon in stock; an empty weapon is skipped")
    void switchWeapon() {
        org.junit.jupiter.api.Assumptions.assumeTrue(host.player.getStartingBombs() > 0, "player starts with bombs");
        weapons.arsenal().add(Weapon.SLEDGEHAMMER, 5);
        assertEquals(Weapon.BOMB, weapons.arsenal().selected(), "the loaded bomb stays in hand");
        int changes = host.arsenalChanges;
        weapons.switchWeapon();
        assertEquals(Weapon.SLEDGEHAMMER, weapons.arsenal().selected(), "the empty laser is skipped");
        weapons.switchWeapon();
        assertEquals(Weapon.BOMB, weapons.arsenal().selected());
        assertEquals(changes + 2, host.arsenalChanges, "the HUD follows every switch");
    }

    @Test
    @DisplayName("items spawn away from the player and are picked up by walking over them")
    void spawnAndPickUp() {
        host.player.teleportTo(-10_000, -10_000);    // far from every corridor point
        weapons.tick(0);
        weapons.tick(6_000_000_000L);               // one spawn interval (5 s) later
        List<Pickup> items = weapons.pickupField().items();
        assertEquals(1, items.size());
        Pickup item = items.get(0);
        String hudBefore = weapons.hudText();
        int changesBefore = host.arsenalChanges;
        host.player.setHitPoints(10);

        host.player.teleportTo(item.x() - HALF, item.y() - HALF);
        weapons.tick(6_010_000_000L);

        assertTrue(weapons.pickupField().items().isEmpty(), "picked up");
        assertTrue(host.arsenalChanges > changesBefore);
        assertTrue(!weapons.hudText().equals(hudBefore) || host.player.getHitPoints() > 10,
                "the item had an effect: " + item.kind());
        assertEquals(item.kind() == PickupKind.HEART, weapons.healFeedback().active(),
                "only a heart flashes the player and shows the arrow");
    }

    @Test
    @DisplayName("a heart flashes the player and shows the arrow until the feedback is over")
    void heartFeedback() {
        host.player.setHitPoints(10);
        boolean healed = false;
        long now = 0;
        for (int i = 0; i < 100 && !healed; i++) {
            host.player.teleportTo(-10_000, -10_000);
            weapons.tick(now);
            now += 3_100_000_000L;
            weapons.tick(now);
            Pickup item = weapons.pickupField().items().get(weapons.pickupField().items().size() - 1);
            host.player.teleportTo(item.x() - HALF, item.y() - HALF);
            now += 10_000_000L;
            weapons.tick(now);
            healed = item.kind() == PickupKind.HEART;
        }
        assertTrue(healed, "a heart spawned within 100 items");
        assertTrue(weapons.healFeedback().active());
        assertTrue(host.player.getHitPoints() > 10, "healed");
        weapons.tick(now + 1_100_000_000L);
        assertFalse(weapons.healFeedback().active(), "over after a second");
    }

    @Test
    @DisplayName("kills by the local player are credited with points by danger; kills by nobody are not")
    void killCredit() {
        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setId("zombie_weak_1");
        model.setHealth(100);
        model.setSpeed(2.0);
        model.setThreatLevel(3.0);
        model.setBehavior(BehaviorType.WANDER);
        ZombieCharacter byPlayer = new ZombieCharacter(new javafx.scene.shape.Rectangle(16, 16), 0, 0, model);
        ZombieCharacter byNobody = new ZombieCharacter(new javafx.scene.shape.Rectangle(16, 16), 50, 0, model);
        byPlayer.subtractHitPoints(1_000, DeathCause.SHOT, PlayerIds.LOCAL_PLAYER);
        byNobody.subtractHitPoints(1_000, DeathCause.FIRE);

        assertTrue(weapons.recordKill(byPlayer, model.getId(), KillScoring.pointsFor(model)));
        assertFalse(weapons.recordKill(byNobody, model.getId(), KillScoring.pointsFor(model)));
        assertEquals(KillScoring.pointsFor(model), weapons.localKillPoints());
        assertEquals(300, weapons.localKillPoints());

        weapons.startGame(host.player, null);
        assertEquals(0, weapons.localKillPoints(), "a new game starts with no kills");
    }

    @Test
    @DisplayName("facing maps to an axis direction (screen y-down)")
    void facingDirection() {
        assertEquals(List.of(-1, 0), asList(FxWeaponController.facingDirection(VectorFacing.LEFT)));
        assertEquals(List.of(1, 0), asList(FxWeaponController.facingDirection(VectorFacing.RIGHT)));
        assertEquals(List.of(0, -1), asList(FxWeaponController.facingDirection(VectorFacing.UP)));
        assertEquals(List.of(0, 1), asList(FxWeaponController.facingDirection(VectorFacing.DOWN)));
    }

    private static List<Integer> asList(int[] values) {
        return List.of(values[0], values[1]);
    }
}
