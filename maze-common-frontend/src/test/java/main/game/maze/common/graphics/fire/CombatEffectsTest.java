package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Combat effects (laser, sledgehammer, hit reactions, shield, pickup)")
class CombatEffectsTest {

    private static void assertInsideBounds(PuffEffect effect) {
        double[] b = effect.bounds();
        for (double p = 0; p < 1; p += 0.02) {
            for (FirePuff puff : effect.puffs(p, p)) {
                assertTrue(puff.x() - puff.radius() >= b[0] - 1e-9 && puff.x() + puff.radius() <= b[2] + 1e-9
                        && puff.y() - puff.radius() >= b[1] - 1e-9 && puff.y() + puff.radius() <= b[3] + 1e-9,
                        effect + " puff outside bounds at " + p + ": " + puff);
            }
        }
        assertTrue(effect.puffs(1.0, 0).isEmpty(), "finished effects draw nothing");
    }

    private static List<FirePuff> layer(List<FirePuff> puffs, FirePuff.Layer layer) {
        return puffs.stream().filter(p -> p.layer() == layer).toList();
    }

    @Test
    @DisplayName("laser beam: a bright line from muzzle to the hit point, sparks at the end")
    void laserBeam() {
        LaserBeamEffect beam = new LaserBeamEffect(10, 50, 210, 50);
        assertInsideBounds(beam);
        List<FirePuff> core = layer(beam.puffs(0.1, 0), FirePuff.Layer.CORE);
        assertTrue(core.stream().anyMatch(p -> p.x() < 20) && core.stream().anyMatch(p -> p.x() > 200),
                "the beam spans muzzle to hit point");
        assertFalse(layer(beam.puffs(0.1, 0), FirePuff.Layer.EMBER).isEmpty(), "sparks where it hits");
        assertTrue(beam.lifetimeSeconds() < 0.3, "a shot is a flash, not a lingering ray");
    }

    @Test
    @DisplayName("sledgehammer: a giant swing, the head sweeping a wide arc around the player")
    void sledgeSwing() {
        SledgeSwingEffect swing = new SledgeSwingEffect(100, 100, 1, 0, 58);
        assertInsideBounds(swing);
        double startAngle = headAngle(swing, 0.02);
        double endAngle = headAngle(swing, 0.5);
        assertTrue(Math.toDegrees(Math.abs(endAngle - startAngle)) > 150, "the swing covers a wide arc");
        assertFalse(layer(swing.puffs(0.7, 0), FirePuff.Layer.SMOKE).isEmpty(), "dust where the hammer lands");
    }

    private static double headAngle(SledgeSwingEffect swing, double progress) {
        FirePuff head = layer(swing.puffs(progress, 0), FirePuff.Layer.DEBRIS).stream()
                .max(java.util.Comparator.comparingDouble(FirePuff::radius)).orElseThrow();
        return Math.atan2(head.y() - 100, head.x() - 100);
    }

    @Test
    @DisplayName("a shot spurts blood along the beam; a blow is a big white impact with stars and stagger")
    void hitReactionsDiffer() {
        HitReactionEffect shot = new HitReactionEffect(HitReactionEffect.Kind.SHOT, 100, 100, 1, 0, 0.5, 4L);
        HitReactionEffect blow = new HitReactionEffect(HitReactionEffect.Kind.BLOW, 100, 100, 1, 0, 0.5, 4L);
        assertInsideBounds(shot);
        assertInsideBounds(blow);

        List<FirePuff> blood = layer(shot.puffs(0.4, 0), FirePuff.Layer.DEBRIS);
        assertFalse(blood.isEmpty());
        assertTrue(blood.stream().allMatch(p -> p.red() > p.green() * 2), "blood is red");
        assertTrue(blood.stream().mapToDouble(FirePuff::x).average().orElse(0) > 100, "flung along the shot");

        assertFalse(layer(blow.puffs(0.05, 0), FirePuff.Layer.CORE).isEmpty(), "white impact flash");
        assertFalse(layer(blow.puffs(0.3, 0), FirePuff.Layer.EMBER).isEmpty(), "dizzy stars");
        assertTrue(layer(shot.puffs(0.05, 0), FirePuff.Layer.SHOCKWAVE).isEmpty(), "shots have no shock ring");
        assertFalse(layer(blow.puffs(0.05, 0), FirePuff.Layer.SHOCKWAVE).isEmpty(), "blows do");

        assertTrue(maxOffset(blow) > maxOffset(shot) * 2, "a blow staggers the enemy much more than a shot");
    }

    @Test
    @DisplayName("reactions scale with the damage taken")
    void reactionsScaleWithDamage() {
        HitReactionEffect light = new HitReactionEffect(HitReactionEffect.Kind.SHOT, 0, 0, 1, 0, 0.1, 9L);
        HitReactionEffect heavy = new HitReactionEffect(HitReactionEffect.Kind.SHOT, 0, 0, 1, 0, 1.0, 9L);
        assertTrue(layer(heavy.puffs(0.3, 0), FirePuff.Layer.DEBRIS).size()
                > layer(light.puffs(0.3, 0), FirePuff.Layer.DEBRIS).size());
        assertTrue(maxOffset(heavy) > maxOffset(light));
        assertEquals(0.0, HitReactionEffect.severity(0, 100));
        assertEquals(1.0, HitReactionEffect.severity(500, 100));
    }

    private static double maxOffset(HitReactionEffect effect) {
        double max = 0;
        for (double p = 0; p < 1; p += 0.01) {
            double[] o = effect.spriteOffset(p);
            max = Math.max(max, Math.hypot(o[0], o[1]));
        }
        return max;
    }

    @Test
    @DisplayName("pickup sparkle and shield bubble")
    void pickupAndShield() {
        PickupSparkleEffect sparkle = new PickupSparkleEffect(50, 50, 0.9f, 0.2f, 0.2f);
        assertInsideBounds(sparkle);
        List<FirePuff> bubble = ShieldBubble.puffs(0, 0, 30, 3.0, 0.5);
        assertFalse(bubble.isEmpty());
        assertTrue(bubble.stream().allMatch(p -> Math.hypot(p.x(), p.y()) + p.radius() <= 30 * ShieldBubble.EXTENT_FACTOR));
        assertTrue(ShieldBubble.puffs(0, 0, 30, 0.0, 0.5).isEmpty(), "no bubble when the shield is off");
    }
}
