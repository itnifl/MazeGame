package main.game.maze.service;

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
     * Kills every living {@link ICanDie} among {@code characters} by {@code cause}
     * (other objects are ignored).
     *
     * @return the number of characters killed
     */
    public static int killAll(Iterable<?> characters, DeathCause cause) {
        int killed = 0;
        for (Object character : characters) {
            if (character instanceof ICanDie mortal && mortal.isAlive()) {
                mortal.subtractHitPoints(mortal.getHitPoints(), cause);
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
     * @return the number of characters crushed
     */
    public static <T> int crushAlong(Iterable<T> characters, Function<? super T, Body> bodyOf,
                                     double ax, double ay, double bx, double by) {
        int crushed = 0;
        for (T character : characters) {
            if (!(character instanceof ICanDie mortal) || !mortal.isAlive()) {
                continue;
            }
            Body body = bodyOf.apply(character);
            if (body != null && CrushZone.crushes(ax, ay, bx, by, body.centerX(), body.centerY(), body.radius())) {
                mortal.subtractHitPoints(mortal.getHitPoints(), DeathCause.CRUSHED);
                crushed++;
            }
        }
        return crushed;
    }
}
