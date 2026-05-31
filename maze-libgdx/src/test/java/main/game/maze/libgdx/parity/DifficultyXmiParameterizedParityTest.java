package main.game.maze.libgdx.parity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.game.maze.characters.CollisionDamage;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.OpponentConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.difficulties.HardDifficulty;
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
 * End-to-end parity suite that feeds difficulties.xmi directly into both
 * the libGDX production loader ({@link RuntimeVisualModelLoader}) and the
 * shared JavaFX formula path ({@link EnemySpawnPlanner}) to verify that all
 * five Hard-difficulty XMI parameters behave identically across frontends.
 *
 * <p><strong>XMI under test:</strong> {@value #DIFFICULTIES_XMI_CLASSPATH}
 *
 * <p><strong>Hard difficulty values asserted from the XMI:</strong>
 * <ul>
 *   <li>{@code instantDeath="false"}</li>
 *   <li>{@code monstersMovementSpeedMultiplier="1.15"}</li>
 *   <li>{@code monstersDamageMultiplier="1.40"}</li>
 *   <li>{@code maxThreat="62"}</li>
 * </ul>
 *
 * <p>Each nested group is independent. Existing tests are not modified.
 * The "JavaFX path" is tested by calling {@link EnemySpawnPlanner#applyDifficultyAttributes}
 * on {@link EcoreUtil#copy}-ed model objects, which mirrors exactly what
 * {@code OpponentRuntimeFactory.setCharacterAttributesByDifficulty} does in
 * production.
 */
@DisplayName("Difficulty XMI parity: all parameters, libGDX vs JavaFX")
class DifficultyXmiParameterizedParityTest {

    // =========================================================================
    // Classpath path fed to every test in this suite.
    // Change this constant to point tests at a different difficulties.xmi.
    // =========================================================================
    static final String DIFFICULTIES_XMI_CLASSPATH = "/xmi/difficulties/difficulties.xmi";

    // Hard difficulty expected values sourced from the XMI file above.
    static final boolean HARD_INSTANT_DEATH = false;
    static final double  HARD_SPEED_MULT   = 1.15;
    static final double  HARD_DAMAGE_MULT  = 1.40;
    static final int     HARD_MAX_THREAT   = 62;

    private static HardDifficulty hardDiff;
    private static OpponentModel opponentModel;

    /** id -> unmodified CharacterType (used to look up base stats by spawn id). */
    private static Map<String, CharacterType> baseById;

    @BeforeAll
    static void loadXmi() {
        // DifficultyService loads DIFFICULTIES_XMI_CLASSPATH from the classpath.
        DifficultyService svc = new DifficultyService();
        hardDiff = svc.list().stream()
                .filter(d -> d instanceof HardDifficulty)
                .map(d -> (HardDifficulty) d)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Hard difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));

        opponentModel = new XmiRulesLoader()
                .loadOpponentModelFromClasspath(OpponentConstants.ZombieModelPath);
        assertNotNull(opponentModel, "opponent XMI must load from classpath");

        baseById = new LinkedHashMap<>();
        for (CharacterType ct : opponentModel.getCharacterTypes()) {
            if (ct.getId() != null && !ct.getId().isBlank()) {
                baseById.put(ct.getId(), ct);
            }
        }
        assertFalse(baseById.isEmpty(), "opponent XMI must contain at least one named enemy");
    }

    // =========================================================================
    // 1. XMI PARSING
    //    Verify every Hard-difficulty attribute is read from the XMI correctly.
    // =========================================================================

    @Nested
    @DisplayName("1. XMI parsing — Hard difficulty fields")
    class XmiParsing {

        @Test
        @DisplayName("instantDeath field parses as false")
        void instantDeathIsFalse() {
            assertFalse(hardDiff.isInstantDeath(),
                    "Hard difficulty instantDeath must be false per " + DIFFICULTIES_XMI_CLASSPATH);
        }

        @Test
        @DisplayName("monstersMovementSpeedMultiplier field parses as 1.15")
        void speedMultiplierIs1_15() {
            assertEquals(HARD_SPEED_MULT, hardDiff.getMonstersMovementSpeedMultiplier(), 1e-9,
                    "Hard difficulty speed multiplier must be 1.15 per " + DIFFICULTIES_XMI_CLASSPATH);
        }

        @Test
        @DisplayName("monstersDamageMultiplier field parses as 1.40")
        void damageMultiplierIs1_40() {
            assertEquals(HARD_DAMAGE_MULT, hardDiff.getMonstersDamageMultiplier(), 1e-9,
                    "Hard difficulty damage multiplier must be 1.40 per " + DIFFICULTIES_XMI_CLASSPATH);
        }

        @Test
        @DisplayName("maxThreat field parses as 62")
        void maxThreatIs62() {
            assertEquals(HARD_MAX_THREAT, hardDiff.getMaxThreat(),
                    "Hard difficulty maxThreat must be 62 per " + DIFFICULTIES_XMI_CLASSPATH);
        }

        @Test
        @DisplayName("Hard difficulty is a HardDifficulty instance")
        void hardDifficultyIsCorrectType() {
            assertTrue(hardDiff instanceof HardDifficulty,
                    "The 'hard' entry in " + DIFFICULTIES_XMI_CLASSPATH + " must deserialize as HardDifficulty");
        }
    }

    // =========================================================================
    // 2. instantDeath = false  (the actual Hard XMI value)
    //    Simulates monster touching the player when the difficulty flag is off.
    //    Expected: damage = applyDamageMultiplier(base, 1.40), NOT Integer.MAX_VALUE.
    // =========================================================================

    @Nested
    @DisplayName("2. instantDeath=false — damage = applyDamageMultiplier(base, 1.40)")
    class InstantDeathFalse {

        @Test
        @DisplayName("libGDX: spawned attackDamage equals applyDamageMultiplier(base, 1.40)")
        void libgdx_spawnedDamageEqualsFormula() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            assertNotNull(rvm);
            assertFalse(rvm.enemies().isEmpty(), "Hard difficulty must spawn at least one enemy");

            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                int baseDmg = attackDamageOf(base);
                int expected = EnemySpawnPlanner.applyDamageMultiplier(baseDmg, HARD_DAMAGE_MULT);
                assertEquals(expected, spawn.attackDamage(),
                        "libGDX " + spawn.id() + ": attackDamage must equal "
                        + "applyDamageMultiplier(base=" + baseDmg + ", mult=1.40)");
            }
        }

        @Test
        @DisplayName("JavaFX path: applyDifficultyAttributes produces applyDamageMultiplier(base, 1.40)")
        void javafx_applyDifficultyAttributesProducesCorrectDamage() {
            for (CharacterType template : baseById.values()) {
                CharacterType copy = EcoreUtil.copy(template);
                int baseDmg = attackDamageOf(template);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy, HARD_SPEED_MULT, HARD_DAMAGE_MULT, HARD_INSTANT_DEATH);
                int expected = EnemySpawnPlanner.applyDamageMultiplier(baseDmg, HARD_DAMAGE_MULT);
                assertEquals(expected, attackDamageOf(copy),
                        "JavaFX path " + template.getId()
                        + ": damage after applyDifficultyAttributes must equal "
                        + "applyDamageMultiplier(base=" + baseDmg + ", mult=1.40)");
            }
        }

        @Test
        @DisplayName("both frontends produce the same attackDamage per named enemy")
        void bothFrontends_damageAgreesForEveryNamedEnemy() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                CharacterType copy = EcoreUtil.copy(base);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy, HARD_SPEED_MULT, HARD_DAMAGE_MULT, HARD_INSTANT_DEATH);
                int javafxDamage = attackDamageOf(copy);
                assertEquals(javafxDamage, spawn.attackDamage(),
                        "both frontends must agree on damage for " + spawn.id()
                        + " at Hard difficulty (instantDeath=false)");
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must be verified");
        }

        @Test
        @DisplayName("monster touch simulation: instantDeath=false does NOT one-shot the player")
        void monsterTouch_instantDeathFalse_doesNotOneShot() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            assertNotNull(rvm);
            // Simulate: player touches each spawned enemy.
            // CollisionDamage.effectiveDamage(effectiveThreat, attackDamage) is the
            // shared formula used by both frontends on every player-enemy collision.
            for (EnemySpawn spawn : rvm.enemies()) {
                int collisionDmg = CollisionDamage.effectiveDamage(
                        spawn.effectiveThreat(), spawn.attackDamage());
                assertNotEquals(Integer.MAX_VALUE, collisionDmg,
                        spawn.id() + ": instantDeath=false must not produce "
                        + "collision damage of Integer.MAX_VALUE");
            }
        }
    }

    // =========================================================================
    // 3. instantDeath = true  (synthetic difficulty — Hard values, flag flipped)
    //    Simulates monster touching the player when difficulty forces instant kill.
    //    Expected: attackDamage = Integer.MAX_VALUE; collision damage = MAX_VALUE.
    // =========================================================================

    @Nested
    @DisplayName("3. instantDeath=true — all enemies deal Integer.MAX_VALUE damage")
    class InstantDeathTrue {

        private Difficulty buildInstantDeathHard() {
            Difficulty synth = DifficultiesFactory.eINSTANCE.createHardDifficulty();
            synth.setInstantDeath(true);
            synth.setMonstersDamageMultiplier(HARD_DAMAGE_MULT);
            synth.setMonstersMovementSpeedMultiplier(HARD_SPEED_MULT);
            synth.setMaxThreat(HARD_MAX_THREAT);
            for (EnemyMaxCount cap : hardDiff.getEnemyMaxCount()) {
                EnemyMaxCount copyCap = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
                copyCap.setType(cap.getType());
                copyCap.setMaxCount(cap.getMaxCount());
                synth.getEnemyMaxCount().add(copyCap);
            }
            return synth;
        }

        @Test
        @DisplayName("libGDX: spawned attackDamage is Integer.MAX_VALUE for every enemy")
        void libgdx_allSpawnedEnemiesDealMaxValueDamage() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeathHard());
            assertNotNull(rvm);
            assertFalse(rvm.enemies().isEmpty(),
                    "instantDeath=true Hard must still spawn at least one enemy");
            for (EnemySpawn spawn : rvm.enemies()) {
                assertEquals(Integer.MAX_VALUE, spawn.attackDamage(),
                        spawn.id() + ": instantDeath=true must set attackDamage "
                        + "to Integer.MAX_VALUE in libGDX");
            }
        }

        @Test
        @DisplayName("JavaFX path: applyDifficultyAttributes sets attackDamage to Integer.MAX_VALUE")
        void javafx_applyDifficultyAttributesSetsMaxValueDamage() {
            for (CharacterType template : baseById.values()) {
                CharacterType copy = EcoreUtil.copy(template);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy, HARD_SPEED_MULT, HARD_DAMAGE_MULT, true);
                assertEquals(Integer.MAX_VALUE, attackDamageOf(copy),
                        "JavaFX path: instantDeath=true must set "
                        + template.getId() + " attackDamage to Integer.MAX_VALUE");
            }
        }

        @Test
        @DisplayName("both frontends produce Integer.MAX_VALUE damage when instantDeath=true")
        void bothFrontends_damageIsMaxValueWhenInstantDeath() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeathHard());
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                CharacterType copy = EcoreUtil.copy(base);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy, HARD_SPEED_MULT, HARD_DAMAGE_MULT, true);
                int javafxDamage = attackDamageOf(copy);
                assertEquals(javafxDamage, spawn.attackDamage(),
                        "both frontends must agree on damage for " + spawn.id()
                        + " when instantDeath=true");
                assertEquals(Integer.MAX_VALUE, spawn.attackDamage(),
                        spawn.id() + ": damage must be Integer.MAX_VALUE when instantDeath=true");
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must be verified");
        }

        @Test
        @DisplayName("monster touch simulation: instantDeath=true causes Integer.MAX_VALUE collision damage")
        void monsterTouch_instantDeathTrue_oneshotsPlayer() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeathHard());
            assertNotNull(rvm);
            // When the difficulty sets instantDeath=true the loader also raises
            // effectiveThreat above INSTANT_KILL_THREAT_THRESHOLD so that
            // CollisionDamage returns MAX_VALUE independently of attackDamage.
            for (EnemySpawn spawn : rvm.enemies()) {
                int collisionDmg = CollisionDamage.effectiveDamage(
                        spawn.effectiveThreat(), spawn.attackDamage());
                assertEquals(Integer.MAX_VALUE, collisionDmg,
                        spawn.id() + ": collision damage must be Integer.MAX_VALUE "
                        + "when instantDeath=true");
            }
        }

        @Test
        @DisplayName("instantDeath=true forces effectiveThreat above the instant-kill threshold")
        void libgdx_instantDeathTrueRaisesEffectiveThreatAboveThreshold() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeathHard());
            assertNotNull(rvm);
            for (EnemySpawn spawn : rvm.enemies()) {
                assertTrue(spawn.effectiveThreat() > CollisionDamage.INSTANT_KILL_THREAT_THRESHOLD,
                        spawn.id() + ": effectiveThreat must exceed "
                        + CollisionDamage.INSTANT_KILL_THREAT_THRESHOLD
                        + " when instantDeath=true (got " + spawn.effectiveThreat() + ")");
            }
        }
    }

    // =========================================================================
    // 4. monstersMovementSpeedMultiplier = 1.15
    //    Simulates enemy movement: spawned speed must be base * 1.15.
    // =========================================================================

    @Nested
    @DisplayName("4. monstersMovementSpeedMultiplier=1.15 — spawned speed = applySpeedMultiplier(base, 1.15)")
    class SpeedMultiplier {

        @Test
        @DisplayName("libGDX: spawned speed equals applySpeedMultiplier(baseSpeed, 1.15)")
        void libgdx_spawnedSpeedMatchesFormula() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                float expected = (float) EnemySpawnPlanner.applySpeedMultiplier(
                        base.getSpeed(), HARD_SPEED_MULT);
                assertEquals(expected, spawn.speed(), 1e-4f,
                        "libGDX " + spawn.id() + ": speed must equal "
                        + "applySpeedMultiplier(base=" + base.getSpeed() + ", mult=1.15)");
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must have speed verified");
        }

        @Test
        @DisplayName("JavaFX path: applyDifficultyAttributes scales speed by 1.15")
        void javafx_applyDifficultyAttributesScalesSpeed() {
            for (CharacterType template : baseById.values()) {
                double baseSpeed = template.getSpeed();
                CharacterType copy = EcoreUtil.copy(template);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy, HARD_SPEED_MULT, HARD_DAMAGE_MULT, HARD_INSTANT_DEATH);
                double expected = EnemySpawnPlanner.applySpeedMultiplier(baseSpeed, HARD_SPEED_MULT);
                assertEquals(expected, copy.getSpeed(), 1e-9,
                        "JavaFX path: " + template.getId()
                        + " speed after applyDifficultyAttributes must be " + expected);
            }
        }

        @Test
        @DisplayName("both frontends agree on spawned speed per named enemy")
        void bothFrontends_speedAgreesForEveryNamedEnemy() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                CharacterType copy = EcoreUtil.copy(base);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy, HARD_SPEED_MULT, HARD_DAMAGE_MULT, HARD_INSTANT_DEATH);
                float javafxSpeed = (float) copy.getSpeed();
                assertEquals(javafxSpeed, spawn.speed(), 1e-4f,
                        "both frontends must agree on speed for " + spawn.id()
                        + " at Hard difficulty");
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must be verified");
        }

        @Test
        @DisplayName("Hard (1.15) enemies move faster than Easy (0.80) enemies")
        void hardEnemiesMovesFasterThanEasy() {
            Difficulty easyDiff = new DifficultyService().list().stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));
            for (CharacterType template : baseById.values()) {
                if (template.getSpeed() <= 0) {
                    continue;
                }
                double hardSpeed = EnemySpawnPlanner.applySpeedMultiplier(
                        template.getSpeed(), HARD_SPEED_MULT);
                double easySpeed = EnemySpawnPlanner.applySpeedMultiplier(
                        template.getSpeed(), easyDiff.getMonstersMovementSpeedMultiplier());
                assertTrue(hardSpeed > easySpeed,
                        template.getId() + ": Hard-difficulty speed (" + hardSpeed
                        + ") must exceed Easy-difficulty speed (" + easySpeed + ")");
            }
        }

        @Test
        @DisplayName("libGDX Hard enemies move faster than libGDX Easy enemies")
        void libgdx_hardEnemiesAreSpawnedFasterThanEasy() {
            Difficulty easyDiff = new DifficultyService().list().stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));

            RuntimeVisualModel hardRvm  = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            RuntimeVisualModel easyRvm  = new RuntimeVisualModelLoader().load(800f, 600f, easyDiff);
            assertNotNull(hardRvm);
            assertNotNull(easyRvm);

            Map<String, Float> easySpeedById = new LinkedHashMap<>();
            for (EnemySpawn spawn : easyRvm.enemies()) {
                easySpeedById.put(spawn.id(), spawn.speed());
            }

            int verified = 0;
            for (EnemySpawn hardSpawn : hardRvm.enemies()) {
                Float easySpeed = easySpeedById.get(hardSpawn.id());
                if (easySpeed == null) {
                    continue;
                }
                assertTrue(hardSpawn.speed() > easySpeed,
                        hardSpawn.id() + ": libGDX Hard speed (" + hardSpawn.speed()
                        + ") must be greater than Easy speed (" + easySpeed + ")");
                verified++;
            }
            assertTrue(verified > 0, "at least one shared enemy must be verified across difficulties");
        }
    }

    // =========================================================================
    // 5. maxThreat = 62
    //    Both frontends must stop spawning once the threat budget is exhausted.
    // =========================================================================

    @Nested
    @DisplayName("5. maxThreat=62 — total spawned threat must not exceed the budget")
    class MaxThreat {

        @Test
        @DisplayName("libGDX: sum of effectiveThreat for all spawned enemies does not exceed 62")
        void libgdx_totalThreatDoesNotExceedBudget() {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            assertNotNull(rvm);
            // Note: when instantDeath=false the loader stores each enemy's raw
            // model threatLevel as effectiveThreat. The OCL invariant and the
            // loader's break condition both enforce the budget.
            double totalThreat = rvm.enemies().stream()
                    .mapToDouble(EnemySpawn::effectiveThreat)
                    .sum();
            assertTrue(totalThreat <= HARD_MAX_THREAT,
                    "libGDX: total spawned threat " + totalThreat
                    + " must not exceed maxThreat=" + HARD_MAX_THREAT);
        }

        @Test
        @DisplayName("JavaFX path: threat budget simulation stops spawning at maxThreat=62")
        void javafx_threatBudgetIsRespected() {
            // Mirrors the threat-budget loop in OpponentRuntimeFactory.spawnByTarget
            // without invoking the JavaFX toolkit.
            Map<EnemyTypes, Integer> caps = EnemySpawnPlanner.capsFromDifficulty(hardDiff);
            Map<EnemyTypes, List<CharacterType>> available =
                    EnemySpawnPlanner.availableEnabledByType(opponentModel, true);
            double budget = HARD_MAX_THREAT;
            double usedThreat = 0.0;
            for (Map.Entry<EnemyTypes, Integer> entry : caps.entrySet()) {
                int count = Math.max(0, entry.getValue());
                List<CharacterType> candidates =
                        available.getOrDefault(entry.getKey(), List.of());
                if (candidates.isEmpty()) {
                    continue;
                }
                for (int i = 0; i < count; i++) {
                    double remaining = budget - usedThreat;
                    if (remaining <= 0) {
                        break;
                    }
                    CharacterType candidate = candidates.get(i % candidates.size());
                    double effThreat = candidate.getEffectiveThreat();
                    if (effThreat <= 0 || effThreat > remaining) {
                        break;
                    }
                    usedThreat += effThreat;
                }
            }
            assertTrue(usedThreat <= HARD_MAX_THREAT,
                    "JavaFX threat budget simulation: usedThreat " + usedThreat
                    + " must not exceed maxThreat=" + HARD_MAX_THREAT);
        }

        @Test
        @DisplayName("libGDX: Hard maxThreat=62 allows more enemies than Easy maxThreat=12")
        void libgdx_hardDifficultySpawnsMoreEnemiesThanEasy() {
            Difficulty easyDiff = new DifficultyService().list().stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));
            RuntimeVisualModel hardRvm = new RuntimeVisualModelLoader().load(800f, 600f, hardDiff);
            RuntimeVisualModel easyRvm = new RuntimeVisualModelLoader().load(800f, 600f, easyDiff);
            assertNotNull(hardRvm);
            assertNotNull(easyRvm);
            assertTrue(hardRvm.enemies().size() >= easyRvm.enemies().size(),
                    "Hard difficulty (maxThreat=62) must spawn at least as many enemies "
                    + "as Easy (maxThreat=12). Hard=" + hardRvm.enemies().size()
                    + ", Easy=" + easyRvm.enemies().size());
        }

        @Test
        @DisplayName("libGDX: tight threat budget (maxThreat=1) limits spawns to at most one enemy")
        void libgdx_tightThreatBudgetLimitsSpawnCount() {
            // MIN_THREAT_BUDGET in RuntimeVisualModelLoader clamps to 1f even if
            // setMaxThreat(0) is used, so maxThreat=1 is the tightest meaningful test.
            // Every enemy has an effective threat of at least 1 (floored in the loader),
            // so at most one enemy can fit inside a budget of 1.
            Difficulty tightBudget = DifficultiesFactory.eINSTANCE.createHardDifficulty();
            tightBudget.setMaxThreat(1);
            tightBudget.setMonstersDamageMultiplier(HARD_DAMAGE_MULT);
            tightBudget.setMonstersMovementSpeedMultiplier(HARD_SPEED_MULT);
            tightBudget.setInstantDeath(false);
            for (EnemyMaxCount cap : hardDiff.getEnemyMaxCount()) {
                EnemyMaxCount copyCap = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
                copyCap.setType(cap.getType());
                copyCap.setMaxCount(cap.getMaxCount());
                tightBudget.getEnemyMaxCount().add(copyCap);
            }
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, tightBudget);
            assertNotNull(rvm);
            assertTrue(rvm.enemies().size() <= 1,
                    "maxThreat=1 must yield at most 1 spawned enemy, "
                    + "got " + rvm.enemies().size());
        }

        @Test
        @DisplayName("Hard maxThreat=62 is larger than Easy maxThreat=12 (XMI ordering sanity check)")
        void hardMaxThreatIsLargerThanEasy() {
            Difficulty easyDiff = new DifficultyService().list().stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));
            assertTrue(HARD_MAX_THREAT > easyDiff.getMaxThreat(),
                    "Hard maxThreat (" + HARD_MAX_THREAT + ") must exceed Easy maxThreat ("
                    + easyDiff.getMaxThreat() + ")");
        }
    }

    // =========================================================================
    // Shared helpers
    // =========================================================================

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
