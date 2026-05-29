package main.game.maze.platform;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Production audio engine backed by JavaFX {@code Media}/{@code MediaPlayer}.
 *
 * <p>Maintains a single cached {@link MediaPlayer} per classpath resource so
 * repeated calls for the same sound do not reload the file. Playback is
 * dispatched onto the FX application thread via {@link FxScheduler} so callers
 * may invoke {@link #play} from any thread.
 *
 * <p>If the FX media backend is unavailable (e.g. headless CI without GStreamer)
 * the engine disables the offending sound and logs a warning; it never throws.
 */
public final class JavaFxAudioEngine implements IAudioEngine {

    private static final Logger LOGGER = Logger.getLogger(JavaFxAudioEngine.class.getName());

    private final ConcurrentHashMap<String, MediaPlayer> playersByResource = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Boolean> disabledResources = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> lastPlayedAtMs = new ConcurrentHashMap<>();

    @Override
    public void play(String resourcePath) {
        playRateLimited(resourcePath, resourcePath, 0L);
    }

    @Override
    public void playRateLimited(String resourcePath, String soundId, long cooldownMs) {
        if (resourcePath == null || resourcePath.isBlank()) return;
        if (Boolean.TRUE.equals(disabledResources.get(resourcePath))) return;

        String key = soundId == null ? resourcePath : soundId;
        if (cooldownMs > 0) {
            long now = System.currentTimeMillis();
            Long last = lastPlayedAtMs.get(key);
            if (last != null && (now - last) < cooldownMs) {
                return;
            }
            lastPlayedAtMs.put(key, now);
        }

        MediaPlayer player = playersByResource.computeIfAbsent(resourcePath, this::buildPlayer);
        if (player == null) {
            disabledResources.put(resourcePath, Boolean.TRUE);
            return;
        }

        FxScheduler.get().runOnFxThread(() -> {
            try {
                if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                    player.stop();
                }
                player.seek(Duration.ZERO);
                player.play();
            } catch (Exception ex) {
                LOGGER.log(Level.FINE, "Failed to play " + resourcePath, ex);
            }
        });
    }

    @Override
    public void dispose() {
        for (MediaPlayer player : playersByResource.values()) {
            try {
                player.stop();
                player.dispose();
            } catch (Exception ignored) {
                // best-effort cleanup
            }
        }
        playersByResource.clear();
        disabledResources.clear();
        lastPlayedAtMs.clear();
    }

    private MediaPlayer buildPlayer(String resourcePath) {
        try {
            var url = Objects.requireNonNull(JavaFxAudioEngine.class.getResource(resourcePath),
                    () -> "Missing audio resource: " + resourcePath);
            Media media = new Media(url.toExternalForm());
            return new MediaPlayer(media);
        } catch (MediaException mediaEx) {
            LOGGER.warning("Media backend rejected " + resourcePath + ": " + mediaEx.getMessage());
            return null;
        } catch (Exception ex) {
            LOGGER.warning("Cannot load audio " + resourcePath + ": " + ex.getMessage());
            return null;
        }
    }
}
