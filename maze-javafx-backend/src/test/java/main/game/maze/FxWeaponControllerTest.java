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
import main.game.maze.game.session.PlayerLives;
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
        final List<String> blasts = new ArrayList<>();
        int arsenalChanges;


        @Override public PlayerCharacter player() { return player; }
        @Override public Pane board() { return null; }
        @Override public FxEnemyCoordinator enemies() { return null; }
        @Override public int boardWidth() { return 800; }
        @Override public void plantBomb(double x, double y) { bombs.add(new double[] {x, y}); }
        @Override public void redrawMaze() { }
        @Override public void showMessage(String text) { messages.add(text); }
        @Override public void arsenalChanged() { arsenalChanges++; }
        @Override public int boardHeight() { return 600; }
        @Override public void blast(double x, double y, int damage, double radius, boolean hurtsPlayer, String label) {
            blasts.add(label);
        }
    }

    /** killCredit animates a dying enemy, which needs the JavaFX toolkit; the test must not rely on another test starting it. */
    @org.junit.jupiter.api.BeforeAll
    static void initFx() throws Exception {
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
        try {
            javafx.application.Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(5, java.util.concurrent.TimeUnit.SECONDS), "JavaFX startup timed out");
    }

    private RecordingHost host;
    private FxWeaponController weapons;
    private PlayerLives lives;

    @BeforeEach
    void setUp() {
        host = new RecordingHost();
        weapons = new FxWeaponController(host,
                new PickupField(PickupCatalogLoader.loadDefault(), new Random(7)), new PowerShield());
        lives = new PlayerLives(1);
        weapons.startGame(host.player, new GameMazeWorld(), lives);
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
        assertTrue(!weapons.hudText().equals(hudBefore) || host.player.getHitPoints() > 10 || lives.remaining() > 1
                        || weapons.statusText().startsWith("Shield "),
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

        weapons.startGame(host.player, null, new PlayerLives(1));
        assertEquals(0, weapons.localKillPoints(), "a new game starts with no kills");
    }

    @Test
    @DisplayName("the weapons HUD model follows the arsenal and the lives")
    void hudModel() {
        weapons.arsenal().add(Weapon.LASER_RIFLE, 5);
        lives.gain();
        var model = weapons.hudModel();
        assertEquals(2, model.lives());
        assertTrue(model.slots().stream().anyMatch(slot -> slot.weapon() == Weapon.LASER_RIFLE && slot.count() == 5));
        weapons.protect(2.0);
        assertTrue(weapons.hudText().contains("Shield"), "respawn protection is the shield");
        assertTrue(weapons.statusText().startsWith("Shield "), "the score panel shows the shield countdown");
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

    @Test
    @DisplayName("a new level starts with the weapons handed on from the previous one")
    void inheritedArsenal() {
        main.game.maze.game.items.PlayerArsenal carried = new main.game.maze.game.items.PlayerArsenal(7);
        carried.add(Weapon.LASER_RIFLE, 4);
        carried.add(Weapon.GATLING_GUN, 2);

        weapons.startGame(host.player, new GameMazeWorld(), lives,
                main.game.maze.game.items.PlayerArsenal.restore(carried.snapshot()));

        assertEquals(7, weapons.arsenal().count(Weapon.BOMB));
        assertEquals(4, weapons.arsenal().count(Weapon.LASER_RIFLE));
        assertEquals(2, weapons.arsenal().count(Weapon.GATLING_GUN));
        assertEquals(7, weapons.arsenalSnapshot().counts().get(Weapon.BOMB));
    }

    @Test
    @DisplayName("a gun dropped where a sergeant fell is picked up by walking over it")
    void droppedGunCanBePickedUp() {
        host.player.teleportTo(-10_000, -10_000);
        weapons.tick(0);
        weapons.dropPickup(PickupKind.GATLING_GUN, 300, 300);
        assertTrue(weapons.pickupField().items().stream().anyMatch(item -> item.kind() == PickupKind.GATLING_GUN));

        host.player.teleportTo(300 - HALF, 300 - HALF);
        weapons.tick(1_000_000L);

        assertTrue(weapons.pickupField().items().stream().noneMatch(item -> item.kind() == PickupKind.GATLING_GUN));
        assertEquals(weapons.pickupField().catalog().spec(PickupKind.GATLING_GUN).amount(),
                weapons.arsenal().count(Weapon.GATLING_GUN));
    }

    @Test
    @DisplayName("the last-life gift: the pulse cannon with six shots in the hands; fire starts a two second charge and spends nothing yet")
    void pulseCannonChargesFirst() {
        weapons.grantLastLifeGift(main.game.maze.game.level.LevelCatalogLoader.loadDefault().level(6).lastLifeGift());
        assertEquals(Weapon.PULSE_CANNON, weapons.arsenal().selected());
        assertEquals(6, weapons.arsenal().count(Weapon.PULSE_CANNON));
        assertTrue(host.messages.stream().anyMatch(m -> m.contains("Last life")));

        weapons.useWeapon();
        assertTrue(weapons.pulseCharging(), "charging up");
        assertEquals(6, weapons.arsenal().count(Weapon.PULSE_CANNON), "no shot is spent until the beam leaves");
        weapons.tick(0);
        weapons.tick(1_500_000_000L);                 // 1.5 s later: still charging
        assertTrue(weapons.pulseCharging());
        assertEquals(6, weapons.arsenal().count(Weapon.PULSE_CANNON));
    }

    @Test
    @DisplayName("choosing another weapon cancels the charge and spends nothing; the cannon stays in stock")
    void switchingCancelsTheCharge() {
        weapons.grantLastLifeGift(main.game.maze.game.level.LevelCatalogLoader.loadDefault().level(6).lastLifeGift());
        weapons.useWeapon();
        weapons.switchWeapon();
        assertFalse(weapons.pulseCharging());
        assertTrue(weapons.arsenal().selected() != Weapon.PULSE_CANNON);
        assertEquals(6, weapons.arsenal().count(Weapon.PULSE_CANNON));
    }

    @Test
    @DisplayName("a level without the gift gives nothing")
    void noGift() {
        int before = weapons.arsenal().count(Weapon.PULSE_CANNON);
        weapons.grantLastLifeGift(null);
        assertEquals(before, weapons.arsenal().count(Weapon.PULSE_CANNON));
    }

    @Test
    @DisplayName("shop weapons: a thrown grenade goes off after its fuse through the game screen's blast")
    void thrownGrenadeGoesOff() {
        weapons.arsenal().add(Weapon.GRENADE, 2);
        weapons.arsenal().select(Weapon.GRENADE);
        weapons.useWeapon();
        assertEquals(1, weapons.arsenal().count(Weapon.GRENADE));
        assertTrue(host.blasts.isEmpty(), "not yet: the fuse burns");
        weapons.tick(1_000_000_000L);
        weapons.tick(4_000_000_000L);   // three seconds later: past the 1.2 s fuse
        assertEquals(List.of("Grenade"), host.blasts);
        assertTrue(host.messages.contains("Grenade thrown"));
    }

    @Test
    @DisplayName("shop weapons: the airstrike drops all its bombs, the fat bomb goes off once")
    void airstrikeAndFatBomb() {
        weapons.arsenal().add(Weapon.AIRSTRIKE, 1);
        weapons.arsenal().add(Weapon.FAT_BOMB, 1);
        weapons.arsenal().select(Weapon.AIRSTRIKE);
        weapons.useWeapon();
        weapons.arsenal().select(Weapon.FAT_BOMB);
        weapons.useWeapon();
        weapons.tick(1_000_000_000L);
        weapons.tick(11_000_000_000L);
        int bombs = PickupCatalogLoader.loadDefault().spec(PickupKind.AIRSTRIKE).units();
        assertEquals(bombs, host.blasts.stream().filter("Airstrike"::equals).count());
        assertEquals(1, host.blasts.stream().filter("Fat bomb"::equals).count());
    }

    @Test
    @DisplayName("shop weapons: the extra life fills the health bar, and is kept when health is already full")
    void lifeRefill() {
        weapons.arsenal().add(Weapon.LIFE_REFILL, 3);
        weapons.arsenal().select(Weapon.LIFE_REFILL);
        weapons.useWeapon();
        assertEquals(3, weapons.arsenal().count(Weapon.LIFE_REFILL), "full health: not spent");
        assertTrue(host.messages.contains(main.game.maze.game.items.PickupEffects.HEALTH_ALREADY_FULL));
        host.player.setHitPoints(1);
        weapons.useWeapon();
        assertEquals(2, weapons.arsenal().count(Weapon.LIFE_REFILL));
        assertEquals(host.player.getMaxHitPoints(), host.player.getHitPoints(), "the bar is full");
    }

    @Test
    @DisplayName("the shop's extra long shield makes a shield cube last longer this level")
    void shieldBoost() {
        weapons.setBoosts(main.game.maze.game.level.LevelBoosts.NONE.withShield(3));
        assertEquals(3.0, weapons.boosts().shieldDurationFactor(), 1e-9);
        weapons.setBoosts(null);
        assertEquals(main.game.maze.game.level.LevelBoosts.NONE, weapons.boosts());
    }
}
