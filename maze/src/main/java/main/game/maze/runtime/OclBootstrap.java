package main.game.maze.runtime;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.ecore.delegate.OCLDelegateDomainFactory;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;

import org.eclipse.ocl.ecore.delegate.OCLDelegateDomain;

public final class OclBootstrap {
    private static boolean initialized = false;

    public static synchronized void init() {
        if (initialized) return;
        initialized = true;

        var uri = OCLDelegateDomain.OCL_DELEGATE_URI; // "http://www.eclipse.org/emf/2002/Ecore/OCL"
            EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.put(
                uri, new OCLInvocationDelegateFactory.Global());
            EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(
                uri, new OCLSettingDelegateFactory.Global());
            EValidator.ValidationDelegate.Registry.INSTANCE.put(
                uri, new OCLValidationDelegateFactory.Global());
        

        EPackage.Registry.INSTANCE.put(
            "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
            new OCLDelegateDomainFactory()
        );
    }
}
