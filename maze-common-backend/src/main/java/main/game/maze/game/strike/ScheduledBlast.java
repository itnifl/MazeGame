package main.game.maze.game.strike;

/**
 * One blast that is on its way, in world space (y down), shared by both frontends: a thrown grenade, a fat bomb, a bomb
 * of an airstrike or the bunker buster's impact. The frontend sets it off with its own blast (flames, sound, shake) when
 * the {@link BlastSchedule} says it is due.
 *
 * @param secondsLeft  until it goes off
 * @param fuseSeconds  the whole fuse, for drawing how far it has burnt
 * @param x            where, world x
 * @param y            where, world y (down)
 * @param damage       of the blast
 * @param radius       how far its flames reach
 * @param hurtsPlayer  whether the player's own blast can hurt the player (grenades and bombs do, the airstrike does not)
 * @param showsFuse    whether a fuse glow is drawn where it lies (a grenade or a bomb lying on the floor, not a bomb falling)
 * @param label        for the status line ("Grenade exploded")
 */
public record ScheduledBlast(double secondsLeft, double fuseSeconds, double x, double y, int damage, double radius,
                             boolean hurtsPlayer, boolean showsFuse, String label) {

    public ScheduledBlast {
        secondsLeft = Math.max(0.0, secondsLeft);
        fuseSeconds = Math.max(secondsLeft, fuseSeconds);
        damage = Math.max(0, damage);
        radius = Math.max(0.0, radius);
        label = label == null ? "" : label;
    }

    /** The same blast {@code dt} seconds later. */
    ScheduledBlast after(double dt) {
        return new ScheduledBlast(secondsLeft - dt, fuseSeconds, x, y, damage, radius, hurtsPlayer, showsFuse, label);
    }

    /** How much of the fuse is left: 1 just set, 0 going off. */
    public double fuseLeft() {
        return fuseSeconds <= 0 ? 0.0 : secondsLeft / fuseSeconds;
    }
}
