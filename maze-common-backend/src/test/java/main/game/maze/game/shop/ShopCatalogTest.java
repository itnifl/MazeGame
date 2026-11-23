package main.game.maze.game.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.Set;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.items.Weapon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Shop catalogue (shop.xmi): three cheap, four medium and three expensive items")
class ShopCatalogTest {

    private static final ShopCatalog SHOP = ShopCatalogLoader.loadDefault();
    private static final PickupCatalog ITEMS = PickupCatalogLoader.loadDefault();

    @Test
    @DisplayName("3 cheap, 4 medium, 3 expensive, each item once")
    void tiers() {
        assertEquals(3, SHOP.tier(ShopTier.CHEAP).size());
        assertEquals(4, SHOP.tier(ShopTier.MEDIUM).size());
        assertEquals(3, SHOP.tier(ShopTier.EXPENSIVE).size());
        Set<ShopItemKind> kinds = EnumSet.noneOf(ShopItemKind.class);
        for (ShopItem item : SHOP.items()) {
            assertTrue(kinds.add(item.kind()), "listed twice: " + item.kind());
        }
        assertEquals(EnumSet.allOf(ShopItemKind.class), kinds, "every item of the issue is sold");
    }

    @Test
    @DisplayName("every cheap item costs less than every medium one, and every medium one less than every expensive one")
    void pricesFollowTheTiers() {
        int maxCheap = SHOP.tier(ShopTier.CHEAP).stream().mapToInt(ShopItem::price).max().orElseThrow();
        int minMedium = SHOP.tier(ShopTier.MEDIUM).stream().mapToInt(ShopItem::price).min().orElseThrow();
        int maxMedium = SHOP.tier(ShopTier.MEDIUM).stream().mapToInt(ShopItem::price).max().orElseThrow();
        int minExpensive = SHOP.tier(ShopTier.EXPENSIVE).stream().mapToInt(ShopItem::price).min().orElseThrow();
        assertTrue(maxCheap < minMedium);
        assertTrue(maxMedium < minExpensive);
    }

    @Test
    @DisplayName("every weapon the shop sells is in the items model, with its sprite and sounds, and never lies about the maze")
    void weaponsAreDefinedInTheItemsModel() {
        for (ShopItem item : SHOP.items()) {
            Weapon weapon = item.kind().weapon();
            if (weapon == null) {
                continue;
            }
            PickupSpec spec = ITEMS.spec(weapon.pickup());
            assertEquals(0, spec.spawnWeight(), weapon + " is only sold in the shop");
            assertNotNull(ShopCatalogTest.class.getResource(spec.sprite()), "sprite of " + weapon + ": " + spec.sprite());
            assertNotNull(ShopCatalogTest.class.getResource(spec.pickupSound()), "sound of " + weapon);
            if (spec.useSound() != null) {
                assertNotNull(ShopCatalogTest.class.getResource(spec.useSound()), "use sound of " + weapon);
            }
        }
    }

    @Test
    @DisplayName("the extra life is three refills; the infantry strike is ten soldiers")
    void issueNumbers() {
        assertEquals(2, SHOP.unlockLevel(ShopTier.CHEAP), "weak items from the first shop");
        assertEquals(4, SHOP.unlockLevel(ShopTier.MEDIUM), "medium items from the shop between levels 3 and 4");
        assertEquals(6, SHOP.unlockLevel(ShopTier.EXPENSIVE), "strong items from the shop between levels 5 and 6");
        assertTrue(SHOP.sold(ShopTier.MEDIUM, 4) && !SHOP.sold(ShopTier.MEDIUM, 3));
        assertTrue(new ShopCatalog(SHOP.items()).sold(ShopTier.EXPENSIVE, 2), "a tier without an unlock is always sold");
        assertEquals(3, SHOP.item(ShopItemKind.EXTRA_LIFE).amount());
        assertEquals(Weapon.LIFE_REFILL, ShopItemKind.EXTRA_LIFE.weapon());
        assertEquals(10, ITEMS.spec(Weapon.INFANTRY_STRIKE.pickup()).units());
    }

    @Test
    @DisplayName("invalid items are refused when the model is read")
    void validation() {
        assertThrows(IllegalArgumentException.class,
                () -> new ShopItem(ShopItemKind.GRENADE, "G", "", ShopTier.CHEAP, 0, 5, 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new ShopItem(ShopItemKind.GRENADE, "G", "", ShopTier.CHEAP, 10, 0, 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new ShopItem(ShopItemKind.SHIELD_BOOST, "S", "", ShopTier.CHEAP, 10, 0, 1.0));
        assertThrows(UnsupportedOperationException.class, () -> SHOP.tier(ShopTier.CHEAP).clear(), "read-only");
    }
}
