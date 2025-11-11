package main.game.maze.difficulties.test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultyGameData;
import main.game.maze.difficulties.EnemyMaxCount;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class EnemyCountsTest {

  @BeforeAll
  static void init() {
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent("xmi", new XMIResourceFactoryImpl());
    DifficultiesPackage.eINSTANCE.eClass();
  }

  @Test
  void validateEnemyMaxCountsForNormal() throws Exception {
    ResourceSet rs = new ResourceSetImpl();
    Resource r = rs.getResource(URI.createURI("./src/test/resources/difficultiesBasic.xmi", true), true);
    r.load(null);

    org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(r);

    var diag = Diagnostician.INSTANCE.validate(r.getContents().get(0));
    System.out.println("Diag: " + diag);

    var root = (DifficultyGameData) r.getContents().get(0);
    Difficulty normal = (Difficulty) root.getCurrentDifficulty();

    Map<String,Integer> counts = normal.getEnemyMaxCount().stream()
        .collect(Collectors.toMap(
            emc -> emc.getType().name(),
            EnemyMaxCount::getMaxCount
        ));

    assertEquals(5, counts.get("GHOST").intValue());
    assertEquals(3, counts.get("ZOMBIE").intValue());
    assertEquals(0, counts.get("PUMPKINBOMBER").intValue());
  }
}
