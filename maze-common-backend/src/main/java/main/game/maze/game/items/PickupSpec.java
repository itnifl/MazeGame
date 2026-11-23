package main.game.maze.game.items;

/**
 * One item of the pickup catalogue, as defined in {@code xmi/items/items.xmi}.
 *
 * @param amount          bombs, laser shots or sledgehammer swings granted; for HEART the percent
 *                        of max health restored
 * @param durationSeconds how long the effect lasts (SHIELD; how long the infantry strike's squad stays)
 * @param damage          damage per laser shot / sledgehammer swing / blast / soldier round
 * @param range           laser range / sledgehammer reach / throw distance / rocket flight / soldier sight, in pixels
 * @param useSound        played when the weapon is used (laser, sledgehammer); may be null
 * @param chargeSeconds   seconds the weapon charges up before the shot leaves (pulse beam cannon); 0 = at once
 * @param chargeSound     played while it charges; may be null
 * @param blastRadius     how far the flames of the weapon's blast reach, in pixels (grenades, fat bomb, bunker buster,
 *                        airstrike); 0 = no blast
 * @param fuseSeconds     seconds from use to the blast; for the airstrike, the delay before the first bomb falls
 * @param units           how many: bombs dropped by the airstrike, soldiers in the infantry strike
 * @param piercePower     bunker buster: the power its rocket carries through the walls and towers in its way
 */
public record PickupSpec(PickupKind kind, String displayName, String sprite, String pickupSound,
                         String useSound, int spawnWeight, int amount, double durationSeconds,
                         int damage, double range, double chargeSeconds, String chargeSound,
                         double blastRadius, double fuseSeconds, int units, int piercePower) {
}
