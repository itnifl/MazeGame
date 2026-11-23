package main.game.maze.game.strike;

import main.game.maze.game.items.PickupKind;

/**
 * One blast that is on its way, in world space (y down), shared by both frontends: a thrown grenade, a fat bomb, a bomb
 * of an airstrike or the bunker buster's impact. The frontend sets it off with its own blast (flames, sound, shake) when
 * the {@link BlastSchedule} says it is due; a {@link #nuclear} one as a round blast with a mushroom cloud.
 *
 * @param secondsLeft  until it goes off
 * @param fuseSeconds  the whole fuse, for drawing how far it has burnt
 * @param x            where, world x
 * @param y            where, world y (down)
 * @param damage       of the blast
 * @param radius       how far its flames reach
 * @param hurtsPlayer  whether the player's own blast can hurt the player (grenades and bombs do, the airstrike does not)
 * @param showsFuse    whether it lies on the floor with a burning fuse (a grenade or a bomb, not a bomb falling)
 * @param label        for the status line ("Grenade exploded")
 * @param source       the weapon it came from, for drawing it where it lies; may be null
 * @param nuclear      a round blast through walls with a mushroom cloud instead of the bomb's flames
 */
public record ScheduledBlast(double secondsLeft, double fuseSeconds, double x, double y, int damage, double radius,
                             boolean hurtsPlayer, boolean showsFuse, String label, PickupKind source, boolean nuclear) {

    /** A flame blast of no particular weapon. */
    public ScheduledBlast(double secondsLeft, double fuseSeconds, double x, double y, int damage, double radius,
                          boolean hurtsPlayer, boolean showsFuse, String label) {
        this(secondsLeft, fuseSeconds, x, y, damage, radius, hurtsPlayer, showsFuse, label, null, false);
    }

    public ScheduledBlast {
        secondsLeft = Math.max(0.0, secondsLeft);
        fuseSeconds = Math.max(secondsLeft, fuseSeconds);
        damage = Math.max(0, damage);
        radius = Math.max(0.0, radius);
        label = label == null ? "" : label;
    }

    /** The same blast {@code dt} seconds later. */
    ScheduledBlast after(double dt) {
        return new ScheduledBlast(secondsLeft - dt, fuseSeconds, x, y, damage, radius, hurtsPlayer, showsFuse, label,
                source, nuclear);
    }

    /** How much of the fuse is left: 1 just set, 0 going off. */
    public double fuseLeft() {
        return fuseSeconds <= 0 ? 0.0 : secondsLeft / fuseSeconds;
    }
}
