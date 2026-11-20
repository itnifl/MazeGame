package main.game.maze.game.level;

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

    /** A fresh gun: no burst under way, no cooldown. */
    public void reset() {
        cooldown = 0;
        roundsLeft = 0;
        roundTimer = 0;
    }
}
