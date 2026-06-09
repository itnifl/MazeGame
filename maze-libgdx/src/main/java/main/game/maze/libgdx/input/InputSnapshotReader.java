package main.game.maze.libgdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Reads libGDX input once per frame and exposes an immutable snapshot.
 */
public final class InputSnapshotReader {

    private final EdgeKeyTracker edgeKeyTracker = new EdgeKeyTracker();

    public InputFrame read(Set<Integer> trackedKeys) {
        Set<Integer> held = new LinkedHashSet<>();
        Set<Integer> edge = new LinkedHashSet<>();

        for (int keyCode : trackedKeys) {
            boolean pressed = Gdx.input.isKeyPressed(keyCode);
            if (pressed) {
                held.add(keyCode);
            }
            if (edgeKeyTracker.consumeEdge(keyCode, pressed)) {
                edge.add(keyCode);
            }
        }

        return new InputFrame(
                Set.copyOf(held),
                Set.copyOf(edge),
                Gdx.input.getX(),
                Gdx.input.getY(),
                Gdx.input.isButtonJustPressed(Buttons.LEFT));
    }

    public boolean isJustPressed(int keyCode) {
        return Gdx.input.isKeyJustPressed(keyCode);
    }

    public void reset() {
        edgeKeyTracker.reset();
    }
}
