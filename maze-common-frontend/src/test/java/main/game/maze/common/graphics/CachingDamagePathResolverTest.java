package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CachingDamagePathResolver")
class CachingDamagePathResolverTest {

    @Test
    @DisplayName("returns the derived path when the resource exists, else the base path")
    void resolvesWithFallback() {
        DamagePathResolver resolver = new CachingDamagePathResolver(
                Set.of("/s/zombie1-damaged-right.png")::contains);

        assertEquals("/s/zombie1-damaged-right.png", resolver.resolve("/s/zombie1-right.png", DamageState.DAMAGED));
        assertEquals("/s/zombie1-right.png", resolver.resolve("/s/zombie1-right.png", DamageState.TIRED));
    }

    @Test
    @DisplayName("looks each (path, state) up once, however often it is resolved")
    void cachesLookups() {
        List<String> lookups = new ArrayList<>();
        DamagePathResolver resolver = new CachingDamagePathResolver(path -> {
            lookups.add(path);
            return path.contains("damaged");
        });

        for (int frame = 0; frame < 100; frame++) {
            resolver.resolve("/s/ghost2.png", DamageState.DAMAGED);
            resolver.resolve("/s/ghost2.png", DamageState.BLEEDING);
        }

        assertEquals(List.of("/s/ghost2-damaged.png", "/s/ghost2-bleeding.png"), lookups);
    }

    @Test
    @DisplayName("HEALTHY, null state and blank paths pass through without a lookup")
    void passThrough() {
        List<String> lookups = new ArrayList<>();
        DamagePathResolver resolver = new CachingDamagePathResolver(path -> {
            lookups.add(path);
            return true;
        });

        assertEquals("/s/a.png", resolver.resolve("/s/a.png", DamageState.HEALTHY));
        assertEquals("/s/a.png", resolver.resolve("/s/a.png", null));
        assertEquals(" ", resolver.resolve(" ", DamageState.DAMAGED));
        assertNull(resolver.resolve(null, DamageState.DAMAGED));
        assertEquals(List.of(), lookups);
    }

    @Test
    @DisplayName("classpath factory resolves real damage sprites and falls back for missing ones")
    void classpathFactory() {
        DamagePathResolver resolver = DamagePathResolver.classpath(CachingDamagePathResolverTest.class);

        assertEquals("/main/game/maze/pumpkinbomber-tired-left.png",
                resolver.resolve("/main/game/maze/pumpkinbomber-left.png", DamageState.TIRED));
        assertEquals("/main/game/maze/zombieGameOverBackground1.png",
                resolver.resolve("/main/game/maze/zombieGameOverBackground1.png", DamageState.DAMAGED));
    }
}
