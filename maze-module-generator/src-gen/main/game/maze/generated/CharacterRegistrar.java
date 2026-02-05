package main.game.maze.generated;

import java.util.function.Consumer;
import java.util.logging.Logger;

import main.game.maze.opponents.*;

/**
 * Generated character registrar that dispatches to type-specific handlers.
 * Eliminates instanceof chains by using switch on eClass name.
 * 
 * @generated from opponents.ecore
 */
public final class CharacterRegistrar {

    private static final Logger LOGGER = Logger.getLogger(CharacterRegistrar.class.getName());

    private CharacterRegistrar() { /* utility class */ }

    /**
     * Functional interface for type-specific registration handlers.
     */
    @FunctionalInterface
    public interface RegistrationHandler<T extends CharacterType> {
        void register(T character);
    }

    /**
     * Registers a character using type-specific handling.
     * Generated switch eliminates instanceof checks.
     * 
     * @param character the CharacterType to register
     * @param zombieHandler handler for Zombie types
     * @param ghostHandler handler for Ghost types
     * @param pumpkinBomberHandler handler for PumpkinBomber types
     */
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
            case "Zombie" -> {
                if (zombieHandler != null) {
                    zombieHandler.register((Zombie) character);
                }
            }
            case "Ghost" -> {
                if (ghostHandler != null) {
                    ghostHandler.register((Ghost) character);
                }
            }
            case "PumpkinBomber" -> {
                if (pumpkinBomberHandler != null) {
                    pumpkinBomberHandler.register((PumpkinBomber) character);
                }
            }
            default -> LOGGER.warning("Unknown character type: " + typeName);
        }
    }

    /**
     * Gets all known character type names from the model.
     * @return array of type names
     */
    public static String[] getKnownTypes() {
        return new String[] { "Zombie", "Ghost", "PumpkinBomber" };
    }

    /**
     * Checks if a type name is known.
     * @param typeName the type to check
     * @return true if the type is in the model
     */
    public static boolean isKnownType(String typeName) {
        return switch (typeName) {
            case "Zombie", "Ghost", "PumpkinBomber" -> true;
            default -> false;
        };
    }
}
