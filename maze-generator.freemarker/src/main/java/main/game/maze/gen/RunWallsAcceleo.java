// /maze-generator.acceleo/src/main/java/main/game/maze/gen/RunWallsAcceleo.java
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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import main.game.maze.walls.WallMaterial;
import main.game.maze.walls.WallMaterialBaseType;
import main.game.maze.walls.WallModel;
import main.game.maze.walls.WallsPackage;

/**
 * FreeMarker-based walls code generator - true template-driven MDD.
 * 
 * Templates are loaded from src/main/resources/templates/walls/ and
 * the EMF model is transformed into a template data model for processing.
 */
public class RunWallsAcceleo {

    private static final Logger LOGGER = Logger.getLogger(RunWallsAcceleo.class.getName());

    // Default values for null-safety
    private static final WallMaterialBaseType DEFAULT_BASE_TYPE = WallMaterialBaseType.STEEL;
    private static final String DEFAULT_BASE_IMAGE = "/images/walls/default_wall.png";
    private static final String DEFAULT_DISPLAY_NAME = "Unknown Wall";

    private final Configuration freemarkerConfig;

    public RunWallsAcceleo() {
        // Configure FreeMarker
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);
        freemarkerConfig.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates/walls");
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerConfig.setLogTemplateExceptions(false);
        freemarkerConfig.setWrapUncheckedExceptions(true);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            LOGGER.severe("Usage: RunWallsAcceleo <wallsModel.xmi> <outDir>");
            System.exit(1);
        }

        String wallsModelPath = new File(args[0]).getAbsolutePath();
        String outDir = new File(args[1]).getAbsolutePath();

        new RunWallsAcceleo().run(wallsModelPath, outDir);
    }

    public void run(String wallsModelPath, String outDir) throws Exception {

        // 1. ResourceSet + XMI-factory for .xmi
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry()
          .getExtensionToFactoryMap()
          .put("xmi", new XMIResourceFactoryImpl());

        // 2. Register EPackage for walls model
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                WallsPackage.eNS_URI,
                WallsPackage.eINSTANCE
        );

        // 3. Load walls model
        File modelFile = new File(wallsModelPath);
        URI modelURI = URI.createFileURI(modelFile.getAbsolutePath());
        Resource modelRes = rs.getResource(modelURI, true);
        EObject root = modelRes.getContents().get(0);

        LOGGER.fine("Walls model root: " + root.eClass().getName()
                + " from " + root.eClass().getEPackage().getNsURI());

        // 4. Create output folder
        File outFolder = new File(outDir, "main/game/maze/generated");
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        LOGGER.info("Generating walls code into: " + outFolder.getAbsolutePath());

        // 5. Generate code using FreeMarker templates
        if (root instanceof WallModel wallModel) {
            validateWallModel(wallModel);
            Map<String, Object> dataModel = buildTemplateDataModel(wallModel);
            
            generateFromTemplate("WallRegistry.ftl", dataModel, new File(outFolder, "WallRegistry.java"));
            generateFromTemplate("WallMaterialRenderer.ftl", dataModel, new File(outFolder, "WallMaterialRenderer.java"));
            generateFromTemplate("WallCollisionHandler.ftl", dataModel, new File(outFolder, "WallCollisionHandler.java"));
            
            LOGGER.info("Walls generation done.");
        } else {
            throw new IllegalArgumentException("Expected WallModel, got: " + root.eClass().getName());
        }
    }

    /**
     * Builds the FreeMarker data model from the EMF WallModel.
     * This transforms the EMF structure into a template-friendly Map structure.
     */
    private Map<String, Object> buildTemplateDataModel(WallModel emfModel) {
        Map<String, Object> model = new HashMap<>();
        
        // Materials from the model
        List<Map<String, Object>> materials = new ArrayList<>();
        
        for (WallMaterial m : emfModel.getMaterials()) {
            Map<String, Object> materialData = new HashMap<>();
            materialData.put("id", m.getId());
            materialData.put("displayName", nullSafe(m.getDisplayName(), DEFAULT_DISPLAY_NAME));
            WallMaterialBaseType baseType = m.getWallBaseType() != null ? m.getWallBaseType() : DEFAULT_BASE_TYPE;
            materialData.put("baseType", baseType.toString());
            materialData.put("breakable", m.isBreakable());
            materialData.put("hitPoints", m.getHitPoints());
            materialData.put("baseImage", nullSafe(m.getBaseImage(), DEFAULT_BASE_IMAGE));
            
            materials.add(materialData);
        }
        
        model.put("materials", materials);
        
        // Base types with their rendering/collision properties
        List<Map<String, Object>> baseTypes = new ArrayList<>();
        baseTypes.add(createBaseTypeData("GLASS", 200, 230, 255, 0.6, 2.0, 0, "wall_glass_shatter", "wall_glass_crack"));
        baseTypes.add(createBaseTypeData("DIRT", 139, 90, 43, 1.0, 1.5, 1, "wall_dirt_crumble", "wall_dirt_thud"));
        baseTypes.add(createBaseTypeData("WOOD", 160, 82, 45, 1.0, 1.0, 2, "wall_wood_splinter", "wall_wood_knock"));
        baseTypes.add(createBaseTypeData("STONE", 128, 128, 128, 1.0, 0.75, 3, "wall_stone_crack", "wall_stone_chip"));
        baseTypes.add(createBaseTypeData("STEEL", 70, 70, 90, 1.0, 0.5, 5, "wall_steel_dent", "wall_steel_clang"));
        
        model.put("baseTypes", baseTypes);
        
        return model;
    }

    private Map<String, Object> createBaseTypeData(String name, int r, int g, int b, double alpha,
                                                    double damageMultiplier, int resistance,
                                                    String breakSound, String hitSound) {
        Map<String, Object> bt = new HashMap<>();
        bt.put("name", name);
        bt.put("colorR", r);
        bt.put("colorG", g);
        bt.put("colorB", b);
        bt.put("alpha", alpha);
        bt.put("damageMultiplier", damageMultiplier);
        bt.put("resistance", resistance);
        bt.put("breakSound", breakSound);
        bt.put("hitSound", hitSound);
        return bt;
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
     * Validates the wall model and reports any issues.
     */
    private void validateWallModel(WallModel model) {
        if (model.getMaterials() == null || model.getMaterials().isEmpty()) {
            throw new IllegalStateException("Wall model has no materials defined. " +
                "At least one WallMaterial is required.");
        }
        
        StringBuilder warnings = new StringBuilder();
        int index = 0;
        for (WallMaterial m : model.getMaterials()) {
            String id = m.getId();
            if (id == null || id.isBlank()) {
                throw new IllegalStateException(String.format(
                    "WallMaterial at index %d has null or blank 'id'. " +
                    "Every material must have a unique id.", index));
            }
            
            if (m.getWallBaseType() == null) {
                warnings.append(String.format(
                    "  WARNING: Material '%s' has null wallBaseType, using default: %s%n",
                    id, DEFAULT_BASE_TYPE));
            }
            
            if (m.getBaseImage() == null || m.getBaseImage().isBlank()) {
                warnings.append(String.format(
                    "  WARNING: Material '%s' has null/blank baseImage, using default: %s%n",
                    id, DEFAULT_BASE_IMAGE));
            }
            
            if (m.getDisplayName() == null || m.getDisplayName().isBlank()) {
                warnings.append(String.format(
                    "  WARNING: Material '%s' has null/blank displayName, using default: %s%n",
                    id, DEFAULT_DISPLAY_NAME));
            }
            index++;
        }
        
        if (warnings.length() > 0) {
            LOGGER.log(Level.WARNING, "Model validation warnings:\n{0}", warnings);
        }
    }

    // ========== Utility Methods ==========

    private static String nullSafe(String value, String defaultValue) {
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }
}


