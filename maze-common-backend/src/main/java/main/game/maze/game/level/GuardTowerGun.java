package main.game.maze.game.level;

/**
 * The weapons of one guard tower, shared by both frontends. The tower works to a steady cycle of
 * {@link GuardTowerSpec#rpgIntervalSeconds()}: an rpg at the start of each cycle and, halfway through, a burst of
 * gatling rounds, so the gatling fires in between the rpgs. The cycle keeps running all the time; shots only happen
 * while the tower sees the player (in range, no more than {@code maxWallsOver} walls between) and is alive.
 */
public final class GuardTowerGun {

    /** What happened during one tick. */
    public record Shots(int rpgs, int gatlingRounds) {
        public static final Shots NONE = new Shots(0, 0);
    }

    private final GuardTowerSpec spec;
    private double clock;
    private boolean fresh = true;

    public GuardTowerGun(GuardTowerSpec spec) {
        this.spec = spec;
    }

    /** Whether a tower at (tx, ty) can shoot at a player at (px, py), with {@code wallsBetween} walls in between. */
    public boolean canSee(double tx, double ty, double px, double py, int wallsBetween) {
        return wallsBetween <= spec.maxWallsOver() && Math.hypot(px - tx, py - ty) <= spec.sightRange();
    }

    /**
     * Advances the cycle.
     *
     * @param dt         seconds since the last tick
     * @param seesPlayer whether the tower may shoot now; when false the shots of this tick are skipped
     */
    public Shots tick(double dt, boolean seesPlayer) {
        double from = fresh ? -1e-9 : clock;
        fresh = false;
        clock += Math.max(0, dt);
        if (!seesPlayer) {
            return Shots.NONE;
        }
        double interval = spec.rpgIntervalSeconds();
        int rpgs = 0;
        int rounds = 0;
        for (long cycle = (long) Math.floor(Math.max(from, 0) / interval); cycle * interval <= clock; cycle++) {
            double start = cycle * interval;
            if (start > from && start <= clock) {
                rpgs++;
            }
            for (int round = 0; round < spec.gatlingRounds(); round++) {
                double at = start + spec.gatlingStartSeconds() + round * spec.gatlingRoundIntervalSeconds();
                if (at > from && at <= clock) {
                    rounds++;
                }
            }
        }
        return new Shots(rpgs, rounds);
    }

    /** Restarts the cycle: the first rpg comes at the next tick. */
    public void reset() {
        clock = 0;
        fresh = true;
    }
}
