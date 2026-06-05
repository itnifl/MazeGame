/*
 * MazeGame DSL - Code Generator
 * 
 * This class generates Java code and XMI model instances from MazeDsl files.
 */
package main.game.maze.dsl.generator;

import java.util.Locale;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import main.game.maze.dsl.mazeDsl.*;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
public class MazeDslGenerator extends AbstractGenerator {

    @Override
    public void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
        for (var content : resource.getContents()) {
            if (content instanceof GameConfiguration) {
                GameConfiguration game = (GameConfiguration) content;
                String baseName = toFileName(game.getName());
                
                // Generate Java factory class
                fsa.generateFile(
                    "main/game/maze/generated/" + toClassName(game.getName()) + "Factory.java",
                    generateFactoryClass(game)
                );
                
                // Generate XMI model instance
                fsa.generateFile(
                    "xmi/" + baseName + "-config.xmi",
                    generateXmiInstance(game)
                );
                
                // Generate difficulty configuration
                if (game.getDifficulty() != null) {
                    fsa.generateFile(
                        "xmi/" + baseName + "-difficulty.xmi",
                        generateDifficultyXmi(game)
                    );
                }
            }
        }
    }

    /**
     * Generates a Java factory class for creating game elements.
     */
    private CharSequence generateFactoryClass(GameConfiguration game) {
        StringBuilder sb = new StringBuilder();
        String className = toClassName(game.getName()) + "Factory";
        
        sb.append("/*\n");
        sb.append(" * Auto-generated from " + game.getName() + ".mazegame\n");
        sb.append(" * DO NOT EDIT - Changes will be overwritten\n");
        sb.append(" */\n");
        sb.append("package main.game.maze.generated;\n\n");
        
        // Imports
        sb.append("import main.game.maze.opponents.*;\n");
        sb.append("import main.game.maze.opponents.impl.*;\n");
        sb.append("import main.game.maze.behaviour.*;\n");
        sb.append("import main.game.maze.behaviour.impl.*;\n");
        sb.append("import main.game.maze.difficulties.*;\n");
        sb.append("import main.game.maze.difficulties.impl.*;\n");
        sb.append("import java.util.List;\n");
        sb.append("import java.util.ArrayList;\n\n");
        
        sb.append("/**\n");
        sb.append(" * Factory class for creating game elements defined in " + game.getName() + ".\n");
        sb.append(" */\n");
        sb.append("public class " + className + " {\n\n");
        
        // Generate opponent factory methods
        for (OpponentConfig opponent : game.getOpponents()) {
            sb.append(generateOpponentMethod(opponent));
        }
        
        // Generate patrol factory methods
        for (PatrolConfig patrol : game.getPatrols()) {
            sb.append(generatePatrolMethod(patrol));
        }
        
        // Generate difficulty getter
        if (game.getDifficulty() != null) {
            sb.append(generateDifficultyMethod(game.getDifficulty()));
        }
        
        // Generate method to get all opponents
        sb.append("    /**\n");
        sb.append("     * Creates and returns all opponents defined in this configuration.\n");
        sb.append("     */\n");
        sb.append("    public static List<CharacterType> createAllOpponents() {\n");
        sb.append("        List<CharacterType> opponents = new ArrayList<>();\n");
        for (OpponentConfig opponent : game.getOpponents()) {
            sb.append("        opponents.add(create" + toClassName(opponent.getName()) + "());\n");
        }
        sb.append("        return opponents;\n");
        sb.append("    }\n\n");
        
        sb.append("}\n");
        
        return sb;
    }

    /**
     * Generates a factory method for a single opponent.
     */
    private CharSequence generateOpponentMethod(OpponentConfig opponent) {
        StringBuilder sb = new StringBuilder();
        String methodName = "create" + toClassName(opponent.getName());
        String returnType = getJavaType(opponent.getType());
        
        sb.append("    /**\n");
        sb.append("     * Creates a " + opponent.getName() + " opponent.\n");
        if (opponent.getDisplayName() != null) {
            sb.append("     * Display name: " + escapeForJavaComment(opponent.getDisplayName()) + "\n");
        }
        sb.append("     */\n");
        sb.append("    public static " + returnType + " " + methodName + "() {\n");
        sb.append("        " + returnType + " opponent = " + getFactoryCall(opponent.getType()) + ";\n");
        
        if (opponent.getDisplayName() != null) {
            sb.append("        opponent.setDisplayName(\"" + escapeForJavaString(opponent.getDisplayName()) + "\");\n");
        }
        sb.append("        opponent.setId(\"" + opponent.getName() + "\");\n");
        
        if (opponent.getHealth() != 0) {
            sb.append("        opponent.setHealth(" + opponent.getHealth() + ");\n");
        }
        if (opponent.getSpeed() != 0) {
            sb.append("        opponent.setSpeed(" + opponent.getSpeed() + ");\n");
        }
        if (opponent.getThreatLevel() != 0) {
            sb.append("        opponent.setThreatLevel(" + opponent.getThreatLevel() + ");\n");
        }
        if (opponent.getBehavior() != null) {
            sb.append("        opponent.setBehavior(BehaviorType." + toEnumLiteral(opponent.getBehavior().getName()) + ");\n");
        }
        
        // Character-specific settings
        CharacterSpecifics specifics = opponent.getCharacterSpecifics();
        if (specifics instanceof ZombieSpecifics) {
            ZombieSpecifics zs = (ZombieSpecifics) specifics;
            if (zs.getAttackDamage() != 0) {
                sb.append("        opponent.setAttackDamage(" + zs.getAttackDamage() + ");\n");
            }
            if (zs.getInfectionLevel() != 0) {
                sb.append("        opponent.setInfectionLevel(" + zs.getInfectionLevel() + ");\n");
            }
            if (zs.getResurrectionTime() != 0) {
                sb.append("        opponent.setResurrectionTime(" + zs.getResurrectionTime() + ");\n");
            }
        } else if (specifics instanceof GhostSpecifics) {
            GhostSpecifics gs = (GhostSpecifics) specifics;
            if (gs.getAttackDamage() != 0) {
                sb.append("        opponent.setAttackDamage(" + gs.getAttackDamage() + ");\n");
            }
            if (gs.getVisibilityLevel() != 0) {
                sb.append("        opponent.setVisibilityLevel(" + gs.getVisibilityLevel() + ");\n");
            }
            if (gs.getNonTangibilityEnergy() != 0) {
                sb.append("        opponent.setNonTangibilityEnergy(" + gs.getNonTangibilityEnergy() + ");\n");
            }
        } else if (specifics instanceof RangedSpecifics) {
            RangedSpecifics rs = (RangedSpecifics) specifics;
            if (rs.getAttackRange() != 0) {
                sb.append("        opponent.setAttackRange(" + rs.getAttackRange() + ");\n");
            }
            if (rs.getAttackCooldown() != 0) {
                sb.append("        opponent.setAttackCooldownMs(" + rs.getAttackCooldown() + ");\n");
            }
            if (rs.getAttackDamage() != 0) {
                sb.append("        opponent.setAttackDamage(" + rs.getAttackDamage() + ");\n");
            }
            if (rs.getProjectileSpeed() != 0) {
                sb.append("        opponent.setProjectileSpeed(" + rs.getProjectileSpeed() + ");\n");
            }
            if (rs.getProjectileType() != null) {
                sb.append("        opponent.setProjectileType(ProjectileType." + toEnumLiteral(rs.getProjectileType().getName()) + ");\n");
            }
            if (rs.getSplashRadius() != 0) {
                sb.append("        opponent.setSplashRadius(" + rs.getSplashRadius() + ");\n");
            }
        }
        
        sb.append("        return opponent;\n");
        sb.append("    }\n\n");
        
        return sb;
    }

    /**
     * Generates a factory method for a patrol configuration.
     */
    private CharSequence generatePatrolMethod(PatrolConfig patrol) {
        StringBuilder sb = new StringBuilder();
        String methodName = "create" + toClassName(patrol.getName()) + "Patrol";
        
        sb.append("    /**\n");
        sb.append("     * Creates the patrol behavior: " + patrol.getName() + "\n");
        sb.append("     */\n");
        sb.append("    public static PatrolBehavior " + methodName + "() {\n");
        sb.append("        PatrolBehavior patrol = BehaviourFactory.eINSTANCE.createPatrolBehavior();\n");
        
        if (patrol.getVisionRange() != 0) {
            sb.append("        patrol.setBaseVisionRange(" + patrol.getVisionRange() + ");\n");
        }
        
        sb.append("        \n");
        sb.append("        // Add waypoints\n");
        for (Waypoint wp : patrol.getWaypoints()) {
            sb.append("        {\n");
            sb.append("            PatrolPoint point = BehaviourFactory.eINSTANCE.createPatrolPoint();\n");
            sb.append("            Position pos = BehaviourFactory.eINSTANCE.createPosition();\n");
            sb.append("            pos.setPosX(" + wp.getX() + ");\n");
            sb.append("            pos.setPosY(" + wp.getY() + ");\n");
            sb.append("            point.setPoint(pos);\n");
            if (wp.getWaitTime() != 0) {
                sb.append("            point.setWaitTime(" + wp.getWaitTime() + ");\n");
            }
            sb.append("            patrol.getPath().add(point);\n");
            sb.append("        }\n");
        }
        
        sb.append("        return patrol;\n");
        sb.append("    }\n\n");
        
        return sb;
    }

    /**
     * Generates a method for creating the difficulty settings.
     */
    private CharSequence generateDifficultyMethod(DifficultyConfig difficulty) {
        StringBuilder sb = new StringBuilder();
        String difficultyClass = getDifficultyClass(difficulty.getLevel());
        
        sb.append("    /**\n");
        sb.append("     * Creates the difficulty configuration.\n");
        sb.append("     */\n");
        sb.append("    public static Difficulty createDifficulty() {\n");
        sb.append("        " + difficultyClass + " difficulty = DifficultiesFactory.eINSTANCE.create" + difficultyClass + "();\n");
        
        if (difficulty.isInstantDeath()) {
            sb.append("        difficulty.setInstantDeath(true);\n");
        }
        if (difficulty.getSpeedMultiplier() != 0) {
            sb.append("        difficulty.setMonstersMovementSpeedMultiplier(" + difficulty.getSpeedMultiplier() + ");\n");
        }
        if (difficulty.getDamageMultiplier() != 0) {
            sb.append("        difficulty.setMonstersDamageMultiplier(" + difficulty.getDamageMultiplier() + ");\n");
        }
        if (difficulty.getMaxThreat() != 0) {
            sb.append("        difficulty.setMaxThreat(" + difficulty.getMaxThreat() + ");\n");
        }
        
        // Enemy limits
        for (EnemyLimit limit : difficulty.getEnemyLimits()) {
            sb.append("        {\n");
            sb.append("            EnemyMaxCount count = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();\n");
            sb.append("            count.setType(EnemyTypes." + toEnumLiteral(limit.getType().getName()) + ");\n");
            sb.append("            count.setMaxCount(" + limit.getMaxCount() + ");\n");
            sb.append("            difficulty.getEnemyMaxCount().add(count);\n");
            sb.append("        }\n");
        }
        
        sb.append("        return difficulty;\n");
        sb.append("    }\n\n");
        
        return sb;
    }

    /**
     * Generates XMI instance for the opponents model.
     */
    private CharSequence generateXmiInstance(GameConfiguration game) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!-- Auto-generated from " + game.getName() + ".mazegame -->\n");
        sb.append("<opp:OpponentModel xmi:version=\"2.0\"\n");
        sb.append("    xmlns:xmi=\"http://www.omg.org/XMI\"\n");
        sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
        sb.append("    xmlns:opp=\"http://main.game.maze/opponents\"\n");
        sb.append("    name=\"" + escapeForXmlAttribute(game.getName()) + "\">\n");
        
        for (OpponentConfig opponent : game.getOpponents()) {
            String xsiType = getXsiType(opponent.getType());
            sb.append("  <characterTypes xsi:type=\"opp:" + xsiType + "\"\n");
            sb.append("      id=\"" + escapeForXmlAttribute(opponent.getName()) + "\"");
            
            if (opponent.getDisplayName() != null) {
                sb.append("\n      displayName=\"" + escapeForXmlAttribute(opponent.getDisplayName()) + "\"");
            }
            if (opponent.getHealth() != 0) {
                sb.append("\n      health=\"" + opponent.getHealth() + "\"");
            }
            if (opponent.getSpeed() != 0) {
                sb.append("\n      speed=\"" + opponent.getSpeed() + "\"");
            }
            if (opponent.getThreatLevel() != 0) {
                sb.append("\n      threatLevel=\"" + opponent.getThreatLevel() + "\"");
            }
            if (opponent.getBehavior() != null) {
                sb.append("\n      behavior=\"" + toEnumLiteral(opponent.getBehavior().getName()) + "\"");
            }
            
            sb.append("/>\n");
        }
        
        sb.append("</opp:OpponentModel>\n");
        
        return sb;
    }

    /**
     * Generates XMI instance for the difficulty settings.
     */
    private CharSequence generateDifficultyXmi(GameConfiguration game) {
        DifficultyConfig diff = game.getDifficulty();
        StringBuilder sb = new StringBuilder();
        
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!-- Auto-generated from " + game.getName() + ".mazegame -->\n");
        sb.append("<diff:DifficultyGameData xmi:version=\"2.0\"\n");
        sb.append("    xmlns:xmi=\"http://www.omg.org/XMI\"\n");
        sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
        sb.append("    xmlns:diff=\"http://main.game.maze/difficulties\">\n");
        
        String diffType = getDifficultyXsiType(diff.getLevel());
        sb.append("  <difficulties xsi:type=\"diff:" + diffType + "\"");
        
        if (diff.isInstantDeath()) {
            sb.append("\n      instantDeath=\"true\"");
        }
        if (diff.getSpeedMultiplier() != 0) {
            sb.append("\n      monstersMovementSpeedMultiplier=\"" + diff.getSpeedMultiplier() + "\"");
        }
        if (diff.getDamageMultiplier() != 0) {
            sb.append("\n      monstersDamageMultiplier=\"" + diff.getDamageMultiplier() + "\"");
        }
        if (diff.getMaxThreat() != 0) {
            sb.append("\n      maxThreat=\"" + diff.getMaxThreat() + "\"");
        }
        
        if (diff.getEnemyLimits().isEmpty()) {
            sb.append("/>\n");
        } else {
            sb.append(">\n");
            for (EnemyLimit limit : diff.getEnemyLimits()) {
                sb.append("    <enemyMaxCount type=\"" + toEnumLiteral(limit.getType().getName()) + "\" ");
                sb.append("maxCount=\"" + limit.getMaxCount() + "\"/>\n");
            }
            sb.append("  </difficulties>\n");
        }
        
        sb.append("</diff:DifficultyGameData>\n");
        
        return sb;
    }

    // Helper methods
    
    private String toClassName(String name) {
        if (name == null || name.isEmpty()) return "Unknown";
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String toFileName(String name) {
        return toClassName(name).toLowerCase(Locale.ROOT);
    }

    private String toEnumLiteral(String name) {
        return name.toUpperCase(Locale.ROOT);
    }

    private String getJavaType(CharacterTypeEnum type) {
        switch (type) {
            case ZOMBIE: return "Zombie";
            case GHOST: return "Ghost";
            case PUMPKINBOMBER: return "PumpkinBomber";
            default: return "CharacterType";
        }
    }

    private String getFactoryCall(CharacterTypeEnum type) {
        switch (type) {
            case ZOMBIE: return "OpponentsFactory.eINSTANCE.createZombie()";
            case GHOST: return "OpponentsFactory.eINSTANCE.createGhost()";
            case PUMPKINBOMBER: return "OpponentsFactory.eINSTANCE.createPumpkinBomber()";
            default: return "null";
        }
    }

    private String getXsiType(CharacterTypeEnum type) {
        switch (type) {
            case ZOMBIE: return "Zombie";
            case GHOST: return "Ghost";
            case PUMPKINBOMBER: return "PumpkinBomber";
            default: return "CharacterType";
        }
    }

    private String getDifficultyClass(DifficultyLevel level) {
        return getDifficultyTypeName(level);
    }

    private String getDifficultyXsiType(DifficultyLevel level) {
        return getDifficultyTypeName(level);
    }

    private String getDifficultyTypeName(DifficultyLevel level) {
        switch (level) {
            case EASY: return "EasyDifficulty";
            case NORMAL: return "NormalDifficulty";
            case HARD: return "HardDifficulty";
            default: return "NormalDifficulty";
        }
    }

    private String escapeForJavaString(String value) {
        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    private String escapeForJavaComment(String value) {
        return value.replace("*/", "*&#47;");
    }

    private String escapeForXmlAttribute(String value) {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;");
    }
}


