/*
 * MazeGame DSL - Menu Theme Tests
 *
 * The look of the screens between levels is written in the DSL (menutheme.mazegame) and rendered with a FreeMarker
 * template into the XMI both frontends load (menutheme.xmi); these tests keep the two in step and check the theme rules
 * of the validator.
 */
package main.game.maze.dsl.tests;

import static main.game.maze.dsl.tests.XmiModelSupport.compare;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import main.game.maze.dsl.generator.MazeDslGenerator;
import main.game.maze.dsl.mazeDsl.GameConfiguration;
import main.game.maze.dsl.mazeDsl.MazeDslPackage;
import main.game.maze.dsl.validation.MazeDslValidator;

public class MazeDslThemeTest {

    private static final String THEME_DIR = "maze-common-backend/src/main/resources/xmi/theme/";

    private ParseHelper<GameConfiguration> parseHelper;
    private ValidationTestHelper validationHelper;

    @BeforeEach
    public void setUp() {
        IInjectorProvider provider = new MazeDslInjectorProvider();
        Injector injector = provider.getInjector();
        parseHelper = injector.getInstance(Key.get(new TypeLiteral<ParseHelper<GameConfiguration>>() {}));
        validationHelper = injector.getInstance(ValidationTestHelper.class);
    }

    private GameConfiguration parse(String text) throws Exception {
        GameConfiguration game = parseHelper.parse(text);
        assertNotNull(game);
        assertTrue(game.eResource().getErrors().isEmpty(), "parses: " + game.eResource().getErrors());
        return game;
    }

    @Test
    public void shippedThemeSourceGeneratesTheShippedXmi() throws Exception {
        EPackage themeModel = XmiModelSupport.metamodel(THEME_DIR + "menutheme.ecore");
        GameConfiguration source = parse(Files.readString(XmiModelSupport.repoFile(THEME_DIR + "menutheme.mazegame"),
                StandardCharsets.UTF_8));
        // a bad value in the DSL is found here, before menutheme.xmi is written
        validationHelper.assertNoErrors(source);
        String generated = MazeDslGenerator.menuThemeXmi(source);
        if ("true".equalsIgnoreCase(System.getenv("MAZE_THEME_REGENERATE"))) {
            // MAZE_THEME_REGENERATE=true mvn -o verify -pl main.game.maze.dsl.tests rewrites menutheme.xmi
            Files.writeString(XmiModelSupport.repoFile(THEME_DIR + "menutheme.xmi"), generated, StandardCharsets.UTF_8);
        }
        Resource fromDsl = XmiModelSupport.loadXmi(themeModel, generated, "generated");
        Resource shipped = XmiModelSupport.loadXmi(themeModel,
                Files.readString(XmiModelSupport.repoFile(THEME_DIR + "menutheme.xmi"), StandardCharsets.UTF_8), "shipped");

        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(fromDsl.getContents().get(0));
        assertTrue(diagnostic.getSeverity() < Diagnostic.ERROR,
                "the generated XMI is valid against menutheme.ecore: " + diagnostic.getMessage());

        List<String> differences = new ArrayList<>();
        compare(shipped.getContents().get(0), fromDsl.getContents().get(0), "theme", differences);
        assertTrue(differences.isEmpty(), "menutheme.xmi is out of step with menutheme.mazegame (regenerate it with "
                + "MAZE_THEME_REGENERATE=true): " + differences);
    }

    @Test
    public void theTemplateWritesWhatIsGivenAndLeavesOutAMissingSlice() throws Exception {
        String xmi = MazeDslGenerator.menuThemeXmi(parse(
            "game t { theme { font \"A & B\" colour TITLE \"#ffffff\" image TOKEN \"/ui/token.png\""
            + " image PANEL \"/ui/panel.png\" slice 30 badge CHEAP \"/ui/c.png\" colour \"#cd8c4e\" } }"));
        assertTrue(xmi.contains("font=\"A &amp; B\""), xmi);
        assertTrue(xmi.contains("<swatches role=\"TITLE\" colour=\"#ffffff\"/>"), xmi);
        assertTrue(xmi.contains("<images role=\"TOKEN\" path=\"/ui/token.png\"/>"), "no slice written: " + xmi);
        assertTrue(xmi.contains("<images role=\"PANEL\" path=\"/ui/panel.png\" slice=\"30\"/>"), xmi);
        assertTrue(xmi.contains("<badges tier=\"CHEAP\" image=\"/ui/c.png\" colour=\"#cd8c4e\"/>"), xmi);
        assertTrue(xmi.contains("Generated from t.mazegame"), xmi);
    }

    @Test
    public void themeRulesAreCheckedInTheDsl() throws Exception {
        GameConfiguration game = parse(
            "game t { theme { font \"F\"\n"
            + "  colour GLOW \"#fff\"\n"
            + "  colour TITLE \"#ffffff\" colour TITLE \"#000000\"\n"
            + "  image SPARKLE \"ui/x.png\" slice -1\n"
            + "  badge LEGENDARY \"/ui/l.png\" colour \"gold\"\n"
            + "} }");
        var swatch = MazeDslPackage.Literals.THEME_SWATCH_CONFIG;
        var image = MazeDslPackage.Literals.THEME_IMAGE_CONFIG;
        var badge = MazeDslPackage.Literals.THEME_BADGE_CONFIG;
        var theme = MazeDslPackage.Literals.MENU_THEME_CONFIG;
        validationHelper.assertError(game, swatch, MazeDslValidator.INVALID_THEME, "Unknown colour GLOW");
        validationHelper.assertError(game, swatch, MazeDslValidator.INVALID_THEME, "#rrggbb or #rrggbbaa, not #fff");
        validationHelper.assertError(game, image, MazeDslValidator.INVALID_THEME, "Unknown image SPARKLE");
        validationHelper.assertError(game, image, MazeDslValidator.INVALID_THEME, "starting with /");
        validationHelper.assertError(game, image, MazeDslValidator.INVALID_THEME, "0 or more pixels");
        validationHelper.assertError(game, badge, MazeDslValidator.INVALID_THEME, "Unknown tier LEGENDARY");
        validationHelper.assertError(game, badge, MazeDslValidator.INVALID_THEME, "not gold");
        validationHelper.assertError(game, theme, MazeDslValidator.INVALID_THEME, "colour TITLE twice");
        validationHelper.assertError(game, theme, MazeDslValidator.INVALID_THEME, "has no image for");
        validationHelper.assertError(game, theme, MazeDslValidator.INVALID_THEME, "has no tier badge for [CHEAP, MEDIUM, EXPENSIVE]");
    }

    @Test
    public void theNewWordsAreStillValidNames() throws Exception {
        GameConfiguration game = parse("game theme { opponent badge { type zombie } opponent colour { type ghost } }");
        assertTrue("theme".equals(game.getName()));
        assertTrue("colour".equals(game.getOpponents().get(1).getName()));
    }
}
