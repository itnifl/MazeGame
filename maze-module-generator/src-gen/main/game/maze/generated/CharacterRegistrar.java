package main.game.maze.generated;

import java.util.logging.Logger;
import main.game.maze.opponents.*;

/**
 * Generated character registrar that dispatches to type-specific handlers.
 * @generated from opponents.ecore
 */
public final class CharacterRegistrar {

    private static final Logger LOGGER = Logger.getLogger(CharacterRegistrar.class.getName());

    private CharacterRegistrar() { }

    @FunctionalInterface
    public interface RegistrationHandler<T extends CharacterType> {
        void register(T character);
    }

    public static void register(
            CharacterType character,
            RegistrationHandler<Zombie> zombieHandler,
            RegistrationHandler<Ghost> ghostHandler,
            RegistrationHandler<PumpkinBomber> pumpkinBomberHandler) {
        if (character == null) {
            LOGGER.warning("Attempted to register null character");
            return;
        }
        String typeName = character.eClass().getName();
        switch (typeName) {
            case "Zombie" -> { if (zombieHandler != null) zombieHandler.register((Zombie) character); }
            case "Ghost" -> { if (ghostHandler != null) ghostHandler.register((Ghost) character); }
            case "PumpkinBomber" -> { if (pumpkinBomberHandler != null) pumpkinBomberHandler.register((PumpkinBomber) character); }
            default -> LOGGER.warning("Unknown character type: " + typeName);
        }
    }

    public static String[] getKnownTypes() {
        return new String[] { "Zombie", "Ghost", "PumpkinBomber" };
    }

    public static boolean isKnownType(String typeName) {
        return "Zombie".equals(typeName) || "Ghost".equals(typeName) || "PumpkinBomber".equals(typeName);
    }
}
