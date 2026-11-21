package main.game.maze.libgdx.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.emf.common.util.BasicDiagnostic;

import main.game.maze.game.score.KillScoring;
import main.game.maze.service.MortalityRules;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.characters.CollisionDamage;
import main.game.maze.config.model.PlayerConfig;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.OpponentConstants;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.generated.WallRegistry;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.game.level.DifficultyTier;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.LevelPlacement;
import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.game.level.TeleportGhostSpec;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.ProjectileType;
import main.game.maze.opponents.Zombie;
import main.game.maze.opponents.util.EnemySpawnPlanner;
import main.game.maze.opponents.util.OpponentsValidator;
import main.game.maze.service.DifficultyService;

/**
 * Builds a backend neutral visual runtime model by reading the same EMF/XMI
 * sources used by the JavaFX game runtime.
 */
public final class RuntimeVisualModelLoader {

    private static final Logger LOGGER = Logger.getLogger(RuntimeVisualModelLoader.class.getName());
    private static final String ARENA_REQUIRED_MESSAGE = "arena must not be null";
    private static final String OPPONENT_MODEL_LOAD_FAILED_MESSAGE = "Failed to load opponent model, spawning no enemies";
    private static final String PLAYER_CONFIG_FALLBACK_MESSAGE = "Falling back to default player config";
    private static final String DIFFICULTY_LOAD_FALLBACK_MESSAGE = "Failed to read current difficulty, using defaults";
    private static final String STYLE_LOAD_FALLBACK_MESSAGE = "Failed to load visual style config, using defaults";
    private static final String OCL_VALIDATION_WARNING_MESSAGE = "Opponent model OCL validation reported diagnostics: {0}";
    private static final String OCL_VALIDATION_UNAVAILABLE_MESSAGE = "Opponent model OCL validation unavailable at runtime, continuing with model load";
    private static final String DEFAULT_WALL_IMAGE = "/main/game/maze/woodWall.png";
    private static final String DEFAULT_ZOMBIE_IMAGE = "/main/game/maze/zombie1-right.png";
    private static final String DEFAULT_GHOST_IMAGE = "/main/game/maze/ghost1.png";
    private static final String DEFAULT_PUMPKIN_IMAGE = "/main/game/maze/pumpkinbomber.png";
    private static final String DEFAULT_ZOMBIE_TOUCH_SOUND = "/main/game/maze/zombieScream.mp3";
    private static final String EMPTY_TEXT = "";
    private static final String GENERATED_ENEMY_ID_SEPARATOR = "_";
    private static final double MIN_PLAYER_SPEED = 1.0d;
    private static final float CENTER_RATIO = 0.5f;
    /** Danger of a sergeant, for the kill points. */
    private static final float SERGEANT_THREAT = 5f;
    /** Danger of a wall builder, for the kill points: it cannot hurt anyone. */
    private static final float WALL_BUILDER_THREAT = 1f;
    /** Danger of a guard tower, for the kill points. */
    private static final float GUARD_TOWER_THREAT = 12f;
    /** Side of a guard tower sprite on the board, in pixels: bigger than any walking enemy. */
    static final int GUARD_TOWER_SIZE = 56;
    /** Danger of a portal ghost, for the kill points (it never hurts). */
    private static final float PORTAL_GHOST_THREAT = 3f;
    private static final long ENEMY_SPAWN_RANDOM_SEED = 1337L;
    private static final float MIN_THREAT_BUDGET = 1f;
    // MIN_DAMAGE_MULTIPLIER removed: EnemySpawnPlanner.applyDamageMultiplier already clamps to 0.
    private static final int DEFAULT_OPPONENT_MAX_THREAT = 20;
    private static final int DEFAULT_ZOMBIE_CAP = 2;
    private static final int DEFAULT_GHOST_CAP = 2;
    private static final int DEFAULT_PUMPKIN_CAP = 1;
    private static final float MIN_BORDER_PADDING = 1f;
    private static final float SPAWN_MARGIN_BORDER_RATIO = 0.25f;
    private static final float MIN_EXCLUSION_RADIUS = 42f;
    private static final float MIN_WALL_CLEARANCE = 2f;
    private static final float WALL_CLEARANCE_SIZE_RATIO = 0.35f;
    private static final float GOAL_SIZE = 50f;
    private static final int SPAWN_MARGIN = 30;
    private static final int MAX_SPAWN_ATTEMPTS_PER_ENEMY = 120;
    private final MazeVisualStyleConfig style = loadStyle();
    /** The level being started: its threat capacity and sergeants apply to the next {@code load}. */
    private volatile LevelSpec level;

