package main.game.maze.game.ufo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.flame.FlameTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UFO retaliation (level 7): two UFOs beam the ground close to the player for ten seconds, red soldiers hunt them")
class UfoRaidTest {

    private static final UfoRaidSpec SPEC = LevelCatalogLoader.loadDefault().level(7).ufoRaid();
    /** The same raid without red soldiers, to test the beams alone. */
    private static final UfoRaidSpec BEAMS_ONLY = new UfoRaidSpec(SPEC.triggerTier(), SPEC.ufos(), SPEC.seconds(),
            SPEC.ufoSpeed(), SPEC.beamEverySeconds(), SPEC.chargeSeconds(), SPEC.beamSeconds(), SPEC.beamRadius(),
            SPEC.aimMinDistance(), SPEC.aimMaxDistance(), SPEC.damageFraction(), 0, SPEC.soldierHealth(),
            SPEC.soldierDamage(), SPEC.soldierSight(), SPEC.soldierSeconds(), SPEC.bannerText(), SPEC.ufoSprite(),
            SPEC.beamSprite(), SPEC.arriveSound(), SPEC.beamSound());
    private static final double W = 1000;
    private static final double H = 800;

    private static final InfantrySquad.Terrain OPEN = new InfantrySquad.Terrain() {
        @Override public boolean wallBetween(double fromX, double fromY, double toX, double toY) { return false; }
        @Override public List<Point2D> route(Point2D from, Point2D to) { return List.of(to); }
    };

    /** A player standing still in the middle, with 100 health. */
    private static final class Player implements UfoRaid.Target {
        double x = W / 2;
        double y = H / 2;
        int health = 100;
        boolean shield;
        int hurts;

        @Override public double x() { return x; }
        @Override public double y() { return y; }
        @Override public int maxHitPoints() { return 100; }
        @Override public boolean shielded() { return shield; }
        @Override public void hurt(int damage) { health -= damage; hurts++; }
    }

