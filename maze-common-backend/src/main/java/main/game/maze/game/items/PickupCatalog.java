package main.game.maze.game.items;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * The pickup rules and items of the game, loaded from the items model
 * ({@link PickupCatalogLoader}). Shared by both frontends.
 *
 * @param spawnIntervalSeconds  a new item spawns this often while below the cap
 * @param itemSize              drawn item size in px (the model's {@code itemSizeFactor} x the player size);
 *                              also the touch and spacing size
 * @param minDistanceFromPlayer items never spawn closer than this to the player
 * @param caps                  item caps by board width, from the model
 * @param specs                 every item kind with its values
 */
public record PickupCatalog(double spawnIntervalSeconds, double itemSize, double minDistanceFromPlayer,
                            List<Cap> caps, Map<PickupKind, PickupSpec> specs) {

    /** At most {@code maxItems} on boards at least {@code minBoardWidth} wide. */
    public record Cap(int minBoardWidth, int maxItems) {
    }

    public PickupCatalog {
        caps = List.copyOf(caps);
        specs = Collections.unmodifiableMap(new EnumMap<>(specs));
    }

    /** Item radius for touching and spacing: half the item size. */
    public double pickupRadius() {
        return itemSize / 2;
    }

    /** How far an item's centre keeps from every wall's centre line: half an item plus half a wall, plus 1 px. */
    public double spawnClearance() {
        return itemSize / 2 + StageConstants.WallThicknessPx / 2.0 + 1;
    }

    public PickupSpec spec(PickupKind kind) {
        PickupSpec spec = specs.get(kind);
        if (spec == null) {
            throw new IllegalArgumentException("no pickup defined for " + kind);
        }
        return spec;
    }

    /** Item cap for a board of this width: the widest cap whose minimum the board reaches. */
    public int maxItemsForBoardWidth(int boardWidth) {
        Cap best = null;
        for (Cap cap : caps) {
            if (boardWidth >= cap.minBoardWidth() && (best == null || cap.minBoardWidth() > best.minBoardWidth())) {
                best = cap;
            }
        }
        return best == null ? 0 : Math.max(0, best.maxItems());
    }

    /** A random kind, weighted by {@link PickupSpec#spawnWeight()}. */
    public PickupKind randomKind(Random random) {
        int total = 0;
        for (PickupSpec spec : specs.values()) {
            total += Math.max(0, spec.spawnWeight());
        }
        if (total <= 0) {
            throw new IllegalStateException("no pickup has a positive spawn weight");
        }
        int roll = random.nextInt(total);
        for (PickupSpec spec : specs.values()) {
            roll -= Math.max(0, spec.spawnWeight());
            if (roll < 0) {
                return spec.kind();
            }
        }
        throw new IllegalStateException("unreachable");
    }
}
