package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Lava picture: a flowing river of lava with wooden bridges, drawn under the walls")
class LavaEffectTest {

    private static LavaEffect lava(double flip) {
        List<LavaEffect.Point> path = new ArrayList<>();
        for (int x = 0; x <= 400; x += 12) {
            path.add(new LavaEffect.Point(x, 200 + 40 * Math.sin(x / 60.0)));
        }
        return new LavaEffect(List.of(new LavaEffect.Stream(path, List.of(new LavaEffect.Bridge(200, 200, 0.2, 56)))),
                46, 10, LavaEffect.Look.DEFAULT, 800, 400, flip);
    }

    @Test
    @DisplayName("it lasts as long as the level and draws banks, a river, flowing blobs and the bridge")
    void drawsTheStream() {
        LavaEffect effect = lava(0);
        assertTrue(effect.lifetimeSeconds() > 3600);
        List<FirePuff> puffs = effect.puffs(0, 1.0);
        assertFalse(puffs.isEmpty());
        assertTrue(puffs.stream().anyMatch(p -> p.layer() == FirePuff.Layer.SMOKE), "banks and river");
        assertTrue(puffs.stream().anyMatch(p -> p.layer() == FirePuff.Layer.INNER), "flowing blobs");
        assertTrue(puffs.stream().anyMatch(p -> p.layer() == FirePuff.Layer.DEBRIS), "the bridge, on top of the lava");
        assertTrue(effect.puffs(1.0, 1.0).isEmpty(), "nothing once over");
    }

    @Test
    @DisplayName("the lava flows: the blobs are in other places a moment later")
    void flows() {
        LavaEffect effect = lava(0);
        double a = effect.puffs(0, 1.0).stream().filter(p -> p.layer() == FirePuff.Layer.INNER).mapToDouble(FirePuff::x).sum();
        double b = effect.puffs(0, 2.5).stream().filter(p -> p.layer() == FirePuff.Layer.INNER).mapToDouble(FirePuff::x).sum();
        assertTrue(a != b);
    }

    @Test
    @DisplayName("the bridge is drawn after (over) every lava puff, so it lies on the lava")
    void bridgeOnTop() {
        List<FirePuff> puffs = lava(0).puffs(0, 1.0);
        int firstBridge = -1;
        int lastLava = -1;
        for (int i = 0; i < puffs.size(); i++) {
            if (puffs.get(i).layer() == FirePuff.Layer.DEBRIS && firstBridge < 0) {
                firstBridge = i;
            }
            if (puffs.get(i).layer() != FirePuff.Layer.DEBRIS) {
                lastLava = i;
            }
        }
        assertTrue(firstBridge > lastLava);
    }

    @Test
    @DisplayName("for a y-up screen the picture is turned over the board height")
    void flipped() {
        // the sparks jitter a little in a direction of their own, so they are left out of the comparison
        // (and the order inside a layer may differ, so both are sorted by place)
        java.util.Comparator<FirePuff> byPlace = java.util.Comparator.comparing(FirePuff::layer)
                .thenComparingDouble(FirePuff::x).thenComparingDouble(FirePuff::radius);
        List<FirePuff> down = lava(0).puffs(0, 1.0).stream().filter(p -> p.layer() != FirePuff.Layer.EMBER).sorted(byPlace.thenComparingDouble(FirePuff::y)).toList();
        List<FirePuff> up = lava(400).puffs(0, 1.0).stream().filter(p -> p.layer() != FirePuff.Layer.EMBER)
                .sorted(byPlace.thenComparingDouble(p -> -p.y())).toList();
        assertEquals(down.size(), up.size());
        for (int i = 0; i < down.size(); i++) {
            assertEquals(400 - down.get(i).y(), up.get(i).y(), 1e-6, "layer " + down.get(i).layer() + " index " + i + " x " + down.get(i).x());
            assertEquals(down.get(i).x(), up.get(i).x(), 1e-6);
        }
    }

    @Test
    @DisplayName("without streams there is nothing to draw")
    void empty() {
        assertTrue(new LavaEffect(List.of(), 46, 10, LavaEffect.Look.DEFAULT, 800, 400, 0).puffs(0, 1).isEmpty());
    }

