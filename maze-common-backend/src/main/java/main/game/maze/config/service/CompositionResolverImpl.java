package main.game.maze.config.service;

import main.game.maze.difficulties.EnemyTypes;

import java.util.*;

/**
 * Implements the composition logic:
 * 
 *     If countsOverride is present, use it (takes precedence over ratios).
 *     If not, distribute according to ratios.
 *     Apply caps and then redistribute so the total enemy count is preserved.
 * 
 */
public final class CompositionResolverImpl implements CompositionResolver {

    private final Map<String, ProfileRules> profiles;

    public CompositionResolverImpl(Map<String, ProfileRules> profiles) {
        this.profiles = Objects.requireNonNull(profiles, "profiles must not be null");
        if (profiles.isEmpty()) {
            throw new IllegalStateException("No profiles loaded");
        }
    }

    /**
     * Given a profile name, returns the final enemy composition.
     */
    @Override
    public Map<EnemyTypes, Integer> resolve(String profile) {
        ProfileRules profileRules = Optional.ofNullable(profiles.get(profile))
                .orElseThrow(() -> new IllegalArgumentException("Unknown profile: " + profile));

        int totalEnemyCount = Math.max(0, profileRules.enemyCount());

        // 1) If explicit counts are provided, they override everything else
        if (profileRules.countsOverride() != null && !profileRules.countsOverride().isEmpty()) {

            Map<EnemyTypes, Integer> nonNegativeCounts =
                    clampNonNegative(profileRules.countsOverride());
            Map<EnemyTypes, Integer> capped =
                    applyCaps(nonNegativeCounts, profileRules.caps());

            return redistributeToTotal(capped, totalEnemyCount);
        }

        // Otherwise, use ratios
        Map<EnemyTypes, Double> sanitizedRatios = sanitizeRatios(profileRules.ratios());
        Map<EnemyTypes, Double> normalizedRatios = normalize(sanitizedRatios);

        if (normalizedRatios.isEmpty() || totalEnemyCount == 0) {
            return Map.of();
        }

        Map<EnemyTypes, Double> scaledTargets = scale(normalizedRatios, totalEnemyCount);
        Map<EnemyTypes, Integer> initialAllocation = largestRemainderAllocate(scaledTargets);
        Map<EnemyTypes, Integer> cappedAllocation =
                applyCaps(initialAllocation, profileRules.caps());

        return redistributeToTotal(cappedAllocation, totalEnemyCount);
    }

    /**
     * Ensures that all counts are non-negative. Null values become 0.
     */
    private static Map<EnemyTypes, Integer> clampNonNegative(Map<EnemyTypes, Integer> input) {
        Map<EnemyTypes, Integer> result = new EnumMap<>(EnemyTypes.class);

        input.forEach((type, value) -> {
            int safeValue = (value == null ? 0 : value);
            result.put(type, Math.max(0, safeValue));
        });

        return result;
    }

    /**
     * Filters out invalid ratio values (null, NaN, non-positive). Those become 0.
     */
    private static Map<EnemyTypes, Double> sanitizeRatios(Map<EnemyTypes, Double> ratios) {
        if (ratios == null) {
            return Map.of();
        }

        Map<EnemyTypes, Double> result = new EnumMap<>(EnemyTypes.class);
        ratios.forEach((type, value) -> {
            double safeValue =
                    (value == null || Double.isNaN(value) || value <= 0.0) ? 0.0 : value;
            result.put(type, safeValue);
        });
        return result;
    }

    /**
     * Normalizes the given map so that the sum of values is 1.0.
     * Returns an empty map if the sum is non-positive.
     */
    private static Map<EnemyTypes, Double> normalize(Map<EnemyTypes, Double> values) {
        double sum = values.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        if (sum <= 0.0) {
            return Map.of();
        }

        Map<EnemyTypes, Double> result = new EnumMap<>(EnemyTypes.class);
        final double total = sum;
        values.forEach((type, value) -> result.put(type, value / total));
        return result;
    }

