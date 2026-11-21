package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.libgdx.game.PlayerCombatStateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A lethal hazard hit counts in the same frame, so it suppresses a win on the goal")
class DeadAfterExtraDamageTest {

    @Test
    @DisplayName("damage that kills the player after the combat frame was read makes the frame dead")
    void lethalHitAfterTheFrame() {
        PlayerCombatStateService combat = new PlayerCombatStateService();
        combat.reset(100);
        boolean frameDead = false;
        combat.applyDirectDamage(500);
        assertTrue(GdxGameCombatAndEnemyFlowSupport.deadAfterExtraDamage(frameDead, combat));
    }

    @Test
    @DisplayName("an alive player and a non-lethal hit keep the frame as it was")
    void nonLethalHit() {
        PlayerCombatStateService combat = new PlayerCombatStateService();
        combat.reset(100);
        combat.applyDirectDamage(20);
        assertFalse(GdxGameCombatAndEnemyFlowSupport.deadAfterExtraDamage(false, combat));
        assertTrue(GdxGameCombatAndEnemyFlowSupport.deadAfterExtraDamage(true, combat), "a frame that was dead stays dead");
        assertFalse(GdxGameCombatAndEnemyFlowSupport.deadAfterExtraDamage(false, null));
    }
}
