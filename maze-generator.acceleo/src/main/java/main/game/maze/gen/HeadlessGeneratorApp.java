package main.game.maze.gen;
///maze-generator.acceleo/src/main/game/maze/gen/HeadlessGeneratorApp.java
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class HeadlessGeneratorApp implements IApplication {
  @Override
  public Object start(IApplicationContext context) throws Exception {
    String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
    
    if (args == null || args.length < 2) {
      System.out.println("Usage: -application main.game.maze.gen.RunAcceleo <input.xmi> <outDir>");
      return IApplication.EXIT_OK;
    }

    String opponentPath = new File(args[0]).getAbsolutePath();
    String diffPath     = new File(args[1]).getAbsolutePath();
    String outDir       = new File(args[2]).getAbsolutePath();

    System.out.println("Generating from:");
    System.out.println("  Opponents: " + opponentPath);
    System.out.println("  Difficulty: " + diffPath);
    System.out.println("  Output:    " + outDir);

    new RunAcceleo().run(opponentPath, diffPath, outDir);
    
    return IApplication.EXIT_OK;
  }
  @Override public void stop() {}
}
