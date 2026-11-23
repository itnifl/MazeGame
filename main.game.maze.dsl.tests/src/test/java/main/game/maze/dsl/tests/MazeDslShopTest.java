/*
 * MazeGame DSL - Shop Tests
 *
 * The shop between levels is written in the DSL (shop.mazegame) and rendered with a FreeMarker template into the XMI
 * the game loads (shop.xmi); these tests keep the two in step and check the shop rules of the validator.
 */
package main.game.maze.dsl.tests;

import static main.game.maze.dsl.tests.XmiModelSupport.compare;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class MazeDslShopTest {

    private static final String SHOP_DIR = "maze-common-backend/src/main/resources/xmi/shop/";

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
    public void shippedShopSourceGeneratesTheShippedXmi() throws Exception {
        EPackage shopModel = XmiModelSupport.metamodel(SHOP_DIR + "shop.ecore");
        GameConfiguration source = parse(Files.readString(XmiModelSupport.repoFile(SHOP_DIR + "shop.mazegame"),
                StandardCharsets.UTF_8));
        // a bad value in the DSL is found here, before shop.xmi is written
        validationHelper.assertNoErrors(source);
        String generated = MazeDslGenerator.shopXmi(source);
        if ("true".equalsIgnoreCase(System.getenv("MAZE_SHOP_REGENERATE"))) {
            // MAZE_SHOP_REGENERATE=true mvn -o verify -pl main.game.maze.dsl.tests rewrites shop.xmi from shop.mazegame
            Files.writeString(XmiModelSupport.repoFile(SHOP_DIR + "shop.xmi"), generated, StandardCharsets.UTF_8);
        }
        Resource fromDsl = XmiModelSupport.loadXmi(shopModel, generated, "generated");
        Resource shipped = XmiModelSupport.loadXmi(shopModel,
                Files.readString(XmiModelSupport.repoFile(SHOP_DIR + "shop.xmi"), StandardCharsets.UTF_8), "shipped");

        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(fromDsl.getContents().get(0));
        assertTrue(diagnostic.getSeverity() < Diagnostic.ERROR, "the generated XMI is valid against shop.ecore: " + diagnostic.getMessage());

        List<String> differences = new ArrayList<>();
        compare(shipped.getContents().get(0), fromDsl.getContents().get(0), "shop", differences);
        assertTrue(differences.isEmpty(), "shop.xmi is out of step with shop.mazegame (regenerate it with "
                + "MAZE_SHOP_REGENERATE=true): " + differences);
    }

    @Test
    public void theTemplateEscapesTextAndLeavesOutWhatIsNotWritten() throws Exception {
        String xmi = MazeDslGenerator.shopXmi(parse(
            "game s { shop { item GRENADE \"Bang & <boom> \\\"x\\\"\" tier CHEAP price 1500 amount 2 } }"));
        assertTrue(xmi.contains("displayName=\"Bang &amp; &lt;boom&gt; &quot;x&quot;\""), xmi);
        assertTrue(xmi.contains("price=\"1500\""), "no thousands separator: " + xmi);
        assertTrue(!xmi.contains("factor=") && !xmi.contains("description="), "left out: " + xmi);
        assertTrue(xmi.contains("Generated from s.mazegame"), xmi);
    }

    @Test
    public void shopRulesAreCheckedInTheDsl() throws Exception {
        GameConfiguration game = parse(
            "game s { shop {\n"
            + "  item ROCKET_PACK \"?\" tier CHEAP price 10 amount 1\n"
            + "  item GRENADE \"G\" tier CHEAPEST price 0\n"
            + "  item SHIELD_BOOST \"S\" tier CHEAP price 10 factor 1.0\n"
            + "  item SHIELD_BOOST \"S2\" tier CHEAP price 10 factor 2.0\n"
            + "} }");
        var shopItem = MazeDslPackage.Literals.SHOP_ITEM_CONFIG;
        validationHelper.assertError(game, shopItem, MazeDslValidator.INVALID_SHOP_ITEM, "Unknown shop item ROCKET_PACK");
        validationHelper.assertError(game, shopItem, MazeDslValidator.INVALID_SHOP_ITEM, "Unknown tier CHEAPEST");
        validationHelper.assertError(game, shopItem, MazeDslValidator.INVALID_SHOP_ITEM, "must cost something");
        validationHelper.assertError(game, shopItem, MazeDslValidator.INVALID_SHOP_ITEM, "amount of at least 1");
        validationHelper.assertError(game, shopItem, MazeDslValidator.INVALID_SHOP_ITEM, "factor above 1");
        validationHelper.assertError(game, MazeDslPackage.Literals.SHOP_CONFIG, MazeDslValidator.INVALID_SHOP_ITEM,
                "sells SHIELD_BOOST twice");
    }

    @Test
    public void theNewWordsAreStillValidNames() throws Exception {
        GameConfiguration game = parse("game shop { opponent price { type zombie } opponent tier { type ghost } }");
        assertEquals("shop", game.getName());
        assertEquals("price", game.getOpponents().get(0).getName());
    }
}
