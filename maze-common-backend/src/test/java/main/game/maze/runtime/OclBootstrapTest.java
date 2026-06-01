package main.game.maze.runtime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.junit.jupiter.api.Test;

class OclBootstrapTest {

    private static final String CLASSIC_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL";
    private static final String PIVOT_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";

    @Test
    void initRegistersFactoriesForClassicAndPivotUris() {
        OclBootstrap.init();

        assertNotNull(EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.get(CLASSIC_URI));
        assertNotNull(EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.get(CLASSIC_URI));
        assertNotNull(EValidator.ValidationDelegate.Registry.INSTANCE.get(CLASSIC_URI));

        assertNotNull(EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.get(PIVOT_URI));
        assertNotNull(EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.get(PIVOT_URI));
        assertNotNull(EValidator.ValidationDelegate.Registry.INSTANCE.get(PIVOT_URI));
    }
}
