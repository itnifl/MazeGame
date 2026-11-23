package main.game.maze.game.strike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.game.strike.StrikeTestTargets.Dummy;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.flame.FlameTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Infantry strike: ten soldiers arrive and hunt everything that is not the player")
class InfantrySquadTest {

    private static final InfantrySquad.Orders ORDERS = new InfantrySquad.Orders(10, 30, 40, 300);

    /** An open field: no walls, routes go straight to the goal. */
    private static final InfantrySquad.Terrain OPEN = new InfantrySquad.Terrain() {
        @Override public boolean wallBetween(double fromX, double fromY, double toX, double toY) { return false; }
        @Override public List<Point2D> route(Point2D from, Point2D to) { return List.of(to); }
    };

    /** A wall everywhere: nothing can be seen. */
    private static final InfantrySquad.Terrain BLIND = new InfantrySquad.Terrain() {
        @Override public boolean wallBetween(double fromX, double fromY, double toX, double toY) { return true; }
        @Override public List<Point2D> route(Point2D from, Point2D to) { return List.of(to); }
    };

    private static List<Point2D> grid() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                points.add(new Point2D(x * 30, y * 30));
            }
        }
        return points;
    }

    @Test
    @DisplayName("they arrive together in one random area")
    void arriveTogether() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        assertTrue(squad.deploy(grid(), ORDERS));
        List<InfantrySquad.SoldierView> soldiers = squad.soldiers();
        assertEquals(10, soldiers.size());
        for (InfantrySquad.SoldierView a : soldiers) {
            for (InfantrySquad.SoldierView b : soldiers) {
                assertTrue(Math.hypot(a.x() - b.x(), a.y() - b.y()) <= 4 * 30, "close together");
            }
        }
        assertFalse(new InfantrySquad(new Random(1)).deploy(List.of(), ORDERS), "nowhere to land");
    }

    @Test
    @DisplayName("an enemy in sight is shot until it dies; the player is never shot")
    void shootEnemiesInSight() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 30, 40, 300));
        Dummy enemy = new Dummy(100, 0, 100);
        FlameTarget player = new FlameTarget() {
            @Override public double centerX() { return 10; }
            @Override public double centerY() { return 0; }
            @Override public int hitPoints() { return 100; }
            @Override public void applyDamage(int amount) { throw new AssertionError("the player was shot"); }
            @Override public boolean isPassThrough() { return true; }
        };
        List<InfantrySquad.Round> rounds = new ArrayList<>();
        for (int i = 0; i < 100 && enemy.hp > 0; i++) {
            rounds.addAll(squad.tick(0.1, List.of(enemy, player), OPEN).rounds());
        }
        assertTrue(enemy.hp <= 0, "killed");
        assertEquals(3, rounds.size(), "three rounds of 40 kill 100 hit points");
        assertTrue(rounds.get(rounds.size() - 1).killed());
        assertEquals(100, rounds.get(0).toX(), 1e-9);
        assertEquals(0, squad.soldiers().get(0).x(), 1e-9, "a soldier stands still while it shoots");
    }

    @Test
    @DisplayName("no shooting through walls: the soldiers walk towards the enemy instead")
    void walkWhenBlocked() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 30, 40, 300));
        Dummy enemy = new Dummy(200, 0, 100);
        for (int i = 0; i < 10; i++) {
            assertTrue(squad.tick(0.1, List.of(enemy), BLIND).rounds().isEmpty());
        }
        assertEquals(100, enemy.hp);
        InfantrySquad.SoldierView soldier = squad.soldiers().get(0);
        assertEquals(InfantrySquad.SPEED * 1.0, soldier.x(), 1e-6, "walked one second towards the enemy");
        assertEquals(1.0, soldier.facingX(), 1e-9);
    }

    @Test
    @DisplayName("enemies out of sight range are not shot")
    void sightRange() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 30, 40, 50));
        Dummy far = new Dummy(400, 0, 100);
        assertTrue(squad.tick(0.1, List.of(far), OPEN).rounds().isEmpty());
        assertEquals(100, far.hp);
    }

    @Test
    @DisplayName("they leave when their time is up")
    void leaveWhenTimeIsUp() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(grid(), new InfantrySquad.Orders(10, 1.0, 40, 300));
        assertTrue(squad.active());
        squad.tick(0.6, List.of(), OPEN);
        assertTrue(squad.active());
        assertEquals(0.4, squad.timeLeft(), 1e-9);
        squad.tick(0.6, List.of(), OPEN);
        assertFalse(squad.active());
        assertTrue(squad.soldiers().isEmpty());
        assertEquals(0, squad.timeLeft(), 1e-9);
    }

    @Test
    @DisplayName("an enemy touching a soldier hurts it; the soldier falls, lies a while and is gone")
    void soldiersCanFall() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 60, 0, 0, 50));
        assertEquals(1, squad.livingPositions().size());
        Dummy zombie = new Dummy(10, 0, 100);   // touching
        InfantrySquad.Report fell = InfantrySquad.Report.NOTHING;
        for (int i = 0; i < 40 && fell.fallen().isEmpty(); i++) {
            fell = squad.tick(0.1, List.of(zombie), BLIND);
        }
        assertEquals(1, fell.fallen().size(), "50 health at 25 a second: down in about two seconds");
        assertTrue(squad.soldiers().get(0).fallen());
        assertTrue(squad.livingPositions().isEmpty(), "a fallen soldier lures nobody");
        squad.tick(InfantrySquad.FALLEN_SECONDS + 0.1, List.of(), BLIND);
        assertFalse(squad.active(), "the body is gone and nobody is left");
    }

    @Test
    @DisplayName("soldiers that cannot be hurt (no health in the model) never fall")
    void noHealthMeansUnhurt() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 60, 0, 0));
        for (int i = 0; i < 100; i++) {
            assertTrue(squad.tick(0.1, List.of(new Dummy(5, 0, 100)), BLIND).fallen().isEmpty());
        }
    }

    @Test
    @DisplayName("a walking soldier counts its stride for the animation")
    void walkingIsAnimated() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 30, 40, 300));
        squad.tick(0.5, List.of(new Dummy(400, 0, 100)), BLIND);
        InfantrySquad.SoldierView soldier = squad.soldiers().get(0);
        assertTrue(soldier.walking());
        assertEquals(InfantrySquad.SPEED * 0.5, soldier.walked(), 1e-6);
        assertTrue(StrikeVisuals.soldierSprite(soldier).contains("soldier-side-walk"));
        assertFalse(StrikeVisuals.soldierFlipX(soldier), "walking right");
    }

    @Test
    @DisplayName("a ranged shot hurts the soldier it hits, who falls at the next frame when its health is gone")
    void shotSoldiersFall() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 60, 0, 0, 50));
        assertFalse(squad.hit(100, 0, 60), "nobody stands there");
        assertTrue(squad.hit(5, 0, 30));
        assertTrue(squad.tick(0.1, List.of(), BLIND).fallen().isEmpty(), "20 health left");
        assertTrue(squad.hit(0, 0, 30));
        assertEquals(1, squad.tick(0.1, List.of(), BLIND).fallen().size());
        assertFalse(squad.hit(0, 0, 30), "a fallen soldier is not hit again");
        assertTrue(squad.livingPositions().isEmpty());
    }

    @Test
    @DisplayName("soldiers that cannot be hurt are not hit by shots either")
    void unhurtByShots() {
        InfantrySquad squad = new InfantrySquad(new Random(3));
        squad.deploy(List.of(new Point2D(0, 0)), new InfantrySquad.Orders(1, 60, 0, 0));
        assertFalse(squad.hit(0, 0, 1000));
        assertTrue(squad.tick(0.1, List.of(), BLIND).fallen().isEmpty());
    }
}
