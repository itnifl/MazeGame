package main.game.maze.config.service;

import main.game.maze.difficulties.EnemyTypes;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CompositionResolverImplTest {

    private static CompositionResolverImpl resolver(ProfileRules... rules) {
        Map<String, ProfileRules> profiles = new java.util.LinkedHashMap<>();
        for (ProfileRules r : rules) {
            profiles.put(r.name(), r);
        }
        return new CompositionResolverImpl(profiles);
    }

    // Ratio-based: single type should receive all enemies.
    @Test
    void resolve_singleTypeRatio_allocatesAllEnemies() {
        Map<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
        ratios.put(EnemyTypes.ZOMBIE, 1.0);

        ProfileRules rule = new ProfileRules("easy", 5, ratios, null, null);
        var result = resolver(rule).resolve("easy");

        assertEquals(5, result.getOrDefault(EnemyTypes.ZOMBIE, 0));
    }

    // Count override takes precedence over ratios.
    @Test
    void resolve_countsOverride_ignoresRatios() {
        Map<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
        ratios.put(EnemyTypes.ZOMBIE, 0.5);
        ratios.put(EnemyTypes.GHOST, 0.5);

        Map<EnemyTypes, Integer> override = new EnumMap<>(EnemyTypes.class);
        override.put(EnemyTypes.PUMPKINBOMBER, 3);

        ProfileRules rule = new ProfileRules("hard", 3, ratios, override, null);
        var result = resolver(rule).resolve("hard");

        // All 3 should come from the override, not the ratios.
        assertEquals(3, result.values().stream().mapToInt(Integer::intValue).sum());
        assertTrue(result.containsKey(EnemyTypes.PUMPKINBOMBER));
    }

    // Total enemies after resolve must equal enemyCount regardless of ratio distribution.
    @Test
    void resolve_totalAlwaysEqualsEnemyCount() {
        Map<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
        ratios.put(EnemyTypes.ZOMBIE, 0.6);
        ratios.put(EnemyTypes.GHOST, 0.4);

        ProfileRules rule = new ProfileRules("normal", 10, ratios, null, null);
        var result = resolver(rule).resolve("normal");

        int total = result.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(10, total, "Total enemy count must match enemyCount");
    }

    // Two consecutive resolves with the same profile return the same result.
    @Test
    void resolve_isIdempotent() {
        Map<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
        ratios.put(EnemyTypes.ZOMBIE, 1.0);

        ProfileRules rule = new ProfileRules("easy", 4, ratios, null, null);
        CompositionResolverImpl r = resolver(rule);

        var first = r.resolve("easy");
        var second = r.resolve("easy");

        assertEquals(first, second, "Repeated resolve of same profile must be idempotent");
    }

    // Unknown profile throws IllegalArgumentException.
    @Test
    void resolve_unknownProfile_throwsIllegalArgument() {
        Map<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
        ratios.put(EnemyTypes.ZOMBIE, 1.0);

        ProfileRules rule = new ProfileRules("easy", 3, ratios, null, null);
        var r = resolver(rule);

        assertThrows(IllegalArgumentException.class, () -> r.resolve("does-not-exist"));
    }

    // Null profiles map is rejected at construction time.
    @Test
    void constructor_nullProfiles_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new CompositionResolverImpl(null));
    }

    // Empty profiles map is rejected at construction time.
    @Test
    void constructor_emptyProfiles_throwsIllegalState() {
        assertThrows(IllegalStateException.class, () -> new CompositionResolverImpl(Map.of()));
    }

    // Cap applied: no type can exceed its cap.
    @Test
    void resolve_capsAreRespected() {
        Map<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
        ratios.put(EnemyTypes.ZOMBIE, 1.0);

        Map<EnemyTypes, Integer> caps = new EnumMap<>(EnemyTypes.class);
        caps.put(EnemyTypes.ZOMBIE, 2);

        ProfileRules rule = new ProfileRules("capped", 5, ratios, null, caps);
        var result = resolver(rule).resolve("capped");

        assertTrue(result.getOrDefault(EnemyTypes.ZOMBIE, 0) <= 2, "Zombie count must not exceed cap");
    }
}
