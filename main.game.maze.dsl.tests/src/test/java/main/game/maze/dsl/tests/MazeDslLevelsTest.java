/*
 * MazeGame DSL - Levels Tests
 *
 * The levels are written in the DSL and generated into the XMI the game loads; these tests keep the two in step.
 */
package main.game.maze.dsl.tests;

import static main.game.maze.dsl.tests.XmiModelSupport.compare;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import main.game.maze.dsl.generator.MazeDslGenerator;
import main.game.maze.dsl.mazeDsl.*;

public class MazeDslLevelsTest {

    private static final String LEVELS_DIR = "maze-common-backend/src/main/resources/xmi/levels/";

    private ParseHelper<GameConfiguration> parseHelper;
    private org.eclipse.xtext.testing.validation.ValidationTestHelper validationHelper;
    private ISerializer serializer;

    @BeforeEach
    public void setUp() {
        IInjectorProvider provider = new MazeDslInjectorProvider();
        Injector injector = provider.getInjector();
        parseHelper = injector.getInstance(Key.get(new TypeLiteral<ParseHelper<GameConfiguration>>() {}));
        validationHelper = injector.getInstance(org.eclipse.xtext.testing.validation.ValidationTestHelper.class);
        serializer = injector.getInstance(ISerializer.class);
    }

    private static Path repoFile(String relative) {
        return XmiModelSupport.repoFile(relative);
    }

    private static Resource loadXmi(EPackage levels, String xmi, String name) throws IOException {
        return XmiModelSupport.loadXmi(levels, xmi, name);
    }

    private static EPackage levelsMetamodel() {
        return XmiModelSupport.metamodel(LEVELS_DIR + "levels.ecore");
    }

    private GameConfiguration parseShippedLevels() throws Exception {
        String source = Files.readString(repoFile(LEVELS_DIR + "levels.mazegame"), StandardCharsets.UTF_8);
        GameConfiguration game = parseHelper.parse(source);
        assertNotNull(game);
        assertTrue(game.eResource().getErrors().isEmpty(), "the levels source parses: " + game.eResource().getErrors());
        return game;
    }

    @Test
    public void shippedLevelsSourceGeneratesTheShippedXmi() throws Exception {
        EPackage levels = levelsMetamodel();
        GameConfiguration source = parseShippedLevels();
        // a bad value in the DSL is found here, before levels.xmi is written (regenerating must never leave a file the game cannot load)
        validationHelper.assertNoErrors(source);
        String generated = MazeDslGenerator.levelsXmi(source);
        if ("true".equalsIgnoreCase(System.getenv("MAZE_LEVELS_REGENERATE"))) {
            // MAZE_LEVELS_REGENERATE=true mvn -o test -pl main.game.maze.dsl.tests rewrites levels.xmi from levels.mazegame
            Files.writeString(repoFile(LEVELS_DIR + "levels.xmi"), generated, StandardCharsets.UTF_8);
        }
        Resource fromDsl = loadXmi(levels, generated, "generated");
        Resource shipped = loadXmi(levels, Files.readString(repoFile(LEVELS_DIR + "levels.xmi"), StandardCharsets.UTF_8), "shipped");

        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(fromDsl.getContents().get(0));
        assertTrue(diagnostic.getSeverity() < Diagnostic.ERROR, "the generated XMI is valid against levels.ecore: " + diagnostic.getMessage());

        List<String> differences = new ArrayList<>();
        compare(shipped.getContents().get(0), fromDsl.getContents().get(0), "levels", differences);
        assertTrue(differences.isEmpty(), "levels.xmi is out of step with levels.mazegame (regenerate it with "
                + "MazeDslGenerator.levelsXmi): " + differences);
    }

    @Test
    public void aLevelBuiltOnAnotherAndValuesLeftOutStayUnset() throws Exception {
        GameConfiguration game = parseHelper.parse(
            "game Small {\n"
            + "  level 1 \"One\" { }\n"
            + "  level 2 \"Two\" {\n"
            + "    basedOn 1\n"
            + "    wallMaterials false\n"
            + "    cascadeDepth 0\n"
            + "    ring \"Edge\" share 0.5 materials STEEL_BASIC\n"
            + "  }\n"
            + "}\n");
        assertTrue(game.eResource().getErrors().isEmpty(), "parses: " + game.eResource().getErrors());
        String xmi = MazeDslGenerator.levelsXmi(game);

        assertTrue(xmi.contains("baseLevel=\"1\""), xmi);
        assertTrue(xmi.contains("maxCascadeDepth=\"0\""), "an explicit 0 is written, so it overrides the base: " + xmi);
        assertTrue(xmi.contains("showWallMaterials=\"false\""), "an explicit false is written: " + xmi);
        assertTrue(xmi.contains("breakableShare=\"0.5\""), xmi);
        String levelOne = xmi.substring(xmi.indexOf("<levels number=\"1\""), xmi.indexOf("</levels>", xmi.indexOf("<levels number=\"1\"")) + 1);
        assertTrue(!levelOne.contains("threatCapacityFactor") && !levelOne.contains("maxCascadeDepth"),
                "what level 1 leaves out is not written: " + levelOne);
    }

