package main.game.maze;

import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;
import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.fire.HealFeedback;
import main.game.maze.common.graphics.fire.HitReactionEffect;
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
import main.game.maze.game.items.PulseCharge;
import main.game.maze.common.graphics.fire.PulseChargeEffect;
import main.game.maze.game.level.LastLifeGiftSpec;
import main.game.maze.game.items.PowerShield;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.items.WeaponHud;
import main.game.maze.game.items.ShotEffects;
import main.game.maze.game.level.LevelBoosts;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.game.strike.ScheduledBlast;
import main.game.maze.game.strike.StrikePlanner;
import main.game.maze.game.strike.StrikeVisuals;
import main.game.maze.game.strike.StrikeWeapons;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.javafx.render.FxStrikeLayer;
import main.game.maze.mazeworld.flame.FlameTarget;
import main.game.maze.game.session.PlayerLives;
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

        /** Redraws the maze after the laser damaged a wall. */
        void redrawMaze();

        void showMessage(String text);

        /** The arsenal or shield changed: refresh the arsenal HUD ({@link #hudText()}). */
        void arsenalChanged();

        /** The pulse beam left the cannon: shake the screen. */
        default void impactShake() {
        }

        /** Board height, for the airstrike's aim at the centre of the maze. */
        int boardHeight();

        /**
         * A blast of a shop weapon at (x, y): flames that break walls and hurt enemies, the boom and the shake, credited
         * to the player; it hurts the player too when {@code hurtsPlayer}.
         */
        void blast(double x, double y, int damage, double radius, boolean hurtsPlayer, String label);

        /**
         * A nuclear blast of a shop weapon at (x, y) (the small nuclear grenade): a round blast through walls
         * ({@code NuclearBlastEngine}) drawn as a mushroom cloud, credited to the player; it hurts the player too when
         * {@code hurtsPlayer}.
         */
        void nuclearBlast(double x, double y, int damage, double radius, boolean hurtsPlayer, String label);
    }


    static final String WEAPON_SWITCH_SOUND = "/main/game/maze/sounds/weapon-switch.wav";
    static final String BOMB_PLANTED_MESSAGE = "Bomb planted";
    static final String OUT_OF_AMMO_MESSAGE = "Out of ammo - pick up weapons in the maze";
    /** Effects are drawn in front of the characters. */
    private static final int EFFECT_VIEW_ORDER = -9;
    private static final double HALF_PLAYER = StageConstants.PlayerCharacterXYSize * 0.5d;

    private final Host host;
    private final PickupField pickupField;
    private final PowerShield shield;
    private final KillLedger killLedger = new KillLedger();
    private final FxPickupLayer pickupLayer;
    private final HealFeedback healFeedback = new HealFeedback();
    private final PulseCharge pulseCharge = new PulseCharge();
    private double chargeEffectTimer;
    private PlayerArsenal arsenal = new PlayerArsenal(0);
    private PlayerLives lives = new PlayerLives(1);
    private List<Point2D> spawnPoints = List.of();
    private long lastTickNanos = -1;
    /** Grenades, fat bombs, airstrikes and the infantry strike (shared rules). */
    private final StrikeWeapons strikes;
    private final FxStrikeLayer strikeLayer;
    /** The boosts bought in the shop for this level. */
    private LevelBoosts boosts = LevelBoosts.NONE;
    private GameMazeWorld world;

    public FxWeaponController(Host host) {
        this(host, new PickupField(PickupCatalogLoader.loadDefault(), new Random()), new PowerShield());
    }

    FxWeaponController(Host host, PickupField pickupField, PowerShield shield) {
        this.host = host;
        this.pickupField = pickupField;
        this.shield = shield;
        this.pickupLayer = new FxPickupLayer(host::board);
        this.strikes = new StrikeWeapons(pickupField.catalog(), new Random());
        this.strikeLayer = new FxStrikeLayer(host::board);
    }

    /** The boosts bought in the shop for the level that starts (longer shields, stronger bombs). */
    public void setBoosts(LevelBoosts boosts) {
        this.boosts = boosts == null ? LevelBoosts.NONE : boosts;
    }

    public LevelBoosts boosts() {
        return boosts;
    }

    /** New game: the difficulty's bombs, no items, shield or kills yet; items spawn in this level's corridors. */
    public void startGame(PlayerCharacter player, GameMazeWorld world, PlayerLives lives) {
        startGame(player, world, lives, null);
    }

    /**
     * New level: as {@link #startGame(PlayerCharacter, GameMazeWorld, PlayerLives)}, but with the weapons
     * handed on from the previous level when {@code inherited} is not null.
     */
    public void startGame(PlayerCharacter player, GameMazeWorld world, PlayerLives lives, PlayerArsenal inherited) {
        this.lives = lives;
        arsenal = inherited != null ? inherited : new PlayerArsenal(player.getStartingBombs());
        pickupField.clear();
        shield.reset();
        shield.activate(PlayerLives.RESPAWN_PROTECTION_SECONDS);   // spawn shield, like the pickup
        killLedger.clear();
        pickupLayer.clear();
        healFeedback.reset();
        lastTickNanos = -1;
        pulseCharge.cancel();
        strikes.reset();
        strikeLayer.clear();
        this.world = world;
        player.setShield(shield::isActive);
        spawnPoints = pickupField.spawnPointsOf(world);
        host.arsenalChanged();
    }

    /** Attack key: use the weapon in hand (bomb, laser rifle or sledgehammer). */
    public void useWeapon() {
        PlayerCharacter player = host.player();
        if (player == null) {
            return;
        }
        if (arsenal.selected() == Weapon.PULSE_CANNON && arsenal.count(Weapon.PULSE_CANNON) > 0) {
            startPulseCharge();   // it takes two seconds to energy up before the beam leaves; nothing is spent yet
            return;
        }
        if (arsenal.selected() == Weapon.LIFE_REFILL && arsenal.count(Weapon.LIFE_REFILL) > 0
                && PickupEffects.lifeRefillAmount(player.getHitPoints(), player.getMaxHitPoints()) == 0) {
            host.showMessage(PickupEffects.HEALTH_ALREADY_FULL);   // kept for when it is needed
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
        if (StrikeWeapons.handles(used)) {
            useStrike(used, player, center);
            return;
        }
        switch (used) {
            case BOMB -> plantBomb(player, center);
            case LASER_RIFLE, GATLING_GUN -> fireLaser(player, center, catalog.spec(used.pickup()));
            case SLEDGEHAMMER -> swingSledgehammer(player, center, catalog.spec(PickupKind.SLEDGEHAMMER));
            case PULSE_CANNON -> firePulseBeam(player, center, catalog.spec(PickupKind.PULSE_CANNON));
            case BUNKER_BUSTER -> fireBunkerBuster(player, center, catalog.spec(PickupKind.BUNKER_BUSTER));
            case LIFE_REFILL -> refillLife(player, catalog.spec(PickupKind.LIFE_REFILL));
            default -> throw new IllegalStateException("no use defined for " + used);
        }
    }

    /** The bomb: it lies at the player's feet as a bomb with a burning fuse, and goes off like the grenades. */
    private void plantBomb(PlayerCharacter player, double[] center) {
        strikes.plantBomb(center[0], center[1], player.getFlameAttackDamage(), boosts);
        host.showMessage(BOMB_PLANTED_MESSAGE);
    }

    /** Grenades, the fat bomb and the strikes: the shared {@link StrikeWeapons} plans them, {@link #tick} sets them off. */
    private void useStrike(Weapon weapon, PlayerCharacter player, double[] center) {
        int[] dir = facingDirection(player.getFacing());
        StrikeWeapons.Use use = strikes.use(weapon, new StrikeWeapons.Launch(center[0], center[1], dir[0], dir[1],
                world != null ? world.getMazeVectors() : List.of(), host.boardWidth(), host.boardHeight(), spawnPoints,
                boosts));
        playUseSound(use.spec());
        host.showMessage(use.message());
    }

    /** The bunker buster: a rocket that goes through the walls it is strong enough for, and a blast where it stops. */
    private void fireBunkerBuster(PlayerCharacter player, double[] center, PickupSpec buster) {
        int[] dir = facingDirection(player.getFacing());
        double reach = fireLaser(player, center, buster);
        ScheduledBlast impact = StrikePlanner.bunkerBusterImpact(buster, center[0], center[1], dir[0], dir[1], reach);
        host.blast(impact.x(), impact.y(), impact.damage(), impact.radius(), impact.hurtsPlayer(), impact.label());
    }

    /** The extra life of the shop: the health bar is filled. */
    private void refillLife(PlayerCharacter player, PickupSpec life) {
        player.heal(PickupEffects.lifeRefillAmount(player.getHitPoints(), player.getMaxHitPoints()));
        healFeedback.trigger();
        playUseSound(life);
        host.showMessage("Health filled up");
    }

    private void startPulseCharge() {
        if (pulseCharge.charging()) {
            return;
        }
        PickupSpec spec = pickupField.catalog().spec(PickupKind.PULSE_CANNON);
        pulseCharge.start(spec.chargeSeconds());
        chargeEffectTimer = 0;
        if (spec.chargeSound() != null) {
            AudioEngine.get().play(spec.chargeSound());
        }
        host.showMessage("Pulse cannon charging...");
    }

    /** Whether the pulse beam cannon is charging up now. */
    public boolean pulseCharging() {
        return pulseCharge.charging();
    }

    /** Per frame while it charges: the ball of light on the player, and the shot when the charge is full. */
    private void tickPulseCharge(double dt, PlayerCharacter player, double[] center) {
        if (!pulseCharge.charging()) {
            return;
        }
        if (arsenal.selected() != Weapon.PULSE_CANNON || arsenal.count(Weapon.PULSE_CANNON) <= 0 || player.getHitPoints() <= 0) {
            pulseCharge.cancel();   // another weapon was chosen, or the player died: nothing is spent
            return;
        }
        chargeEffectTimer -= dt;
        if (chargeEffectTimer <= 0) {
            playEffect(new PulseChargeEffect(center[0], center[1], pulseCharge.progress()));
            chargeEffectTimer = PulseChargeEffect.FRAME_SECONDS * 0.6;
        }
        if (pulseCharge.tick(dt) && arsenal.use() == Weapon.PULSE_CANNON) {
            host.arsenalChanged();
            firePulseBeam(player, center, pickupField.catalog().spec(PickupKind.PULSE_CANNON));
        }
    }

    private void firePulseBeam(PlayerCharacter player, double[] center, PickupSpec cannon) {
        fireLaser(player, center, cannon);   // the beam: far stronger than the laser, it blows up a tower in one shot
        host.impactShake();
    }

    /**
     * The gift of the last life: the weapon and its shots are put in the hands, and stay the weapon in hand until the
     * player chooses another.
     */
    public void grantLastLifeGift(LastLifeGiftSpec gift) {
        if (gift == null) {
            return;
        }
        gift.give(arsenal);
        host.arsenalChanged();
        host.showMessage("Last life! " + ArsenalHud.text(arsenal, null));
    }

    /** An item lying where it was dropped, e.g. the gun of a killed sergeant. */
    public void dropPickup(PickupKind kind, double x, double y) {
        pickupField.dropAt(kind, x, y);
    }

    /** Plays a short visual effect on the board (a sparkle where a teleported player appears, ...). */
    public void showEffect(main.game.maze.common.graphics.fire.PuffEffect effect) {
        playEffect(effect);
    }

    /** One enemy round on its way: a short tracer line. */
    public void showTracer(double fromX, double fromY, double toX, double toY) {
        playEffect(main.game.maze.game.items.ShotEffects.enemyRound(fromX, fromY, toX, toY));
    }

    /** What the player holds now, to carry into the next level. */
    public main.game.maze.game.items.ArsenalSnapshot arsenalSnapshot() {
        return arsenal.snapshot();
    }

    /** Tab: switch to the next weapon in stock. */
    public void switchWeapon() {
        pulseCharge.cancel();
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
        tickPulseCharge(dt, player, center);
        pickupField.tick(dt, host.boardWidth(), spawnPoints, center[0], center[1]);
        for (Pickup pickup : pickupField.collect(center[0], center[1], HALF_PLAYER)) {
            PickupSpec spec = pickupField.catalog().spec(pickup.kind());
            PickupEffects.apply(spec, arsenal, shield, player::heal, player.getMaxHitPoints(), lives, boosts);
            AudioEngine.get().play(spec.pickupSound());
            if (pickup.kind() == PickupKind.HEART) {
                healFeedback.trigger();   // flash + arrow over the head
            }
            float[] colour = ArsenalHud.sparkleColour(pickup.kind());
            playEffect(new PickupSparkleEffect(pickup.x(), pickup.y(), colour[0], colour[1], colour[2]));
            host.arsenalChanged();
        }
        healFeedback.tick(dt);
        tickStrikes(dt, nowNanos / 1e9);
        if (arsenal.tickBombRegen(dt) > 0) {
            host.arsenalChanged();
        }
        double clock = nowNanos / 1e9;
        pickupLayer.showItems(pickupField.items(), pickupField.catalog(), clock);
        pickupLayer.showHeldWeapon(arsenal.selected(), arsenal.count(arsenal.selected()), pickupField.catalog(),
                center[0], center[1], HALF_PLAYER, player.getFacing());
        pickupLayer.showShield(center[0], center[1], HALF_PLAYER * 1.7, shield.remainingSeconds(), clock);
        pickupLayer.showHeal(healFeedback, center[0], center[1], HALF_PLAYER * 2, clock);
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

    /** The weapons HUD (top-left): weapon icons, the one in hand, and the lives. */
    public WeaponHud.Model hudModel() {
        return WeaponHud.model(arsenal, pickupField.catalog(), lives.remaining());
    }

    /** No damage for a while, e.g. right after a respawn. */
    public void protect(double seconds) {
        shield.activate(seconds);
    }

    /** The score panel line: the shield countdown only (weapons are in the weapons HUD). */
    public String statusText() {
        return ArsenalHud.statusText(shield);
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

    HealFeedback healFeedback() {
        return healFeedback;
    }

    KillLedger killLedger() {
        return killLedger;
    }

    /** Level teardown: removes the item sprites, the weapon in hand and the shield bubble. */
    public void dispose() {
        pickupLayer.clear();
        strikeLayer.clear();
        strikes.reset();
    }

    /** @return how far the shot reached */
    private double fireLaser(PlayerCharacter player, double[] center, PickupSpec laser) {
        int[] dir = facingDirection(player.getFacing());
        FxEnemyCoordinator.LaserHit hit = host.enemies().fireLaser(center[0], center[1], dir[0], dir[1], laser,
                LaserBeamEngine.PLAYER_BEAM_HALF_WIDTH, PlayerIds.LOCAL_PLAYER);
        double reach = hit.shot().reach();
        playEffect(main.game.maze.game.items.ShotEffects.forWeapon(laser.kind(), center[0], center[1],
                center[0] + dir[0] * reach, center[1] + dir[1] * reach));
        if (hit.enemy() != null) {
            react(hit.enemy(), HitReactionEffect.Kind.SHOT, dir[0], dir[1], hit.damage());
        }
        if (hit.shot().outcome() == LaserBeamEngine.Outcome.WALL_DAMAGED) {
            host.redrawMaze();
        }
        playUseSound(laser);
        return reach;
    }

    /** Per frame: the fuses burn down and their blasts go off; the soldiers fire and walk; both are drawn. */
    private void tickStrikes(double dt, double clockSeconds) {
        InfantrySquad.Terrain terrain = world != null && world.getNavigationGraph() != null
                ? InfantrySquad.Terrain.of(world) : null;
        List<FlameTarget> enemies = strikes.squadActive() ? host.enemies().livingTargets(PlayerIds.LOCAL_PLAYER) : List.of();
        StrikeWeapons.Frame frame = strikes.tick(dt, enemies, terrain);
        for (ScheduledBlast blast : frame.blasts()) {
            if (blast.nuclear()) {
                host.nuclearBlast(blast.x(), blast.y(), blast.damage(), blast.radius(), blast.hurtsPlayer(), blast.label());
            } else {
                host.blast(blast.x(), blast.y(), blast.damage(), blast.radius(), blast.hurtsPlayer(), blast.label());
            }
        }
        if (!frame.fallen().isEmpty()) {
            AudioEngine.get().play(StrikeVisuals.SOLDIER_DOWN_SOUND);
        }
        for (InfantrySquad.Round round : frame.rounds()) {
            playEffect(ShotEffects.enemyRound(round.fromX(), round.fromY(), round.toX(), round.toY()));
            AudioEngine.get().playRateLimited(StrikeVisuals.SOLDIER_FIRE_SOUND, StrikeVisuals.SOLDIER_FIRE_SOUND_KEY,
                    StrikeVisuals.SOLDIER_FIRE_COOLDOWN_MILLIS);
        }
        strikeLayer.showSoldiers(strikes.soldiers(), StageConstants.PlayerCharacterXYSize * StrikeVisuals.SOLDIER_SIZE_FACTOR);
        strikeLayer.showFuses(strikes.pendingBlasts(), pickupField.catalog(), clockSeconds);
        strikeLayer.showFalling(strikes.pendingBlasts(), pickupField.catalog());
    }

    /**
     * The soldiers of the infantry strike for the enemies (centres, board space). Safe to use from the AI thread: the
     * positions are a snapshot, and a hit is passed on to the FX thread, where the squad lives.
     */
    public SoldierTargets soldierTargets() {
        SoldierTargets squad = strikes.soldierTargets();
        return SoldierTargets.of(squad::positions,
                (soldier, damage) -> javafx.application.Platform.runLater(() -> squad.hit(soldier, damage)));
    }

    /** The bombs and grenades lying on the floor and the airstrike bombs on their way. */
    public List<ScheduledBlast> pendingBlasts() {
        return strikes.pendingBlasts();
    }

    /** Enemies this close to a soldier go for it instead of the player (items model). */
    public double soldierLureRadius() {
        return strikes.lureRadius();
    }

    /** Whether the infantry strike's soldiers are in the maze now. */
    public boolean squadActive() {
        return strikes.squadActive();
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
