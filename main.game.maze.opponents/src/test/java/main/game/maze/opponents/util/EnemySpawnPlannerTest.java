package main.game.maze.opponents.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;

class EnemySpawnPlannerTest {

    private static final String OCL_DELEGATE_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL";

    @BeforeAll
    static void setup() {
        EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.put(
                OCL_DELEGATE_URI, new OCLInvocationDelegateFactory.Global());
        EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(
                OCL_DELEGATE_URI, new OCLSettingDelegateFactory.Global());
        EValidator.ValidationDelegate.Registry.INSTANCE.put(
                OCL_DELEGATE_URI, new OCLValidationDelegateFactory.Global());
        OpponentsPackage.eINSTANCE.eClass();
    }

    @Test
    void capsForNullDifficultyAreEmpty() {
        assertTrue(EnemySpawnPlanner.capsFromDifficulty(null).isEmpty());
    }

    @Test
    void capsReflectDifficultyEnemyMaxCounts() {
        Difficulty d = DifficultiesFactory.eINSTANCE.createNormalDifficulty();
        addCap(d, EnemyTypes.ZOMBIE, 3);
        addCap(d, EnemyTypes.GHOST, 2);
        addCap(d, EnemyTypes.PUMPKINBOMBER, 1);

        Map<EnemyTypes, Integer> caps = EnemySpawnPlanner.capsFromDifficulty(d);

        assertEquals(3, caps.get(EnemyTypes.ZOMBIE));
        assertEquals(2, caps.get(EnemyTypes.GHOST));
        assertEquals(1, caps.get(EnemyTypes.PUMPKINBOMBER));
    }

    @Test
    void negativeMaxCountsClampToZero() {
        Difficulty d = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        addCap(d, EnemyTypes.ZOMBIE, -5);
        assertEquals(0, EnemySpawnPlanner.capsFromDifficulty(d).get(EnemyTypes.ZOMBIE));
    }

    @Test
    void availableEnabledByTypeFiltersDisabledAndSortsByThreat() {
        OpponentModel model = OpponentsFactory.eINSTANCE.createOpponentModel();

        Zombie weakZombie = OpponentsFactory.eINSTANCE.createZombie();
        weakZombie.setEnabled(true);
        weakZombie.setHealth(10);
        weakZombie.setThreatLevel(0.5d);

        Zombie strongZombie = OpponentsFactory.eINSTANCE.createZombie();
        strongZombie.setEnabled(true);
        strongZombie.setHealth(100);
        strongZombie.setThreatLevel(5.0d);

        Zombie disabledZombie = OpponentsFactory.eINSTANCE.createZombie();
        disabledZombie.setEnabled(false);
        disabledZombie.setHealth(50);
        disabledZombie.setThreatLevel(9.0d);

        Ghost ghost = OpponentsFactory.eINSTANCE.createGhost();
        ghost.setEnabled(true);

        PumpkinBomber bomber = OpponentsFactory.eINSTANCE.createPumpkinBomber();
        bomber.setEnabled(true);

        model.getCharacterTypes().add(strongZombie);
        model.getCharacterTypes().add(weakZombie);
        model.getCharacterTypes().add(disabledZombie);
        model.getCharacterTypes().add(ghost);
        model.getCharacterTypes().add(bomber);

        Map<EnemyTypes, List<CharacterType>> sorted = EnemySpawnPlanner.availableEnabledByType(model, true);
        List<CharacterType> zombies = sorted.get(EnemyTypes.ZOMBIE);
        assertNotNull(zombies);
        assertEquals(2, zombies.size(), "Disabled zombie must be filtered out");
        assertTrue(zombies.get(0).getEffectiveThreat() <= zombies.get(1).getEffectiveThreat(),
                "Lists must be sorted ascending by effective threat");
        assertNotNull(sorted.get(EnemyTypes.GHOST));
        assertNotNull(sorted.get(EnemyTypes.PUMPKINBOMBER));
    }

