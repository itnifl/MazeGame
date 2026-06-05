package main.game.maze.libgdx.backend;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import main.game.maze.common.graphics.IAudioEngine;

/**
 * libGDX audio engine. Maps classpath-style resource paths (e.g.
 * {@code "/sound/scream.wav"}) to libGDX {@link com.badlogic.gdx.files.FileHandle}
 * lookups under {@code Gdx.files.internal} (relative to the classpath root,
 * leading slash trimmed). Caches one {@link Sound} per resource and supports
 * per-id cooldowns identical to the JavaFX engine so call sites are portable.
 *
 * <p>If libGDX is not initialised, or a resource cannot be loaded, the engine
 * disables that resource and logs a warning. Never throws.
 */
public final class GdxAudioEngine implements IAudioEngine {

    private static final Logger LOGGER = Logger.getLogger(GdxAudioEngine.class.getName());

    private final ConcurrentHashMap<String, Sound> soundsByResource = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LoopState> loopsByChannel = new ConcurrentHashMap<>();
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
        if (Gdx.audio == null || Gdx.files == null) return;

        String key = soundId == null ? resourcePath : soundId;
        if (cooldownMs > 0) {
            long now = System.currentTimeMillis();
            Long last = lastPlayedAtMs.get(key);
            if (last != null && (now - last) < cooldownMs) {
                return;
            }
            lastPlayedAtMs.put(key, now);
        }

        Sound sound = soundsByResource.computeIfAbsent(resourcePath, this::buildSound);
        if (sound == null) {
            disabledResources.add(resourcePath);
            return;
        }

        try {
            sound.play();
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "Failed to play " + resourcePath, ex);
        }
    }

    @Override
    public void playLoop(String resourcePath, String channelId) {
        if (resourcePath == null || resourcePath.isBlank()) return;
        if (channelId == null || channelId.isBlank()) return;
        if (disabledResources.contains(resourcePath)) return;
        if (Gdx.audio == null || Gdx.files == null) return;

        Sound sound = soundsByResource.computeIfAbsent(resourcePath, this::buildSound);
        if (sound == null) {
            disabledResources.add(resourcePath);
            return;
        }

        stopChannel(channelId);
        try {
            long playbackId = sound.loop();
            loopsByChannel.put(channelId, new LoopState(sound, playbackId));
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "Failed to loop " + resourcePath + " on channel " + channelId, ex);
        }
    }

    @Override
    public void stopChannel(String channelId) {
        if (channelId == null || channelId.isBlank()) return;
        LoopState state = loopsByChannel.remove(channelId);
        if (state == null) return;
        try {
            state.sound.stop(state.playbackId);
        } catch (Exception ignored) {
            // best-effort stop
        }
    }

    @Override
    public void dispose() {
        for (String channelId : loopsByChannel.keySet()) {
            stopChannel(channelId);
        }
        loopsByChannel.clear();
        for (Sound sound : soundsByResource.values()) {
            try {
                sound.dispose();
            } catch (Exception ignored) {
                // best-effort cleanup
            }
        }
        soundsByResource.clear();
        disabledResources.clear();
        lastPlayedAtMs.clear();
    }

    private static final class LoopState {
        private final Sound sound;
        private final long playbackId;

        private LoopState(Sound sound, long playbackId) {
            this.sound = sound;
            this.playbackId = playbackId;
        }
    }

    private Sound buildSound(String resourcePath) {
        try {
            String relative = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
            FileHandle handle = Gdx.files.internal(relative);
            if (!handle.exists()) {
                LOGGER.warning("Missing audio resource: " + resourcePath);
                return null;
            }
            return Gdx.audio.newSound(handle);
        } catch (Exception ex) {
            LOGGER.warning("Cannot load audio " + resourcePath + ": " + ex.getMessage());
            return null;
        }
    }
}


