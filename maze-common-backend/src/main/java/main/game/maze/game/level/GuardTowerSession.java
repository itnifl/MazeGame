package main.game.maze.game.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.game.maze.game.targeting.RangedTargeting;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.mazeworld.Point2D;

/**
 * Everything the guard towers of one level do, decided once for both frontends. The frontend keeps the tower
 * enemies (their health, sprites and the damage they take from bullets and blasts) and calls {@link #tick} every
 * frame with where they stand and how healthy they are; the session answers with what happened: gatling rounds that
 * hit the player, rockets that took off and rockets that landed, and towers that were hit or blown up (for the
 * sounds and the destruction). A tower shoots at the nearest soldier of the infantry strike it sees, else at the player
 * ({@link RangedTargeting}). A rocket flies at {@link GuardTowerSpec#rpgSpeed()} to where its target was when it was
 * fired, and explodes there, hurting the player and the soldiers within its blast that no wall shields. Coordinates are one world space (the frontend converts when it draws in another).
 */
public final class GuardTowerSession {

    /** Counts the walls between a tower and the player (the frontend knows its walls). */
    @FunctionalInterface
    public interface WallCounter {
        int between(double fromX, double fromY, double toX, double toY);
    }

    /** A tower as the frontend sees it. {@code health <= 0} means it is gone. */
    public record Tower(String id, double x, double y, int health) {
    }

    /** A rocket in the air. */
    public record Rocket(long id, String towerId, double fromX, double fromY, double toX, double toY, double x, double y,
                         double angleRadians) {
    }

    /** A rocket that has just landed. */
    public record Impact(double x, double y) {
    }

    public enum Event { HIT, DESTROYED }

    /** What happened to a tower during the tick: it took damage, or it was destroyed. */
    public record TowerEvent(String towerId, double x, double y, Event event) {
    }

    /**
     * A burst round fired at the player, who takes {@link GuardTowerSpec#gatlingDamage()} for each, or at a soldier
     * (then also listed in {@link Result#soldierHits()}).
     */
    public record Round(String towerId, double fromX, double fromY, double toX, double toY) {
    }

    /** A soldier hit by a round or a rocket: the frontend hurts the soldier standing at {@code soldier}. */
    public record SoldierHit(Point2D soldier, int damage) {
    }

    /** What happened during one tick. */
    public record Result(List<Round> rounds, List<Rocket> launches, List<Impact> impacts, List<TowerEvent> events,
                         int playerDamage, List<SoldierHit> soldierHits) {
        public static final Result NOTHING = new Result(List.of(), List.of(), List.of(), List.of(), 0, List.of());

        public Result(List<Round> rounds, List<Rocket> launches, List<Impact> impacts, List<TowerEvent> events,
                      int playerDamage) {
            this(rounds, launches, impacts, events, playerDamage, List.of());
        }

        public boolean isEmpty() {
            return rounds.isEmpty() && launches.isEmpty() && impacts.isEmpty() && events.isEmpty() && playerDamage == 0
                    && soldierHits.isEmpty();
        }

        /** Hurts the soldiers this tick hit (the frontend's view of them, in the space the towers were given). */
        public void hitSoldiers(SoldierTargets soldiers) {
            for (SoldierHit hit : soldierHits) {
                soldiers.hit(hit.soldier(), hit.damage());
            }
        }
    }

    /**
     * The longest stretch one tick may advance: after a pause (a dialog over the game, a stalled frame) the towers
     * carry on from where they were instead of firing everything they missed at once.
     */
    public static final double MAX_TICK_SECONDS = 0.25;

    private final GuardTowerSpec spec;
    private final Map<String, GuardTowerGun> guns = new HashMap<>();
    private final Map<String, Integer> lastHealth = new HashMap<>();
    private final List<Rocket> flying = new ArrayList<>();
    private final Map<Long, Double> remaining = new HashMap<>();
    private long nextRocket = 1;

    public GuardTowerSession(GuardTowerSpec spec) {
        this.spec = spec;
    }

    public GuardTowerSpec spec() {
        return spec;
    }

    /** The rockets in the air now, with their positions, for drawing. */
    public List<Rocket> rockets() {
        return List.copyOf(flying);
    }

    /**
     * Advances the towers.
     *
     * @param dt       seconds since the last tick
     * @param towers   every tower, alive or not (a dead one stays in the list so its end is noticed once)
     * @param playerX  the player's centre (with playerY)
     * @param walls    counts the walls between a tower and the player
     */
    public Result tick(double dt, List<Tower> towers, double playerX, double playerY, WallCounter walls) {
        return tick(dt, towers, playerX, playerY, List.of(), walls);
    }

