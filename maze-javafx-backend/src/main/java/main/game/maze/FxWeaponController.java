package main.game.maze;

import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;
import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.fire.HitReactionEffect;
import main.game.maze.common.graphics.fire.LaserBeamEffect;
import main.game.maze.common.graphics.fire.PickupSparkleEffect;
import main.game.maze.common.graphics.fire.PuffEffect;
import main.game.maze.common.graphics.fire.SledgeSwingEffect;
import main.game.maze.game.items.ArsenalHud;
import main.game.maze.game.items.Pickup;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupEffects;
import main.game.maze.game.items.PickupField;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.PowerShield;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.score.KillLedger;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.javafx.render.FxExplosionView;
import main.game.maze.javafx.render.FxPickupLayer;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.flame.LaserBeamEngine;

/**
 * JavaFX controller for the player's weapons, the pickups lying in the maze and kill credit: the
 * counterpart of libGDX {@code GdxWeaponSupport}, using the same shared rules ({@link PickupField},
 * {@link PlayerArsenal}, {@link PowerShield}, {@link KillLedger}, {@link LaserBeamEngine},
 * {@code MortalityService.strikeAround}). The {@link Host} gives it the player, the board and the
 * few game actions it needs, so it can be tested without the FXML game screen. Use on the JavaFX
 * Application Thread, except {@link #recordKill}, which is thread-safe.
 */
public final class FxWeaponController {

    /** What the controller needs from the game screen. */
    public interface Host {
        PlayerCharacter player();

        Pane board();

        FxEnemyCoordinator enemies();

        /** Board width, which decides the item cap (small / medium / large maps). */
        int boardWidth();

        /** Plants a bomb at the given board point. */
        void plantBomb(double x, double y);

        /** Redraws the maze after the laser damaged a wall. */
        void redrawMaze();

        void showMessage(String text);

        /** The arsenal or shield changed: refresh the arsenal HUD ({@link #hudText()}). */
        void arsenalChanged();
    }

    static final String WEAPON_SWITCH_SOUND = "/main/game/maze/sounds/weapon-switch.wav";
    static final String OUT_OF_AMMO_MESSAGE = "Out of ammo - pick up weapons in the maze";
    /** Effects are drawn in front of the characters. */
    private static final int EFFECT_VIEW_ORDER = -9;
    private static final double HALF_PLAYER = StageConstants.PlayerCharacterXYSize * 0.5d;

    private final Host host;
    private final PickupField pickupField;
    private final PowerShield shield;
    private final KillLedger killLedger = new KillLedger();
    private final FxPickupLayer pickupLayer;
    private PlayerArsenal arsenal = new PlayerArsenal(0);
    private List<Point2D> spawnPoints = List.of();
    private long lastTickNanos = -1;

    public FxWeaponController(Host host) {
        this(host, new PickupField(PickupCatalogLoader.loadDefault(), new Random()), new PowerShield());
    }

    FxWeaponController(Host host, PickupField pickupField, PowerShield shield) {
        this.host = host;
        this.pickupField = pickupField;
        this.shield = shield;
        this.pickupLayer = new FxPickupLayer(host::board);
    }

    /** New game: the difficulty's bombs, no items, shield or kills yet; items spawn in this level's corridors. */
    public void startGame(PlayerCharacter player, GameMazeWorld world) {
        arsenal = new PlayerArsenal(player.getStartingBombs());
        pickupField.clear();
        shield.reset();
        killLedger.clear();
        pickupLayer.clear();
        lastTickNanos = -1;
        player.setShield(shield::isActive);
        spawnPoints = PickupField.spawnPointsOf(world);
        host.arsenalChanged();
    }

    /** Attack key: use the weapon in hand (bomb, laser rifle or sledgehammer). */
    public void useWeapon() {
        PlayerCharacter player = host.player();
        if (player == null) {
            return;
        }
        Weapon used = arsenal.use();
        if (used == null) {
            AudioEngine.get().play(WEAPON_SWITCH_SOUND);
            host.showMessage(OUT_OF_AMMO_MESSAGE);
            return;
        }
        host.arsenalChanged();
        double[] center = centerOf(player);
        PickupCatalog catalog = pickupField.catalog();
        switch (used) {
            case BOMB -> host.plantBomb(center[0], center[1]);
            case LASER_RIFLE -> fireLaser(player, center, catalog.spec(PickupKind.LASER_RIFLE));
            case SLEDGEHAMMER -> swingSledgehammer(player, center, catalog.spec(PickupKind.SLEDGEHAMMER));
        }
    }

    /** Tab: switch to the next weapon in stock. */
    public void switchWeapon() {
        Weapon before = arsenal.selected();
        if (arsenal.cycle() != before) {
            AudioEngine.get().play(WEAPON_SWITCH_SOUND);
        }
        host.arsenalChanged();
    }

