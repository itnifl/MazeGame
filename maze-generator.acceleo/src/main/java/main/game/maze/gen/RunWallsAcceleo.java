// /maze-generator.acceleo/src/main/game/maze/gen/RunWallsAcceleo.java
package main.game.maze.gen;

import java.io.File;
import java.net.URL;
import java.util.Collections;

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import main.game.maze.walls;

public class RunWallsAcceleo {

    // NB: sti uten .mtl, til GenerateWalls.mtl
    private static final String MODULE_PATH   = "main/game/maze/gen/templates/GenerateWalls";
    private static final String TEMPLATE_NAME = "generate";

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: RunWallsAcceleo <wallsModel.walls> <outDir>");
            System.exit(1);
        }

        String wallsModelPath = new File(args[0]).getAbsolutePath();
        String outDir         = new File(args[1]).getAbsolutePath();

        new RunWallsAcceleo().run(wallsModelPath, outDir);
    }

    public void run(String wallsModelPath, String outDir) throws Exception {
        // 1. ResourceSet + factory for .walls
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry()
          .getExtensionToFactoryMap()
          .put("walls", new XMIResourceFactoryImpl());

        // 2. Registrer EPackage for veggmodellen
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                WallsPackage.eNS_URI,
                WallsPackage.eINSTANCE
        );

        // 3. Last walls-modellen
        Resource wallsRes = rs.getResource(
                URI.createFileURI(new File(wallsModelPath).getAbsolutePath()),
                true
        );
        EObject root = wallsRes.getContents().get(0);

        System.out.println("DEBUG (walls): Loaded XMI root");
        System.out.println("  Type:    " + root.eClass().getName());
        System.out.println("  Package: " + root.eClass().getEPackage().getNsURI());
        System.out.println("  Java:    " + root.getClass().getName());
        System.out.println("==========================================");

        // 4. Last .emtl-modulen
        URL moduleUrl = RunWallsAcceleo.class.getResource("/" + MODULE_PATH + ".emtl");
        if (moduleUrl == null) {
            throw new IllegalStateException("Cannot find Acceleo module: /" + MODULE_PATH + ".emtl");
        }

        Resource moduleRes = rs.getResource(URI.createURI(moduleUrl.toString()), true);
        Module module = (Module) moduleRes.getContents().get(0);

        // 5. Output-katalog
        File outFolder = new File(outDir);
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        System.out.println("Starting Acceleo walls generation...");
        AcceleoService service = new AcceleoService();
        service.doGenerate(module, TEMPLATE_NAME, root, Collections.emptyList(), outFolder, null);
        System.out.println("Walls generation complete. Output in: " + outFolder.getAbsolutePath());
    }
}