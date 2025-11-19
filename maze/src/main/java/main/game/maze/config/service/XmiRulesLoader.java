package main.game.maze.config.service;

import java.net.URL;
import java.util.*;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultyGameData;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Loads XMI with EMF and builds ProfileRules for each difficulty.
 * It translates the XMI files into simpler data used by the CompositionResolver.
 */
public final class XmiRulesLoader {

    // Paths inside src/main/resources
    private static final String DIFFICULTIES_XMI_PATH = "xmi/difficulties/difficulties.xmi";
    private static final String OPPONENTS_XMI_PATH = "xmi/opponents/opponentModel.xmi";

    /**
     * Loads difficulty and opponent data from XMI and constructs rules for each profile.
     *
     * @return map of profile name → ProfileRules
     */
    public Map<String, ProfileRules> load() {
        // Initialize EMF to read XMI
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

        // Register EPackages (so EMF knows the generated classes)
        DifficultiesPackage.eINSTANCE.eClass();
        OpponentsPackage.eINSTANCE.eClass();

        // Build URIs from classpath and load resources
        URI difficultiesUri = uriFromResource(DIFFICULTIES_XMI_PATH);
        URI opponentsUri = uriFromResource(OPPONENTS_XMI_PATH);

        DifficultyGameData difficultiesRoot = (DifficultyGameData) resourceSet
                .getResource(difficultiesUri, true)
                .getContents()
                .get(0);

        OpponentModel opponentsRoot = (OpponentModel) resourceSet
                .getResource(opponentsUri, true)
                .getContents()
                .get(0);

        // Average threat per type (only enabled enemies)
        Map<EnemyTypes, Double> averageThreatByType = avgThreatByType(opponentsRoot);

        // Build ProfileRules for each difficulty level declared in difficulties.xmi
        Map<String, ProfileRules> result = new LinkedHashMap<>();

        for (Difficulty difficulty : difficultiesRoot.getDifficulties()) {
            XMIResource resource = (XMIResource) difficulty.eResource();

            // XMI id (for example "easy", "normal", "hard")
            String name = resource.getID(difficulty);

            // Caps per type from enemyMaxCount
            Map<EnemyTypes, Integer> caps = new EnumMap<>(EnemyTypes.class);
            for (EnemyMaxCount maxCount : difficulty.getEnemyMaxCount()) {
                EnemyTypes type = EnemyTypes.valueOf(maxCount.getType().name());
                int cappedValue = Math.max(0, maxCount.getMaxCount());
                caps.put(type, cappedValue);
            }

            // Ratios derived from caps > 0, then normalized to sum to 1
            int capSum = caps.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            Map<EnemyTypes, Double> ratios = new EnumMap<>(EnemyTypes.class);
            if (capSum > 0) {
                caps.forEach((type, cap) -> {
                    if (cap > 0) {
                        ratios.put(type, cap / (double) capSum);
                    }
                });
            }

            // Estimated enemyCount : maxThreat / meanThreat
            double meanThreat = averageThreatByType.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(1.0);

            int estimatedEnemyCount = Math.max(
                    1,
                    (int) Math.floor(difficulty.getMaxThreat() / meanThreat)
            );

            // countsOverride is empty for now (XMI support can be added later)
            Map<EnemyTypes, Integer> countsOverride = Map.of();

            ProfileRules rules = new ProfileRules(
                    name,
                    estimatedEnemyCount,
                    ratios,
                    countsOverride,
                    caps
            );

            result.put(name, rules);
        }

        return result;
    }

    /* ================= Helpers ================= */

    private static URI uriFromResource(String path) {
        URL url = Thread.currentThread()
                .getContextClassLoader()
                .getResource(path);

        if (url == null) {
            throw new IllegalStateException("Resource not found on classpath: " + path);
        }

        return URI.createURI(url.toString());
    }

    private static Map<EnemyTypes, Double> avgThreatByType(OpponentModel opponentModel) {
        Map<EnemyTypes, List<Double>> threatsByType = new EnumMap<>(EnemyTypes.class);

        for (CharacterType character : opponentModel.getCharacterTypes()) {
            if (!character.isEnabled()) {
                continue;
            }

            EnemyTypes type =
                    (character instanceof Ghost) ? EnemyTypes.GHOST
                            : (character instanceof Zombie) ? EnemyTypes.ZOMBIE
                            : EnemyTypes.PUMPKINBOMBER;

            threatsByType
                    .computeIfAbsent(type, key -> new ArrayList<>())
                    .add(character.getThreatLevel());
        }

        Map<EnemyTypes, Double> averageThreat = new EnumMap<>(EnemyTypes.class);
        threatsByType.forEach((type, threatLevels) -> {
            double avg = threatLevels.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(1.0);
            // Ensure there is always some minimal threat
            averageThreat.put(type, Math.max(0.1, avg));
        });

        // Ensure all enemy types have a default value
        for (EnemyTypes type : EnemyTypes.values()) {
            averageThreat.putIfAbsent(type, 1.0);
        }

        return averageThreat;
    }
}
