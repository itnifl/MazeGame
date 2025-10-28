module main.game.maze.opponents {
    requires org.eclipse.emf.common;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.ecore.xmi; 
    requires main.game.maze.difficulties;
    // keep exports/opens as before
    exports main.game.maze.opponents;
    exports main.game.maze.opponents.util;

    opens main.game.maze.opponents to org.eclipse.emf.ecore;
}