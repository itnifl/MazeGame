package main.game.maze.codegen;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultWriterFactory;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.walls.WallsPackage;

/**
 * Acceleo 4 launcher for MazeGame code generation.
 * 
 * Generates Java code from EMF models using Acceleo 4 M2T templates.
 * 
 * Usage: java -jar maze-generator-java.jar <opponentsXMI> <wallsXMI> <outputDir>
 */
public class AcceleoGeneratorMain {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: AcceleoGeneratorMain <opponentsXMI> <wallsXMI> <outputDir>");
            System.exit(1);
        }

        String opponentsPath = args[0];
        String wallsPath = args[1];
        String outputDir = args[2];

        System.out.println("=== MazeGame Acceleo 4 Generator ===");
        System.out.println("Opponents model: " + new File(opponentsPath).getAbsolutePath());
        System.out.println("Walls model: " + new File(wallsPath).getAbsolutePath());
        System.out.println("Output directory: " + new File(outputDir).getAbsolutePath());
        System.out.println();

        try {
            AcceleoGeneratorMain generator = new AcceleoGeneratorMain();
            generator.generate(opponentsPath, wallsPath, outputDir);
            System.out.println("\n✓ Code generation complete!");
        } catch (Exception e) {
            System.err.println("✗ Generation failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void generate(String opponentsPath, String wallsPath, String outputDir) 
            throws IOException, URISyntaxException {
        
        // Initialize EMF packages
        OpponentsPackage.eINSTANCE.eClass();
        WallsPackage.eINSTANCE.eClass();
        
        // Create resource set for loading models
        ResourceSet resourceSet = createResourceSet();

        // Create resolver for Acceleo modules (3 args: ClassLoader, EPackage.Registry, qualifierSep)
        IQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(
            getClass().getClassLoader(),
            resourceSet.getPackageRegistry(),
            AcceleoParser.QUALIFIER_SEPARATOR
        );

        // Create parser and evaluator
        AcceleoParser parser = new AcceleoParser();
        String newLine = System.lineSeparator();
        
        // Create query environment for Acceleo (4 args: options, resolver, resourceSet, forWorkspace)
        Map<String, String> options = new HashMap<>();
        options.put(AcceleoUtil.NEW_LINE_OPTION, newLine);
        IQualifiedNameQueryEnvironment queryEnvironment = 
            AcceleoUtil.newAcceleoQueryEnvironment(options, resolver, resourceSet, false);

        // Create evaluator and register the module loader
        AcceleoEvaluator evaluator = new AcceleoEvaluator(
            queryEnvironment.getLookupEngine(), newLine);
        ModuleLoader moduleLoader = new ModuleLoader(parser, evaluator);
        resolver.addLoader(moduleLoader);

        // Register all available qualified names so modules can be found
        resolver.getAvailableQualifiedNames();

        // Generate opponents code
        System.out.println("Generating opponent code...");
        Resource opponentsResource = loadModel(resourceSet, opponentsPath);
        generateFromModule(
            "main::game::maze::codegen::acceleo4::Generate",
            opponentsResource,
            outputDir,
            resolver,
            queryEnvironment,
            evaluator,
            resourceSet
        );
        System.out.println("  ✓ OpponentRegistry.java");
        System.out.println("  ✓ CharacterRegistrar.java");
        System.out.println("  ✓ CharacterAttributeSetter.java");
        System.out.println("  ✓ CharacterGraphicsFactory.java");

        // Generate walls code
        System.out.println("Generating wall code...");
        Resource wallsResource = loadModel(resourceSet, wallsPath);
        generateFromModule(
            "main::game::maze::codegen::acceleo4::GenerateWalls",
            wallsResource,
            outputDir,
            resolver,
            queryEnvironment,
            evaluator,
            resourceSet
        );
        System.out.println("  ✓ WallRegistry.java");
    }

    private ResourceSet createResourceSet() {
        ResourceSet resourceSet = new ResourceSetImpl();
        
        // Register XMI resource factory
        resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap()
            .put(Resource.Factory.Registry.DEFAULT_EXTENSION, 
                 new XMIResourceFactoryImpl());
        
        // Register EPackages
        resourceSet.getPackageRegistry().put(
            OpponentsPackage.eNS_URI, 
            OpponentsPackage.eINSTANCE
        );
        resourceSet.getPackageRegistry().put(
            WallsPackage.eNS_URI, 
            WallsPackage.eINSTANCE
        );
        
        return resourceSet;
    }

    private Resource loadModel(ResourceSet resourceSet, String modelPath) throws IOException {
        File modelFile = new File(modelPath);
        if (!modelFile.exists()) {
            throw new IOException("Model file not found: " + modelFile.getAbsolutePath());
        }
        
        URI modelURI = URI.createFileURI(modelFile.getAbsolutePath());
        Resource resource = resourceSet.getResource(modelURI, true);
        
        if (resource == null || resource.getContents().isEmpty()) {
            throw new IOException("Failed to load model: " + modelPath);
        }
        
        return resource;
    }

    private void generateFromModule(
            String moduleQualifiedName,
            Resource modelResource,
            String outputDir,
            IQualifiedNameResolver resolver,
            IQualifiedNameQueryEnvironment queryEnvironment,
            AcceleoEvaluator evaluator,
            ResourceSet resourceSet) throws IOException {
        
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        // Use forward slashes for URI consistency
        String outputPath = outputDirectory.getAbsolutePath().replace('\\', '/');
        if (!outputPath.endsWith("/")) {
            outputPath += "/";
        }
        URI outputURI = URI.createFileURI(outputPath);

        // Resolve and load the module
        resolver.resolve(moduleQualifiedName);
        Object resolvedObj = resolver.resolve(moduleQualifiedName);
        if (resolvedObj == null) {
            throw new IOException("Could not resolve module: " + moduleQualifiedName);
        }
        if (!(resolvedObj instanceof Module)) {
            throw new IOException("Resolved object is not a Module: " + resolvedObj.getClass().getName() + " - " + resolvedObj);
        }
        Module module = (Module) resolvedObj;
        System.out.println("  Module resolved: " + module.getName() + " (" + module.getModuleElements().size() + " elements)");
        System.out.println("  Output URI: " + outputURI);
        
        // Debug: check main templates
        java.util.List<org.eclipse.acceleo.Template> mainTemplates = AcceleoUtil.getMainTemplates(module);
        System.out.println("  Main templates found: " + mainTemplates.size());
        for (org.eclipse.acceleo.Template t : mainTemplates) {
            System.out.println("    - " + t.getName() + " (params: " + t.getParameters().size() + ")");
        }

        // Create generation strategy with proper lifecycle management
        IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(
            resourceSet.getURIConverter(),
            new DefaultWriterFactory()
        );

        try {
            AcceleoUtil.generate(
                evaluator,
                queryEnvironment,
                module,
                modelResource,
                strategy,
                outputURI,
                null,  // log URI
                new BasicMonitor()
            );
        } catch (Exception e) {
            throw new IOException("Generation failed for module " + moduleQualifiedName, e);
        } finally {
            // Ensure strategy is terminated (flushes writers)
            strategy.terminate();
        }
        
        // Verify files were written
        File generatedDir = new File(outputDir, "main/game/maze/generated");
        if (generatedDir.exists()) {
            File[] files = generatedDir.listFiles();
            if (files != null) {
                System.out.println("  Generated " + files.length + " files in " + generatedDir.getAbsolutePath());
            }
        } else {
            System.err.println("  WARNING: Output directory not created: " + generatedDir.getAbsolutePath());
        }
    }
}
