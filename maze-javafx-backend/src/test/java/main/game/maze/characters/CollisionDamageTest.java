package main.game.maze.characters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Covers F15: enemies whose model threatLevel exceeds 100 should
 * instantly kill the player on contact, irrespective of attackDamage.
 */
public class CollisionDamageTest {

    @Test
    @DisplayName("threatLevel at or below 100 returns the base attack damage")
    void baseDamageReturnedWhenThreatBelowOrAtThreshold() {
        assertEquals(5, CollisionDamage.effectiveDamage(0.0, 5));
        assertEquals(5, CollisionDamage.effectiveDamage(50.0, 5));
        assertEquals(5, CollisionDamage.effectiveDamage(100.0, 5));
    }

    @Test
    @DisplayName("threatLevel above 100 returns Integer.MAX_VALUE (instant kill)")
    void instantKillWhenThreatAboveThreshold() {
        assertEquals(Integer.MAX_VALUE, CollisionDamage.effectiveDamage(100.0001, 5));
        assertEquals(Integer.MAX_VALUE, CollisionDamage.effectiveDamage(150.0, 5));
        assertEquals(Integer.MAX_VALUE, CollisionDamage.effectiveDamage(9999.0, 1));
    }

    @Test
    @DisplayName("negative base damage clamps to zero when below threshold")
    void negativeBaseDamageClampsToZero() {
        assertEquals(0, CollisionDamage.effectiveDamage(0.0, -10));
    }

    @Test
    @DisplayName("instant-kill applies even when base damage is zero or negative")
    void instantKillIgnoresBaseDamage() {
        assertEquals(Integer.MAX_VALUE, CollisionDamage.effectiveDamage(101.0, 0));
        assertEquals(Integer.MAX_VALUE, CollisionDamage.effectiveDamage(101.0, -50));
    }
}
