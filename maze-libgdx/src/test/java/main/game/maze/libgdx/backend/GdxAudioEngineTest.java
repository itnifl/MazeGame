package main.game.maze.libgdx.backend;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class GdxAudioEngineTest {
    @AfterEach
    void reset() {
        Gdx.audio = null;
        Gdx.files = null;
    }

    @Test
    void streamsLoopsAndDisposesReplacedAndStoppedChannels() {
        Gdx.audio = mock(Audio.class);
        Gdx.files = mock(Files.class);
        FileHandle file = mock(FileHandle.class);
        Music first = mock(Music.class);
        Music second = mock(Music.class);
        when(Gdx.files.internal("music.wav")).thenReturn(file);
        when(file.exists()).thenReturn(true);
        when(Gdx.audio.newMusic(file)).thenReturn(first, second);
        GdxAudioEngine engine = new GdxAudioEngine();

        engine.playLoop("/music.wav", "menu");
        verify(first).setLooping(true);
        verify(first).play();
        verify(Gdx.audio, never()).newSound(file);

        engine.playLoop("/music.wav", "menu");
        verify(first).stop();
        verify(first).dispose();
        engine.dispose();
        verify(second).stop();
        verify(second).dispose();
    }
}
