package main.game.maze.opponents;

import static org.junit.jupiter.api.Assertions.*;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import main.game.maze.difficulties.*;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;


class MaxThreatValidationTest {

  private static final String OCL_DELEGATE_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL";

  @BeforeAll
  static void initDelegates() {
    var uri = OCL_DELEGATE_URI;
    EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.put(
        uri, new OCLInvocationDelegateFactory.Global());
    EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(
        uri, new OCLSettingDelegateFactory.Global());
    EValidator.ValidationDelegate.Registry.INSTANCE.put(
        uri, new OCLValidationDelegateFactory.Global());
  }


  private static void ensureXmiFactoryRegistered() {
    var map = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
    map.putIfAbsent("xmi", new XMIResourceFactoryImpl());
    map.putIfAbsent(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
  }

  @Test
  void sumEffectiveThreat_mustNotExceed_maxThreat() {
    // Ensure packages + factories are initialized
    OpponentsPackage.eINSTANCE.eClass();
    DifficultiesPackage.eINSTANCE.eClass();
    ensureXmiFactoryRegistered();

    // Build model
    OpponentModel model = OpponentsFactory.eINSTANCE.createOpponentModel();
    var easyDifficulty = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
    easyDifficulty.setMaxThreat(10);
    model.setSelectedDifficulty(easyDifficulty);

    Zombie ct1 = OpponentsFactory.eINSTANCE.createZombie();
    ct1.setThreatLevel(3);
    CharacterType ct2 = OpponentsFactory.eINSTANCE.createZombie();
    ct2.setThreatLevel(4);
    model.getCharacterTypes().add(ct1);
    model.getCharacterTypes().add(ct2);

    // (Optional) attach to an in-memory XMI resource
    ResourceSet rs = new ResourceSetImpl();
    // make sure the RS sees your packages (often not necessary, but harmless)
    rs.getPackageRegistry().put(OpponentsPackage.eNS_URI, OpponentsPackage.eINSTANCE);
    rs.getPackageRegistry().put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);

    Resource r = rs.createResource(URI.createURI("memory:/opponentModel.xmi"));
    r.getContents().add(easyDifficulty);
    r.getContents().add(model);

    System.out.println("Max threat is: " + model.getMaxThreat());
    System.out.println("Current threat is: " + model.getGameSetCurrentThreatLevel());
  

    // Validate OK
    var okDiag = Diagnostician.INSTANCE.validate(model);
    assertEquals(Diagnostic.OK, okDiag.getSeverity(), () -> "Expected OK but got: " + okDiag);

    // Exceed limit
    CharacterType ct3 = OpponentsFactory.eINSTANCE.createZombie();
    ct3.setThreatLevel(7);
    model.getCharacterTypes().add(ct3);

    var badDiag = Diagnostician.INSTANCE.validate(model);
    assertTrue(badDiag.getSeverity() >= Diagnostic.ERROR,
        () -> "Expected a validation error when sum exceeds maxThreat, got: " + badDiag);
  }
}


