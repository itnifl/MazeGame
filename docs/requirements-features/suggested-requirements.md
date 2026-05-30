# Suggested Requirements

## Candidate Additions

- SR-1: Add an automated cross-frontend movement parity test that replays the same enemy seeds and world snapshots for WANDER, PATROL, and AGGRESSIVE modes, and fails if one frontend reintroduces small-area movement loops that the other does not reproduce.
- SR-2: Define `/showenemypath` and `/sep` as a live runtime-path overlay requirement. The overlay shall show the exact active path buffer an enemy is currently following in AGGRESSIVE path-follow and PATROL path modes, and shall not substitute a newly computed shortest path to the player.
- SR-3: Define WANDER collision behavior explicitly. A wander enemy shall keep moving in its current direction until blocked, then choose a random passable cardinal direction, including the immediate reverse direction when that is the random result or the only passable option.
