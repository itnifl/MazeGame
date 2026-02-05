package main.game.maze.gen;
// /maze-generator.acceleo/src/main/game/maze/gen/RunAcceleo.java
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.parser.AcceleoParser;
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
  private static final String MODULE_PATH = "main/game/maze/gen/templates/Generate";
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
        System.out.println("Java Class: " + root.getClass().getName());
        System.out.println("==========================================");

        // 4. Load module - try .emtl first, then compile .mtl if needed
        Module module = null;
        URL moduleUrl = RunAcceleo.class.getResource("/" + MODULE_PATH + ".emtl");
        
        if (moduleUrl != null) {
            // Pre-compiled .emtl exists  - use it
            System.out.println("Using pre-compiled .emtl module");
            Resource moduleRes = rs.getResource(URI.createURI(moduleUrl.toString()), true);
            module = (Module) moduleRes.getContents().get(0);
        } else {
           // No .emtl found - try to compile .mtl at runtime
            System.out.println("No .emtl found, attempting to compile .mtl at runtime...");
            URL mtlUrl = RunAcceleo.class.getResource("/" + MODULE_PATH + ".mtl");
            if (mtlUrl == null) {
                throw new IllegalStateException("Cannot find Generate.mtl or .emtl at /" + MODULE_PATH);
            }
            
            try {
                // Use Acceleo parser to compile .mtl
                AcceleoParser parser = new AcceleoParser();
                File mtlFile = new File(mtlUrl.toURI());
                java.util.List<File> files = java.util.Collections.singletonList(mtlFile);
                parser.parse(files, new java.util.ArrayList<>(), new java.util.ArrayList<>());
                
                // Now load the compiled module
                String emtlPath = mtlUrl.toString().replace(".mtl", ".emtl");
                Resource moduleRes = rs.getResource(URI.createURI(emtlPath), true);
                module = (Module) moduleRes.getContents().get(0);
                System.out.println("Successfully compiled and loaded .mtl template");
            } catch (Exception e) {
                throw new RuntimeException("Failed to compile .mtl template: " + e.getMessage(), e);
            }
        }

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
