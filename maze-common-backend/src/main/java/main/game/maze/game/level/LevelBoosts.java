package main.game.maze.game.level;

/**
 * Upgrades bought in the shop that last for the next level only, shared by both frontends.
 *
 * @param shieldDurationFactor a shield cube picked up during the level lasts this many times as long (1 = normal)
 * @param bombPowerFactor      the player's planted bombs (the bomb and the fat bomb) reach this many times as far and do
 *                             this many times the damage (1 = normal)
 */
public record LevelBoosts(double shieldDurationFactor, double bombPowerFactor) {

    /** No boosts: everything as normal. */
    public static final LevelBoosts NONE = new LevelBoosts(1.0, 1.0);

    public LevelBoosts {
        shieldDurationFactor = sanitise(shieldDurationFactor);
        bombPowerFactor = sanitise(bombPowerFactor);
    }

    /** With the shield factor raised to {@code factor} (never lowered: buying the same boost twice does not stack). */
    public LevelBoosts withShield(double factor) {
        return new LevelBoosts(Math.max(shieldDurationFactor, sanitise(factor)), bombPowerFactor);
    }

    /** With the bomb factor raised to {@code factor} (never lowered: buying the same boost twice does not stack). */
    public LevelBoosts withBombPower(double factor) {
        return new LevelBoosts(shieldDurationFactor, Math.max(bombPowerFactor, sanitise(factor)));
    }

    public boolean hasShieldBoost() {
        return shieldDurationFactor > 1.0;
    }

    public boolean hasBombBoost() {
        return bombPowerFactor > 1.0;
    }

    /** Bomb damage with the boost applied. */
    public int boostedBombDamage(int damage) {
        return (int) Math.round(Math.max(0, damage) * bombPowerFactor);
    }

    /** Bomb flame reach with the boost applied. */
    public double boostedBombRange(double range) {
        return Math.max(0, range) * bombPowerFactor;
    }

    private static double sanitise(double factor) {
        return Double.isFinite(factor) && factor >= 1.0 ? factor : 1.0;
    }
}