    /** Per frame: spawn and pick up items, update their sprites, the weapon in hand and the shield. */
    public void tick(long nowNanos) {
        double dt = lastTickNanos < 0 ? 0 : (nowNanos - lastTickNanos) / 1e9;
        lastTickNanos = nowNanos;
        PlayerCharacter player = host.player();
        if (player == null) {
            return;
        }
        double[] center = centerOf(player);
        pickupField.tick(dt, host.boardWidth(), spawnPoints, center[0], center[1]);
        for (Pickup pickup : pickupField.collect(center[0], center[1], HALF_PLAYER)) {
            PickupSpec spec = pickupField.catalog().spec(pickup.kind());
            PickupEffects.apply(spec, arsenal, shield, player::heal, player.getMaxHitPoints());
            AudioEngine.get().play(spec.pickupSound());
            float[] colour = ArsenalHud.sparkleColour(pickup.kind());
            playEffect(new PickupSparkleEffect(pickup.x(), pickup.y(), colour[0], colour[1], colour[2]));
            host.arsenalChanged();
        }
        double clock = nowNanos / 1e9;
        pickupLayer.showItems(pickupField.items(), pickupField.catalog(), clock);
        pickupLayer.showHeldWeapon(arsenal.selected(), arsenal.count(arsenal.selected()), pickupField.catalog(),
                center[0], center[1], HALF_PLAYER, player.getFacing());
        pickupLayer.showShield(center[0], center[1], HALF_PLAYER * 1.7, shield.remainingSeconds(), clock);
        if (shield.isActive()) {
            host.arsenalChanged();   // shield countdown
        }
    }

    /**
     * Credits a dead enemy to whoever killed it (called by the enemy as it dies).
     *
     * @return true when the kill was credited to a player
     */
    public boolean recordKill(ICanDie victim, String victimTypeId, int points) {
        return killLedger.record(victim, victimTypeId, points).isPresent();
    }

    /** Points the local player earned for kills this game. */
    public int localKillPoints() {
        return killLedger.pointsFor(PlayerIds.LOCAL_PLAYER);
    }

    /** The arsenal HUD line, e.g. {@code Bombs 3  [Laser 5]  Sledge 0  Shield 4s}. */
    public String hudText() {
        return ArsenalHud.text(arsenal, shield);
    }

    PlayerArsenal arsenal() {
        return arsenal;
    }

    PickupField pickupField() {
        return pickupField;
    }

    KillLedger killLedger() {
        return killLedger;
    }

    /** Level teardown: removes the item sprites, the weapon in hand and the shield bubble. */
    public void dispose() {
        pickupLayer.clear();
    }

    private void fireLaser(PlayerCharacter player, double[] center, PickupSpec laser) {
        int[] dir = facingDirection(player.getFacing());
        FxEnemyCoordinator.LaserHit hit = host.enemies().fireLaser(center[0], center[1], dir[0], dir[1], laser,
                LaserBeamEngine.PLAYER_BEAM_HALF_WIDTH, PlayerIds.LOCAL_PLAYER);
        double reach = hit.shot().reach();
        playEffect(new LaserBeamEffect(center[0], center[1], center[0] + dir[0] * reach, center[1] + dir[1] * reach));
        if (hit.enemy() != null) {
            react(hit.enemy(), HitReactionEffect.Kind.SHOT, dir[0], dir[1], hit.damage());
        }
        if (hit.shot().outcome() == LaserBeamEngine.Outcome.WALL_DAMAGED) {
            host.redrawMaze();
        }
        playUseSound(laser);
    }

    private void swingSledgehammer(PlayerCharacter player, double[] center, PickupSpec sledge) {
        int[] dir = facingDirection(player.getFacing());
        List<ICanDie> struck = host.enemies().swingSledgehammer(center[0], center[1], sledge, PlayerIds.LOCAL_PLAYER);
        playEffect(new SledgeSwingEffect(center[0], center[1], dir[0], dir[1], sledge.range()));
        for (ICanDie mortal : struck) {
            if (mortal instanceof ComputerCharacter enemy) {
                double[] at = FxEnemyCoordinator.centerOf(enemy);
                react(enemy, HitReactionEffect.Kind.BLOW, at[0] - center[0], at[1] - center[1], sledge.damage());
            }
        }
        playUseSound(sledge);
    }

    private void react(ComputerCharacter enemy, HitReactionEffect.Kind kind, double dirX, double dirY, int damage) {
        double[] at = FxEnemyCoordinator.centerOf(enemy);
        HitReactionEffect reaction = new HitReactionEffect(kind, at[0], at[1], dirX, dirY,
                HitReactionEffect.severity(damage, enemy.getMaxHitPoints()), System.nanoTime());
        enemy.react(reaction);
        playEffect(reaction);
    }

    private static void playUseSound(PickupSpec spec) {
        if (spec.useSound() != null) {
            AudioEngine.get().play(spec.useSound());
        }
    }

    private void playEffect(PuffEffect effect) {
        Pane board = host.board();
        if (board != null) {
            FxExplosionView.of(effect).play(board, effect.lifetimeSeconds(), EFFECT_VIEW_ORDER);
        }
    }

    private static double[] centerOf(PlayerCharacter player) {
        return new double[] {
                player.getCharacterPosition().getX() + HALF_PLAYER,
                player.getCharacterPosition().getY() + HALF_PLAYER};
    }

    static int[] facingDirection(VectorFacing facing) {
        return switch (facing) {
            case LEFT -> new int[] {-1, 0};
            case UP -> new int[] {0, -1};
            case DOWN -> new int[] {0, 1};
            default -> new int[] {1, 0};
        };
    }
}
