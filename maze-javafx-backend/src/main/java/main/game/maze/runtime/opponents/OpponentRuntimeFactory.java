package main.game.maze.runtime.opponents;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
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
import org.eclipse.emf.ecore.util.EcoreUtil;

import main.game.maze.constants.OpponentConstants;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.opponents.util.EnemySpawnPlanner;
import main.game.maze.opponents.util.OpponentsValidator;
import main.game.maze.service.DifficultyService;
import main.game.maze.generated.CharacterRegistrar;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PumpkinBomberCharacter;
import main.game.maze.characters.ZombieCharacter;
import main.game.maze.App;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyTypes;

/**
 * Factory that instantiates runtime character objects from EMF OpponentModel XMI files.
 * All Node creation and GameController registration happen on the JavaFX Application Thread.
 */
public final class OpponentRuntimeFactory {

    private static final Logger _logger = Logger.getLogger(OpponentRuntimeFactory.class.getName());
    private static final int SPAWN_MARGIN = 20;

    private OpponentRuntimeFactory() { /* utility class */  }


    public static void instantiateFromModel(EnemyRegistrar registrar) {
        instantiateFromModel(registrar, null);
    }

    public static void instantiateFromModel(EnemyRegistrar registrar, Difficulty override) {
        instantiateFromModelInternal(registrar, override);
    }

