package main.game.maze.game.status;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatusMessageBusTest {

    @Test
    void publishedMessageExpiresAfterTick() {
        StatusMessageBus bus = new StatusMessageBus();

        bus.publish("Hello", 1f);
        bus.tick(0.4f);

        assertTrue(bus.hasMessage());
        assertEquals("Hello", bus.currentMessage());

        bus.tick(0.7f);

        assertFalse(bus.hasMessage());
        assertEquals("", bus.currentMessage());
    }
}
