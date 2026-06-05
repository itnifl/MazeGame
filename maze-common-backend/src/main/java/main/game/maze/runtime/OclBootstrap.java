package main.game.maze.runtime;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EOperationImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegate;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegate;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.ValidationDelegate;

public final class OclBootstrap {
    private static boolean initialized = false;
    private static final String OCL_DELEGATE_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL";
    private static final String OCL_LPG_DELEGATE_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL/LPG";
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
        registerFor(OCL_LPG_DELEGATE_URI);
        registerFor(OCL_PIVOT_DELEGATE_URI);

        // If any EPackage was loaded BEFORE init() (common in test JVMs where
        // sibling tests touch generated EMF packages first), its EStructuralFeatures
        // will have cached the default SettingDelegate, which throws
        // "settings is null" for OCL-derived attributes. Re-resolve them now that
        // the OCL factories are registered.
        invalidateCachedDelegates();
    }

    private static void registerFor(String uri) {
        EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.put(
            uri, new SafeOCLInvocationDelegateFactory(uri));
        EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(
            uri, new SafeOCLSettingDelegateFactory(uri));
        EValidator.ValidationDelegate.Registry.INSTANCE.put(
            uri, new SafeOCLValidationDelegateFactory(uri));
    }

    private static final class SafeOCLSettingDelegateFactory extends OCLSettingDelegateFactory {
        private SafeOCLSettingDelegateFactory(String uri) {
            super(uri);
        }

        @Override
        public EStructuralFeature.Internal.SettingDelegate createSettingDelegate(EStructuralFeature structuralFeature) {
            EPackage pkg = structuralFeature.getEContainingClass().getEPackage();
            loadDelegateDomain(pkg);
            return super.createSettingDelegate(structuralFeature);
        }
    }

    private static final class SafeOCLInvocationDelegateFactory extends OCLInvocationDelegateFactory {
        private SafeOCLInvocationDelegateFactory(String uri) {
            super(uri);
        }

        @Override
        public EOperation.Internal.InvocationDelegate createInvocationDelegate(EOperation operation) {
            EPackage pkg = operation.getEContainingClass().getEPackage();
            loadDelegateDomain(pkg);
            return new OCLInvocationDelegate(getDelegateDomain(pkg), operation);
        }
    }

    private static final class SafeOCLValidationDelegateFactory extends OCLValidationDelegateFactory {
        private SafeOCLValidationDelegateFactory(String uri) {
            super(uri);
        }

        @Override
        public ValidationDelegate createValidationDelegate(EClassifier classifier) {
            EPackage pkg = classifier.getEPackage();
            loadDelegateDomain(pkg);
            return new OCLValidationDelegate(getDelegateDomain(pkg), classifier);
        }
    }

    private static void invalidateCachedDelegates() {
        for (Object value : EPackage.Registry.INSTANCE.values()) {
            // values() returns raw stored entries; skip unresolved EPackage.Descriptor
            // wrappers to avoid forcing their initialization here.
            if (value instanceof EPackage pkg) {
                resetPackage(pkg);
            }
        }
    }

    private static void resetPackage(EPackage pkg) {
        for (EClassifier classifier : pkg.getEClassifiers()) {
            if (classifier instanceof EClass eClass) {
                for (EStructuralFeature f : eClass.getEStructuralFeatures()) {
                    if (f instanceof EStructuralFeatureImpl impl) {
                        impl.setSettingDelegate(null);
                    }
                }
                for (EOperation op : eClass.getEOperations()) {
                    if (op instanceof EOperationImpl opImpl) {
                        opImpl.setInvocationDelegate(null);
                    }
                }
            }
        }
        for (EPackage sub : pkg.getESubpackages()) {
            resetPackage(sub);
        }
    }
}


