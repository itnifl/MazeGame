package main.game.maze.characters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        int cooldown = defaultIfNull(model.getAttackCooldownMs(), 1000);
        if (nowMs - lastShotMs < cooldown) return;

        double sx = getCharacterGraphics().getLayoutX();
        double sy = getCharacterGraphics().getLayoutY();
        double tx = target.getLayoutX();
        double ty = target.getLayoutY();

        double dx = tx - sx, dy = ty - sy;

        double range = defaultIfNull(model.getAttackRange(), 350.0);
        if ((dx*dx + dy*dy) > range * range) return;

        double speed     = defaultIfNull(model.getProjectileSpeed(), 260.0);
        double arcHeight = Math.max(0.0, defaultIfNull(model.getArcHeight(), 40.0));
        PumpkinProjectile p = PumpkinProjectile.createArc(sx, sy, tx, ty, speed, arcHeight,
                defaultIfNull(model.getSplashRadius(), 48.0),
                defaultIfNull(model.getAttackDamage(), 5),
                model.getProjectileImage());

        if (p != null) {
            projectiles.add(p);
            lastShotMs = nowMs;
            // playSound(model.getThrowSound());
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
            p.tick(dtSeconds);

            boolean outOfBounds =
                p.node.getLayoutX() < 0 || p.node.getLayoutX() > maxX ||
                p.node.getLayoutY() < 0 || p.node.getLayoutY() > maxY ||
                p.lifeSeconds > 5.0;

            // early collision while flying
            boolean hitNow = false;
            FxPositionBounds pb = new FxPositionBounds(p.node.getBoundsInParent());
            for (ICanSubscribeAndNotifyPosition s : touchTargets) {
                if (!(s instanceof ICanDie victim)) continue;
                Node n = ((Character)s).getCharacterGraphics();
                if (n != null && pb.intersects(new FxPositionBounds(n.getBoundsInParent()))) {
                    explode(p, victim);
                    hitNow = true; break;
                }
            }

            // Wall collision: stop and damage the wall if it is breakable.
            if (!hitNow && App.gameController != null) {
                double wx = p.node.getLayoutX();
                double wy = p.node.getLayoutY();
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

            if (hitNow || arrived || outOfBounds) {
                // splash damage (including on arrival)
                for (ICanSubscribeAndNotifyPosition s : touchTargets) {
                    if (!(s instanceof ICanDie victim)) continue;
                    Node n = ((Character)s).getCharacterGraphics();
                    if (n == null) continue;
                    double dx = n.getLayoutX() - p.node.getLayoutX();
                    double dy = n.getLayoutY() - p.node.getLayoutY();
                    if ((dx*dx + dy*dy) <= p.splashRadius * p.splashRadius) {
                        victim.subtractHitPoints(p.damage);
                    }
                }
                playSound(model.getExplosionSound());
                p.dispose();
                it.remove();
            }
        }
    }

    private void explode(PumpkinProjectile p, ICanDie victim) {
        victim.subtractHitPoints(p.damage);
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
            App.gameController.unregisterComputerCharacter(this, g);
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

    // projectile with parametric arc (no gravity)

    private static final class PumpkinProjectile {
        final ImageView node;
        final double splashRadius;
        final int damage;

        final double sx, sy, tx, ty;
        final double arcHeight;
        final double duration; // seconds to arrive
        double u = 0.0;        // 0..1 progress
        double lifeSeconds = 0;

        private PumpkinProjectile(ImageView node,
                                  double sx, double sy, double tx, double ty,
                                  double duration, double arcHeight,
                                  double splashRadius, int damage) {
            this.node = node;
            this.sx = sx; this.sy = sy; this.tx = tx; this.ty = ty;
            this.duration = Math.max(0.15, duration);
            this.arcHeight = Math.max(0.0, arcHeight);
            this.splashRadius = splashRadius;
            this.damage = damage;
        }

        public static PumpkinProjectile createArc(double sx, double sy, double tx, double ty,
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

            if (projectileImagePath != null && !projectileImagePath.isBlank()) {
                var url = PumpkinBomberCharacter.class.getResource(projectileImagePath);
                if (url != null) iv.setImage(new Image(url.toExternalForm()));
            }

            return new PumpkinProjectile(iv, sx, sy, tx, ty, duration, arcHeight, splashRadius, dmg);
        }

        private void tick(double dt) {
            lifeSeconds += dt;
            u = Math.min(1.0, u + dt / duration);
            // linear path + vertical bump for arc
            double x = lerp(sx, tx, u);
            double y = lerp(sy, ty, u) - arcHeight * Math.sin(Math.PI * u);
            main.game.maze.common.graphics.UiScheduler.get().runOnUiThread(() -> {
                node.setLayoutX(x);
                node.setLayoutY(y);
            });
        }

        boolean isArrived() { return u >= 1.0; }

        void dispose() {
            main.game.maze.common.graphics.UiScheduler.get().runOnUiThread(() -> {
                if (node.getParent() != null) node.setVisible(false);
            });
        }

        private static double lerp(double a, double b, double t) { return a + (b - a) * t; }
    }
}
