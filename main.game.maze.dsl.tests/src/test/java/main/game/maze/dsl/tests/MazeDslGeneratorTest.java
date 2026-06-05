/*
 * MazeGame DSL - Generator Tests
 * 
 * Tests for verifying code generation works correctly.
 */
package main.game.maze.dsl.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import main.game.maze.dsl.generator.MazeDslGenerator;
import main.game.maze.dsl.mazeDsl.*;

/**
 * Tests for MazeDsl code generation.
 */
public class MazeDslGeneratorTest {

    private ParseHelper<GameConfiguration> parseHelper;

    private MazeDslGenerator generator;

    @BeforeEach
    public void setUp() {
        IInjectorProvider provider = new MazeDslInjectorProvider();
        Injector injector = provider.getInjector();
        parseHelper = injector.getInstance(Key.get(new TypeLiteral<ParseHelper<GameConfiguration>>() {}));
        generator = injector.getInstance(MazeDslGenerator.class);
    }

    @Test
    public void testGenerateFactoryClass() throws Exception {
        GameConfiguration model = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Enemy1 {\n" +
            "        type zombie\n" +
            "        health 100\n" +
            "        threatLevel 25\n" +
            "    }\n" +
            "}\n"
        );
        
        InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
        generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
        
        // Check that factory class was generated
        assertTrue(fsa.getTextFiles().containsKey(
            "DEFAULT_OUTPUTmain/game/maze/generated/TestLevelFactory.java"));
        
        String content = fsa.getTextFiles().get(
            "DEFAULT_OUTPUTmain/game/maze/generated/TestLevelFactory.java").toString();
        
        // Verify content
        assertTrue(content.contains("public class TestLevelFactory"));
        assertTrue(content.contains("createEnemy1"));
        assertTrue(content.contains("setHealth(100)"));
        // threatLevel is a DOUBLE, so 25 becomes 25.0 in the generated code
        assertTrue(content.contains("setThreatLevel(25.0)"));
    }

    @Test
    public void testGenerateXmiInstance() throws Exception {
        GameConfiguration model = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Zombie1 {\n" +
            "        type zombie\n" +
            "        displayName \"Test Zombie\"\n" +
            "    }\n" +
            "}\n"
        );
        
        InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
        generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
        
        // Check that XMI was generated
        assertTrue(fsa.getTextFiles().containsKey(
            "DEFAULT_OUTPUTxmi/testlevel-config.xmi"));
        
        String content = fsa.getTextFiles().get(
            "DEFAULT_OUTPUTxmi/testlevel-config.xmi").toString();
        
        // Verify XMI content
        assertTrue(content.contains("OpponentModel"));
        assertTrue(content.contains("xsi:type=\"opp:Zombie\""));
        assertTrue(content.contains("displayName=\"Test Zombie\""));
    }

    @Test
    public void testGenerateDifficultyXmi() throws Exception {
        GameConfiguration model = parseHelper.parse(
            "game TestLevel {\n" +
            "    difficulty {\n" +
            "        level hard\n" +
            "        maxThreat 100\n" +
            "        speedMultiplier 1.5\n" +
            "        limit zombie max 10\n" +
            "    }\n" +
            "}\n"
        );
        
        InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
        generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
        
        // Check that difficulty XMI was generated
        assertTrue(fsa.getTextFiles().containsKey(
            "DEFAULT_OUTPUTxmi/testlevel-difficulty.xmi"));
        
        String content = fsa.getTextFiles().get(
            "DEFAULT_OUTPUTxmi/testlevel-difficulty.xmi").toString();
        
        // Verify generated content is non-empty
        assertFalse(content.isBlank());
    }

    @Test
    public void testGeneratePatrolMethod() throws Exception {
        GameConfiguration model = parseHelper.parse(
            "game TestLevel {\n" +
            "    patrol GuardPath {\n" +
            "        visionRange 200.0\n" +
            "        path [(0, 0), (100, 50), (200, 100)]\n" +
            "    }\n" +
            "}\n"
        );
        
        InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
        generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
        
        String content = fsa.getTextFiles().get(
            "DEFAULT_OUTPUTmain/game/maze/generated/TestLevelFactory.java").toString();
        
        // Verify patrol method
        assertTrue(content.contains("createGuardPathPatrol"));
        assertTrue(content.contains("PatrolBehavior"));
        assertTrue(content.contains("setBaseVisionRange(200.0)"));
        assertTrue(content.contains("setPosX(0"));
        assertTrue(content.contains("setPosX(100"));
    }

    @Test
    public void testGenerateAllOpponentsMethod() throws Exception {
        GameConfiguration model = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Enemy1 { type zombie }\n" +
            "    opponent Enemy2 { type ghost }\n" +
            "    opponent Enemy3 { type pumpkinbomber }\n" +
            "}\n"
        );
        
        InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
        generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
        
        String content = fsa.getTextFiles().get(
            "DEFAULT_OUTPUTmain/game/maze/generated/TestLevelFactory.java").toString();
        
        // Verify createAllOpponents method
        assertTrue(content.contains("createAllOpponents"));
        assertTrue(content.contains("createEnemy1()"));
        assertTrue(content.contains("createEnemy2()"));
        assertTrue(content.contains("createEnemy3()"));
    }
}


