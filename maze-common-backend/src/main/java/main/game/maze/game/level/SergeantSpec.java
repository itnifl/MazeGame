package main.game.maze.game.level;

/**
 * The zombie sergeant, as defined in {@code levels.xmi}: a stationary enemy that fires its gatling gun
 * in bursts at a player it can see, and drops the gun when killed.
 *
 * @param sightRange            pixels; the sergeant only fires at a player this close with no wall between
 * @param roundsPerBurst        rounds fired in one burst
 * @param roundDamage           damage of each round that hits the player
 * @param roundIntervalSeconds  time between the rounds of a burst
 * @param burstCooldownSeconds  pause between bursts, measured from the start of a burst
 * @param dropAmount            bursts the dropped gatling gun holds
 * @param postDistanceFromHeart pixels from the heart that sergeants stand guard
 */
public record SergeantSpec(String displayName, int health, double sightRange, int roundsPerBurst,
                           int roundDamage, double roundIntervalSeconds, double burstCooldownSeconds,
                           String sprite, String fireSound, DropWeapon dropWeapon, int dropAmount,
                           double postDistanceFromHeart) {

    public SergeantSpec {
        if (health <= 0 || sightRange <= 0 || roundsPerBurst <= 0 || roundDamage < 0
                || roundIntervalSeconds <= 0 || burstCooldownSeconds <= 0 || dropAmount <= 0) {
            throw new IllegalArgumentException("sergeant values must be positive: " + displayName);
        }
    }

    /** How long one burst lasts, from its first round to its last. */
    public double burstDurationSeconds() {
        return (roundsPerBurst - 1) * roundIntervalSeconds;
    }
}
