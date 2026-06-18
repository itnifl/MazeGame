package main.game.maze.gen;
///maze-generator.acceleo/src/main/game/maze/gen/HeadlessGeneratorApp.java
import java.io.File;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class HeadlessGeneratorApp implements IApplication {
  @Override
  public Object start(IApplicationContext context) throws Exception {
    String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
    
    if (args == null || args.length < 3) {
      System.out.println("Usage: -application main.game.maze.gen.RunAcceleo <opponents.xmi> <difficulties.xmi> <outDir>");
      return Integer.valueOf(1);
    }

    String opponentPath = new File(args[0]).getAbsolutePath();
    String diffPath     = new File(args[1]).getAbsolutePath();
    String outDir       = new File(args[2]).getAbsolutePath();

    System.out.println("Generating from:");
    System.out.println("  Opponents: " + opponentPath);
    System.out.println("  Difficulty: " + diffPath);
    System.out.println("  Output:    " + outDir);

    try {
      new RunAcceleo().run(opponentPath, diffPath, outDir);
      return IApplication.EXIT_OK;
    } catch (Throwable t) {
      t.printStackTrace();
      return Integer.valueOf(13);
    }
  }
  @Override public void stop() {}
}
