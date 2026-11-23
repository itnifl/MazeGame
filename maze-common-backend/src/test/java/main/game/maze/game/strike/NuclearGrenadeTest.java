package main.game.maze.game.strike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.common.graphics.fire.MushroomCloudEffect;
import main.game.maze.game.items.BlastShape;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PickupSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("The small nuclear grenade looks and blows up like one")
class NuclearGrenadeTest {

    private static final PickupCatalog ITEMS = PickupCatalogLoader.loadDefault();
    private static final PickupSpec NUKE = ITEMS.spec(PickupKind.NUKE_GRENADE);

    @Test
    @DisplayName("the items model makes it a nuclear blast; the other grenades keep the bomb's flames")
    void nuclearInTheModel() {
        assertEquals(BlastShape.NUCLEAR, NUKE.blastShape());
        assertFalse(ITEMS.spec(PickupKind.GRENADE).nuclear());
        assertFalse(ITEMS.spec(PickupKind.FAT_BOMB).nuclear());
        ScheduledBlast thrown = StrikePlanner.thrownGrenade(NUKE, 0, 0, 1, 0, List.of());
        assertTrue(thrown.nuclear());
        assertFalse(thrown.hurtsPlayer(), "it reaches further than it can be thrown: the thrower is spared");
        assertEquals(PickupKind.NUKE_GRENADE, thrown.source());
        assertFalse(StrikePlanner.thrownGrenade(ITEMS.spec(PickupKind.GRENADE), 0, 0, 1, 0, List.of()).nuclear());
    }

    @Test
    @DisplayName("where it lies it shows the nuclear grenade itself, larger than a grenade, with a radioactive green glow")
    void looksLikeANuke() {
        ScheduledBlast lying = StrikePlanner.thrownGrenade(NUKE, 0, 0, 1, 0, List.of());
        assertEquals(NUKE.sprite(), StrikeVisuals.lyingSprite(lying, ITEMS));
        ScheduledBlast grenade = StrikePlanner.thrownGrenade(ITEMS.spec(PickupKind.GRENADE), 0, 0, 1, 0, List.of());
        assertTrue(StrikeVisuals.lyingSize(lying, ITEMS) > StrikeVisuals.lyingSize(grenade, ITEMS));
        float[] green = StrikeVisuals.fuseColour(lying);
        assertTrue(green[1] > green[0] && green[1] > green[2], "green");
    }

    @Test
    @DisplayName("it goes off as a mushroom cloud: a flash first, a stem and a cap rising, then smoke, in both screen directions")
    void mushroomCloud() {
        MushroomCloudEffect down = new MushroomCloudEffect(100, 100, 420, -1);
        MushroomCloudEffect up = new MushroomCloudEffect(100, 100, 420, 1);
        assertFalse(down.puffs(0.02, 0).isEmpty(), "the flash");
        assertTrue(down.puffs(1.0, 0).isEmpty(), "over");
        double meanDown = down.puffs(0.6, 1).stream().mapToDouble(p -> p.y()).average().orElseThrow();
        double meanUp = up.puffs(0.6, 1).stream().mapToDouble(p -> p.y()).average().orElseThrow();
        assertTrue(meanDown < 100, "rises up the screen (y down)");
        assertTrue(meanUp > 100, "rises up the screen (y up)");
        double[] bounds = down.bounds();
        down.puffs(0.6, 1).forEach(p -> assertTrue(p.y() - p.radius() >= bounds[1] && p.y() + p.radius() <= bounds[3]));
        assertNotNull(MushroomCloudEffect.class.getResource(MushroomCloudEffect.SOUND), "the nuclear roar");
    }
}
