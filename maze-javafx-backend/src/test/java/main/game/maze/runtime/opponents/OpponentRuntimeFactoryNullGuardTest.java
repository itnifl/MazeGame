package main.game.maze.runtime.opponents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link OpponentRuntimeFactory} null-guard paths that are safe to
 * exercise without OCL bootstrap or JavaFX toolkit.
 *
 * <p>The OCL ecore delegate jars are absent from the test classpath so the full
 * spawn pipeline cannot be exercised here.  These tests cover only the early-return
 * guards that fire before any EMF/OCL code is reached.</p>
 */
class OpponentRuntimeFactoryNullGuardTest {

    /** Null registrar → warning logged, returns immediately without touching OCL or XMI. */
    @Test
    void instantiateFromModel_nullRegistrar_doesNotThrow() {
        assertDoesNotThrow(() -> OpponentRuntimeFactory.instantiateFromModel(null),
                "null registrar must be rejected silently before reaching OCL/XMI code");
    }

    /** The two-arg overload with null registrar must also return early. */
    @Test
    void instantiateFromModel_nullRegistrarWithOverride_doesNotThrow() {
        assertDoesNotThrow(() -> OpponentRuntimeFactory.instantiateFromModel(null, null),
                "null registrar (two-arg) must be rejected silently");
    }
}