    private static List<Point2D> ground() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 0; x < 33; x++) {
            for (int y = 0; y < 26; y++) {
                points.add(new Point2D(x * 30, y * 30));
            }
        }
        return points;
    }

    @Test
    @DisplayName("level 7 has the retaliation of the model: two UFOs, ten seconds, half the health, ten red soldiers")
    void levelSevenModel() {
        assertEquals(2, SPEC.ufos());
        assertEquals(10.0, SPEC.seconds(), 1e-9);
        assertEquals(0.5, SPEC.damageFraction(), 1e-9);
        assertEquals(10, SPEC.soldiers());
        assertEquals("EXPENSIVE", SPEC.triggerTier());
        for (int level = 1; level <= 6; level++) {
            assertEquals(null, LevelCatalogLoader.loadDefault().level(level).ufoRaid(), "level " + level + " has no UFOs");
        }
    }

    @Test
    @DisplayName("only the expensive weapons of the shop bring the UFOs")
    void expensiveWeaponsOnly() {
        assertTrue(UfoRaid.triggeredBy(SPEC, Weapon.NUKE_GRENADE));
        assertTrue(UfoRaid.triggeredBy(SPEC, Weapon.AIRSTRIKE));
        assertTrue(UfoRaid.triggeredBy(SPEC, Weapon.INFANTRY_STRIKE));
        for (Weapon cheap : new Weapon[] {Weapon.BOMB, Weapon.GRENADE, Weapon.PLASMA_GRENADE, Weapon.FAT_BOMB,
            Weapon.BUNKER_BUSTER, Weapon.LASER_RIFLE, Weapon.LIFE_REFILL}) {
            assertFalse(UfoRaid.triggeredBy(SPEC, cheap), cheap + " is not expensive");
        }
        assertFalse(UfoRaid.triggeredBy(null, Weapon.AIRSTRIKE), "a level without a retaliation brings nothing");
    }

    @Test
    @DisplayName("two UFOs cross the map back and forth, and are gone after ten seconds")
    void ufosCrossAndLeave() {
        UfoRaid raid = new UfoRaid(new Random(5));
        Player player = new Player();
        raid.trigger(BEAMS_ONLY, W, H, player, ground());
        assertEquals(2, raid.ufos().size());
        boolean wentRight = false;
        boolean wentLeft = false;
        double minX = Double.MAX_VALUE;
        double maxX = 0;
        for (double t = 0; t < 9.9; t += 0.05) {
            raid.tick(0.05, player, OPEN);
            for (UfoRaid.UfoView ufo : raid.ufos()) {
                wentRight |= ufo.movingRight();
                wentLeft |= !ufo.movingRight();
                minX = Math.min(minX, ufo.x());
                maxX = Math.max(maxX, ufo.x());
                assertTrue(ufo.y() > 0 && ufo.y() < H, "inside the map");
            }
        }
        assertTrue(wentRight && wentLeft, "back and forth");
        assertTrue(maxX - minX > W * 0.8, "they cross the whole map: " + minX + ".." + maxX);
        for (double t = 0; t < 0.4; t += 0.05) {
            raid.tick(0.05, player, OPEN);
        }
        assertFalse(raid.ufosActive(), "the attack lasts ten seconds");
    }

    @Test
    @DisplayName("a beam lands close to the player, never straight at them")
    void beamsLandNearButNotOnThePlayer() {
        UfoRaid raid = new UfoRaid(new Random(11));
        Player player = new Player();
        raid.trigger(BEAMS_ONLY, W, H, player, ground());
        int beams = 0;
        for (double t = 0; t < 10; t += 0.05) {
            raid.tick(0.05, player, OPEN);
            for (UfoRaid.UfoView ufo : raid.ufos()) {
                if (ufo.beam() != null) {
                    beams++;
                    double distance = Math.hypot(ufo.beam().x() - player.x, ufo.beam().y() - player.y);
                    assertTrue(distance >= SPEC.aimMinDistance() - 1e-6, "not straight at the player: " + distance);
                    assertTrue(distance <= SPEC.aimMaxDistance() + 1e-6, "but close: " + distance);
                }
            }
        }
        assertTrue(beams > 0, "they do beam");
    }

    @Test
    @DisplayName("a player who bumps into a beam loses half their health, once per beam")
    void beamTakesHalfTheHealth() {
        UfoRaid raid = new UfoRaid(new Random(2));
        Player player = new Player();
        raid.trigger(BEAMS_ONLY, W, H, player, ground());
        int hits = 0;
        int beamsFired = 0;
        for (double t = 0; t < 10 && player.health > 0; t += 0.05) {
            // the player walks right into every beam as it comes on (bumps into it)
            for (UfoRaid.UfoView ufo : raid.ufos()) {
                if (ufo.beam() != null && !ufo.beam().charging()) {
                    player.x = ufo.beam().x();
                    player.y = ufo.beam().y();
                }
            }
            UfoRaid.Frame frame = raid.tick(0.05, player, OPEN);
            hits += frame.hits();
            beamsFired += frame.beamsFired();
            if (hits == 1) {
                assertEquals(50, player.health, "half of 100");
                break;
            }
        }
        assertEquals(1, hits, "caught in a beam (" + beamsFired + " fired)");
        assertEquals(50, player.health);
        // standing in the same beam does not hurt again
        player.health = 100;
        for (UfoRaid.UfoView ufo : raid.ufos()) {
            if (ufo.beam() != null) {
                player.x = ufo.beam().x();
                player.y = ufo.beam().y();
            }
        }
        int before = player.hurts;
        raid.tick(0.05, player, OPEN);
        assertEquals(before, player.hurts, "once per beam");
    }

    @Test
    @DisplayName("a shield blocks the beams")
    void shieldBlocksBeams() {
        UfoRaid raid = new UfoRaid(new Random(2));
        Player player = new Player();
        player.shield = true;
        raid.trigger(BEAMS_ONLY, W, H, player, ground());
        int blocked = 0;
        for (double t = 0; t < 10; t += 0.05) {
            for (UfoRaid.UfoView ufo : raid.ufos()) {
                if (ufo.beam() != null && !ufo.beam().charging()) {
                    player.x = ufo.beam().x();
                    player.y = ufo.beam().y();
                }
            }
            blocked += raid.tick(0.05, player, OPEN).blocked();
        }
        assertTrue(blocked > 0, "the beams hit the shield");
        assertEquals(100, player.health, "and nothing gets through");
    }

    @Test
    @DisplayName("a player who keeps clear of the beams is not hurt by them")
    void stayingClearIsSafe() {
        UfoRaid raid = new UfoRaid(new Random(4));
        Player player = new Player();
        raid.trigger(BEAMS_ONLY, W, H, player, ground());
        for (double t = 0; t < 10; t += 0.05) {
            for (UfoRaid.UfoView ufo : raid.ufos()) {
                if (ufo.beam() != null) {
                    // step away from the marked spot
                    double dx = player.x - ufo.beam().x();
                    double dy = player.y - ufo.beam().y();
                    if (Math.hypot(dx, dy) < ufo.beam().radius() + 60) {
                        player.x = ufo.beam().x() + ufo.beam().radius() + 60;
                    }
                }
            }
            raid.tick(0.05, player, OPEN);
        }
        assertEquals(100, player.health);
    }

    @Test
    @DisplayName("ten red soldiers are dropped some way off and hunt the player, who is shot at")
    void redSoldiersHuntThePlayer() {
        UfoRaid raid = new UfoRaid(new Random(8));
        Player player = new Player();
        assertTrue(raid.trigger(SPEC, W, H, player, ground()));
        assertEquals(10, raid.soldiers().size());
        for (InfantrySquad.SoldierView soldier : raid.soldiers()) {
            assertTrue(Math.hypot(soldier.x() - player.x, soldier.y() - player.y) > 100, "not dropped on the player");
        }
        double closest = Double.MAX_VALUE;
        int rounds = 0;
        for (double t = 0; t < 8; t += 0.05) {
            rounds += raid.tick(0.05, player, OPEN).rounds().size();
            for (InfantrySquad.SoldierView soldier : raid.soldiers()) {
                closest = Math.min(closest, Math.hypot(soldier.x() - player.x, soldier.y() - player.y));
            }
        }
        assertTrue(closest < SPEC.soldierSight(), "they come for the player: " + closest);
        assertTrue(rounds > 0, "and fire");
        assertTrue(player.health < 100, "the rounds hurt: " + player.health);
    }

    @Test
    @DisplayName("the shield blocks the soldiers' rounds too")
    void shieldBlocksRounds() {
        UfoRaid raid = new UfoRaid(new Random(8));
        Player player = new Player();
        player.shield = true;
        raid.trigger(SPEC, W, H, player, ground());
        for (double t = 0; t < 8; t += 0.05) {
            raid.tick(0.05, player, OPEN);
        }
        assertEquals(100, player.health);
    }

    @Test
    @DisplayName("the player's weapons kill the red soldiers, and touching them does not")
    void soldiersCanBeKilled() {
        UfoRaid raid = new UfoRaid(new Random(8));
        Player player = new Player();
        raid.trigger(SPEC, W, H, player, ground());
        List<FlameTarget> targets = raid.soldierTargets();
        assertEquals(10, targets.size());
        for (FlameTarget target : targets) {
            assertEquals(SPEC.soldierHealth(), target.hitPoints());
            assertFalse(target.isPassThrough());
            target.applyDamage(SPEC.soldierHealth());
        }
        raid.tick(0.05, player, OPEN);
        assertTrue(raid.soldiers().stream().allMatch(InfantrySquad.SoldierView::fallen), "all fell");
        assertTrue(raid.soldierTargets().isEmpty(), "the fallen cannot be hit again");
    }

    @Test
    @DisplayName("nothing happens when the level has no retaliation, and reset clears everything")
    void resetClears() {
        UfoRaid raid = new UfoRaid(new Random(1));
        Player player = new Player();
        raid.trigger(SPEC, W, H, player, ground());
        assertTrue(raid.active());
        raid.reset();
        assertFalse(raid.active());
        assertEquals(0, raid.tick(0.1, player, OPEN).beamsFired());
    }
}
