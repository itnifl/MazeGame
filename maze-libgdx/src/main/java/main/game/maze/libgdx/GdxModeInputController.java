package main.game.maze.libgdx;

/**
 * Edge-trigger key latch helper for mode-level keyboard actions.
 */
final class GdxModeInputController {

    private boolean escLatch;
    private boolean hLatch;
    private boolean oLatch;
    private boolean tLatch;

    boolean consumeEsc(boolean escPressed) {
        return consumeEdge(escPressed, Key.ESC);
    }

    boolean consumeH(boolean hPressed) {
        return consumeEdge(hPressed, Key.H);
    }

    boolean consumeO(boolean oPressed) {
        return consumeEdge(oPressed, Key.O);
    }

    boolean consumeT(boolean tPressed) {
        return consumeEdge(tPressed, Key.T);
    }

    void reset() {
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
