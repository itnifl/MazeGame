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

    private static String levelWithSecondRing(String secondRing) {
        return "game G { level 3 \"Three\" { mines { count 3 ringRadius 62.0 " + secondRing
            + " triggerRadius 22.0 pulse 0.9 blastRadius 150.0 blastDamage 15 playerDamage 20 holeRadius 30.0 } } }";
    }

    @Test
    public void testNegativeSecondRingIsAnError() throws Exception {
        GameConfiguration result = parseHelper.parse(levelWithSecondRing("secondRing -1.0"));
        validationHelper.assertError(result, MazeDslPackage.Literals.MINE_RING_CONFIG,
            MazeDslValidator.INVALID_SECOND_RING);
    }

    @Test
    public void testSecondRingInsideTheFirstIsAnError() throws Exception {
        GameConfiguration result = parseHelper.parse(levelWithSecondRing("secondRing 40.0"));
        validationHelper.assertError(result, MazeDslPackage.Literals.MINE_RING_CONFIG,
            MazeDslValidator.INVALID_SECOND_RING);
    }

    @Test
    public void testSecondRingOutsideTheFirstIsValid() throws Exception {
        validationHelper.assertNoIssues(parseHelper.parse(levelWithSecondRing("secondRing 118.0")));
        validationHelper.assertNoIssues(parseHelper.parse(levelWithSecondRing("")));
    }

    @Test
    public void testMaterialsNamedLikeNewKeywordsStillParse() throws Exception {
        GameConfiguration result = parseHelper.parse(
            "game G { level 2 \"Two\" { ring \"R\" materials material, builders } }");
        validationHelper.assertNoErrors(result);
        org.junit.jupiter.api.Assertions.assertEquals("material", ((LevelConfig) result.getLevels().get(0)).getZones().get(0).getMaterialIds().get(0));
    }

    private static String levelWithLava(String values) {
        return "game G { level 6 \"Six\" { lava { streams 2 width 46.0 bridges 2 bridgeLength 56.0 " + values + " } } }";
    }

    @Test
    public void testLavaValuesOutOfRangeAreErrors() throws Exception {
        for (String bad : new String[] {"glow 1.5", "crust 1.2", "wavelength 0.0", "flowSpeed -1.0", "meander -5.0"}) {
            validationHelper.assertError(parseHelper.parse(levelWithLava(bad)), MazeDslPackage.Literals.LAVA_CONFIG,
                MazeDslValidator.INVALID_LAVA);
        }
        validationHelper.assertNoErrors(parseHelper.parse(levelWithLava("meander 95.0 wavelength 340.0 flowSpeed 34.0 glow 0.65 crust 0.45")));
        validationHelper.assertNoErrors(parseHelper.parse(levelWithLava("")));
    }

    @Test
    public void testAGiftNeedsAShot() throws Exception {
        validationHelper.assertError(parseHelper.parse("game G { level 6 \"Six\" { lastLifeWeapon PULSE_CANNON shots 0 } }"),
            MazeDslPackage.Literals.LAST_LIFE_GIFT_CONFIG, MazeDslValidator.INVALID_LAVA);
        validationHelper.assertNoErrors(parseHelper.parse("game G { level 6 \"Six\" { lastLifeWeapon PULSE_CANNON shots 6 } }"));
    }

    private static String levelWithUfoRaid(String values) {
        return "game G { level 7 \"Seven\" { ufoRaid { " + values + " } } }";
    }

    private static final String UFO_RAID_OK = "trigger EXPENSIVE ufos 2 duration 10.0 ufoSpeed 150.0 beamEvery 1.5 charge 0.8 "
            + "beamSeconds 1.2 beamRadius 38.0 aimMin 50.0 aimMax 170.0 damageFraction 0.5 soldiers 10 soldierHealth 60 "
            + "soldierDamage 4 soldierSight 320.0 soldierSeconds 45.0 ufoSprite \"/u.png\" beamSprite \"/b.png\"";

    @Test
    public void testUfoRaidParsesAndValidates() throws Exception {
        GameConfiguration game = parseHelper.parse(levelWithUfoRaid(UFO_RAID_OK));
        validationHelper.assertNoErrors(game);
        UfoRaidConfig raid = game.getLevels().get(0).getUfoRaid();
        org.junit.jupiter.api.Assertions.assertEquals(2, raid.getUfos());
        org.junit.jupiter.api.Assertions.assertEquals(0.5, raid.getDamageFraction(), 1e-9);
        org.junit.jupiter.api.Assertions.assertEquals(10, raid.getSoldiers());
        org.junit.jupiter.api.Assertions.assertEquals("EXPENSIVE", raid.getTriggerTier());
    }

    @Test
    public void testUfoRaidValuesOutOfRangeAreErrors() throws Exception {
        String[][] bad = {
            {"ufos 2", "ufos 0"},
            {"damageFraction 0.5", "damageFraction 1.5"},
            {"aimMin 50.0 aimMax 170.0", "aimMin 200.0 aimMax 100.0"},
            {"trigger EXPENSIVE", "trigger LEGENDARY"},
            {"duration 10.0", "duration 0.0"},
        };
        for (String[] change : bad) {
            validationHelper.assertError(parseHelper.parse(levelWithUfoRaid(UFO_RAID_OK.replace(change[0], change[1]))),
                MazeDslPackage.Literals.UFO_RAID_CONFIG, MazeDslValidator.INVALID_UFO_RAID);
        }
    }
}
