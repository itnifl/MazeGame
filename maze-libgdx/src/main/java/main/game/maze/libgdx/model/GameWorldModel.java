package main.game.maze.libgdx.model;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.WallRubble;
import main.game.maze.common.graphics.fire.HealFeedback;
import main.game.maze.common.graphics.fire.PuffEffect;
import main.game.maze.common.remains.RemainsLedger;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.session.PlayerLives;
import main.game.maze.game.items.PickupField;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.PowerShield;
import main.game.maze.game.score.KillLedger;
import main.game.maze.game.runtime.RetiredShooters;
import main.game.maze.dto.Score;
import main.game.maze.libgdx.model.DeadEnemy;
import main.game.maze.libgdx.model.EnemyDeathAnimation;
import main.game.maze.game.score.PathHintBudget;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Mutable gameplay runtime state boundary for libGDX play sessions.
 */
public final class GameWorldModel {

    private MazeArena maze;
    private PlayerState player;
    private RuntimeVisualModel runtimeModel;
    private float activePlayerSpeed;
    private float activeGoalSize;
    private float activeGoalX;
    private float activeGoalY;
    private float currentHpRatio = 1f;
    private float playerTintRed = 1f;
    private float playerTintGreen = 1f;
    private float playerTintBlue = 1f;
    private boolean infectionWarningVisible;
    private boolean playedWinSound;
    private boolean playedGameOverSound;
    private boolean deathSequenceStarted;
    private float deathDisplayRemainingSeconds;
    private float pathPenaltyPoints;
    private PathHintBudget pathHintBudget = new PathHintBudget(PathHintBudget.EASY_SECONDS);
    private float explosionShakeRemainingSeconds;
    private float explosionShakeIntensity;

    private final List<GdxEnemyRuntime> animatedEnemies = new ArrayList<>();
    private final List<DeadEnemy> deadEnemies = new ArrayList<>();
    private final List<EnemyDeathAnimation> dyingEnemies = new ArrayList<>();
    private final List<Point2D> activePathPoints = new ArrayList<>();
    private final List<GdxEnemyRuntime.ProjectileVisual> enemyProjectiles = new ArrayList<>();
    private final List<GdxEnemyRuntime.BeamVisual> enemyBeams = new ArrayList<>();
    private final List<BombFuseGlow> bombFuseGlows = new ArrayList<>();
    private final List<FireBlastVisual> fireBlasts = new ArrayList<>();
    private final List<PumpkinBurstVisual> enemyBursts = new ArrayList<>();
    private final List<ActiveWallRubble> wallRubble = new ArrayList<>();
    private final RemainsLedger<EnemySpawn, RemainsVisual> remains = new RemainsLedger<>(RemainsLedger.DEFAULT_CAPACITY);
    private final RetiredShooters<GdxEnemyRuntime> retiredShooters = new RetiredShooters<>(GdxEnemyRuntime::hasShotsInFlight);
    private final PickupField pickupField = new PickupField(PickupCatalogLoader.loadDefault(), new java.util.Random());
    private final PowerShield shield = new PowerShield();
    private final main.game.maze.game.strike.StrikeWeapons strikes =
            new main.game.maze.game.strike.StrikeWeapons(pickupField.catalog(), new java.util.Random());
    private main.game.maze.game.level.LevelBoosts boosts = main.game.maze.game.level.LevelBoosts.NONE;
    private final KillLedger killLedger = new KillLedger();
    private final List<ActiveEffect> effects = new ArrayList<>();
    private final HealFeedback healFeedback = new HealFeedback();
    private final PlayerLives lives = new PlayerLives(1);
    private PlayerArsenal arsenal = new PlayerArsenal(0);
    private final List<Score> highScoreRows = new ArrayList<>();

    public MazeArena maze() {
        return maze;
    }

    public void setMaze(MazeArena maze) {
        this.maze = maze;
    }

    public PlayerState player() {
        return player;
    }

    public void setPlayer(PlayerState player) {
        this.player = player;
    }

    public RuntimeVisualModel runtimeModel() {
        return runtimeModel;
    }

    public void setRuntimeModel(RuntimeVisualModel runtimeModel) {
        this.runtimeModel = runtimeModel;
    }

    public float activePlayerSpeed() {
        return activePlayerSpeed;
    }

    public void setActivePlayerSpeed(float activePlayerSpeed) {
        this.activePlayerSpeed = activePlayerSpeed;
    }

