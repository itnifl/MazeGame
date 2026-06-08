/*
 * MazeGame DSL - Validation Tests
 * 
 * Tests for verifying validation rules work correctly.
 */
package main.game.maze.dsl.tests;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import main.game.maze.dsl.mazeDsl.*;
import main.game.maze.dsl.validation.MazeDslValidator;

/**
 * Tests for MazeDsl validation rules.
 */
public class MazeDslValidationTest {

    private ParseHelper<GameConfiguration> parseHelper;

    private ValidationTestHelper validationHelper;

    @BeforeEach
    public void setUp() {
        IInjectorProvider provider = new MazeDslInjectorProvider();
        Injector injector = provider.getInjector();
        parseHelper = injector.getInstance(Key.get(new TypeLiteral<ParseHelper<GameConfiguration>>() {}));
        validationHelper = injector.getInstance(ValidationTestHelper.class);
    }

    @Test
    public void testValidThreatLevel() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Enemy1 {\n" +
            "        type zombie\n" +
            "        threatLevel 50\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertNoErrors(result);
    }

    @Test
    public void testThreatLevelExceedsMax() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Enemy1 {\n" +
            "        type zombie\n" +
            "        threatLevel 150\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertError(result, 
            MazeDslPackage.Literals.OPPONENT_CONFIG,
            MazeDslValidator.INVALID_THREAT_LEVEL);
    }

    @Test
    public void testNegativeThreatLevel() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Enemy1 {\n" +
            "        type zombie\n" +
            "        threatLevel -10\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertError(result, 
            MazeDslPackage.Literals.OPPONENT_CONFIG,
            MazeDslValidator.INVALID_THREAT_LEVEL);
    }

    @Test
    public void testInsufficientWaypoints() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    patrol SinglePoint {\n" +
            "        path [(0, 0)]\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertWarning(result, 
            MazeDslPackage.Literals.PATROL_CONFIG,
            MazeDslValidator.INSUFFICIENT_WAYPOINTS);
    }

    @Test
    public void testValidPatrol() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    patrol ValidPath {\n" +
            "        path [(0, 0), (100, 100)]\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertNoWarnings(result, 
            MazeDslPackage.Literals.PATROL_CONFIG,
            MazeDslValidator.INSUFFICIENT_WAYPOINTS);
    }

    @Test
    public void testDuplicateOpponentNames() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Enemy1 {\n" +
            "        type zombie\n" +
            "    }\n" +
            "    opponent Enemy1 {\n" +
            "        type ghost\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertError(result, 
            MazeDslPackage.Literals.OPPONENT_CONFIG,
            MazeDslValidator.DUPLICATE_OPPONENT_NAME);
    }

    @Test
    public void testMismatchedZombieSpecifics() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Ghost1 {\n" +
            "        type ghost\n" +
            "        zombie-stats {\n" +
            "            attackDamage 10\n" +
            "        }\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertWarning(result, 
            MazeDslPackage.Literals.OPPONENT_CONFIG,
            MazeDslValidator.MISMATCHED_SPECIFICS);
    }

    @Test
    public void testMatchingGhostSpecifics() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    opponent Ghost1 {\n" +
            "        type ghost\n" +
            "        ghost-stats {\n" +
            "            visibilityLevel 80\n" +
            "        }\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertNoWarnings(result, 
            MazeDslPackage.Literals.OPPONENT_CONFIG,
            MazeDslValidator.MISMATCHED_SPECIFICS);
    }

    @Test
    public void testNegativeMaxThreat() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    difficulty {\n" +
            "        level normal\n" +
            "        maxThreat -10\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertError(result, 
            MazeDslPackage.Literals.DIFFICULTY_CONFIG,
            MazeDslValidator.INVALID_MAX_THREAT);
    }

    @Test
    public void testNegativeEnemyLimit() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    difficulty {\n" +
            "        level normal\n" +
            "        limit zombie max -5\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertError(result, 
            MazeDslPackage.Literals.ENEMY_LIMIT,
            MazeDslValidator.INVALID_MAX_COUNT);
    }

    @Test
    public void testWaypointsInsideZoneDoNotWarn() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    patrol GuardRoute {\n" +
            "        zone {\n" +
            "            topLeft (0, 0)\n" +
            "            width 100\n" +
            "            height 100\n" +
            "        }\n" +
            "        path [(10, 10), (90, 90)]\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertNoWarnings(result,
            MazeDslPackage.Literals.PATROL_CONFIG,
            MazeDslValidator.WAYPOINT_OUTSIDE_ZONE);
    }

    @Test
    public void testWaypointOutsideZoneWarns() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game TestLevel {\n" +
            "    patrol GuardRoute {\n" +
            "        zone {\n" +
            "            topLeft (0, 0)\n" +
            "            width 100\n" +
            "            height 100\n" +
            "        }\n" +
            "        path [(10, 10), (150, 90)]\n" +
            "    }\n" +
            "}\n"
        );
        validationHelper.assertWarning(result,
            MazeDslPackage.Literals.PATROL_CONFIG,
            MazeDslValidator.WAYPOINT_OUTSIDE_ZONE);
    }
}
