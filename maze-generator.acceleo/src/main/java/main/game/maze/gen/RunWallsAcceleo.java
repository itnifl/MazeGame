// /maze-generator.acceleo/src/main/game/maze/gen/RunWallsAcceleo.java
package main.game.maze.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import main.game.maze.walls.WallMaterial;
import main.game.maze.walls.WallModel;
import main.game.maze.walls.WallsPackage;

/**
 * Standalone walls code generator - no Acceleo 3 dependencies.
 */
public class RunWallsAcceleo {

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

        System.out.println("Walls model root: " + root.eClass().getName()
                + " from " + root.eClass().getEPackage().getNsURI());

        // 4. Create output folder
        File outFolder = new File(outDir, "main/game/maze/generated");
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        System.out.println("Generating walls code into: " + outFolder.getAbsolutePath());

        // 5. Generate WallRegistry.java directly (standalone, no Acceleo 3)
        if (root instanceof WallModel wallModel) {
            generateWallRegistry(wallModel, outFolder);
            generateWallMaterialRenderer(outFolder);
            generateWallCollisionHandler(outFolder);
            System.out.println("Walls generation done.");
        } else {
            throw new IllegalArgumentException("Expected WallModel, got: " + root.eClass().getName());
        }
    }

    private void generateWallRegistry(WallModel model, File outFolder) throws IOException {
        File outFile = new File(outFolder, "WallRegistry.java");
        try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
            pw.println("package main.game.maze.generated;");
            pw.println();
            pw.println("import main.game.maze.walls.WallMaterialBaseType;");
            pw.println("import java.util.Collections;");
            pw.println("import java.util.HashMap;");
            pw.println("import java.util.Map;");
            pw.println();
            pw.println("/**");
            pw.println(" * Generated wall registry with all wall materials.");
            pw.println(" * @generated from walls.ecore");
            pw.println(" */");
            pw.println("public final class WallRegistry {");
            pw.println();
            pw.println("    public static final class WallDefinition {");
            pw.println("        public final String id;");
            pw.println("        public final String displayName;");
            pw.println("        public final WallMaterialBaseType baseType;");
            pw.println("        public final boolean breakable;");
            pw.println("        public final int hitPoints;");
            pw.println("        public final String baseImage;");
            pw.println();
            pw.println("        public WallDefinition(String id,");
            pw.println("                              String displayName,");
            pw.println("                              WallMaterialBaseType baseType,");
            pw.println("                              boolean breakable,");
            pw.println("                              int hitPoints,");
            pw.println("                              String baseImage) {");
            pw.println("            this.id = id;");
            pw.println("            this.displayName = displayName;");
            pw.println("            this.baseType = baseType;");
            pw.println("            this.breakable = breakable;");
            pw.println("            this.hitPoints = hitPoints;");
            pw.println("            this.baseImage = baseImage;");
            pw.println("        }");
            pw.println("    }");
            pw.println();
            pw.println("    private static final Map<String, WallDefinition> BY_ID = new HashMap<String, WallDefinition>();");
            pw.println();
            pw.println("    static {");
            
            for (WallMaterial m : model.getMaterials()) {
                pw.println("        register(new WallDefinition(");
                pw.println("            \"" + m.getId() + "\",");
                pw.println("            \"" + m.getDisplayName() + "\",");
                pw.println("            WallMaterialBaseType." + m.getWallBaseType().toString() + ",");
                pw.println("            " + m.isBreakable() + ",");
                pw.println("            " + m.getHitPoints() + ",");
                pw.println("            \"" + m.getBaseImage() + "\"");
                pw.println("        ));");
            }
            
            pw.println("    }");
            pw.println();
            pw.println("    private static void register(WallDefinition def) {");
            pw.println("        BY_ID.put(def.id, def);");
            pw.println("    }");
            pw.println();
            pw.println("    public static WallDefinition get(String id) {");
            pw.println("        return BY_ID.get(id);");
            pw.println("    }");
            pw.println();
            pw.println("    public static Map<String, WallDefinition> all() {");
            pw.println("        return Collections.unmodifiableMap(BY_ID);");
            pw.println("    }");
            pw.println();
            pw.println("    public static String[] getKnownBaseTypes() {");
            pw.println("        return new String[] {");
            pw.println("            \"GLASS\", \"DIRT\", \"WOOD\", \"STONE\", \"STEEL\"");
            pw.println("        };");
            pw.println("    }");
            pw.println();
            pw.println("    public static int getMaterialCount() {");
            pw.println("        return BY_ID.size();");
            pw.println("    }");
            pw.println();
            pw.println("    private WallRegistry() {");
            pw.println("    }");
            pw.println("}");
        }
        System.out.println("  Generated: WallRegistry.java");
    }

    private void generateWallMaterialRenderer(File outFolder) throws IOException {
        File outFile = new File(outFolder, "WallMaterialRenderer.java");
        try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
            pw.println("package main.game.maze.generated;");
            pw.println();
            pw.println("import java.util.logging.Logger;");
            pw.println("import main.game.maze.walls.WallMaterial;");
            pw.println("import main.game.maze.walls.WallMaterialBaseType;");
            pw.println();
            pw.println("/**");
            pw.println(" * Generated renderer for WallMaterial instances.");
            pw.println(" * @generated from walls.ecore");
            pw.println(" */");
            pw.println("public final class WallMaterialRenderer {");
            pw.println();
            pw.println("    private static final Logger LOGGER = Logger.getLogger(WallMaterialRenderer.class.getName());");
            pw.println();
            pw.println("    private WallMaterialRenderer() { }");
            pw.println();
            pw.println("    public record Color(int r, int g, int b, double alpha) {");
            pw.println("        public Color(int r, int g, int b) { this(r, g, b, 1.0); }");
            pw.println("    }");
            pw.println();
            pw.println("    public static Color getBaseColor(WallMaterialBaseType baseType) {");
            pw.println("        if (baseType == null) return new Color(128, 128, 128);");
            pw.println("        return switch (baseType) {");
            pw.println("            case GLASS -> new Color(200, 230, 255, 0.6);");
            pw.println("            case DIRT -> new Color(139, 90, 43);");
            pw.println("            case WOOD -> new Color(160, 82, 45);");
            pw.println("            case STONE -> new Color(128, 128, 128);");
            pw.println("            case STEEL -> new Color(70, 70, 90);");
            pw.println("        };");
            pw.println("    }");
            pw.println();
            pw.println("    public static double getOpacity(WallMaterialBaseType baseType) {");
            pw.println("        if (baseType == null) return 1.0;");
            pw.println("        return switch (baseType) {");
            pw.println("            case GLASS -> 0.6;");
            pw.println("            default -> 1.0;");
            pw.println("        };");
            pw.println("    }");
            pw.println();
            pw.println("    public static boolean isTransparent(WallMaterialBaseType baseType) {");
            pw.println("        return baseType == WallMaterialBaseType.GLASS;");
            pw.println("    }");
            pw.println();
            pw.println("    public static String getBreakSound(WallMaterialBaseType baseType) {");
            pw.println("        if (baseType == null) return \"wall_generic_break\";");
            pw.println("        return switch (baseType) {");
            pw.println("            case GLASS -> \"wall_glass_shatter\";");
            pw.println("            case DIRT -> \"wall_dirt_crumble\";");
            pw.println("            case WOOD -> \"wall_wood_splinter\";");
            pw.println("            case STONE -> \"wall_stone_crack\";");
            pw.println("            case STEEL -> \"wall_steel_dent\";");
            pw.println("        };");
            pw.println("    }");
            pw.println();
            pw.println("    public static String getHitSound(WallMaterialBaseType baseType) {");
            pw.println("        if (baseType == null) return \"wall_generic_hit\";");
            pw.println("        return switch (baseType) {");
            pw.println("            case GLASS -> \"wall_glass_crack\";");
            pw.println("            case DIRT -> \"wall_dirt_thud\";");
            pw.println("            case WOOD -> \"wall_wood_knock\";");
            pw.println("            case STONE -> \"wall_stone_chip\";");
            pw.println("            case STEEL -> \"wall_steel_clang\";");
            pw.println("        };");
            pw.println("    }");
            pw.println("}");
        }
        System.out.println("  Generated: WallMaterialRenderer.java");
    }

    private void generateWallCollisionHandler(File outFolder) throws IOException {
        File outFile = new File(outFolder, "WallCollisionHandler.java");
        try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
            pw.println("package main.game.maze.generated;");
            pw.println();
            pw.println("import java.util.function.Consumer;");
            pw.println("import java.util.logging.Logger;");
            pw.println("import main.game.maze.walls.WallMaterial;");
            pw.println("import main.game.maze.walls.WallMaterialBaseType;");
            pw.println();
            pw.println("/**");
            pw.println(" * Generated collision handler for wall materials.");
            pw.println(" * @generated from walls.ecore");
            pw.println(" */");
            pw.println("public final class WallCollisionHandler {");
            pw.println();
            pw.println("    private static final Logger LOGGER = Logger.getLogger(WallCollisionHandler.class.getName());");
            pw.println();
            pw.println("    private WallCollisionHandler() { }");
            pw.println();
            pw.println("    public record CollisionResult(");
            pw.println("        boolean wallDestroyed,");
            pw.println("        int damageDealt,");
            pw.println("        int remainingHitPoints,");
            pw.println("        String soundEffect,");
            pw.println("        String particleEffect");
            pw.println("    ) {}");
            pw.println();
            pw.println("    public static int calculateEffectiveDamage(int baseDamage, WallMaterialBaseType baseType) {");
            pw.println("        if (baseType == null) return baseDamage;");
            pw.println("        double multiplier = switch (baseType) {");
            pw.println("            case GLASS -> 2.0;");
            pw.println("            case DIRT -> 1.5;");
            pw.println("            case WOOD -> 1.0;");
            pw.println("            case STONE -> 0.75;");
            pw.println("            case STEEL -> 0.5;");
            pw.println("        };");
            pw.println("        return (int) Math.ceil(baseDamage * multiplier);");
            pw.println("    }");
            pw.println();
            pw.println("    public static int getResistance(WallMaterialBaseType baseType) {");
            pw.println("        if (baseType == null) return 0;");
            pw.println("        return switch (baseType) {");
            pw.println("            case GLASS -> 0;");
            pw.println("            case DIRT -> 1;");
            pw.println("            case WOOD -> 2;");
            pw.println("            case STONE -> 3;");
            pw.println("            case STEEL -> 5;");
            pw.println("        };");
            pw.println("    }");
            pw.println("}");
        }
        System.out.println("  Generated: WallCollisionHandler.java");
    }
}
