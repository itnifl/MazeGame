package main.game.maze.game.level;

/**
 * The mines round each sergeant, as defined in {@code levels.xmi}.
 *
 * @param count         mines in the circle round one sergeant
 * @param ringRadius    distance of the circle from the sergeant, in pixels
 * @param triggerRadius how close the player must come to set a mine off
 * @param pulseSeconds  one beat of the pulsating glow
 * @param blastRadius   reach of the blast along the corridors
 * @param blastDamage   damage of the blast to walls and enemies (a little less than the player takes)
 * @param playerDamage  damage the player takes when they set the mine off (a little)
 * @param holeRadius    radius of the hole the blast leaves in the ground
 */
public record MineSpec(int count, double ringRadius, double triggerRadius, double pulseSeconds,
                       double blastRadius, int blastDamage, int playerDamage, double holeRadius) {

    public MineSpec {
        if (count <= 0 || ringRadius <= 0 || triggerRadius <= 0 || pulseSeconds <= 0
                || blastRadius <= 0 || blastDamage <= 0 || playerDamage < 0 || holeRadius <= 0) {
            throw new IllegalArgumentException("mine values must be positive");
        }
    }
}
