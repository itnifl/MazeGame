package main.game.maze.libgdx.input;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.game.maze.libgdx.input.command.GameCommand;

/**
 * Registry mapping logical actions to keys and commands.
 */
public final class KeyBindingRegistry {

    public enum BindingKind {
        EDGE,
        HELD
    }

    public record KeyBinding(int keyCode, BindingKind kind) {
    }

    private final Map<GameAction, List<KeyBinding>> bindings = new EnumMap<>(GameAction.class);
    private final Map<GameAction, GameCommand> commands = new EnumMap<>(GameAction.class);
    private final List<GameAction> executionOrder = new ArrayList<>();

    public KeyBindingRegistry bind(GameAction action, int keyCode, BindingKind kind) {
        bindings.computeIfAbsent(action, ignored -> new ArrayList<>()).add(new KeyBinding(keyCode, kind));
        if (!executionOrder.contains(action)) {
            executionOrder.add(action);
        }
        return this;
    }

    public KeyBindingRegistry command(GameAction action, GameCommand command) {
        commands.put(action, command);
        if (!executionOrder.contains(action)) {
            executionOrder.add(action);
        }
        return this;
    }

    public List<GameAction> executionOrder() {
        return List.copyOf(executionOrder);
    }

    public List<KeyBinding> bindingsFor(GameAction action) {
        return List.copyOf(bindings.getOrDefault(action, List.of()));
    }

    public GameCommand commandFor(GameAction action) {
        return commands.get(action);
    }

    public Set<Integer> trackedKeyCodes() {
        Set<Integer> keys = new LinkedHashSet<>();
        for (List<KeyBinding> actionBindings : bindings.values()) {
            for (KeyBinding binding : actionBindings) {
                keys.add(binding.keyCode());
            }
        }
        return Set.copyOf(keys);
    }

    public boolean isTriggered(GameAction action, InputFrame frame) {
        for (KeyBinding binding : bindingsFor(action)) {
            boolean active = binding.kind() == BindingKind.EDGE
                    ? frame.isEdge(binding.keyCode())
                    : frame.isHeld(binding.keyCode());
            if (active) {
                return true;
            }
        }
        return false;
    }
}
