package main.game.maze.opponents;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ecore.delegate.OCLDelegateDomain;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import main.game.maze.opponents.util.OpponentsValidator;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


public class ModelLoadSmokeTest {

  @BeforeAll
  static void setup() {
        var uri = OCLDelegateDomain.OCL_DELEGATE_URI; // "http://www.eclipse.org/emf/2002/Ecore/OCL"
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
  void loadsAndValidatesAllSamples() throws Exception {
    List<String> samples = List.of(
      "./src/test/java/main/game/maze/opponents/opponentsBasic.xmi"
      // add more sample paths here if you have them
    );
    for (String p : samples) {
      ResourceSet rs = new ResourceSetImpl();
      Resource r = rs.getResource(URI.createURI(p, true), true);
      r.load(null);

      assertTrue(r.getErrors().isEmpty(), "Load errors in " + p + ": " + r.getErrors());
      assertTrue(r.getWarnings().isEmpty(), "Load warnings in " + p + ": " + r.getWarnings());

      var diag = new org.eclipse.emf.common.util.BasicDiagnostic();
      var ok = OpponentsValidator.INSTANCE.validate(r.getContents().get(0), diag, null);
      assertTrue(ok, "Validation failed for " + p + ": " + diag);
    }
  }
}
