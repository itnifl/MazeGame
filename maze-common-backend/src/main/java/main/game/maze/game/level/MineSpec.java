package main.game.maze.game.level;

/**
 * The mines round each sergeant, as defined in {@code levels.xmi}.
 *
 * @param count         mines in the circle round one sergeant
 * @param ringRadius    distance of the circle from the sergeant, in pixels
 * @param secondRingRadius distance of a second circle with the same number of mines, staggered; 0 = no second circle
 * @param triggerRadius how close the player must come to set a mine off
 * @param pulseSeconds  one beat of the pulsating glow
 * @param blastRadius   reach of the blast along the corridors
 * @param blastDamage   damage of the blast to walls and enemies (a little less than the player takes)
 * @param playerDamage  damage the player takes when they set the mine off (a little)
 * @param holeRadius    radius of the hole the blast leaves in the ground
 */
public record MineSpec(int count, double ringRadius, double secondRingRadius, double triggerRadius, double pulseSeconds,
                       double blastRadius, int blastDamage, int playerDamage, double holeRadius) {

    public MineSpec {
        if (secondRingRadius != 0 && secondRingRadius <= ringRadius) {
            throw new IllegalArgumentException("the second circle must lie outside the first");
        }
        if (count <= 0 || ringRadius <= 0 || triggerRadius <= 0 || pulseSeconds <= 0
                || blastRadius <= 0 || blastDamage <= 0 || playerDamage < 0 || holeRadius <= 0) {
            throw new IllegalArgumentException("mine values must be positive");
        }
    }

    /** Mines round one sergeant when nothing is in the way: one circle, or two. */
    public int minesPerSergeant() {
        return secondRingRadius > 0 ? 2 * count : count;
    }
}