    /**
     * Instantiate characters from an XMI model and register them with the provided {@link EnemyRegistrar}.
     *
     * @param registrar the registrar responsible for placing spawned characters into the scene
     */
    public static void instantiateFromModelInternal(EnemyRegistrar registrar, Difficulty setOverrideDifficulty) {

        /** REFACTOR START Extract into XmiRulesLoader  line 73-161 **/
        String resourcePath = OpponentConstants.ZombieModelPath;

        if (resourcePath == null || resourcePath.isBlank()) {
            _logger.warning("instantiateFromModel called with empty resourcePath");
            return;
        }
        if (registrar == null) {
            _logger.warning("instantiateFromModel requires a non-null EnemyRegistrar");
            return;
        }
        // should I use the RunComposition.java instead o delete it?
        var loader = new main.game.maze.config.service.XmiRulesLoader();
            OpponentModel opponentModel = loader.loadOpponentModelFromClasspath(
            main.game.maze.constants.OpponentConstants.ZombieModelPath);   
        /* REFACTOR END Extract into XmiRulesLoader line 73-137*/

        /* REFACTOR START Composition Resolver line 137-161 */
        var diff = resolveActiveDifficulty(setOverrideDifficulty, opponentModel);
        if (diff == null) {
            _logger.warning("No selectedDifficulty set; spawning with default caps/multipliers.");
        } else {
            _logger.log(Level.INFO, "Selected difficulty: {0}", diff.getClass());
        }
        // 1) Key for the profile (e.g., "easy", "normal", "hard")
        String key = diff != null
                ? diff.eClass().getName().toLowerCase(java.util.Locale.ROOT)
                : "default";

        // 2) Build caps from Difficulty (shared with libGDX via EnemySpawnPlanner)
        EnumMap<EnemyTypes, Integer> caps = new EnumMap<>(EnemySpawnPlanner.capsFromDifficulty(diff));
        
        // 3) Choose a total enemyCount and ratios for the resolver
        //    - enemyCount: use sum(caps) as a reasonable default 
        //    - ratios: uniform distribution among types present in caps (or among all types if caps empty)
        int enemyCount = caps.values().stream().mapToInt(Integer::intValue).sum();
        if (enemyCount <= 0) enemyCount = 10;

        EnumSet<EnemyTypes> presentTypes = caps.isEmpty()
                ? EnumSet.allOf(EnemyTypes.class)
                : EnumSet.copyOf(caps.keySet());

        EnumMap<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
        double share = presentTypes.isEmpty() ? 0.0 : (1.0 / presentTypes.size());
        for (EnemyTypes t : presentTypes) {
            ratios.put(t, share);
        }

        main.game.maze.config.service.ProfileRules pr =
        new main.game.maze.config.service.ProfileRules(
                key,                // name
                enemyCount,         // enemyCount
                ratios,             // ratios
                Map.of(),           // countsOverride (empty -> ratios used)
                caps                // caps
        );

        Map<String, main.game.maze.config.service.ProfileRules> profiles = Map.of(key, pr);


        main.game.maze.config.service.CompositionResolver resolver =
        new main.game.maze.config.service.CompositionResolverImpl(profiles);
        
        Map<EnemyTypes, Integer> target = resolver.resolve(key);

        final int maxThreatByDifficulty = diff != null ? diff.getMaxThreat() : Integer.MAX_VALUE;
        final double speedMultiplierByDifficulty = diff != null ? diff.getMonstersMovementSpeedMultiplier() : 1d;
        final double dmgMultiplierByDifficulty = diff != null ? diff.getMonstersDamageMultiplier() : 1d;
        final boolean instantDeath = diff != null && diff.isInstantDeath();

        /* REFACTOR END Composition Resolver line 137-161 */
        AtomicInteger noOfGhostsSpawned = new AtomicInteger(0);
        AtomicInteger noOfZombiesSpawned = new AtomicInteger(0);
        AtomicInteger noOfPumpkinBombersSpawned = new AtomicInteger(0);
      
        var characterList = opponentModel.getCharacterTypes();

        // Reset per-spawn aggressive counter and stash the active difficulty so
        // the per-character registration path can apply the aggressive cap from
        // EnemySpawnPlanner.aggressiveCapForDifficulty without changing the
        // method signature of the generated CharacterRegistrar dispatch.
        currentSpawnDifficulty = diff;
        aggressiveAssignedThisSpawn.set(0);


        double threatSum = spawnByTarget(
            characterList,  // candidates
            target,                             // resolvers decision
            caps,                               // per-type spawn ceilings (F8)
            maxThreatByDifficulty,              // threat limit per difficulty
            speedMultiplierByDifficulty,
            dmgMultiplierByDifficulty,
            instantDeath,
            registrar,
            noOfGhostsSpawned, noOfZombiesSpawned, noOfPumpkinBombersSpawned
        );

        _logger.log(Level.INFO, "Spawned characters - Ghosts: {0}, Zombies: {1}, PumpkinBombers: {2}, Total Threat: {3} vs Max Threat: {4}",
            new Object[] {
                noOfGhostsSpawned.get(),
                noOfZombiesSpawned.get(),
                noOfPumpkinBombersSpawned.get(),
                threatSum,
                maxThreatByDifficulty
            }
        );

        validateOrFail(opponentModel);
         
}

/**
 * Spawns exactly the counts requested by 'target' while honoring maxThreat.
 * Picks candidates of each type from the model (ascending threat to fit easier).
 */
private static double spawnByTarget(
        List<CharacterType> all,
        Map<EnemyTypes, Integer> target,
        Map<EnemyTypes, Integer> caps,
        int maxThreat,
        double speedMult,
        double dmgMult,
        boolean instantDeath,
        EnemyRegistrar registrar,
        AtomicInteger spawnedGhosts,
        AtomicInteger spawnedZombies,
        AtomicInteger spawnedPumpkins
) {
    // Prepare consumable lists per type (ordered by ascending threat)"
    // Build pools only for types that have at least one *enabled* instance in `all`.
    Map<EnemyTypes, List<CharacterType>> poolofAvailableEnemies = new EnumMap<>(EnemyTypes.class);

    for (EnemyTypes t : EnemyTypes.values()) {
        boolean anyEnabled = all.stream()
            .filter(CharacterType::isEnabled)
            .anyMatch(ct ->
                (t == EnemyTypes.ZOMBIE        && ct instanceof Zombie) ||
                (t == EnemyTypes.GHOST         && ct instanceof Ghost) ||
                (t == EnemyTypes.PUMPKINBOMBER && ct instanceof PumpkinBomber)
            );

        if (anyEnabled) {
            poolofAvailableEnemies.put(t, new ArrayList<>());
        }
    }


    all.stream()
       .filter(ct -> ct.isEnabled())
       .sorted(java.util.Comparator.comparingDouble(CharacterType::getEffectiveThreat))
       .forEach(ct -> {
           if (ct instanceof Zombie) poolofAvailableEnemies.get(EnemyTypes.ZOMBIE).add(ct);
           else if (ct instanceof Ghost) poolofAvailableEnemies.get(EnemyTypes.GHOST).add(ct);
           else if (ct instanceof PumpkinBomber) poolofAvailableEnemies.get(EnemyTypes.PUMPKINBOMBER).add(ct);
       });

    double threat = 0.0;

    for (Map.Entry<EnemyTypes, Integer> e : target.entrySet()) {
        EnemyTypes type = e.getKey();
        int requested = Math.max(0, e.getValue());
        int toSpawn = EnemySpawnPlanner.clampToCapLimit(type, requested, caps);
        if (toSpawn < requested) {
            _logger.log(Level.INFO,
                "EnemyMaxCount cap applied: type={0}, requested={1}, capped to={2}",
                new Object[] { type, requested, toSpawn });
        }
        var candidates = poolofAvailableEnemies.getOrDefault(type, new ArrayList<CharacterType>());

        if (candidates.isEmpty()) {
            continue;
        }

        for (int i = 0; i < toSpawn; i++) {
            double remaining = maxThreat - threat;
            if (remaining <= 0) return threat;

            // Shuffle candidates for randomised tie-breaking when multiple
            // templates share the minimum effective threat.
            List<CharacterType> shuffled = new ArrayList<>(candidates);
            Collections.shuffle(shuffled, ThreadLocalRandom.current());

            // Select the cheapest fitting template (minimum effectiveThreat that
            // still fits in the remaining budget). Picking the smallest fit prevents
            // early over-spending that would block later slots from being filled.
            CharacterType bestFit = null;
            double minFitThreat = Double.MAX_VALUE;
            for (CharacterType template : shuffled) {
                double effThreat = template.getEffectiveThreat();
                if (effThreat > 0 && effThreat <= remaining && effThreat < minFitThreat) {
                    bestFit = template;
                    minFitThreat = effThreat;
                }
            }
            CharacterType picked = bestFit != null ? EcoreUtil.copy(bestFit) : null;

            if (picked == null) break; // no candidate fits within the remaining threat budget

            // apply difficulty multipliers
            setCharacterAttributesByDifficulty(picked, speedMult, dmgMult, instantDeath);

            // register in game (updates per-type counters)
            doCharacterRegistration(registrar, picked, spawnedGhosts, spawnedZombies, spawnedPumpkins);

            threat += picked.getEffectiveThreat();
        }

    }
    return threat;
}



