package main.game.maze.game.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RetiredShooters")
class RetiredShootersTest {

    @Test
    @DisplayName("a dead shooter is kept only while its shots are still in the air")
    void keptUntilShotsLand() {
        Set<String> airborne = new HashSet<>(Set.of("bomber"));
        RetiredShooters<String> retired = new RetiredShooters<>(airborne::contains);

        retired.retire("bomber");
        retired.retire("zombie");           // nothing in the air: not kept
        assertEquals(List.of("bomber"), retired.active());

        airborne.remove("bomber");          // its last pumpkin landed
        assertEquals(List.of(), retired.active());
    }

    @Test
    @DisplayName("retiring twice keeps one entry; clear drops all (new level)")
    void noDuplicatesAndClear() {
        RetiredShooters<String> retired = new RetiredShooters<>(s -> true);
        retired.retire("bomber");
        retired.retire("bomber");
        assertEquals(List.of("bomber"), retired.active());
        retired.clear();
        assertEquals(List.of(), retired.active());
    }
}
