package main.game.maze.common.graphics;

/**
 * Centralised audio facade. Game code requests sounds by classpath resource
 * path; the active engine decides how (or whether) to play them. Sound
 * playback is rate-limited per sound id by the engine, so callers do not need
 * to manage cooldowns themselves.
 *
 * <p>Pure business logic must never instantiate backend audio types directly;
 * it must route playback through {@link AudioEngine#get()}.
 */
public interface IAudioEngine {

    /**
     * Plays a sound resource (classpath path, e.g. {@code "/sound/scream.wav"}).
     * Failures are absorbed and reported via logging by the implementation;
     * never thrown to the caller.
     *
     * @param resourcePath classpath path of the audio resource
     */
    void play(String resourcePath);

    /**
     * Plays a sound at most once per {@code cooldownMs} window for the given
     * logical id. Useful for events that fire many times per second (collision
     * scream, etc.) where rapid retriggering is undesirable.
     *
     * @param resourcePath classpath path of the audio resource
     * @param soundId      logical key used to track cooldown; defaults to
     *                     {@code resourcePath} when {@code null}
     * @param cooldownMs   minimum milliseconds between successive plays
     */
    void playRateLimited(String resourcePath, String soundId, long cooldownMs);

    /**
     * Starts looping playback for the given logical channel.
     */
    void playLoop(String resourcePath, String channelId);

    /**
     * Stops playback for a logical channel started via {@link #playLoop}.
     */
    void stopChannel(String channelId);

    /** Stops all active playback and releases backing resources. */
    void dispose();
}


