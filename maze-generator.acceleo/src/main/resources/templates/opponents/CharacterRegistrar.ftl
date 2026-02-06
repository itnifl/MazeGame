package main.game.maze.generated;

import java.util.logging.Logger;
import main.game.maze.opponents.*;

/**
 * Generated character registrar that dispatches to type-specific handlers.
 * @generated from opponents.ecore via FreeMarker template
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
<#list model.enemyTypes as type>
            RegistrationHandler<${type}> ${type?uncap_first}Handler<#sep>,</#sep></#list>) {
        if (character == null) {
            LOGGER.warning("Attempted to register null character");
            return;
        }
        String typeName = character.eClass().getName();
        switch (typeName) {
<#list model.enemyTypes as type>
            case "${type}" -> { if (${type?uncap_first}Handler != null) ${type?uncap_first}Handler.register((${type}) character); }
</#list>
            default -> LOGGER.warning("Unknown character type: " + typeName);
        }
    }

    public static String[] getKnownTypes() {
        return new String[] { <#list model.enemyTypes as type>"${type}"<#sep>, </#sep></#list> };
    }

    public static boolean isKnownType(String typeName) {
        return <#list model.enemyTypes as type>"${type}".equals(typeName)<#sep> || </#sep></#list>;
    }
}
