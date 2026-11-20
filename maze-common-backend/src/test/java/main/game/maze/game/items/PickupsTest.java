package main.game.maze.game.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pickups (shared backend)")
class PickupsTest {

    private static final PickupCatalog CATALOG = PickupCatalogLoader.loadDefault();

    private static List<Point2D> grid() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 20; x < 800; x += 40) {
            for (int y = 20; y < 600; y += 40) {
                points.add(new Point2D(x, y));
            }
        }
        return points;
    }

    @Test
    @DisplayName("the catalogue comes from the shipped items model")
    void catalogueFromModel() {
        assertEquals(3.0, CATALOG.spawnIntervalSeconds(), "a new item every 3 seconds until the cap");
        assertEquals(StageConstants.PlayerCharacterXYSize, CATALOG.itemSize(), 1e-9, "items are drawn as big as the player");
        assertEquals(CATALOG.itemSize() / 2, CATALOG.pickupRadius(), 1e-9);
        assertEquals(EnumSet.allOf(PickupKind.class), CATALOG.specs().keySet());
        PickupSpec laser = CATALOG.spec(PickupKind.LASER_RIFLE);
        assertEquals(5, laser.amount(), "a laser rifle gives five shots");
        assertTrue(laser.useSound().endsWith("laser-fire.wav"));
        assertEquals(5, CATALOG.spec(PickupKind.SLEDGEHAMMER).amount(), "five swings");
        assertEquals(5.0, CATALOG.spec(PickupKind.SHIELD).durationSeconds(), "shield lasts five seconds");
        assertEquals(1, CATALOG.spec(PickupKind.BOMB).amount());
        for (PickupSpec spec : CATALOG.specs().values()) {
            assertNotNull(PickupsTest.class.getResource(spec.sprite()), "sprite missing: " + spec.sprite());
            assertNotNull(PickupsTest.class.getResource(spec.pickupSound()), "sound missing: " + spec.pickupSound());
            if (spec.useSound() != null) {
                assertNotNull(PickupsTest.class.getResource(spec.useSound()), "sound missing: " + spec.useSound());
            }
        }
        Set<String> sounds = new java.util.HashSet<>();
        CATALOG.specs().values().forEach(spec -> sounds.add(spec.pickupSound()));
        assertEquals(PickupKind.values().length, sounds.size(), "every item has its own pickup sound");
    }

    @Test
    @DisplayName("caps: easy (small map) 5, normal (medium) 10, hard and large maps 15")
    void capsByBoardSize() {
        assertEquals(5, CATALOG.maxItemsForBoardWidth(StageConstants.BoardMaxX));
        assertEquals(10, CATALOG.maxItemsForBoardWidth(StageConstants.BoardMaxXMedium));
        assertEquals(15, CATALOG.maxItemsForBoardWidth(StageConstants.BoardMaxXLarge));
        assertEquals(15, CATALOG.maxItemsForBoardWidth(3_000));
    }

    @Test
    @DisplayName("one item every 3 s up to the cap; picking items up lets spawning continue")
    void spawnsEveryIntervalUpToCap() {
        PickupField field = new PickupField(CATALOG, new Random(1));
        for (int i = 0; i < 4; i++) {
            field.tick(2.99, StageConstants.BoardMaxX, grid(), -1_000, -1_000);
            assertEquals(i, field.items().size(), "nothing before the interval is up");
            field.tick(0.02, StageConstants.BoardMaxX, grid(), -1_000, -1_000);
            assertEquals(i + 1, field.items().size());
        }
        for (int i = 0; i < 10; i++) {
            field.tick(3.0, StageConstants.BoardMaxX, grid(), -1_000, -1_000);
        }
        assertEquals(5, field.items().size(), "easy map holds at most 5");

        Pickup first = field.items().get(0);
        List<Pickup> taken = field.collect(first.x(), first.y(), 12);
        assertEquals(List.of(first), taken);
        assertEquals(4, field.items().size());
        field.tick(3.0, StageConstants.BoardMaxX, grid(), -1_000, -1_000);
        assertEquals(5, field.items().size(), "spawning continues after a pickup");
    }

    @Test
    @DisplayName("items spawn away from the player and from each other")
    void spawnPlacement() {
        PickupField field = new PickupField(CATALOG, new Random(7));
        for (int i = 0; i < 15; i++) {
            field.tick(3.0, StageConstants.BoardMaxXLarge, grid(), 300, 300);
        }
        List<Pickup> items = field.items();
        assertEquals(15, items.size());
        for (Pickup a : items) {
            assertTrue(Math.hypot(a.x() - 300, a.y() - 300) >= CATALOG.minDistanceFromPlayer());
            for (Pickup b : items) {
                if (a != b) {
                    assertTrue(Math.hypot(a.x() - b.x(), a.y() - b.y()) >= CATALOG.itemSize());
                }
            }
        }
        field.clear();
        assertTrue(field.items().isEmpty());
    }

    @Test
    @DisplayName("spawn points are corridor points inside the maze, clear of every wall by half an item and half a wall")
    void spawnPointsOfLevel() {
        GameMazeWorld world = new GameMazeWorld();
        PickupField field = new PickupField(CATALOG, new Random(3));
        List<Point2D> points = field.spawnPointsOf(world);
        assertFalse(points.isEmpty());
        int nodes = 0;
        for (var column : world.getNavigationGraph().getGrid()) {
            for (var node : column) {
                if (node != null) {
                    nodes++;
                }
            }
        }
        assertTrue(points.size() < nodes, "grid nodes on or near walls and outside the maze are left out");
        double[] box = wallBounds(world.getMazeVectors());
        for (Point2D point : points) {
            assertTrue(point.getX() > box[0] && point.getX() < box[2] && point.getY() > box[1] && point.getY() < box[3],
                    "inside the maze: " + point);
            for (Vector2D wall : world.getMazeVectors()) {
                assertTrue(PickupField.distanceToSegment(point.getX(), point.getY(), wall) >= CATALOG.spawnClearance(),
                        point + " too close to " + wall);
            }
        }
        assertTrue(CATALOG.spawnClearance() >= CATALOG.itemSize() / 2 + StageConstants.WallThicknessPx / 2.0);
        assertTrue(field.spawnPointsOf(null).isEmpty());
    }

    @Test
    @DisplayName("no spawned item ever overlaps a wall on the shipped maze")
    void itemsNeverSpawnInsideWalls() {
        GameMazeWorld world = new GameMazeWorld();
        PickupField field = new PickupField(CATALOG, new Random(11));
        List<Point2D> spots = field.spawnPointsOf(world);
        for (int i = 0; i < 60; i++) {
            field.tick(3.0, 5_000, spots, -10_000, -10_000);
            for (Pickup item : field.items()) {
                for (Vector2D wall : world.getMazeVectors()) {
                    assertTrue(PickupField.distanceToSegment(item.x(), item.y(), wall)
                            >= CATALOG.itemSize() / 2 + StageConstants.WallThicknessPx / 2.0, item + " in " + wall);
                }
            }
            if (field.items().size() >= 15) {
                field.collect(field.items().get(0).x(), field.items().get(0).y(), 1);
            }
        }
    }

    @Test
    @DisplayName("distance to a wall segment: perpendicular inside the span, to the nearest end outside it")
    void distanceToSegment() {
        Vector2D wall = new Vector2D(0, 0, 100, 0);
        assertEquals(10, PickupField.distanceToSegment(50, 10, wall), 1e-9);
        assertEquals(5, PickupField.distanceToSegment(-3, 4, wall), 1e-9);
        assertEquals(0, PickupField.distanceToSegment(100, 0, wall), 1e-9);
        assertEquals(7, PickupField.distanceToSegment(3, 7, new Vector2D(3, 0, 3, 0)), 1e-9, "zero-length wall");
    }

    private static double[] wallBounds(List<Vector2D> walls) {
        double[] box = {Double.MAX_VALUE, Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
        for (Vector2D w : walls) {
            for (Point2D p : List.of(w.getStart(), w.getEnd())) {
                box[0] = Math.min(box[0], p.getX());
                box[1] = Math.min(box[1], p.getY());
                box[2] = Math.max(box[2], p.getX());
                box[3] = Math.max(box[3], p.getY());
            }
        }
        return box;
    }

    @Test
    @DisplayName("arsenal: Tab cycles owned weapons, using spends ammo, empty weapons fall back to bombs")
    void arsenal() {
        PlayerArsenal arsenal = new PlayerArsenal(2);
        assertEquals(Weapon.BOMB, arsenal.selected());
        assertEquals(Weapon.BOMB, arsenal.cycle(), "nothing else owned yet");

        arsenal.add(Weapon.LASER_RIFLE, 5);
        arsenal.add(Weapon.SLEDGEHAMMER, 1);
        assertEquals(Weapon.LASER_RIFLE, arsenal.cycle());
        assertEquals(Weapon.SLEDGEHAMMER, arsenal.cycle());
        assertEquals(Weapon.BOMB, arsenal.cycle());
        assertEquals(Weapon.LASER_RIFLE, arsenal.cycle());

        for (int i = 0; i < 5; i++) {
            assertEquals(Weapon.LASER_RIFLE, arsenal.use());
        }
        assertEquals(0, arsenal.count(Weapon.LASER_RIFLE));
        assertEquals(Weapon.SLEDGEHAMMER, arsenal.selected(), "empty laser switches to the next owned weapon");
        assertEquals(Weapon.SLEDGEHAMMER, arsenal.use());
        assertEquals(Weapon.BOMB, arsenal.selected());
        arsenal.use();
        arsenal.use();
        assertNull(arsenal.use(), "nothing left to use");
        arsenal.add(Weapon.LASER_RIFLE, 5);
        assertEquals(5, arsenal.count(Weapon.LASER_RIFLE), "a new rifle gives five new shots");
        assertEquals(Weapon.LASER_RIFLE, arsenal.selected(), "out of everything: a picked-up weapon goes into the hands");
        assertEquals(Weapon.LASER_RIFLE, arsenal.use());
    }

    @Test
    @DisplayName("a pickup does not change the weapon in hand while it still has ammo")
    void pickupKeepsLoadedWeaponInHand() {
        PlayerArsenal arsenal = new PlayerArsenal(1);
        arsenal.add(Weapon.SLEDGEHAMMER, 5);
        assertEquals(Weapon.BOMB, arsenal.selected());
        PlayerArsenal empty = new PlayerArsenal(0);
        empty.add(Weapon.BOMB, 1);
        assertEquals(Weapon.BOMB, empty.selected());
        assertEquals(Weapon.BOMB, empty.use());
    }

    @Test
    @DisplayName("power shield blocks for its duration; a new cube restarts it")
    void shield() {
        AtomicLong now = new AtomicLong();
        PowerShield shield = new PowerShield(now::get);
        assertFalse(shield.isActive());
        shield.activate(5.0);
        now.addAndGet(4_900_000_000L);
        assertTrue(shield.isActive());
        assertEquals(0.1, shield.remainingSeconds(), 1e-6);
        shield.activate(5.0);
        now.addAndGet(4_900_000_000L);
        assertTrue(shield.isActive());
        now.addAndGet(200_000_000L);
        assertFalse(shield.isActive());
        assertEquals(0.0, shield.remainingSeconds());
    }

    @Test
    @DisplayName("picking up applies the item: bombs, shots, swings, shield, health")
    void effects() {
        PlayerArsenal arsenal = new PlayerArsenal(0);
        PowerShield shield = new PowerShield(() -> 0L);
        List<Integer> healed = new ArrayList<>();

        for (PickupKind kind : PickupKind.values()) {
            PickupEffects.apply(CATALOG.spec(kind), arsenal, shield, healed::add, 150);
        }

        assertEquals(1, arsenal.count(Weapon.BOMB));
        assertEquals(5, arsenal.count(Weapon.LASER_RIFLE));
        assertEquals(5, arsenal.count(Weapon.SLEDGEHAMMER));
        assertTrue(shield.isActive());
        assertEquals(List.of(30), healed, "a small heart restores 20 % of max health");
    }

    @Test
    @DisplayName("weighted random kind covers every kind")
    void randomKinds() {
        Random random = new Random(3);
        Set<PickupKind> seen = EnumSet.noneOf(PickupKind.class);
        for (int i = 0; i < 200; i++) {
            seen.add(CATALOG.randomKind(random));
        }
        assertEquals(EnumSet.allOf(PickupKind.class), seen);
    }

    @Test
    @DisplayName("HUD text lists every weapon with its stock, marks the one in hand and shows the shield")
    void hudText() {
        AtomicLong now = new AtomicLong();
        PowerShield shield = new PowerShield(now::get);
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.LASER_RIFLE, 5);
        arsenal.cycle();
        assertEquals("Bombs 3  [Laser 5]  Sledge 0", ArsenalHud.text(arsenal, shield));
        shield.activate(5.0);
        now.addAndGet(1_500_000_000L);
        assertEquals("Bombs 3  [Laser 5]  Sledge 0  Shield 4s", ArsenalHud.text(arsenal, shield));
        for (PickupKind kind : PickupKind.values()) {
            assertEquals(3, ArsenalHud.sparkleColour(kind).length);
        }
    }
}