    /** Sets the level whose threat capacity and sergeants the next {@code load} uses (null: the classic level 1). */
    public void useLevel(LevelSpec level) {
        this.level = level;
    }

    public RuntimeVisualModel load(float widthPx, float heightPx) {
        return load(null, widthPx, heightPx, null);
    }

    public RuntimeVisualModel load(float widthPx, float heightPx, Difficulty difficulty) {
        return load(null, widthPx, heightPx, difficulty);
    }

    public RuntimeVisualModel load(MazeArena arena) {
        if (arena == null) {
            throw new IllegalArgumentException(ARENA_REQUIRED_MESSAGE);
        }
        return load(arena, arena.widthPx(), arena.heightPx(), null);
    }

    public RuntimeVisualModel load(MazeArena arena, Difficulty difficulty) {
        if (arena == null) {
            throw new IllegalArgumentException(ARENA_REQUIRED_MESSAGE);
        }
        return load(arena, arena.widthPx(), arena.heightPx(), difficulty);
    }

    private RuntimeVisualModel load(MazeArena arena, float widthPx, float heightPx, Difficulty selectedDifficulty) {
        PlayerConfig playerConfig = loadPlayerConfig();
        Difficulty difficulty = selectedDifficulty != null ? selectedDifficulty : loadCurrentDifficulty();
        WallRegistry.WallDefinition wallDefinition = resolveWallDefinition(difficulty, style);
        List<EnemySpawn> enemies = loadEnemySpawns(arena, widthPx, heightPx, difficulty);

        return new RuntimeVisualModel(
                normalizePath(playerConfig.imageBase()),
            normalizePath(playerConfig.imageDeath()),
                (float) Math.max(MIN_PLAYER_SPEED, playerConfig.speed()),
                StageConstants.PlayerCharacterXYSize,
            Math.max(1, playerConfig.health()),
                Math.max(0, playerConfig.bombCount()),
                Math.max(1, playerConfig.bombDamage()),
                style.backgroundImageForDifficultyName(difficultyName(difficulty)),
                wallDefinition != null ? normalizePath(wallDefinition.baseImage) : DEFAULT_WALL_IMAGE,
                normalizePath(style.goalImagePath()),
                widthPx * CENTER_RATIO,
                heightPx * CENTER_RATIO,
                GOAL_SIZE,
                enemies);
    }

    private PlayerConfig loadPlayerConfig() {
        XmiRulesLoader loader = new XmiRulesLoader();
        try {
            return loader.loadPlayerConfigFromClasspath(
                    PlayerConstants.PlayerModelPath,
                    PlayerConstants.PlayerModelEcorePath);
        } catch (RuntimeException ex) {
            LOGGER.log(Level.WARNING, PLAYER_CONFIG_FALLBACK_MESSAGE, ex);
            return PlayerConfig.defaults();
        }
    }

    private Difficulty loadCurrentDifficulty() {
        try {
            return new DifficultyService().getCurrent();
        } catch (RuntimeException ex) {
            LOGGER.log(Level.WARNING, DIFFICULTY_LOAD_FALLBACK_MESSAGE, ex);
            return null;
        }
    }

