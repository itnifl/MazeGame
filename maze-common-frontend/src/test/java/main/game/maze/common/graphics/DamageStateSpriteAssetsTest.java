package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the generated damage-state sprites (issue #80, tools/sprite-gen) are on the
 * classpath under the names {@link DamageStateResolver} derives, are valid PNGs, and keep
 * the healthy sprite's dimensions so frontends can swap them in without resizing.
 */
@DisplayName("Damage-state sprite assets")
class DamageStateSpriteAssetsTest {

    private static final String SPRITE_DIR = "/main/game/maze/";

    /** Healthy sprites referenced by enemy CharacterTypes in opponentModel.xmi. */
    private static final List<String> BASE_SPRITES = List.of(
            "zombie1-left.png", "zombie1-right.png", "zombie1-up.png", "zombie1-down.png",
            "zombie2-left.png", "zombie2-right.png", "zombie2-up.png", "zombie2-down.png",
            "zombie3-left.png", "zombie3-right.png", "zombie3-up.png", "zombie3-down.png",
            "ghost1.png", "ghost1-left.png", "ghost1-right.png",
            "ghost2.png", "ghost2-left.png", "ghost2-right.png", "ghost2-up.png", "ghost2-down.png",
            "ghost3.png", "ghost3-left.png", "ghost3-right.png", "ghost3-up.png", "ghost3-down.png",
            "pumpkinbomber.png", "pumpkinbomber-left.png", "pumpkinbomber-up.png");

    static Stream<Arguments> baseAndState() {
        List<Arguments> args = new ArrayList<>();
        for (String base : BASE_SPRITES) {
            for (DamageState state : DamageState.values()) {
                if (state != DamageState.HEALTHY) {
                    args.add(Arguments.of(SPRITE_DIR + base, state));
                }
            }
        }
        return args.stream();
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("baseAndState")
    @DisplayName("resolver picks up the generated sprite instead of falling back")
    void resolverFindsGeneratedSprite(String basePath, DamageState state) {
        String resolved = DamageStateResolver.resolveWithFallback(basePath, state);

        assertEquals(DamageStateResolver.deriveStatePath(basePath, state), resolved);
        assertNotEquals(basePath, resolved);
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("baseAndState")
    @DisplayName("generated sprite is a valid PNG with the healthy sprite's dimensions")
    void generatedSpriteMatchesBaseDimensions(String basePath, DamageState state) throws IOException {
        BufferedImage healthy = load(basePath);
        BufferedImage derived = load(DamageStateResolver.deriveStatePath(basePath, state));

        assertEquals(healthy.getWidth(), derived.getWidth(), "width");
        assertEquals(healthy.getHeight(), derived.getHeight(), "height");
    }

    private static BufferedImage load(String path) throws IOException {
        try (InputStream in = DamageStateSpriteAssetsTest.class.getResourceAsStream(path)) {
            assertNotNull(in, "missing resource " + path);
            BufferedImage img = ImageIO.read(in);
            assertNotNull(img, "not a readable image " + path);
            return img;
        }
    }
}
