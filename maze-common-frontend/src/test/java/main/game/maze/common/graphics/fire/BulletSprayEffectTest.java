package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bullet spray (gatling gun): separate bullets in a narrow cone, not a beam")
class BulletSprayEffectTest {

    private static List<FirePuff> heads(List<FirePuff> puffs) {
        return puffs.stream().filter(p -> p.layer() == FirePuff.Layer.CORE).toList();
    }

    @Test
    @DisplayName("it is over after its lifetime and empty outside its life")
    void lifetime() {
        BulletSprayEffect spray = new BulletSprayEffect(0, 0, 300, 0, 9);
        assertTrue(spray.lifetimeSeconds() > 0);
        assertTrue(spray.puffs(1.0, 0).isEmpty());
        assertTrue(spray.puffs(Double.NaN, 0).isEmpty());
    }

    @Test
    @DisplayName("each bullet is one small bright head; there is no continuous line like the laser beam")
    void bulletsAreSeparate() {
        BulletSprayEffect spray = new BulletSprayEffect(0, 0, 400, 0, 9);
        int most = 0;
        for (double progress = 0.05; progress < 1.0; progress += 0.05) {
            int bullets = heads(spray.puffs(progress, 0)).size();
            assertTrue(bullets <= 9, "never more heads than bullets: " + bullets);
            most = Math.max(most, bullets);
        }
        assertTrue(most >= 3, "several bullets are in the air at once: " + most);
        int laserPuffs = new LaserBeamEffect(0, 0, 400, 0).puffs(0.3, 0).size();
        assertTrue(spray.puffs(0.5, 0).size() < laserPuffs, "far fewer puffs than a continuous beam");
    }

    @Test
    @DisplayName("the bullets fan out sideways but fly the way of the shot")
    void spreadAroundTheLine() {
        BulletSprayEffect spray = new BulletSprayEffect(0, 100, 400, 100, 9);
        double minY = 1e9;
        double maxY = -1e9;
        for (double progress = 0.05; progress < 1.0; progress += 0.02) {
            for (FirePuff head : heads(spray.puffs(progress, 0))) {
                minY = Math.min(minY, head.y());
                maxY = Math.max(maxY, head.y());
                assertTrue(head.x() >= -1 && head.x() <= 430, "flies along the shot: " + head.x());
            }
        }
        assertTrue(maxY - minY > 10, "the bullets spread out: " + (maxY - minY));
        assertTrue(maxY - minY < 120, "but stay in a narrow cone: " + (maxY - minY));
    }

    @Test
    @DisplayName("bullets land at the end of the shot and strike with sparks")
    void impacts() {
        BulletSprayEffect spray = new BulletSprayEffect(0, 0, 400, 0, 9);
        boolean sparkNearTarget = false;
        for (double progress = 0.3; progress < 1.0; progress += 0.02) {
            for (FirePuff puff : spray.puffs(progress, 0)) {
                if (puff.layer() == FirePuff.Layer.EMBER && Math.abs(puff.x() - 400) < 30) {
                    sparkNearTarget = true;
                }
            }
        }
        assertTrue(sparkNearTarget, "sparks where the bullets hit");
    }

    @Test
    @DisplayName("a puff never leaves the bounds, and the effect is the same every time")
    void boundsAndDeterminism() {
        BulletSprayEffect spray = new BulletSprayEffect(20, 30, 220, 180, 9);
        double[] bounds = spray.bounds();
        for (double progress = 0; progress < 1.0; progress += 0.05) {
            List<FirePuff> puffs = spray.puffs(progress, 1.0);
            assertEquals(puffs, spray.puffs(progress, 1.0));
            for (FirePuff puff : puffs) {
                assertTrue(puff.x() >= bounds[0] && puff.x() <= bounds[2] && puff.y() >= bounds[1] && puff.y() <= bounds[3],
                        "inside the bounds: " + puff);
            }
        }
    }

    @Test
    @DisplayName("a short spray of three bullets is a single enemy round")
    void smallSpray() {
        BulletSprayEffect round = new BulletSprayEffect(0, 0, 300, 0, 3);
        int most = 0;
        for (double progress = 0.05; progress < 1.0; progress += 0.05) {
            most = Math.max(most, heads(round.puffs(progress, 0)).size());
        }
        assertTrue(most >= 1 && most <= 3);
        assertFalse(round.puffs(0.4, 0).isEmpty());
    }
}