    @Test
    public void generatedXmiEscapesText() throws Exception {
        GameConfiguration game = parseHelper.parse("game Quotes { level 1 \"A & B <\\\"C\\\">\" { } }\n");
        String xmi = MazeDslGenerator.levelsXmi(game);
        assertTrue(xmi.contains("A &amp; B &lt;&quot;C&quot;&gt;"), xmi);
        assertEquals(1, game.getLevels().size());
    }

    @Test
    public void anOmittedValueAndAnExplicitDefaultAreDifferent() throws Exception {
        EPackage levels = levelsMetamodel();
        String omitted = MazeDslGenerator.levelsXmi(parseHelper.parse(
            "game A { level 1 \"One\" { } level 2 \"Two\" { basedOn 1 } }"));
        String explicit = MazeDslGenerator.levelsXmi(parseHelper.parse(
            "game A { level 1 \"One\" { } level 2 \"Two\" { basedOn 1 wallMaterials false } }"));

        List<String> differences = new ArrayList<>();
        compare(loadXmi(levels, omitted, "omitted").getContents().get(0),
                loadXmi(levels, explicit, "explicit").getContents().get(0), "levels", differences);

        assertTrue(differences.stream().anyMatch(d -> d.contains("showWallMaterials") && d.contains("set false vs set true")),
                "an explicit default must differ from an omitted value: " + differences);
    }

    @Test
    public void wordsTheLevelSectionAddedAreStillValidNames() throws Exception {
        GameConfiguration game = parseHelper.parse(
            "game gatling {\n"
            + "  opponent mines { type zombie patrol teleport loot ring }\n"
            + "  opponent gatling { type ghost }\n"
            + "  patrol teleport { path [(1, 2)] }\n"
            + "  loot-table ring { item sprite { type food value 1 } }\n"
            + "}\n");
        assertTrue(game.eResource().getErrors().isEmpty(), "existing names keep parsing: " + game.eResource().getErrors());
        assertEquals("gatling", game.getName());
        assertEquals("mines", game.getOpponents().get(0).getName());
        assertNotNull(game.getOpponents().get(0).getPatrolRef(), "a reference by a new keyword resolves");
        assertEquals("teleport", game.getOpponents().get(0).getPatrolRef().getName());
        assertEquals("ring", game.getOpponents().get(0).getLootRef().getName());
    }

    @Test
    public void dropsStillMeansGatlingInTheSergeantBlock() throws Exception {
        GameConfiguration game = parseHelper.parse(
            "game G { level 2 \"Two\" { sergeant \"S\" { health 1 sightRange 1.0 rounds 1 roundDamage 1 roundInterval 0.1 "
            + "burstCooldown 1.0 sprite \"/s.png\" drops gatling postDistance 1.0 } } }\n");
        assertTrue(game.eResource().getErrors().isEmpty(), game.eResource().getErrors().toString());
        assertEquals("GATLING_GUN", game.getLevels().get(0).getSergeant().getDropWeapon().name());
    }

    @Test
    public void smallDecimalsSurviveSerializingWithoutTheOriginalText() throws Exception {
        // a model built in memory has no text nodes, so the serializer writes the numbers itself
        MazeDslFactory factory = MazeDslFactory.eINSTANCE;
        GameConfiguration game = factory.createGameConfiguration();
        game.setName("RoundTrip");
        LevelConfig level = factory.createLevelConfig();
        level.setNumber(1);
        level.setName("One");
        level.setThreatCapacityFactor(0.0001);
        LevelZone zone = factory.createLevelZone();
        zone.setDisplayName("Z");
        zone.setBreakableShare(0.00005);
        zone.getMaterialIds().add("STEEL_BASIC");
        level.getZones().add(zone);
        SergeantConfig sergeant = factory.createSergeantConfig();
        sergeant.setDisplayName("S");
        sergeant.setHealth(1);
        sergeant.setSightRange(0.0001);
        sergeant.setRoundsPerBurst(1);
        sergeant.setRoundDamage(1);
        sergeant.setRoundIntervalSeconds(0.00001);
        sergeant.setBurstCooldownSeconds(12345678901.0);
        sergeant.setSprite("/s.png");
        sergeant.setDropWeapon(DropWeaponEnum.GATLING_GUN);
        sergeant.setPostDistanceFromHeart(0.0002);
        level.setSergeant(sergeant);
        game.getLevels().add(level);
        new XtextResourceSet().createResource(URI.createURI("mem:/roundtrip.mazegame")).getContents().add(game);

        String text = serializer.serialize(game);
        assertTrue(!text.contains("E-") && !text.contains("E1"), "plain decimal notation: " + text);

        GameConfiguration again = parseHelper.parse(text);
        assertTrue(again.eResource().getErrors().isEmpty(), "the written text parses again: " + again.eResource().getErrors() + "\n" + text);
        LevelConfig back = again.getLevels().get(0);
        assertEquals(0.0001, back.getThreatCapacityFactor(), 1e-12);
        assertEquals(0.00005, back.getZones().get(0).getBreakableShare(), 1e-12);
        assertEquals(0.0001, back.getSergeant().getSightRange(), 1e-12);
        assertEquals(0.00001, back.getSergeant().getRoundIntervalSeconds(), 1e-12);
        assertEquals(12345678901.0, back.getSergeant().getBurstCooldownSeconds(), 1e-3);
        assertEquals(0.0002, back.getSergeant().getPostDistanceFromHeart(), 1e-12);
    }
}
