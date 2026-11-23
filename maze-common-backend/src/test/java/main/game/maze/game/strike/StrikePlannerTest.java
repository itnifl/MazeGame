package main.game.maze.game.strike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.level.LevelBoosts;
import main.game.maze.mazeworld.Vector2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Strike planner: where and when the shop weapons' blasts go off")
class StrikePlannerTest {

    private static final PickupCatalog ITEMS = PickupCatalogLoader.loadDefault();
    private static final PickupSpec GRENADE = ITEMS.spec(PickupKind.GRENADE);
    /** A wall across the corridor 100 px to the right of the thrower at (0, 0). */
    private static final List<Vector2D> WALL_AHEAD = List.of(new Vector2D(100, -50, 100, 50));

    @Test
    @DisplayName("a grenade flies its full range in the open, and lands short of a wall in the way")
    void throwDistance() {
        assertEquals(GRENADE.range(), StrikePlanner.throwDistance(0, 0, 1, 0, GRENADE.range(), List.of()), 1e-9);
        double toWall = StrikePlanner.throwDistance(0, 0, 1, 0, GRENADE.range(), WALL_AHEAD);
        assertEquals(100 - StrikePlanner.LANDING_MARGIN, toWall, 1e-6);
        assertEquals(GRENADE.range(), StrikePlanner.throwDistance(0, 0, -1, 0, GRENADE.range(), WALL_AHEAD), 1e-9,
                "the wall behind does not matter");
    }

    @Test
    @DisplayName("the thrown grenade's blast is where it lands, after its fuse, with its damage and radius")
    void thrownGrenade() {
        ScheduledBlast blast = StrikePlanner.thrownGrenade(GRENADE, 0, 0, 1, 0, WALL_AHEAD);
        assertEquals(100 - StrikePlanner.LANDING_MARGIN, blast.x(), 1e-6);
        assertEquals(0, blast.y(), 1e-9);
        assertEquals(GRENADE.fuseSeconds(), blast.secondsLeft(), 1e-9);
        assertEquals(GRENADE.damage(), blast.damage());
        assertEquals(GRENADE.blastRadius(), blast.radius(), 1e-9);
        assertTrue(blast.hurtsPlayer() && blast.showsFuse());
    }

    @Test
    @DisplayName("the stronger grenades are stronger: plasma beats grenade, the nuke beats plasma")
    void grenadeStrengths() {
        PickupSpec plasma = ITEMS.spec(PickupKind.PLASMA_GRENADE);
        PickupSpec nuke = ITEMS.spec(PickupKind.NUKE_GRENADE);
        assertTrue(plasma.damage() > GRENADE.damage() && plasma.blastRadius() > GRENADE.blastRadius());
        assertTrue(nuke.damage() > plasma.damage() && nuke.blastRadius() > plasma.blastRadius());
    }

    @Test
    @DisplayName("the fat bomb is planted at the feet, and the flame boost makes it stronger")
    void fatBomb() {
        PickupSpec fat = ITEMS.spec(PickupKind.FAT_BOMB);
        ScheduledBlast normal = StrikePlanner.fatBomb(fat, 40, 60, LevelBoosts.NONE);
        assertEquals(40, normal.x(), 1e-9);
        assertEquals(60, normal.y(), 1e-9);
        assertEquals(fat.damage(), normal.damage());
        ScheduledBlast boosted = StrikePlanner.fatBomb(fat, 40, 60, LevelBoosts.NONE.withBombPower(2.0));
        assertEquals(fat.damage() * 2, boosted.damage());
        assertEquals(fat.blastRadius() * 2, boosted.radius(), 1e-9);
    }

    @Test
    @DisplayName("the airstrike drops its bombs round the centre, one after the other, and never on the player who called it")
    void airstrike() {
        PickupSpec strike = ITEMS.spec(PickupKind.AIRSTRIKE);
        double width = 1100;
        double height = 800;
        List<ScheduledBlast> plan = StrikePlanner.airstrike(strike, width, height, new Random(7));
        assertEquals(strike.units(), plan.size());
        double reach = strike.range() + StrikePlanner.AIM_SCATTER * Math.max(width, height) * Math.sqrt(2);
        for (int i = 0; i < plan.size(); i++) {
            ScheduledBlast bomb = plan.get(i);
            assertTrue(Math.hypot(bomb.x() - width / 2, bomb.y() - height / 2) <= reach + 1e-6, "near the centre");
            assertTrue(bomb.x() >= 0 && bomb.x() <= width && bomb.y() >= 0 && bomb.y() <= height, "on the board");
            assertFalse(bomb.hurtsPlayer());
            assertFalse(bomb.showsFuse(), "falling bombs have no fuse on the floor");
            if (i > 0) {
                assertTrue(bomb.secondsLeft() > plan.get(i - 1).secondsLeft(), "one after the other");
            }
        }
        assertEquals(strike.fuseSeconds(), plan.get(0).secondsLeft(), 1e-9);
        assertEquals(strike.fuseSeconds() + strike.durationSeconds(), plan.get(plan.size() - 1).secondsLeft(), 1e-9);
    }

    @Test
    @DisplayName("the bunker buster blows up where its rocket stopped, set back out of the wall")
    void bunkerBusterImpact() {
        PickupSpec buster = ITEMS.spec(PickupKind.BUNKER_BUSTER);
        ScheduledBlast impact = StrikePlanner.bunkerBusterImpact(buster, 10, 20, 0, 1, 300);
        assertEquals(10, impact.x(), 1e-9);
        assertEquals(20 + 300 - StrikePlanner.IMPACT_STEP_BACK, impact.y(), 1e-9);
        assertEquals(buster.damage(), impact.damage());
        assertTrue(buster.piercePower() > ITEMS.spec(PickupKind.LASER_RIFLE).damage() * 10, "it goes through walls");
    }
}
