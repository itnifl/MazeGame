package main.game.maze.game.ufo;

/**
 * The retaliation of a level, as defined in {@code levels.xmi} (see {@code UfoRaid} in {@code levels.ecore}): when the
 * player uses a weapon of {@code triggerTier} of the shop, {@code ufos} UFOs cross the map back and forth for
 * {@code seconds} and beam the ground close to the player; a beam that catches the player takes {@code damageFraction} of
 * their health unless a shield blocks it. At the same time {@code soldiers} red soldiers are dropped to hunt the player.
 *
 * @param triggerTier     CHEAP, MEDIUM or EXPENSIVE: the tier of the shop whose weapons bring the UFOs
 * @param ufos            how many UFOs
 * @param seconds         how long the UFO attack lasts
 * @param ufoSpeed        pixels per second
 * @param beamEverySeconds about how often one UFO fires (a little random)
 * @param chargeSeconds   how long the spot is marked before the beam comes
 * @param beamSeconds     how long a beam stays on
 * @param beamRadius      radius of a beamed spot, pixels
 * @param aimMinDistance  a beam lands at least this far from the player: never straight at them
 * @param aimMaxDistance  ... and at most this far
 * @param damageFraction  share of the player's maximum health a beam takes (0.5 = half)
 * @param soldiers        red soldiers dropped
 * @param soldierHealth   their health
 * @param soldierDamage   damage of one of their rounds
 * @param soldierSight    how far they see the player, pixels
 * @param soldierSeconds  how long they stay if not killed
 */
public record UfoRaidSpec(String triggerTier, int ufos, double seconds, double ufoSpeed, double beamEverySeconds,
                          double chargeSeconds, double beamSeconds, double beamRadius, double aimMinDistance,
                          double aimMaxDistance, double damageFraction, int soldiers, int soldierHealth,
                          int soldierDamage, double soldierSight, double soldierSeconds, String bannerText,
                          String ufoSprite, String beamSprite, String arriveSound, String beamSound) {

    public UfoRaidSpec {
        if (triggerTier == null || !(triggerTier.equals("CHEAP") || triggerTier.equals("MEDIUM") || triggerTier.equals("EXPENSIVE"))) {
            throw new IllegalArgumentException("the trigger tier is CHEAP, MEDIUM or EXPENSIVE: " + triggerTier);
        }
        if (ufos < 1 || !(seconds > 0) || !(ufoSpeed > 0) || !(beamEverySeconds > 0) || !(chargeSeconds >= 0)
                || !(beamSeconds > 0) || !(beamRadius > 0)) {   // (!(x > 0) also rejects NaN)
            throw new IllegalArgumentException("a UFO raid needs UFOs, a duration, a speed and beams that take time");
        }
        if (!(aimMinDistance >= 0) || !(aimMaxDistance >= aimMinDistance)) {
            throw new IllegalArgumentException("the beams land between aimMin and aimMax from the player (aimMin <= aimMax)");
        }
        if (!(damageFraction > 0 && damageFraction <= 1)) {
            throw new IllegalArgumentException("damageFraction is the share of the health a beam takes: above 0, at most 1");
        }
        if (soldiers < 0 || soldierHealth < 1 || soldierDamage < 0 || !(soldierSight > 0) || !(soldierSeconds > 0)) {
            throw new IllegalArgumentException("the red soldiers need health, sight and time to hunt");
        }
        if (ufoSprite == null || beamSprite == null) {
            throw new IllegalArgumentException("a UFO raid needs a UFO sprite and a beam sprite");
        }
        bannerText = bannerText == null ? "UFO RETALIATION" : bannerText;
    }
}
