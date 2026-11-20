package main.game.maze.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.remains.CrushZone;
import main.game.maze.common.remains.DeathCause;

/**
 * Killing rules shared by both frontends, applied through the {@link ICanDie} contract so JavaFX
 * characters and libGDX enemies die the same way. Frontends only say where an enemy's body is.
 */
public final class MortalityService {

    /** An enemy's body for crushing: centre and radius, in the same space as the wall. */
    public record Body(double centerX, double centerY, double radius) {
    }

    private MortalityService() {
    }

    /**
     * Kills every living, unprotected {@link ICanDie} among {@code characters} by {@code cause}
     * (other objects and enemies in respawn protection are left alone).
     *
     * @return the number of characters that actually died
     */
    public static int killAll(Iterable<?> characters, DeathCause cause) {
        return killAll(characters, cause, null);
    }

    /** As {@link #killAll(Iterable, DeathCause)}, crediting the kills to {@code attackerId}. */
    public static int killAll(Iterable<?> characters, DeathCause cause, String attackerId) {
        int killed = 0;
        for (Object character : characters) {
            if (character instanceof ICanDie mortal && kill(mortal, cause, attackerId)) {
                killed++;
            }
        }
        return killed;
    }

    /**
     * Crushes every living {@link ICanDie} whose body touches the collapsed wall segment
     * from (ax, ay) to (bx, by) ({@link DeathCause#CRUSHED}).
     *
     * @param bodyOf where a character's body is; null for characters that cannot be crushed
     * @return the number of characters that were actually crushed
     */
    public static <T> int crushAlong(Iterable<T> characters, Function<? super T, Body> bodyOf,
                                     double ax, double ay, double bx, double by) {
        return crushAlong(characters, bodyOf, ax, ay, bx, by, null);
    }

    /** As above, crediting crushed enemies to {@code attackerId} (who brought the wall down). */
    public static <T> int crushAlong(Iterable<T> characters, Function<? super T, Body> bodyOf,
                                     double ax, double ay, double bx, double by, String attackerId) {
        int crushed = 0;
        for (T character : characters) {
            if (!(character instanceof ICanDie mortal) || !mortal.isAlive()) {
                continue;
            }
            Body body = bodyOf.apply(character);
            if (body != null && CrushZone.crushes(ax, ay, bx, by, body.centerX(), body.centerY(), body.radius())
                    && kill(mortal, DeathCause.CRUSHED, attackerId)) {
                crushed++;
            }
        }
        return crushed;
    }

    /** Whether a wall separates the striker at its origin from the point (x, y). */
    @FunctionalInterface
    public interface LineOfSight {
        boolean blocked(double x, double y);
    }

    /**
     * Melee strike (sledgehammer): every living, unprotected {@link ICanDie} whose body is within
     * {@code reach} of (cx, cy) and not behind a wall takes {@code damage} of {@code cause}.
     * Walls themselves are never harmed.
     *
     * @return the characters that were struck, nearest first
     */
    public static <T> List<ICanDie> strikeAround(Iterable<T> characters, Function<? super T, Body> bodyOf,
                                                          double cx, double cy, double reach, int damage,
                                                          DeathCause cause, String attackerId,
                                                          LineOfSight wallBetween) {
        List<ICanDie> struck = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        for (T character : characters) {
            if (!(character instanceof ICanDie mortal) || !mortal.isAlive() || mortal.isInvulnerable()) {
                continue;
            }
            Body body = bodyOf.apply(character);
            if (body == null) {
                continue;
            }
            double distance = Math.hypot(body.centerX() - cx, body.centerY() - cy);
            if (distance - body.radius() <= reach && !wallBetween.blocked(body.centerX(), body.centerY())) {
                int at = 0;
                while (at < distances.size() && distances.get(at) <= distance) {
                    at++;
                }
                struck.add(at, mortal);
                distances.add(at, distance);
            }
        }
        for (ICanDie mortal : struck) {
            mortal.subtractHitPoints(damage, cause, attackerId);
        }
        return struck;
    }

    /** Deals a lethal hit; true only if it actually killed (not dead already, not protected). */
    private static boolean kill(ICanDie mortal, DeathCause cause, String attackerId) {
        if (!mortal.isAlive() || mortal.isInvulnerable()) {
            return false;
        }
        mortal.subtractHitPoints(mortal.getHitPoints(), cause, attackerId);
        return !mortal.isAlive();
    }
}
