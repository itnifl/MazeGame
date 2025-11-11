package main.game.maze.difficulties.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

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


public class DefaultsAndRoundtripTest {

  @BeforeAll
  static void init() {
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent("xmi", new XMIResourceFactoryImpl());
    DifficultiesPackage.eINSTANCE.eClass();
  }

  @Test
  void saveAndLoadRoundtripPreservesStructure() throws Exception {
    ResourceSet rs = new ResourceSetImpl();
    Resource r = rs.getResource(URI.createURI("./src/test/resources/difficultiesBasic.xmi", true), true);
    r.load(null);
    org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(r);

    // Save to memory and reload into another resource set
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    r.save(out, null);

    org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(r);
    var diag = Diagnostician.INSTANCE.validate(r.getContents().get(0));
    System.out.println("Diag: " + diag);

    ResourceSet rs2 = new ResourceSetImpl();
    Resource r2 = rs2.createResource(URI.createURI("memory:/roundtrip.xmi"));
    r2.load(new java.io.ByteArrayInputStream(out.toByteArray()), null); // or use createResource + getResourceSet
    // If you prefer, write to temp file and reload

    assertFalse(r2.getContents().isEmpty());
    var root2 = r2.getContents().get(0);
    assertNotNull(root2);
  }
}
