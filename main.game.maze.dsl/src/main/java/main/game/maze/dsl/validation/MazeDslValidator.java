/*
 * MazeGame DSL - Validator
 * 
 * This class defines validation rules for the MazeDsl language.
 * These rules ensure that game configurations are semantically correct.
 */
package main.game.maze.dsl.validation;

import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import main.game.maze.dsl.mazeDsl.*;

/**
 * Custom validation rules for MazeDsl.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class MazeDslValidator extends AbstractMazeDslValidator {

    public static final String INVALID_THREAT_LEVEL = "invalidThreatLevel";
    public static final String INVALID_HEALTH = "invalidHealth";
    public static final String INVALID_SECOND_RING = "invalidSecondRing";
    public static final String INVALID_LAVA = "invalidLava";
    public static final String INVALID_UFO_RAID = "invalidUfoRaid";
    public static final String INVALID_SPEED = "invalidSpeed";
    public static final String INSUFFICIENT_WAYPOINTS = "insufficientWaypoints";
    public static final String INVALID_MAX_COUNT = "invalidMaxCount";
    public static final String INVALID_MAX_THREAT = "invalidMaxThreat";
    public static final String DUPLICATE_OPPONENT_NAME = "duplicateOpponentName";
    public static final String DUPLICATE_PATROL_NAME = "duplicatePatrolName";
    public static final String WAYPOINT_OUTSIDE_ZONE = "waypointOutsideZone";
    public static final String MISMATCHED_SPECIFICS = "mismatchedSpecifics";
    public static final String PATROL_REF_REQUIRED = "patrolRefRequired";

    /**
     * Validates that threat level is within acceptable bounds (0-100).
     */
    @Check
    public void checkThreatLevel(OpponentConfig opponent) {
        if (opponent.getThreatLevel() > 100) {
            error("Threat level cannot exceed 100",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__THREAT_LEVEL,
                  INVALID_THREAT_LEVEL);
        }
        if (opponent.getThreatLevel() < 0) {
            error("Threat level cannot be negative",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__THREAT_LEVEL,
                  INVALID_THREAT_LEVEL);
        }
    }

    /**
     * Validates that health is positive (0 means use default).
     */
    /**
     * The lava values the game would refuse to load (see {@code LavaSpec}) are errors in the DSL, so a bad value is found
     * before the levels XMI is written and not when the game starts.
     */
    @Check
    public void checkLava(LavaConfig lava) {
        positive(lava.getStreamCount() >= 1, "streams must be at least 1", MazeDslPackage.Literals.LAVA_CONFIG__STREAM_COUNT);
        positive(lava.getWidth() > 0, "width must be positive", MazeDslPackage.Literals.LAVA_CONFIG__WIDTH);
        positive(lava.getBridgesPerStream() >= 1, "bridges must be at least 1", MazeDslPackage.Literals.LAVA_CONFIG__BRIDGES_PER_STREAM);
        positive(lava.getBridgeLength() > 0, "bridgeLength must be positive", MazeDslPackage.Literals.LAVA_CONFIG__BRIDGE_LENGTH);
        if (lava.getMeander() != null) {
            positive(lava.getMeander() >= 0, "meander cannot be negative", MazeDslPackage.Literals.LAVA_CONFIG__MEANDER);
        }
        if (lava.getWavelength() != null) {
            positive(lava.getWavelength() > 0, "wavelength must be positive", MazeDslPackage.Literals.LAVA_CONFIG__WAVELENGTH);
        }
        if (lava.getFlowSpeed() != null) {
            positive(lava.getFlowSpeed() >= 0, "flowSpeed cannot be negative", MazeDslPackage.Literals.LAVA_CONFIG__FLOW_SPEED);
        }
        if (lava.getGlow() != null) {
            positive(lava.getGlow() >= 0 && lava.getGlow() <= 1, "glow must be between 0 and 1", MazeDslPackage.Literals.LAVA_CONFIG__GLOW);
        }
        if (lava.getCrust() != null) {
            positive(lava.getCrust() >= 0 && lava.getCrust() <= 1, "crust must be between 0 and 1", MazeDslPackage.Literals.LAVA_CONFIG__CRUST);
        }
    }

    /** The last-life gift needs at least one shot. */
    /** The UFO raid values the game would refuse to load (see {@code UfoRaidSpec}) are errors in the DSL. */
    @Check
    public void checkUfoRaid(UfoRaidConfig raid) {
        String tier = raid.getTriggerTier();
        ufoRule(tier == null || tier.equals("CHEAP") || tier.equals("MEDIUM") || tier.equals("EXPENSIVE"),
                "trigger is CHEAP, MEDIUM or EXPENSIVE", MazeDslPackage.Literals.UFO_RAID_CONFIG__TRIGGER_TIER);
        ufoRule(raid.getUfos() >= 1, "ufos must be at least 1", MazeDslPackage.Literals.UFO_RAID_CONFIG__UFOS);
        ufoRule(raid.getSeconds() > 0, "duration must be positive", MazeDslPackage.Literals.UFO_RAID_CONFIG__SECONDS);
        ufoRule(raid.getUfoSpeed() > 0, "ufoSpeed must be positive", MazeDslPackage.Literals.UFO_RAID_CONFIG__UFO_SPEED);
        ufoRule(raid.getBeamRadius() > 0, "beamRadius must be positive", MazeDslPackage.Literals.UFO_RAID_CONFIG__BEAM_RADIUS);
        ufoRule(raid.getAimMinDistance() >= 0 && raid.getAimMaxDistance() >= raid.getAimMinDistance(), "aimMin must not be above aimMax",
                MazeDslPackage.Literals.UFO_RAID_CONFIG__AIM_MAX_DISTANCE);
        ufoRule(raid.getDamageFraction() > 0 && raid.getDamageFraction() <= 1, "damageFraction is above 0 and at most 1",
                MazeDslPackage.Literals.UFO_RAID_CONFIG__DAMAGE_FRACTION);
        ufoRule(raid.getSoldierHealth() >= 1, "soldierHealth must be at least 1",
                MazeDslPackage.Literals.UFO_RAID_CONFIG__SOLDIER_HEALTH);
    }

    @Check
    public void checkLastLifeGift(LastLifeGiftConfig gift) {
        positive(gift.getShots() >= 1, "a gift needs at least one shot", MazeDslPackage.Literals.LAST_LIFE_GIFT_CONFIG__SHOTS);
    }

    private void ufoRule(boolean ok, String message, org.eclipse.emf.ecore.EStructuralFeature feature) {
        if (!ok) {
            error(message, feature, INVALID_UFO_RAID);
        }
    }

    private void positive(boolean ok, String message, org.eclipse.emf.ecore.EStructuralFeature feature) {
        if (!ok) {
            error(message, feature, INVALID_LAVA);
        }
    }

    public static final String INVALID_SHOP_ITEM = "invalidShopItem";
    public static final String INVALID_THEME = "invalidTheme";

    /** The literals of {@code Swatch} and {@code Art} in {@code xmi/theme/menutheme.ecore}. */
    public static final java.util.List<String> THEME_SWATCHES = java.util.List.of("TITLE", "TEXT", "MUTED", "ACCENT", "PRICE",
            "TOO_DEAR", "GOOD", "BAD", "BUTTON_TEXT", "SHADOW");
    public static final java.util.List<String> THEME_ART = java.util.List.of("BACKDROP", "VIGNETTE", "HAZARD_STRIP", "PANEL",
            "BANNER", "CARD", "CARD_SELECTED", "CARD_LOCKED", "SLOT", "BUTTON_GO", "BUTTON_BUY", "BUTTON_STEEL",
            "BUTTON_DANGER", "TOKEN", "LOCK");

    /** The literals of {@code ShopItemKind} in {@code xmi/shop/shop.ecore}; the boosts are the first two. */
    public static final java.util.List<String> SHOP_ITEM_KINDS = java.util.List.of("SHIELD_BOOST", "FLAME_BOOST", "GRENADE",
            "PLASMA_GRENADE", "NUKE_GRENADE", "BUNKER_BUSTER", "FAT_BOMB", "AIRSTRIKE", "INFANTRY_STRIKE", "EXTRA_LIFE");
    /** The literals of {@code ShopTier} in {@code xmi/shop/shop.ecore}. */
    public static final java.util.List<String> SHOP_TIERS = java.util.List.of("CHEAP", "MEDIUM", "EXPENSIVE");

    private static boolean isBoost(String kind) {
        return "SHIELD_BOOST".equals(kind) || "FLAME_BOOST".equals(kind);
    }

    /**
     * A shop item the game would refuse to load (see {@code ShopItem}) is an error in the DSL, so it is found before
     * shop.xmi is written: an unknown kind or tier, a price that is not positive, a weapon without uses, a boost that does
     * not boost.
     */
    @Check
    public void checkThemeSwatch(ThemeSwatchConfig swatch) {
        if (!THEME_SWATCHES.contains(swatch.getRole())) {
            error("Unknown colour " + swatch.getRole() + "; one of " + THEME_SWATCHES,
                  MazeDslPackage.Literals.THEME_SWATCH_CONFIG__ROLE, INVALID_THEME);
        }
        checkColour(swatch.getColour(), MazeDslPackage.Literals.THEME_SWATCH_CONFIG__COLOUR);
    }

    @Check
    public void checkThemeImage(ThemeImageConfig image) {
        if (!THEME_ART.contains(image.getRole())) {
            error("Unknown image " + image.getRole() + "; one of " + THEME_ART,
                  MazeDslPackage.Literals.THEME_IMAGE_CONFIG__ROLE, INVALID_THEME);
        }
        if (image.getPath() == null || !image.getPath().startsWith("/")) {
            error("An image is a classpath path starting with /", MazeDslPackage.Literals.THEME_IMAGE_CONFIG__PATH,
                  INVALID_THEME);
        }
        if (image.getSlice() != null && image.getSlice() < 0) {
            error("A slice is 0 or more pixels", MazeDslPackage.Literals.THEME_IMAGE_CONFIG__SLICE, INVALID_THEME);
        }
    }

    @Check
    public void checkThemeBadge(ThemeBadgeConfig badge) {
        if (!SHOP_TIERS.contains(badge.getTier())) {
            error("Unknown tier " + badge.getTier() + "; one of " + SHOP_TIERS,
                  MazeDslPackage.Literals.THEME_BADGE_CONFIG__TIER, INVALID_THEME);
        }
        checkColour(badge.getColour(), MazeDslPackage.Literals.THEME_BADGE_CONFIG__COLOUR);
    }

    private void checkColour(String colour, org.eclipse.emf.ecore.EAttribute feature) {
        if (colour == null || !colour.matches("#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})")) {
            error("A colour is #rrggbb or #rrggbbaa, not " + colour, feature, INVALID_THEME);
        }
    }

    /** The theme is complete: every colour, image and tier badge, each once (the game cannot draw a missing one). */
    @Check
    public void checkThemeIsComplete(MenuThemeConfig theme) {
        checkEachOnce(theme.getSwatches().stream().map(ThemeSwatchConfig::getRole).toList(), THEME_SWATCHES, "colour",
                MazeDslPackage.Literals.MENU_THEME_CONFIG__SWATCHES);
        checkEachOnce(theme.getImages().stream().map(ThemeImageConfig::getRole).toList(), THEME_ART, "image",
                MazeDslPackage.Literals.MENU_THEME_CONFIG__IMAGES);
        checkEachOnce(theme.getBadges().stream().map(ThemeBadgeConfig::getTier).toList(), SHOP_TIERS, "tier badge",
                MazeDslPackage.Literals.MENU_THEME_CONFIG__BADGES);
    }

    private void checkEachOnce(java.util.List<String> written, java.util.List<String> all, String what,
                               org.eclipse.emf.ecore.EReference feature) {
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (int i = 0; i < written.size(); i++) {
            if (!seen.add(written.get(i))) {
                error("The theme has the " + what + " " + written.get(i) + " twice", feature, i, INVALID_THEME);
            }
        }
        java.util.List<String> missing = all.stream().filter(name -> !seen.contains(name)).toList();
        if (!missing.isEmpty()) {
            error("The theme has no " + what + " for " + missing, feature, INVALID_THEME);
        }
    }

    @Check
    public void checkShopItem(ShopItemConfig item) {
        if (!SHOP_ITEM_KINDS.contains(item.getKind())) {
            error("Unknown shop item " + item.getKind() + "; one of " + SHOP_ITEM_KINDS,
                  MazeDslPackage.Literals.SHOP_ITEM_CONFIG__KIND, INVALID_SHOP_ITEM);
        }
        if (!SHOP_TIERS.contains(item.getTier())) {
            error("Unknown tier " + item.getTier() + "; one of " + SHOP_TIERS,
                  MazeDslPackage.Literals.SHOP_ITEM_CONFIG__TIER, INVALID_SHOP_ITEM);
        }
        if (item.getPrice() <= 0) {
            error("A shop item must cost something", MazeDslPackage.Literals.SHOP_ITEM_CONFIG__PRICE, INVALID_SHOP_ITEM);
        }
        if (isBoost(item.getKind())) {
            if (item.getFactor() == null || item.getFactor() <= 1.0) {
                error("A boost needs a factor above 1", MazeDslPackage.Literals.SHOP_ITEM_CONFIG__FACTOR, INVALID_SHOP_ITEM);
            }
        } else if (item.getAmount() == null || item.getAmount() < 1) {
            error("A weapon in the shop needs an amount of at least 1", MazeDslPackage.Literals.SHOP_ITEM_CONFIG__AMOUNT,
                  INVALID_SHOP_ITEM);
        }
    }

    /** A tier opens from a real level (the first shop is the one before level 2), and with a known tier. */
    @Check
    public void checkShopUnlock(ShopUnlockConfig unlock) {
        if (!SHOP_TIERS.contains(unlock.getTier())) {
            error("Unknown tier " + unlock.getTier() + "; one of " + SHOP_TIERS,
                  MazeDslPackage.Literals.SHOP_UNLOCK_CONFIG__TIER, INVALID_SHOP_ITEM);
        }
        if (unlock.getLevel() < 2) {
            error("The first shop is the one before level 2, so a tier opens from level 2 or later",
                  MazeDslPackage.Literals.SHOP_UNLOCK_CONFIG__LEVEL, INVALID_SHOP_ITEM);
        }
    }

    /** Each item is sold once, and each tier is unlocked once. */
    @Check
    public void checkShopHasNoDuplicates(ShopConfig shop) {
        java.util.Set<String> tiers = new java.util.HashSet<>();
        for (int i = 0; i < shop.getUnlocks().size(); i++) {
            if (!tiers.add(shop.getUnlocks().get(i).getTier())) {
                error("The tier " + shop.getUnlocks().get(i).getTier() + " is unlocked twice",
                      MazeDslPackage.Literals.SHOP_CONFIG__UNLOCKS, i, INVALID_SHOP_ITEM);
            }
        }
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (int i = 0; i < shop.getItems().size(); i++) {
            ShopItemConfig item = shop.getItems().get(i);
            if (!seen.add(item.getKind())) {
                error("The shop sells " + item.getKind() + " twice", MazeDslPackage.Literals.SHOP_CONFIG__ITEMS, i,
                      INVALID_SHOP_ITEM);
            }
        }
    }

    /** 0 (or leaving it out) is the only way to say "no second ring"; a negative radius is a mistake, not a silent 0. */
    @Check
    public void checkSecondRing(MineRingConfig mines) {
        if (mines.getSecondRingRadius() < 0) {
            error("The second ring radius cannot be negative (leave it out for no second ring)",
                  MazeDslPackage.Literals.MINE_RING_CONFIG__SECOND_RING_RADIUS,
                  INVALID_SECOND_RING);
        } else if (mines.getSecondRingRadius() > 0 && mines.getSecondRingRadius() <= mines.getRingRadius()) {
            error("The second ring must lie outside the first ring (ringRadius " + mines.getRingRadius() + ")",
                  MazeDslPackage.Literals.MINE_RING_CONFIG__SECOND_RING_RADIUS,
                  INVALID_SECOND_RING);
        }
    }

    @Check
    public void checkHealth(OpponentConfig opponent) {
        if (opponent.getHealth() < 0) {
            error("Health cannot be negative",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__HEALTH,
                  INVALID_HEALTH);
        }
    }

    /**
     * Validates that speed is positive.
     */
    @Check
    public void checkSpeed(OpponentConfig opponent) {
        if (opponent.getSpeed() < 0) {
            error("Speed cannot be negative",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__SPEED,
                  INVALID_SPEED);
        }
    }

    /**
     * Validates that patrol paths have at least 2 waypoints.
     */
    @Check
    public void checkPatrolWaypoints(PatrolConfig patrol) {
        if (patrol.getWaypoints().size() < 2) {
            var feature = patrol.getWaypoints().isEmpty()
                ? MazeDslPackage.Literals.PATROL_CONFIG__NAME
                : MazeDslPackage.Literals.PATROL_CONFIG__WAYPOINTS;
            warning("Patrol should have at least 2 waypoints for meaningful movement",
                    feature,
                    INSUFFICIENT_WAYPOINTS);
        }
    }

    /**
     * Validates that max threat in difficulty is reasonable.
     */
    @Check
    public void checkMaxThreat(DifficultyConfig difficulty) {
        if (difficulty.getMaxThreat() < 0) {
            error("Max threat cannot be negative",
                  MazeDslPackage.Literals.DIFFICULTY_CONFIG__MAX_THREAT,
                  INVALID_MAX_THREAT);
        }
    }

    /**
     * Validates that enemy limits have positive max count.
     */
    @Check
    public void checkEnemyLimit(EnemyLimit limit) {
        if (limit.getMaxCount() < 0) {
            error("Max enemy count cannot be negative",
                  MazeDslPackage.Literals.ENEMY_LIMIT__MAX_COUNT,
                  INVALID_MAX_COUNT);
        }
    }

    /**
     * Validates that waypoints are within the patrol zone if defined.
     */
    @Check
    public void checkWaypointsInZone(PatrolConfig patrol) {
        PatrolZoneConfig zone = patrol.getZone();
        if (zone == null) {
            return;
        }

        double minX = zone.getTopLeftX();
        double minY = zone.getTopLeftY();
        double maxX = minX + zone.getWidth();
        double maxY = minY + zone.getHeight();

        for (Waypoint wp : patrol.getWaypoints()) {
            if (wp.getX() < minX || wp.getX() > maxX ||
                wp.getY() < minY || wp.getY() > maxY) {
                warning("Waypoint (" + wp.getX() + ", " + wp.getY() + ") is outside patrol zone",
                        MazeDslPackage.Literals.PATROL_CONFIG__WAYPOINTS,
                        WAYPOINT_OUTSIDE_ZONE);
            }
        }
    }

    /**
     * Validates that character-specific blocks match the character type.
     */
    @Check
    public void checkCharacterSpecificsMatch(OpponentConfig opponent) {
        CharacterSpecifics specifics = opponent.getCharacterSpecifics();
        if (specifics == null) {
            return;
        }

        CharacterTypeEnum type = opponent.getType();
        
        if (specifics instanceof ZombieSpecifics && type != CharacterTypeEnum.ZOMBIE) {
            warning("zombie-stats block should only be used with zombie type opponents",
                    MazeDslPackage.Literals.OPPONENT_CONFIG__CHARACTER_SPECIFICS,
                    MISMATCHED_SPECIFICS);
        }
        
        if (specifics instanceof GhostSpecifics && type != CharacterTypeEnum.GHOST) {
            warning("ghost-stats block should only be used with ghost type opponents",
                    MazeDslPackage.Literals.OPPONENT_CONFIG__CHARACTER_SPECIFICS,
                    MISMATCHED_SPECIFICS);
        }
        
        if (specifics instanceof RangedSpecifics && type != CharacterTypeEnum.PUMPKINBOMBER) {
            warning("ranged-stats block should only be used with pumpkinbomber type opponents",
                    MazeDslPackage.Literals.OPPONENT_CONFIG__CHARACTER_SPECIFICS,
                    MISMATCHED_SPECIFICS);
        }
    }

    /**
     * Validates that opponent names are unique within a game configuration.
     */
    @Check(CheckType.NORMAL)
    public void checkUniqueOpponentNames(GameConfiguration game) {
        java.util.Set<String> names = new java.util.HashSet<>();
        for (OpponentConfig opponent : game.getOpponents()) {
            if (!names.add(opponent.getName())) {
                error("Duplicate opponent name: " + opponent.getName(),
                      opponent,
                      MazeDslPackage.Literals.OPPONENT_CONFIG__NAME,
                      DUPLICATE_OPPONENT_NAME);
            }
        }
    }

    /**
     * Validates that patrol names are unique within a game configuration.
     */
    @Check(CheckType.NORMAL)
    public void checkUniquePatrolNames(GameConfiguration game) {
        java.util.Set<String> names = new java.util.HashSet<>();
        for (PatrolConfig patrol : game.getPatrols()) {
            if (!names.add(patrol.getName())) {
                error("Duplicate patrol name: " + patrol.getName(),
                      patrol,
                      MazeDslPackage.Literals.PATROL_CONFIG__NAME,
                      DUPLICATE_PATROL_NAME);
            }
        }
    }

    /**
     * Validates that opponents with patrol behavior have a patrol reference.
     */
    @Check
    public void checkPatrolBehaviorHasRef(OpponentConfig opponent) {
        if (opponent.getBehavior() == BehaviorTypeEnum.PATROL && opponent.getPatrolRef() == null) {
            error("Opponents with patrol behavior must have a patrolRef",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__PATROL_REF,
                  PATROL_REF_REQUIRED);
        }
    }

    /**
     * Validates total threat level doesn't exceed max threat.
     */
    @Check(CheckType.EXPENSIVE)
    public void checkTotalThreatVsMaxThreat(GameConfiguration game) {
        DifficultyConfig diff = game.getDifficulty();
        if (diff == null) {
            return;
        }

        double totalThreat = 0;
        for (OpponentConfig opponent : game.getOpponents()) {
            totalThreat += opponent.getThreatLevel();
        }

        if (totalThreat > diff.getMaxThreat()) {
            warning("Total threat level (" + totalThreat + 
                    ") exceeds max threat (" + diff.getMaxThreat() + ")",
                    MazeDslPackage.Literals.GAME_CONFIGURATION__OPPONENTS);
        }
    }
}
