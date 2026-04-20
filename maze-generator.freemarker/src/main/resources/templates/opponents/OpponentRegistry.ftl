package main.game.maze.generated;

import java.util.logging.Logger;

/**
 * Generated opponent registry with all character types.
 * @generated from opponents.ecore via FreeMarker template
 */
public class OpponentRegistry {
    private static final Logger LOGGER = Logger.getLogger(OpponentRegistry.class.getName());
    public static final String GAME_NAME = "${model.gameName?j_string}";

    public static void listEnemies() {
<#list model.enemies as enemy>
        LOGGER.info("Enemy: ${enemy.displayName?j_string} (Health: ${enemy.health?c})");
</#list>
    }

    public static int getEnemyTypeCount() {
        return ${model.enemies?size};
    }

    public static String[] getEnemyTypeNames() {
        return new String[] { <#list model.enemies as enemy>"${enemy.displayName?j_string}"<#sep>, </#sep></#list> };
    }
}
