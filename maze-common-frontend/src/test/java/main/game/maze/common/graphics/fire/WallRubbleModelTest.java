package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("WallRubbleModel")
class WallRubbleModelTest {

    private static final WallRubble WALL = new WallRubble(100, 50, 220, 50, 12, RubbleMaterial.STONE, 9L);

    private static List<FirePuff> layer(List<FirePuff> puffs, FirePuff.Layer layer) {
        return puffs.stream().filter(p -> p.layer() == layer).toList();
    }

    /** Mean distance of chunks from the wall line (y = 50). */
    private static double meanSpread(List<FirePuff> chunks) {
        return chunks.stream().mapToDouble(p -> Math.abs(p.y() - 50)).average().orElse(0);
    }

    @Test
    @DisplayName("chunks start on the wall line and tumble out to both sides")
    void chunksFallOutward() {
        List<FirePuff> start = layer(WallRubbleModel.puffs(WALL, 0.0, 0), FirePuff.Layer.DEBRIS);
        List<FirePuff> settled = layer(WallRubbleModel.puffs(WALL, 0.5, 0), FirePuff.Layer.DEBRIS);
        assertFalse(start.isEmpty());
        // only the small highlight offset (a fraction of a chunk) lies off the line at the start
        assertTrue(meanSpread(start) < WALL.thickness() * 0.1, "chunks begin on the wall");
        assertTrue(meanSpread(settled) > WALL.thickness(), "chunks spread out from the wall");
        assertTrue(settled.stream().anyMatch(p -> p.y() > 50) && settled.stream().anyMatch(p -> p.y() < 50),
                "rubble falls to both sides");
    }

    @Test
    @DisplayName("a dust cloud rises and settles")
    void dustCloud() {
        assertTrue(layer(WallRubbleModel.puffs(WALL, 0.0, 0), FirePuff.Layer.SMOKE).isEmpty());
        assertFalse(layer(WallRubbleModel.puffs(WALL, 0.3, 0), FirePuff.Layer.SMOKE).isEmpty());
        assertTrue(WallRubbleModel.puffs(WALL, 1.0, 0).isEmpty());
    }

    @Test
    @DisplayName("every puff stays within the rubble extent around the wall")
    void staysInsideExtent() {
        double ext = WALL.maxExtent();
        for (double p = 0; p < 1; p += 0.01) {
            for (FirePuff puff : WallRubbleModel.puffs(WALL, p, p)) {
                assertTrue(puff.x() - puff.radius() >= 100 - ext && puff.x() + puff.radius() <= 220 + ext
                        && Math.abs(puff.y() - 50) + puff.radius() <= ext, "outside extent at " + p + ": " + puff);
            }
        }
    }

    @Test
    @DisplayName("longer walls make more chunks; ordered by layer; deterministic")
    void scalesAndIsStable() {
        WallRubble shortWall = new WallRubble(0, 0, 30, 0, 12, RubbleMaterial.STONE, 1L);
        WallRubble longWall = new WallRubble(0, 0, 300, 0, 12, RubbleMaterial.STONE, 1L);
        assertTrue(layer(WallRubbleModel.puffs(longWall, 0.2, 0), FirePuff.Layer.DEBRIS).size()
                > layer(WallRubbleModel.puffs(shortWall, 0.2, 0), FirePuff.Layer.DEBRIS).size());
        List<FirePuff> puffs = WallRubbleModel.puffs(WALL, 0.4, 0.3);
        for (int i = 1; i < puffs.size(); i++) {
            assertTrue(puffs.get(i - 1).layer().ordinal() <= puffs.get(i).layer().ordinal());
        }
        assertEquals(puffs, WallRubbleModel.puffs(WALL, 0.4, 0.3));
    }

    @Test
    @DisplayName("steel throws sparks; other materials do not")
    void steelSparks() {
        WallRubble steel = new WallRubble(100, 50, 220, 50, 12, RubbleMaterial.STEEL, 9L);
        assertFalse(layer(WallRubbleModel.puffs(steel, 0.1, 0), FirePuff.Layer.EMBER).isEmpty());
        assertTrue(layer(WallRubbleModel.puffs(WALL, 0.1, 0), FirePuff.Layer.EMBER).isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"STONE_BASIC, STONE", "glass_basic, GLASS", "WOOD_BASIC, WOOD", "DIRT_BASIC, DIRT",
            "STEEL_SOLID, STEEL", "MARBLE_FANCY, STONE", "'', STONE"})
    void materialFromModelId(String id, RubbleMaterial expected) {
        assertEquals(expected, RubbleMaterial.fromMaterialId(id));
    }

    @Test
    void visualThicknessIsChunkierThanTheWallLine() {
        assertEquals(12.0, WallRubble.visualThickness(5), 1e-9);
        assertEquals(10.0, WallRubble.visualThickness(1), 1e-9);
    }

    @Test
    void validation() {
        assertEquals(RubbleMaterial.STONE, RubbleMaterial.fromMaterialId(null));
        assertThrows(IllegalArgumentException.class, () -> new WallRubble(0, 0, 10, 0, 0, RubbleMaterial.STONE, 1));
        assertThrows(NullPointerException.class, () -> new WallRubble(0, 0, 10, 0, 5, null, 1));
    }
}
