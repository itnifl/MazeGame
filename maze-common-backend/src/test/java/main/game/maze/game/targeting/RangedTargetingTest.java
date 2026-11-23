package main.game.maze.game.targeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ranged enemies shoot the nearest soldier they can hit before the player (SR-400)")
class RangedTargetingTest {

    private static final RangedTargeting.Sight WITHIN_300 = (x, y) -> Math.hypot(x, y) <= 300;

    @Test
    @DisplayName("the nearest soldier in sight is chosen over the player")
    void nearestSoldierFirst() {
        Point2D far = new Point2D(250, 0);
        Point2D near = new Point2D(0, 120);
        RangedTargeting.Target target = RangedTargeting.choose(List.of(far, near), 0, 0, 50, 0, WITHIN_300);
        assertTrue(target.isSoldier());
        assertSame(near, target.soldier());
        assertTrue(target.inSight());
        assertEquals(120, target.y(), 1e-9);
    }

    @Test
    @DisplayName("a soldier out of sight is passed over for the next one")
    void skipsSoldiersOutOfSight() {
        Point2D behindAWall = new Point2D(40, 0);
        Point2D open = new Point2D(200, 0);
        RangedTargeting.Sight wallAt40 = (x, y) -> x != 40;
        assertSame(open, RangedTargeting.choose(List.of(behindAWall, open), 0, 0, 10, 0, wallAt40).soldier());
    }

    @Test
    @DisplayName("with no soldier in sight the player is the target, in sight or not")
    void playerOtherwise() {
        RangedTargeting.Target target = RangedTargeting.choose(List.of(new Point2D(900, 0)), 0, 0, 100, 0, WITHIN_300);
        assertFalse(target.isSoldier());
        assertNull(target.soldier());
        assertTrue(target.inSight());
        assertFalse(RangedTargeting.choose(List.of(), 0, 0, 500, 0, WITHIN_300).inSight());
        assertFalse(RangedTargeting.choose(null, 0, 0, 500, 0, WITHIN_300).isSoldier());
    }
}
