package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Map;
import main.game.maze.game.items.ArsenalSnapshot;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupEffects;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.PowerShield;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.session.PlayerLives;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Level boosts: the shop's extra long shield and extra long flames")
class LevelBoostsTest {

    @TempDir
    Path dir;

    @Test
    @DisplayName("none by default; a boost never lowers a factor and never goes below 1")
    void factors() {
        assertFalse(LevelBoosts.NONE.hasShieldBoost());
        assertFalse(LevelBoosts.NONE.hasBombBoost());
        LevelBoosts boosts = LevelBoosts.NONE.withShield(3).withBombPower(1.5);
        assertTrue(boosts.hasShieldBoost() && boosts.hasBombBoost());
        assertEquals(3, boosts.withShield(2).shieldDurationFactor(), 1e-9, "not lowered");
        assertEquals(1, new LevelBoosts(0.2, Double.NaN).bombPowerFactor(), 1e-9);
        assertEquals(150, boosts.boostedBombDamage(100));
        assertEquals(330, boosts.boostedBombRange(220), 1e-9);
    }

    @Test
    @DisplayName("a shield cube lasts longer with the shield boost")
    void longerShield() {
        long[] now = {0};
        PowerShield shield = new PowerShield(() -> now[0]);
        var spec = PickupCatalogLoader.loadDefault().spec(PickupKind.SHIELD);
        PickupEffects.apply(spec, new PlayerArsenal(0), shield, hp -> { }, 100, new PlayerLives(1),
                LevelBoosts.NONE.withShield(3));
        assertEquals(spec.durationSeconds() * 3, shield.remainingSeconds(), 1e-6);
    }

    @Test
    @DisplayName("the boosts are saved in the savepoint file and read back; older files have none")
    void savedInTheFile() {
        FileSavePointStore store = new FileSavePointStore(dir.resolve("save.properties"));
        ArsenalSnapshot arsenal = new ArsenalSnapshot(Map.of(Weapon.GRENADE, 5), Weapon.GRENADE);
        store.save(new SavePoint(3, arsenal, 900, "Hard", new LevelBoosts(3, 1.6)));
        SavePoint back = store.load().orElseThrow();
        assertEquals(new LevelBoosts(3, 1.6), back.boosts());
        assertEquals(5, back.arsenal().counts().get(Weapon.GRENADE));
        store.save(new SavePoint(3, null, 900));
        assertEquals(LevelBoosts.NONE, store.load().orElseThrow().boosts());
    }
}
