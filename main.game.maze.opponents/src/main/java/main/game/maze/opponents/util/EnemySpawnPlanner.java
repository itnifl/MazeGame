package main.game.maze.opponents.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.opponents.BehaviorType;
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
     *
     * <p>Mirrors the JavaFX clamp in {@code OpponentRuntimeFactory
     * .setCharacterAttributesByDifficulty}: any positive base damage stays at
     * least 1 after scaling so a low multiplier never silently nullifies an
     * enemy's attack on one frontend while keeping it lethal on the other.
     */
    public static int applyDamageMultiplier(int baseDamage, double multiplier) {
        int safeBase = Math.max(0, baseDamage);
        double safeMul = Math.max(0d, multiplier);
        int scaled = (int) Math.round(safeBase * safeMul);
        if (safeBase > 0) {
            return Math.max(1, scaled);
        }
        return Math.max(0, scaled);
    }

    /**
     * Apply the difficulty movement speed multiplier to a base speed value.
     * Both frontends MUST use this so a Zombie on Easy moves at the same
     * relative pace whether the player is on the JavaFX or libGDX runtime.
     *
     * <p>Mirrors the JavaFX path in {@code OpponentRuntimeFactory
     * .setCharacterAttributesByDifficulty}, which does
     * {@code character.setSpeed(character.getSpeed() * speedMultiplier)} with
     * no clamping; negative multipliers are coerced to 0 to avoid moving
     * enemies backwards along the oscillation path.
     */
    public static double applySpeedMultiplier(double baseSpeed, double multiplier) {
        double safeBase = Math.max(0d, baseSpeed);
        double safeMul = Math.max(0d, multiplier);
        return safeBase * safeMul;
    }

    /**
     * Apply difficulty multipliers in-place to the spawned runtime character.
     * Both frontends must call this helper to keep damage and speed parity.
     */
    public static void applyDifficultyAttributes(
            CharacterType characterType,
            double speedMultiplier,
            double damageMultiplier,
            boolean instantDeath) {
        if (characterType == null) {
            return;
        }

        characterType.setSpeed(applySpeedMultiplier(characterType.getSpeed(), speedMultiplier));

        if (characterType instanceof Zombie zombie) {
            zombie.setAttackDamage(resolveAttackDamage(zombie.getAttackDamage(), damageMultiplier, instantDeath));
        } else if (characterType instanceof Ghost ghost) {
            ghost.setAttackDamage(resolveAttackDamage(ghost.getAttackDamage(), damageMultiplier, instantDeath));
        } else if (characterType instanceof PumpkinBomber pumpkinBomber) {
            pumpkinBomber.setAttackDamage(resolveAttackDamage(
                    pumpkinBomber.getAttackDamage(), damageMultiplier, instantDeath));
        }
    }

    /**
     * Runtime behaviour policy shared between JavaFX and libGDX.
     *
     * <p>AGGRESSIVE is preserved from the model. PASSIVE is normalized to WANDER
     * so enemies do not remain static at spawn. For WANDER/PATROL we keep the
     * legacy runtime mix by assigning PATROL or WANDER with a 50/50 split so
     * both frontends render the same blend.
     */
    public static BehaviorType resolveRuntimeBehavior(BehaviorType modelBehavior, Random random) {
        if (modelBehavior == BehaviorType.AGGRESSIVE) {
            return modelBehavior;
        }
        if (modelBehavior == BehaviorType.PASSIVE) {
            return BehaviorType.WANDER;
        }
        if (random == null) {
            return modelBehavior != null ? modelBehavior : BehaviorType.WANDER;
        }
        return random.nextDouble() < 0.5d ? BehaviorType.PATROL : BehaviorType.WANDER;
    }

    private static int resolveAttackDamage(int baseDamage, double damageMultiplier, boolean instantDeath) {
        if (instantDeath) {
            return Integer.MAX_VALUE;
        }
        return applyDamageMultiplier(baseDamage, damageMultiplier);
    }
}
