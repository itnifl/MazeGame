package main.game.maze.game.strike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.LevelBoosts;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Strike weapons: grenades, fat bomb, airstrike and infantry strike, the same in both frontends")
class StrikeWeaponsTest {

    private static final PickupCatalog ITEMS = PickupCatalogLoader.loadDefault();

    private static StrikeWeapons.Launch launch() {
        List<Point2D> ground = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ground.add(new Point2D(30 + i * 30, 400));
        }
        return new StrikeWeapons.Launch(100, 100, 1, 0, List.of(), 1100, 800, ground, LevelBoosts.NONE);
    }

    @Test
    @DisplayName("which weapons it handles")
    void handles() {
        assertTrue(StrikeWeapons.handles(Weapon.GRENADE));
        assertTrue(StrikeWeapons.handles(Weapon.PLASMA_GRENADE));
        assertTrue(StrikeWeapons.handles(Weapon.NUKE_GRENADE));
        assertTrue(StrikeWeapons.handles(Weapon.FAT_BOMB));
        assertTrue(StrikeWeapons.handles(Weapon.AIRSTRIKE));
        assertTrue(StrikeWeapons.handles(Weapon.INFANTRY_STRIKE));
        assertFalse(StrikeWeapons.handles(Weapon.BUNKER_BUSTER), "a shot of the frontend");
        assertFalse(StrikeWeapons.handles(Weapon.LIFE_REFILL), "heals the frontend's player");
        assertFalse(StrikeWeapons.handles(Weapon.BOMB));
        assertFalse(StrikeWeapons.handles(null));
        StrikeWeapons strikes = new StrikeWeapons(ITEMS, new Random(1));
        assertThrows(IllegalArgumentException.class, () -> strikes.use(Weapon.LASER_RIFLE, launch()));
    }

    @Test
    @DisplayName("a thrown grenade goes off after its fuse, ahead of the thrower")
    void grenade() {
        StrikeWeapons strikes = new StrikeWeapons(ITEMS, new Random(1));
        StrikeWeapons.Use use = strikes.use(Weapon.GRENADE, launch());
        assertEquals(PickupKind.GRENADE, use.spec().kind());
        assertEquals(1, strikes.pendingBlasts().size());
        assertTrue(strikes.tick(use.spec().fuseSeconds() / 2, List.of(), null).blasts().isEmpty());
        List<ScheduledBlast> due = strikes.tick(use.spec().fuseSeconds(), List.of(), null).blasts();
        assertEquals(1, due.size());
        assertEquals(100 + use.spec().range(), due.get(0).x(), 1e-9);
    }

    @Test
    @DisplayName("an airstrike drops all its bombs; an infantry strike lands its soldiers; reset clears both")
    void strikes() {
        StrikeWeapons strikes = new StrikeWeapons(ITEMS, new Random(1));
        strikes.use(Weapon.AIRSTRIKE, launch());
        assertEquals(ITEMS.spec(PickupKind.AIRSTRIKE).units(), strikes.pendingBlasts().size());
        StrikeWeapons.Use infantry = strikes.use(Weapon.INFANTRY_STRIKE, launch());
        assertTrue(infantry.message().startsWith("10 soldiers"));
        assertTrue(strikes.squadActive());
        assertEquals(10, strikes.soldiers().size());
        int dropped = 0;
        for (int i = 0; i < 100; i++) {
            dropped += strikes.tick(0.1, List.of(), InfantrySquadTestTerrain.OPEN).blasts().size();
        }
        assertEquals(ITEMS.spec(PickupKind.AIRSTRIKE).units(), dropped);
        strikes.use(Weapon.FAT_BOMB, launch());
        strikes.reset();
        assertTrue(strikes.pendingBlasts().isEmpty());
        assertFalse(strikes.squadActive());
    }

    /** An open field for the squad. */
    static final class InfantrySquadTestTerrain {
        static final InfantrySquad.Terrain OPEN = new InfantrySquad.Terrain() {
            @Override public boolean wallBetween(double fromX, double fromY, double toX, double toY) { return false; }
            @Override public List<Point2D> route(Point2D from, Point2D to) { return List.of(to); }
        };

        private InfantrySquadTestTerrain() {
        }
    }
}
