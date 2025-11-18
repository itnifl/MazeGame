package main.game.maze.runtime.opponents;

import java.io.InputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
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
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import main.game.maze.opponents.util.OpponentsValidator;
import main.game.maze.service.DifficultyService;
import main.game.maze.util.Dialogs;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PumpkinBomberCharacter;
import main.game.maze.characters.ZombieCharacter;
import main.game.maze.GameController;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.EnemyTypes;

/**
 * Factory that instantiates runtime character objects from EMF OpponentModel XMI files.
 * All Node creation and GameController registration happen on the JavaFX Application Thread.
 */
public final class OpponentRuntimeFactory {

    private static final Logger _logger = Logger.getLogger(OpponentRuntimeFactory.class.getName());
    private static final int SPAWN_MARGIN = 20;
    private static volatile boolean xmiFactoryRegistered = false;

    private OpponentRuntimeFactory() { /* utility class */  }


    public static void instantiateFromModel(GameController gameController) {
        instantiateFromModel(gameController, null);
    }

    public static void instantiateFromModel(GameController gameController, Difficulty override) {
        instantiateFromModelInternal(gameController, override);
    }

    /**
     * Instantiate characters from an XMI model and register them with the provided GameController.
     *
     * @param resourcePath  classpath path to the XMI (e.g. "/opponents/instances/classic_zombie.xmi")
     * @param gameController the controller responsible for registering characters and nodes
     */
    public static void instantiateFromModelInternal(GameController gameController, Difficulty setOverrideDifficulty) {
        String resourcePath = OpponentConstants.ZombieModelPath;

        if (resourcePath == null || resourcePath.isBlank()) {
            _logger.warning("instantiateFromModel called with empty resourcePath");
            return;
        }
        if (gameController == null) {
            _logger.warning("instantiateFromModel requires a non-null GameController");
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
                _logger.log(Level.WARNING, "Opponent model resource not found: {0}", resourcePath);
                return;
            }

            URI modelUri = URI.createURI(resourceUrlReference.toString());
            Resource resource = resourceSet.getResource(modelUri, true);
            if (resource == null || resource.getContents().isEmpty()) {
                _logger.log(Level.WARNING, "Loaded resource is empty or null: {0}", resourcePath);
                return;
            }

            Object rootObject;
            try {
                rootObject = resource.getContents().get(0);
            } catch (Exception loadEx) {
                _logger.log(Level.SEVERE, "Failed to load root content: " + resourcePath, loadEx);
                throw loadEx;
            }
            
            if (!(rootObject instanceof OpponentModel)) {
                _logger.log(Level.WARNING, "Root object is not an OpponentModel: {0}", resourcePath);
                return;
            }            
            OpponentModel opponentModel;
            try {
                opponentModel = (OpponentModel) rootObject;
            } catch (Exception loadEx) {
                _logger.log(Level.SEVERE, "Failed to cast root object to OpponentModel: " + resourcePath, loadEx);
                throw loadEx;
            }

            var diff = resolveActiveDifficulty(setOverrideDifficulty, opponentModel);
            if (diff == null) {
                _logger.warning("No selectedDifficulty set; spawning without caps/multipliers.");
            } else {
                _logger.log(Level.INFO, "Selected difficulty: {0}", diff.getClass());
            }

            Map<EnemyTypes, Integer> monsterTypecaps = new EnumMap<>(EnemyTypes.class);
            int maxThreatByDifficulty = Integer.MAX_VALUE;
            double speedMultiplierByDifficulty = 1.0;
            double dmgMultiplierByDifficulty = 1.0;
            boolean instantDeath = false;


            if (diff != null) {
                for (var e : diff.getEnemyMaxCount()) {
                    monsterTypecaps.put(e.getType(), e.getMaxCount());
                }
                maxThreatByDifficulty = diff.getMaxThreat();
                speedMultiplierByDifficulty = diff.getMonstersMovementSpeedMultiplier();
                dmgMultiplierByDifficulty = diff.getMonstersDamageMultiplier();
                instantDeath = diff.isInstantDeath();
            }
            
            AtomicInteger noOfGhostsSpawned = new AtomicInteger(0);
            AtomicInteger noOfZombiesSpawned = new AtomicInteger(0);
            AtomicInteger noOfPumpkinBombersSpawned = new AtomicInteger(0);
            double threatSum = 0.0;
            
            var characterList = opponentModel.getCharacterTypes();            
            double sum = threatSum;  // working copy

            sum = populateRandomWeakCharacters(characterList, 
                monsterTypecaps, maxThreatByDifficulty, gameController,
                noOfGhostsSpawned, noOfZombiesSpawned, noOfPumpkinBombersSpawned,
                speedMultiplierByDifficulty, dmgMultiplierByDifficulty, instantDeath,
                sum);

            sum = populateRandomCharacters(characterList, diff,
                monsterTypecaps, maxThreatByDifficulty, gameController,
                noOfGhostsSpawned, noOfZombiesSpawned, noOfPumpkinBombersSpawned,
                speedMultiplierByDifficulty, dmgMultiplierByDifficulty, instantDeath,
                sum);

            threatSum = sum;
            validateOrFail(opponentModel);
        } catch (Exception loadException) {
            _logger.log(Level.SEVERE, "Failed to load or instantiate opponent model: " + resourcePath + " with " + loadException.getMessage(), loadException);
             Dialogs.showError(
                "Failed to load opponents",
                "The opponent configuration could not be loaded.",
                loadException.getMessage(),
                loadException
            );
            throw loadException;
        }
    }

    private static double populateRandomCharacters(List<CharacterType> characterList, 
        Difficulty diff,
        Map<EnemyTypes, Integer> monsterTypecaps,
            double maxThreatByDifficulty, 
            GameController gameController,
            AtomicInteger noOfGhostsSpawned, 
            AtomicInteger noOfZombiesSpawned, 
            AtomicInteger noOfPumpkinBombersSpawned,
            double speedMultiplierByDifficulty, 
            double dmgMultiplierByDifficulty, 
            boolean instantDeath,
            double sum) {
        
            // Sort once: highest effective threat first
            var byThreatDesc = characterList.stream()
                .filter(CharacterType::isEnabled)
                .sorted(java.util.Comparator.comparingDouble(CharacterType::getEffectiveThreat).reversed())
                .toList();

            while (sum <= maxThreatByDifficulty) {
                final double remaining = maxThreatByDifficulty - sum;

                var charactersFiltered = byThreatDesc.stream()
                    .filter(ct -> ct.getEffectiveThreat() > 0)
                    .filter(ct -> ct.getEffectiveThreat() <= remaining)
                    .filter(ct -> !characterNrCapsIsExceeded(ct, 
                        noOfGhostsSpawned, 
                        noOfZombiesSpawned, 
                        noOfPumpkinBombersSpawned, 
                        monsterTypecaps));

                var next = diff instanceof EasyDifficulty ? charactersFiltered.findFirst() // lowest that fits
                    : charactersFiltered.findAny(); 

                if (next.isEmpty()) break;

                var picked = next.get();

                setCharacterAttributesByDifficulty(picked, 
                    speedMultiplierByDifficulty, 
                    dmgMultiplierByDifficulty, 
                    instantDeath);
                    
                doCharacterRegistration(gameController, 
                    picked, 
                    noOfGhostsSpawned, 
                    noOfZombiesSpawned, 
                    noOfPumpkinBombersSpawned);

                sum += picked.getEffectiveThreat();
            }
        return sum;
    }


    private static double populateRandomWeakCharacters(
        List<CharacterType> characterList, 
        Map<EnemyTypes, Integer> monsterTypecaps,
            double maxThreatByDifficulty, 
            GameController gameController,
            AtomicInteger noOfGhostsSpawned, 
            AtomicInteger noOfZombiesSpawned, 
            AtomicInteger noOfPumpkinBombersSpawned,
            double speedMultiplierByDifficulty, 
            double dmgMultiplierByDifficulty, 
            boolean instantDeath,
            double sum) {

        var byThreatAsc = characterList.stream()
                .filter(CharacterType::isEnabled)
                .sorted(java.util.Comparator.comparingDouble(CharacterType::getEffectiveThreat));

            var typesInModel = byThreatAsc
                .map(ct -> ct.getClass())   
                .distinct()
                .toList();

            for (var t : typesInModel) {
                double remaining = maxThreatByDifficulty - sum;

                var seed = characterList.stream()
                    .filter(CharacterType::isEnabled)
                    .filter(ct -> ct.getClass() == t)
                    .filter(ct -> ct.getEffectiveThreat() > 0)
                    .sorted(java.util.Comparator.comparingDouble(CharacterType::getEffectiveThreat)) // easiest of that type
                    .filter(ct -> ct.getEffectiveThreat() <= remaining)
                    .filter(ct -> !characterNrCapsIsExceeded(ct,
                            noOfGhostsSpawned,
                            noOfZombiesSpawned,
                            noOfPumpkinBombersSpawned,
                            monsterTypecaps))
                    .findFirst();

                if (seed.isPresent()) {
                    var picked = seed.get();
                    setCharacterAttributesByDifficulty(picked, speedMultiplierByDifficulty, dmgMultiplierByDifficulty, instantDeath);
                    doCharacterRegistration(gameController, picked,
                            noOfGhostsSpawned, noOfZombiesSpawned, noOfPumpkinBombersSpawned);
                    sum += picked.getEffectiveThreat();
                }
            }
        return sum;
    }


    private static void doCharacterRegistration(GameController gameController, CharacterType characterType,
            AtomicInteger noOfGhostsSpawned, AtomicInteger noOfZombiesSpawned, AtomicInteger noOfPumpkinBombersSpawned) {
                
            final double spawnX = ThreadLocalRandom.current()
                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, StageConstants.BoardMaxX - SPAWN_MARGIN));
            final double spawnY = ThreadLocalRandom.current()
                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, StageConstants.BoardMaxY - SPAWN_MARGIN));
            
            if (characterType instanceof Zombie z) {
                noOfZombiesSpawned.incrementAndGet();        
                registerZombieCharacter(gameController, spawnX, spawnY, z);
            } else if (characterType instanceof Ghost g) {                            
                noOfGhostsSpawned.incrementAndGet();                    
                registerGhostCharacter(gameController, spawnX, spawnY, g);
            }  else if (characterType instanceof PumpkinBomber b) {
                noOfPumpkinBombersSpawned.incrementAndGet();
                registerPumpkinBomberCharacter(gameController, spawnX, spawnY, b);
            } else {
                _logger.log(Level.FINER, "Skipping unsupported character type: {0}", characterType.getClass().getName());
            }
    }

    private static void registerPumpkinBomberCharacter(GameController gameController, double spawnX, double spawnY,
            PumpkinBomber b) {
        Platform.runLater(() -> {
            try {
                Node graphicsNode = createCharacterGraphics(b, StageConstants.PumpkinBomberCharacterXYSize);
                graphicsNode.setLayoutX(spawnX);
                graphicsNode.setLayoutY(spawnY);
                var character = new PumpkinBomberCharacter(graphicsNode, spawnX, spawnY, b);
                //gameController.registerComputerCharacter(character, graphicsNode); //Need new method to register pumpkin bomber
            } catch (Exception fxException) {
                _logger.log(Level.SEVERE, "Failed to create or register a PumpkinBomberCharacter.", fxException);
            }
        });
    }

    private static void registerGhostCharacter(GameController gameController, double spawnX, double spawnY, Ghost g) {
        Platform.runLater(() -> {
            try {
                Node graphicsNode = createCharacterGraphics(g, StageConstants.GhostCharacterXYSize);
                graphicsNode.setLayoutX(spawnX);
                graphicsNode.setLayoutY(spawnY);
                var character = new GhostCharacter(graphicsNode, spawnX, spawnY, g);
                gameController.registerComputerCharacter(character, graphicsNode);
            } catch (Exception fxException) {
                _logger.log(Level.SEVERE, "Failed to create or register a GhostCharacter.", fxException);
            }
        });
    }

    private static void registerZombieCharacter(GameController gameController, double spawnX,
            double spawnY, Zombie z) {                
        Platform.runLater(() -> {
            try {
                Node graphicsNode = createCharacterGraphics(z, StageConstants.ZombieCharacterXYSize);
                graphicsNode.setLayoutX(spawnX);
                graphicsNode.setLayoutY(spawnY);
                ZombieCharacter zombieCharacter = new ZombieCharacter(graphicsNode, spawnX, spawnY, z);
                gameController.registerComputerCharacter(zombieCharacter, graphicsNode);
            } catch (Exception fxException) {
                _logger.log(Level.SEVERE, "Failed to create or register a ZombieCharacter.", fxException);
            }
        });
    }

    private static void setCharacterAttributesByDifficulty(CharacterType characterType,
            double speedMultiplierByDifficulty, double dmgMultiplierByDifficulty, boolean instantDeath) {

        characterType.setSpeed(characterType.getSpeed() * speedMultiplierByDifficulty);

        if (characterType instanceof Zombie z) {
          if (instantDeath) {
                z.setAttackDamage(Integer.MAX_VALUE);
            } else {
                z.setAttackDamage(Math.max(1, (int)Math.round(z.getAttackDamage() * dmgMultiplierByDifficulty)));
            }
        } else if (characterType instanceof Ghost g) {
            if (instantDeath) {
                g.setAttackDamage(Integer.MAX_VALUE);
            } else {
                g.setAttackDamage(Math.max(1, (int)Math.round(g.getAttackDamage() * dmgMultiplierByDifficulty)));
            }
        } else if (characterType instanceof PumpkinBomber b) {
            if (instantDeath) {
                b.setAttackDamage(Integer.MAX_VALUE);
            } else {
                b.setAttackDamage(Math.max(1, (int)Math.round(b.getAttackDamage() * dmgMultiplierByDifficulty)));
            }
        }
        
    }

    private static boolean characterNrCapsIsExceeded(
        CharacterType characterType, 
        AtomicInteger noOfGhosts, 
        AtomicInteger noOfZombies, 
        AtomicInteger noOfPumpkinBombers,
        Map<EnemyTypes, Integer> monsterTypecaps) {
        EnemyTypes et;
        if (characterType instanceof Ghost)  et = EnemyTypes.GHOST;
        else if (characterType instanceof Zombie) et = EnemyTypes.ZOMBIE;
        else if (characterType instanceof PumpkinBomber) et = EnemyTypes.PUMPKINBOMBER;
        else return true;

        int cap = monsterTypecaps.getOrDefault(et, Integer.MAX_VALUE);

        if (et == EnemyTypes.GHOST && noOfGhosts.get() >= cap)  return true;
        if (et == EnemyTypes.ZOMBIE && noOfZombies.get() >= cap) return true;
        if (et == EnemyTypes.PUMPKINBOMBER && noOfPumpkinBombers.get() >= cap) return true;

        return false;
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

    private static Difficulty resolveActiveDifficulty(Difficulty override, OpponentModel model) {
        Difficulty resolved = null;

        if (override != null) {
            resolved = override;
        } else if (model != null && model.getSelectedDifficulty() != null) {
            resolved = model.getSelectedDifficulty();
        } else {
            try {
                resolved = new DifficultyService().getCurrent();
            } catch (Exception e) {
                _logger.log(Level.WARNING, "Falling back: no difficulty available", e);
                return null;
            }
        }

        // Keep OpponentModel in sync for validators, caps, etc.
        if (model != null && resolved != null && model.getSelectedDifficulty() != resolved) {
            model.setSelectedDifficulty(resolved);
        }

        return resolved;
    }

}
