package main.game.maze.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MortalityRules")
class MortalityRulesTest {

    @Test
    @DisplayName("max HP comes from the model health, at least 1")
    void maxHitPoints() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setHealth(120);
        assertEquals(120, MortalityRules.maxHitPoints(zombie));
        zombie.setHealth(0);
        assertEquals(1, MortalityRules.maxHitPoints(zombie));
        assertEquals(MortalityRules.DEFAULT_MAX_HIT_POINTS, MortalityRules.maxHitPoints(null));
    }

    @Test
    @DisplayName("zombies resurrect after the model time, or a default when the model says 0; others stay dead")
    void resurrection() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setResurrectionTime(0);
        assertEquals(MortalityRules.DEFAULT_ZOMBIE_RESURRECTION_MS, MortalityRules.resurrectionTimeMs(zombie));
        zombie.setResurrectionTime(3);
        assertEquals(3_000, MortalityRules.resurrectionTimeMs(zombie));

        Ghost ghost = OpponentsFactory.eINSTANCE.createGhost();
        PumpkinBomber bomber = OpponentsFactory.eINSTANCE.createPumpkinBomber();
        assertEquals(0, MortalityRules.resurrectionTimeMs(ghost));
        assertEquals(0, MortalityRules.resurrectionTimeMs(bomber));
        assertEquals(0, MortalityRules.resurrectionTimeMs(null));
        assertTrue(MortalityRules.RESPAWN_INVULNERABILITY_SECONDS > 0);
    }
}
