package main.game.maze.common.input;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.game.maze.common.input.command.GameCommand;

/**
 * Registry mapping logical actions to keys and commands, parameterized on the
 * physical key type {@code K}.
 *
 * @param <K> the physical key type ({@code Integer} for libGDX, {@code KeyCode} for JavaFX)
 */
public final class KeyBindingRegistry<K> {

    public enum BindingKind {
        EDGE,
        HELD
    }

    public record KeyBinding<K>(K key, BindingKind kind) {
    }

    private final Map<GameAction, List<KeyBinding<K>>> bindings = new EnumMap<>(GameAction.class);
    private final Map<GameAction, GameCommand<K>> commands = new EnumMap<>(GameAction.class);
    private final List<GameAction> executionOrder = new ArrayList<>();

    public KeyBindingRegistry<K> bind(GameAction action, K key, BindingKind kind) {
        bindings.computeIfAbsent(action, ignored -> new ArrayList<>()).add(new KeyBinding<>(key, kind));
        if (!executionOrder.contains(action)) {
            executionOrder.add(action);
        }
        return this;
    }

    public KeyBindingRegistry<K> command(GameAction action, GameCommand<K> command) {
        commands.put(action, command);
        if (!executionOrder.contains(action)) {
            executionOrder.add(action);
        }
        return this;
    }

    public List<GameAction> executionOrder() {
        return List.copyOf(executionOrder);
    }

    public List<KeyBinding<K>> bindingsFor(GameAction action) {
        return List.copyOf(bindings.getOrDefault(action, List.of()));
    }

    public GameCommand<K> commandFor(GameAction action) {
        return commands.get(action);
    }

    public Set<K> trackedKeys() {
        Set<K> keys = new LinkedHashSet<>();
        for (List<KeyBinding<K>> actionBindings : bindings.values()) {
            for (KeyBinding<K> binding : actionBindings) {
                keys.add(binding.key());
            }
        }
        return Set.copyOf(keys);
    }

    public boolean isTriggered(GameAction action, InputFrame<K> frame) {
        for (KeyBinding<K> binding : bindingsFor(action)) {
            boolean active = binding.kind() == BindingKind.EDGE
                    ? frame.isEdge(binding.key())
                    : frame.isHeld(binding.key());
            if (active) {
                return true;
            }
        }
        return false;
    }
}
