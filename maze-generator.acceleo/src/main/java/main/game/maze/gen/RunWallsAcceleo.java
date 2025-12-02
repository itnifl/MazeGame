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

public class RunWallsAcceleo {

    // .emtl ligger på denne klassenpathen i pluginen
    private static final String MODULE_PATH = "main/game/maze/gen/templates/GenerateWalls"; // uten .emtl
    private static final String TEMPLATE_NAME = "generate";

    public void run(String wallsModelPath, String outDir) throws Exception {

        // 1. ResourceSet + XMI-factory for .xmi
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry()
          .getExtensionToFactoryMap()
          .put("xmi", new XMIResourceFactoryImpl());

        // 2. Registrer EPackage for veggmodellen
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                main.game.maze.walls.WallsPackage.eNS_URI,
                main.game.maze.walls.WallsPackage.eINSTANCE
        );

        // 3. Last walls-modellen
        File modelFile = new File(wallsModelPath);
        URI modelURI = URI.createFileURI(modelFile.getAbsolutePath());
        Resource modelRes = rs.getResource(modelURI, true);
        EObject root = modelRes.getContents().get(0);

        System.out.println("Walls model root: " + root.eClass().getName()
                + " from " + root.eClass().getEPackage().getNsURI());

        // 4. Last .emtl-modulen fra classpath
        URL moduleUrl = RunWallsAcceleo.class.getResource("/" + MODULE_PATH + ".emtl");
        if (moduleUrl == null) {
            throw new IllegalStateException("Cannot find GenerateWalls.emtl at /" + MODULE_PATH + ".emtl");
        }

        Resource moduleRes = rs.getResource(URI.createURI(moduleUrl.toString()), true);
        Module module = (Module) moduleRes.getContents().get(0);

        // 5. Sørg for output-folder
        File outFolder = new File(outDir);
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        System.out.println("Generating walls code into: " + outFolder.getAbsolutePath());

        // 6. Kjør Acceleo
        AcceleoService service = new AcceleoService();
        service.doGenerate(module, TEMPLATE_NAME, root,
                Collections.emptyList(), outFolder, null);

        System.out.println("Walls generation done.");
    }
}
