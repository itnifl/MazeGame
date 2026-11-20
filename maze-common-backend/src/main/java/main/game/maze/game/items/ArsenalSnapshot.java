package main.game.maze.game.items;

import java.util.Map;

/** What a {@link PlayerArsenal} held at one moment: the stock of every weapon and the one in hand. */
public record ArsenalSnapshot(Map<Weapon, Integer> counts, Weapon selected) {

    public ArsenalSnapshot {
        counts = Map.copyOf(counts);
    }
}
