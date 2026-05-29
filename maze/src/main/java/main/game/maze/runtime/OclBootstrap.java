package main.game.maze.runtime;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;

public final class OclBootstrap {
    private static boolean initialized = false;
    private static final String OCL_DELEGATE_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL";
    // Some ecores in this repo (e.g. main.game.maze.behaviour/movements.ecore) declare their
    // invocation/setting/validation delegates under the Pivot URI. Pivot itself is not on the
    // runtime classpath, so we alias the Pivot URI to the classic OCL factories. The constraint
    // expressions used here are within the classic OCL subset (oclIsKindOf, oclAsType, ->any,
    // let/in, if/then/else/endif), so this keeps Pivot-annotated invariants/derivations active
    // at runtime instead of silently dropping them.
    private static final String OCL_PIVOT_DELEGATE_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";

    public static synchronized void init() {
        if (initialized) return;
        initialized = true;

        registerFor(OCL_DELEGATE_URI);
        registerFor(OCL_PIVOT_DELEGATE_URI);
    }

    private static void registerFor(String uri) {
        EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.put(
            uri, new OCLInvocationDelegateFactory.Global());
        EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(
            uri, new OCLSettingDelegateFactory.Global());
        EValidator.ValidationDelegate.Registry.INSTANCE.put(
            uri, new OCLValidationDelegateFactory.Global());
    }
}
