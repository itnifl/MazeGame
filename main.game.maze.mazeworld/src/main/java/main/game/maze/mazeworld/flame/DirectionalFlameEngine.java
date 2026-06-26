package main.game.maze.mazeworld.flame;

import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Shared directional flame corridor algorithm used by both the JavaFX and
 * libGDX frontends.
 *
 * <p>Each frontend adapts its enemy/player types to {@link FlameTarget} and
 * delegates here.  All damage-application, wall-stopping, and budget-tracking
 * logic therefore lives in one place (DRY, SRP).</p>
 *
 * <p>Rules enforced by the engine:</p>
 * <ul>
 *   <li>Targets and walls are processed in ascending distance order from the
 *       origin along the direction ray.</li>
 *   <li>A blocking target (enemy/wall) that survives after receiving damage
 *       stops the flame — no further targets in that direction are hit.</li>
 *   <li>Pass-through targets (e.g. the player) receive the remaining budget as
 *       damage but neither consume the budget nor stop the flame.</li>
 *   <li>A destroyed breakable wall allows the flame to continue beyond it.</li>
 *   <li>An indestructible wall always stops the flame.</li>
 * </ul>
 */
public final class DirectionalFlameEngine {

    private DirectionalFlameEngine() {
    }

    // -----------------------------------------------------------------------
    // Public API
    // -----------------------------------------------------------------------

    /**
     * Applies one directional flame corridor in direction {@code (dirX, dirY)}.
     *
     * @param candidates     all living, non-invulnerable potential targets; the engine
     *                       filters to only those inside the corridor
     * @param world          maze world used for breakable-wall lookup; may be {@code null}
     *                       (then walls are treated as indestructible)
     * @param wallVectors    live wall segment list from {@code world.getMazeVectors()};
     *                       must be the same object reference used by
     *                       {@code world.findBreakableWall()} so identity matching works
     * @param originX        blast origin X in world pixels
     * @param originY        blast origin Y in world pixels
     * @param dirX           direction component: +1 east, -1 west, 0 for north/south
     * @param dirY           direction component: +1 south, -1 north, 0 for east/west
     * @param damageBudget   maximum damage this direction may deal
     * @param maxRange       maximum reach in pixels
     * @param corridorHalfWidth half the perpendicular width of the damage corridor
     * @return total damage dealt in this direction
     */
    public static int applyDirectionalFlame(
            List<FlameTarget> candidates,
            GameMazeWorld world,
            List<Vector2D> wallVectors,
            double originX, double originY,
            int dirX, int dirY,
            int damageBudget, double maxRange,
            double corridorHalfWidth) {

        if (damageBudget <= 0 || maxRange <= 0d) {
            return 0;
        }

        // Filter candidates to corridor and record distances
        List<FlameEntry> targets = new ArrayList<>();
        for (FlameTarget candidate : candidates) {
            Double dist = projectedDistanceOnRay(
                    originX, originY,
                    candidate.centerX(), candidate.centerY(),
                    dirX, dirY, maxRange, corridorHalfWidth);
            if (dist != null) {
                targets.add(new FlameEntry(candidate, dist));
            }
        }
        targets.sort(Comparator.comparingDouble(FlameEntry::distance));

        int remaining = damageBudget;
        int applied = 0;
        int targetIndex = 0;
        double searchStart = 0d;

        while (remaining > 0 && searchStart <= maxRange) {
            WallHit nextWall = findNextWallHit(originX, originY, dirX, dirY, wallVectors, searchStart, maxRange, corridorHalfWidth);
            double wallDistance = nextWall == null ? Double.POSITIVE_INFINITY : nextWall.distance();

            // Process all targets that sit before the next wall
            while (targetIndex < targets.size()) {
                FlameEntry current = targets.get(targetIndex);

                // Wall between origin and target?
                if (wallVectors != null && !wallVectors.isEmpty()
                        && WallCollisionUtil.wallBetweenVectors(
                                originX, originY,
                                current.target().centerX(), current.target().centerY(),
                                wallVectors)) {
                    if (nextWall != null && world != null) {
                        BreakableWall bw = world.findBreakableWall(nextWall.wall());
                        if (bw != null) {
                            int toApply = Math.min(remaining, Math.max(0, bw.getRemainingHp()));
                            if (toApply > 0) {
                                boolean destroyed = world.applyWallDamage(bw, toApply);
                                remaining -= toApply;
                                applied += toApply;
                                if (!destroyed) {
                                    return applied; // wall survived → flame stops
                                }
                                searchStart = nextWall.distance(); // wall destroyed → advance
                                break; // re-evaluate wall list from new searchStart
                            }
                        }
                    }
                    return applied; // indestructible wall stops the flame
                }

                if (current.distance() >= wallDistance) {
                    break; // next wall is closer — handle below
                }

                if (current.target().isPassThrough()) {
                    // Pass-through: player receives remaining budget but does not block
                    if (remaining > 0) {
                        current.target().applyDamage(remaining);
                    }
                    targetIndex++;
                    continue;
                }

                // Blocking target (enemy)
                int hp = Math.max(0, current.target().hitPoints());
                if (hp > 0) {
                    int toApply = Math.min(hp, remaining);
                    current.target().applyDamage(toApply);
                    remaining -= toApply;
                    applied += toApply;
                    if (hp > toApply) {
                        return applied; // target survived → flame stops
                    }
                }
                targetIndex++;
                if (remaining <= 0) {
                    return applied;
                }
            }

            // All pre-wall targets processed — handle the wall itself
            if (nextWall == null || world == null) {
                break; // no wall or no world: nothing blocks, continue to end-of-range cleanup
            }

            BreakableWall bw = world.findBreakableWall(nextWall.wall());
            if (bw == null) {
                break; // indestructible wall — stops flame
            }

            int toApply = Math.min(remaining, Math.max(0, bw.getRemainingHp()));
            if (toApply <= 0) {
                break;
            }
            boolean destroyed = world.applyWallDamage(bw, toApply);
            remaining -= toApply;
            applied += toApply;
            if (!destroyed) {
                break; // wall survived — stops flame
            }
            searchStart = nextWall.distance(); // wall destroyed — advance past it
        }

        // Budget remains after all walls — apply to any leftover in-corridor targets
        while (remaining > 0 && targetIndex < targets.size()) {
            FlameEntry current = targets.get(targetIndex);
            if (current.target().isPassThrough()) {
                current.target().applyDamage(remaining);
                targetIndex++;
                continue;
            }
            int hp = Math.max(0, current.target().hitPoints());
            if (hp > 0) {
                int toApply = Math.min(hp, remaining);
                current.target().applyDamage(toApply);
                remaining -= toApply;
                applied += toApply;
                if (hp > toApply) {
                    return applied; // target survived
                }
            }
            targetIndex++;
        }
        return applied;
    }

