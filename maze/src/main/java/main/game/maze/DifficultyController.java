package main.game.maze;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

// === generated EMF types; adjust package names if different ===
//import main.game.maze.difficulties.*;   // DifficultyGameData, Difficulty, DifficultiesPackage
import main.game.maze.opponents.*;      // OpponentModel
// =============================================================

public class DifficultyController {
/* 
    private static final String XMI_CLASSPATH = "/opponents/difficulties/difficulties.xmi";

    @FXML private ListView<Difficulty> difficultyList;
    @FXML private Label subtitle;
    @FXML private Button startButton;

    private GameController gameController;
    private OpponentModel opponentModel;            // optional, but recommended
    private DifficultyGameData gameData;            // loaded root

    
    public void init(GameController gc, OpponentModel oppModel) {
        this.gameController = gc;
        this.opponentModel  = oppModel;
        loadModel();
        bindUI();
    }

    
    public void init(GameController gc) {
        init(gc, null);
    }

    // ---------- UI actions ----------

    @FXML
    private void startGame() {
        Difficulty selected = difficultyList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        // keep the model in sync
        gameData.setCurrentDifficulty(selected);

        // make OpponentModel derive maxThreat via selectedDifficulty.currentDifficulty
        if (opponentModel != null) {
            opponentModel.setSelectedDifficulty(gameData);
        }

        // push values into the running game (speed/damage/etc.)
        DifficultyRuntime.applyToGame(selected, gameController);

        // ... continue to your game scene here ...
    }

    // ---------- internals ----------

    private void bindUI() {
        difficultyList.getItems().setAll(gameData.getDifficulties());

        difficultyList.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Difficulty d, boolean empty) {
                super.updateItem(d, empty);
                if (empty || d == null) { setText(""); return; }
                // show a friendly label: prefer a 'name' attr if you add one, else class name
                setText(d.eClass().getName());
            }
        });

        // select current (or first) and show a subtitle
        Difficulty current = gameData.getCurrentDifficulty();
        if (current == null && !gameData.getDifficulties().isEmpty()) {
            current = gameData.getDifficulties().get(0);
        }
        if (current != null) {
            difficultyList.getSelectionModel().select(current);
            subtitle.setText("Current: " + current.eClass().getName());
        } else {
            subtitle.setText("Select a difficulty");
        }

        difficultyList.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            subtitle.setText(n == null ? "Select a difficulty" : "Selected: " + n.eClass().getName());
        });
    }

    private void loadModel() {
        ensureEMF();
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        Resource r = rs.getResource(URI.createURI("classpath:" + XMI_CLASSPATH), true);
        EObject root = r.getContents().get(0);
        if (!(root instanceof DifficultyGameData)) {
            throw new IllegalStateException("Root must be DifficultyGameData, got: " + root.eClass().getName());
        }
        gameData = (DifficultyGameData) root;

        // If an OpponentModel was passed in but lacks a link, set it so OCL derivation works:
        if (opponentModel != null && opponentModel.getSelectedDifficulty() == null) {
            opponentModel.setSelectedDifficulty(gameData);
        }
    }

    private static void ensureEMF() {
        // register generated package so classpath:XMI can be de/serialized
        if (!EPackage.Registry.INSTANCE.containsKey(DifficultiesPackage.eNS_URI)) {
            EPackage.Registry.INSTANCE.put(DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);
        }
        // If opponents model is also loaded here, register its package too:
        if (!EPackage.Registry.INSTANCE.containsKey(OpponentsPackage.eNS_URI)) {
            EPackage.Registry.INSTANCE.put(OpponentsPackage.eNS_URI, OpponentsPackage.eINSTANCE);
        }
    }
    */
}
