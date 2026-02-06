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

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.walls.WallsPackage;

/**
 * Acceleo 4 launcher for MazeGame code generation.
 * 
 * Generates Java code from EMF models using Acceleo 4 M2T templates.
 * Supports all four model domains: opponents, walls, difficulties, behaviour.
 * 
 * Usage: java -jar maze-generator-java.jar <opponentsXMI> <wallsXMI> <difficultiesXMI> <behaviourEcore> <outputDir>
 * 
 * Or with fewer arguments for partial generation:
 * Usage: java -jar maze-generator-java.jar <opponentsXMI> <wallsXMI> <outputDir>
 */
public class AcceleoGeneratorMain {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: AcceleoGeneratorMain <opponentsXMI> <wallsXMI> <outputDir>");
            System.err.println("   or: AcceleoGeneratorMain <opponentsXMI> <wallsXMI> <difficultiesXMI> <behaviourEcore> <outputDir>");
            System.exit(1);
        }

        try {
            AcceleoGeneratorMain generator = new AcceleoGeneratorMain();
            
            if (args.length == 3) {
                // Legacy mode: opponents + walls only
                String opponentsPath = args[0];
                String wallsPath = args[1];
                String outputDir = args[2];
                
                System.out.println("=== MazeGame Acceleo 4 Generator (Basic Mode) ===");
                System.out.println("Opponents model: " + new File(opponentsPath).getAbsolutePath());
                System.out.println("Walls model: " + new File(wallsPath).getAbsolutePath());
                System.out.println("Output directory: " + new File(outputDir).getAbsolutePath());
                System.out.println();
                
                generator.generate(opponentsPath, wallsPath, outputDir);
            } else if (args.length >= 5) {
                // Full mode: all domains
                String opponentsPath = args[0];
                String wallsPath = args[1];
                String difficultiesPath = args[2];
                String behaviourPath = args[3];
                String outputDir = args[4];
                
                System.out.println("=== MazeGame Acceleo 4 Generator (Full Mode) ===");
                System.out.println("Opponents model: " + new File(opponentsPath).getAbsolutePath());
                System.out.println("Walls model: " + new File(wallsPath).getAbsolutePath());
                System.out.println("Difficulties model: " + new File(difficultiesPath).getAbsolutePath());
                System.out.println("Behaviour model: " + new File(behaviourPath).getAbsolutePath());
                System.out.println("Output directory: " + new File(outputDir).getAbsolutePath());
                System.out.println();
                
                generator.generateAll(opponentsPath, wallsPath, difficultiesPath, behaviourPath, outputDir);
            } else {
                System.err.println("Invalid number of arguments");
                System.exit(1);
            }
            
            System.out.println("\n✓ Code generation complete!");
        } catch (Exception e) {
            System.err.println("✗ Generation failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Full generation across all four domains.
     */
    public void generateAll(String opponentsPath, String wallsPath, 
                            String difficultiesPath, String behaviourPath,
                            String outputDir) throws IOException, URISyntaxException {
        
        // Initialize all EMF packages
        initializePackages();
        
        // Create resource set for loading models
        ResourceSet resourceSet = createResourceSet();
        
        // Setup Acceleo environment
        GenerationContext ctx = createGenerationContext(resourceSet);
        
        // Generate opponents code
        System.out.println("Generating opponent code...");
        Resource opponentsResource = loadModel(resourceSet, opponentsPath);
        generateFromModule(
            "main::game::maze::codegen::acceleo4::Generate",
            opponentsResource, outputDir, ctx
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
            wallsResource, outputDir, ctx
        );
        System.out.println("  ✓ WallRegistry.java");
        System.out.println("  ✓ WallMaterialRenderer.java");
        System.out.println("  ✓ WallCollisionHandler.java");
        
        // Generate difficulties code
        System.out.println("Generating difficulty code...");
        Resource difficultiesResource = loadModel(resourceSet, difficultiesPath);
        generateFromModule(
            "main::game::maze::codegen::acceleo4::GenerateDifficulties",
            difficultiesResource, outputDir, ctx
        );
        System.out.println("  ✓ DifficultyConfigurator.java");
        System.out.println("  ✓ EnemySpawnLimits.java");
        System.out.println("  ✓ DifficultyRegistry.java");
        
        // Generate behaviour code (uses ecore package, not XMI instance)
        System.out.println("Generating behaviour code...");
        Resource behaviourResource = loadModel(resourceSet, behaviourPath);
        generateFromModule(
            "main::game::maze::codegen::acceleo4::GenerateBehaviour",
            behaviourResource, outputDir, ctx
        );
        System.out.println("  ✓ BehaviorDispatcher.java");
        System.out.println("  ✓ PathCalculatorFactory.java");
        System.out.println("  ✓ BehaviorRegistry.java");
    }

    /**
     * Legacy generation for opponents and walls only.
     */
    public void generate(String opponentsPath, String wallsPath, String outputDir) 
            throws IOException, URISyntaxException {
        
        // Initialize EMF packages
        initializePackages();
        
        // Create resource set for loading models
        ResourceSet resourceSet = createResourceSet();

        // Setup Acceleo environment
        GenerationContext ctx = createGenerationContext(resourceSet);

        // Generate opponents code
        System.out.println("Generating opponent code...");
        Resource opponentsResource = loadModel(resourceSet, opponentsPath);
        generateFromModule(
            "main::game::maze::codegen::acceleo4::Generate",
            opponentsResource, outputDir, ctx
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
            wallsResource, outputDir, ctx
        );
        System.out.println("  ✓ WallRegistry.java");
        System.out.println("  ✓ WallMaterialRenderer.java");
        System.out.println("  ✓ WallCollisionHandler.java");
    }
    
    private void initializePackages() {
        OpponentsPackage.eINSTANCE.eClass();
        WallsPackage.eINSTANCE.eClass();
        DifficultiesPackage.eINSTANCE.eClass();
        BehaviourPackage.eINSTANCE.eClass();
    }

    private ResourceSet createResourceSet() {
        ResourceSet resourceSet = new ResourceSetImpl();
        
        // Register XMI resource factory
        resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap()
            .put(Resource.Factory.Registry.DEFAULT_EXTENSION, 
                 new XMIResourceFactoryImpl());
        
        // Register all EPackages
        resourceSet.getPackageRegistry().put(
            OpponentsPackage.eNS_URI, 
            OpponentsPackage.eINSTANCE
        );
        resourceSet.getPackageRegistry().put(
            WallsPackage.eNS_URI, 
            WallsPackage.eINSTANCE
        );
        resourceSet.getPackageRegistry().put(
            DifficultiesPackage.eNS_URI, 
            DifficultiesPackage.eINSTANCE
        );
        resourceSet.getPackageRegistry().put(
            BehaviourPackage.eNS_URI, 
            BehaviourPackage.eINSTANCE
        );
        
        return resourceSet;
    }
    
    /**
     * Holds Acceleo generation context for reuse across multiple generations.
     */
    private static class GenerationContext {
        final IQualifiedNameResolver resolver;
        final IQualifiedNameQueryEnvironment queryEnvironment;
        final AcceleoEvaluator evaluator;
        final ResourceSet resourceSet;
        
        GenerationContext(IQualifiedNameResolver resolver,
                         IQualifiedNameQueryEnvironment queryEnvironment,
                         AcceleoEvaluator evaluator,
                         ResourceSet resourceSet) {
            this.resolver = resolver;
            this.queryEnvironment = queryEnvironment;
            this.evaluator = evaluator;
            this.resourceSet = resourceSet;
        }
    }
    
    private GenerationContext createGenerationContext(ResourceSet resourceSet) {
        // Create resolver for Acceleo modules
        IQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(
            getClass().getClassLoader(),
            resourceSet.getPackageRegistry(),
            AcceleoParser.QUALIFIER_SEPARATOR
        );

        // Create parser and evaluator
        AcceleoParser parser = new AcceleoParser();
        String newLine = System.lineSeparator();
        
        // Create query environment for Acceleo
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
        
        return new GenerationContext(resolver, queryEnvironment, evaluator, resourceSet);
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
            GenerationContext ctx) throws IOException {
        
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
        ctx.resolver.resolve(moduleQualifiedName);
        Object resolvedObj = ctx.resolver.resolve(moduleQualifiedName);
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
            ctx.resourceSet.getURIConverter(),
            new DefaultWriterFactory()
        );

        try {
            // Debug model contents
            System.out.println("  Model resource contents: " + modelResource.getContents().size());
            for (org.eclipse.emf.ecore.EObject root : modelResource.getContents()) {
                System.out.println("    Root: " + root.eClass().getName() + " (" + root.eClass().getEPackage().getNsURI() + ")");
            }
            
            AcceleoUtil.generate(
                ctx.evaluator,
                ctx.queryEnvironment,
                module,
                modelResource,
                strategy,
                outputURI,
                null,  // log URI
                new BasicMonitor()
            );
        } catch (Exception e) {
            System.err.println("  Exception during generation: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
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
