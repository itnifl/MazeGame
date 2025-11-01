package main.game.maze.runtime.opponents;

import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import main.game.maze.constants.OpponentConstants;
import main.game.maze.constants.StageConstants;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;
import main.game.maze.opponents.util.OpponentsValidator;
import main.game.maze.util.Dialogs;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.ZombieCharacter;
import main.game.maze.GameController;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.EnemyTypes;

/**
 * Factory that instantiates runtime character objects from EMF OpponentModel XMI files.
 * All Node creation and GameController registration happen on the JavaFX Application Thread.
 */
public final class OpponentRuntimeFactory {

    private static final Logger LOGGER = Logger.getLogger(OpponentRuntimeFactory.class.getName());
    private static final int SPAWN_MARGIN = 20;
    private static volatile boolean xmiFactoryRegistered = false;

    private OpponentRuntimeFactory() { /* utility class */  }

    /**
     * Instantiate characters from an XMI model and register them with the provided GameController.
     *
     * @param resourcePath  classpath path to the XMI (e.g. "/opponents/instances/classic_zombie.xmi")
     * @param gameController the controller responsible for registering characters and nodes
     */
    public static void instantiateFromModel(GameController gameController) {
        String resourcePath = OpponentConstants.ZombieModelPath;

        if (resourcePath == null || resourcePath.isBlank()) {
            LOGGER.warning("instantiateFromModel called with empty resourcePath");
            return;
        }
        if (gameController == null) {
            LOGGER.warning("instantiateFromModel requires a non-null GameController");
            return;
        }

        try {
            ensureXmiFactoryRegistered();

            ResourceSet resourceSet = new ResourceSetImpl();            

            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
            OpponentsPackage.eINSTANCE.eClass(); 
            DifficultiesPackage.eINSTANCE.eClass();
            
            EPackage.Registry.INSTANCE.put(OpponentsPackage.eNS_URI, OpponentsPackage.eINSTANCE);
            EPackage.Registry.INSTANCE.put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);

            resourceSet.getPackageRegistry().put(OpponentsPackage.eNS_URI, OpponentsPackage.eINSTANCE);
            resourceSet.getPackageRegistry().put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);
                        
            var resourceUrlReference = main.game.maze.opponents.OpponentsPackage.class.getResource(resourcePath);

            if(resourceUrlReference == null) {
                resourceUrlReference = OpponentRuntimeFactory.class.getResource(resourcePath);
            }
            
            if (resourceUrlReference == null) {
                LOGGER.log(Level.WARNING, "Opponent model resource not found: {0}", resourcePath);
                return;
            }

            URI modelUri = URI.createURI(resourceUrlReference.toString());
            Resource resource = resourceSet.getResource(modelUri, true);
            if (resource == null || resource.getContents().isEmpty()) {
                LOGGER.log(Level.WARNING, "Loaded resource is empty or null: {0}", resourcePath);
                return;
            }

            Object rootObject;
            try {
                rootObject = resource.getContents().get(0);
            } catch (Exception loadEx) {
                LOGGER.log(Level.SEVERE, "Failed to load root content: " + resourcePath, loadEx);
                throw loadEx;
            }
            
            if (!(rootObject instanceof OpponentModel)) {
                LOGGER.log(Level.WARNING, "Root object is not an OpponentModel: {0}", resourcePath);
                return;
            }

            OpponentModel opponentModel;
            try {
                opponentModel = (OpponentModel) rootObject;
            } catch (Exception loadEx) {
                LOGGER.log(Level.SEVERE, "Failed to cast root object to OpponentModel: " + resourcePath, loadEx);
                throw loadEx;
            }

            var diff = opponentModel.getSelectedDifficulty();
            if (diff == null) {
                LOGGER.warning("No selectedDifficulty set; spawning without caps/multipliers.");
            } else {
                LOGGER.log(Level.INFO, "Selected difficulty: {0}", diff.getClass());
            }

            Map<EnemyTypes, Integer> monsterTypecaps = new EnumMap<>(EnemyTypes.class);
            int maxThreat = Integer.MAX_VALUE;
            double speedMul = 1.0;
            double dmgMul = 1.0;
            boolean instantDeath = false;


            if (diff != null) {
                for (var e : diff.getEnemyMaxCount()) {
                    monsterTypecaps.put(e.getType(), e.getMaxCount());
                }
                maxThreat = diff.getMaxThreat();
                speedMul = diff.getMonstersMovementSpeedMultiplier();
                dmgMul   = diff.getMonstersDamageMultiplier();
                instantDeath = diff.isInstantDeath();
            }
            
            int ghosts = 0, zombies = 0;
            double threatSum = 0.0;
            