    @Test
    void availableEnabledByTypeOnNullModelIsEmpty() {
        assertTrue(EnemySpawnPlanner.availableEnabledByType(null, false).isEmpty());
        assertTrue(EnemySpawnPlanner.availableEnabledByType(null, true).isEmpty());
    }

    @Test
    void toEnemyTypeMapsAllConcreteSubclasses() {
        assertEquals(EnemyTypes.ZOMBIE, EnemySpawnPlanner.toEnemyType(OpponentsFactory.eINSTANCE.createZombie()));
        assertEquals(EnemyTypes.GHOST, EnemySpawnPlanner.toEnemyType(OpponentsFactory.eINSTANCE.createGhost()));
        assertEquals(EnemyTypes.PUMPKINBOMBER,
                EnemySpawnPlanner.toEnemyType(OpponentsFactory.eINSTANCE.createPumpkinBomber()));
        assertNull(EnemySpawnPlanner.toEnemyType(null));
    }

    @Test
    void applyDamageMultiplierRoundsAndClampsNegatives() {
        assertEquals(0, EnemySpawnPlanner.applyDamageMultiplier(0, 1.0d));
        assertEquals(1, EnemySpawnPlanner.applyDamageMultiplier(10, 0d));
        assertEquals(15, EnemySpawnPlanner.applyDamageMultiplier(10, 1.5d));
        assertEquals(20, EnemySpawnPlanner.applyDamageMultiplier(10, 2.0d));
        assertEquals(8, EnemySpawnPlanner.applyDamageMultiplier(10, 0.75d));
        assertEquals(0, EnemySpawnPlanner.applyDamageMultiplier(-10, 1.0d));
        assertEquals(0, EnemySpawnPlanner.applyDamageMultiplier(10, -2.0d));
    }

    @Test
    void applyDifficultyAttributesUsesSharedSpeedAndDamageRules() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setSpeed(4.0d);
        zombie.setAttackDamage(10);

        EnemySpawnPlanner.applyDifficultyAttributes(zombie, 1.5d, 0.5d, false);
        assertEquals(6.0d, zombie.getSpeed(), 1e-9);
        assertEquals(5, zombie.getAttackDamage());

