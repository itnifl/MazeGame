package main.game.maze.characters;

import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import main.game.maze.opponents.util.EnemySpawnPlanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the difficulty speed multiplier flows correctly from
 * EnemySpawnPlanner through the model into a spawned ZombieCharacter,
 * ensuring parity between JavaFX and libGDX frontends (both use
 * EnemySpawnPlanner.applyDifficultyAttributes).
 */
class FxDifficultySpeedPropagationTest {

    // -----------------------------------------------------------------------
    // EnemySpawnPlanner.applySpeedMultiplier — shared utility
    // -----------------------------------------------------------------------

    @ParameterizedTest(name = "base={0} × multiplier={1} = {2}")
    @CsvSource({
        "3.0, 0.80, 2.4",   // Easy
        "3.0, 1.00, 3.0",   // Normal
        "3.0, 1.15, 3.45",  // Hard
        "0.0, 1.15, 0.0",   // zero base stays zero
        "3.0, 0.0,  0.0",   // zero multiplier coerced to 0
    })
    void applySpeedMultiplier_knownDifficultyValues(double base, double multiplier, double expected) {
        double result = EnemySpawnPlanner.applySpeedMultiplier(base, multiplier);
        assertEquals(expected, result, 0.0001,
                String.format("speed %.2f × %.2f must equal %.4f", base, multiplier, expected));
    }

    @Test
    void applySpeedMultiplier_negativeMultiplier_treatedAsZero() {
        double result = EnemySpawnPlanner.applySpeedMultiplier(3.0, -1.0);
        assertEquals(0.0, result, 0.0001,
                "Negative multiplier must be coerced to zero (no backward movement)");
    }

    @Test
    void applySpeedMultiplier_negativeBase_treatedAsZero() {
        double result = EnemySpawnPlanner.applySpeedMultiplier(-5.0, 1.15);
        assertEquals(0.0, result, 0.0001,
                "Negative base speed must be clamped to zero before scaling");
    }

    // -----------------------------------------------------------------------
    // EnemySpawnPlanner.applyDifficultyAttributes — model mutation
    // -----------------------------------------------------------------------

    @Test
    void applyDifficultyAttributes_hardMultiplier_updatesModelSpeed() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setSpeed(3.0);

        EnemySpawnPlanner.applyDifficultyAttributes(zombie, 1.15, 1.0, false);

        assertEquals(3.45, zombie.getSpeed(), 0.0001,
                "Hard difficulty (×1.15) must update the model speed to 3.45");
    }

    @Test
    void applyDifficultyAttributes_easyMultiplier_updatesModelSpeed() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setSpeed(3.0);

        EnemySpawnPlanner.applyDifficultyAttributes(zombie, 0.80, 1.0, false);

        assertEquals(2.4, zombie.getSpeed(), 0.0001,
                "Easy difficulty (×0.80) must reduce the model speed to 2.4");
    }

    @Test
    void applyDifficultyAttributes_normalMultiplier_speedUnchanged() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setSpeed(3.0);

        EnemySpawnPlanner.applyDifficultyAttributes(zombie, 1.0, 1.0, false);

        assertEquals(3.0, zombie.getSpeed(), 0.0001,
                "Normal difficulty (×1.0) must leave speed unchanged");
    }

    @Test
    void applyDifficultyAttributes_nullCharacter_doesNotThrow() {
        assertDoesNotThrow(() -> EnemySpawnPlanner.applyDifficultyAttributes(null, 1.15, 1.0, false),
                "Null characterType must be silently ignored");
    }

    // -----------------------------------------------------------------------
    // ZombieCharacter picks up the difficulty-scaled speed from the model
    // -----------------------------------------------------------------------

    @Test
    void zombieCharacter_hardDifficulty_modelSpeedReflectsScale() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setSpeed(3.0);
        zombie.setHealth(10);
        zombie.setImageBase("/main/game/maze/zombie1.png");

        EnemySpawnPlanner.applyDifficultyAttributes(zombie, 1.15, 1.0, false);

        // Confirm the model carries the difficulty-adjusted speed before construction.
        assertEquals(3.45, zombie.getSpeed(), 0.0001,
                "Model speed must be 3.45 (hard ×1.15) prior to character construction");

        // ZombieCharacter passes mapSpeed(model.getSpeed()) = max(1.0, 3.45) = 3.45
        // to super(); we verify the round-trip through the model accessor.
        ZombieCharacter character = new ZombieCharacter(null, 0, 0, zombie);
        assertEquals(3.45, character.getModel().getSpeed(), 0.0001,
                "ZombieCharacter.getModel().getSpeed() must reflect the hard-difficulty scale");
    }

    @Test
    void zombieCharacter_easyDifficulty_modelSpeedReflectsScale() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setSpeed(3.0);
        zombie.setHealth(10);
        zombie.setImageBase("/main/game/maze/zombie1.png");

        EnemySpawnPlanner.applyDifficultyAttributes(zombie, 0.80, 1.0, false);

        ZombieCharacter character = new ZombieCharacter(null, 0, 0, zombie);
        assertEquals(2.4, character.getModel().getSpeed(), 0.0001,
                "ZombieCharacter.getModel().getSpeed() must reflect the easy-difficulty scale");
    }

    @Test
    void zombieCharacter_hardDifficulty_fasterThanEasy() {
        Zombie hardZombie = OpponentsFactory.eINSTANCE.createZombie();
        hardZombie.setSpeed(3.0);
        hardZombie.setHealth(10);
        hardZombie.setImageBase("/main/game/maze/zombie1.png");
        EnemySpawnPlanner.applyDifficultyAttributes(hardZombie, 1.15, 1.0, false);

        Zombie easyZombie = OpponentsFactory.eINSTANCE.createZombie();
        easyZombie.setSpeed(3.0);
        easyZombie.setHealth(10);
        easyZombie.setImageBase("/main/game/maze/zombie1.png");
        EnemySpawnPlanner.applyDifficultyAttributes(easyZombie, 0.80, 1.0, false);

        ZombieCharacter hard = new ZombieCharacter(null, 0, 0, hardZombie);
        ZombieCharacter easy = new ZombieCharacter(null, 0, 0, easyZombie);

        assertTrue(hard.getModel().getSpeed() > easy.getModel().getSpeed(),
                "Hard difficulty zombie must be faster than easy difficulty zombie");
    }
}
