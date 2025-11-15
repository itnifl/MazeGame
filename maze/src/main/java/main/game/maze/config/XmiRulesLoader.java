package main.game.maze.config;

import java.net.URL;
import java.util.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

// Loads XMI with EMF and builds ProfileRules for each mode
// It translates the xmi files to easier data for the Resolver
import main.game.maze.difficulties.*; // DifficultyGameData, DifficultyBase, EnemyMaxCount, DifficultyPackage
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;

public final class XmiRulesLoader {

    //Path inside src/main/resources
  private static final String DIFF_PATH = "xmi/difficulties/difficulties.xmi";
  private static final String OPP_PATH  = "xmi/opponents/opponentModel.xmi";

  public Map<String, ProfileRules> load() {
    // initialize EMF to read XMI
    ResourceSet rs = new ResourceSetImpl();
    rs.getResourceFactoryRegistry().getExtensionToFactoryMap()
      .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

    // Registers EPackages (so EMF acknowledge the classes)
    DifficultiesPackage.eINSTANCE.eClass();
    OpponentsPackage.eINSTANCE.eClass();

    // URIs built from classpath and resource load
    URI diffUri = uriFromResource(DIFF_PATH);
    URI oppUri  = uriFromResource(OPP_PATH);

    DifficultyGameData diffRoot = (DifficultyGameData) rs.getResource(diffUri, true).getContents().get(0);
    OpponentModel oppRoot       = (OpponentModel)       rs.getResource(oppUri,  true).getContents().get(0);

    // avg threat per type (only allowed enemies)
    Map<EnemyType, Double> avgThreat = avgThreatByType(oppRoot);

    // Builds ProfileRules for each difficulty level declared in difficulties.xmi
    Map<String, ProfileRules> out = new LinkedHashMap<>();
    for (Difficulty d : diffRoot.getDifficulties()) {

        // xmi:id (easy/normal/hard)
        String name = ((XMIResource) d.eResource()).getID(d); // "easy","normal","hard"
      // Caps per type from enemyMaxCount
      Map<EnemyType,Integer> caps = new EnumMap<>(EnemyType.class);
      for (EnemyMaxCount emc : d.getEnemyMaxCount()) {
        caps.put(EnemyType.valueOf(emc.getType().name()), Math.max(0, emc.getMaxCount()));
      }
      // Ratios derived from caps > 0 -> normalized to sum 1
      int capSum = caps.values().stream().mapToInt(Integer::intValue).sum();
      Map<EnemyType,Double> ratios = new EnumMap<>(EnemyType.class);
      if (capSum > 0) caps.forEach((t,c)-> { if (c>0) ratios.put(t, c/(double)capSum); });

      // estimated enemyCount : maxThreat/ meanThreat
      double meanThreat = avgThreat.values().stream().mapToDouble(x->x).average().orElse(1.0);
      int enemyCount = Math.max(1, (int)Math.floor(d.getMaxThreat() / meanThreat));

      // countsOverride empty since xmi implementation is needed
      out.put(name, new ProfileRules(name, enemyCount, ratios, Map.of(), caps));
    }
    return out;
  }

  /* ================= Helpers ================= */

  private static URI uriFromResource(String path) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(path);
    if (url == null) throw new IllegalStateException("No se encuentra en classpath: " + path);
    return URI.createURI(url.toString());
  }

  private static Map<EnemyType, Double> avgThreatByType(OpponentModel oppRoot) {
    Map<EnemyType, List<Double>> vals = new EnumMap<>(EnemyType.class);
    for (CharacterType c : oppRoot.getCharacterTypes()) {
      if (!c.isEnabled()) continue;
      EnemyType t = (c instanceof Ghost) ? EnemyType.GHOST
                   : (c instanceof Zombie) ? EnemyType.ZOMBIE
                   : EnemyType.PUMPKINBOMBER;
      vals.computeIfAbsent(t,k->new ArrayList<>()).add(c.getThreatLevel());
    }
    Map<EnemyType, Double> avg = new EnumMap<>(EnemyType.class);
    vals.forEach((t, list)-> avg.put(t, Math.max(0.1, list.stream().mapToDouble(x->x).average().orElse(1.0))));
    for (EnemyType t : EnemyType.values()) avg.putIfAbsent(t, 1.0);
    return avg;
  }


}
