module main.game.maze.behaviour {
    // Krever nødvendige EMF-avhengigheter
    requires org.eclipse.emf.common;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.ecore.xmi; 
    
    // Eksporterer nødvendige pakker for andre moduler
    exports main.game.maze.behaviour;
    exports main.game.maze.behaviour.util;
    
    // Åpner pakken for EMF for refleksjon (f.eks. for å bruke EMF's XMI, etc.)
    opens main.game.maze.behaviour to org.eclipse.emf.ecore;
}