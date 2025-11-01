package main.game.maze.opponents;

import static org.junit.jupiter.api.Assertions.*;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.junit.jupiter.api.Test;
import main.game.maze.opponents.*;
import main.game.maze.opponents.util.*; // if you have util
import main.game.maze.difficulties.*;



class MaxThreatValidationTest {

  @Test
  void sumEffectiveThreat_mustNotExceed_maxThreat() {
    // Build a small fixture from your generated EMF API
    OpponentModel model = OpponentsFactory.eINSTANCE.createOpponentModel();
    var easyDifficulty = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
    easyDifficulty.setMaxThreat(10);
    model.setSelectedDifficulty(easyDifficulty);
     
    System.out.println("Using maxThreat on difficulty: " + easyDifficulty.getMaxThreat());
    System.out.println("Using maxThreat on OpponentModel: " + model.getMaxThreat());

    Zombie ct1 = OpponentsFactory.eINSTANCE.createZombie();
    ct1.setThreatLevel(3);    
    CharacterType ct2 = OpponentsFactory.eINSTANCE.createZombie();
    ct2.setThreatLevel(4);

    System.out.println("Adding " + ct1.getThreatLevel() + " threat from ct1");
    model.getCharacterTypes().add(ct1);
    System.out.println("Adding " + ct2.getThreatLevel() + " threat from ct2");
    model.getCharacterTypes().add(ct2);
    double currentThreatLevel = ct1.getThreatLevel() + ct2.getThreatLevel();

    Diagnostic okDiag = Diagnostician.INSTANCE.validate(model);
    assertEquals(Diagnostic.OK, okDiag.getSeverity(), () -> "Expected OK but got: " + okDiag);

    // Now exceed the limit
    CharacterType ct3 = OpponentsFactory.eINSTANCE.createZombie();
    ct3.setThreatLevel(7);
    System.out.println("Adding " + ct3.getThreatLevel() + " threat from ct3");
    model.getCharacterTypes().add(ct3);
    model.setGameSetCurrentThreatLevel(currentThreatLevel + ct3.getThreatLevel());

    Diagnostic badDiag = Diagnostician.INSTANCE.validate(model);
    assertTrue(badDiag.getSeverity() >= Diagnostic.ERROR,
        () -> "Expected a validation error when sum exceeds maxThreat, got: " + badDiag);        
  }
}

