package main.game.maze.game.level;

/**
 * What happens when the player is teleported, as defined in {@code levels.xmi}: the cooldown, the word that
 * appears on the screen and is spoken, and the sounds. The same in both frontends.
 *
 * @param cooldownSeconds after any teleport the player cannot be teleported again for this long
 * @param bannerText      the word on the screen
 * @param bannerSeconds   how long it stays, including its fades
 * @param zapSound        classpath path of the zap sound, or null
 * @param voiceSound      classpath path of the spoken word, or null
 */
public record TeleportRulesSpec(double cooldownSeconds, String bannerText, double bannerSeconds,
                                String zapSound, String voiceSound) {

    /** What a level without rules of its own uses; the same as the defaults of the metamodel. */
    public static final TeleportRulesSpec DEFAULT = new TeleportRulesSpec(2.5, "Teleported", 1.6,
            "/main/game/maze/sounds/teleport.wav", "/main/game/maze/sounds/teleported.wav");

    public TeleportRulesSpec {
        if (cooldownSeconds < 0 || bannerSeconds < 0) {
            throw new IllegalArgumentException("teleport times cannot be negative");
        }
    }
}
