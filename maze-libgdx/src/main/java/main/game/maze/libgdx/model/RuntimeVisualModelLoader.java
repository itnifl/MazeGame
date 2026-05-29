package main.game.maze.libgdx.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.game.maze.config.model.PlayerConfig;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.OpponentConstants;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.generated.WallRegistry;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import main.game.maze.service.DifficultyService;

/**
 * Builds a backend neutral visual runtime model by reading the same EMF/XMI
 * sources used by the JavaFX game runtime.
 */
public final class RuntimeVisualModelLoader {

    private static final Logger LOGGER = Logger.getLogger(RuntimeVisualModelLoader.class.getName());
    private static final String HEART_IMAGE = "/main/game/maze/heart2.png";
    private static final float GOAL_SIZE = 50f;
    private static final int SPAWN_MARGIN = 30;

    public RuntimeVisualModel load(float widthPx, float heightPx) {
        PlayerConfig playerConfig = loadPlayerConfig();
        Difficulty difficulty = loadCurrentDifficulty();
        WallRegistry.WallDefinition wallDefinition = resolveWallDefinition(difficulty);
        List<EnemySpawn> enemies = loadEnemySpawns(widthPx, heightPx, difficulty);

        return new RuntimeVisualModel(
                normalizePath(playerConfig.imageBase()),
                (float) Math.max(1.0d, playerConfig.speed()),
                StageConstants.PlayerCharacterXYSize,
                wallDefinition != null ? normalizePath(wallDefinition.baseImage) : "/main/game/maze/woodWall.png",
                HEART_IMAGE,
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

    private List<EnemySpawn> loadEnemySpawns(float widthPx, float heightPx, Difficulty difficulty) {
        OpponentModel model;
        try {
            model = new XmiRulesLoader().loadOpponentModelFromClasspath(OpponentConstants.ZombieModelPath);
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
                float x = randomInRange(random, SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1f, widthPx - SPAWN_MARGIN));
                float y = randomInRange(random, SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1f, heightPx - SPAWN_MARGIN));
                out.add(new EnemySpawn(defaultIfBlank(picked.getImageBase(), fallbackEnemyImage(type)), x, y, size, threat));
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

    private static WallRegistry.WallDefinition resolveWallDefinition(Difficulty difficulty) {
        if (difficulty == null) {
            return WallRegistry.get("DIRT_BASIC");
        }
        String name = difficulty.eClass().getName().toLowerCase(Locale.ROOT);
        if (name.contains("hard")) {
            return WallRegistry.get("STEEL_SOLID");
        }
        if (name.contains("normal")) {
            return WallRegistry.get("WOOD_BASIC");
        }
        return WallRegistry.get("DIRT_BASIC");
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