    private static final java.util.concurrent.atomic.AtomicInteger aggressiveAssignedThisSpawn =
            new java.util.concurrent.atomic.AtomicInteger(0);
    private static volatile Difficulty currentSpawnDifficulty;

    /**
     * Delegates character registration to the generated CharacterRegistrar.
     * This eliminates manual instanceof chains - the generated code handles dispatch.
     */
    private static void doCharacterRegistration(EnemyRegistrar registrar, CharacterType characterType,
            AtomicInteger noOfGhostsSpawned, AtomicInteger noOfZombiesSpawned, AtomicInteger noOfPumpkinBombersSpawned) {
                
            final double spawnX = ThreadLocalRandom.current()
                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, App.getBoardMaxX() - SPAWN_MARGIN));
            final double spawnY = ThreadLocalRandom.current()
                .nextInt(SPAWN_MARGIN, Math.max(SPAWN_MARGIN + 1, App.getBoardMaxY() - SPAWN_MARGIN));
            
            BehaviorType runtimeBehavior = EnemySpawnPlanner.resolveRuntimeBehaviorWithAggressiveCap(
                    characterType.getBehavior(),
                    currentSpawnDifficulty,
                    aggressiveAssignedThisSpawn.get(),
                    ThreadLocalRandom.current());
            if (runtimeBehavior == BehaviorType.AGGRESSIVE) {
                aggressiveAssignedThisSpawn.incrementAndGet();
            }
            characterType.setBehavior(runtimeBehavior);
            _logger.log(Level.FINE, "Assigned {0} behavior to {1}",
                    new Object[] { runtimeBehavior, characterType.getClass().getSimpleName() });

            // Use generated CharacterRegistrar for type-safe dispatch
            CharacterRegistrar.register(
                characterType,
                // Ghost handler
                g -> {
                    noOfGhostsSpawned.incrementAndGet();
                    Point2D resolved = registrar.resolveEnemySpawnPosition(
                            spawnX,
                            spawnY,
                            StageConstants.GhostCharacterXYSize);
                    registerGhostCharacter(registrar, resolved.getX(), resolved.getY(), g);
                },
                // Zombie handler
                z -> {
                    noOfZombiesSpawned.incrementAndGet();
                    Point2D resolved = registrar.resolveEnemySpawnPosition(
                            spawnX,
                            spawnY,
                            StageConstants.ZombieCharacterXYSize);
                    registerZombieCharacter(registrar, resolved.getX(), resolved.getY(), z);
                },
                // PumpkinBomber handler
                b -> {
                    noOfPumpkinBombersSpawned.incrementAndGet();
                    Point2D resolved = registrar.resolveEnemySpawnPosition(
                            spawnX,
                            spawnY,
                            StageConstants.PumpkinBomberCharacterXYSize);
                    registerPumpkinBomberCharacter(registrar, resolved.getX(), resolved.getY(), b);
                }
            );
    }

    private static void registerPumpkinBomberCharacter(EnemyRegistrar registrar, double spawnX, double spawnY,
            PumpkinBomber b) {
        Platform.runLater(() -> {
            try {
                Node graphicsNode = createCharacterGraphics(b, StageConstants.PumpkinBomberCharacterXYSize);
                graphicsNode.setLayoutX(spawnX);
                graphicsNode.setLayoutY(spawnY);
                new PumpkinBomberCharacter(graphicsNode, spawnX, spawnY, b);
                // PumpkinBomber does not yet implement IMovingComputerCharacter — registration deferred
            } catch (Exception fxException) {
                _logger.log(Level.SEVERE, "Failed to create or register a PumpkinBomberCharacter.", fxException);
            }
        });
    }

    private static void registerGhostCharacter(EnemyRegistrar registrar, double spawnX, double spawnY, Ghost g) {
        Platform.runLater(() -> {
            try {
                Node graphicsNode = createCharacterGraphics(g, StageConstants.GhostCharacterXYSize);
                graphicsNode.setLayoutX(spawnX);
                graphicsNode.setLayoutY(spawnY);
                var character = new GhostCharacter(graphicsNode, spawnX, spawnY, g);
                registrar.registerComputerCharacter(character, graphicsNode);
            } catch (Exception fxException) {
                _logger.log(Level.SEVERE, "Failed to create or register a GhostCharacter.", fxException);
            }
        });
    }

    private static void registerZombieCharacter(EnemyRegistrar registrar, double spawnX,
            double spawnY, Zombie z) {
        Platform.runLater(() -> {
            try {
                Node graphicsNode = createCharacterGraphics(z, StageConstants.ZombieCharacterXYSize);
                graphicsNode.setLayoutX(spawnX);
                graphicsNode.setLayoutY(spawnY);
                ZombieCharacter zombieCharacter = new ZombieCharacter(graphicsNode, spawnX, spawnY, z);
                registrar.registerComputerCharacter(zombieCharacter, graphicsNode);
            } catch (Exception fxException) {
                _logger.log(Level.SEVERE, "Failed to create or register a ZombieCharacter.", fxException);
            }
        });
    }

    /**
     * Applies difficulty multipliers to character attributes.
     * Scales speed by the given multiplier and applies damage multiplier
     * or instant death depending on difficulty configuration.
     */
    private static void setCharacterAttributesByDifficulty(CharacterType characterType,
            double speedMultiplierByDifficulty, double dmgMultiplierByDifficulty, boolean instantDeath) {
        EnemySpawnPlanner.applyDifficultyAttributes(
                characterType,
                speedMultiplierByDifficulty,
                dmgMultiplierByDifficulty,
                instantDeath);
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
