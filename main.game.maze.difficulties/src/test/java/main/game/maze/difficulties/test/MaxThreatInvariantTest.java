package main.game.maze.difficulties.test;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import main.game.maze.difficulties.DifficultiesPackage;

public class MaxThreatInvariantTest {

  @BeforeAll
  static void init() {
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent("xmi", new XMIResourceFactoryImpl());
    DifficultiesPackage.eINSTANCE.eClass();
  }

  @Test
  void invalidMaxThreatBelowZeroShouldFailValidation() throws Exception {
    // Create a small in-memory model with invalid maxThreat
    var rs = new ResourceSetImpl();
    var r = rs.createResource(URI.createURI("memory:/bad.xmi"));
    var diff = DifficultiesPackage.eINSTANCE.getDifficultiesFactory().createEasyDifficulty();
    diff.setMaxThreat(-1); // invalid
    var root = DifficultiesPackage.eINSTANCE.getDifficultiesFactory().createDifficultyGameData();
    root.getDifficulties().add(diff);
    r.getContents().add(root);

    var diag = Diagnostician.INSTANCE.validate(root);
    // expectation: there should be an error if you added a constraint in Ecore (else adjust to your rule)
    assertTrue(diag.getSeverity() >= Diagnostic.ERROR);
  }
}


