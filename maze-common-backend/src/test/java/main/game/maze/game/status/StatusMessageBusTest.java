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

    @Test
    void bannerFadesInHoldsAndFadesOut() {
        StatusMessageBus bus = new StatusMessageBus();
        assertFalse(bus.hasBanner());
        assertEquals(0f, bus.bannerAlpha(), 1e-6f);

        bus.showBanner("Teleported", 1.6f);
        assertTrue(bus.hasBanner());
        assertEquals("Teleported", bus.bannerText());
        assertEquals(0f, bus.bannerAlpha(), 1e-6f, "starts transparent");

        bus.tick(0.075f);
        assertTrue(bus.bannerAlpha() > 0.3f && bus.bannerAlpha() < 0.7f, "fading in: " + bus.bannerAlpha());
        bus.tick(0.5f);
        assertEquals(1f, bus.bannerAlpha(), 1e-6f, "fully visible in the middle");
        bus.tick(0.95f);
        assertTrue(bus.bannerAlpha() < 0.7f && bus.bannerAlpha() > 0f, "fading out: " + bus.bannerAlpha());
        bus.tick(0.2f);
        assertFalse(bus.hasBanner(), "gone after its time");
        assertEquals("", bus.bannerText());
    }

    @Test
    void bannerAndStatusLineAreIndependent() {
        StatusMessageBus bus = new StatusMessageBus();
        bus.publish("Status", 5f);
        bus.showBanner("Teleported", 0.3f);
        bus.tick(0.4f);
        assertFalse(bus.hasBanner());
        assertTrue(bus.hasMessage(), "the status line is still there");
        bus.showBanner("", 1f);
        assertFalse(bus.hasBanner(), "an empty banner shows nothing");
    }
}
