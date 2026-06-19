package main.game.maze.characters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import main.game.maze.App;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.characters.interfaces.IHaveModel;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.interfaces.IDeathSubscriber;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.ProjectileType;
import main.game.maze.common.graphics.AudioEngine;

public class PumpkinBomberCharacter extends ComputerCharacter
        implements ICanKill, ICharacterAnimations, ICanSubscribeAndNotifyPosition, ICanDie, IHaveModel<PumpkinBomber> {

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
    }

    private static int mapSpeed(double modelSpeed) {
        return Math.max(1, (int)Math.round(modelSpeed));
    }

    // animations
    private class HappyAction implements ICharacterAction { public void doAction(Object g) {} }
    private class DieAction   implements ICharacterAction { public void doAction(Object g) {} }

    @Override public void PlayHappyAnimation() { super.doCharacterAnimation(new HappyAction()); }
    @Override public void PlayDieAnimation()   { super.doCharacterAnimation(new DieAction()); }

    // ranged attack API (uses arcHeight instead of gravity)

    public void tryShootAt(Node target, long nowMs) {
        if (target == null || getCharacterGraphics() == null) {
            return;
        }
        int cooldown = defaultIfNull(model.getAttackCooldownMs(), 1000);
        if (nowMs - lastShotMs < cooldown) return;

        double sx = getCharacterGraphics().getLayoutX();
        double sy = getCharacterGraphics().getLayoutY();
        double tx = target.getLayoutX();
        double ty = target.getLayoutY();

        double dx = tx - sx, dy = ty - sy;

        double range = defaultIfNull(model.getAttackRange(), 350.0);
        if ((dx*dx + dy*dy) > range * range) return;

        ProjectileType projectileType = model.getProjectileType() == null
                ? ProjectileType.STRAIGHT
                : model.getProjectileType();

        if (projectileType == ProjectileType.BEAM) {
            boolean blocked = App.gameController != null && App.gameController.isWallBetween(sx, sy, tx, ty);
            if (!blocked) {
                ICanDie victim = resolveVictimForTargetNode(target);
                if (victim != null) {
                    victim.subtractHitPoints(Math.max(1, defaultIfNull(model.getAttackDamage(), 5)));
                }
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
            double prevX = p.node.getLayoutX();
            double prevY = p.node.getLayoutY();
            p.tick(dtSeconds);

            boolean outOfBounds =
                p.node.getLayoutX() < 0 || p.node.getLayoutX() > maxX ||
                p.node.getLayoutY() < 0 || p.node.getLayoutY() > maxY ||
                p.lifeSeconds > 5.0;

            boolean blockedByWall = p.type == ProjectileType.STRAIGHT
                    && App.gameController != null
                    && App.gameController.isWallBetween(prevX, prevY, p.node.getLayoutX(), p.node.getLayoutY());

            // early collision while flying
            boolean hitNow = false;
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

            boolean arrived = p.isArrived();

            if (blockedByWall || hitNow || arrived || outOfBounds) {
                // hitNow is always false for LOB (guarded by the continue above); kept for clarity.
                boolean shouldApplySplash = p.type == ProjectileType.LOB && (arrived || outOfBounds);
                for (ICanSubscribeAndNotifyPosition s : touchTargets) {
                    if (!(s instanceof ICanDie victim)) continue;
                    Node n = characterGraphicsOf(s);
                    if (n == null) continue;
                    double dx = n.getLayoutX() - p.node.getLayoutX();
                    double dy = n.getLayoutY() - p.node.getLayoutY();
                    if (shouldApplySplash && (dx*dx + dy*dy) <= p.splashRadius * p.splashRadius) {
                        victim.subtractHitPoints(p.damage);
                    }
                }
                if (!blockedByWall) {
                    playSound(model.getExplosionSound());
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

    private ICanDie resolveVictimForTargetNode(Node target) {
        if (target instanceof ICanDie directVictim) {
            return directVictim;
        }
        for (ICanSubscribeAndNotifyPosition subscriber : touchTargets) {
            if (!(subscriber instanceof ICanDie victim)) {
                continue;
            }
            Node subscriberGraphics = characterGraphicsOf(subscriber);
            if (subscriberGraphics == target) {
                return victim;
            }
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
