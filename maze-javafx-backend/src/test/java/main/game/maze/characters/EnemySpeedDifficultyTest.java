package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import javafx.scene.shape.Rectangle;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import main.game.maze.mazeworld.Point2D;

public class EnemySpeedDifficultyTest {

    @Test
    public void testZombieSpeedWithMultiplier() {
        // Create a zombie model with base speed 3
        Zombie zombieModel = OpponentsFactory.eINSTANCE.createZombie();
        zombieModel.setSpeed(3.0);
        
        // 1.15 multiplier for Hard difficulty -> 3.45
        double multiplier = 1.15;
        double expectedSpeed = 3.0 * multiplier;
        assertEquals(3.45, expectedSpeed, 0.001);
        
        zombieModel.setSpeed(expectedSpeed);
        ZombieCharacter fastZombie = new ZombieCharacter(null, 0, 0, zombieModel);
        
        // Manually set maxX/maxY in the character
        try {
            java.lang.reflect.Field maxXField = Character.class.getDeclaredField("maxX");
            maxXField.setAccessible(true);
            maxXField.set(fastZombie, 1000);
            
            java.lang.reflect.Field maxYField = Character.class.getDeclaredField("maxY");
            maxYField.setAccessible(true);
            maxYField.set(fastZombie, 1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        fastZombie.teleportTo(10, 10);
        
        // We use moveCharacterRight directly because moveRight resets the direction vector
        // based on (0, -speed) which is not what we want to test here.
        // Actually moveRight(speed, force) calls moveCharacterRight(speed, force).
        // The issue is likely that characterGraphics and characterView are both null, 
        // so it returns false.
        
        // Create a dummy view
        Rectangle rect = new Rectangle();
        fastZombie.setCharacterGraphics(rect);
        
        boolean success = fastZombie.moveRight(expectedSpeed, true);
        assertTrue(success, "moveRight should return true");
        
        assertEquals(13.45, fastZombie.getCharacterPosition().getX(), 0.001, 
            "Character should have moved by the fractional speed amount");
    }
}
