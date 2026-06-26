package main.game.maze.common.input;

/**
 * Logical game actions produced from key bindings.
 * Frontend-neutral; both libGDX and JavaFX frontends consume this enum.
 */
public enum GameAction {
    RETURN_TO_MENU,
    TOGGLE_TERMINAL,
    OPEN_HIGH_SCORES,
    TOGGLE_SPANNING_TREE,
    APPLY_PATH_HINT,
    FLAME_ATTACK,
    MOVE_PLAYER
}
