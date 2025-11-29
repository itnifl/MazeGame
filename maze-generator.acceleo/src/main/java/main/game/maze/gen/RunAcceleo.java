package main.game.maze.gen;
// /maze-generator.acceleo/src/main/game/maze/gen/RunAcceleo.java
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.difficulties.DifficultiesPackage;

public class RunAcceleo {
  // Change these to your module + main template
  private static final String MODULE_PATH = "main/game/maze/gen/templates/Generate"; // .mtl without extension
  private static final String TEMPLATE_NAME = "generate";

  public static void main(String[] args) throws Exception {
    if (args.length < 3) {
        System.out.println("Usage: RunAcceleo <opponentModel.xmi> <difficulties.xmi> <outDir>");
        System.exit(1);
    }

    String opponentModelPath = new File(args[0]).getAbsolutePath();
    String difficultiesPath = new File(args[1]).getAbsolutePath();
    String outDir    = new File(args[2]).getAbsolutePath();

    new RunAcceleo().run(opponentModelPath, difficultiesPath, outDir);
  }

  public void run(String opponentModelPath,
                    String difficultiesPath,
                    String outDir) throws Exception {

        // 1. Register XMI Factory
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        // 2. Register your EPackages manually (since we are running standalone/headless)
        // Note: These classes are now visible because we added them to Require-Bundle in MANIFEST.MF
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                main.game.maze.difficulties.DifficultiesPackage.eNS_URI,
                main.game.maze.difficulties.DifficultiesPackage.eINSTANCE
        );
        org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(
                main.game.maze.opponents.OpponentsPackage.eNS_URI,
                main.game.maze.opponents.OpponentsPackage.eINSTANCE
        );

        // 3. Load Resources
        // We load the difficulties first just to ensure it's in the ResourceSet, 
        // though Acceleo might find it via relative paths if configured correctly.
        rs.getResource(URI.createFileURI(new File(difficultiesPath).getAbsolutePath()), true);
        
        // Load the Opponent model (which presumably is the input for the template)
        Resource oppRes = rs.getResource(URI.createFileURI(new File(opponentModelPath).getAbsolutePath()), true);
        EObject root = oppRes.getContents().get(0);

        // 4. Load the compiled Acceleo Module (.emtl)
        URL moduleUrl = RunAcceleo.class.getResource("/" + MODULE_PATH + ".emtl");
        if (moduleUrl == null) {
            throw new IllegalStateException("Cannot find Acceleo module: /" + MODULE_PATH + ".emtl");
        }
        
        Resource moduleRes = rs.getResource(URI.createURI(moduleUrl.toString()), true);
        Module module = (Module) moduleRes.getContents().get(0);

        // 5. Run Generation
        File outFolder = new File(outDir);
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        System.out.println("Starting Acceleo generation...");
        AcceleoService service = new AcceleoService();
        service.doGenerate(module, TEMPLATE_NAME, root, Collections.emptyList(), outFolder, null);
        
        System.out.println("Acceleo generation complete. Output in: " + outFolder.getAbsolutePath());
  }
}
