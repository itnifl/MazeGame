package main.game.maze.game.items;

import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Loads the pickup catalogue from the items model with dynamic EMF: the metamodel
 * ({@code items.ecore}) is read at runtime and the instance ({@code items.xmi}) is validated
 * against it, so the item rules live in the model rather than in code.
 */
public final class PickupCatalogLoader {

    public static final String DEFAULT_ECORE = "/xmi/items/items.ecore";
    public static final String DEFAULT_MODEL = "/xmi/items/items.xmi";

    private static volatile PickupCatalog defaultCatalog;

    private PickupCatalogLoader() {
    }

    /** The shipped catalogue (loaded once). */
    public static PickupCatalog loadDefault() {
        PickupCatalog catalog = defaultCatalog;
        if (catalog == null) {
            synchronized (PickupCatalogLoader.class) {
                if (defaultCatalog == null) {
                    defaultCatalog = load(DEFAULT_ECORE, DEFAULT_MODEL);
                }
                catalog = defaultCatalog;
            }
        }
        return catalog;
    }

    /** Loads a catalogue from classpath metamodel and model paths. */
    public static PickupCatalog load(String ecorePath, String modelPath) {
        ResourceSet resources = new ResourceSetImpl();
        resources.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
        resources.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        Resource ecore = resources.getResource(uri(ecorePath), true);
        EPackage itemsPackage = (EPackage) ecore.getContents().get(0);
        resources.getPackageRegistry().put(itemsPackage.getNsURI(), itemsPackage);

        Resource model = resources.getResource(uri(modelPath), true);
        if (model.getContents().isEmpty()) {
            throw new IllegalStateException("empty items model: " + modelPath);
        }
        EObject root = model.getContents().get(0);

        List<PickupCatalog.Cap> caps = new ArrayList<>();
        for (EObject cap : list(root, "caps")) {
            caps.add(new PickupCatalog.Cap(integer(cap, "minBoardWidth"), integer(cap, "maxItems")));
        }
        Map<PickupKind, PickupSpec> specs = new EnumMap<>(PickupKind.class);
        for (EObject item : list(root, "items")) {
            PickupKind kind = PickupKind.valueOf(((EEnumLiteral) literal(item, "kind")).getName());
            specs.put(kind, new PickupSpec(kind, string(item, "displayName"), string(item, "sprite"),
                    string(item, "pickupSound"), string(item, "useSound"), integer(item, "spawnWeight"),
                    integer(item, "amount"), decimal(item, "durationSeconds"), integer(item, "damage"),
                    decimal(item, "range")));
        }
        return new PickupCatalog(decimal(root, "spawnIntervalSeconds"), decimal(root, "pickupRadius"),
                decimal(root, "minDistanceFromPlayer"), caps, specs);
    }

    private static URI uri(String classpathPath) {
        URL url = PickupCatalogLoader.class.getResource(classpathPath);
        if (url == null) {
            throw new IllegalStateException("items model resource not found: " + classpathPath);
        }
        return URI.createURI(url.toString());
    }

    @SuppressWarnings("unchecked")
    private static List<EObject> list(EObject owner, String feature) {
        return (List<EObject>) owner.eGet(owner.eClass().getEStructuralFeature(feature));
    }

    private static Object value(EObject owner, String feature) {
        return owner.eGet(owner.eClass().getEStructuralFeature(feature));
    }

    private static Object literal(EObject owner, String feature) {
        Object value = value(owner, feature);
        if (value instanceof EEnumLiteral) {
            return value;
        }
        throw new IllegalStateException("not an enum literal: " + feature + " = " + value);
    }

    private static String string(EObject owner, String feature) {
        Object value = value(owner, feature);
        return value instanceof String text && !text.isBlank() ? text : null;
    }

    private static int integer(EObject owner, String feature) {
        Object value = value(owner, feature);
        return value instanceof Number number ? number.intValue() : 0;
    }

    private static double decimal(EObject owner, String feature) {
        Object value = value(owner, feature);
        return value instanceof Number number ? number.doubleValue() : 0.0;
    }
}
