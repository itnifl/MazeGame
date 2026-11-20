package main.game.maze.game.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.service.DifficultyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PlayerLives: respawn while lives are left, game over on the last one")
class PlayerLivesTest {

    @Test
    @DisplayName("three lives: the first two deaths respawn, the third is game over")
    void losingLives() {
        PlayerLives lives = new PlayerLives(3);
        assertEquals(3, lives.remaining());
        assertTrue(lives.loseLife(), "respawn, 2 left");
        assertTrue(lives.loseLife(), "respawn, 1 left");
        assertFalse(lives.loseLife(), "the last life: game over");
        assertEquals(0, lives.remaining());
        assertFalse(lives.loseLife(), "stays at 0");
        assertEquals(0, lives.remaining());
    }

    @Test
    @DisplayName("an extra life adds one; at least one life to start with")
    void gainingLives() {
        PlayerLives lives = new PlayerLives(1);
        lives.gain();
        assertEquals(2, lives.remaining());
        assertTrue(lives.loseLife());
        assertEquals(1, new PlayerLives(0).remaining(), "never fewer than one life at the start");
        lives.reset(2);
        assertEquals(2, lives.remaining());
    }

    @Test
    @DisplayName("starting lives come from the difficulty model: easy 3, normal 2, hard 1")
    void startingLivesFromDifficulty() {
        DifficultyService service = new DifficultyService();
        assertEquals(3, PlayerLives.startingFor(find(service, "easy")));
        assertEquals(2, PlayerLives.startingFor(find(service, "normal")));
        assertEquals(1, PlayerLives.startingFor(find(service, "hard")));
        assertEquals(1, PlayerLives.startingFor(null), "no difficulty: one life, as before lives existed");
        Difficulty unset = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        assertEquals(1, PlayerLives.startingFor(unset), "the model default is one life");
        assertTrue(PlayerLives.RESPAWN_PROTECTION_SECONDS > 0);
    }

    private static Difficulty find(DifficultyService service, String id) {
        for (Difficulty difficulty : service.list()) {
            String type = difficulty.eClass().getName().toLowerCase(java.util.Locale.ROOT);
            if (type.startsWith(id)) {
                return difficulty;
            }
        }
        throw new AssertionError("no difficulty " + id);
    }
}
