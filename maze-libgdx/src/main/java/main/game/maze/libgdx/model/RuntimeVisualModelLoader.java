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

import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
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
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import main.game.maze.opponents.util.OpponentsValidator;
import main.game.maze.service.DifficultyService;

/**
 * Builds a backend neutral visual runtime model by reading the same EMF/XMI
 * sources used by the JavaFX game runtime.
 */
public final class RuntimeVisualModelLoader {

    private static final Logger LOGGER = Logger.getLogger(RuntimeVisualModelLoader.class.getName());
    private static final float GOAL_SIZE = 50f;
    private static final int SPAWN_MARGIN = 30;
    private static final int MAX_SPAWN_ATTEMPTS_PER_ENEMY = 120;
    private final MazeVisualStyleConfig style = loadStyle();

    public RuntimeVisualModel load(float widthPx, float heightPx) {
        return load(null, widthPx, heightPx);
    }

    public RuntimeVisualModel load(MazeArena arena) {
        if (arena == null) {
            throw new IllegalArgumentException("arena must not be null");
        }
        return load(arena, arena.widthPx(), arena.heightPx());
    }

    private RuntimeVisualModel load(MazeArena arena, float widthPx, float heightPx) {
        PlayerConfig playerConfig = loadPlayerConfig();
        Difficulty difficulty = loadCurrentDifficulty();
        WallRegistry.WallDefinition wallDefinition = resolveWallDefinition(difficulty, style);
        List<EnemySpawn> enemies = loadEnemySpawns(arena, widthPx, heightPx, difficulty);

        return new RuntimeVisualModel(
                normalizePath(playerConfig.imageBase()),
                (float) Math.max(1.0d, playerConfig.speed()),
                StageConstants.PlayerCharacterXYSize,
                style.backgroundImageForDifficultyName(difficultyName(difficulty)),
                wallDefinition != null ? normalizePath(wallDefinition.baseImage) : "/main/game/maze/woodWall.png",
                normalizePath(style.goalImagePath()),
                widthPx * 0.5f,
                heightPx * 0.5f,
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
            LOGGER.log(Level.WARNING, "Falling back to default player config", ex);
            return PlayerConfig.defaults();
        }
    }

    private Difficulty loadCurrentDifficulty() {
        try {
            return new DifficultyService().getCurrent();
        } catch (RuntimeException ex) {
            LOGGER.log(Level.WARNING, "Failed to read current difficulty, using defaults", ex);
            return null;
        }
    }

