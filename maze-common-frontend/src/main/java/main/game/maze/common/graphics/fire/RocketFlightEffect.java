package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * A rocket flying from (x1, y1) to (x2, y2) over its short life (the bunker buster): the {@link RocketEffect} drawn
 * where the rocket is at each moment, nose first. The blast at the end is the frontend's. Frontend-neutral.
 */
public record RocketFlightEffect(double x1, double y1, double x2, double y2) implements PuffEffect {

    /** Seconds the flight takes on screen. */
    public static final double FLIGHT_SECONDS = 0.3;

    @Override
    public double lifetimeSeconds() {
        return FLIGHT_SECONDS;
    }

    /** Where the rocket is at {@code progress} (0 at the muzzle, 1 at the end). */
    public RocketEffect rocketAt(double progress) {
        double t = Math.max(0, Math.min(1, progress));
        return new RocketEffect(x1 + (x2 - x1) * t, y1 + (y2 - y1) * t, Math.atan2(y2 - y1, x2 - x1));
    }

    @Override
    public List<FirePuff> puffs(double progress, double clockSeconds) {
        if (Double.isNaN(progress) || progress >= 1) {
            return List.of();
        }
        return rocketAt(progress).puffs(0, clockSeconds);
    }

    @Override
    public double[] bounds() {
        double[] start = rocketAt(0).bounds();
        double[] end = rocketAt(1).bounds();
        return new double[] {Math.min(start[0], end[0]), Math.min(start[1], end[1]),
                Math.max(start[2], end[2]), Math.max(start[3], end[3])};
    }
}
