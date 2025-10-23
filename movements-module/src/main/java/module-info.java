module main.game.maze.behaviour {
    requires org.eclipse.emf.common;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.ecore.xmi;
    // keep exports/opens as before
    exports main.game.maze.behaviour;
    exports main.game.maze.behaviour.util;
    opens main.game.maze.behaviour to org.eclipse.emf.ecore;
}