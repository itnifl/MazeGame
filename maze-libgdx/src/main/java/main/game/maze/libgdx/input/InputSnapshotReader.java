package main.game.maze.libgdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import java.util.LinkedHashSet;
import java.util.Set;
import main.game.maze.common.input.EdgeKeyTracker;
import main.game.maze.common.input.InputFrame;

/**
 * Reads libGDX input once per frame and exposes an immutable snapshot.
 */
public final class InputSnapshotReader {

    private final EdgeKeyTracker<Integer> edgeKeyTracker = new EdgeKeyTracker<>();

    public InputFrame<Integer> read(Set<Integer> trackedKeys) {
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

        return new InputFrame<>(
                Set.copyOf(held),
                Set.copyOf(edge),
                Gdx.input.getX(),
                Gdx.input.getY(),
                Gdx.input.isButtonJustPressed(Buttons.LEFT));
    }
}