    private List<EnemySpawn> loadEnemySpawns(MazeArena arena, float widthPx, float heightPx, Difficulty difficulty) {
        OpponentModel model;
        try {
            model = new XmiRulesLoader().loadOpponentModelFromClasspath(OpponentConstants.ZombieModelPath);
            validateOpponentModel(model);
        } catch (RuntimeException ex) {
            LOGGER.log(Level.WARNING, OPPONENT_MODEL_LOAD_FAILED_MESSAGE, ex);
            return List.of();
        }

        Map<EnemyTypes, Integer> caps = capsFromDifficulty(difficulty);
        Map<EnemyTypes, List<CharacterType>> availableByType = EnemySpawnPlanner.availableEnabledByType(model, false);

        List<EnemySpawn> out = new ArrayList<>();
        Random random = new Random(ENEMY_SPAWN_RANDOM_SEED);
        LevelSpec activeLevel = level;
        int maxThreat = difficulty == null ? 0
                : activeLevel != null ? activeLevel.maxThreat(difficulty.getMaxThreat()) : difficulty.getMaxThreat();
        float threatBudget = difficulty != null ? Math.max(MIN_THREAT_BUDGET, maxThreat) : Float.MAX_VALUE;
        double damageMultiplier = difficulty != null ? Math.max(0d, difficulty.getMonstersDamageMultiplier()) : 1d;
        double speedMultiplier = difficulty != null ? difficulty.getMonstersMovementSpeedMultiplier() : 1d;
        boolean instantDeath = difficulty != null && difficulty.isInstantDeath();
        float usedThreat = 0f;
        int aggressiveAssigned = 0;
        float startX = arena != null ? arena.startX() : widthPx * CENTER_RATIO;
        float startY = arena != null ? arena.startY() : heightPx * CENTER_RATIO;
        float goalX = arena != null ? arena.goalX() : widthPx * CENTER_RATIO;
        float goalY = arena != null ? arena.goalY() : heightPx * CENTER_RATIO;

        for (Map.Entry<EnemyTypes, Integer> entry : caps.entrySet()) {
            EnemyTypes type = entry.getKey();
            int count = Math.max(0, entry.getValue());
            List<CharacterType> candidates = availableByType.get(type);
            if (candidates == null || candidates.isEmpty() || count == 0) {
                continue;
            }

            for (int i = 0; i < count; i++) {
                CharacterType picked = candidates.get(i % candidates.size());
                float threat = (float) Math.max(1d, picked.getEffectiveThreat());
                if (usedThreat + threat > threatBudget) {
                    break;
                }
                usedThreat += threat;

                float size = sizeForType(type);
                EnemySpawn accepted = null;
                for (int attempt = 0; attempt < MAX_SPAWN_ATTEMPTS_PER_ENEMY; attempt++) {
                    float x = randomInRange(random, SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1f, widthPx - SPAWN_MARGIN));
                    float y = randomInRange(random, SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1f, heightPx - SPAWN_MARGIN));
                    if (!isValidSpawn(arena, x, y, size, widthPx, heightPx, startX, startY, goalX, goalY)) {
                        continue;
                    }
                    int baseDamage = Math.max(0, attackDamageFor(picked));
                    int attackDamage = instantDeath
                            ? Integer.MAX_VALUE
                            : EnemySpawnPlanner.applyDamageMultiplier(baseDamage, damageMultiplier);
                    float spawnSpeed = (float) EnemySpawnPlanner.applySpeedMultiplier(picked.getSpeed(), speedMultiplier);
                        var runtimeBehavior = EnemySpawnPlanner.resolveRuntimeBehaviorWithAggressiveCap(
                                picked.getBehavior(), difficulty, aggressiveAssigned, random);
                        if (runtimeBehavior == main.game.maze.opponents.BehaviorType.AGGRESSIVE) {
                            aggressiveAssigned++;
                        }
                    int infectionLevel = infectionLevelFor(picked);
                    String touchSound = touchSoundFor(picked);
                    float effectiveThreat = instantDeath
                            ? (float) (CollisionDamage.INSTANT_KILL_THREAT_THRESHOLD + 1d)
                            : threat;
                    String baseImage = defaultIfBlank(picked.getImageBase(), fallbackEnemyImage(type));
                    accepted = new EnemySpawn(
                            defaultIfBlank(picked.getId(), type.name().toLowerCase(Locale.ROOT) + GENERATED_ENEMY_ID_SEPARATOR + i),
                            baseImage,
                            x,
                            y,
                            size,
                            effectiveThreat,
                            attackDamage,
                            infectionLevel,
                            touchSound,
                            runtimeBehavior,
                            spawnSpeed,
                            nonTangibilityEnergyFor(picked),
                            visibilityLevelFor(picked),
                            projectileTypeFor(picked),
                            splashRadiusFor(picked),
                            arcHeightFor(picked),
                            attackRangeFor(picked),
                            attackCooldownFor(picked),
                            projectileSpeedFor(picked),
                            resurrectionTimeMsFor(picked),
                            maxHitPointsFor(picked),
                            animationSpecFor(picked, baseImage),
                            KillScoring.pointsFor(picked));
                    break;
                }
                if (accepted != null) {
                    out.add(accepted);
                }
            }
        }

        out.addAll(sergeantSpawns(activeLevel, arena, difficulty, widthPx, heightPx));
        out.addAll(portalGhostSpawns(activeLevel, arena, difficulty, widthPx, heightPx, startX, startY));
        out.addAll(guardTowerSpawns(activeLevel, arena, difficulty, heightPx, startX, startY));
        out.addAll(wallBuilderSpawns(activeLevel, arena, difficulty, widthPx, heightPx));
        return List.copyOf(out);
    }

    /**
     * The level's zombie sergeants: as many as the difficulty says, on free corridor points near the heart
     * (only in a generated maze, which has the world to find them in). Screen space is y-up.
     */
    private static List<EnemySpawn> sergeantSpawns(LevelSpec level, MazeArena arena, Difficulty difficulty,
                                                   float widthPx, float heightPx) {
        if (level == null || !level.hasSergeants() || !(arena instanceof RealMaze realMaze)) {
            return List.of();
        }
        SergeantSpec spec = level.sergeant();
        DifficultyTier tier = DifficultyTier.fromDifficultyName(difficulty == null ? null : difficulty.eClass().getName());
        List<Point2D> posts = LevelPlacement.sergeantPosts(level, tier, realMaze.sourceWorld(),
                widthPx * CENTER_RATIO, heightPx * CENTER_RATIO);
        List<EnemySpawn> spawns = new ArrayList<>();
        for (int index = 0; index < posts.size(); index++) {
            Point2D post = posts.get(index);
            spawns.add(new EnemySpawn(SergeantSpec.SPAWN_ID_PREFIX + GENERATED_ENEMY_ID_SEPARATOR + index,
                    spec.sprite(), (float) post.getX(), heightPx - (float) post.getY(),
                    StageConstants.ZombieCharacterXYSize, SERGEANT_THREAT, 0, 0, EMPTY_TEXT,
                    BehaviorType.PASSIVE, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                    EnemySpawn.DEFAULT_PROJECTILE_TYPE, EnemySpawn.DEFAULT_SPLASH_RADIUS, EnemySpawn.DEFAULT_ARC_HEIGHT,
                    EnemySpawn.DEFAULT_ATTACK_RANGE, EnemySpawn.DEFAULT_ATTACK_COOLDOWN_MS,
                    EnemySpawn.DEFAULT_PROJECTILE_SPEED, 0, spec.health(), EnemyAnimationSpec.defaults(),
                    KillScoring.pointsForThreat(SERGEANT_THREAT)));
        }
        return spawns;
    }

    /**
     * The level's teleporting ghosts: as many as the difficulty says, at random free corridor points away from
     * the start (only in a generated maze). Screen space is y-up.
     */
    private static List<EnemySpawn> portalGhostSpawns(LevelSpec level, MazeArena arena, Difficulty difficulty,
                                                      float widthPx, float heightPx, float startX, float startY) {
        if (level == null || !level.hasTeleportGhosts() || !(arena instanceof RealMaze realMaze)) {
            return List.of();
        }
        TeleportGhostSpec ghost = level.teleportGhost();
        DifficultyTier tier = DifficultyTier.fromDifficultyName(difficulty == null ? null : difficulty.eClass().getName());
        // the shared placement works in world space (y-down); this screen's y axis points up
        List<Point2D> spots = LevelPlacement.portalGhostSpots(level, tier, realMaze.sourceWorld(), startX, heightPx - startY);
        List<EnemySpawn> spawns = new ArrayList<>();
        for (int index = 0; index < spots.size(); index++) {
            Point2D spot = spots.get(index);
            spawns.add(new EnemySpawn(TeleportGhostSpec.SPAWN_ID_PREFIX + GENERATED_ENEMY_ID_SEPARATOR + index,
                    ghost.sprite(), (float) spot.getX(), heightPx - (float) spot.getY(),
                    StageConstants.GhostCharacterXYSize, PORTAL_GHOST_THREAT, 0, 0, EMPTY_TEXT,
                    BehaviorType.WANDER, (float) ghost.speed(), 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                    EnemySpawn.DEFAULT_PROJECTILE_TYPE, EnemySpawn.DEFAULT_SPLASH_RADIUS, EnemySpawn.DEFAULT_ARC_HEIGHT,
                    EnemySpawn.DEFAULT_ATTACK_RANGE, EnemySpawn.DEFAULT_ATTACK_COOLDOWN_MS,
                    EnemySpawn.DEFAULT_PROJECTILE_SPEED, 0, ghost.health(), EnemyAnimationSpec.defaults(),
                    KillScoring.pointsForThreat(PORTAL_GHOST_THREAT)));
        }
        return spawns;
    }

    /**
     * The level's guard towers: as many as the difficulty says, on free corridor points in the outer part of the
     * map, away from the start (only in a generated maze). Screen space is y-up.
     */
    private static List<EnemySpawn> guardTowerSpawns(LevelSpec level, MazeArena arena, Difficulty difficulty,
                                                     float heightPx, float startX, float startY) {
        if (level == null || !level.hasTowers() || !(arena instanceof RealMaze realMaze)) {
            return List.of();
        }
        GuardTowerSpec tower = level.guardTower();
        DifficultyTier tier = DifficultyTier.fromDifficultyName(difficulty == null ? null : difficulty.eClass().getName());
        List<Point2D> posts = LevelPlacement.towerPosts(level, tier, realMaze.sourceWorld(), startX, heightPx - startY);
        EnemyAnimationSpec looks = new EnemyAnimationSpec(null, null, null, null, 1, 1.0f, true);   // damaged looks on
        List<EnemySpawn> spawns = new ArrayList<>();
        for (int index = 0; index < posts.size(); index++) {
            Point2D post = posts.get(index);
            spawns.add(new EnemySpawn(GuardTowerSpec.SPAWN_ID_PREFIX + GENERATED_ENEMY_ID_SEPARATOR + index,
                    tower.sprite(), (float) post.getX(), heightPx - (float) post.getY(),
                    GUARD_TOWER_SIZE, GUARD_TOWER_THREAT, 0, 0, EMPTY_TEXT,
                    BehaviorType.PASSIVE, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                    EnemySpawn.DEFAULT_PROJECTILE_TYPE, EnemySpawn.DEFAULT_SPLASH_RADIUS, EnemySpawn.DEFAULT_ARC_HEIGHT,
                    EnemySpawn.DEFAULT_ATTACK_RANGE, EnemySpawn.DEFAULT_ATTACK_COOLDOWN_MS,
                    EnemySpawn.DEFAULT_PROJECTILE_SPEED, 0, tower.health(), looks,
                    KillScoring.pointsForThreat(GUARD_TOWER_THREAT)));
        }
        return spawns;
    }

    /**
     * The level's wall builders: as many as the difficulty says, on free corridor points in the area round the heart
     * (only in a generated maze). Screen space is y-up.
     */
    private static List<EnemySpawn> wallBuilderSpawns(LevelSpec level, MazeArena arena, Difficulty difficulty,
                                                      float widthPx, float heightPx) {
        if (level == null || !level.hasBuilders() || !(arena instanceof RealMaze realMaze)) {
            return List.of();
        }
        WallBuilderSpec builder = level.wallBuilder();
        DifficultyTier tier = DifficultyTier.fromDifficultyName(difficulty == null ? null : difficulty.eClass().getName());
        List<Point2D> spots = LevelPlacement.builderSpots(level, tier, realMaze.sourceWorld(),
                widthPx * CENTER_RATIO, heightPx * CENTER_RATIO);
        List<EnemySpawn> spawns = new ArrayList<>();
        for (int index = 0; index < spots.size(); index++) {
            Point2D spot = spots.get(index);
            // no attack damage, no resurrection: a builder cannot harm the player and stays dead when killed
            spawns.add(new EnemySpawn(WallBuilderSpec.SPAWN_ID_PREFIX + GENERATED_ENEMY_ID_SEPARATOR + index,
                    builder.sprite(), (float) spot.getX(), heightPx - (float) spot.getY(),
                    StageConstants.ZombieCharacterXYSize, WALL_BUILDER_THREAT, 0, 0, EMPTY_TEXT,
                    BehaviorType.PASSIVE, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                    EnemySpawn.DEFAULT_PROJECTILE_TYPE, EnemySpawn.DEFAULT_SPLASH_RADIUS, EnemySpawn.DEFAULT_ARC_HEIGHT,
                    EnemySpawn.DEFAULT_ATTACK_RANGE, EnemySpawn.DEFAULT_ATTACK_COOLDOWN_MS,
                    EnemySpawn.DEFAULT_PROJECTILE_SPEED, 0, builder.health(), EnemyAnimationSpec.defaults(),
                    KillScoring.pointsForThreat(WALL_BUILDER_THREAT)));
        }
        return spawns;
    }

    private static float randomInRange(Random random, float min, float max) {
        if (max <= min) {
            return min;
        }
        return min + random.nextFloat() * (max - min);
    }

    private static Map<EnemyTypes, Integer> capsFromDifficulty(Difficulty difficulty) {
        Map<EnemyTypes, Integer> caps = EnemySpawnPlanner.capsFromDifficulty(difficulty);
        if (caps.isEmpty()) {
            return defaultCaps();
        }
        return caps;
    }

    private static Map<EnemyTypes, Integer> defaultCaps() {
        Map<EnemyTypes, Integer> caps = new EnumMap<>(EnemyTypes.class);
        caps.put(EnemyTypes.ZOMBIE, DEFAULT_ZOMBIE_CAP);
        caps.put(EnemyTypes.GHOST, DEFAULT_GHOST_CAP);
        caps.put(EnemyTypes.PUMPKINBOMBER, DEFAULT_PUMPKIN_CAP);
        return caps;
    }

    private static Map<EnemyTypes, List<CharacterType>> buildAvailableByType(OpponentModel model) {
        return EnemySpawnPlanner.availableEnabledByType(model, false);
    }

    private static EnemyTypes toEnemyType(CharacterType ct) {
        return EnemySpawnPlanner.toEnemyType(ct);
    }

    private static float sizeForType(EnemyTypes type) {
        return switch (type) {
            case ZOMBIE -> StageConstants.ZombieCharacterXYSize;
            case GHOST -> StageConstants.GhostCharacterXYSize;
            case PUMPKINBOMBER -> StageConstants.PumpkinBomberCharacterXYSize;
        };
    }

    private static String fallbackEnemyImage(EnemyTypes type) {
        return switch (type) {
            case ZOMBIE -> DEFAULT_ZOMBIE_IMAGE;
            case GHOST -> DEFAULT_GHOST_IMAGE;
            case PUMPKINBOMBER -> DEFAULT_PUMPKIN_IMAGE;
        };
    }

    private static int attackDamageFor(CharacterType type) {
        if (type instanceof Zombie zombie) {
            return zombie.getAttackDamage();
        }
        if (type instanceof Ghost ghost) {
            return ghost.getAttackDamage();
        }
        if (type instanceof PumpkinBomber pumpkinBomber) {
            return pumpkinBomber.getAttackDamage();
        }
        return 0;
    }

    private static int infectionLevelFor(CharacterType type) {
        if (type instanceof Zombie zombie) {
            return zombie.getInfectionLevel();
        }
        return 0;
    }

    private static double nonTangibilityEnergyFor(CharacterType type) {
        if (type instanceof Ghost ghost) {
            return ghost.getNonTangibilityEnergy();
        }
        return 0.0;
    }

    private static int visibilityLevelFor(CharacterType type) {
        if (type instanceof Ghost ghost) {
            return ghost.getVisibilityLevel();
        }
        return EnemySpawn.DEFAULT_VISIBILITY_LEVEL;
    }

    private static ProjectileType projectileTypeFor(CharacterType type) {
        if (type instanceof PumpkinBomber pumpkinBomber && pumpkinBomber.getProjectileType() != null) {
            return pumpkinBomber.getProjectileType();
        }
        return EnemySpawn.DEFAULT_PROJECTILE_TYPE;
    }

    private static float splashRadiusFor(CharacterType type) {
        if (type instanceof PumpkinBomber pumpkinBomber) {
            return (float) Math.max(0d, pumpkinBomber.getSplashRadius());
        }
        return EnemySpawn.DEFAULT_SPLASH_RADIUS;
    }

    private static float arcHeightFor(CharacterType type) {
        if (type instanceof PumpkinBomber pumpkinBomber) {
            return (float) Math.max(0d, pumpkinBomber.getArcHeight());
        }
        return EnemySpawn.DEFAULT_ARC_HEIGHT;
    }

    private static float attackRangeFor(CharacterType type) {
        if (type instanceof PumpkinBomber pumpkinBomber) {
            return (float) Math.max(0d, pumpkinBomber.getAttackRange());
        }
        return EnemySpawn.DEFAULT_ATTACK_RANGE;
    }

    private static int attackCooldownFor(CharacterType type) {
        if (type instanceof PumpkinBomber pumpkinBomber) {
            return Math.max(0, pumpkinBomber.getAttackCooldownMs());
        }
        return EnemySpawn.DEFAULT_ATTACK_COOLDOWN_MS;
    }

    private static float projectileSpeedFor(CharacterType type) {
        if (type instanceof PumpkinBomber pumpkinBomber) {
            return (float) Math.max(0d, pumpkinBomber.getProjectileSpeed());
        }
        return EnemySpawn.DEFAULT_PROJECTILE_SPEED;
    }

    /** Shared with JavaFX: see {@link MortalityRules#resurrectionTimeMs}. */
    private static int resurrectionTimeMsFor(CharacterType type) {
        return MortalityRules.resurrectionTimeMs(type);
    }

    /** Shared with JavaFX: see {@link MortalityRules#maxHitPoints}. */
    private static int maxHitPointsFor(CharacterType type) {
        return MortalityRules.maxHitPoints(type);
    }

    private static String touchSoundFor(CharacterType type) {
        if (type instanceof Zombie zombie) {
            return defaultIfBlank(zombie.getTouchSound(), DEFAULT_ZOMBIE_TOUCH_SOUND);
        }
        return EMPTY_TEXT;
    }

    private static EnemyAnimationSpec animationSpecFor(CharacterType type, String fallbackImage) {
        String left  = defaultIfBlank(type.getImageTurnLeft(),  fallbackImage);
        String right = defaultIfBlank(type.getImageTurnRight(), fallbackImage);
        String up    = defaultIfBlank(type.getImageTurnUp(),    fallbackImage);
        String down  = defaultIfBlank(type.getImageTurnDown(),  fallbackImage);
        int frameCount = Math.max(1, type.getAnimationFrameCount());
        float scale = (float) Math.max(0.01d, type.getSpriteScale());
        boolean damageStatesEnabled = type != null && type.isDamageStatesEnabled();
        return new EnemyAnimationSpec(left, right, up, down, frameCount, scale, damageStatesEnabled);
    }

    private static boolean isValidSpawn(
            MazeArena arena,
            float x,
            float y,
            float size,
            float widthPx,
            float heightPx,
            float startX,
            float startY,
            float goalX,
            float goalY) {
            float half = size * CENTER_RATIO;
            float border = Math.max(half + MIN_BORDER_PADDING, SPAWN_MARGIN * SPAWN_MARGIN_BORDER_RATIO);
        if (x < border || x > widthPx - border || y < border || y > heightPx - border) {
            return false;
        }

            float exclusion = Math.max(size, MIN_EXCLUSION_RADIUS);
        if (distanceSquared(x, y, startX, startY) <= exclusion * exclusion) {
            return false;
        }
        if (distanceSquared(x, y, goalX, goalY) <= exclusion * exclusion) {
            return false;
        }

        if (arena == null) {
            return true;
        }
        float clearance = Math.max(MIN_WALL_CLEARANCE, half * WALL_CLEARANCE_SIZE_RATIO);
        for (WallSegment wall : arena.walls()) {
            if (distanceSquaredToSegment(x, y, wall) <= clearance * clearance) {
                return false;
            }
        }
        return true;
    }

    private static float distanceSquared(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    private static float distanceSquaredToSegment(float px, float py, WallSegment wall) {
        float x1 = wall.x1;
        float y1 = wall.y1;
        float x2 = wall.x2;
        float y2 = wall.y2;
        float dx = x2 - x1;
        float dy = y2 - y1;
        float lenSq = dx * dx + dy * dy;
        if (lenSq <= 0f) {
            return distanceSquared(px, py, x1, y1);
        }
        float t = ((px - x1) * dx + (py - y1) * dy) / lenSq;
        t = Math.max(0f, Math.min(1f, t));
        float cx = x1 + t * dx;
        float cy = y1 + t * dy;
        return distanceSquared(px, py, cx, cy);
    }

    WallRegistry.WallDefinition resolveWallDefinition(Difficulty difficulty, MazeVisualStyleConfig visualStyle) {
        try {
            String wallId = visualStyle.wallTypeIdForDifficultyName(difficultyName(difficulty));
            WallRegistry.WallDefinition byStyle = WallRegistry.get(wallId);
            if (byStyle != null) {
                return byStyle;
            }
            return WallRegistry.get(MazeVisualStyleConfig.DEFAULT.wallTypeIdForDifficultyName(difficultyName(difficulty)));
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            LOGGER.log(Level.SEVERE,
                    "WallRegistry static initializer failed — main.game.maze.walls may be missing from classpath. "
                    + "Falling back to default wall image.", e);
            return null;
        }
    }

    private static String difficultyName(Difficulty difficulty) {
        if (difficulty == null) {
            return EMPTY_TEXT;
        }
        return difficulty.eClass().getName().toLowerCase(Locale.ROOT);
    }

    private static MazeVisualStyleConfig loadStyle() {
        try {
            return new XmiMazeVisualStyleLoader().load();
        } catch (RuntimeException ex) {
            try {
                return new PropertiesMazeVisualStyleLoader().load();
            } catch (RuntimeException fallbackEx) {
                LOGGER.log(Level.WARNING, STYLE_LOAD_FALLBACK_MESSAGE, fallbackEx);
                return MazeVisualStyleConfig.DEFAULT;
            }
        }
    }

    private static void validateOpponentModel(OpponentModel model) {
        try {
            BasicDiagnostic diagnostics = new BasicDiagnostic();
            boolean valid = OpponentsValidator.INSTANCE.validate(model, diagnostics, null);
            if (!valid) {
                LOGGER.log(Level.WARNING, OCL_VALIDATION_WARNING_MESSAGE, diagnostics.getMessage());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, OCL_VALIDATION_UNAVAILABLE_MESSAGE);
        }
    }

    private static String normalizePath(String path) {
        if (path == null || path.isBlank()) {
            return "";
        }
        return path.startsWith("/") ? path : "/" + path;
    }

    private static String defaultIfBlank(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value;
    }
}
