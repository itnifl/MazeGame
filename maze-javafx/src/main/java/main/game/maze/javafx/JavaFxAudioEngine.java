package main.game.maze.javafx;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.common.graphics.UiScheduler;

/**
 * Production audio engine backed by JavaFX {@code Media}/{@code MediaPlayer}.
 *
 * <p>Maintains a single cached {@link MediaPlayer} per classpath resource so
 * repeated calls for the same sound do not reload the file. Playback is
 * dispatched onto the FX application thread via {@link UiScheduler} so callers
 * may invoke {@link #play} from any thread.
 *
 * <p>If the FX media backend is unavailable (e.g. headless CI without
 * GStreamer) the engine disables the offending sound and logs a warning;
 * it never throws.
 */
public final class JavaFxAudioEngine implements IAudioEngine {

    private static final Logger LOGGER = Logger.getLogger(JavaFxAudioEngine.class.getName());

    private final ConcurrentHashMap<String, MediaPlayer> playersByResource = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, MediaPlayer> loopPlayersByChannel = new ConcurrentHashMap<>();
    private final Set<String> disabledResources = ConcurrentHashMap.newKeySet();
    private final ConcurrentHashMap<String, Long> lastPlayedAtMs = new ConcurrentHashMap<>();

    @Override
    public void play(String resourcePath) {
        playRateLimited(resourcePath, resourcePath, 0L);
    }

    @Override
    public void playRateLimited(String resourcePath, String soundId, long cooldownMs) {
        if (resourcePath == null || resourcePath.isBlank()) return;
        if (disabledResources.contains(resourcePath)) return;

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
            disabledResources.add(resourcePath);
            return;
        }

        UiScheduler.get().runOnUiThread(() -> {
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
    public void playLoop(String resourcePath, String channelId) {
        if (resourcePath == null || resourcePath.isBlank()) return;
        if (channelId == null || channelId.isBlank()) return;
        if (disabledResources.contains(resourcePath)) return;

        MediaPlayer next = buildPlayer(resourcePath);
        if (next == null) {
            disabledResources.add(resourcePath);
            return;
        }

        next.setCycleCount(MediaPlayer.INDEFINITE);
        MediaPlayer previous = loopPlayersByChannel.put(channelId, next);
        UiScheduler.get().runOnUiThread(() -> {
            try {
                stopAndDispose(previous);
                next.seek(Duration.ZERO);
                next.play();
            } catch (Exception ex) {
                LOGGER.log(Level.FINE, "Failed to loop " + resourcePath + " on channel " + channelId, ex);
            }
        });
    }

    @Override
    public void stopChannel(String channelId) {
        if (channelId == null || channelId.isBlank()) return;
        MediaPlayer player = loopPlayersByChannel.remove(channelId);
        if (player == null) return;
        UiScheduler.get().runOnUiThread(() -> stopAndDispose(player));
    }

    @Override
    public void dispose() {
        for (MediaPlayer loopPlayer : loopPlayersByChannel.values()) {
            stopAndDispose(loopPlayer);
        }
        loopPlayersByChannel.clear();
        for (MediaPlayer player : playersByResource.values()) {
            stopAndDispose(player);
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

    private static void stopAndDispose(MediaPlayer player) {
        if (player == null) return;
        try {
            player.stop();
            player.dispose();
        } catch (Exception ignored) {
            // best-effort cleanup
        }
    }
}
