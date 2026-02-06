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

import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.difficulties.DifficultiesPackage;

/**
 * Standalone opponents/difficulties code generator - no Acceleo 3 dependencies.
 */
public class RunAcceleo {

  public static void main(String[] args) throws Exception {
    if (args.length < 3) {
        System.out.println("Usage: RunAcceleo <opponentModel.xmi> <difficulties.xmi> <outDir>");
        System.exit(1);
    }

    String opponentModelPath = new File(args[0]).getAbsolutePath();
    String difficultiesPath = new File(args[1]).getAbsolutePath();
    String outDir = new File(args[2]).getAbsolutePath();

    new RunAcceleo().run(opponentModelPath, difficultiesPath, outDir);
  }

  public void run(String opponentModelPath,
                  String difficultiesPath,
                  String outDir) throws Exception {

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

        System.out.println("DEBUG: Loaded XMI Root Object");
        System.out.println("Type: " + root.eClass().getName());
        System.out.println("Package: " + root.eClass().getEPackage().getNsURI());
        System.out.println("==========================================");

        // 4. Create output folder
        File outFolder = new File(outDir, "main/game/maze/generated");
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        // 5. Generate code directly (standalone, no Acceleo 3)
        if (root instanceof OpponentModel model) {
            generateOpponentRegistry(model, outFolder);
            generateCharacterRegistrar(model, outFolder);
            generateCharacterAttributeSetter(model, outFolder);
            generateCharacterGraphicsFactory(model, outFolder);
            System.out.println("Opponent generation complete. Output in: " + outFolder.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Expected OpponentModel, got: " + root.eClass().getName());
        }
  }

  private void generateOpponentRegistry(OpponentModel model, File outFolder) throws IOException {
      File outFile = new File(outFolder, "OpponentRegistry.java");
      try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
          pw.println("package main.game.maze.generated;");
          pw.println();
          pw.println("/**");
          pw.println(" * Generated opponent registry with all character types.");
          pw.println(" * @generated from opponents.ecore");
          pw.println(" */");
          pw.println("public class OpponentRegistry {");
          pw.println("    public static final String GAME_NAME = \"" + model.getName() + "\";");
          pw.println();
          pw.println("    public static void listEnemies() {");
          for (CharacterType enemy : model.getCharacterTypes()) {
              pw.println("        System.out.println(\"Enemy: " + enemy.getDisplayName() + " (Health: " + enemy.getHealth() + ")\");");
          }
          pw.println("    }");
          pw.println();
          pw.println("    public static int getEnemyTypeCount() {");
          pw.println("        return " + model.getCharacterTypes().size() + ";");
          pw.println("    }");
          pw.println();
          pw.println("    public static String[] getEnemyTypeNames() {");
          pw.print("        return new String[] { ");
          boolean first = true;
          for (CharacterType enemy : model.getCharacterTypes()) {
              if (!first) pw.print(", ");
              pw.print("\"" + enemy.getDisplayName() + "\"");
              first = false;
          }
          pw.println(" };");
          pw.println("    }");
          pw.println("}");
      }
      System.out.println("  Generated: OpponentRegistry.java");
  }

