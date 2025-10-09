package main.game.maze;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.game.maze.actions.RestartGameAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests RestartGameAction while stubbing App.inGameMediaPlayer with a real, muted MediaPlayer
 * sourced from a generated silent WAV written using only java.base (no java.desktop).
 */
public class RestartGameActionTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    /** Run a task on the FX thread and surface any thrown errors to the test thread. */
    private static void runOnFx(Runnable r) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        final Throwable[] err = new Throwable[1];
        Platform.runLater(() -> {
            try { r.run(); } catch (Throwable t) { err[0] = t; } finally { done.countDown(); }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "FX task timed out");
        if (err[0] != null) {
            if (err[0] instanceof RuntimeException re) throw re;
            if (err[0] instanceof Error e) throw e;
            throw new RuntimeException(err[0]);
        }
    }

    /**
     * Write a minimal valid PCM WAV (RIFF) file with silence (zeros).
     * Uses 1 channel, 16-bit samples, 44.1 kHz, ~0.1s duration.
     */
    private static File writeSilentWav() throws Exception {
        final int channels = 1;
        final int sampleRate = 44100;
        final int bitsPerSample = 16;
        final int bytesPerSample = bitsPerSample / 8;
        final int numSamples = sampleRate / 10; // ~0.1s (4410 samples)
        final int subchunk2Size = numSamples * channels * bytesPerSample;
        final int byteRate = sampleRate * channels * bytesPerSample;
        final int blockAlign = channels * bytesPerSample;
        final int chunkSize = 36 + subchunk2Size; // 4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)

        File f = File.createTempFile("silent-", ".wav");
        f.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(f)) {
            // RIFF header
            out.write(new byte[]{'R','I','F','F'});
            out.write(le32(chunkSize));
            out.write(new byte[]{'W','A','V','E'});

            // fmt  subchunk
            out.write(new byte[]{'f','m','t',' '});
            out.write(le32(16));                 // Subchunk1Size for PCM
            out.write(le16(1));                  // AudioFormat = 1 (PCM)
            out.write(le16((short) channels));   // NumChannels
            out.write(le32(sampleRate));         // SampleRate
            out.write(le32(byteRate));           // ByteRate
            out.write(le16((short) blockAlign)); // BlockAlign
            out.write(le16((short) bitsPerSample)); // BitsPerSample

            // data subchunk
            out.write(new byte[]{'d','a','t','a'});
            out.write(le32(subchunk2Size));

            // PCM data: all zeros (silence)
            byte[] zeros = new byte[subchunk2Size];
            out.write(zeros);
        }
        return f;
        }

    private static byte[] le16(int v) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) v).array();
    }
    private static byte[] le32(int v) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(v).array();
    }

    /** Ensure App.inGameMediaPlayer is a real, muted MediaPlayer backed by our silent WAV. */
    private static void ensureMediaPlayerReady() throws Exception {
        if (App.inGameMediaPlayer != null) return;
        File wav = writeSilentWav();
        Media media = new Media(wav.toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);
        mp.setMute(true);
        App.inGameMediaPlayer = mp;
    }

    @Test
    @DisplayName("Load replaces the content root once")
    @org.junit.jupiter.api.Disabled("Not ready yet: requires refactor to allow injecting a mock GameController")
    void loadReplacesRoot() throws Exception {
        runOnFx(() -> {
            try {
                ensureMediaPlayerReady();

                AnchorPane oldRoot = new AnchorPane();
                Stage stage = new Stage();
                stage.setScene(new Scene(oldRoot, 800, 600));

                RestartGameAction action = new RestartGameAction(oldRoot);
                action.Load();

                assertEquals(1, oldRoot.getChildren().size(),
                        "RestartGameAction should replace content with a single new child/root");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    @DisplayName("Repeated Load calls do not accumulate children")
    @org.junit.jupiter.api.Disabled("Not ready yet: requires refactor to allow injecting a mock GameController")
    void repeatedLoadDoesNotAccumulateChildren() throws Exception {
        runOnFx(() -> {
            try {
                ensureMediaPlayerReady();

                AnchorPane oldRoot = new AnchorPane();
                Stage stage = new Stage();
                stage.setScene(new Scene(oldRoot, 800, 600));

                RestartGameAction action = new RestartGameAction(oldRoot);
                action.Load();
                action.Load(); // intentionally twice

                assertEquals(1, oldRoot.getChildren().size(),
                        "Content must remain a single child after repeated Load()");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
