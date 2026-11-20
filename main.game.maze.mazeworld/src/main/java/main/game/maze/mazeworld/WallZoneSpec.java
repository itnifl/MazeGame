package main.game.maze.mazeworld;

import java.util.List;

/**
 * How the walls of one zone are made: which share of them can be broken and from which materials.
 *
 * @param breakableShare share of the zone's walls that can be broken, 0.0 (all solid) to 1.0 (all breakable)
 * @param materials      materials of the breakable walls (never empty)
 */
public record WallZoneSpec(double breakableShare, List<WallMaterialSpec> materials) {

    public WallZoneSpec {
        if (breakableShare < 0.0 || breakableShare > 1.0) {
            throw new IllegalArgumentException("breakableShare must be between 0 and 1");
        }
        if (materials == null || materials.isEmpty()) {
            throw new IllegalArgumentException("a zone needs at least one wall material");
        }
        materials = List.copyOf(materials);
    }
}