    public float activeGoalSize() {
        return activeGoalSize;
    }

    public void setActiveGoalSize(float activeGoalSize) {
        this.activeGoalSize = activeGoalSize;
    }

    public float activeGoalX() {
        return activeGoalX;
    }

    public void setActiveGoalX(float activeGoalX) {
        this.activeGoalX = activeGoalX;
    }

    public float activeGoalY() {
        return activeGoalY;
    }

    public void setActiveGoalY(float activeGoalY) {
        this.activeGoalY = activeGoalY;
    }

    public float currentHpRatio() {
        return currentHpRatio;
    }

    public void setCurrentHpRatio(float currentHpRatio) {
        this.currentHpRatio = currentHpRatio;
    }

    public float playerTintRed() {
        return playerTintRed;
    }

    public void setPlayerTintRed(float playerTintRed) {
        this.playerTintRed = playerTintRed;
    }

    public float playerTintGreen() {
        return playerTintGreen;
    }

    public void setPlayerTintGreen(float playerTintGreen) {
        this.playerTintGreen = playerTintGreen;
    }

    public float playerTintBlue() {
        return playerTintBlue;
    }

    public void setPlayerTintBlue(float playerTintBlue) {
        this.playerTintBlue = playerTintBlue;
    }

    public boolean infectionWarningVisible() {
        return infectionWarningVisible;
    }

    public void setInfectionWarningVisible(boolean infectionWarningVisible) {
        this.infectionWarningVisible = infectionWarningVisible;
    }

    public boolean playedWinSound() {
        return playedWinSound;
    }

    public void setPlayedWinSound(boolean playedWinSound) {
        this.playedWinSound = playedWinSound;
    }

    public boolean playedGameOverSound() {
        return playedGameOverSound;
    }

    public void setPlayedGameOverSound(boolean playedGameOverSound) {
        this.playedGameOverSound = playedGameOverSound;
    }

    public boolean deathSequenceStarted() {
        return deathSequenceStarted;
    }

    public void setDeathSequenceStarted(boolean deathSequenceStarted) {
        this.deathSequenceStarted = deathSequenceStarted;
    }

    public float deathDisplayRemainingSeconds() {
        return deathDisplayRemainingSeconds;
    }

    public void setDeathDisplayRemainingSeconds(float deathDisplayRemainingSeconds) {
        this.deathDisplayRemainingSeconds = deathDisplayRemainingSeconds;
    }

    public float pathPenaltyPoints() {
        return pathPenaltyPoints;
    }

    public void setPathPenaltyPoints(float pathPenaltyPoints) {
        this.pathPenaltyPoints = pathPenaltyPoints;
    }

    public PathHintBudget pathHintBudget() {
        return pathHintBudget;
    }

    public void setPathHintBudget(PathHintBudget pathHintBudget) {
        this.pathHintBudget = pathHintBudget;
    }

    public float explosionShakeRemainingSeconds() {
        return explosionShakeRemainingSeconds;
    }

    public void setExplosionShakeRemainingSeconds(float explosionShakeRemainingSeconds) {
        this.explosionShakeRemainingSeconds = Math.max(0f, explosionShakeRemainingSeconds);
    }

    public float explosionShakeIntensity() {
        return explosionShakeIntensity;
    }

    public void setExplosionShakeIntensity(float explosionShakeIntensity) {
        this.explosionShakeIntensity = Math.max(0f, explosionShakeIntensity);
    }

    public List<GdxEnemyRuntime> animatedEnemies() {
        return animatedEnemies;
    }

    public List<DeadEnemy> deadEnemies() {
        return deadEnemies;
    }

    public List<EnemyDeathAnimation> dyingEnemies() {
        return dyingEnemies;
    }

    public List<Point2D> activePathPoints() {
        return activePathPoints;
    }

    public List<GdxEnemyRuntime.ProjectileVisual> enemyProjectiles() {
        return enemyProjectiles;
    }

    public List<GdxEnemyRuntime.BeamVisual> enemyBeams() {
        return enemyBeams;
    }

    public List<BombFuseGlow> bombFuseGlows() {
        return bombFuseGlows;
    }

    /** Player bomb fire blasts to draw this frame; rebuilt each frame by the screen controller. */
    public List<FireBlastVisual> fireBlasts() {
        return fireBlasts;
    }

