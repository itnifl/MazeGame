/*
 * MazeGame DSL - shared helpers for the tests that keep a DSL source and its generated XMI in step.
 */
package main.game.maze.dsl.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/** Loads metamodels and XMI instances of the repository and compares two instances value by value. */
final class XmiModelSupport {

    private XmiModelSupport() {
    }

    /** Finds a file of the repository by climbing up from the working directory (tests run in their module). */
    static Path repoFile(String relative) {
        Path dir = Path.of("").toAbsolutePath();
        for (int up = 0; up < 6 && dir != null; up++, dir = dir.getParent()) {
            Path candidate = dir.resolve(relative);
            if (Files.exists(candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException("not found from " + Path.of("").toAbsolutePath() + ": " + relative);
    }

    /** The metamodel (an .ecore file of the repository). */
    static EPackage metamodel(String ecorePath) {
        ResourceSetImpl resources = new ResourceSetImpl();
        resources.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
        Resource ecore = resources.getResource(URI.createFileURI(repoFile(ecorePath).toString()), true);
        return (EPackage) ecore.getContents().get(0);
    }

    /** An XMI instance of the metamodel, from text. */
    static Resource loadXmi(EPackage metamodel, String xmi, String name) throws IOException {
        ResourceSetImpl resources = new ResourceSetImpl();
        resources.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        resources.getPackageRegistry().put(metamodel.getNsURI(), metamodel);
        Resource resource = resources.createResource(URI.createURI("mem:/" + name + ".xmi"));
        resource.load(new ByteArrayInputStream(xmi.getBytes(StandardCharsets.UTF_8)), Map.of());
        return resource;
    }

    /** Compares what the game would see: every value, with the metamodel's defaults for what is not set. */
    static void compare(EObject expected, EObject actual, String path, List<String> differences) {
        if (expected.eClass() != actual.eClass()) {
            differences.add(path + ": " + expected.eClass().getName() + " vs " + actual.eClass().getName());
            return;
        }
        for (EStructuralFeature feature : expected.eClass().getEAllStructuralFeatures()) {
            Object a = expected.eGet(feature);
            Object b = actual.eGet(feature);
            String where = path + "." + feature.getName();
            if (feature.isUnsettable() && expected.eIsSet(feature) != actual.eIsSet(feature)) {
                // an explicit value equal to the default is not the same as a value left out (a level then
                // takes it from its base level), so whether it is set must match too
                differences.add(where + ": set " + expected.eIsSet(feature) + " vs set " + actual.eIsSet(feature));
                continue;
            }
            if (feature.isMany()) {
                List<?> la = (List<?>) a;
                List<?> lb = (List<?>) b;
                if (la.size() != lb.size()) {
                    differences.add(where + ": " + la.size() + " vs " + lb.size() + " entries");
                    continue;
                }
                for (int i = 0; i < la.size(); i++) {
                    compareValue(la.get(i), lb.get(i), where + "[" + i + "]", differences);
                }
            } else {
                compareValue(a, b, where, differences);
            }
        }
    }

    private static void compareValue(Object a, Object b, String where, List<String> differences) {
        if (a == null || b == null) {
            if (a != b) {
                differences.add(where + ": " + a + " vs " + b);
            }
        } else if (a instanceof EEnumLiteral la && b instanceof EEnumLiteral lb) {
            if (!la.getName().equals(lb.getName())) {
                differences.add(where + ": " + la.getName() + " vs " + lb.getName());
            }
        } else if (a instanceof EObject ea && b instanceof EObject eb) {
            compare(ea, eb, where, differences);
        } else if (a instanceof Number na && b instanceof Number nb) {
            if (Math.abs(na.doubleValue() - nb.doubleValue()) > 1e-9) {
                differences.add(where + ": " + a + " vs " + b);
            }
        } else if (!a.equals(b)) {
            differences.add(where + ": " + a + " vs " + b);
        }
    }
}
