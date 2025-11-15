module main.game.maze {
    requires main.game.maze.difficulties;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.common;
    requires transitive javafx.graphics;
    requires transitive javafx.media;
    requires org.eclipse.emf.ecore.xmi;
    requires java.logging;    
    requires main.game.maze.opponents;
    requires main.game.maze.behaviour;    

    requires org.eclipse.ocl.ecore;
    requires org.eclipse.ocl.common;

    exports main.game.maze; 
    exports main.game.maze.dto;
    exports main.game.maze.config;
    opens main.game.maze to javafx.fxml, javafx.base, javafx.controls;
}