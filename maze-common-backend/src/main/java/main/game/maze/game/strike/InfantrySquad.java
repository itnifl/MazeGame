package main.game.maze.game.strike;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.flame.FlameTarget;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;

/**
 * The infantry strike, shared by both frontends: a squad of soldiers arrives in a random area of the maze and, for as long
 * as it stays, hunts everything that is not the player who called it in. A soldier who sees an enemy within its sight
 * (no wall between) stops and fires at it; otherwise it walks the maze's navigation graph towards the nearest enemy, or
 * wanders when there are none. Enemies go for the soldiers first ({@link DecoyTargeting}); an enemy touching a soldier
 * hurts it ({@link #ENEMY_TOUCH_DPS}), and a soldier whose health is gone falls and lies a while. The squad leaves when its
 * time is up.
 *
 * <p>World space (y down). The frontend passes the living enemies each frame as {@link FlameTarget}s (whose damage it
 * credits to the player), draws the soldiers ({@link #soldiers()}) and the rounds {@link #tick} returns. Not thread-safe.
 */
public final class InfantrySquad {

    /** Walking speed of a soldier, pixels per second. */
    public static final double SPEED = 95.0;
    /** Seconds between two rounds of one soldier. */
    public static final double FIRE_INTERVAL = 0.55;
    /** Seconds between two route plans of one soldier. */
    public static final double REPATH_SECONDS = 0.9;
    /** How long the muzzle flash of a round is shown on the soldier. */
    public static final double FLASH_SECONDS = 0.12;

    /** What a soldier needs to know about the maze. */
    public interface Terrain {
        /** Whether a wall stands between the two points (no line of fire). */
        boolean wallBetween(double fromX, double fromY, double toX, double toY);

        /** A walkable route from one point to the other (empty when there is none). */
        List<Point2D> route(Point2D from, Point2D to);

        /** The terrain of a maze world: its walls and its navigation graph. */
        static Terrain of(GameMazeWorld world) {
            return new Terrain() {
                @Override
                public boolean wallBetween(double fromX, double fromY, double toX, double toY) {
                    return WallCollisionUtil.wallBetweenVectors(fromX, fromY, toX, toY, world.getMazeVectors());
                }

                @Override
                public List<Point2D> route(Point2D from, Point2D to) {
                    return MazeNavigationGraphService.findPath(world.getNavigationGraph(), from, to);
                }
            };
        }
    }

    /** One round fired: from the soldier to the enemy, and whether it killed it. */
    public record Round(double fromX, double fromY, double toX, double toY, boolean killed) {
    }

    /**
     * A soldier as it is drawn: where, which way it faces (-1, 0, 1 on each axis), whether its gun flashes, whether it is
     * walking and how far it has walked (for the stride), and whether it has fallen.
     */
    public record SoldierView(double x, double y, double facingX, double facingY, boolean firing, boolean walking,
                              double walked, boolean fallen) {
    }

    /** How the squad fights: from the items model's infantry strike. {@code health} 0 = the soldiers cannot be hurt. */
    public record Orders(int soldiers, double seconds, int damage, double sight, int health) {

        public Orders(int soldiers, double seconds, int damage, double sight) {
            this(soldiers, seconds, damage, sight, 0);
        }
    }

    /** One frame of the squad: the rounds fired and the soldiers who fell. */
    public record Report(List<Round> rounds, List<SoldierView> fallen) {
        public Report {
            rounds = List.copyOf(rounds);
            fallen = List.copyOf(fallen);
        }

        static final Report NOTHING = new Report(List.of(), List.of());
    }

    /** Damage per second an enemy does to a soldier it touches. */
    public static final double ENEMY_TOUCH_DPS = 25.0;
    /** An enemy whose centre is this close to a soldier's touches it. */
    public static final double TOUCH_DISTANCE = 26.0;
    /** How long a fallen soldier lies before it is gone. */
    public static final double FALLEN_SECONDS = 4.0;

    private static final class Soldier {
        double x;
        double y;
        double facingX = 1;
        double facingY;
        List<Point2D> route = List.of();
        int next;
        double repathIn;
        double reloadIn;
        double flash;
        double health;
        double walked;
        boolean walking;
        boolean fallen;
        /** Seconds the body of a fallen soldier still lies. */
        double bodyLeft;

        boolean alive() {
            return !fallen;
        }

        Soldier(double x, double y, double repathIn, double reloadIn) {
            this.x = x;
            this.y = y;
            this.repathIn = repathIn;
            this.reloadIn = reloadIn;
        }
    }

    private final Random random;
    private final List<Soldier> squad = new ArrayList<>();
    private List<Point2D> openGround = List.of();
    private Orders orders = new Orders(0, 0, 0, 0);
    private double timeLeft;
    /** Where the living soldiers are, published for the enemy AI (which may run on another thread). */
    private volatile List<Point2D> livingPositions = List.of();

    public InfantrySquad(Random random) {
        this.random = random;
    }

