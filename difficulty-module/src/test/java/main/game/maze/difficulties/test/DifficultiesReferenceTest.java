package main.game.maze.difficulties.test;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultyGameData;

public class DifficultiesReferenceTest {

  @BeforeAll
  static void init() {
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent("xmi", new XMIResourceFactoryImpl());
    DifficultiesPackage.eINSTANCE.eClass();
  }

  @Test
  void currentDifficultyHrefResolvesAndHasExpectedMaxThreat() throws Exception {
    ResourceSet rs = new ResourceSetImpl();
    Resource r = rs.getResource(URI.createURI("./src/test/resources/difficultiesBasic.xmi", true), true);
    r.load(null);

    org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(r);
    var diag = Diagnostician.INSTANCE.validate(r.getContents().get(0));
    System.out.println("Diag: " + diag);

    var root = (DifficultyGameData) r.getContents().get(0);

    assertNotNull(root.getCurrentDifficulty(), "currentDifficulty should be resolved");
    Difficulty cur = root.getCurrentDifficulty();
    assertEquals(20, cur.getMaxThreat(), "Normal difficulty maxThreat should be 20");
  }
}