    private static LavaEffect looking(LavaEffect.Look look) {
        List<LavaEffect.Point> path = new ArrayList<>();
        for (int x = 0; x <= 400; x += 12) {
            path.add(new LavaEffect.Point(x, 200 + 40 * Math.sin(x / 60.0)));
        }
        return new LavaEffect(List.of(new LavaEffect.Stream(path, List.of())), 46, 10, look, 800, 400, 0);
    }

    @Test
    @DisplayName("the look comes from the model: more crust means more dark plates, no glow means no light on the floor")
    void lookFollowsTheModel() {
        long dark = looking(new LavaEffect.Look(34, 0.5, 1.0)).puffs(0, 2.0).stream().filter(p -> p.layer() == FirePuff.Layer.SMOKE).count();
        long bare = looking(new LavaEffect.Look(34, 0.5, 0.0)).puffs(0, 2.0).stream().filter(p -> p.layer() == FirePuff.Layer.SMOKE).count();
        assertTrue(dark > bare, "crust adds plates: " + dark + " vs " + bare);
        long lit = looking(new LavaEffect.Look(34, 1.0, 0.5)).puffs(0, 2.0).stream().filter(p -> p.layer() == FirePuff.Layer.BODY).count();
        long unlit = looking(new LavaEffect.Look(34, 0.0, 0.5)).puffs(0, 2.0).stream().filter(p -> p.layer() == FirePuff.Layer.BODY).count();
        assertTrue(lit > unlit, "glow lights the floor");
    }

    @Test
    @DisplayName("the flow speed decides how fast the pattern travels")
    void flowSpeed() {
        double slow = looking(new LavaEffect.Look(5, 0.5, 0.6)).puffs(0, 3.0).stream()
                .filter(p -> p.layer() == FirePuff.Layer.SMOKE).mapToDouble(FirePuff::x).sum();
        double slowLater = looking(new LavaEffect.Look(5, 0.5, 0.6)).puffs(0, 3.1).stream()
                .filter(p -> p.layer() == FirePuff.Layer.SMOKE).mapToDouble(FirePuff::x).sum();
        double fast = looking(new LavaEffect.Look(80, 0.5, 0.6)).puffs(0, 3.0).stream()
                .filter(p -> p.layer() == FirePuff.Layer.SMOKE).mapToDouble(FirePuff::x).sum();
        double fastLater = looking(new LavaEffect.Look(80, 0.5, 0.6)).puffs(0, 3.1).stream()
                .filter(p -> p.layer() == FirePuff.Layer.SMOKE).mapToDouble(FirePuff::x).sum();
        assertTrue(Math.abs(fastLater - fast) > Math.abs(slowLater - slow), "faster lava moves further in the same time");
    }

    @Test
    @DisplayName("the surface lives: bubbles, sparks and smoke come and go with the clock")
    void alive() {
        long a = lava(0).puffs(0, 0.4).stream().filter(p -> p.layer() == FirePuff.Layer.EMBER || p.layer() == FirePuff.Layer.RIM).count();
        assertTrue(a > 0, "sparks and bubbles");
        double b = lava(0).puffs(0, 0.4).stream().filter(p -> p.layer() == FirePuff.Layer.EMBER).mapToDouble(FirePuff::y).sum();
        double c = lava(0).puffs(0, 1.3).stream().filter(p -> p.layer() == FirePuff.Layer.EMBER).mapToDouble(FirePuff::y).sum();
        assertTrue(b != c);
    }

    @Test
    @DisplayName("details placed by distance along the stream land where they belong, also on a path of many short bent segments")
    void detailsSitOnTheStream() {
        List<LavaEffect.Point> path = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            path.add(new LavaEffect.Point(i * 13.0, 100 + (i % 2) * 3));   // 100 segments of about 13 px
        }
        LavaEffect effect = new LavaEffect(List.of(new LavaEffect.Stream(path, List.of())), 46, 10,
                new LavaEffect.Look(34, 0.5, 1.0), 1400, 300, 0);
        for (FirePuff puff : effect.puffs(0, 5.0)) {
            if (puff.layer() == FirePuff.Layer.SMOKE && puff.radius() < 14 && puff.alpha() > 0.9) {   // crust plates
                assertTrue(puff.x() >= -20 && puff.x() <= 1320 && Math.abs(puff.y() - 101.5) <= 40, "a plate off the stream at " + puff.x() + "," + puff.y());
            }
        }
    }
}
