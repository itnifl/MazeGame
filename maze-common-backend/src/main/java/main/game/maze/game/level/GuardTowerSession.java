package main.game.maze.game.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Everything the guard towers of one level do, decided once for both frontends. The frontend keeps the tower
 * enemies (their health, sprites and the damage they take from bullets and blasts) and calls {@link #tick} every
 * frame with where they stand and how healthy they are; the session answers with what happened: gatling rounds that
 * hit the player, rockets that took off and rockets that landed, and towers that were hit or blown up (for the
 * sounds and the destruction). A rocket flies at {@link GuardTowerSpec#rpgSpeed()} to where the player was when it
 * was fired, and explodes there. Coordinates are one world space (the frontend converts when it draws in another).
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

    /** A burst round: fired at the player, who takes {@link GuardTowerSpec#gatlingDamage()} for each. */
    public record Round(String towerId, double fromX, double fromY, double toX, double toY) {
    }

    /** What happened during one tick. */
    public record Result(List<Round> rounds, List<Rocket> launches, List<Impact> impacts, List<TowerEvent> events,
                         int playerDamage) {
        public static final Result NOTHING = new Result(List.of(), List.of(), List.of(), List.of(), 0);

        public boolean isEmpty() {
            return rounds.isEmpty() && launches.isEmpty() && impacts.isEmpty() && events.isEmpty() && playerDamage == 0;
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
        dt = Math.min(Math.max(0, dt), MAX_TICK_SECONDS);
        List<Round> rounds = new ArrayList<>();
        List<Rocket> launches = new ArrayList<>();
        List<TowerEvent> events = new ArrayList<>();
        int damage = 0;
        // rockets already in the air fly first, so one launched in this tick does not fly for time before its launch
        List<Impact> impacts = fly(dt);
        for (Impact impact : impacts) {
            boolean shielded = walls.between(impact.x(), impact.y(), playerX, playerY) > 0;
            if (!shielded && Math.hypot(playerX - impact.x(), playerY - impact.y()) <= spec.rpgBlastRadius()) {
                damage += spec.rpgPlayerDamage();
            }
        }
        for (Tower tower : towers) {
            events.addAll(healthEvents(tower));
            GuardTowerGun gun = guns.computeIfAbsent(tower.id(), id -> new GuardTowerGun(spec));
            boolean alive = tower.health() > 0;
            boolean sees = alive && gun.canSee(tower.x(), tower.y(), playerX, playerY,
                    walls.between(tower.x(), tower.y(), playerX, playerY));
            GuardTowerGun.Shots shots = gun.tick(dt, sees);
            for (int i = 0; i < shots.gatlingRounds(); i++) {
                rounds.add(new Round(tower.id(), tower.x(), tower.y(), playerX, playerY));
                damage += spec.gatlingDamage();
            }
            for (int i = 0; i < shots.rpgs(); i++) {
                launches.add(launch(tower, playerX, playerY));
            }
        }
        if (rounds.isEmpty() && launches.isEmpty() && impacts.isEmpty() && events.isEmpty() && damage == 0) {
            return Result.NOTHING;
        }
        return new Result(List.copyOf(rounds), List.copyOf(launches), List.copyOf(impacts), List.copyOf(events), damage);
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
