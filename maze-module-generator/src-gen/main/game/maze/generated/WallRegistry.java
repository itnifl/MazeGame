package main.game.maze.generated;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.game.maze.walls.WallMaterialBaseType;

public final class WallRegistry {

    public static final class WallDefinition {
        public final String id;
        public final String displayName;
        public final WallMaterialBaseType baseType;
        public final boolean breakable;
        public final int hitPoints;
        public final String baseImage;

        public WallDefinition(String id,
                              String displayName,
                              WallMaterialBaseType baseType,
                              boolean breakable,
                              int hitPoints,
                              String baseImage) {
            this.id = id;
            this.displayName = displayName;
            this.baseType = baseType;
            this.breakable = breakable;
            this.hitPoints = hitPoints;
            this.baseImage = baseImage;
        }
    }

    private static final Map<String, WallDefinition> BY_ID = new HashMap<String, WallDefinition>();

    static {
        register(new WallDefinition(
            "DIRT_BASIC",
            "Dirt",
         WallMaterialBaseType.DIRT
,
            true,
            10,
            "/main/game/maze/dirtWall.png"
        ));
        register(new WallDefinition(
            "WOOD_BASIC",
            "Wood",
         WallMaterialBaseType.WOOD
,
            true,
            20,
            "/main/game/maze/woodWall.png"
        ));
        register(new WallDefinition(
            "STEEL_SOLID",
            "Steel",
        WallMaterialBaseType.STEEL
,
            false,
            0,
            "/main/game/maze/steelWall.png"
        ));
    }

    private static void register(WallDefinition def) {
        BY_ID.put(def.id, def);
    }

    public static WallDefinition get(String id) {
        return BY_ID.get(id);
    }

    public static Map<String, WallDefinition> all() {
        return Collections.unmodifiableMap(BY_ID);
    }

    private WallRegistry() {
    }
}
