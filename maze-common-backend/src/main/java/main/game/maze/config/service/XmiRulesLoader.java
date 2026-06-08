package main.game.maze.config.service;

import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import main.game.maze.config.model.PlayerConfig;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.difficulties.DifficultiesPackage;


/**
 * Centralized loader for EMF XMI resources used by the game.
 * Focus here: loading OpponentModel from a classpath resource.
 */
public final class XmiRulesLoader {

  private static final Logger LOG = Logger.getLogger(XmiRulesLoader.class.getName());
  private static volatile boolean resourceFactoriesRegistered = false;

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
    URL url = resolveClasspathUrl(classpathXmi);

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

  public PlayerConfig loadPlayerConfigFromClasspath(String classpathXmi, String classpathEcore) {
    Objects.requireNonNull(classpathXmi, "classpathXmi must not be null");
    Objects.requireNonNull(classpathEcore, "classpathEcore must not be null");
    ensureXmiFactory();

    ResourceSet rs = new ResourceSetImpl();
    EPackage dynamicPlayerPackage = loadDynamicEPackage(rs, classpathEcore);
    rs.getPackageRegistry().put(dynamicPlayerPackage.getNsURI(), dynamicPlayerPackage);
    EPackage.Registry.INSTANCE.put(dynamicPlayerPackage.getNsURI(), dynamicPlayerPackage);

    URL xmiUrl = resolveClasspathUrl(classpathXmi);
    Resource playerResource = rs.getResource(URI.createURI(xmiUrl.toString()), true);
    if (playerResource == null || playerResource.getContents().isEmpty()) {
      throw new IllegalStateException("Loaded player resource is empty: " + classpathXmi);
    }

    EObject root = playerResource.getContents().get(0);
    EObject playerCharacter = asEObject(readFeature(root, "playerCharacter"), "playerCharacter");

    String id = asString(readFeature(playerCharacter, "id"), "player_default");
    String displayName = asString(readFeature(playerCharacter, "displayName"), "Player");
    boolean enabled = asBoolean(readFeature(playerCharacter, "enabled"), true);
    int health = asInt(readFeature(playerCharacter, "health"), PlayerConstants.DefaultHealth);
    double speed = asDouble(readFeature(playerCharacter, "speed"), PlayerConstants.DefaultSpeed);

    String imageBase = asString(readFeature(playerCharacter, "ImageBase"), PlayerConstants.DefaultPlayerImage);
    String imageTurnLeft = asString(readFeature(playerCharacter, "ImageTurnLeft"), imageBase);
    String imageTurnRight = asString(readFeature(playerCharacter, "ImageTurnRight"), imageBase);
    String imageTurnUp = asString(readFeature(playerCharacter, "ImageTurnUp"), imageBase);
    String imageTurnDown = asString(readFeature(playerCharacter, "ImageTurnDown"), imageBase);
    String imageDeath = asString(readFeature(playerCharacter, "ImageDeath"), PlayerConstants.DefaultDeathImage);

    return new PlayerConfig(
        id,
        displayName,
        enabled,
        health,
        speed,
        imageBase,
        imageTurnLeft,
        imageTurnRight,
        imageTurnUp,
        imageTurnDown,
        imageDeath);
  }

  private EPackage loadDynamicEPackage(ResourceSet rs, String classpathEcore) {
    URL ecoreUrl = resolveClasspathUrl(classpathEcore);
    Resource ecoreResource = rs.getResource(URI.createURI(ecoreUrl.toString()), true);
    if (ecoreResource == null || ecoreResource.getContents().isEmpty()) {
      throw new IllegalStateException("Loaded player ecore is empty: " + classpathEcore);
    }
    Object root = ecoreResource.getContents().get(0);
    if (!(root instanceof EPackage ePackage)) {
      throw new IllegalStateException("Root is not EPackage: " + root);
    }
    return ePackage;
  }

  private URL resolveClasspathUrl(String classpathPath) {
    URL url = OpponentsPackage.class.getResource(classpathPath);
    if (url == null) {
      url = XmiRulesLoader.class.getResource(classpathPath);
    }
    if (url == null) {
      throw new IllegalStateException("Resource not found in classpath: " + classpathPath);
    }
    return url;
  }

  private static Object readFeature(EObject object, String featureName) {
    EStructuralFeature feature = object.eClass().getEStructuralFeature(featureName);
    if (feature == null) {
      throw new IllegalStateException("Missing feature '" + featureName + "' on " + object.eClass().getName());
    }
    return object.eGet(feature);
  }

  private static EObject asEObject(Object value, String featureName) {
    if (value instanceof EObject eo) {
      return eo;
    }
    throw new IllegalStateException("Feature '" + featureName + "' is not an EObject: " + value);
  }

  private static String asString(Object value, String fallback) {
    if (value instanceof String s && !s.isBlank()) {
      return s;
    }
    return fallback;
  }

  private static boolean asBoolean(Object value, boolean fallback) {
    if (value instanceof Boolean b) {
      return b;
    }
    return fallback;
  }

  private static int asInt(Object value, int fallback) {
    if (value instanceof Integer i) {
      return i;
    }
    if (value instanceof Number n) {
      return n.intValue();
    }
    return fallback;
  }

  private static double asDouble(Object value, double fallback) {
    if (value instanceof Double d) {
      return d;
    }
    if (value instanceof Number n) {
      return n.doubleValue();
    }
    return fallback;
  }

  /** Registers XMI and Ecore factories only once. */
  private static synchronized void ensureXmiFactory() {
    if (!resourceFactoriesRegistered) {
      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
          .put("xmi", new XMIResourceFactoryImpl());
      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
          .put("ecore", new EcoreResourceFactoryImpl());
      resourceFactoriesRegistered = true;
      LOG.fine("XMI and Ecore resource factories registered.");
    }
  }
}
