package main.game.maze.service;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import main.game.maze.constants.DifficultyResourceConstants;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultyGameData;

public final class DifficultyService {
    private static final Logger LOGGER = Logger.getLogger(DifficultyService.class.getName());
    private static final String DIFFICULTIES_XMI = DifficultyResourceConstants.DifficultiesXmiPath;
    private final DifficultyGameData data;

    public DifficultyService() {
        // Make sure the generated package is initialized
        DifficultiesPackage.eINSTANCE.eClass();

        // ResourceSet and XMI factory
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap()
          .put("xmi", new XMIResourceFactoryImpl());

        // Register the package in BOTH registries
        EPackage.Registry global = EPackage.Registry.INSTANCE;
        EPackage.Registry local  = rs.getPackageRegistry();
        global.put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);
        local.put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);

        // (Optional but safe) also register the literal used in your XMI
        final String XMI_NS = "http://main.game.maze/difficulties";
        global.put(XMI_NS, DifficultiesPackage.eINSTANCE);
        local.put(XMI_NS, DifficultiesPackage.eINSTANCE);

        // Use Extended MetaData backed by *this* registry
        BasicExtendedMetaData emd = new BasicExtendedMetaData(local);
        Map<Object,Object> loadOpts = new HashMap<>();
        loadOpts.put(XMLResource.OPTION_EXTENDED_META_DATA, emd);

        // Load with options
        URL url = Objects.requireNonNull(getClass().getResource(DIFFICULTIES_XMI),
                "difficulties.xmi not on classpath");
        Resource r = rs.createResource(URI.createURI(url.toString()));
        try {
            r.load(loadOpts);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load difficulties.xmi", e);
        }
        this.data = (DifficultyGameData) r.getContents().get(0);

        // Sanity: should print false/true
        var ref = DifficultiesPackage.Literals.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY;
        LOGGER.fine("currentDifficulty containment=" + ref.isContainment()
                + ", resolveProxies=" + ref.isResolveProxies()
                + ", nsURI=" + DifficultiesPackage.eNS_URI);
    }

    public List<Difficulty> list() { return data.getDifficulties(); }
    public Difficulty getCurrent() { return data.getCurrentDifficulty(); }
    public void setCurrent(Difficulty d) { data.setCurrentDifficulty(d); }
}


