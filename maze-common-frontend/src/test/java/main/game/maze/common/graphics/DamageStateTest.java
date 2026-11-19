package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("DamageState")
class DamageStateTest {

    @Test
    @DisplayName("labels match lower-case enum names")
    void labelsMatchLowerCaseEnumNames() {
        assertEquals("healthy", DamageState.HEALTHY.label());
        assertEquals("damaged", DamageState.DAMAGED.label());
        assertEquals("bleeding", DamageState.BLEEDING.label());
        assertEquals("tired", DamageState.TIRED.label());
    }

    @ParameterizedTest(name = "HP {0} / {1} -> {2}")
    @CsvSource({
        "100, 100, HEALTHY",
        "76,  100, HEALTHY",
        "75,  100, DAMAGED",
        "51,  100, DAMAGED",
        "50,  100, BLEEDING",
        "26,  100, BLEEDING",
        "25,  100, TIRED",
        "1,   100, TIRED",
        "0,   100, TIRED",
        "-5,  100, TIRED"
    })
    @DisplayName("threshold boundaries with maxHp = 100")
    void thresholdBoundariesWith100MaxHp(int currentHp, int maxHp, DamageState expected) {
        assertEquals(expected, DamageState.of(currentHp, maxHp));
    }

    @Test
    @DisplayName("maxHp <= 0 returns HEALTHY (edge case)")
    void maxHpZeroOrNegativeReturnsHealthy() {
        assertEquals(DamageState.HEALTHY, DamageState.of(0, 0));
        assertEquals(DamageState.HEALTHY, DamageState.of(10, 0));
        assertEquals(DamageState.HEALTHY, DamageState.of(-5, 0));
        assertEquals(DamageState.HEALTHY, DamageState.of(10, -10));
    }

    @ParameterizedTest(name = "Scaled HP {0} / {1} -> {2}")
    @CsvSource({
        "40, 40, HEALTHY",
        "31, 40, HEALTHY",
        "30, 40, DAMAGED",
        "21, 40, DAMAGED",
        "20, 40, BLEEDING",
        "11, 40, BLEEDING",
        "10, 40, TIRED",
        "1,  40, TIRED",
        "0,  40, TIRED"
    })
    @DisplayName("threshold boundaries with non-100 maxHp (e.g. Stone wall / elite HP = 40)")
    void thresholdBoundariesWithScaledMaxHp(int currentHp, int maxHp, DamageState expected) {
        assertEquals(expected, DamageState.of(currentHp, maxHp));
    }
}
