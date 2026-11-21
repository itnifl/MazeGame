package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.interfaces.IDeathSubscriber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JavaFX player: lava kills at once, whatever the shield says")
class PlayerLavaDeathTest {

    @Test
    @DisplayName("the shield blocks ordinary damage but not lava; the death is announced once")
    void lavaKillsThroughTheShield() {
        PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        player.setHitPoints(100);
        player.setShield(() -> true);
        AtomicInteger deaths = new AtomicInteger();
        player.addDeathNotificationSubscriber((IDeathSubscriber) who -> deaths.incrementAndGet());

        player.subtractHitPoints(40);
        assertEquals(100, player.getHitPoints(), "the shield blocks it");
        player.killOutright();
        assertTrue(player.getHitPoints() <= 0, "the lava does not care");
        assertEquals(1, deaths.get());
        player.killOutright();
        assertEquals(1, deaths.get(), "already dead: not killed twice");
    }
}
