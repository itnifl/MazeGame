package main.game.maze.libgdx.controller;

/**
 * Edge-trigger key latch helper for mode-level keyboard actions.
 */
public final class GdxModeInputController {

    private boolean escLatch;
    private boolean hLatch;
    private boolean oLatch;
    private boolean tLatch;

    public boolean consumeEsc(boolean escPressed) {
        return consumeEdge(escPressed, Key.ESC);
    }

    public boolean consumeH(boolean hPressed) {
        return consumeEdge(hPressed, Key.H);
    }

    public boolean consumeO(boolean oPressed) {
        return consumeEdge(oPressed, Key.O);
    }

    public boolean consumeT(boolean tPressed) {
        return consumeEdge(tPressed, Key.T);
    }

    public void reset() {
        escLatch = false;
        hLatch = false;
        oLatch = false;
        tLatch = false;
    }

    private boolean consumeEdge(boolean pressed, Key key) {
        boolean latch = switch (key) {
            case ESC -> escLatch;
            case H -> hLatch;
            case O -> oLatch;
            case T -> tLatch;
        };

        boolean fired = pressed && !latch;

        switch (key) {
            case ESC -> escLatch = pressed;
            case H -> hLatch = pressed;
            case O -> oLatch = pressed;
            case T -> tLatch = pressed;
        }
        return fired;
    }

    private enum Key {
        ESC,
        H,
        O,
        T
    }
}
