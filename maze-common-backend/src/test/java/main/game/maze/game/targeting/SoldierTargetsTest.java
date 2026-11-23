package main.game.maze.game.targeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Soldiers as targets: where they stand and how they are hit")
class SoldierTargetsTest {

    private final List<String> hits = new ArrayList<>();
    private final SoldierTargets soldiers = SoldierTargets.of(
            () -> List.of(new Point2D(100, 100), new Point2D(300, 100)),
            (soldier, damage) -> hits.add(soldier.getX() + "," + soldier.getY() + ":" + damage));

    @Test
    @DisplayName("a shot touches the nearest soldier whose body it reaches")
    void touching() {
        assertEquals(100, soldiers.touching(110, 100, 0).getX(), 1e-9);
        assertNull(soldiers.touching(200, 100, 5), "between the two");
        assertEquals(300, soldiers.touching(270, 100, 20).getX(), 1e-9, "reach plus the body radius");
        assertNull(SoldierTargets.NONE.touching(100, 100, 50));
    }

    @Test
    @DisplayName("a splash hits every soldier within its radius")
    void splash() {
        assertEquals(1, soldiers.splash(90, 100, 30, 7));
        assertEquals(List.of("100.0,100.0:7"), hits);
        assertEquals(2, soldiers.splash(200, 100, 100, 3));
    }

    @Test
    @DisplayName("a mapped view shows the soldiers in its own space and hits them in the original one")
    void mapped() {
        SoldierTargets corners = soldiers.shifted(-16, -16);
        assertEquals(84, corners.positions().get(0).getX(), 1e-9);
        corners.hit(corners.positions().get(1), 5);
        assertEquals(List.of("300.0,100.0:5"), hits);
        SoldierTargets yUp = soldiers.mapped(p -> new Point2D(p.getX(), 500 - p.getY()),
                p -> new Point2D(p.getX(), 500 - p.getY()));
        assertEquals(400, yUp.positions().get(0).getY(), 1e-9);
        assertTrue(SoldierTargets.NONE.positions().isEmpty());
    }
}
