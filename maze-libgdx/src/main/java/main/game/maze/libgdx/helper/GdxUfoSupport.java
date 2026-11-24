package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.items.ShotEffects;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.game.strike.StrikeVisuals;
import main.game.maze.game.ufo.UfoRaid;
import main.game.maze.game.ufo.UfoRaidSpec;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.SpriteVisual;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * libGDX side of the UFO retaliation (level 7). All rules come from the shared backend ({@link UfoRaid}); like
 * {@link GdxStrikeSupport} this class only converts between libGDX screen space (y up) and the shared world space (y down),
 * draws the UFOs, their beams and the red soldiers as sprites, and plays the sounds. The counterpart of the JavaFX
 * {@code FxUfoLayer}.
 */
public final class GdxUfoSupport {

    /** A UFO is drawn this many times the player's size. */
    static final float UFO_SIZE_FACTOR = 2.6f;
    /** The discs stacked along a beam to draw its column of light. */
    private static final int COLUMN_DISCS = 8;

    private GdxUfoSupport() {
    }

    /** The player as the raid sees them: world space (y down), the shield and the health bar of the game. */
    public static UfoRaid.Target target(GameWorldModel model, PlayerState player, PlayerCombatStateService combat,
                                        float heightPx) {
        return new UfoRaid.Target() {
            @Override public double x() { return player.x(); }
            @Override public double y() { return heightPx - player.y(); }
            @Override public int maxHitPoints() { return combat.maxHitPoints(); }
            @Override public boolean shielded() { return model.shield().isActive(); }
            @Override public void hurt(int damage) { combat.applyDirectDamage(damage); }
        };
    }

    /**
     * After the player used {@code weapon}: when it is one of the trigger tier of this level, the UFOs arrive and the red
     * soldiers are dropped.
     *
     * @return the word for the screen, or null when nothing came
     */
    public static String retaliateAfter(GameWorldModel model, MazeArena maze, PlayerState player,
                                        PlayerCombatStateService combat, Weapon weapon, List<Point2D> openGround) {
        if (maze == null || player == null || !model.strikes().brings(weapon)) {
            return null;
        }
        String banner = model.strikes().retaliate(maze.widthPx(), maze.heightPx(),
                target(model, player, combat, maze.heightPx()), openGround);
        UfoRaidSpec spec = model.strikes().raid().spec();
        if (spec != null && spec.arriveSound() != null) {
            AudioEngine.get().play(spec.arriveSound());
        }
        return banner;
    }

    /** One frame of the retaliation: sounds for the beams, the red soldiers' rounds shown. */
    public static UfoRaid.Frame tick(GameWorldModel model, MazeArena maze, PlayerState player,
                                     PlayerCombatStateService combat, float dt) {
        if (maze == null || player == null || !model.strikes().raid().active()) {
            return UfoRaid.Frame.NOTHING_HAPPENED;
        }
        float height = maze.heightPx();
        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        InfantrySquad.Terrain terrain = world != null && world.getNavigationGraph() != null
                ? InfantrySquad.Terrain.of(world) : null;
        UfoRaidSpec spec = model.strikes().raid().spec();
        UfoRaid.Frame frame = model.strikes().tickRaid(dt, target(model, player, combat, height), terrain);
        if (frame.beamsFired() > 0 && spec != null && spec.beamSound() != null) {
            AudioEngine.get().playRateLimited(spec.beamSound(), "ufo.beam", 250L);
        }
        if (!frame.fallen().isEmpty()) {
            AudioEngine.get().play(StrikeVisuals.SOLDIER_DOWN_SOUND);
        }
        for (InfantrySquad.Round round : frame.rounds()) {
            model.effects().add(new GameWorldModel.ActiveEffect(ShotEffects.enemyRound(
                    round.fromX(), height - round.fromY(), round.toX(), height - round.toY())));
            AudioEngine.get().playRateLimited(StrikeVisuals.SOLDIER_FIRE_SOUND, StrikeVisuals.SOLDIER_FIRE_SOUND_KEY,
                    StrikeVisuals.SOLDIER_FIRE_COOLDOWN_MILLIS);
        }
        return frame;
    }

    /** The red soldiers and the spots the beams mark or burn on the floor (screen space). */
    public static List<SpriteVisual> floorSprites(GameWorldModel model, MazeArena maze, float playerSize, double clock) {
        UfoRaid raid = model.strikes().raid();
        if (maze == null || !raid.active() || raid.spec() == null) {
            return List.of();
        }
        float height = maze.heightPx();
        List<SpriteVisual> sprites = new ArrayList<>();
        float soldierSize = (float) (playerSize * StrikeVisuals.SOLDIER_SIZE_FACTOR);
        for (InfantrySquad.SoldierView soldier : raid.soldiers()) {
            sprites.add(new SpriteVisual((float) soldier.x(), height - (float) soldier.y(), soldierSize, 0f,
                    StrikeVisuals.soldierFlipX(soldier), StrikeVisuals.soldierSprite(soldier, StrikeVisuals.RED_SOLDIER_DIR)));
        }
        for (UfoRaid.UfoView ufo : raid.ufos()) {
            UfoRaid.BeamView beam = ufo.beam();
            if (beam == null) {
                continue;
            }
            sprites.add(new SpriteVisual((float) beam.x(), height - (float) beam.y(), (float) discSize(beam, clock), 0f,
                    false, raid.spec().beamSprite()));
        }
        return sprites;
    }

    /** The UFOs and the columns of light they beam down (screen space), drawn over everything on the floor. */
    public static List<SpriteVisual> airSprites(GameWorldModel model, MazeArena maze, float playerSize, double clock) {
        UfoRaid raid = model.strikes().raid();
        if (maze == null || !raid.ufosActive() || raid.spec() == null) {
            return List.of();
        }
        float height = maze.heightPx();
        List<SpriteVisual> sprites = new ArrayList<>();
        for (UfoRaid.UfoView ufo : raid.ufos()) {
            UfoRaid.BeamView beam = ufo.beam();
            if (beam != null && !beam.charging()) {
                for (int i = 1; i <= COLUMN_DISCS; i++) {
                    double t = i / (double) (COLUMN_DISCS + 1);   // 0 at the UFO, 1 at the ground
                    double x = beam.ufoX() + (beam.x() - beam.ufoX()) * t;
                    double y = beam.ufoY() + (beam.y() - beam.ufoY()) * t;
                    float size = (float) (beam.radius() * (0.5 + 1.2 * t));
                    sprites.add(new SpriteVisual((float) x, height - (float) y, size, 0f, false, raid.spec().beamSprite()));
                }
            }
            // the saucer bobs a little
            float bob = (float) Math.sin(clock * 5 + ufo.x() * 0.02) * 3f;
            sprites.add(new SpriteVisual((float) ufo.x(), height - (float) ufo.y() + bob, playerSize * UFO_SIZE_FACTOR, 0f,
                    ufo.movingRight(), raid.spec().ufoSprite()));
        }
        return sprites;
    }

    /** Size of the disc on the ground: a marker that grows while the beam charges, full size and pulsing while it burns. */
    static double discSize(UfoRaid.BeamView beam, double clock) {
        double full = beam.radius() * 2;
        if (beam.charging()) {
            return full * (0.25 + 0.75 * beam.progress());
        }
        return full * (0.95 + 0.07 * Math.sin(clock * 30));
    }
}