        EnemySpawnPlanner.applyDifficultyAttributes(zombie, 2.0d, 10d, true);
        assertEquals(Integer.MAX_VALUE, zombie.getAttackDamage());
    }

    @Test
    void resolveRuntimeBehaviorPreservesAggressiveAndNormalizesPassiveToWander() {
        Random seeded = new Random(1337L);
        assertEquals(BehaviorType.AGGRESSIVE,
                EnemySpawnPlanner.resolveRuntimeBehavior(BehaviorType.AGGRESSIVE, seeded));
        assertEquals(BehaviorType.WANDER,
                EnemySpawnPlanner.resolveRuntimeBehavior(BehaviorType.PASSIVE, seeded));
    }

    @Test
    void resolveRuntimeBehaviorProducesPatrolWanderMixDeterministically() {
        Random seeded = new Random(1337L);
        BehaviorType first = EnemySpawnPlanner.resolveRuntimeBehavior(BehaviorType.WANDER, seeded);
        BehaviorType second = EnemySpawnPlanner.resolveRuntimeBehavior(BehaviorType.WANDER, seeded);
        assertTrue(first == BehaviorType.WANDER || first == BehaviorType.PATROL);
        assertTrue(second == BehaviorType.WANDER || second == BehaviorType.PATROL);
        assertTrue(first != second,
                "Seeded draw should produce a stable non-degenerate mix for parity checks");
    }

    @Test
    void aggressiveCapForDifficultyMatchesGameplayContract() {
        Difficulty easy = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        Difficulty normal = DifficultiesFactory.eINSTANCE.createNormalDifficulty();
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        assertEquals(1, EnemySpawnPlanner.aggressiveCapForDifficulty(easy));
        assertEquals(3, EnemySpawnPlanner.aggressiveCapForDifficulty(normal));
        assertEquals(5, EnemySpawnPlanner.aggressiveCapForDifficulty(hard));
        assertEquals(Integer.MAX_VALUE, EnemySpawnPlanner.aggressiveCapForDifficulty(null));
    }

    @Test
    void resolveRuntimeBehaviorWithAggressiveCapDowngradesOverflowToWander() {
        Random seeded = new Random(42L);
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        // Under the cap (5): AGGRESSIVE stays AGGRESSIVE
        for (int assigned = 0; assigned < 5; assigned++) {
            assertEquals(BehaviorType.AGGRESSIVE,
                    EnemySpawnPlanner.resolveRuntimeBehaviorWithAggressiveCap(
                            BehaviorType.AGGRESSIVE, hard, assigned, seeded));
        }
        // At and above the cap: AGGRESSIVE downgrades to WANDER
        assertEquals(BehaviorType.WANDER,
                EnemySpawnPlanner.resolveRuntimeBehaviorWithAggressiveCap(
                        BehaviorType.AGGRESSIVE, hard, 5, seeded));
        assertEquals(BehaviorType.WANDER,
                EnemySpawnPlanner.resolveRuntimeBehaviorWithAggressiveCap(
                        BehaviorType.AGGRESSIVE, hard, 99, seeded));
    }

    // -----------------------------------------------------------------------
    // clampToCapLimit — F8 enforcement
    // -----------------------------------------------------------------------

    @Test
    void clampToCapLimit_requestedBelowCap_unchanged() {
        Map<EnemyTypes, Integer> caps = capsMap(EnemyTypes.ZOMBIE, 3);
        assertEquals(2, EnemySpawnPlanner.clampToCapLimit(EnemyTypes.ZOMBIE, 2, caps));
    }

    @Test
    void clampToCapLimit_requestedEqualsCap_unchanged() {
        Map<EnemyTypes, Integer> caps = capsMap(EnemyTypes.ZOMBIE, 3);
        assertEquals(3, EnemySpawnPlanner.clampToCapLimit(EnemyTypes.ZOMBIE, 3, caps));
    }

    @Test
    void clampToCapLimit_requestedExceedsCap_clamped() {
        Map<EnemyTypes, Integer> caps = capsMap(EnemyTypes.ZOMBIE, 3);
        assertEquals(3, EnemySpawnPlanner.clampToCapLimit(EnemyTypes.ZOMBIE, 5, caps));
    }

    @Test
    void clampToCapLimit_zeroCap_returnsZero() {
        Map<EnemyTypes, Integer> caps = capsMap(EnemyTypes.PUMPKINBOMBER, 0);
        assertEquals(0, EnemySpawnPlanner.clampToCapLimit(EnemyTypes.PUMPKINBOMBER, 4, caps));
    }

    @Test
    void clampToCapLimit_noCapForType_returnsRequested() {
        Map<EnemyTypes, Integer> caps = capsMap(EnemyTypes.ZOMBIE, 3);
        assertEquals(7, EnemySpawnPlanner.clampToCapLimit(EnemyTypes.GHOST, 7, caps));
    }

    @Test
    void clampToCapLimit_emptyCaps_returnsRequested() {
        assertEquals(5, EnemySpawnPlanner.clampToCapLimit(EnemyTypes.ZOMBIE, 5, Map.of()));
    }

    @Test
    void clampToCapLimit_negativeRequested_returnsZero() {
        Map<EnemyTypes, Integer> caps = capsMap(EnemyTypes.ZOMBIE, 3);
        assertEquals(0, EnemySpawnPlanner.clampToCapLimit(EnemyTypes.ZOMBIE, -1, caps));
    }

    private static Map<EnemyTypes, Integer> capsMap(EnemyTypes type, int cap) {
        EnumMap<EnemyTypes, Integer> m = new EnumMap<>(EnemyTypes.class);
        m.put(type, cap);
        return m;
    }

    private static void addCap(Difficulty d, EnemyTypes type, int max) {
        EnemyMaxCount c = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
        c.setType(type);
        c.setMaxCount(max);
        d.getEnemyMaxCount().add(c);
    }
}
