package main.game.maze.opponents.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;

/**
 * Shared spawn-planning helpers used by both the JavaFX runtime
 * (OpponentRuntimeFactory) and the libGDX runtime (RuntimeVisualModelLoader)
 * so the two frontends always agree on caps, available pools, and damage math.
 *
 * <p>This is the home of the rules enforced by GR-4, GR-6, and GR-10 in
 * game-rules.md. Per-frontend reimplementations of these calculations are
 * forbidden.
 */
public final class EnemySpawnPlanner {

    private EnemySpawnPlanner() {
    }

    /**
     * Build the per-type enemy cap map exactly as the rules document
     * requires: cap[type] = max(0, difficulty.getMaxCount(type)).
     * Returns an empty map when {@code difficulty} is null.
     */
    public static Map<EnemyTypes, Integer> capsFromDifficulty(Difficulty difficulty) {
        Map<EnemyTypes, Integer> caps = new EnumMap<>(EnemyTypes.class);
        if (difficulty == null) {
            return caps;
        }
        difficulty.getEnemyMaxCount().forEach(e -> caps.put(e.getType(), Math.max(0, e.getMaxCount())));
        return caps;
    }

    /**
     * Returns the enabled CharacterType instances of the given model grouped
     * by EnemyTypes. If {@code sortByAscendingThreat} is true, each list is
     * sorted by {@link CharacterType#getEffectiveThreat()} ascending.
     */
    public static Map<EnemyTypes, List<CharacterType>> availableEnabledByType(
            OpponentModel model, boolean sortByAscendingThreat) {
        Map<EnemyTypes, List<CharacterType>> out = new EnumMap<>(EnemyTypes.class);
        if (model == null) {
            return out;
        }
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
        if (sortByAscendingThreat) {
            Comparator<CharacterType> byThreat = Comparator.comparingDouble(CharacterType::getEffectiveThreat);
            for (List<CharacterType> list : out.values()) {
                list.sort(byThreat);
            }
        }
        return out;
    }

    /**
     * Map an EMF CharacterType instance to its EnemyTypes enum.
     */
    public static EnemyTypes toEnemyType(CharacterType ct) {
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

    /**
     * Apply the difficulty damage multiplier to a base attack damage value.
     * Both frontends MUST use this to keep per-tick damage identical.
     */
    public static int applyDamageMultiplier(int baseDamage, double multiplier) {
        int safeBase = Math.max(0, baseDamage);
        double safeMul = Math.max(0d, multiplier);
        return Math.max(0, (int) Math.round(safeBase * safeMul));
    }
}
