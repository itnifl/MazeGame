// /maze-generator.acceleo/src/main/game/maze/gen/RunWallsAcceleo.java
package main.game.maze.gen;

import java.io.File;
import main.game.maze.gen.templates.GenerateWalls;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.common.util.BasicMonitor;

import main.game.maze.gen.templates.GenerateWalls; // <- den som oppdateres nå

public class RunWallsAcceleo {

    public void run(String wallsModelPath, String outDir) throws Exception {
        // register factory for .walls/.xmi
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry()
          .getExtensionToFactoryMap()
          .put("xmi", new XMIResourceFactoryImpl());

        // registrer EPackage for veggmodellen
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                main.game.maze.walls.WallsPackage.eNS_URI,
                main.game.maze.walls.WallsPackage.eINSTANCE
        );

        URI modelURI = URI.createFileURI(new File(wallsModelPath).getAbsolutePath());
        File outFolder = new File(outDir);
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        // bruk den genererte Acceleo-generatoren
        GenerateWalls generator =
                new GenerateWalls(modelURI, outFolder, Collections.emptyList());
        generator.doGenerate(new BasicMonitor());
    }
}
