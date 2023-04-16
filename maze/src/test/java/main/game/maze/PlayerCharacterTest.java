package main.game.maze;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import main.game.maze.characters.PlayerCharacter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerCharacterTest {

    private Node mockCharacterGraphics;
    private ProgressBar mockHpBar;
    private PlayerCharacter player;

    @BeforeEach
    void setUp() {
        player = new PlayerCharacter(mockCharacterGraphics, 0, 0, mockHpBar);
    }

    @Test
    void testGetHitPoints() {
        assertEquals(100, player.getHitPoints());
    }

    @Test
    void testSetHitPoints() {
        player.setHitPoints(50);
        assertEquals(50, player.getHitPoints());
    }

    @Test
    void testSubtractHitPoints() {
        player.subtractHitPoints(20);
        assertEquals(80, player.getHitPoints());
    }

    @Test
    void testAddHitPoints() {
        player.addHitPoints(20);
        assertEquals(120, player.getHitPoints());
        ;
    }

}