    private List<EnemySpawn> loadEnemySpawns(MazeArena arena, float widthPx, float heightPx, Difficulty difficulty) {
        OpponentModel model;
        try {
            model = new XmiRulesLoader().loadOpponentModelFromClasspath(OpponentConstants.ZombieModelPath);
            validateOpponentModel(model);
        } catch (RuntimeException ex) {
            LOGGER.log(Level.WARNING, "Failed to load opponent model, spawning no enemies", ex);
            return List.of();
        }

        Map<EnemyTypes, Integer> caps = capsFromDifficulty(difficulty);
        Map<EnemyTypes, List<CharacterType>> availableByType = buildAvailableByType(model);

        List<EnemySpawn> out = new ArrayList<>();
        Random random = new Random(1337L);
        float threatBudget = difficulty != null ? Math.max(1f, difficulty.getMaxThreat()) : Float.MAX_VALUE;
        float usedThreat = 0f;
        float startX = arena != null ? arena.startX() : widthPx * 0.5f;
        float startY = arena != null ? arena.startY() : heightPx * 0.5f;
        float goalX = arena != null ? arena.goalX() : widthPx * 0.5f;
        float goalY = arena != null ? arena.goalY() : heightPx * 0.5f;

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
                    accepted = new EnemySpawn(defaultIfBlank(picked.getImageBase(), fallbackEnemyImage(type)), x, y, size, threat);
                    break;
                }
                if (accepted != null) {
                    out.add(accepted);
                }
            }
        }

        return List.copyOf(out);
    }

    private static float randomInRange(Random random, float min, float max) {
        if (max <= min) {
            return min;
        }
        return min + random.nextFloat() * (max - min);
    }

    private static Map<EnemyTypes, Integer> capsFromDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            return defaultCaps();
        }
        Map<EnemyTypes, Integer> caps = new EnumMap<>(EnemyTypes.class);
        difficulty.getEnemyMaxCount().forEach(e -> caps.put(e.getType(), Math.max(0, e.getMaxCount())));
        if (caps.isEmpty()) {
            return defaultCaps();
        }
        return caps;
    }

    private static Map<EnemyTypes, Integer> defaultCaps() {
        Map<EnemyTypes, Integer> caps = new EnumMap<>(EnemyTypes.class);
        caps.put(EnemyTypes.ZOMBIE, 2);
        caps.put(EnemyTypes.GHOST, 2);
        caps.put(EnemyTypes.PUMPKINBOMBER, 1);
        return caps;
    }

    private static Map<EnemyTypes, List<CharacterType>> buildAvailableByType(OpponentModel model) {
        Map<EnemyTypes, List<CharacterType>> out = new EnumMap<>(EnemyTypes.class);
        for (CharacterType ct : model.getCharacterTypes()) {
            if (!ct.isEnabled()) {
                continue;
            }
            EnemyTypes type = toEnemyType(ct);
            if (type == null) {
                continue;
            }
            out.computeIfAbsent(type, ignored -> new ArrayList<>()).add(ct);
        }
        return out;
    }

    private static EnemyTypes toEnemyType(CharacterType ct) {
        if (ct instanceof Zombie) {
            return EnemyTypes.ZOMBIE;
        }
        if (ct instanceof Ghost) {
            return EnemyTypes.GHOST;
        }
        if (ct instanceof PumpkinBomber) {
            return EnemyTypes.PUMPKINBOMBER;
        }
        return null;
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
            case ZOMBIE -> "/main/game/maze/zombie1-right.png";
            case GHOST -> "/main/game/maze/ghost1.png";
            case PUMPKINBOMBER -> "/main/game/maze/pumpkinbomber.png";
        };
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
        float half = size * 0.5f;
        float border = Math.max(half + 1f, SPAWN_MARGIN * 0.25f);
        if (x < border || x > widthPx - border || y < border || y > heightPx - border) {
            return false;
        }

        float exclusion = Math.max(size, 42f);
        if (distanceSquared(x, y, startX, startY) <= exclusion * exclusion) {
            return false;
        }
        if (distanceSquared(x, y, goalX, goalY) <= exclusion * exclusion) {
            return false;
        }

        if (arena == null) {
            return true;
        }
        float clearance = Math.max(2f, half * 0.35f);
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

    private WallRegistry.WallDefinition resolveWallDefinition(Difficulty difficulty, MazeVisualStyleConfig visualStyle) {
        String wallId = visualStyle.wallTypeIdForDifficultyName(difficultyName(difficulty));
        WallRegistry.WallDefinition byStyle = WallRegistry.get(wallId);
        if (byStyle != null) {
            return byStyle;
        }
        return WallRegistry.get(MazeVisualStyleConfig.DEFAULT.wallTypeIdForDifficultyName(difficultyName(difficulty)));
    }

    private static String difficultyName(Difficulty difficulty) {
        if (difficulty == null) {
            return "";
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
                LOGGER.log(Level.WARNING, "Failed to load visual style config, using defaults", fallbackEx);
                return MazeVisualStyleConfig.DEFAULT;
            }
        }
    }

    private static void validateOpponentModel(OpponentModel model) {
        try {
            BasicDiagnostic diagnostics = new BasicDiagnostic();
            boolean valid = OpponentsValidator.INSTANCE.validate(model, diagnostics, null);
            if (!valid) {
                LOGGER.log(Level.WARNING, "Opponent model OCL validation reported diagnostics: {0}", diagnostics.getMessage());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Opponent model OCL validation threw at runtime, continuing with model load", ex);
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
