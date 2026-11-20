package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * A short visual effect drawn as layered {@link FirePuff}s (laser beam, sledgehammer swing, hit
 * reaction, pickup sparkle). Frontend-neutral: each frontend has one adapter that plays any
 * {@code PuffEffect} with its explosion renderer.
 */
public interface PuffEffect {

    /** How long the effect plays. */
    double lifetimeSeconds();

    /**
     * @param progress     0 at the start, 1 when over
     * @param clockSeconds running clock for flicker
     * @return puffs ordered back to front; empty when {@code progress >= 1}
     */
    List<FirePuff> puffs(double progress, double clockSeconds);

    /** Area containing every puff over the whole life: {minX, minY, maxX, maxY}. */
    double[] bounds();
}
