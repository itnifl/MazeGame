package main.game.maze.config.service;

import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.difficulties.DifficultiesPackage;


/**
 * Centralized loader for EMF XMI resources used by the game.
 * Focus here: loading OpponentModel from a classpath resource.
 */
public final class XmiRulesLoader {

  private static final Logger LOG = Logger.getLogger(XmiRulesLoader.class.getName());
  private static volatile boolean XMI_FACTORY_REGISTERED = false;

  public XmiRulesLoader() {}

  /**
   * Loads an OpponentModel from a classpath path (e.g. "/xmi/opponents/opponentModel.xmi").
   * - Registers EMF packages once.
   * - Resolves the classpath URL.
   * - Loads and returns the root OpponentModel.
   * Throws RuntimeException if anything fails (caller can show dialogs/log).
   */
  public OpponentModel loadOpponentModelFromClasspath(String classpathXmi) {
    Objects.requireNonNull(classpathXmi, "classpathXmi must not be null");
    ensureXmiFactory();

    // 1) Register packages in both global and per-ResourceSet registries
    OpponentsPackage.eINSTANCE.eClass();
    DifficultiesPackage.eINSTANCE.eClass();

    EPackage.Registry.INSTANCE.put(OpponentsPackage.eNS_URI, OpponentsPackage.eINSTANCE);
    EPackage.Registry.INSTANCE.put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);

    ResourceSet rs = new ResourceSetImpl();
    rs.getPackageRegistry().put(OpponentsPackage.eNS_URI, OpponentsPackage.eINSTANCE);
    rs.getPackageRegistry().put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);

    // 2) Resolve the classpath resource
    URL url = OpponentsPackage.class.getResource(classpathXmi);
    if (url == null) {
      url = XmiRulesLoader.class.getResource(classpathXmi);
    }
    if (url == null) {
      throw new IllegalStateException("Opponent model resource not found in classpath: " + classpathXmi);
    }

    // 3) Load via EMF
    URI uri = URI.createURI(url.toString());
    Resource res = rs.getResource(uri, true);
    if (res == null || res.getContents().isEmpty()) {
      throw new IllegalStateException("Loaded resource is empty: " + classpathXmi);
    }

    Object root = res.getContents().get(0);
    if (!(root instanceof OpponentModel om)) {
      throw new IllegalStateException("Root is not OpponentModel: " + root);
    }

    return om;
  }

  /** Registers the XMI factory only once. */
  // Reduces redundancy and efficiency
  private static synchronized void ensureXmiFactory() {
    if (!XMI_FACTORY_REGISTERED) {
      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
          .put("xmi", new XMIResourceFactoryImpl());
      XMI_FACTORY_REGISTERED = true;
      LOG.fine("XMIResourceFactoryImpl registered.");
    }
  }
}
