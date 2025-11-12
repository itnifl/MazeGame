package main.game.maze.gen;

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
      System.err.println("Usage: -application main.game.maze.gen.app <input.xmi> <outDir>");
      return IApplication.EXIT_OK;
    }
    String modelPath = new File(args[0]).getAbsolutePath();
    String outDir    = new File(args[1]).getAbsolutePath();
    new RunAcceleo().run(modelPath, outDir);
    return IApplication.EXIT_OK;
  }
  @Override public void stop() {}
}