    /**
     * The squad arrives: {@code orders.soldiers()} soldiers on the open points nearest to a random one of them (a random
     * area of the maze). A squad that is already there is replaced by the new one.
     *
     * @param openPoints walkable points of the maze, away from walls and lava (the pickup spawn points)
     * @return false when there is nowhere to put them
     */
    public boolean deploy(List<Point2D> openPoints, Orders orders) {
        dismiss();
        if (openPoints == null || openPoints.isEmpty() || orders.soldiers() <= 0) {
            return false;
        }
        this.orders = orders;
        this.openGround = List.copyOf(openPoints);
        Point2D anchor = openGround.get(random.nextInt(openGround.size()));
        List<Point2D> nearest = new ArrayList<>(openGround);
        nearest.sort(Comparator.comparingDouble(anchor::distance));
        for (int i = 0; i < orders.soldiers(); i++) {
            Point2D at = nearest.get(i % nearest.size());
            // staggered timers, so the squad does not plan and fire all in the same frame
            Soldier soldier = new Soldier(at.getX(), at.getY(), random.nextDouble() * REPATH_SECONDS,
                    random.nextDouble() * FIRE_INTERVAL);
            soldier.health = orders.health();
            squad.add(soldier);
        }
        timeLeft = Math.max(0.0, orders.seconds());
        publishPositions();
        return true;
    }

    /**
     * Where the living soldiers are now (world space), safe to read from any thread: enemies near them go for them
     * ({@link DecoyTargeting}).
     */
    public List<Point2D> livingPositions() {
        return livingPositions;
    }

    private void publishPositions() {
        List<Point2D> positions = new ArrayList<>();
        for (Soldier soldier : squad) {
            if (soldier.alive()) {
                positions.add(new Point2D(soldier.x, soldier.y));
            }
        }
        livingPositions = List.copyOf(positions);
    }

    /** Whether the squad is in the maze now. */
    public boolean active() {
        return !squad.isEmpty();
    }

    /** Seconds until the squad leaves. */
    public double timeLeft() {
        return active() ? timeLeft : 0.0;
    }

    /** The squad leaves (time up, new level, teardown). */
    public void dismiss() {
        squad.clear();
        timeLeft = 0;
        livingPositions = List.of();
    }

    public List<SoldierView> soldiers() {
        List<SoldierView> views = new ArrayList<>(squad.size());
        for (Soldier soldier : squad) {
            views.add(view(soldier));
        }
        return views;
    }

    private static SoldierView view(Soldier soldier) {
        return new SoldierView(soldier.x, soldier.y, soldier.facingX, soldier.facingY, soldier.alive() && soldier.flash > 0,
                soldier.alive() && soldier.walking, soldier.walked, !soldier.alive());
    }

    /**
     * One frame: enemies touching a soldier hurt it; every living soldier fires at an enemy it sees, or walks towards one;
     * fallen soldiers lie a while and are gone.
     *
     * @param enemies the living enemies (pass-through targets, i.e. the player, are never fired at)
     * @return the rounds fired and the soldiers who fell this frame
     */
    public Report tick(double dt, List<FlameTarget> enemies, Terrain terrain) {
        if (!active() || dt <= 0) {
            return Report.NOTHING;
        }
        timeLeft -= dt;
        if (timeLeft <= 0) {
            dismiss();
            return Report.NOTHING;
        }
        List<FlameTarget> living = new ArrayList<>();
        for (FlameTarget enemy : enemies) {
            if (!enemy.isPassThrough() && enemy.hitPoints() > 0) {
                living.add(enemy);
            }
        }
        List<Round> rounds = new ArrayList<>();
        List<SoldierView> fallen = new ArrayList<>();
        for (Soldier soldier : squad) {
            if (!soldier.alive()) {
                soldier.bodyLeft -= dt;
                continue;
            }
            if (takeTouchDamage(soldier, living, dt)) {
                fallen.add(view(soldier));
                continue;
            }
            soldier.reloadIn -= dt;
            soldier.repathIn -= dt;
            soldier.flash -= dt;
            soldier.walking = false;
            FlameTarget inSight = targetInSight(soldier, living, terrain);
            if (inSight != null) {
                face(soldier, inSight.centerX() - soldier.x, inSight.centerY() - soldier.y);
                if (soldier.reloadIn <= 0) {
                    rounds.add(fire(soldier, inSight));
                }
                continue;   // a soldier stands still while it has an enemy in its sights
            }
            walk(soldier, dt, living, terrain);
        }
        // a fallen soldier's body is gone after a while; the squad is gone when nobody is left
        squad.removeIf(soldier -> !soldier.alive() && soldier.bodyLeft <= 0);
        if (squad.isEmpty()) {
            dismiss();
        }
        publishPositions();
        return new Report(rounds, fallen);
    }

