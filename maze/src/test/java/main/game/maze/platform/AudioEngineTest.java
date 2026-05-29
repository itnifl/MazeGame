package main.game.maze.platform;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AudioEngineTest {

    private NoopAudioEngine fake;

    @BeforeEach
    void installFake() {
        fake = new NoopAudioEngine();
        AudioEngine.set(fake);
    }

    @AfterEach
    void restoreDefault() {
        AudioEngine.reset();
    }

    @Test
    @DisplayName("play records the resource path")
    void playRecords() {
        AudioEngine.get().play("/sound/scream.wav");

        List<String> played = fake.playedResources();
        assertEquals(1, played.size());
        assertEquals("/sound/scream.wav", played.get(0));
    }

    @Test
    @DisplayName("playRateLimited records the resource path regardless of cooldown")
    void playRateLimitedRecords() {
        AudioEngine.get().playRateLimited("/sound/a.wav", "id-a", 1000L);
        AudioEngine.get().playRateLimited("/sound/b.wav", "id-b", 1000L);

        assertEquals(List.of("/sound/a.wav", "/sound/b.wav"), fake.playedResources());
    }

    @Test
    @DisplayName("set(null) is rejected")
    void setNullRejected() {
        assertThrows(IllegalArgumentException.class, () -> AudioEngine.set(null));
    }

    @Test
    @DisplayName("dispose clears recorded resources on the noop engine")
    void disposeClearsHistory() {
        AudioEngine.get().play("/x.wav");
        AudioEngine.get().dispose();

        assertTrue(fake.playedResources().isEmpty());
    }
}
