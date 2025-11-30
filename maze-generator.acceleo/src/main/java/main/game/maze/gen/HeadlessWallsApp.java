// /maze-generator.acceleo/src/main/game/maze/gen/HeadlessWallsApp.java
package main.game.maze.gen;

import java.io.File;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class HeadlessWallsApp implements IApplication {

    @Override
    public Object start(IApplicationContext context) throws Exception {
        String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);

        if (args == null || args.length < 2) {
            System.out.println("Usage: -application main.game.maze.gen.HeadlessWallsApp <wallsModel.walls> <outDir>");
            return IApplication.EXIT_OK;
        }

        String wallsPath = new File(args[0]).getAbsolutePath();
        String outDir    = new File(args[1]).getAbsolutePath();

        System.out.println("Generating walls from:");
        System.out.println("  Walls model: " + wallsPath);
        System.out.println("  Output:      " + outDir);

        new RunWallsAcceleo().run(wallsPath, outDir);

        return IApplication.EXIT_OK;
    }

    @Override
    public void stop() {
        // nothing to do
    }
}