  private void generateCharacterRegistrar(OpponentModel model, File outFolder) throws IOException {
      File outFile = new File(outFolder, "CharacterRegistrar.java");
      try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
          pw.println("package main.game.maze.generated;");
          pw.println();
          pw.println("import java.util.logging.Logger;");
          pw.println("import main.game.maze.opponents.*;");
          pw.println();
          pw.println("/**");
          pw.println(" * Generated character registrar that dispatches to type-specific handlers.");
          pw.println(" * @generated from opponents.ecore");
          pw.println(" */");
          pw.println("public final class CharacterRegistrar {");
          pw.println();
          pw.println("    private static final Logger LOGGER = Logger.getLogger(CharacterRegistrar.class.getName());");
          pw.println();
          pw.println("    private CharacterRegistrar() { }");
          pw.println();
          pw.println("    @FunctionalInterface");
          pw.println("    public interface RegistrationHandler<T extends CharacterType> {");
          pw.println("        void register(T character);");
          pw.println("    }");
          pw.println();
          pw.println("    public static void register(");
          pw.println("            CharacterType character,");
          pw.println("            RegistrationHandler<Zombie> zombieHandler,");
          pw.println("            RegistrationHandler<Ghost> ghostHandler,");
          pw.println("            RegistrationHandler<PumpkinBomber> pumpkinBomberHandler) {");
          pw.println("        if (character == null) {");
          pw.println("            LOGGER.warning(\"Attempted to register null character\");");
          pw.println("            return;");
          pw.println("        }");
          pw.println("        String typeName = character.eClass().getName();");
          pw.println("        switch (typeName) {");
          pw.println("            case \"Zombie\" -> { if (zombieHandler != null) zombieHandler.register((Zombie) character); }");
          pw.println("            case \"Ghost\" -> { if (ghostHandler != null) ghostHandler.register((Ghost) character); }");
          pw.println("            case \"PumpkinBomber\" -> { if (pumpkinBomberHandler != null) pumpkinBomberHandler.register((PumpkinBomber) character); }");
          pw.println("            default -> LOGGER.warning(\"Unknown character type: \" + typeName);");
          pw.println("        }");
          pw.println("    }");
          pw.println();
          pw.println("    public static String[] getKnownTypes() {");
          pw.println("        return new String[] { \"Zombie\", \"Ghost\", \"PumpkinBomber\" };");
          pw.println("    }");
          pw.println();
          pw.println("    public static boolean isKnownType(String typeName) {");
          pw.println("        return \"Zombie\".equals(typeName) || \"Ghost\".equals(typeName) || \"PumpkinBomber\".equals(typeName);");
          pw.println("    }");
          pw.println("}");
      }
      System.out.println("  Generated: CharacterRegistrar.java");
  }

  private void generateCharacterAttributeSetter(OpponentModel model, File outFolder) throws IOException {
      File outFile = new File(outFolder, "CharacterAttributeSetter.java");
      try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
          pw.println("package main.game.maze.generated;");
          pw.println();
          pw.println("import java.util.logging.Logger;");
          pw.println("import main.game.maze.opponents.*;");
          pw.println();
          pw.println("/**");
          pw.println(" * Generated attribute setter for applying difficulty multipliers.");
          pw.println(" * @generated from opponents.ecore");
          pw.println(" */");
          pw.println("public final class CharacterAttributeSetter {");
          pw.println();
          pw.println("    private static final Logger LOGGER = Logger.getLogger(CharacterAttributeSetter.class.getName());");
          pw.println();
          pw.println("    private CharacterAttributeSetter() { }");
          pw.println();
          pw.println("    public static void applyDifficultyMultipliers(");
          pw.println("            CharacterType character,");
          pw.println("            double healthMultiplier,");
          pw.println("            double threatMultiplier,");
          pw.println("            double speedMultiplier) {");
          pw.println("        if (character == null) return;");
          pw.println("        String typeName = character.eClass().getName();");
          pw.println("        switch (typeName) {");
          pw.println("            case \"Zombie\" -> applyZombieMultipliers((Zombie) character, healthMultiplier, threatMultiplier, speedMultiplier);");
          pw.println("            case \"Ghost\" -> applyGhostMultipliers((Ghost) character, healthMultiplier, threatMultiplier, speedMultiplier);");
          pw.println("            case \"PumpkinBomber\" -> applyPumpkinBomberMultipliers((PumpkinBomber) character, healthMultiplier, threatMultiplier, speedMultiplier);");
          pw.println("            default -> LOGGER.warning(\"Unknown character type for multipliers: \" + typeName);");
          pw.println("        }");
          pw.println("    }");
          pw.println();
          pw.println("    private static void applyZombieMultipliers(Zombie z, double hm, double tm, double sm) {");
          pw.println("        z.setHealth((int) (z.getHealth() * hm));");
          pw.println("        z.setThreatLevel(z.getThreatLevel() * tm);");
          pw.println("        z.setSpeed(z.getSpeed() * sm);");
          pw.println("    }");
          pw.println();
          pw.println("    private static void applyGhostMultipliers(Ghost g, double hm, double tm, double sm) {");
          pw.println("        g.setHealth((int) (g.getHealth() * hm));");
          pw.println("        g.setThreatLevel(g.getThreatLevel() * tm);");
          pw.println("        g.setSpeed(g.getSpeed() * sm);");
          pw.println("    }");
          pw.println();
          pw.println("    private static void applyPumpkinBomberMultipliers(PumpkinBomber p, double hm, double tm, double sm) {");
          pw.println("        p.setHealth((int) (p.getHealth() * hm));");
          pw.println("        p.setThreatLevel(p.getThreatLevel() * tm);");
          pw.println("        p.setSpeed(p.getSpeed() * sm);");
          pw.println("    }");
          pw.println();
          pw.println("    public static int getBaseHealth(String typeName) {");
          pw.println("        return switch (typeName) {");
          pw.println("            case \"Zombie\" -> 100;");
          pw.println("            case \"Ghost\" -> 50;");
          pw.println("            case \"PumpkinBomber\" -> 75;");
          pw.println("            default -> 0;");
          pw.println("        };");
          pw.println("    }");
          pw.println();
          pw.println("    public static double getBaseThreatLevel(String typeName) {");
          pw.println("        return switch (typeName) {");
          pw.println("            case \"Zombie\" -> 1.0;");
          pw.println("            case \"Ghost\" -> 0.5;");
          pw.println("            case \"PumpkinBomber\" -> 1.5;");
          pw.println("            default -> 0.0;");
          pw.println("        };");
          pw.println("    }");
          pw.println();
          pw.println("    public static void applyDamageMultiplier(CharacterType character, double damageMultiplier, boolean instantDeath) {");
          pw.println("        if (character == null) return;");
          pw.println("        String typeName = character.eClass().getName();");
          pw.println("        switch (typeName) {");
          pw.println("            case \"Zombie\" -> applyZombieDamage((Zombie) character, damageMultiplier, instantDeath);");
          pw.println("            case \"Ghost\" -> applyGhostDamage((Ghost) character, damageMultiplier, instantDeath);");
          pw.println("            case \"PumpkinBomber\" -> applyPumpkinBomberDamage((PumpkinBomber) character, damageMultiplier, instantDeath);");
          pw.println("            default -> LOGGER.warning(\"Unknown character type for damage multiplier: \" + typeName);");
          pw.println("        }");
          pw.println("    }");
          pw.println();
          pw.println("    private static void applyZombieDamage(Zombie z, double multiplier, boolean instantDeath) {");
          pw.println("        if (instantDeath) z.setAttackDamage(Integer.MAX_VALUE);");
          pw.println("        else z.setAttackDamage(Math.max(1, (int) Math.round(z.getAttackDamage() * multiplier)));");
          pw.println("    }");
          pw.println();
          pw.println("    private static void applyGhostDamage(Ghost g, double multiplier, boolean instantDeath) {");
          pw.println("        if (instantDeath) g.setAttackDamage(Integer.MAX_VALUE);");
          pw.println("        else g.setAttackDamage(Math.max(1, (int) Math.round(g.getAttackDamage() * multiplier)));");
          pw.println("    }");
          pw.println();
          pw.println("    private static void applyPumpkinBomberDamage(PumpkinBomber p, double multiplier, boolean instantDeath) {");
          pw.println("        if (instantDeath) p.setAttackDamage(Integer.MAX_VALUE);");
          pw.println("        else p.setAttackDamage(Math.max(1, (int) Math.round(p.getAttackDamage() * multiplier)));");
          pw.println("    }");
          pw.println("}");
      }
      System.out.println("  Generated: CharacterAttributeSetter.java");
  }

  private void generateCharacterGraphicsFactory(OpponentModel model, File outFolder) throws IOException {
      File outFile = new File(outFolder, "CharacterGraphicsFactory.java");
      try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
          pw.println("package main.game.maze.generated;");
          pw.println();
          pw.println("import java.util.logging.Logger;");
          pw.println("import main.game.maze.opponents.*;");
          pw.println();
          pw.println("/**");
          pw.println(" * Generated factory for character graphics/sprites.");
          pw.println(" * @generated from opponents.ecore");
          pw.println(" */");
          pw.println("public final class CharacterGraphicsFactory {");
          pw.println();
          pw.println("    private static final Logger LOGGER = Logger.getLogger(CharacterGraphicsFactory.class.getName());");
          pw.println();
          pw.println("    private CharacterGraphicsFactory() { }");
          pw.println();
          pw.println("    public static String getSpritePath(CharacterType character) {");
          pw.println("        if (character == null) return \"/images/default_enemy.png\";");
          pw.println("        String typeName = character.eClass().getName();");
          pw.println("        return switch (typeName) {");
          pw.println("            case \"Zombie\" -> getZombieSprite((Zombie) character);");
          pw.println("            case \"Ghost\" -> getGhostSprite((Ghost) character);");
          pw.println("            case \"PumpkinBomber\" -> getPumpkinBomberSprite((PumpkinBomber) character);");
          pw.println("            default -> \"/images/default_enemy.png\";");
          pw.println("        };");
          pw.println("    }");
          pw.println();
          pw.println("    private static String getZombieSprite(Zombie z) {");
          pw.println("        String img = z.getImageBase();");
          pw.println("        return (img != null && !img.isEmpty()) ? img : \"/images/zombie_default.png\";");
          pw.println("    }");
          pw.println();
          pw.println("    private static String getGhostSprite(Ghost g) {");
          pw.println("        String img = g.getImageBase();");
          pw.println("        return (img != null && !img.isEmpty()) ? img : \"/images/ghost_default.png\";");
          pw.println("    }");
          pw.println();
          pw.println("    private static String getPumpkinBomberSprite(PumpkinBomber p) {");
          pw.println("        String img = p.getImageBase();");
          pw.println("        return (img != null && !img.isEmpty()) ? img : \"/images/pumpkinbomber_default.png\";");
          pw.println("    }");
          pw.println();
          pw.println("    public static int getAnimationFrameCount(String typeName) {");
          pw.println("        return switch (typeName) {");
          pw.println("            case \"Zombie\" -> 4;");
          pw.println("            case \"Ghost\" -> 6;");
          pw.println("            case \"PumpkinBomber\" -> 4;");
          pw.println("            default -> 1;");
          pw.println("        };");
          pw.println("    }");
          pw.println();
          pw.println("    public static double getSpriteScale(String typeName) {");
          pw.println("        return switch (typeName) {");
          pw.println("            case \"Zombie\" -> 1.0;");
          pw.println("            case \"Ghost\" -> 0.8;");
          pw.println("            case \"PumpkinBomber\" -> 1.2;");
          pw.println("            default -> 1.0;");
          pw.println("        };");
          pw.println("    }");
          pw.println("}");
      }
      System.out.println("  Generated: CharacterGraphicsFactory.java");
  }
}
