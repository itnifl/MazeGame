package main.game.maze.game.items;

/**
 * One item of the pickup catalogue, as defined in {@code xmi/items/items.xmi}.
 *
 * @param amount          bombs, laser shots or sledgehammer swings granted; for HEART the percent
 *                        of max health restored
 * @param durationSeconds how long the effect lasts (SHIELD)
 * @param damage          damage per laser shot / sledgehammer swing
 * @param range           laser range / sledgehammer reach, in pixels
 * @param useSound        played when the weapon is used (laser, sledgehammer); may be null
 * @param chargeSeconds   seconds the weapon charges up before the shot leaves (pulse beam cannon); 0 = at once
 * @param chargeSound     played while it charges; may be null
 */
public record PickupSpec(PickupKind kind, String displayName, String sprite, String pickupSound,
                         String useSound, int spawnWeight, int amount, double durationSeconds,
                         int damage, double range, double chargeSeconds, String chargeSound) {
}
