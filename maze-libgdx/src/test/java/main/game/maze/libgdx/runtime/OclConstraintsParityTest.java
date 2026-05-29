package main.game.maze.libgdx.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;
import main.game.maze.runtime.OclBootstrap;

/**
 * Verifies that OCL-based model constraints (maxThreat invariant on the
 * opponent model, non-negative maxThreat on Difficulty) behave identically
 * when initialized via {@link OclBootstrap} from the libGDX runtime path
 * as they do from the JavaFX path. Mirrors:
 *   maze/src/test/java/main/game/maze/runtime/OclBootstrapTest.java
 *   main.game.maze.opponents/.../MaxThreatValidationTest.java
 *   main.game.maze.difficulties/.../MaxThreatInvariantTest.java
 */
class OclConstraintsParityTest {

    private static final String CLASSIC_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL";
    private static final String PIVOT_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";

    @BeforeAll
    static void bootstrap() {
        OclBootstrap.init();
        OpponentsPackage.eINSTANCE.eClass();
        DifficultiesPackage.eINSTANCE.eClass();
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
                .putIfAbsent("xmi", new XMIResourceFactoryImpl());
    }

    @Test
    void oclBootstrapRegistersClassicAndPivotDelegatesForLibgdxRuntime() {
        assertNotNull(EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.get(CLASSIC_URI));
        assertNotNull(EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.get(CLASSIC_URI));
        assertNotNull(EValidator.ValidationDelegate.Registry.INSTANCE.get(CLASSIC_URI));

        assertNotNull(EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.get(PIVOT_URI));
        assertNotNull(EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.get(PIVOT_URI));
        assertNotNull(EValidator.ValidationDelegate.Registry.INSTANCE.get(PIVOT_URI));
    }

    @Test
    void sumOfThreatLevels_mustNotExceedMaxThreat() {
        OpponentModel model = OpponentsFactory.eINSTANCE.createOpponentModel();
        var difficulty = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        difficulty.setMaxThreat(10);
        model.setSelectedDifficulty(difficulty);

        Zombie a = OpponentsFactory.eINSTANCE.createZombie();
        a.setThreatLevel(3);
        Zombie b = OpponentsFactory.eINSTANCE.createZombie();
        b.setThreatLevel(4);
        model.getCharacterTypes().add(a);
        model.getCharacterTypes().add(b);

        ResourceSet rs = new ResourceSetImpl();
        rs.getPackageRegistry().put(OpponentsPackage.eNS_URI, OpponentsPackage.eINSTANCE);
        rs.getPackageRegistry().put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);
        Resource r = rs.createResource(URI.createURI("memory:/libgdx-opponents.xmi"));
        r.getContents().add(difficulty);
        r.getContents().add(model);

        // Force OCL-derived attributes to materialize before validation (parity
        // with main.game.maze.opponents/.../MaxThreatValidationTest.java).
        model.getMaxThreat();
        model.getGameSetCurrentThreatLevel();

        var ok = Diagnostician.INSTANCE.validate(model);
        assertEquals(Diagnostic.OK, ok.getSeverity(),
                () -> "Within budget should validate OK, got: " + ok);

        CharacterType over = OpponentsFactory.eINSTANCE.createZombie();
        ((Zombie) over).setThreatLevel(7);
        model.getCharacterTypes().add(over);

        var bad = Diagnostician.INSTANCE.validate(model);
        assertTrue(bad.getSeverity() >= Diagnostic.ERROR,
                () -> "Sum exceeding maxThreat must fail validation, got: " + bad);
    }

    @Test
    void difficultyMaxThreat_belowZero_failsValidation() {
        ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.createResource(URI.createURI("memory:/libgdx-bad-difficulty.xmi"));
        var diff = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        diff.setMaxThreat(-1);
        var root = DifficultiesFactory.eINSTANCE.createDifficultyGameData();
        root.getDifficulties().add(diff);
        r.getContents().add(root);

        var diag = Diagnostician.INSTANCE.validate(root);
        assertTrue(diag.getSeverity() >= Diagnostic.ERROR,
                () -> "Negative maxThreat must fail validation, got: " + diag);
    }
}