    /**
     * Returns the pixel distance the flame visually reaches in direction
     * {@code (dirX, dirY)} before hitting a surviving wall, capped at
     * {@code maxRange}.  Call AFTER applying damage so destroyed walls are
     * already absent from {@code walls}.
     *
     * @param corridorHalfWidth perpendicular half-width of the flame corridor;
     *                          walls whose span overlaps this corridor are
     *                          considered blocking
     */
    public static double flameVisualRange(
            double originX, double originY,
            int dirX, int dirY,
            List<Vector2D> walls, double maxRange,
            double corridorHalfWidth) {
        WallHit hit = findNextWallHit(originX, originY, dirX, dirY, walls, 0d, maxRange, corridorHalfWidth);
        return hit == null ? maxRange : hit.distance();
    }

    // -----------------------------------------------------------------------
    // Internal helpers
    // -----------------------------------------------------------------------

    /**
     * Returns the signed ray-axis distance if {@code (targetX, targetY)} lies
     * within the flame corridor in direction {@code (dirX, dirY)}, or
     * {@code null} if outside the corridor or behind the origin.
     */
    static Double projectedDistanceOnRay(
            double originX, double originY,
            double targetX, double targetY,
            int dirX, int dirY,
            double maxRange, double corridorHalfWidth) {
        if (dirX != 0) {
            if (Math.abs(targetY - originY) > corridorHalfWidth) {
                return null;
            }
            double delta = targetX - originX;
            if (Math.signum(delta) != dirX) {
                return null;
            }
            double dist = Math.abs(delta);
            return dist <= maxRange ? dist : null;
        }
        if (Math.abs(targetX - originX) > corridorHalfWidth) {
            return null;
        }
        double delta = targetY - originY;
        if (Math.signum(delta) != dirY) {
            return null;
        }
        double dist = Math.abs(delta);
        return dist <= maxRange ? dist : null;
    }

    /**
     * Finds the nearest wall segment that the flame ray crosses in direction
     * {@code (dirX, dirY)}, beyond {@code searchStart} and within
     * {@code maxRange}.
     *
     * <p>A wall is considered in the flame path if its perpendicular span
     * overlaps the flame corridor (i.e. the corridor centred on the origin
     * with half-width {@code corridorHalfWidth}).  This is consistent with
     * how {@link #projectedDistanceOnRay} filters targets.</p>
     *
     * @param corridorHalfWidth perpendicular half-width of the flame corridor
     */
    static WallHit findNextWallHit(
            double originX, double originY,
            int dirX, int dirY,
            List<Vector2D> walls,
            double searchStart, double maxRange,
            double corridorHalfWidth) {
        if (walls == null || walls.isEmpty()) {
            return null;
        }
        WallHit nearest = null;
        for (Vector2D wall : walls) {
            double x1 = wall.getStart().getX();
            double y1 = wall.getStart().getY();
            double x2 = wall.getEnd().getX();
            double y2 = wall.getEnd().getY();

            if (dirX != 0 && Math.abs(x1 - x2) < 0.001d) {
                // Horizontal flame (east/west): blocked by vertical walls whose Y span
                // overlaps the flame corridor centred on originY.
                double minY = Math.min(y1, y2);
                double maxY = Math.max(y1, y2);
                if (originY + corridorHalfWidth < minY || originY - corridorHalfWidth > maxY) {
                    continue;
                }
                double dist = x1 - originX;
                if (Math.signum(dist) != dirX) {
                    continue;
                }
                double abs = Math.abs(dist);
                if (abs <= searchStart || abs > maxRange) {
                    continue;
                }
                if (nearest == null || abs < nearest.distance()) {
                    nearest = new WallHit(wall, abs);
                }
                continue;
            }

            if (dirY != 0 && Math.abs(y1 - y2) < 0.001d) {
                // Vertical flame (north/south): blocked by horizontal walls whose X span
                // overlaps the flame corridor centred on originX.
                double minX = Math.min(x1, x2);
                double maxX = Math.max(x1, x2);
                if (originX + corridorHalfWidth < minX || originX - corridorHalfWidth > maxX) {
                    continue;
                }
                double dist = y1 - originY;
                if (Math.signum(dist) != dirY) {
                    continue;
                }
                double abs = Math.abs(dist);
                if (abs <= searchStart || abs > maxRange) {
                    continue;
                }
                if (nearest == null || abs < nearest.distance()) {
                    nearest = new WallHit(wall, abs);
                }
            }
        }
        return nearest;
    }

    // -----------------------------------------------------------------------
    // Internal records
    // -----------------------------------------------------------------------

    record WallHit(Vector2D wall, double distance) {
    }

    private record FlameEntry(FlameTarget target, double distance) {
    }
}
