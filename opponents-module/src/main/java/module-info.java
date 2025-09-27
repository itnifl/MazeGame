module main.game.maze.opponents {
    requires org.eclipse.emf.common;
    requires org.eclipse.emf.ecore;
    // keep exports/opens as before
    exports main.game.maze.opponents;
    opens main.game.maze.opponents to org.eclipse.emf.ecore;
}