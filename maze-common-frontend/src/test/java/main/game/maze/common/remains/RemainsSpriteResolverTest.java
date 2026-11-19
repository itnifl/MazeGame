package main.game.maze.common.remains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("RemainsSpriteResolver")
class RemainsSpriteResolverTest {

    private static final String DIR = RemainsSpriteResolver.REMAINS_DIR;

    @ParameterizedTest
    @CsvSource({
            "/main/game/maze/zombie1-down.png, zombie1",
            "/main/game/maze/zombie2-damaged-left.png, zombie2",
            "/main/game/maze/ghost1.png, ghost1",
            "/main/game/maze/pumpkinbomber-bleeding-up.png, pumpkinbomber",
            "ghost3-tired.png, ghost3"
    })
    void variantStripsDirectionAndDamageLabels(String body, String expected) {
        assertEquals(expected, RemainsSpriteResolver.variantOf(body));
    }

    @Test
    @DisplayName("fire leaves one of the shared coal piles, picked by seed")
    void fireLeavesCoal() {
        RemainsSpriteResolver resolver = new RemainsSpriteResolver(path -> true);
        for (long seed = -5; seed < 20; seed++) {
            String path = resolver.spriteFor(DeathCause.FIRE, "/main/game/maze/zombie1-down.png", seed);
            assertTrue(path.equals(DIR + "burned-1.png") || path.equals(DIR + "burned-2.png")
                    || path.equals(DIR + "burned-3.png"), path);
        }
    }

    @Test
    @DisplayName("shot and crushed use the enemy's own remains, else the generic blood")
    void shotAndCrushedPerVariantWithFallback() {
        RemainsSpriteResolver resolver = new RemainsSpriteResolver(
                Set.of(DIR + "zombie1-shot.png", DIR + "zombie1-crushed.png")::contains);

        assertEquals(DIR + "zombie1-shot.png",
                resolver.spriteFor(DeathCause.SHOT, "/main/game/maze/zombie1-left.png", 0));
        assertEquals(DIR + "zombie1-crushed.png",
                resolver.spriteFor(DeathCause.CRUSHED, "/main/game/maze/zombie1-down.png", 0));
        assertEquals(DIR + "generic-shot.png",
                resolver.spriteFor(DeathCause.SHOT, "/main/game/maze/newmonster.png", 0));
        assertEquals(DIR + "generic-crushed.png",
                resolver.spriteFor(DeathCause.CRUSHED, null, 0));
    }

    @Test
    @DisplayName("other causes leave nothing")
    void otherLeavesNothing() {
        assertNull(new RemainsSpriteResolver(path -> true).spriteFor(DeathCause.OTHER, "/a/zombie1.png", 0));
        assertNull(new RemainsSpriteResolver(path -> true).spriteFor(null, "/a/zombie1.png", 0));
    }

    @Test
    @DisplayName("every shipped enemy has burned, shot and crushed remains on the classpath")
    void shippedSpritesExist() {
        RemainsSpriteResolver resolver = RemainsSpriteResolver.classpath(RemainsSpriteResolverTest.class);
        for (String body : new String[] {"zombie1-down.png", "zombie2-down.png", "zombie3-down.png",
                "ghost1.png", "ghost2.png", "ghost3.png", "pumpkinbomber.png"}) {
            String variant = RemainsSpriteResolver.variantOf(body);
            assertEquals(DIR + variant + "-shot.png", resolver.spriteFor(DeathCause.SHOT, "/main/game/maze/" + body, 0));
            assertEquals(DIR + variant + "-crushed.png",
                    resolver.spriteFor(DeathCause.CRUSHED, "/main/game/maze/" + body, 0));
        }
        for (int i = 1; i <= RemainsSpriteResolver.BURNED_VARIANTS; i++) {
            assertTrue(main.game.maze.common.graphics.DamageStateResolver.resourceExists(
                    RemainsSpriteResolverTest.class, DIR + "burned-" + i + ".png"));
        }
        for (String generic : new String[] {"generic-shot.png", "generic-crushed.png"}) {
            assertTrue(main.game.maze.common.graphics.DamageStateResolver.resourceExists(
                    RemainsSpriteResolverTest.class, DIR + generic));
        }
    }

    @Test
    void deathCauseRemainsFlag() {
        assertTrue(DeathCause.FIRE.leavesRemains());
        assertTrue(DeathCause.SHOT.leavesRemains());
        assertTrue(DeathCause.CRUSHED.leavesRemains());
        assertEquals(false, DeathCause.OTHER.leavesRemains());
    }
}
