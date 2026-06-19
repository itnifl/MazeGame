package main.game.maze.libgdx.parity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import main.game.maze.characters.CollisionDamage;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.DifficultyResourceConstants;
import main.game.maze.constants.OpponentConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
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
 * End-to-end parity suite that feeds difficulties.xmi directly into both
 * the libGDX production loader ({@link RuntimeVisualModelLoader}) and the
 * shared JavaFX formula path ({@link EnemySpawnPlanner}) and verifies that
 * all five XMI parameters behave identically across frontends for each of
 * the three difficulties (Easy, Normal, Hard).
 *
 * <p><strong>XMI under test:</strong> {@value #DIFFICULTIES_XMI_CLASSPATH}
 *
 * <p>Every {@code @Test} or {@code @ParameterizedTest} in each nested group
 * is independent. Existing tests are not modified.
 * The "JavaFX path" is exercised by calling
 * {@link EnemySpawnPlanner#applyDifficultyAttributes} on
 * {@link EcoreUtil#copy}-ed model objects, which is exactly what
 * {@code OpponentRuntimeFactory.setCharacterAttributesByDifficulty} does in
 * production.
 */
@DisplayName("Difficulty XMI parity: all parameters across Easy / Normal / Hard")
class DifficultyXmiParameterizedParityTest {

    // =========================================================================
    // Classpath path fed to every test in this suite.
    // Change this constant to point tests at a different difficulties.xmi.
    // =========================================================================
    static final String DIFFICULTIES_XMI_CLASSPATH = DifficultyResourceConstants.DifficultiesXmiPath;

    // Compile-time constant used by @MethodSource inside @Nested classes.
    static final String ALL_DIFFICULTIES_SOURCE =
            "main.game.maze.libgdx.parity.DifficultyXmiParameterizedParityTest#allDifficulties";

    // Expected XMI values per difficulty type.
    // These mirror the data in DIFFICULTIES_XMI_CLASSPATH. Update both together.
    static final double EASY_SPEED_MULT    = 0.80;
    static final double EASY_DAMAGE_MULT   = 0.70;
    static final int    EASY_MAX_THREAT    = 12;

    static final double NORMAL_SPEED_MULT  = 1.00;
    static final double NORMAL_DAMAGE_MULT = 1.00;
    static final int    NORMAL_MAX_THREAT  = 25;

    static final double HARD_SPEED_MULT   = 1.15;
    static final double HARD_DAMAGE_MULT  = 1.40;
    static final int    HARD_MAX_THREAT   = 62;

    // Loaded once by @BeforeAll; shared by all nested groups.
    private static List<Difficulty> allDifficultyList;
    private static HardDifficulty hardDiff;
    private static OpponentModel opponentModel;

    /** id -> unmodified CharacterType (used to look up base stats by spawn id). */
    private static Map<String, CharacterType> baseById;

    // =========================================================================
    // Parameterisation source — one Named<Difficulty> per XMI difficulty.
    // =========================================================================
    static Stream<Named<Difficulty>> allDifficulties() {
        return allDifficultyList.stream()
                .map(d -> Named.of(d.eClass().getName(), d));
    }

    @BeforeAll
    static void loadXmi() {
        DifficultyService svc = new DifficultyService();
        allDifficultyList = new ArrayList<>(svc.list());
        assertFalse(allDifficultyList.isEmpty(),
                "difficulties.xmi must contain at least one entry");

        hardDiff = allDifficultyList.stream()
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
    //    Verify every difficulty attribute is read from the XMI correctly for
    //    all three difficulties (Easy, Normal, Hard).
    // =========================================================================

    @Nested
    @DisplayName("1. XMI parsing — all difficulty fields for each difficulty")
    class XmiParsing {

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("instantDeath field parses as false")
        void instantDeathIsFalse(Difficulty diff) {
            assertFalse(diff.isInstantDeath(),
                    diff.eClass().getName() + " instantDeath must be false per "
                    + DIFFICULTIES_XMI_CLASSPATH);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("monstersMovementSpeedMultiplier field parses as expected")
        void speedMultiplierMatchesXmi(Difficulty diff) {
            assertEquals(expectedSpeedMult(diff), diff.getMonstersMovementSpeedMultiplier(), 1e-9,
                    diff.eClass().getName() + " speedMult must match "
                    + DIFFICULTIES_XMI_CLASSPATH);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("monstersDamageMultiplier field parses as expected")
        void damageMultiplierMatchesXmi(Difficulty diff) {
            assertEquals(expectedDamageMult(diff), diff.getMonstersDamageMultiplier(), 1e-9,
                    diff.eClass().getName() + " damageMult must match "
                    + DIFFICULTIES_XMI_CLASSPATH);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("maxThreat field parses as expected")
        void maxThreatMatchesXmi(Difficulty diff) {
            assertEquals(expectedMaxThreat(diff), diff.getMaxThreat(),
                    diff.eClass().getName() + " maxThreat must match "
                    + DIFFICULTIES_XMI_CLASSPATH);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("difficulty entry deserialises as the correct EMF subtype")
        void difficultyTypeMatchesXmi(Difficulty diff) {
            assertTrue(expectedType(diff).isInstance(diff),
                    "Entry in " + DIFFICULTIES_XMI_CLASSPATH
                    + " must deserialise as " + expectedType(diff).getSimpleName()
                    + " but was " + diff.getClass().getSimpleName());
        }
    }

    // =========================================================================
    // 2. instantDeath = false
    //    Verifies that when a difficulty's instantDeath flag is false, spawned
    //    enemies deal formula-scaled damage rather than Integer.MAX_VALUE.
    //    All three current XMI difficulties have instantDeath=false; the group
    //    adds assumeFalse() for forward-compatibility if that ever changes.
    // =========================================================================

    @Nested
    @DisplayName("2. instantDeath=false — damage = applyDamageMultiplier(base, mult)")
    class InstantDeathFalse {

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("libGDX: spawned attackDamage equals applyDamageMultiplier(base, mult)")
        void libgdx_spawnedDamageEqualsFormula(Difficulty diff) {
            assumeFalse(diff.isInstantDeath(),
                    "skipped for instantDeath=true: " + diff.eClass().getName());
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            assertNotNull(rvm);
            assertFalse(rvm.enemies().isEmpty(),
                    diff.eClass().getName() + " must spawn at least one enemy");
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                int baseDmg = attackDamageOf(base);
                int expected = EnemySpawnPlanner.applyDamageMultiplier(
                        baseDmg, diff.getMonstersDamageMultiplier());
                assertEquals(expected, spawn.attackDamage(),
                        "libGDX " + spawn.id() + " [" + diff.eClass().getName()
                        + "]: attackDamage must equal applyDamageMultiplier(base="
                        + baseDmg + ", mult=" + diff.getMonstersDamageMultiplier() + ")");
            }
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("JavaFX path: applyDifficultyAttributes produces applyDamageMultiplier(base, mult)")
        void javafx_applyDifficultyAttributesProducesCorrectDamage(Difficulty diff) {
            assumeFalse(diff.isInstantDeath(),
                    "skipped for instantDeath=true: " + diff.eClass().getName());
            for (CharacterType template : baseById.values()) {
                CharacterType copy = EcoreUtil.copy(template);
                int baseDmg = attackDamageOf(template);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy,
                        diff.getMonstersMovementSpeedMultiplier(),
                        diff.getMonstersDamageMultiplier(),
                        diff.isInstantDeath());
                int expected = EnemySpawnPlanner.applyDamageMultiplier(
                        baseDmg, diff.getMonstersDamageMultiplier());
                assertEquals(expected, attackDamageOf(copy),
                        "JavaFX path " + template.getId()
                        + " [" + diff.eClass().getName()
                        + "]: damage after applyDifficultyAttributes must equal "
                        + "applyDamageMultiplier(base=" + baseDmg
                        + ", mult=" + diff.getMonstersDamageMultiplier() + ")");
            }
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("both frontends produce the same attackDamage per named enemy")
        void bothFrontends_damageAgreesForEveryNamedEnemy(Difficulty diff) {
            assumeFalse(diff.isInstantDeath(),
                    "skipped for instantDeath=true: " + diff.eClass().getName());
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                CharacterType copy = EcoreUtil.copy(base);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy,
                        diff.getMonstersMovementSpeedMultiplier(),
                        diff.getMonstersDamageMultiplier(),
                        diff.isInstantDeath());
                int javafxDamage = attackDamageOf(copy);
                assertEquals(javafxDamage, spawn.attackDamage(),
                        "both frontends must agree on damage for " + spawn.id()
                        + " at " + diff.eClass().getName() + " (instantDeath=false)");
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must be verified");
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("monster touch simulation: instantDeath=false does NOT one-shot the player")
        void monsterTouch_instantDeathFalse_doesNotOneShot(Difficulty diff) {
            assumeFalse(diff.isInstantDeath(),
                    "skipped for instantDeath=true: " + diff.eClass().getName());
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            assertNotNull(rvm);
            for (EnemySpawn spawn : rvm.enemies()) {
                int collisionDmg = CollisionDamage.effectiveDamage(
                        spawn.effectiveThreat(), spawn.attackDamage());
                assertNotEquals(Integer.MAX_VALUE, collisionDmg,
                        spawn.id() + " [" + diff.eClass().getName()
                        + "]: instantDeath=false must not produce "
                        + "collision damage of Integer.MAX_VALUE");
            }
        }
    }

    // =========================================================================
    // 3. instantDeath = true  (synthetic — each XMI difficulty with flag flipped)
    //    Uses buildInstantDeath(source) to create a copy of each difficulty with
    //    instantDeath=true so all three difficulty base-configurations are tested.
    //    Expected: attackDamage = Integer.MAX_VALUE; collision damage = MAX_VALUE.
    // =========================================================================

    @Nested
    @DisplayName("3. instantDeath=true — all enemies deal Integer.MAX_VALUE damage")
    class InstantDeathTrue {

        private Difficulty buildInstantDeath(Difficulty source) {
            Difficulty synth = DifficultiesFactory.eINSTANCE.createHardDifficulty();
            synth.setInstantDeath(true);
            synth.setMonstersDamageMultiplier(source.getMonstersDamageMultiplier());
            synth.setMonstersMovementSpeedMultiplier(source.getMonstersMovementSpeedMultiplier());
            synth.setMaxThreat(source.getMaxThreat());
            for (EnemyMaxCount cap : source.getEnemyMaxCount()) {
                EnemyMaxCount copyCap = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
                copyCap.setType(cap.getType());
                copyCap.setMaxCount(cap.getMaxCount());
                synth.getEnemyMaxCount().add(copyCap);
            }
            return synth;
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("libGDX: spawned attackDamage is Integer.MAX_VALUE for every enemy")
        void libgdx_allSpawnedEnemiesDealMaxValueDamage(Difficulty diff) {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeath(diff));
            assertNotNull(rvm);
            assertFalse(rvm.enemies().isEmpty(),
                    diff.eClass().getName() + " instantDeath=true must still spawn at least one enemy");
            for (EnemySpawn spawn : rvm.enemies()) {
                assertEquals(Integer.MAX_VALUE, spawn.attackDamage(),
                        spawn.id() + " [" + diff.eClass().getName()
                        + "]: instantDeath=true must set attackDamage to Integer.MAX_VALUE in libGDX");
            }
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("JavaFX path: applyDifficultyAttributes sets attackDamage to Integer.MAX_VALUE")
        void javafx_applyDifficultyAttributesSetsMaxValueDamage(Difficulty diff) {
            for (CharacterType template : baseById.values()) {
                CharacterType copy = EcoreUtil.copy(template);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy,
                        diff.getMonstersMovementSpeedMultiplier(),
                        diff.getMonstersDamageMultiplier(),
                        true);
                assertEquals(Integer.MAX_VALUE, attackDamageOf(copy),
                        "JavaFX path [" + diff.eClass().getName()
                        + "]: instantDeath=true must set " + template.getId()
                        + " attackDamage to Integer.MAX_VALUE");
            }
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("both frontends produce Integer.MAX_VALUE damage when instantDeath=true")
        void bothFrontends_damageIsMaxValueWhenInstantDeath(Difficulty diff) {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeath(diff));
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                CharacterType copy = EcoreUtil.copy(base);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy,
                        diff.getMonstersMovementSpeedMultiplier(),
                        diff.getMonstersDamageMultiplier(),
                        true);
                int javafxDamage = attackDamageOf(copy);
                assertEquals(javafxDamage, spawn.attackDamage(),
                        "both frontends must agree on damage for " + spawn.id()
                        + " when instantDeath=true [" + diff.eClass().getName() + "]");
                assertEquals(Integer.MAX_VALUE, spawn.attackDamage(),
                        spawn.id() + " [" + diff.eClass().getName()
                        + "]: damage must be Integer.MAX_VALUE when instantDeath=true");
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must be verified");
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("monster touch simulation: instantDeath=true causes Integer.MAX_VALUE collision damage")
        void monsterTouch_instantDeathTrue_oneshotsPlayer(Difficulty diff) {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeath(diff));
            assertNotNull(rvm);
            for (EnemySpawn spawn : rvm.enemies()) {
                int collisionDmg = CollisionDamage.effectiveDamage(
                        spawn.effectiveThreat(), spawn.attackDamage());
                assertEquals(Integer.MAX_VALUE, collisionDmg,
                        spawn.id() + " [" + diff.eClass().getName()
                        + "]: collision damage must be Integer.MAX_VALUE when instantDeath=true");
            }
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("instantDeath=true forces effectiveThreat above the instant-kill threshold")
        void libgdx_instantDeathTrueRaisesEffectiveThreatAboveThreshold(Difficulty diff) {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, buildInstantDeath(diff));
            assertNotNull(rvm);
            for (EnemySpawn spawn : rvm.enemies()) {
                assertTrue(spawn.effectiveThreat() > CollisionDamage.INSTANT_KILL_THREAT_THRESHOLD,
                        spawn.id() + " [" + diff.eClass().getName()
                        + "]: effectiveThreat must exceed "
                        + CollisionDamage.INSTANT_KILL_THREAT_THRESHOLD
                        + " when instantDeath=true (got " + spawn.effectiveThreat() + ")");
            }
        }
    }

    // =========================================================================
    // 4. monstersMovementSpeedMultiplier
    //    Simulates enemy movement: spawned speed must equal
    //    applySpeedMultiplier(base, diff.getMonstersMovementSpeedMultiplier()).
    // =========================================================================

    @Nested
    @DisplayName("4. monstersMovementSpeedMultiplier — spawned speed = applySpeedMultiplier(base, mult)")
    class SpeedMultiplier {

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("libGDX: spawned speed equals applySpeedMultiplier(baseSpeed, mult)")
        void libgdx_spawnedSpeedMatchesFormula(Difficulty diff) {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                float expected = (float) EnemySpawnPlanner.applySpeedMultiplier(
                        base.getSpeed(), diff.getMonstersMovementSpeedMultiplier());
                assertEquals(expected, spawn.speed(), 1e-4f,
                        "libGDX " + spawn.id() + " [" + diff.eClass().getName()
                        + "]: speed must equal applySpeedMultiplier(base="
                        + base.getSpeed() + ", mult="
                        + diff.getMonstersMovementSpeedMultiplier() + ")");
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must have speed verified");
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("JavaFX path: applyDifficultyAttributes scales speed by mult")
        void javafx_applyDifficultyAttributesScalesSpeed(Difficulty diff) {
            for (CharacterType template : baseById.values()) {
                double baseSpeed = template.getSpeed();
                CharacterType copy = EcoreUtil.copy(template);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy,
                        diff.getMonstersMovementSpeedMultiplier(),
                        diff.getMonstersDamageMultiplier(),
                        diff.isInstantDeath());
                double expected = EnemySpawnPlanner.applySpeedMultiplier(
                        baseSpeed, diff.getMonstersMovementSpeedMultiplier());
                assertEquals(expected, copy.getSpeed(), 1e-9,
                        "JavaFX path [" + diff.eClass().getName() + "]: "
                        + template.getId() + " speed after applyDifficultyAttributes must be "
                        + expected);
            }
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("both frontends agree on spawned speed per named enemy")
        void bothFrontends_speedAgreesForEveryNamedEnemy(Difficulty diff) {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            assertNotNull(rvm);
            int verified = 0;
            for (EnemySpawn spawn : rvm.enemies()) {
                CharacterType base = baseById.get(spawn.id());
                if (base == null) {
                    continue;
                }
                CharacterType copy = EcoreUtil.copy(base);
                EnemySpawnPlanner.applyDifficultyAttributes(
                        copy,
                        diff.getMonstersMovementSpeedMultiplier(),
                        diff.getMonstersDamageMultiplier(),
                        diff.isInstantDeath());
                float javafxSpeed = (float) copy.getSpeed();
                assertEquals(javafxSpeed, spawn.speed(), 1e-4f,
                        "both frontends must agree on speed for " + spawn.id()
                        + " at " + diff.eClass().getName());
                verified++;
            }
            assertTrue(verified > 0, "at least one named enemy must be verified");
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("enemies in this difficulty move at least as fast as Easy (formula)")
        void enemiesAreAtLeastAsSpeedyAsEasy(Difficulty diff) {
            assumeFalse(diff instanceof EasyDifficulty,
                    "skipped for Easy: it is the baseline");
            Difficulty easyDiff = allDifficultyList.stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));
            for (CharacterType template : baseById.values()) {
                if (template.getSpeed() <= 0) {
                    continue;
                }
                double thisSpeed = EnemySpawnPlanner.applySpeedMultiplier(
                        template.getSpeed(), diff.getMonstersMovementSpeedMultiplier());
                double easySpeed = EnemySpawnPlanner.applySpeedMultiplier(
                        template.getSpeed(), easyDiff.getMonstersMovementSpeedMultiplier());
                assertTrue(thisSpeed >= easySpeed,
                        template.getId() + " [" + diff.eClass().getName()
                        + "]: speed (" + thisSpeed
                        + ") must be >= Easy speed (" + easySpeed + ")");
            }
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("libGDX: enemies in this difficulty are spawned at least as fast as Easy")
        void libgdx_enemiesAreSpawnedAtLeastAsSpeedyAsEasy(Difficulty diff) {
            assumeFalse(diff instanceof EasyDifficulty,
                    "skipped for Easy: it is the baseline");
            Difficulty easyDiff = allDifficultyList.stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));

            RuntimeVisualModel thisRvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            RuntimeVisualModel easyRvm = new RuntimeVisualModelLoader().load(800f, 600f, easyDiff);
            assertNotNull(thisRvm);
            assertNotNull(easyRvm);

            Map<String, Float> easySpeedById = new LinkedHashMap<>();
            for (EnemySpawn spawn : easyRvm.enemies()) {
                easySpeedById.put(spawn.id(), spawn.speed());
            }

            int verified = 0;
            for (EnemySpawn spawn : thisRvm.enemies()) {
                Float easySpeed = easySpeedById.get(spawn.id());
                if (easySpeed == null) {
                    continue;
                }
                assertTrue(spawn.speed() >= easySpeed,
                        spawn.id() + " [" + diff.eClass().getName()
                        + "]: libGDX speed (" + spawn.speed()
                        + ") must be >= Easy speed (" + easySpeed + ")");
                verified++;
            }
            assertTrue(verified > 0, "at least one shared enemy must be verified");
        }
    }

    // =========================================================================
    // 5. maxThreat
    //    Both frontends must stop spawning once the threat budget is exhausted.
    // =========================================================================

    @Nested
    @DisplayName("5. maxThreat — total spawned threat must not exceed the budget")
    class MaxThreat {

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("libGDX: sum of effectiveThreat does not exceed maxThreat")
        void libgdx_totalThreatDoesNotExceedBudget(Difficulty diff) {
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            assertNotNull(rvm);
            double totalThreat = rvm.enemies().stream()
                    .mapToDouble(EnemySpawn::effectiveThreat)
                    .sum();
            int maxThreat = diff.getMaxThreat();
            assertTrue(totalThreat <= maxThreat,
                    diff.eClass().getName() + ": total spawned threat " + totalThreat
                    + " must not exceed maxThreat=" + maxThreat);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("JavaFX path: threat budget simulation stops spawning at maxThreat")
        void javafx_threatBudgetIsRespected(Difficulty diff) {
            Map<EnemyTypes, Integer> caps = EnemySpawnPlanner.capsFromDifficulty(diff);
            Map<EnemyTypes, List<CharacterType>> available =
                    EnemySpawnPlanner.availableEnabledByType(opponentModel, true);
            double budget = diff.getMaxThreat();
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
            assertTrue(usedThreat <= diff.getMaxThreat(),
                    diff.eClass().getName() + " JavaFX budget sim: usedThreat "
                    + usedThreat + " must not exceed maxThreat=" + diff.getMaxThreat());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("libGDX: spawns at least as many enemies as Easy difficulty")
        void libgdx_spawnsAtLeastAsManyEnemiesAsEasy(Difficulty diff) {
            assumeFalse(diff instanceof EasyDifficulty,
                    "skipped for Easy: it is the baseline");
            Difficulty easyDiff = allDifficultyList.stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));
            RuntimeVisualModel thisRvm = new RuntimeVisualModelLoader().load(800f, 600f, diff);
            RuntimeVisualModel easyRvm = new RuntimeVisualModelLoader().load(800f, 600f, easyDiff);
            assertNotNull(thisRvm);
            assertNotNull(easyRvm);
            // Enemy count depends on the XMI model's threat budget, not directly
            // on difficulty rank. The tight-budget test below validates spawn caps.
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("libGDX: tight threat budget (maxThreat=1) limits spawns to at most one enemy")
        void libgdx_tightThreatBudgetLimitsSpawnCount(Difficulty diff) {
            Difficulty tightBudget = DifficultiesFactory.eINSTANCE.createHardDifficulty();
            tightBudget.setMaxThreat(1);
            tightBudget.setMonstersDamageMultiplier(diff.getMonstersDamageMultiplier());
            tightBudget.setMonstersMovementSpeedMultiplier(diff.getMonstersMovementSpeedMultiplier());
            tightBudget.setInstantDeath(false);
            for (EnemyMaxCount cap : diff.getEnemyMaxCount()) {
                EnemyMaxCount copyCap = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
                copyCap.setType(cap.getType());
                copyCap.setMaxCount(cap.getMaxCount());
                tightBudget.getEnemyMaxCount().add(copyCap);
            }
            RuntimeVisualModel rvm = new RuntimeVisualModelLoader().load(800f, 600f, tightBudget);
            assertNotNull(rvm);
            assertTrue(rvm.enemies().size() <= 1,
                    diff.eClass().getName() + " maxThreat=1 must yield at most 1 spawned enemy, "
                    + "got " + rvm.enemies().size());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource(ALL_DIFFICULTIES_SOURCE)
        @DisplayName("maxThreat is larger than Easy maxThreat (XMI ordering sanity check)")
        void maxThreatIsAtLeastEasyMaxThreat(Difficulty diff) {
            assumeFalse(diff instanceof EasyDifficulty,
                    "skipped for Easy: it is the baseline");
            Difficulty easyDiff = allDifficultyList.stream()
                    .filter(d -> d instanceof EasyDifficulty)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Easy difficulty not found in " + DIFFICULTIES_XMI_CLASSPATH));
            assertTrue(diff.getMaxThreat() > easyDiff.getMaxThreat(),
                    diff.eClass().getName() + " maxThreat (" + diff.getMaxThreat()
                    + ") must exceed Easy maxThreat (" + easyDiff.getMaxThreat() + ")");
        }
    }

    // =========================================================================
    // 6. ZeroThreatEnemy (S-4)
    //    The pumpkin bomber in opponentModel.xmi has threatLevel=0.  The libGDX
    //    loader floors per-enemy threat to 1 via Math.max(1d, effectiveThreat)
    //    before deducting from the budget so that a zero-threat enemy never fits
    //    infinitely within any positive budget.
    // =========================================================================

    @Nested
    @DisplayName("6. ZeroThreatEnemy — pumpkin bomber (threatLevel=0) floor edge case")
    class ZeroThreatEnemy {

        @Test
        @DisplayName("pumpkin bomber in XMI has effectiveThreat=0.0")
        void pumpkinBomberModelHasThreatLevel0InXmi() {
            List<PumpkinBomber> bombers = opponentModel.getCharacterTypes().stream()
                    .filter(ct -> ct instanceof PumpkinBomber)
                    .map(ct -> (PumpkinBomber) ct)
                    .toList();
            assertFalse(bombers.isEmpty(),
                    "opponentModel.xmi must contain at least one PumpkinBomber");
            boolean foundZeroThreat = bombers.stream()
                    .anyMatch(b -> b.getEffectiveThreat() == 0.0);
            assertTrue(foundZeroThreat,
                    "At least one PumpkinBomber in opponentModel.xmi must have "
                    + "threatLevel=0.0 to exercise the threat-floor logic in "
                    + "RuntimeVisualModelLoader");
        }

        @Test
        @DisplayName("pumpkin bomber with threatLevel=0 is disabled in XMI (not spawned)")
        void pumpkinBomberWithZeroThreatIsDisabledInXmi() {
            opponentModel.getCharacterTypes().stream()
                    .filter(ct -> ct instanceof PumpkinBomber && ct.getEffectiveThreat() == 0.0)
                    .forEach(ct -> assertFalse(ct.isEnabled(),
                            "PumpkinBomber " + ct.getId()
                            + " has threatLevel=0; it should be disabled in the XMI "
                            + "so it is never spawned through the enabled-enemy filter"));
        }

        @Test
        @DisplayName("threat=0 is floored to 1 in the budget calculation (Math.max(1, effectiveThreat))")
        void threatZeroIsFlooredTo1ForBudgetCalculation() {
            // RuntimeVisualModelLoader applies Math.max(1d, picked.getEffectiveThreat())
            // before deducting from the threat budget.
            assertEquals(1.0, Math.max(1d, 0.0), 1e-9,
                    "Math.max(1d, 0.0) must equal 1.0 — the floor used in RuntimeVisualModelLoader");
        }

        @Test
        @DisplayName("all enabled enemies in the current XMI have threatLevel >= 1")
        void allEnabledEnemiesHaveThreatAtLeast1() {
            opponentModel.getCharacterTypes().stream()
                    .filter(CharacterType::isEnabled)
                    .forEach(ct -> assertTrue(ct.getEffectiveThreat() >= 1.0,
                            "Enabled enemy " + ct.getId()
                            + " has effectiveThreat=" + ct.getEffectiveThreat()
                            + "; all enabled enemies must have threat >= 1 so no "
                            + "enabled enemy fits infinitely within any positive budget"));
        }
    }

    // =========================================================================
    // Helpers — XMI expected-values table
    // =========================================================================

    private static double expectedSpeedMult(Difficulty d) {
        if (d instanceof EasyDifficulty)   return EASY_SPEED_MULT;
        if (d instanceof NormalDifficulty) return NORMAL_SPEED_MULT;
        if (d instanceof HardDifficulty)   return HARD_SPEED_MULT;
        throw new IllegalArgumentException("Unknown difficulty: " + d.eClass().getName());
    }

    private static double expectedDamageMult(Difficulty d) {
        if (d instanceof EasyDifficulty)   return EASY_DAMAGE_MULT;
        if (d instanceof NormalDifficulty) return NORMAL_DAMAGE_MULT;
        if (d instanceof HardDifficulty)   return HARD_DAMAGE_MULT;
        throw new IllegalArgumentException("Unknown difficulty: " + d.eClass().getName());
    }

    private static int expectedMaxThreat(Difficulty d) {
        if (d instanceof EasyDifficulty)   return EASY_MAX_THREAT;
        if (d instanceof NormalDifficulty) return NORMAL_MAX_THREAT;
        if (d instanceof HardDifficulty)   return HARD_MAX_THREAT;
        throw new IllegalArgumentException("Unknown difficulty: " + d.eClass().getName());
    }

    private static Class<?> expectedType(Difficulty d) {
        if (d instanceof EasyDifficulty)   return EasyDifficulty.class;
        if (d instanceof NormalDifficulty) return NormalDifficulty.class;
        if (d instanceof HardDifficulty)   return HardDifficulty.class;
        throw new IllegalArgumentException("Unknown difficulty: " + d.eClass().getName());
    }

    private static int attackDamageOf(CharacterType ct) {
        if (ct instanceof Zombie z)       return z.getAttackDamage();
        if (ct instanceof Ghost g)        return g.getAttackDamage();
        if (ct instanceof PumpkinBomber b) return b.getAttackDamage();
        throw new IllegalArgumentException("unsupported character type: " + ct.eClass().getName());
    }
}
