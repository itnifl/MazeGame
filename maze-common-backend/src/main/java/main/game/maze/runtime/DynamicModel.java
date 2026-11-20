package main.game.maze.runtime;

import java.net.URL;
import java.util.List;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Loads a model with dynamic EMF: the metamodel ({@code .ecore}) is read at runtime and the instance
 * ({@code .xmi}) is validated against it, so the rules live in the model rather than in code. Also
 * reads the values of the loaded objects by feature name.
 */
public final class DynamicModel {

    private DynamicModel() {
    }

    /**
     * Loads and validates the root object of a model.
     *
     * @param anchor      a class whose class loader finds the resources
     * @param ecorePath   classpath path of the metamodel
     * @param modelPath   classpath path of the instance
     * @param description what the model is, for error messages ("items", "levels")
     */
    public static EObject load(Class<?> anchor, String ecorePath, String modelPath, String description) {
        ResourceSet resources = new ResourceSetImpl();
        resources.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
        resources.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        Resource ecore = resources.getResource(uri(anchor, ecorePath, description), true);
        EPackage modelPackage = (EPackage) ecore.getContents().get(0);
        resources.getPackageRegistry().put(modelPackage.getNsURI(), modelPackage);

        Resource model = resources.getResource(uri(anchor, modelPath, description), true);
        if (model.getContents().isEmpty()) {
            throw new IllegalStateException("empty " + description + " model: " + modelPath);
        }
        EObject root = model.getContents().get(0);
        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(root);
        if (diagnostic.getSeverity() == Diagnostic.ERROR) {
            throw new IllegalStateException("invalid " + description + " model " + modelPath + ": " + messages(diagnostic));
        }
        return root;
    }

    private static String messages(Diagnostic diagnostic) {
        StringBuilder text = new StringBuilder();
        for (Diagnostic child : diagnostic.getChildren()) {
            if (child.getSeverity() == Diagnostic.ERROR) {
                text.append(text.isEmpty() ? "" : "; ").append(child.getMessage());
            }
        }
        return text.isEmpty() ? diagnostic.getMessage() : text.toString();
    }

    private static URI uri(Class<?> anchor, String classpathPath, String description) {
        URL url = anchor.getResource(classpathPath);
        if (url == null) {
            throw new IllegalStateException(description + " model resource not found: " + classpathPath);
        }
        return URI.createURI(url.toString());
    }

    @SuppressWarnings("unchecked")
    public static List<EObject> list(EObject owner, String feature) {
        return (List<EObject>) owner.eGet(owner.eClass().getEStructuralFeature(feature));
    }

    @SuppressWarnings("unchecked")
    public static List<String> strings(EObject owner, String feature) {
        return List.copyOf((List<String>) owner.eGet(owner.eClass().getEStructuralFeature(feature)));
    }

    public static Object value(EObject owner, String feature) {
        return owner.eGet(owner.eClass().getEStructuralFeature(feature));
    }

    /** The name of an enum-valued feature. */
    public static String literalName(EObject owner, String feature) {
        Object value = value(owner, feature);
        if (value instanceof EEnumLiteral literal) {
            return literal.getName();
        }
        throw new IllegalStateException("not an enum literal: " + feature + " = " + value);
    }

    /** A text feature, or null when blank / unset. */
    public static String string(EObject owner, String feature) {
        Object value = value(owner, feature);
        return value instanceof String text && !text.isBlank() ? text : null;
    }

    public static int integer(EObject owner, String feature) {
        Object value = value(owner, feature);
        return value instanceof Number number ? number.intValue() : 0;
    }

    public static double decimal(EObject owner, String feature) {
        Object value = value(owner, feature);
        return value instanceof Number number ? number.doubleValue() : 0.0;
    }
}
