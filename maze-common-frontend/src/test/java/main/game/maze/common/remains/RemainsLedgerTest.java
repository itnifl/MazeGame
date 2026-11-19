package main.game.maze.common.remains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RemainsLedger")
class RemainsLedgerTest {

    /** Equal by value but distinct objects, like two identical spawn records. */
    private record Owner(String id) {
    }

    @Test
    @DisplayName("remains stay until their owner respawns; owners are matched by identity")
    void removedWhenOwnerRespawns() {
        RemainsLedger<Owner, String> ledger = new RemainsLedger<>(10);
        Owner a = new Owner("same");
        Owner b = new Owner("same");
        ledger.put(a, "coal-a");
        ledger.put(b, "blood-b");

        assertEquals("coal-a", ledger.remove(a));
        assertEquals(List.of("blood-b"), ledger.values());
        assertNull(ledger.remove(a));
    }

    @Test
    @DisplayName("a second death of the same owner replaces its old remains")
    void replacesSameOwner() {
        RemainsLedger<Owner, String> ledger = new RemainsLedger<>(10);
        Owner a = new Owner("a");
        ledger.put(a, "first");
        ledger.put(a, "second");
        assertEquals(List.of("second"), ledger.values());
    }

    @Test
    @DisplayName("over the cap the oldest remains are dropped and reported")
    void capDropsOldest() {
        RemainsLedger<Owner, String> ledger = new RemainsLedger<>(2);
        assertNull(ledger.put(new Owner("1"), "r1"));
        assertNull(ledger.put(new Owner("2"), "r2"));
        assertEquals("r1", ledger.put(new Owner("3"), "r3"));
        assertEquals(List.of("r2", "r3"), ledger.values());
        ledger.clear();
        assertEquals(List.of(), ledger.values());
        assertThrows(IllegalArgumentException.class, () -> new RemainsLedger<Owner, String>(0));
    }
}
