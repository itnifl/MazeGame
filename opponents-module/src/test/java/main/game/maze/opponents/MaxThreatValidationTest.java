package main.game.maze.opponents;

import static org.junit.jupiter.api.Assertions.*;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.junit.jupiter.api.Test;


class MaxThreatValidationTest {

  @Test
  void sumEffectiveThreat_mustNotExceed_maxThreat() {
    // Build a small fixture from your generated EMF API
    OpponentModel model = OpponentsFactory.eINSTANCE.createOpponentModel();
    //model.setMaxThreat(10);
     
    
    Zombie ct1 = OpponentsFactory.eINSTANCE.createZombie();
    ct1.setThreatLevel(3);    
    CharacterType ct2 = OpponentsFactory.eINSTANCE.createZombie();
    ct2.setThreatLevel(4);

    model.getCharacterTypes().add(ct1);
    model.getCharacterTypes().add(ct2);

    Diagnostic okDiag = Diagnostician.INSTANCE.validate(model);
    assertEquals(Diagnostic.OK, okDiag.getSeverity(), () -> "Expected OK but got: " + okDiag);

    // Now exceed the limit
    CharacterType ct3 = OpponentsFactory.eINSTANCE.createZombie();
    ct3.setThreatLevel(7);
    model.getCharacterTypes().add(ct3);

    Diagnostic badDiag = Diagnostician.INSTANCE.validate(model);
    assertTrue(badDiag.getSeverity() >= Diagnostic.ERROR,
        () -> "Expected a validation error when sum exceeds maxThreat, got: " + badDiag);        
  }
}

