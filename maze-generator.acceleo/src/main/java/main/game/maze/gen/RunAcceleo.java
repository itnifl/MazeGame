package main.game.maze.gen;

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

public class RunAcceleo {
  // Change these to your module + main template
  private static final String MODULE = "main/game/maze/gen/templates/Generate"; // .mtl without extension
  private static final String TEMPLATE = "generate";

  public void run(String modelPath, String outputDir) throws Exception {
    // Load the XMI
    ResourceSet rs = new ResourceSetImpl();
    rs.getResourceFactoryRegistry().getExtensionToFactoryMap()
      .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
    Resource r = rs.getResource(URI.createFileURI(modelPath), true);
    EObject root = r.getContents().get(0);

    // Prepare output
    File out = new File(outputDir);
    if (!out.exists() && !out.mkdirs()) {
      throw new IllegalStateException("Cannot create output dir: " + out);
    }

        // Run Acceleo (engine only, no UI)
    AcceleoService service = new AcceleoService();

    // Finn .emtl-filen fra pluginens classpath
    URL moduleUrl = RunAcceleo.class.getResource("/" + MODULE + ".emtl");
    if (moduleUrl == null) {
      throw new IllegalStateException("Cannot find Acceleo module: /" + MODULE + ".emtl");
    }

    Resource moduleRes = rs.getResource(URI.createURI(moduleUrl.toString()), true);
    Module module = (Module) moduleRes.getContents().get(0);

    List<Object> args = Collections.emptyList();
    service.doGenerate(module, TEMPLATE, root, args, out, null);

    System.out.println("Acceleo done → " + out.getAbsolutePath());
  }
}