    /** Enemy projectile explosions to draw this frame; rebuilt each frame with the other enemy visuals. */
    public List<PumpkinBurstVisual> enemyBursts() {
        return enemyBursts;
    }

    /** Walls currently collapsing; ticked by {@code GdxWallCollapseSupport}. */
    public List<ActiveWallRubble> wallRubble() {
        return wallRubble;
    }

    /** Items lying in the level (spawn every few seconds up to the board's cap). */
    public PickupField pickupField() {
        return pickupField;
    }

    /** The player's weapons and ammunition. */
    public PlayerArsenal arsenal() {
        return arsenal;
    }

    /** New game: a fresh arsenal with the starting bombs. */
    public void resetArsenal(int startingBombs) {
        arsenal = new PlayerArsenal(startingBombs);
    }

    /** New level: the weapons handed on from the previous level instead of a fresh arsenal. */
    public void inheritArsenal(PlayerArsenal inherited) {
        arsenal = inherited;
    }

    /** Grenades, fat bombs, airstrikes and the infantry strike on their way (shared rules). */
    public main.game.maze.game.strike.StrikeWeapons strikes() {
        return strikes;
    }

    /** The boosts bought in the shop for the level being played (longer shields, stronger bombs). */
    public main.game.maze.game.level.LevelBoosts boosts() {
        return boosts;
    }

    public void setBoosts(main.game.maze.game.level.LevelBoosts boosts) {
        this.boosts = boosts == null ? main.game.maze.game.level.LevelBoosts.NONE : boosts;
    }

    public PowerShield shield() {
        return shield;
    }

    /** The player's lives this game (respawn at the start while lives are left). */
    public PlayerLives lives() {
        return lives;
    }

    /** The arrow over the player's head and the flash after a heart is picked up. */
    public HealFeedback healFeedback() {
        return healFeedback;
    }

    /** Who killed what this game (kill points per player). */
    public KillLedger killLedger() {
        return killLedger;
    }

    /** Short combat effects playing now (laser beams, swings, hit reactions, pickup sparkles). */
    public List<ActiveEffect> effects() {
        return effects;
    }

    /** Dead enemies whose shots are still in the air; updated until their projectiles land. */
    public RetiredShooters<GdxEnemyRuntime> retiredShooters() {
        return retiredShooters;
    }

    /** Remains of killed enemies, kept until each enemy respawns or the level restarts. */
    public RemainsLedger<EnemySpawn, RemainsVisual> remains() {
        return remains;
    }

    /** Everything explosive to draw this frame: wall rubble, enemy bursts, then player blasts on top. */
    public List<ExplosionVisual> explosions() {
        List<ExplosionVisual> all = new ArrayList<>(wallRubble.size() + enemyBursts.size() + fireBlasts.size());
        for (ActiveWallRubble active : wallRubble) {
            all.add(active.visual());
        }
        all.addAll(enemyBursts);
        all.addAll(fireBlasts);
        for (ActiveEffect effect : effects) {
            all.add(effect.visual());
        }
        return all;
    }

    /** A playing combat effect and how long it has been running. */
    public static final class ActiveEffect {
        private final PuffEffect effect;
        private float elapsedSeconds;

        public ActiveEffect(PuffEffect effect) {
            this.effect = effect;
        }

        public void tick(float dt) {
            elapsedSeconds += Math.max(0f, dt);
        }

        public boolean finished() {
            return elapsedSeconds >= effect.lifetimeSeconds();
        }

        public PuffEffectVisual visual() {
            return new PuffEffectVisual(effect, (float) Math.min(1.0, elapsedSeconds / effect.lifetimeSeconds()));
        }
    }

    /** A collapsing wall and how long it has been falling. */
    public static final class ActiveWallRubble {
        private final WallRubble rubble;
        private float elapsedSeconds;

        public ActiveWallRubble(WallRubble rubble) {
            this.rubble = rubble;
        }

        public void tick(float dt) {
            elapsedSeconds += Math.max(0f, dt);
        }

        public boolean finished() {
            return elapsedSeconds >= WallRubble.LIFETIME_SECONDS;
        }

        public WallRubbleVisual visual() {
            return new WallRubbleVisual(rubble, (float) Math.min(1.0, elapsedSeconds / WallRubble.LIFETIME_SECONDS));
        }
    }

    public List<Score> highScoreRows() {
        return highScoreRows;
    }
}
