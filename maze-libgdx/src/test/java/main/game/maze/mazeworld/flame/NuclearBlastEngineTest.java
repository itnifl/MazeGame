package main.game.maze.mazeworld.flame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallMaterialSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("NuclearBlastEngine: a round blast through walls, strongest at the centre")
class NuclearBlastEngineTest {

    private static FlameTarget target(double cx, double cy, int hp, AtomicInteger remaining, boolean passThrough) {
        remaining.set(hp);
        return new FlameTarget() {
            @Override public double centerX() { return cx; }
            @Override public double centerY() { return cy; }
            @Override public int hitPoints() { return remaining.get(); }
            @Override public void applyDamage(int amount) { remaining.addAndGet(-amount); }
            @Override public boolean isPassThrough() { return passThrough; }
        };
    }

    @Test
    @DisplayName("full damage in the core, less towards the edge, nothing outside")
    void falloff() {
        assertEquals(1000, NuclearBlastEngine.damageAt(0, 400, 1000));
        assertEquals(1000, NuclearBlastEngine.damageAt(400 * NuclearBlastEngine.CORE_SHARE, 400, 1000));
        assertEquals(250, NuclearBlastEngine.damageAt(400, 400, 1000), "a quarter at the edge");
        int middle = NuclearBlastEngine.damageAt(270, 400, 1000);
        assertTrue(middle > 250 && middle < 1000);
        assertEquals(0, NuclearBlastEngine.damageAt(401, 400, 1000));
    }

    @Test
    @DisplayName("walls do not shield: enemies behind walls, the player too, and the walls in reach are destroyed")
    void throughWalls() {
        GameMazeWorld world = new GameMazeWorld(() -> new ArrayList<>(List.of(new Vector2D(0, 5000, 10, 5000))));
        world.addBuiltWall(new Vector2D(100, -50, 100, 50), new WallMaterialSpec("WOOD_BASIC", "Wood", 50));
        world.addBuiltWall(new Vector2D(900, -50, 900, 50), new WallMaterialSpec("WOOD_BASIC", "Wood", 50));
        AtomicInteger behindWall = new AtomicInteger();
        AtomicInteger player = new AtomicInteger();
        AtomicInteger far = new AtomicInteger();
        int dealt = NuclearBlastEngine.apply(List.of(
                target(200, 0, 3000, behindWall, false),
                target(30, 0, 100, player, true),
                target(2000, 0, 100, far, false)), world, 0, 0, 420, 2400);
        assertTrue(behindWall.get() < 3000, "hurt through the wall");
        assertTrue(player.get() <= 0, "the thrower in the blast is hurt too");
        assertEquals(100, far.get(), "out of reach");
        assertEquals(3000 - behindWall.get(), dealt, "only enemy damage is counted");
        assertEquals(2, world.getMazeVectors().size(), "the near wall is gone, the far one and the far-off one stay");
    }
}