    /**
     * A shot of a ranged enemy hits the living soldier nearest to (x, y), when one stands within
     * {@link SoldierTargets#BODY_RADIUS} of it; it falls at the next {@link #tick} when its health is gone.
     *
     * @return whether a soldier was hit
     */
    public boolean hit(double x, double y, int damage) {
        if (orders.health() <= 0 || damage <= 0) {
            return false;
        }
        Soldier nearest = null;
        double best = SoldierTargets.BODY_RADIUS;
        for (Soldier soldier : squad) {
            double distance = Math.hypot(soldier.x - x, soldier.y - y);
            if (soldier.alive() && distance <= best) {
                best = distance;
                nearest = soldier;
            }
        }
        if (nearest == null) {
            return false;
        }
        nearest.health -= damage;
        return true;
    }

    /**
     * Enemies touching the soldier hurt it (when the soldiers can be hurt at all); a soldier without health left (from
     * touches or from shots since the last frame) falls.
     *
     * @return true when it fell now
     */
    private boolean takeTouchDamage(Soldier soldier, List<FlameTarget> living, double dt) {
        if (orders.health() <= 0) {
            return false;
        }
        for (FlameTarget enemy : living) {
            if (Math.hypot(enemy.centerX() - soldier.x, enemy.centerY() - soldier.y) <= TOUCH_DISTANCE) {
                soldier.health -= ENEMY_TOUCH_DPS * dt;
            }
        }
        if (soldier.health <= 0) {
            soldier.fallen = true;
            soldier.bodyLeft = FALLEN_SECONDS;
            soldier.walking = false;
            return true;
        }
        return false;
    }

    private Round fire(Soldier soldier, FlameTarget enemy) {
        enemy.applyDamage(orders.damage());
        soldier.reloadIn = FIRE_INTERVAL;
        soldier.flash = FLASH_SECONDS;
        return new Round(soldier.x, soldier.y, enemy.centerX(), enemy.centerY(), enemy.hitPoints() <= 0);
    }

    /** The nearest living enemy within sight with no wall between, or null. */
    private FlameTarget targetInSight(Soldier soldier, List<FlameTarget> living, Terrain terrain) {
        double sight = orders.sight();
        List<FlameTarget> near = new ArrayList<>();
        for (FlameTarget enemy : living) {
            if (enemy.hitPoints() > 0 && distance(soldier, enemy) <= sight) {
                near.add(enemy);
            }
        }
        near.sort(Comparator.comparingDouble(enemy -> distance(soldier, enemy)));
        for (FlameTarget enemy : near) {
            if (!terrain.wallBetween(soldier.x, soldier.y, enemy.centerX(), enemy.centerY())) {
                return enemy;
            }
        }
        return null;
    }

    private void walk(Soldier soldier, double dt, List<FlameTarget> living, Terrain terrain) {
        if (soldier.repathIn <= 0 || soldier.next >= soldier.route.size()) {
            plan(soldier, living, terrain);
        }
        double budget = SPEED * dt;
        double start = budget;
        while (budget > 0 && soldier.next < soldier.route.size()) {
            Point2D waypoint = soldier.route.get(soldier.next);
            double dx = waypoint.getX() - soldier.x;
            double dy = waypoint.getY() - soldier.y;
            double length = Math.hypot(dx, dy);
            if (length <= budget) {
                soldier.x = waypoint.getX();
                soldier.y = waypoint.getY();
                soldier.next++;
                budget -= length;
            } else {
                soldier.x += dx / length * budget;
                soldier.y += dy / length * budget;
                budget = 0;
            }
            face(soldier, dx, dy);
        }
        double moved = start - budget;
        soldier.walking = moved > 0;
        soldier.walked += moved;
    }

    /** A new route: to the nearest living enemy, or to a random open point when there are none. */
    private void plan(Soldier soldier, List<FlameTarget> living, Terrain terrain) {
        soldier.repathIn = REPATH_SECONDS;
        Point2D from = new Point2D(soldier.x, soldier.y);
        Point2D goal = null;
        double best = Double.MAX_VALUE;
        for (FlameTarget enemy : living) {
            double distance = distance(soldier, enemy);
            if (distance < best) {
                best = distance;
                goal = new Point2D(enemy.centerX(), enemy.centerY());
            }
        }
        if (goal == null && !openGround.isEmpty()) {
            goal = openGround.get(random.nextInt(openGround.size()));
        }
        soldier.route = goal == null ? List.of() : terrain.route(from, goal);
        soldier.next = 0;
    }

    private static void face(Soldier soldier, double dx, double dy) {
        if (Math.abs(dx) >= Math.abs(dy) && dx != 0) {
            soldier.facingX = Math.signum(dx);
            soldier.facingY = 0;
        } else if (dy != 0) {
            soldier.facingX = 0;
            soldier.facingY = Math.signum(dy);
        }
    }

    private static double distance(Soldier soldier, FlameTarget enemy) {
        return Math.hypot(enemy.centerX() - soldier.x, enemy.centerY() - soldier.y);
    }
}
