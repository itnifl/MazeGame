package main.game.maze.game.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import main.game.maze.common.graphics.fire.BulletSprayEffect;
import main.game.maze.common.graphics.fire.LaserBeamEffect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Shot effects: the gatling gun sprays bullets, the laser fires a beam")
class ShotEffectsTest {

    @Test
    @DisplayName("the gatling gun sprays bullets")
    void gatlingSpraysBullets() {
        assertInstanceOf(BulletSprayEffect.class, ShotEffects.forWeapon(PickupKind.GATLING_GUN, 0, 0, 100, 0));
    }

    @Test
    @DisplayName("the laser rifle fires a beam")
    void laserFiresABeam() {
        assertInstanceOf(LaserBeamEffect.class, ShotEffects.forWeapon(PickupKind.LASER_RIFLE, 0, 0, 100, 0));
    }

    @Test
    @DisplayName("one sergeant round is a small spray")
    void enemyRoundIsASmallSpray() {
        var round = assertInstanceOf(BulletSprayEffect.class, ShotEffects.enemyRound(0, 0, 100, 0));
        assertEquals(ShotEffects.ENEMY_ROUND_BULLETS, round.bullets());
    }
}
