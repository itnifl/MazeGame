package main.game.maze.game.items;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PlayerArsenalRegenTest {

    @Test
    void givesOneBombEveryTenSecondsWhenThePlayerHasBombs() {
        PlayerArsenal arsenal = new PlayerArsenal(2);
        assertEquals(0, arsenal.tickBombRegen(9.9));
        assertEquals(1, arsenal.tickBombRegen(0.1));
        assertEquals(3, arsenal.count(Weapon.BOMB));
    }

    @Test
    void givesThreeBombsWhenThePlayerHasNone() {
        PlayerArsenal arsenal = new PlayerArsenal(0);
        assertEquals(3, arsenal.tickBombRegen(10.0));
        assertEquals(3, arsenal.count(Weapon.BOMB));
        assertEquals(Weapon.BOMB, arsenal.selected());
    }

    @Test
    void keepsTheRemainderBetweenTicks() {
        PlayerArsenal arsenal = new PlayerArsenal(2);
        assertEquals(1, arsenal.tickBombRegen(11));
        assertEquals(1, arsenal.tickBombRegen(9));
        assertEquals(4, arsenal.count(Weapon.BOMB));
    }

    @Test
    void grantsEveryCompleteIntervalAfterALongTick() {
        PlayerArsenal arsenal = new PlayerArsenal(2);
        assertEquals(2, arsenal.tickBombRegen(20));
        assertEquals(4, arsenal.count(Weapon.BOMB));
    }

    @Test
    void longTickWithNoBombsGivesThreeThenOne() {
        PlayerArsenal arsenal = new PlayerArsenal(0);
        assertEquals(4, arsenal.tickBombRegen(20));
        assertEquals(4, arsenal.count(Weapon.BOMB));
    }
}
