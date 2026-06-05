package main.game.maze.opponents;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import main.game.maze.opponents.util.OpponentsValidator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


public class ModelLoadSmokeTest {

  private static final String OCL_DELEGATE_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL";

  @BeforeAll
  static void setup() {
        var uri = OCL_DELEGATE_URI;
    EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.put(
        uri, new OCLInvocationDelegateFactory.Global());
    EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(
        uri, new OCLSettingDelegateFactory.Global());
    EValidator.ValidationDelegate.Registry.INSTANCE.put(
        uri, new OCLValidationDelegateFactory.Global());
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
    OpponentsPackage.eINSTANCE.eClass(); // ensure package is registered
  }

  @Test
  void loadsAndThrowsErrorOnAllIncorrectSamples() throws Exception {
    List<String> samples = List.of(
      "./src/test/java/main/game/maze/opponents/opponentModelFailMaxThreat.xmi"
      // add more sample paths here if you have them
    );
    for (String p : samples) {
      ResourceSet rs = new ResourceSetImpl();
      Resource r = rs.getResource(URI.createURI(p, true), true);
      r.load(null);

      OpponentsPackage.eINSTANCE.eClass();
      for (EObject obj : r.getContents()) {
          for (EStructuralFeature f : obj.eClass().getEAllStructuralFeatures()) {
              obj.eGet(f);
          }
      }

      var root = r.getContents().get(0);
      if (root instanceof OpponentModel model) {
          // Force derived OCL attributes to compute
          model.getMaxThreat();
      }

      assertTrue(r.getErrors().isEmpty(), "Load errors in " + p + ": " + r.getErrors());
      assertTrue(r.getWarnings().isEmpty(), "Load warnings in " + p + ": " + r.getWarnings());

      var diag = new BasicDiagnostic();
      boolean status = OpponentsValidator.INSTANCE.validate(root, diag, null);
      assertFalse(status, "Expected validation to fail, but validation succeeded for " + p + ": " + diag);
    }
  }

  @Test
  void loadsAndValidatesAllCorrectSamples() throws Exception {
    List<String> samples = List.of(
      "./src/test/java/main/game/maze/opponents/opponentsBasic.xmi",
      "./src/test/java/main/game/maze/opponents/opponentModelSuccessMaxThreat.xmi"
      // add more sample paths here if you have them
    );
    for (String p : samples) {
      ResourceSet rs = new ResourceSetImpl();
      Resource r = rs.getResource(URI.createURI(p, true), true);
      r.load(null);

      OpponentsPackage.eINSTANCE.eClass();
      for (EObject obj : r.getContents()) {
          for (EStructuralFeature f : obj.eClass().getEAllStructuralFeatures()) {
              obj.eGet(f);
          }
      }

      var root = r.getContents().get(0);
      if (root instanceof OpponentModel model) {
          // Force derived OCL attributes to compute
          model.getMaxThreat();
      }

      assertTrue(r.getErrors().isEmpty(), "Load errors in " + p + ": " + r.getErrors());
      assertTrue(r.getWarnings().isEmpty(), "Load warnings in " + p + ": " + r.getWarnings());

      var diag = new BasicDiagnostic();
      boolean ok = OpponentsValidator.INSTANCE.validate(root, diag, null);
      assertTrue(ok, "Validation failed for " + p + ": " + diag);
    }
  }
}


