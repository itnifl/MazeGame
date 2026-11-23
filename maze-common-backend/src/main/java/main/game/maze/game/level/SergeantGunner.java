package main.game.maze.game.level;

import main.game.maze.game.targeting.RangedTargeting;
import main.game.maze.game.targeting.SoldierTargets;

/**
 * The gatling gun of one zombie sergeant, shared by both frontends: it sees the player within its
 * range with no wall between, then fires a burst of rounds, rests, and fires again for as long as it
 * sees the player. It never moves. The frontend asks {@link #canSee}, feeds the answer to
 * {@link #tick} every frame and applies {@link SergeantSpec#roundDamage()} for every round fired.
 */
public final class SergeantGunner {

    private final SergeantSpec spec;
    private double cooldown;
    private int roundsLeft;
    private double roundTimer;

    public SergeantGunner(SergeantSpec spec) {
        this.spec = spec;
    }

    /** Whether a sergeant at (sx, sy) sees a player at (px, py): in range and no wall in between. */
    public boolean canSee(double sx, double sy, double px, double py, boolean wallBetween) {
        return !wallBetween && Math.hypot(px - sx, py - sy) <= spec.sightRange();
    }

    /**
     * Advances the gun.
     *
     * @param dt           seconds since the last tick
     * @param seesPlayer   whether the sergeant currently sees the player; losing sight ends the burst
     * @return the rounds fired during this tick
     */
    public int tick(double dt, boolean seesPlayer) {
        cooldown = Math.max(0, cooldown - Math.max(0, dt));
        if (!seesPlayer) {
            roundsLeft = 0;
            return 0;
        }
        if (roundsLeft == 0 && cooldown == 0) {
            roundsLeft = spec.roundsPerBurst();
            roundTimer = 0;
            cooldown = spec.burstCooldownSeconds();
        } else if (roundsLeft > 0) {
            roundTimer -= Math.max(0, dt);
        }
        int fired = 0;
        while (roundsLeft > 0 && roundTimer <= 0) {
            fired++;
            roundsLeft--;
            roundTimer += spec.roundIntervalSeconds();
        }
        return fired;
    }

    /** Whether there is a wall between two points (the frontend knows its walls). */
    @FunctionalInterface
    public interface Walls {
        boolean between(double fromX, double fromY, double toX, double toY);
    }

    /** One tick of the gun: the rounds fired and whom they were fired at. */
    public record Volley(int rounds, RangedTargeting.Target target) {
    }

    /**
     * Advances the gun of a sergeant at (sx, sy): it fires at the nearest soldier it sees ({@link RangedTargeting}),
     * else at the player. The rounds hit the target: the caller hurts the soldier ({@link SoldierTargets#hit}) or the
     * player by {@link SergeantSpec#roundDamage()} each.
     */
    public Volley fire(double dt, double sx, double sy, double playerX, double playerY, SoldierTargets soldiers,
                       Walls walls) {
        RangedTargeting.Target target = RangedTargeting.choose(soldiers.positions(), sx, sy, playerX, playerY,
                (x, y) -> canSee(sx, sy, x, y, walls.between(sx, sy, x, y)));
        return new Volley(tick(dt, target.inSight()), target);
    }

    /** A fresh gun: no burst under way, no cooldown. */
    public void reset() {
        cooldown = 0;
        roundsLeft = 0;
        roundTimer = 0;
    }
}
