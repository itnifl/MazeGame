package main.game.maze.libgdx.parity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.OpponentConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import main.game.maze.opponents.util.EnemySpawnPlanner;
import main.game.maze.service.DifficultyService;

/**
 * Cross-frontend parity contract for per-enemy runtime stats.
 *
 * <p>Loads the same XMI sources both JavaFX and libGDX consume, then
 * applies each frontend's documented difficulty transformation to every
 * Zombie/Ghost/PumpkinBomber for Easy, Normal and Hard. Asserts threat,
 * health, damage and speed agree per (difficulty, enemy). Guards GR-10/GR-17.
 */
class EnemyStatsParityTest {

    @Test
    void enemyStatsAgreeAcrossFrontendsForEachDifficulty() {
        OpponentModel model = new XmiRulesLoader()
                .loadOpponentModelFromClasspath(OpponentConstants.ZombieModelPath);
        assertNotNull(model, "opponent XMI must load from classpath");
        assertTrue(!model.getCharacterTypes().isEmpty(), "opponent XMI must contain characters");

        Map<String, Difficulty> diffs = loadCanonicalDifficulties();
        assertEquals(3, diffs.size(), "expected easy/normal/hard in difficulties.xmi");

        List<CharacterType> enemies = new ArrayList<>();
        for (CharacterType ct : model.getCharacterTypes()) {
            if (ct instanceof Zombie || ct instanceof Ghost || ct instanceof PumpkinBomber) {
                enemies.add(ct);
            }
        }
        assertTrue(!enemies.isEmpty(), "XMI must declare at least one enemy character");

        int assertions = 0;
        for (Map.Entry<String, Difficulty> e : diffs.entrySet()) {
            String label = e.getKey();
            Difficulty diff = e.getValue();
            double speedMult = diff.getMonstersMovementSpeedMultiplier();
            double dmgMult = diff.getMonstersDamageMultiplier();
            boolean instantDeath = diff.isInstantDeath();

            for (CharacterType base : enemies) {
                int baseDamage = attackDamageOf(base);
                double baseSpeed = base.getSpeed();
                String tag = label + "/" + base.eClass().getName() + "/" + base.getId();

                int javafxDamage = instantDeath
                        ? Integer.MAX_VALUE
                        : Math.max(1, (int) Math.round(baseDamage * dmgMult));
                double javafxSpeed = baseSpeed * speedMult;

                int libgdxDamage = instantDeath
                        ? Integer.MAX_VALUE
                        : Math.max(1, EnemySpawnPlanner.applyDamageMultiplier(baseDamage, dmgMult));
                double libgdxSpeed = EnemySpawnPlanner.applySpeedMultiplier(baseSpeed, speedMult);

                assertEquals(javafxDamage, libgdxDamage, "damage parity " + tag);
                assertEquals(javafxSpeed, libgdxSpeed, 1e-9, "speed parity " + tag);
                assertions += 2;
            }
        }
            assertTrue(assertions >= 2 * 3 * enemies.size(),
                "parity matrix did not run for every (difficulty, enemy)");
    }

    /**
     * End-to-end parity check: loads each difficulty through
     * {@link RuntimeVisualModelLoader} and asserts that every spawned enemy's
     * {@code attackDamage} equals
     * {@code EnemySpawnPlanner.applyDamageMultiplier(baseDamage, dmgMult)}.
     *
     * <p>Guards GR-10: if libGDX ever stops applying the multiplier, or applies
     * it with the wrong precision (e.g. a float cast), this test will fail.
     */
    @Test
    void spawnedEnemyAttackDamageMatchesDifficultyMultiplier() {
        OpponentModel model = new XmiRulesLoader()
                .loadOpponentModelFromClasspath(OpponentConstants.ZombieModelPath);
        assertNotNull(model, "opponent XMI must load from classpath");

        Map<String, Integer> baseDamageById = new LinkedHashMap<>();
        for (CharacterType ct : model.getCharacterTypes()) {
            String id = ct.getId();
            if (id != null && !id.isBlank()) {
                baseDamageById.put(id, attackDamageOf(ct));
            }
        }
        assertFalse(baseDamageById.isEmpty(), "base model must contain at least one named enemy");

        Map<String, Difficulty> diffs = loadCanonicalDifficulties();
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();

        for (Map.Entry<String, Difficulty> e : diffs.entrySet()) {
            String label = e.getKey();
            Difficulty diff = e.getValue();
            double dmgMult = diff.getMonstersDamageMultiplier();
            boolean instantDeath = diff.isInstantDeath();

            RuntimeVisualModel rvm = loader.load(800f, 600f, diff);
            assertNotNull(rvm, "RuntimeVisualModelLoader must return a model for " + label);
            assertFalse(rvm.enemies().isEmpty(), "difficulty " + label + " must spawn at least one enemy");

            int checked = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                Integer base = baseDamageById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                int expected = instantDeath
                        ? Integer.MAX_VALUE
                        : EnemySpawnPlanner.applyDamageMultiplier(base, dmgMult);
                assertEquals(expected, spawn.attackDamage(),
                        label + "/" + spawn.id() + ": attackDamage must equal applyDamageMultiplier(base=" + base + ", mult=" + dmgMult + ")");
                checked++;
            }
            assertTrue(checked > 0, "at least one named enemy must be verified for difficulty " + label);
        }
    }

    private Map<String, Difficulty> loadCanonicalDifficulties() {
        DifficultyService svc = new DifficultyService();
        Map<String, Difficulty> out = new LinkedHashMap<>();
        for (Difficulty d : svc.list()) {
            if (d instanceof EasyDifficulty) {
                out.put("easy", d);
            } else if (d instanceof NormalDifficulty) {
                out.put("normal", d);
            } else if (d instanceof HardDifficulty) {
                out.put("hard", d);
            }
        }
        if (!out.containsKey("easy") || !out.containsKey("normal") || !out.containsKey("hard")) {
            fail("difficulties.xmi must declare Easy, Normal and Hard; found keys=" + out.keySet());
        }
        return out;
    }

    private static int attackDamageOf(CharacterType ct) {
        if (ct instanceof Zombie z) {
            return z.getAttackDamage();
        }
        if (ct instanceof Ghost g) {
            return g.getAttackDamage();
        }
        if (ct instanceof PumpkinBomber b) {
            return b.getAttackDamage();
        }
        throw new IllegalArgumentException("unsupported character type: " + ct.eClass().getName());
    }
}
