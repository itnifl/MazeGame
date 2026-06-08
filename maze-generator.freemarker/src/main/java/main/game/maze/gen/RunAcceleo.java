package main.game.maze.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.difficulties.DifficultiesPackage;

/**
 * FreeMarker-based opponents code generator - true template-driven MDD.
 * 
 * Templates are loaded from src/main/resources/templates/opponents/ and
 * the EMF model is transformed into a template data model for processing.
 */
public class RunAcceleo {

    private static final Logger LOGGER = Logger.getLogger(RunAcceleo.class.getName());

    // Default values for null-safety
    private static final String DEFAULT_GAME_NAME = "MazeGame";
    private static final String DEFAULT_DISPLAY_NAME = "Unknown Enemy";
    private static final String DEFAULT_IMAGE_BASE = "/images/default_enemy.png";

    private final Configuration freemarkerConfig;

    public RunAcceleo() {
        // Configure FreeMarker
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);
        freemarkerConfig.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates/opponents");
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerConfig.setLogTemplateExceptions(false);
        freemarkerConfig.setWrapUncheckedExceptions(true);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            LOGGER.severe("Usage: RunAcceleo <opponentModel.xmi> <difficulties.xmi> <outDir>");
            System.exit(1);
        }

        String opponentModelPath = new File(args[0]).getAbsolutePath();
        String difficultiesPath = new File(args[1]).getAbsolutePath();
        String outDir = new File(args[2]).getAbsolutePath();

        new RunAcceleo().run(opponentModelPath, difficultiesPath, outDir);
    }

    public void run(String opponentModelPath, String difficultiesPath, String outDir) throws Exception {

        // 1. Register XMI Factory
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        // 2. Register EPackages
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                DifficultiesPackage.eNS_URI,
                DifficultiesPackage.eINSTANCE
        );
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                OpponentsPackage.eNS_URI,
                OpponentsPackage.eINSTANCE
        );

        // 3. Load Resources
        rs.getResource(URI.createFileURI(new File(difficultiesPath).getAbsolutePath()), true);
        Resource oppRes = rs.getResource(URI.createFileURI(new File(opponentModelPath).getAbsolutePath()), true);
        EObject root = oppRes.getContents().get(0);

        // 4. Create output folder
        File outFolder = new File(outDir, "main/game/maze/generated");
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        // 5. Generate code using FreeMarker templates
        if (!"OpponentModel".equals(root.eClass().getName())) {
            throw new IllegalArgumentException("Expected OpponentModel, got: " + root.eClass().getName());
        }

        validateOpponentModel(root);
        Map<String, Object> dataModel = buildTemplateDataModel(root);

        generateFromTemplate("OpponentRegistry.ftl", dataModel, new File(outFolder, "OpponentRegistry.java"));
        generateFromTemplate("CharacterRegistrar.ftl", dataModel, new File(outFolder, "CharacterRegistrar.java"));
        generateFromTemplate("CharacterAttributeSetter.ftl", dataModel, new File(outFolder, "CharacterAttributeSetter.java"));
        generateFromTemplate("CharacterGraphicsFactory.ftl", dataModel, new File(outFolder, "CharacterGraphicsFactory.java"));

        LOGGER.info("Opponent generation complete. Output in: " + outFolder.getAbsolutePath());
    }

    /**
     * Builds the FreeMarker data model from the EMF OpponentModel.
     * This transforms the EMF structure into a template-friendly Map structure.
     */
    private Map<String, Object> buildTemplateDataModel(EObject emfModel) {
        Map<String, Object> model = new HashMap<>();
        
        // Game name
        String gameName = stringFeature(emfModel, "name");
        model.put("gameName", (gameName != null && !gameName.isBlank()) ? gameName : DEFAULT_GAME_NAME);
        
        // Enemy types (unique class names)
        List<String> enemyTypes = new ArrayList<>();
        
        // Enemies with their attributes (all instances)
        List<Map<String, Object>> enemies = new ArrayList<>();
        
        // Unique enemies (one per type, for generating type-specific methods)
        List<Map<String, Object>> uniqueEnemies = new ArrayList<>();
        java.util.Set<String> seenTypes = new java.util.HashSet<>();
        
        for (EObject enemy : listFeature(emfModel, "characterTypes")) {
            String typeName = enemy.eClass().getName();
            if (!enemyTypes.contains(typeName)) {
                enemyTypes.add(typeName);
            }
            
            Map<String, Object> enemyData = new HashMap<>();
            enemyData.put("type", typeName);
            enemyData.put("displayName", nullSafe(stringFeature(enemy, "displayName"), DEFAULT_DISPLAY_NAME));
            enemyData.put("health", numberFeature(enemy, "health", Integer.valueOf(0)).intValue());
            enemyData.put("threatLevel", numberFeature(enemy, "threatLevel", Double.valueOf(0.0)).doubleValue());
            enemyData.put("imageBase", nullSafe(stringFeature(enemy, "ImageBase"), DEFAULT_IMAGE_BASE));
            enemyData.put("defaultImage", getDefaultImage(typeName));
            enemyData.put("animationFrames", getAnimationFrames(typeName));
            enemyData.put("spriteScale", getSpriteScale(typeName));
            
            enemies.add(enemyData);
            
            // Track unique enemies (first occurrence of each type)
            if (!seenTypes.contains(typeName)) {
                seenTypes.add(typeName);
                uniqueEnemies.add(enemyData);
            }
        }
        
        model.put("enemyTypes", enemyTypes);
        model.put("enemies", enemies);
        model.put("uniqueEnemies", uniqueEnemies);
        
        return model;
    }

    /**
     * Processes a FreeMarker template and writes the output to a file.
     */
    private void generateFromTemplate(String templateName, Map<String, Object> dataModel, File outputFile) 
            throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        
        // Wrap the data model under "model" key for cleaner template access
        Map<String, Object> rootModel = new HashMap<>();
        rootModel.put("model", dataModel);
        
        try (Writer out = new FileWriter(outputFile)) {
            template.process(rootModel, out);
        }
        LOGGER.fine("Generated: " + outputFile.getName());
    }

    /**
     * Validates the opponent model and reports any issues.
     */
    private void validateOpponentModel(EObject model) {
        StringBuilder warnings = new StringBuilder();
        
        String modelName = stringFeature(model, "name");
        if (modelName == null || modelName.isBlank()) {
            warnings.append(String.format(
                "  WARNING: OpponentModel has null/blank 'name', using default: %s%n",
                DEFAULT_GAME_NAME));
        }
        
        List<EObject> characters = listFeature(model, "characterTypes");
        if (characters.isEmpty()) {
            LOGGER.warning("OpponentModel has no character types. Generated code will have empty registries.");
        } else {
            int index = 0;
            for (EObject enemy : characters) {
                String typeName = enemy.eClass().getName();
                
                String displayName = stringFeature(enemy, "displayName");
                if (displayName == null || displayName.isBlank()) {
                    warnings.append(String.format(
                        "  WARNING: %s at index %d has null/blank displayName, using default: %s%n",
                        typeName, index, DEFAULT_DISPLAY_NAME));
                }
                
                String imageBase = stringFeature(enemy, "ImageBase");
                if (imageBase == null || imageBase.isBlank()) {
                    warnings.append(String.format(
                        "  WARNING: %s at index %d has null/blank ImageBase, using type-specific default%n",
                        typeName, index));
                }
                index++;
            }
        }
        
        if (warnings.length() > 0) {
            LOGGER.log(Level.WARNING, "Model validation warnings:\n{0}", warnings);
        }
    }

    // ========== Utility Methods ==========

    private static String nullSafe(String value, String defaultValue) {
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    private static String stringFeature(EObject obj, String featureName) {
        Object value = featureValue(obj, featureName);
        return value instanceof String ? (String) value : null;
    }

    private static Number numberFeature(EObject obj, String featureName, Number defaultValue) {
        Object value = featureValue(obj, featureName);
        return value instanceof Number ? (Number) value : defaultValue;
    }

    @SuppressWarnings("unchecked")
    private static List<EObject> listFeature(EObject obj, String featureName) {
        Object value = featureValue(obj, featureName);
        return value instanceof List<?> ? (List<EObject>) value : List.of();
    }

    private static Object featureValue(EObject obj, String featureName) {
        if (obj == null) {
            return null;
        }
        EStructuralFeature feature = obj.eClass().getEStructuralFeature(featureName);
        if (feature == null) {
            return null;
        }
        return obj.eGet(feature);
    }

    private static String getDefaultImage(String typeName) {
        return switch (typeName) {
            case "Zombie" -> "/images/zombie_default.png";
            case "Ghost" -> "/images/ghost_default.png";
            case "PumpkinBomber" -> "/images/pumpkinbomber_default.png";
            default -> "/images/default_enemy.png";
        };
    }

    private static int getAnimationFrames(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 4;
            case "Ghost" -> 6;
            case "PumpkinBomber" -> 4;
            default -> 1;
        };
    }

    private static double getSpriteScale(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 1.0;
            case "Ghost" -> 0.8;
            case "PumpkinBomber" -> 1.2;
            default -> 1.0;
        };
    }
}
