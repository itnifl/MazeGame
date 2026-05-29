package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.game.maze.libgdx.model.EnemySpawn;

class PlayerCombatStateServiceTest {

    @Test
    void contactDamageReducesHpRatioAndAppliesFlash() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        EnemySpawn enemy = new EnemySpawn("z1", "/main/game/maze/zombie1-down.png", 10f, 10f, 40f, 1f, 5, 0, "");
        var frame = service.update(1f / 30f, 10f, 10f, 15f, List.of(enemy));

        assertTrue(frame.hpRatio() < 1f);
        assertTrue(frame.tintGreen() < 1f);
        assertFalse(frame.dead());
    }

    @Test
    void lethalDamageMarksPlayerDead() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(20);

        EnemySpawn enemy = new EnemySpawn("z2", "/main/game/maze/zombie1-down.png", 10f, 10f, 40f, 101f, 1, 0, "");
        var frame = service.update(1f / 30f, 10f, 10f, 15f, List.of(enemy));

        assertTrue(frame.dead());
        assertTrue(service.isDead());
    }

    @Test
    void infectionTickEventuallyAppliesOngoingDamage() {
        PlayerCombatStateService service = new PlayerCombatStateService(() -> 0.0);
        service.reset(1000);

        EnemySpawn enemy = new EnemySpawn("z3", "/main/game/maze/zombie1-down.png", 10f, 10f, 40f, 1f, 100, 100, "/main/game/maze/zombieScream.mp3");
        service.update(1f / 30f, 10f, 10f, 15f, List.of(enemy));

        boolean hpDroppedBelowImmediateHit = false;
        for (int i = 0; i < 240; i++) {
            var frame = service.update(1f / 30f, 300f, 300f, 15f, List.of(enemy));
            if (frame.hpRatio() < 0.90f) {
                hpDroppedBelowImmediateHit = true;
                break;
            }
        }

        assertTrue(hpDroppedBelowImmediateHit);
    }
}