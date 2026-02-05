// /maze-generator.acceleo/src/main/game/maze/gen/RunWallsAcceleo.java
package main.game.maze.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import main.game.maze.gen.templates.GenerateWalls;

public class RunWallsAcceleo {

    public void run(String wallsModelPath, String outDir) throws Exception {

        // 1. ResourceSet + XMI-factory for .xmi
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry()
          .getExtensionToFactoryMap()
          .put("xmi", new XMIResourceFactoryImpl());

        // 2. Register EPackage for walls model
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                main.game.maze.walls.WallsPackage.eNS_URI,
                main.game.maze.walls.WallsPackage.eINSTANCE
        );

        // 3. Load walls model
        File modelFile = new File(wallsModelPath);
        URI modelURI = URI.createFileURI(modelFile.getAbsolutePath());
        Resource modelRes = rs.getResource(modelURI, true);
        EObject root = modelRes.getContents().get(0);

        System.out.println("Walls model root: " + root.eClass().getName()
                + " from " + root.eClass().getEPackage().getNsURI());

        // 4. Create output folder
        File outFolder = new File(outDir);
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        System.out.println("Generating walls code into: " + outFolder.getAbsolutePath());

        // 5. Use GenerateWalls Java API directly (bypasses .emtl requirement)
        try {
            GenerateWalls generator = new GenerateWalls();
            List<Object> arguments = new ArrayList<>();
            arguments.add(root);
            
            // Call doGenerate with a monitor
            generator.doGenerate(BasicMonitor.toMonitor(new BasicMonitor()));
            
            System.out.println("Walls generation done.");
        } catch (Exception e) {
            System.err.println("Generation failed: " + e.getMessage());
            throw e;
        }
    }
}