    /**
     * Multiplies all values by the given total.
     */
    private static Map<EnemyTypes, Double> scale(Map<EnemyTypes, Double> ratios, int total) {
        Map<EnemyTypes, Double> result = new EnumMap<>(EnemyTypes.class);
        ratios.forEach((type, ratio) -> result.put(type, ratio * total));
        return result;
    }

    /**
     * Allocates integers from fractional targets by:
     * 
     *     Taking the floor of each target
     *     Distributing remaining units to the largest fractional remainders.
     * 
     */
    private static Map<EnemyTypes, Integer> largestRemainderAllocate(
            Map<EnemyTypes, Double> targets
    ) {
        Map<EnemyTypes, Integer> floorValues = new EnumMap<>(EnemyTypes.class);
        Map<EnemyTypes, Double> fractionalParts = new EnumMap<>(EnemyTypes.class);

        int sumOfFloors = 0;
        for (var entry : targets.entrySet()) {

            EnemyTypes type = entry.getKey();
            double value = entry.getValue();

            int floor = (int) Math.floor(value);
            double fraction = value - floor;

            floorValues.put(type, floor);
            fractionalParts.put(type, fraction);
            sumOfFloors += floor;
        }

        int roundedTotal = (int) Math.round(
                targets.values().stream()
                        .mapToDouble(Double::doubleValue)
                        .sum()
        );
        int unitsToDistribute = Math.max(0, roundedTotal - sumOfFloors);

        List<EnemyTypes> order = new ArrayList<>(fractionalParts.keySet());
        order.sort((a, b) -> {
            int cmp = Double.compare(fractionalParts.get(b), fractionalParts.get(a));
            return (cmp != 0) ? cmp : a.name().compareTo(b.name());
        });

        for (int i = 0; i < unitsToDistribute; i++) {
            EnemyTypes type = order.get(i % order.size());
            floorValues.put(type, floorValues.get(type) + 1);
        }

        return floorValues;
    }

    /**
     * Applies caps to the given counts. If a cap is missing, there is effectively no limit.
     */
    private static Map<EnemyTypes, Integer> applyCaps(
            Map<EnemyTypes, Integer> counts,
            Map<EnemyTypes, Integer> caps
    ) {
        if (caps == null || caps.isEmpty()) {
            return counts;
        }

        Map<EnemyTypes, Integer> result = new EnumMap<>(EnemyTypes.class);
        counts.forEach((type, count) -> {
            int cap = Math.max(0, caps.getOrDefault(type, Integer.MAX_VALUE));
            result.put(type, Math.min(count, cap));
        });
        return result;
    }

    /**
     * Adjusts the given counts so that the sum of all enemies equals the desired total.
     * If there are too many enemies, they are removed from the largest buckets first.
     * If there are too few, enemies are added in a round-robin fashion.
     */
    private static Map<EnemyTypes, Integer> redistributeToTotal(
            Map<EnemyTypes, Integer> counts,
            int total
    ) {
        int currentTotal = counts.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        Map<EnemyTypes, Integer> result = new EnumMap<>(counts);

        if (currentTotal == total) {
            return result;
        }

        if (currentTotal > total) {
            // Remove extras from the largest buckets first
            int toRemove = currentTotal - total;

            List<EnemyTypes> order = new ArrayList<>(result.keySet());
            order.sort((a, b) -> Integer.compare(result.get(b), result.get(a)));

            for (EnemyTypes type : order) {
                if (toRemove == 0) {
                    break;
                }

                int currentCount = result.get(type);
                int remove = Math.min(toRemove, currentCount);
                result.put(type, currentCount - remove);
                toRemove -= remove;
            }

            result.replaceAll((type, value) -> Math.max(0, value));
            return result;
        } else {
            // Add missing units in a round-robin fashion
            int toAdd = total - currentTotal;
            List<EnemyTypes> order = new ArrayList<>(result.keySet());

            if (order.isEmpty()) {
                // If there are truly no keys, start with all enemy types
                order = List.of(EnemyTypes.values());
                result.put(order.get(0), 0);
            }

            int index = 0;
            while (toAdd-- > 0) {
                EnemyTypes type = order.get(index++ % order.size());
                result.put(type, result.getOrDefault(type, 0) + 1);
            }

            return result;
        }
    }
}