            var characterList = opponentModel.getCharacterTypes();
            for (var characterType : characterList) {
                if (!characterType.isEnabled()) continue;

                EnemyTypes et;
                if (characterType instanceof Ghost)  et = EnemyTypes.GHOST;
                else if (characterType instanceof Zombie) et = EnemyTypes.ZOMBIE;
                else continue;

                int cap = monsterTypecaps.getOrDefault(et, Integer.MAX_VALUE);

                if (et == EnemyTypes.GHOST && ghosts >= cap)  continue;
                if (et == EnemyTypes.ZOMBIE && zombies >= cap) continue;

                double nextThreat = threatSum + characterType.getThreatLevel();                
                if (nextThreat > maxThreat) continue;
                threatSum += characterType.getEffectiveThreat();

                characterType.setSpeed(characterType.getSpeed() * speedMul);
                if (characterType instanceof Zombie z) {
                    zombies++;
                    
                    if (instantDeath) {
                        z.setAttackDamage(Integer.MAX_VALUE);
                    } else {
                        z.setAttackDamage(Math.max(1, (int)Math.round(z.getAttackDamage() * dmgMul)));
                    }

                    Platform.runLater(() -> {
                        try {
                            final double spawnX = ThreadLocalRandom.current()
                                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, StageConstants.BoardMaxX - SPAWN_MARGIN));
                            final double spawnY = ThreadLocalRandom.current()
                                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, StageConstants.BoardMaxY - SPAWN_MARGIN));

                            Node graphicsNode = createCharacterGraphics(characterType, StageConstants.ZombieCharacterXYSize);
                            graphicsNode.setLayoutX(spawnX);
                            graphicsNode.setLayoutY(spawnY);
                            ZombieCharacter zombieCharacter = new ZombieCharacter(graphicsNode, spawnX, spawnY, (Zombie)characterType);
                            gameController.registerComputerCharacter(zombieCharacter, graphicsNode);
                        } catch (Exception fxException) {
                            LOGGER.log(Level.SEVERE, "Failed to create or register a ZombieCharacter.", fxException);
                        }
                    });
                } else if (characterType instanceof Ghost g) {                            
                    ghosts++;
                    
                    if (instantDeath) {
                        g.setAttackDamage(Integer.MAX_VALUE);
                    } else {
                        g.setAttackDamage(Math.max(1, (int)Math.round(g.getAttackDamage() * dmgMul)));
                    }

                    Platform.runLater(() -> {
                        try {
                            final double spawnX = ThreadLocalRandom.current()
                                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, StageConstants.BoardMaxX - SPAWN_MARGIN));
                            final double spawnY = ThreadLocalRandom.current()
                                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, StageConstants.BoardMaxY - SPAWN_MARGIN));

                            Node graphicsNode = createCharacterGraphics(characterType, StageConstants.GhostCharacterXYSize);
                            graphicsNode.setLayoutX(spawnX);
                            graphicsNode.setLayoutY(spawnY);
                            var character = new GhostCharacter(graphicsNode, spawnX, spawnY, (Ghost)characterType);
                            gameController.registerComputerCharacter(character, graphicsNode);
                        } catch (Exception fxException) {
                            LOGGER.log(Level.SEVERE, "Failed to create or register a GhostCharacter.", fxException);
                        }
                    });
                } else {
                    LOGGER.log(Level.FINER, "Skipping unsupported character type: {0}", characterType.getClass().getName());
                }
            }
            validateOrFail(opponentModel);
        } catch (Exception loadException) {
            LOGGER.log(Level.SEVERE, "Failed to load or instantiate opponent model: " + resourcePath + " with " + loadException.getMessage(), loadException);
             Dialogs.showError(
                "Failed to load opponents",
                "The opponent configuration could not be loaded.",
                loadException.getMessage(),
                loadException
            );
            throw loadException;
        }
    }

    private static synchronized void ensureXmiFactoryRegistered() {
        if (!xmiFactoryRegistered) {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
            xmiFactoryRegistered = true;
        }
    }

    /**
     * Replace this with your real sprite/ImageView creation.
     *
     * @param model the model describing the character
     * @return a JavaFX Node representing the character sprite
     */
    private static Node createCharacterGraphics(CharacterType model, int characterXySize) {
        String imagePath = model.getImageBase(); 
        InputStream imageStream = OpponentRuntimeFactory.class.getResourceAsStream(imagePath);

        ImageView imageView;
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imageView = new ImageView(image);
        } else {
            imageView = new ImageView(); 
            imageView.setFitHeight(characterXySize);
            imageView.setFitWidth(characterXySize);
            imageView.setPreserveRatio(true);
        }

        // Make it behave like the FXML 
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(characterXySize); 
        imageView.setSmooth(true);
        imageView.setId("character-" + System.nanoTime());
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);

        return imageView;
    }

    private static void validateOrFail(OpponentModel model) {
        BasicDiagnostic diag = new BasicDiagnostic();
        boolean ok = OpponentsValidator.INSTANCE.validate(model, diag, null);
        if (!ok) {
            throw new IllegalStateException("Invalid opponent model: " + ", " + diag.getChildren() + ", " + diag.getMessage());
        }
    }
}
