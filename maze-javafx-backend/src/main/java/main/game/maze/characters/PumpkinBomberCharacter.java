package main.game.maze.characters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import main.game.maze.App;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.characters.interfaces.IHaveModel;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.interfaces.IDeathSubscriber;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.ProjectileType;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.mazeworld.Vector2D.VectorFacing;

public class PumpkinBomberCharacter extends ComputerCharacter
        implements ICanKill, ICharacterAnimations, ICanSubscribeAndNotifyPosition, ICanDie, IHaveModel<PumpkinBomber> {

    private static final Logger LOG = Logger.getLogger(PumpkinBomberCharacter.class.getName());

    private final PumpkinBomber model;
    private AtomicInteger hitPoints;

    private final List<IDeathSubscriber> deathSubscribers = new ArrayList<>();
    private final List<ICanSubscribeAndNotifyPosition> touchTargets = new ArrayList<>();

    // ranged state
    private long lastShotMs = 0L;
    private final List<PumpkinProjectile> projectiles = new ArrayList<>();

    public PumpkinBomberCharacter(Node characterGraphics, double x, double y, PumpkinBomber model) {
        super(characterGraphics, model, x, y, mapSpeed(model.getSpeed()));
        this.model = model;
        this.hitPoints = new AtomicInteger(Math.max(1, model.getHealth()));
        this.characterXYSizeFromPoint = StageConstants.ZombieCharacterXYSize;
        calculateMaxPositions();
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
        buildDirectionalImages();
    }

    /**
     * Generates directional sprite variants from the base image so the pumpkin bomber
     * visually turns when moving. If separate directional assets are configured in the
     * model they are used as-is (handled by ComputerCharacter). When they are all the
     * same as the base, we produce mirrored and tinted variants at runtime.
     */
    private void buildDirectionalImages() {
        if (!(getCharacterGraphics() instanceof ImageView)) {
            return;
        }
        Image base = imageRight; // ComputerCharacter already loaded these
        if (base == null || base.getWidth() < 1) {
            return;
        }
        // Only generate variants when all directional paths point at the same file
        String lb = safeId(model.getImageTurnLeft());
        String rb = safeId(model.getImageTurnRight());
        String ub = safeId(model.getImageTurnUp());
        String db = safeId(model.getImageTurnDown());
        if (!lb.equals(rb) || !lb.equals(ub) || !lb.equals(db)) {
            return; // distinct assets configured — trust ComputerCharacter's loading
        }
        int w = (int) base.getWidth();
        int h = (int) base.getHeight();
        PixelReader pr = base.getPixelReader();
        if (pr == null) {
            return;
        }
        // RIGHT — original (facing right)
        // LEFT  — horizontally mirrored
        // UP    — slightly brightened tint
        // DOWN  — original (same as right for top-down view)
        WritableImage left = new WritableImage(w, h);
        WritableImage up   = new WritableImage(w, h);
        PixelWriter pwLeft = left.getPixelWriter();
        PixelWriter pwUp   = up.getPixelWriter();
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Color c = pr.getColor(col, row);
                pwLeft.setColor(w - 1 - col, row, c);                 // mirror X
                pwUp.setColor(col, row, c.brighter().saturate());     // tint lighter
            }
        }
        // Override the images registered in ComputerCharacter's internal map
        // by installing them via the inherited setCharacterImage path.
        // We do this by re-registering via the package-accessible images field.
        // Since that map is private we instead use a VectorFacing subscription trick:
        // register our generated images into a small local map and intercept direction changes.
        var localImages = new java.util.EnumMap<VectorFacing, Image>(VectorFacing.class);
        localImages.put(VectorFacing.RIGHT, base);
        localImages.put(VectorFacing.LEFT,  left);
        localImages.put(VectorFacing.UP,    up);
        localImages.put(VectorFacing.DOWN,  base);
        localImages.put(VectorFacing.IDLE,  base);

        // Replace the direction subscriber to use our richer image map
        this.directionSubscriber = facing -> {
            if (facing != null) {
                Image next = localImages.getOrDefault(facing, base);
                setCharacterImage(next);
            }
        };
    }

    private static String safeId(String s) {
        return s == null ? "" : s;
    }

        private static double mapSpeed(double modelSpeed) {
        return Math.max(1.0, modelSpeed);
    }


    // animations
    private class HappyAction implements ICharacterAction { public void doAction(Object g) {} }
    private class DieAction   implements ICharacterAction { public void doAction(Object g) {} }

    @Override public void PlayHappyAnimation() { super.doCharacterAnimation(new HappyAction()); }
    @Override public void PlayDieAnimation()   { super.doCharacterAnimation(new DieAction()); }

    // ranged attack API (uses arcHeight instead of gravity)

    /**
     * Attempts to fire at the given target position.
     * Accepts logical coordinates (not node layout positions) so this can be
     * called safely from the background movement thread without stale node reads.
     */
    public void tryShootAt(double targetX, double targetY, long nowMs) {
        if (getCharacterGraphics() == null) {
            return;
        }
        int cooldown = defaultIfNull(model.getAttackCooldownMs(), 1000);
        if (nowMs - lastShotMs < cooldown) return;

        double sx = getCharacterPosition().getX();
        double sy = getCharacterPosition().getY();
        double tx = targetX;
        double ty = targetY;

        double dx = tx - sx, dy = ty - sy;

        double range = defaultIfNull(model.getAttackRange(), 350.0);
        if ((dx*dx + dy*dy) > range * range) return;

        ProjectileType projectileType = model.getProjectileType() == null
                ? ProjectileType.STRAIGHT
                : model.getProjectileType();

                if (projectileType == ProjectileType.BEAM) {
            boolean blocked = App.gameController != null && App.gameController.isWallBetween(sx, sy, tx, ty);
            if (blocked) {
                LOG.fine(() -> String.format("[PumpkinBomber@(%.0f,%.0f)] BEAM blocked by wall — target=(%.0f,%.0f)", sx, sy, tx, ty));
            } else {
                ICanDie victim = resolveVictimNearPosition(tx, ty);
                if (victim != null) {
                    victim.subtractHitPoints(Math.max(1, defaultIfNull(model.getAttackDamage(), 5)));
                }
                LOG.fine(() -> String.format("[PumpkinBomber@(%.0f,%.0f)] BEAM fired — target=(%.0f,%.0f)", sx, sy, tx, ty));
            }
            drawBeam(sx, sy, tx, ty, blocked);
            playSound(model.getThrowSound());
            lastShotMs = nowMs;
            return;
        }

        double speed     = defaultIfNull(model.getProjectileSpeed(), 260.0);
        double arcHeight = Math.max(0.0, defaultIfNull(model.getArcHeight(), 40.0));
        PumpkinProjectile p = PumpkinProjectile.create(
                projectileType,
                sx,
                sy,
                tx,
                ty,
                speed,
                arcHeight,
                defaultIfNull(model.getSplashRadius(), 48.0),
                defaultIfNull(model.getAttackDamage(), 5),
                model.getProjectileImage());

        if (p != null) {
            Pane pane = hostPane();
            if (pane != null) {
                main.game.maze.common.graphics.UiScheduler.get().runOnUiThread(() -> pane.getChildren().add(p.node));
            }
            projectiles.add(p);
            playSound(model.getThrowSound());
            lastShotMs = nowMs;
            LOG.fine(() -> String.format("[PumpkinBomber@(%.0f,%.0f)] %s fired — target=(%.0f,%.0f)", sx, sy, projectileType, tx, ty));
        }
    }

    /** Advance all pumpkins; call each frame with dt (seconds). */
    public void updateProjectiles(double dtSeconds) {
        if (projectiles.isEmpty()) return;

        double maxX = App.getBoardMaxX();
        double maxY = App.getBoardMaxY();

        Iterator<PumpkinProjectile> it = projectiles.iterator();
        while (it.hasNext()) {
            PumpkinProjectile p = it.next();
            // Capture computed position BEFORE tick() advances it, for the wall segment check.
            double prevX = p.computedX;
            double prevY = p.computedY;
            p.tick(dtSeconds);

            boolean outOfBounds =
                p.computedX < 0 || p.computedX > maxX ||
                p.computedY < 0 || p.computedY > maxY ||
                p.lifeSeconds > 5.0;

            boolean blockedByWall = p.type == ProjectileType.STRAIGHT
                    && App.gameController != null
                    && App.gameController.isWallBetween(prevX, prevY, p.computedX, p.computedY);

            // early collision while flying — skip if the projectile already crossed a wall this tick
            boolean hitNow = false;
            if (!blockedByWall) {
                FxPositionBounds pb = new FxPositionBounds(p.node.getBoundsInParent());
                for (ICanSubscribeAndNotifyPosition s : touchTargets) {
                    if (p.type == ProjectileType.LOB) {
                        continue;
                    }
                    if (!(s instanceof ICanDie victim)) continue;
                    Node n = characterGraphicsOf(s);
                    if (n != null && pb.intersects(new FxPositionBounds(n.getBoundsInParent()))) {
                        if (p.type == ProjectileType.STRAIGHT) {
                            victim.subtractHitPoints(p.damage);
                        }
                        hitNow = true; break;
                    }
                }
            }

            // Wall collision: only STRAIGHT projectiles collide in-flight.
            // LOB projectiles must pass over walls and explode at the target area.
            if (!hitNow && p.type == ProjectileType.STRAIGHT && App.gameController != null) {
                double wx = p.computedX;
                double wy = p.computedY;
                GameMazeWorld mazeWorld = GameMazeWorld.GetWorld();
                if (mazeWorld != null) {
                    var hitWall = WallCollisionUtil.findFirstHitWall(wx, wy, 16.0, mazeWorld.getMazeVectors());
                    if (hitWall != null) {
                        App.gameController.applyProjectileDamageToWall(hitWall, p.damage);
                        hitNow = true;
                    }
                }
            }

            boolean arrived = p.isArrived();

            if (blockedByWall) {
                LOG.fine(() -> String.format("[PumpkinBomber] STRAIGHT blocked by wall at (%.0f,%.0f)", p.node.getLayoutX(), p.node.getLayoutY()));
            }
            if (blockedByWall || hitNow || arrived || outOfBounds) {
                // hitNow is always false for LOB (guarded by the continue above); kept for clarity.
                boolean shouldApplySplash = p.type == ProjectileType.LOB && (arrived || outOfBounds);
                for (ICanSubscribeAndNotifyPosition s : touchTargets) {
                    if (!(s instanceof ICanDie victim)) continue;
                    Node n = characterGraphicsOf(s);
                    if (n == null) continue;
                    double dx = n.getLayoutX() - p.computedX;
                    double dy = n.getLayoutY() - p.computedY;
                    if (shouldApplySplash && (dx*dx + dy*dy) <= p.splashRadius * p.splashRadius) {
                        victim.subtractHitPoints(p.damage);
                    }
                }
                if (!blockedByWall) {
                    playSound(model.getExplosionSound());
                    if (p.type == ProjectileType.LOB || p.type == ProjectileType.STRAIGHT) {
                        playExplosionVisual(p.computedX, p.computedY, p.splashRadius);
                    }
                }
                p.dispose();
                it.remove();
            }
        }
    }

    // ICanKill / damage

    @Override public int getDamage() {
        return CollisionDamage.effectiveDamage(model.getThreatLevel(), defaultIfNull(model.getAttackDamage(), 5));
    }

    // ICanDie

    @Override
    public void subtractHitPoints(int hp) {
        hitPoints.addAndGet(-hp);
        var g = this.getCharacterGraphics();
        if (hitPoints.get() <= 0) {
            PlayDieAnimation();
            for (var sub : deathSubscribers) sub.AddDeathNotification(this);
            if (App.gameController != null) {
                App.gameController.unregisterComputerCharacter(this, g);
            }
        }
    }

    @Override public int  getHitPoints() { return hitPoints.get(); }
    @Override public void setHitPoints(int hp) { hitPoints = new AtomicInteger(hp); }
    @Override public void addHitPoints(int hp) { hitPoints.addAndGet(hp); }

    // subscriptions

    @Override public void addPositionSubscriber(ICanSubscribeAndNotifyPosition e) { touchTargets.add(e); }
    @Override public void removePositionSubscriber(ICanSubscribeAndNotifyPosition e) { touchTargets.remove(e); }
    @Override public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() { return touchTargets; }

    @Override
    public void doPositionEvaluation(PositionBounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        var graphics = this.getCharacterGraphics();
        if (nodeBounds == null || graphics == null) {
            return;
        }
        var myBounds = new FxPositionBounds(graphics.getBoundsInParent());
        if (!nodeBounds.intersects(myBounds)) {
            return;
        }
        // A wall between the pumpkin bomber and the target blocks damage.
        if (App.gameController != null
                && App.gameController.isWallBetween(
                        myBounds.getCenterX(), myBounds.getCenterY(),
                        nodeBounds.getCenterX(), nodeBounds.getCenterY())) {
            return;
        }
        if (entity instanceof ICanDie victim) {
            victim.subtractHitPoints(Math.max(1, getDamage() / 2)); // light bump damage
        }
    }

    // model

    @Override public PumpkinBomber getModel() { return this.model; }
    public  void addDeathNotificationSubscriber(IDeathSubscriber s) { deathSubscribers.add(s); }

    // helpers

    private static int    defaultIfNull(Integer v, int def)   { return v == null ? def : v; }
    private static double defaultIfNull(Double  v, double def) { return v == null ? def : v; }

    private void playSound(String path) {
        AudioEngine.get().play(path);
    }

    private void playExplosionVisual(double cx, double cy, double splashRadius) {
        Pane pane = hostPane();
        if (pane == null) {
            return;
        }
        double radius = Math.max(12.0, splashRadius * 0.5);
        main.game.maze.common.graphics.UiScheduler.get().runOnUiThread(() -> {
            Circle ring = new Circle(cx, cy, 6);
            ring.setFill(Color.TRANSPARENT);
            ring.setStroke(Color.ORANGERED);
            ring.setStrokeWidth(3);
            ring.setMouseTransparent(true);
            pane.getChildren().add(ring);

            Circle core = new Circle(cx, cy, 4);
            core.setFill(Color.YELLOW);
            core.setOpacity(0.9);
            core.setMouseTransparent(true);
            pane.getChildren().add(core);

            Timeline expand = new Timeline(
                new KeyFrame(Duration.ZERO,
                    new KeyValue(ring.radiusProperty(), 6),
                    new KeyValue(ring.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(240),
                    new KeyValue(ring.radiusProperty(), radius),
                    new KeyValue(ring.opacityProperty(), 0.0))
            );
            expand.setOnFinished(e -> pane.getChildren().removeAll(ring, core));

            FadeTransition coreFade = new FadeTransition(Duration.millis(180), core);
            coreFade.setFromValue(0.9);
            coreFade.setToValue(0.0);

            // Brief screen shake on the board pane's parent (camera layer)
            Node shakeTarget = pane.getParent() != null ? pane.getParent() : pane;
            TranslateTransition shakeX = new TranslateTransition(Duration.millis(40), shakeTarget);
            shakeX.setByX(5);
            shakeX.setAutoReverse(true);
            shakeX.setCycleCount(4);
            shakeX.setOnFinished(e -> shakeTarget.setTranslateX(0));
            shakeX.play();

            expand.play();
            coreFade.play();
        });
    }

    private ICanDie resolveVictimNearPosition(double x, double y) {
        final double threshold = 64.0;
        for (ICanSubscribeAndNotifyPosition subscriber : touchTargets) {
            if (!(subscriber instanceof ICanDie victim)) continue;
            Node n = characterGraphicsOf(subscriber);
            if (n == null) continue;
            double dx = n.getLayoutX() - x;
            double dy = n.getLayoutY() - y;
            if (dx * dx + dy * dy <= threshold * threshold) return victim;
        }
        return null;
    }

    private static Node characterGraphicsOf(ICanSubscribeAndNotifyPosition entity) {
        if (entity instanceof Character character) {
            return character.getCharacterGraphics();
        }
        return null;
    }

    private Pane hostPane() {
        if (getCharacterGraphics() == null || getCharacterGraphics().getParent() == null) {
            return null;
        }
        if (getCharacterGraphics().getParent() instanceof Pane pane) {
            return pane;
        }
        return null;
    }

    private void drawBeam(double sx, double sy, double tx, double ty, boolean blocked) {
        Pane pane = hostPane();
        if (pane == null) {
            return;
        }
        main.game.maze.common.graphics.UiScheduler.get().runOnUiThread(() -> {
            Line line = new Line(sx, sy, tx, ty);
            line.setStroke(blocked ? Color.ORANGERED : Color.AQUA);
            line.setStrokeWidth(3.5);
            line.setOpacity(0.9);
            pane.getChildren().add(line);
            javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(120), line);
            fade.setFromValue(0.9);
            fade.setToValue(0.0);
            fade.setOnFinished(evt -> pane.getChildren().remove(line));
            fade.play();
        });
    }

    // projectile with parametric arc (no gravity)

    private static final class PumpkinProjectile {
        final ProjectileType type;
        final ImageView node;
        final double splashRadius;
        final int damage;

        final double sx, sy, tx, ty;
        final double arcHeight;
        final double duration; // seconds to arrive
        double u = 0.0;        // 0..1 progress
        double lifeSeconds = 0;
        /** Current computed position — updated synchronously in tick() before the async UI move. */
        double computedX;
        double computedY;

        private PumpkinProjectile(ProjectileType type,
                                  ImageView node,
                                  double sx, double sy, double tx, double ty,
                                  double duration, double arcHeight,
                                  double splashRadius, int damage) {
            this.type = type;
            this.node = node;
            this.sx = sx; this.sy = sy; this.tx = tx; this.ty = ty;
            this.duration = Math.max(0.15, duration);
            this.arcHeight = Math.max(0.0, arcHeight);
            this.splashRadius = Math.max(0.0, splashRadius);
            this.damage = damage;
            this.computedX = sx;
            this.computedY = sy;
        }

        public static PumpkinProjectile create(ProjectileType type,
                               double sx, double sy, double tx, double ty,
                               double speed, double arcHeight,
                               double splashRadius, int dmg,
                               String projectileImagePath) {
            double dx = tx - sx, dy = ty - sy;
            double dist = Math.hypot(dx, dy);
            if (dist < 1e-3 || speed <= 1e-6) return null;
            double duration = dist / speed;

            ImageView iv = new ImageView();
            iv.setFitWidth(16); iv.setFitHeight(16); iv.setPreserveRatio(true);
            iv.setLayoutX(sx); iv.setLayoutY(sy);
            Image resolved = loadProjectileImage(type, projectileImagePath);
            iv.setImage(resolved == null ? createFallbackProjectileImage(type) : resolved);

            return new PumpkinProjectile(type, iv, sx, sy, tx, ty, duration, arcHeight, splashRadius, dmg);
        }

        private static Image loadProjectileImage(ProjectileType type, String projectileImagePath) {
            Image configured = loadImageIfPresent(projectileImagePath);
            if (configured != null) {
                return configured;
            }

            // Stable drop-in paths for art pipeline; files can be added later without code changes.
            String typeDefault = switch (type) {
                case LOB -> "/main/game/maze/projectiles/pumpkinbomber/pumpkin_lob_projectile.png";
                case BEAM -> "/main/game/maze/projectiles/pumpkinbomber/pumpkin_beam_core_segment.png";
                case STRAIGHT -> "/main/game/maze/projectiles/pumpkinbomber/pumpkin_straight_projectile.png";
            };
            Image byType = loadImageIfPresent(typeDefault);
            if (byType != null) {
                return byType;
            }

            return loadImageIfPresent("/main/game/maze/pumpkin.png");
        }

        private static Image loadImageIfPresent(String path) {
            if (path == null || path.isBlank()) {
                return null;
            }
            var url = PumpkinBomberCharacter.class.getResource(path);
            return url == null ? null : new Image(url.toExternalForm());
        }

        private void tick(double dt) {
            lifeSeconds += dt;
            u = Math.min(1.0, u + dt / duration);
            // STRAIGHT keeps flat trajectory; LOB applies arc offset.
            double x = lerp(sx, tx, u);
            double y = lerp(sy, ty, u);
            if (type == ProjectileType.LOB) {
                y -= arcHeight * Math.sin(Math.PI * u);
            }
            // Store computed position synchronously so updateProjectiles() can use it
            // for wall and bounds checks without waiting for the async UI update.
            computedX = x;
            computedY = y;
            final double drawX = x;
            final double drawY = y;
            main.game.maze.common.graphics.UiScheduler.get().runOnUiThread(() -> {
                node.setLayoutX(drawX);
                node.setLayoutY(drawY);
            });
        }

        boolean isArrived() { return u >= 1.0; }

        void dispose() {
            main.game.maze.common.graphics.UiScheduler.get().runOnUiThread(() -> {
                if (node.getParent() instanceof Pane pane) {
                    pane.getChildren().remove(node);
                } else {
                    node.setVisible(false);
                }
            });
        }

        private static double lerp(double a, double b, double t) { return a + (b - a) * t; }

        private static Image createFallbackProjectileImage(ProjectileType type) {
            WritableImage fallback = new WritableImage(16, 16);
            var pixelWriter = fallback.getPixelWriter();
            Color color = type == ProjectileType.LOB ? Color.ORANGE : Color.GOLD;
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    int dx = x - 8;
                    int dy = y - 8;
                    if ((dx * dx) + (dy * dy) <= 36) {
                        pixelWriter.setColor(x, y, color);
                    } else {
                        pixelWriter.setColor(x, y, Color.TRANSPARENT);
                    }
                }
            }
            return fallback;
        }
    }
}
