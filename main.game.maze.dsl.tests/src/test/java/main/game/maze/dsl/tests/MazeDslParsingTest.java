/*
 * MazeGame DSL - Parsing Tests
 * 
 * Tests for verifying the grammar parses valid DSL files correctly.
 */
package main.game.maze.dsl.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

import main.game.maze.dsl.mazeDsl.*;

/**
 * Tests for parsing MazeDsl files.
 */
@ExtendWith(InjectionExtension.class)
@InjectWith(MazeDslInjectorProvider.class)
public class MazeDslParsingTest {

    @Inject
    private ParseHelper<GameConfiguration> parseHelper;

    @Test
    public void testParseMinimalGame() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestGame {\n" +
            "}\n"
        );
        assertNotNull(result);
        assertEquals("TestGame", result.getName());
        assertNotNull(result.eResource().getErrors());
        assertTrue(result.eResource().getErrors().isEmpty(), 
            "Unexpected errors: " + result.eResource().getErrors());
    }

    @Test
    public void testParseGameWithDifficulty() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    difficulty {\n" +
            "        level normal\n" +
            "        maxThreat 50\n" +
            "    }\n" +
            "}\n"
        );
        assertNotNull(result);
        assertNotNull(result.getDifficulty());
        assertEquals(DifficultyLevel.NORMAL, result.getDifficulty().getLevel());
        assertEquals(50, result.getDifficulty().getMaxThreat());
    }

    @Test
    public void testParseOpponent() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Enemy1 {\n" +
            "        type zombie\n" +
            "        health 100\n" +
            "        speed 1.5\n" +
            "        threatLevel 25\n" +
            "        behavior aggressive\n" +
            "    }\n" +
            "}\n"
        );
        assertNotNull(result);
        assertEquals(1, result.getOpponents().size());
        
        OpponentConfig opponent = result.getOpponents().get(0);
        assertEquals("Enemy1", opponent.getName());
        assertEquals(CharacterTypeEnum.ZOMBIE, opponent.getType());
        assertEquals(100, opponent.getHealth());
        assertEquals(25.0, opponent.getThreatLevel(), 0.01);
    }

    @Test
    public void testParsePatrol() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    patrol GuardPath {\n" +
            "        visionRange 150.0\n" +
            "        path [\n" +
            "            (0, 0),\n" +
            "            (100, 0),\n" +
            "            (100, 100),\n" +
            "            (0, 100)\n" +
            "        ]\n" +
            "    }\n" +
            "}\n"
        );
        assertNotNull(result);
        assertEquals(1, result.getPatrols().size());
        
        PatrolConfig patrol = result.getPatrols().get(0);
        assertEquals("GuardPath", patrol.getName());
        assertEquals(4, patrol.getWaypoints().size());
    }

    @Test
    public void testParseWaypointWithWaitTime() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    patrol WaitPath {\n" +
            "        path [\n" +
            "            (0, 0) : 1000 ms,\n" +
            "            (100, 100) : 2000 ms\n" +
            "        ]\n" +
            "    }\n" +
            "}\n"
        );
        assertNotNull(result);
        
        PatrolConfig patrol = result.getPatrols().get(0);
        assertEquals(1000, patrol.getWaypoints().get(0).getWaitTime());
        assertEquals(2000, patrol.getWaypoints().get(1).getWaitTime());
    }

    @Test
    public void testParseZombieSpecifics() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Zombie1 {\n" +
            "        type zombie\n" +
            "        zombie-stats {\n" +
            "            attackDamage 15\n" +
            "            infectionLevel 2\n" +
            "            resurrectionTime 5000\n" +
            "        }\n" +
            "    }\n" +
            "}\n"
        );
        assertNotNull(result);
        
        OpponentConfig opponent = result.getOpponents().get(0);
        assertTrue(opponent.getCharacterSpecifics() instanceof ZombieSpecifics);
        
        ZombieSpecifics zombieStats = (ZombieSpecifics) opponent.getCharacterSpecifics();
        assertEquals(15, zombieStats.getAttackDamage());
        assertEquals(2, zombieStats.getInfectionLevel());
    }

    @Test
    public void testParseGhostSpecifics() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Ghost1 {\n" +
            "        type ghost\n" +
            "        ghost-stats {\n" +
            "            visibilityLevel 75\n" +
            "            nonTangibilityEnergy 150.0\n" +
            "        }\n" +
            "    }\n" +
            "}\n"
        );
        assertNotNull(result);
        
        OpponentConfig opponent = result.getOpponents().get(0);
        assertTrue(opponent.getCharacterSpecifics() instanceof GhostSpecifics);
    }

    @Test
    public void testParseLootTable() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    loot-table BasicLoot {\n" +
            "        capacity 10\n" +
            "        item HealthPack {\n" +
            "            type food\n" +
            "            value 25\n" +
            "            weight 1\n" +
            "        }\n" +
            "        item Bomb1 {\n" +
            "            type bomb\n" +
            "            value 50\n" +
            "        }\n" +
            "    }\n" +
            "}\n"
        );
        assertNotNull(result);
        assertEquals(1, result.getLootTables().size());
        
        LootTableConfig lootTable = result.getLootTables().get(0);
        assertEquals("BasicLoot", lootTable.getName());
        assertEquals(2, lootTable.getItems().size());
    }

    @Test
    public void testParseCompleteGame() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TutorialLevel {\n" +
            "    difficulty {\n" +
            "        level easy\n" +
            "        maxThreat 30\n" +
            "        limit zombie max 3\n" +
            "        limit ghost max 1\n" +
            "    }\n" +
            "\n" +
            "    patrol MainPath {\n" +
            "        visionRange 100.0\n" +
            "        path [(0, 0), (200, 0), (200, 200)]\n" +
            "    }\n" +
            "\n" +
            "    opponent Guard1 {\n" +
            "        type zombie\n" +
            "        displayName \"Tutorial Guard\"\n" +
            "        health 50\n" +
            "        threatLevel 10\n" +
            "        behavior patrol\n" +
            "        patrol MainPath\n" +
            "    }\n" +
            "}\n"
        );
        
        assertNotNull(result);
        assertEquals("TutorialLevel", result.getName());
        assertNotNull(result.getDifficulty());
        assertEquals(1, result.getPatrols().size());
        assertEquals(1, result.getOpponents().size());
        
        // Verify cross-reference
        OpponentConfig opponent = result.getOpponents().get(0);
        assertNotNull(opponent.getPatrolRef());
        assertEquals("MainPath", opponent.getPatrolRef().getName());
    }
}
