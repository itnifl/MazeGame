package main.game.maze.game.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.game.level.LastLifeGiftSpec;
import main.game.maze.game.level.LevelCatalog;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pulse beam cannon: the gift of the last life on level 6 and every level after it")
class PulseCannonTest {

    private static final PickupSpec CANNON = PickupCatalogLoader.loadDefault().spec(PickupKind.PULSE_CANNON);
    private static final LevelCatalog LEVELS = LevelCatalogLoader.loadDefault();

    @Test
    @DisplayName("the cannon is defined in the items model: six shots, a two second charge, never lying about the maze")
    void definedInTheModel() {
        assertEquals(6, CANNON.amount(), "six shots");
        assertEquals(2.0, CANNON.chargeSeconds(), 1e-9, "two seconds to energy up");
        assertEquals(0, CANNON.spawnWeight(), "it only comes as the gift of the last life");
        assertNotNull(CANNON.chargeSound());
        assertNotNull(CANNON.useSound());
        assertEquals(Weapon.PULSE_CANNON, CANNON.kind().weapon());
        assertEquals(PickupKind.PULSE_CANNON, Weapon.PULSE_CANNON.pickup());
        for (String path : new String[] {CANNON.sprite(), CANNON.pickupSound(), CANNON.useSound(), CANNON.chargeSound()}) {
            assertNotNull(PulseCannonTest.class.getResource(path), "missing: " + path);
        }
    }

    @Test
    @DisplayName("the beam is strong enough to blow up a guard tower in a single shot, and much stronger than the laser")
    void blowsUpATowerInOneShot() {
        int towerHealth = LEVELS.level(4).guardTower().health();
        assertTrue(CANNON.damage() >= towerHealth, "damage " + CANNON.damage() + " vs tower " + towerHealth);
        PickupSpec laser = PickupCatalogLoader.loadDefault().spec(PickupKind.LASER_RIFLE);
        assertTrue(CANNON.damage() > 20 * laser.damage(), "extremely strong");
        assertTrue(CANNON.range() > laser.range());
    }

    @Test
    @DisplayName("every level from 6 on gives the gift, and no level before it does")
    void levelSixAndAfter() {
        for (int number = 1; number <= LEVELS.count(); number++) {
            LevelSpec level = LEVELS.level(number);
            if (number >= 6) {
                assertTrue(level.hasLastLifeGift(), "level " + number);
                assertEquals(Weapon.PULSE_CANNON, level.lastLifeGift().weapon());
                assertEquals(6, level.lastLifeGift().shots());
            } else {
                assertFalse(level.hasLastLifeGift(), "level " + number);
            }
        }
    }

    @Test
    @DisplayName("the gift is six shots, and the cannon is the weapon in hand until the player chooses another")
    void giftIsInTheHands() {
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.LASER_RIFLE, 4);
        assertEquals(Weapon.BOMB, arsenal.selected());
        LEVELS.level(6).lastLifeGift().give(arsenal);
        assertEquals(6, arsenal.count(Weapon.PULSE_CANNON));
        assertEquals(Weapon.PULSE_CANNON, arsenal.selected(), "selected unless the player selects anything else");
        arsenal.cycle();
        assertTrue(arsenal.selected() != Weapon.PULSE_CANNON, "the player can choose another weapon");
        assertEquals(6, arsenal.count(Weapon.PULSE_CANNON), "and the shots are still there");
        List<Weapon> seen = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            seen.add(arsenal.selected());
            arsenal.cycle();
        }
        assertTrue(seen.contains(Weapon.PULSE_CANNON), "Tab brings the cannon back");
    }

    @Test
    @DisplayName("a gift needs a known weapon and at least one shot")
    void giftValidation() {
        assertThrows(IllegalArgumentException.class, () -> LastLifeGiftSpec.of("DEATH_RAY", 3));
        assertThrows(IllegalArgumentException.class, () -> new LastLifeGiftSpec(Weapon.PULSE_CANNON, 0));
        assertNull(LEVELS.level(5).lastLifeGift());
    }

    @Test
    @DisplayName("the charge takes two seconds, then the shot leaves once; cancelling spends nothing")
    void charge() {
        PulseCharge charge = new PulseCharge();
        assertFalse(charge.charging());
        assertFalse(charge.tick(1.0), "nothing to charge");
        charge.start(CANNON.chargeSeconds());
        assertTrue(charge.charging());
        assertEquals(0.0, charge.progress(), 1e-9);
        assertFalse(charge.tick(1.0));
        assertEquals(0.5, charge.progress(), 1e-9);
        charge.start(5.0);   // pressing fire again while charging changes nothing
        assertFalse(charge.tick(0.9));
        assertTrue(charge.tick(0.2), "the shot leaves after two seconds");
        assertFalse(charge.charging());
        assertFalse(charge.tick(1.0), "only once");
        charge.start(2.0);
        charge.tick(1.0);
        charge.cancel();
        assertFalse(charge.charging());
        assertFalse(charge.tick(5.0), "a cancelled charge never fires");
    }
}
