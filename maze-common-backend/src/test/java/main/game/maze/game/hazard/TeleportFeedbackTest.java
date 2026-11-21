package main.game.maze.game.hazard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.game.maze.common.graphics.fire.TeleportFlashEffect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Teleport feedback: flash in, the word on the screen, a deep voice")
class TeleportFeedbackTest {

    @Test
    @DisplayName("the word on the screen is Teleported, for a short moment")
    void banner() {
        assertEquals("Teleported", TeleportFeedback.BANNER_TEXT);
        assertTrue(TeleportFeedback.BANNER_SECONDS >= 1.0f && TeleportFeedback.BANNER_SECONDS <= 3.0f);
    }

    @Test
    @DisplayName("the player flashes in with the teleport flash, the right way up for the screen")
    void flash() {
        TeleportFlashEffect down = assertInstanceOf(TeleportFlashEffect.class, TeleportFeedback.flashAt(10, 20, false));
        TeleportFlashEffect up = assertInstanceOf(TeleportFlashEffect.class, TeleportFeedback.flashAt(10, 20, true));
        assertEquals(10, down.x(), 1e-9);
        assertEquals(20, down.y(), 1e-9);
        assertEquals(true, up.yUp());
    }

    @Test
    @DisplayName("the zap and the voice exist, and the voice is a real recording: 16-bit mono, about a second or two, not silent")
    void sounds() throws IOException, UnsupportedAudioFileException {
        assertNotNull(TeleportFeedbackTest.class.getResource(TeleportFeedback.ZAP_SOUND), "zap missing");
        var url = TeleportFeedbackTest.class.getResource(TeleportFeedback.VOICE_SOUND);
        assertNotNull(url, "voice missing");
        try (AudioInputStream in = AudioSystem.getAudioInputStream(url)) {
            AudioFormat format = in.getFormat();
            assertEquals(16, format.getSampleSizeInBits());
            assertEquals(1, format.getChannels());
            double seconds = in.getFrameLength() / (double) format.getFrameRate();
            assertTrue(seconds > 0.8 && seconds < 3.0, "a spoken word, not a jingle or a speech: " + seconds + " s");
            byte[] bytes = in.readAllBytes();
            double sum = 0;
            int samples = bytes.length / 2;
            for (int i = 0; i < samples; i++) {
                short s = (short) ((bytes[2 * i + 1] << 8) | (bytes[2 * i] & 0xff));
                sum += (s / 32768.0) * (s / 32768.0);
            }
            assertTrue(Math.sqrt(sum / samples) > 0.03, "audible, not silence");
        }
    }
}
