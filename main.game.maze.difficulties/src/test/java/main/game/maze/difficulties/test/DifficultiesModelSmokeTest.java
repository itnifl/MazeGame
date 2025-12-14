package main.game.maze.difficulties.test;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.game.maze.difficulties.DifficultiesPackage;

public class DifficultiesModelSmokeTest {

  @BeforeAll
  static void init() {
    // Register XMI factory and package
    var map = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
    map.putIfAbsent("xmi", new XMIResourceFactoryImpl());
    DifficultiesPackage.eINSTANCE.eClass();
  }

  @Test
  void loadsAndValidatesDifficultyXmi() throws Exception {
    ResourceSet rs = new ResourceSetImpl();
    Resource r = rs.getResource(URI.createURI("./src/test/resources/difficultiesBasic.xmi", true), true);
    r.load(null);
    org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(r);
    
    var diag1 = Diagnostician.INSTANCE.validate(r.getContents().get(0));
    System.out.println("Diag: " + diag1);

    assertTrue(r.getErrors().isEmpty(), "Load errors: " + r.getErrors());
    assertTrue(r.getWarnings().isEmpty(), "Load warnings: " + r.getWarnings());

    assertFalse(r.getContents().isEmpty(), "Empty resource");

    var root = r.getContents().get(0);
    var diag2 = Diagnostician.INSTANCE.validate(root);
    assertEquals(Diagnostic.OK, diag2.getSeverity(), () -> "Validation failed: " + diag2);
  }
}

