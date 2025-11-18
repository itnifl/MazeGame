module main.game.maze.behaviours {
    requires org.eclipse.emf.common;
    requires org.eclipse.emf.ecore;
    requires main.game.maze.opponents;

 
    exports main.game.maze.behaviours;
    opens main.game.maze.behaviours to org.eclipse.emf.ecore;
}