    /**
     * Advances the towers, with the soldiers of the infantry strike in the maze.
     *
     * @param soldiers where the living soldiers stand (same space as the towers)
     */
    public Result tick(double dt, List<Tower> towers, double playerX, double playerY, List<Point2D> soldiers,
                       WallCounter walls) {
        dt = Math.min(Math.max(0, dt), MAX_TICK_SECONDS);
        List<Point2D> standing = soldiers == null ? List.of() : soldiers;
        List<Round> rounds = new ArrayList<>();
        List<Rocket> launches = new ArrayList<>();
        List<TowerEvent> events = new ArrayList<>();
        List<SoldierHit> soldierHits = new ArrayList<>();
        int damage = 0;
        // rockets already in the air fly first, so one launched in this tick does not fly for time before its launch
        List<Impact> impacts = fly(dt);
        for (Impact impact : impacts) {
            if (inBlast(impact, playerX, playerY, walls)) {
                damage += spec.rpgPlayerDamage();
            }
            for (Point2D soldier : standing) {
                if (inBlast(impact, soldier.getX(), soldier.getY(), walls)) {
                    soldierHits.add(new SoldierHit(soldier, spec.rpgPlayerDamage()));
                }
            }
        }
        for (Tower tower : towers) {
            events.addAll(healthEvents(tower));
            GuardTowerGun gun = guns.computeIfAbsent(tower.id(), id -> new GuardTowerGun(spec));
            boolean alive = tower.health() > 0;
            RangedTargeting.Target target = RangedTargeting.choose(standing, tower.x(), tower.y(), playerX, playerY,
                    (x, y) -> gun.canSee(tower.x(), tower.y(), x, y, walls.between(tower.x(), tower.y(), x, y)));
            GuardTowerGun.Shots shots = gun.tick(dt, alive && target.inSight());
            for (int i = 0; i < shots.gatlingRounds(); i++) {
                rounds.add(new Round(tower.id(), tower.x(), tower.y(), target.x(), target.y()));
                if (target.isSoldier()) {
                    soldierHits.add(new SoldierHit(target.soldier(), spec.gatlingDamage()));
                } else {
                    damage += spec.gatlingDamage();
                }
            }
            for (int i = 0; i < shots.rpgs(); i++) {
                launches.add(launch(tower, target.x(), target.y()));
            }
        }
        if (rounds.isEmpty() && launches.isEmpty() && impacts.isEmpty() && events.isEmpty() && damage == 0
                && soldierHits.isEmpty()) {
            return Result.NOTHING;
        }
        return new Result(List.copyOf(rounds), List.copyOf(launches), List.copyOf(impacts), List.copyOf(events), damage,
                List.copyOf(soldierHits));
    }

    /** Whether (x, y) is within the blast of a rocket that landed, with no wall between to shield it. */
    private boolean inBlast(Impact impact, double x, double y, WallCounter walls) {
        return Math.hypot(x - impact.x(), y - impact.y()) <= spec.rpgBlastRadius()
                && walls.between(impact.x(), impact.y(), x, y) == 0;
    }

    private List<TowerEvent> healthEvents(Tower tower) {
        Integer before = lastHealth.put(tower.id(), tower.health());
        if (before == null || tower.health() >= before) {
            return List.of();
        }
        if (tower.health() <= 0) {
            return List.of(new TowerEvent(tower.id(), tower.x(), tower.y(), Event.DESTROYED));
        }
        return List.of(new TowerEvent(tower.id(), tower.x(), tower.y(), Event.HIT));
    }

    private Rocket launch(Tower tower, double toX, double toY) {
        double angle = Math.atan2(toY - tower.y(), toX - tower.x());
        Rocket rocket = new Rocket(nextRocket++, tower.id(), tower.x(), tower.y(), toX, toY, tower.x(), tower.y(), angle);
        flying.add(rocket);
        remaining.put(rocket.id(), Math.hypot(toX - tower.x(), toY - tower.y()) / spec.rpgSpeed());
        return rocket;
    }

    private List<Impact> fly(double dt) {
        List<Impact> landed = new ArrayList<>();
        for (int i = flying.size() - 1; i >= 0; i--) {
            Rocket rocket = flying.get(i);
            double left = remaining.get(rocket.id()) - Math.max(0, dt);
            if (left <= 0) {
                flying.remove(i);
                remaining.remove(rocket.id());
                landed.add(0, new Impact(rocket.toX(), rocket.toY()));
            } else {
                remaining.put(rocket.id(), left);
                double total = Math.hypot(rocket.toX() - rocket.fromX(), rocket.toY() - rocket.fromY()) / spec.rpgSpeed();
                double share = total <= 0 ? 1 : Math.min(1, 1 - left / total);
                flying.set(i, new Rocket(rocket.id(), rocket.towerId(), rocket.fromX(), rocket.fromY(), rocket.toX(), rocket.toY(),
                        rocket.fromX() + (rocket.toX() - rocket.fromX()) * share,
                        rocket.fromY() + (rocket.toY() - rocket.fromY()) * share, rocket.angleRadians()));
            }
        }
        return landed;
    }

    /** New level (or restart): no rockets in the air, every tower starts its cycle afresh. */
    public void reset() {
        guns.clear();
        lastHealth.clear();
        flying.clear();
        remaining.clear();
    }
}
