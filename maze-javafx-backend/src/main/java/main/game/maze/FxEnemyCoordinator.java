package main.game.maze;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.PumpkinBomberCharacter;
import main.game.maze.characters.ZombieCharacter;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.characters.interfaces.INonTangientMazeGameCharacter;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.EnemySpawnUnstuckService;
import main.game.maze.common.movement.EnemyState;
import main.game.maze.common.movement.GhostNonTangibilityService;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.MovementResult;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Owns all enemy lifecycle concerns: AI movement, infection visuals, and debug
 * overlays. Extracted from GameController to isolate the enemy domain from the
 * top-level FXML coordinator.
 *
 * <p>Movement dispatch uses an {@link EnumMap} keyed by {@link BehaviorType} so
 * that adding a new behaviour only requires registering a handler in the
 * constructor — no switch statement to edit (OCP).</p>
 *
 * <p>Note: this class resides in {@code main.game.maze} rather than
 * {@code main.game.maze.javafx.enemy} because it still has a static dependency on
 * {@link App}. Moving it is deferred until {@code App.getBoardMaxX/Y()} is
 * extracted behind a {@code BoardDimensions} interface.</p>
 */
public final class FxEnemyCoordinator {

    private static final Logger LOGGER = Logger.getLogger(FxEnemyCoordinator.class.getName());

    private static final Duration ENEMY_LABEL_DURATION    = Duration.seconds(20);
    private static final double   ENEMY_LABEL_Y_OFFSET    = 14.0;
    /** Movement tick size used for patrol, aggressive, and phasing energy drain. */
    private static final double   MOVEMENT_TICK_THRESHOLD = 0.06d;
    private static final double   PLAYER_FLAME_SCAN_STEP   = 6.0d;
    /** Half-width of the flame corridor. Four cells wide so the blast fills the corridor visibly. */
    private static final double   PLAYER_FLAME_HALF_WIDTH  = StageConstants.NaviGraphStepSize * 4;

    /** Evaluated lazily so the coordinator can be created before FXML fields are set. */
    private final Supplier<Pane>        gameBoardSupplier;
    private final Supplier<AnchorPane>  rootSupplier;
    private final FxGameWorldModel model;
    private final Supplier<GameMazeWorld> mazeSupplier;
    private final Supplier<PlayerCharacter> playerSupplier;
    private final Runnable pathCanvasRefreshCallback;

    private final List<IMovingComputerCharacter> allComputerCharacters  = new CopyOnWriteArrayList<>();
    private final Map<Node, Timeline>            infectiousMists        = new ConcurrentHashMap<>();
    private final List<Node>                     activeEnemyDebugLabels = new CopyOnWriteArrayList<>();

    private final AntiLoopWanderMovementService       antiLoopWanderMovementService     = new AntiLoopWanderMovementService();
    private final AdaptiveAggressiveMovementService   adaptiveAggressiveMovementService = new AdaptiveAggressiveMovementService();
    private final PatrolMovementService               patrolMovementService             = new PatrolMovementService();
    private final GhostPhasingMovementService         ghostPhasingMovementService       = new GhostPhasingMovementService();

    // Stable WorldView built once in the constructor; captures suppliers by reference.
    private final WorldView worldView;

    // Dispatch table: BehaviorType → movement handler. OCP-friendly extension point.
    private final EnumMap<BehaviorType, Consumer<IMovingComputerCharacter>> movementDispatch;

    private AnimationTimer  enemyPathOverlayTimer;
    private PauseTransition enemyDebugLabelHideTimer;
    private VBox            infectionWarningSign;
    private PauseTransition infectionWarningHideTimer;

    public FxEnemyCoordinator(
            Supplier<Pane>       gameBoardSupplier,
            Supplier<AnchorPane> rootSupplier,
            FxGameWorldModel model,
            Supplier<GameMazeWorld> mazeSupplier,
            Supplier<PlayerCharacter> playerSupplier,
            Runnable pathCanvasRefreshCallback) {
        this.gameBoardSupplier         = gameBoardSupplier;
        this.rootSupplier              = rootSupplier;
        this.model                     = model;
        this.mazeSupplier              = mazeSupplier;
        this.playerSupplier            = playerSupplier;
        this.pathCanvasRefreshCallback = pathCanvasRefreshCallback;

        // Build stable WorldView after all suppliers are set.
        this.worldView = buildWorldView();

        // Register movement handlers. PASSIVE shares the WANDER handler.
        // To add a new BehaviorType: add one entry here — no other change needed.
        movementDispatch = new EnumMap<>(BehaviorType.class);
        movementDispatch.put(BehaviorType.WANDER,     this::doWanderMove);
        movementDispatch.put(BehaviorType.PASSIVE,    this::doWanderMove);
        movementDispatch.put(BehaviorType.PATROL,     this::doPatrolMove);
        movementDispatch.put(BehaviorType.AGGRESSIVE, this::doAggressiveMove);
    }

    // -----------------------------------------------------------------------
    // Registration
    // -----------------------------------------------------------------------

    public void registerCharacter(IMovingComputerCharacter character, Node node) {
        Platform.runLater(() -> {
            Pane board = gameBoardSupplier.get();
            if (board == null || node == null || character == null) return;
            if (!board.getChildren().contains(node)) {
                board.getChildren().add(node);
            }
            if (character instanceof ZombieCharacter zombieCharacter) {
                int infectionLevel = zombieCharacter.getModel() != null
                        ? zombieCharacter.getModel().getInfectionLevel() : 0;
                applyInfectiousMist(node, infectionLevel);
            }
            if (!allComputerCharacters.contains(character)) {
                allComputerCharacters.add(character);
            }
            PlayerCharacter player = playerSupplier.get();
            if (character instanceof ICanSubscribeAndNotifyPosition subscribable && player != null) {
                player.addPositionSubscriber(subscribable);
                subscribable.addPositionSubscriber(player);
            }
        });
    }

    public void unregisterCharacter(IMovingComputerCharacter character, Node node) {
        Platform.runLater(() -> {
            allComputerCharacters.remove(character);
            PlayerCharacter player = playerSupplier.get();
            if (character instanceof ICanSubscribeAndNotifyPosition subscribable && player != null) {
                player.removePositionSubscriber(subscribable);
                subscribable.removePositionSubscriber(player);
            }
            stopInfectiousMist(node);
            Pane board = gameBoardSupplier.get();
            if (board != null && node != null && node.getParent() == board) {
                board.getChildren().remove(node);
            }
        });
    }

    // -----------------------------------------------------------------------
    // AI movement
    // -----------------------------------------------------------------------

    public void stepAll() {
        PlayerCharacter playerCharacter = playerSupplier.get();
        long nowMs = System.currentTimeMillis();
        for (var computerCharacter : allComputerCharacters) {
            try {
                if (computerCharacter instanceof ComputerCharacter cc) {
                    movementDispatch
                            .getOrDefault(cc.getCharacterBehaviour(), this::doWanderMove)
                            .accept(computerCharacter);
                    if (cc instanceof PumpkinBomberCharacter pumpkinBomber && playerCharacter != null) {
                        pumpkinBomber.tryShootAt(worldView.playerX(), worldView.playerY(), nowMs);
                        // updateProjectiles is done on the FX thread below to avoid concurrent
                        // list mutation between the AI background thread and the FX render thread.
                    }
                }
                if (computerCharacter instanceof PumpkinBomberCharacter pbc) {
                    Platform.runLater(() -> {
                        try {
                            // 100 ms per AI tick → 0.1 s dt keeps projectile physics correct.
                            pbc.updateProjectiles(0.1);
                        } catch (Exception ex) {
                            LOGGER.log(Level.WARNING, "Error updating projectiles for: " + pbc, ex);
                        }
                    });
                }
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error moving character: " + computerCharacter, ex);
            }
        }
    }

    public void reset() {
        antiLoopWanderMovementService.reset();
        adaptiveAggressiveMovementService.reset();
        patrolMovementService.reset();
        ghostPhasingMovementService.reset();
    }

    public int killAll() {
        int killed = 0;
        for (var character : allComputerCharacters) {
            if (character instanceof main.game.maze.characters.interfaces.ICanDie canDie) {
                int hp = canDie.getHitPoints();
                if (hp > 0) {
                    canDie.subtractHitPoints(hp);
                    killed++;
                }
            }
        }
        return killed;
    }

    public int applyPlayerFlameAttack(int damage) {
        int remainingDamage = Math.max(0, damage);
        int appliedDamage = 0;
        for (var character : allComputerCharacters) {
            if (remainingDamage <= 0) {
                break;
            }
            if (!(character instanceof main.game.maze.characters.interfaces.ICanDie canDie)) {
                continue;
            }
            int hp = Math.max(0, canDie.getHitPoints());
            if (hp <= 0) {
                continue;
            }
            int damageToApply = Math.min(hp, remainingDamage);
            canDie.subtractHitPoints(damageToApply);
            remainingDamage -= damageToApply;
            appliedDamage += damageToApply;
        }
        return appliedDamage;
    }

    /**
     * Backward-compatible overload without player damage (used by legacy callers).
     */
    public int applyPlayerFlameExplosion(double originX,
                                         double originY,
                                         int damagePerDirection,
                                         double maxRange,
                                         List<Vector2D> walls) {
        return applyPlayerFlameExplosion(originX, originY, damagePerDirection, maxRange, walls,
                Double.NaN, Double.NaN, null);
    }

    /**
     * Fires four cardinal flame corridors from {@code (originX, originY)}.
     *
     * <p>Each direction shares a {@code damagePerDirection} budget. Enemies and walls consume
     * the budget and stop the flame when they survive. The player (if provided) is a
     * pass-through target: they receive the remaining budget at their position but neither
     * consume nor block the flame.</p>
     *
     * @param playerCenterX  player centre X, or {@code Double.NaN} to skip
     * @param playerCenterY  player centre Y, or {@code Double.NaN} to skip
     * @param playerCallback receives the damage dealt to the player; may be {@code null}
     */
    public int applyPlayerFlameExplosion(double originX,
                                         double originY,
                                         int damagePerDirection,
                                         double maxRange,
                                         List<Vector2D> walls,
                                         double playerCenterX,
                                         double playerCenterY,
                                         IntConsumer playerCallback) {
        int safeDamage = Math.max(0, damagePerDirection);
        double safeRange = Math.max(0d, maxRange);
        if (safeDamage <= 0 || safeRange <= 0d) {
            return 0;
        }

        int totalApplied = 0;
        totalApplied += applyDirectionalFlame(originX, originY, 1, 0, safeDamage, safeRange, walls,
                playerCenterX, playerCenterY, playerCallback);
        totalApplied += applyDirectionalFlame(originX, originY, -1, 0, safeDamage, safeRange, walls,
                playerCenterX, playerCenterY, playerCallback);
        totalApplied += applyDirectionalFlame(originX, originY, 0, 1, safeDamage, safeRange, walls,
                playerCenterX, playerCenterY, playerCallback);
        totalApplied += applyDirectionalFlame(originX, originY, 0, -1, safeDamage, safeRange, walls,
                playerCenterX, playerCenterY, playerCallback);
        return totalApplied;
    }

    private int applyDirectionalFlame(double originX,
                                      double originY,
                                      int dirX,
                                      int dirY,
                                      int damageBudget,
                                      double maxRange,
                                      List<Vector2D> walls,
                                      double playerCenterX,
                                      double playerCenterY,
                                      IntConsumer playerCallback) {
        GameMazeWorld maze = mazeSupplier.get();
        List<DirectionalTarget> targets = new ArrayList<>();
        for (var character : allComputerCharacters) {
            if (!(character instanceof main.game.maze.characters.interfaces.ICanDie canDie)) {
                continue;
            }
            if (!(character instanceof ComputerCharacter cc)) {
                continue;
            }
            int hp = Math.max(0, canDie.getHitPoints());
            if (hp <= 0) {
                continue;
            }
            double size = approximateSize(cc);
            double centerX = cc.getCharacterPosition().getX() + size * 0.5d;
            double centerY = cc.getCharacterPosition().getY() + size * 0.5d;
            Double distance = projectedDistanceOnRay(originX, originY, centerX, centerY, dirX, dirY, maxRange);
            if (distance == null) {
                continue;
            }
            targets.add(new DirectionalTarget(canDie, null, distance, centerX, centerY, false));
        }
        // Add player as pass-through target if provided
        if (playerCallback != null && !Double.isNaN(playerCenterX) && !Double.isNaN(playerCenterY)) {
            Double pd = projectedDistanceOnRay(originX, originY, playerCenterX, playerCenterY, dirX, dirY, maxRange);
            if (pd != null) {
                targets.add(new DirectionalTarget(null, playerCallback, pd, playerCenterX, playerCenterY, true));
            }
        }

        targets.sort(Comparator.comparingDouble(DirectionalTarget::distance));

        int remaining = damageBudget;
        int applied = 0;
        int targetIndex = 0;
        double searchStart = 0d;

        while (remaining > 0 && searchStart <= maxRange) {
            WallHit nextWall = findNextWallHit(originX, originY, dirX, dirY, walls, searchStart, maxRange);
            double wallDistance = nextWall == null ? Double.POSITIVE_INFINITY : nextWall.distance();

            while (targetIndex < targets.size()) {
                DirectionalTarget currentTarget = targets.get(targetIndex);
                if (walls != null && !walls.isEmpty()
                        && WallCollisionUtil.wallBetweenVectors(originX, originY, currentTarget.centerX(), currentTarget.centerY(), walls)) {
                    if (nextWall != null && maze != null) {
                        BreakableWall breakableWall = maze.findBreakableWall(nextWall.wall());
                        if (breakableWall != null) {
                            int toApply = Math.min(remaining, Math.max(0, breakableWall.getRemainingHp()));
                            if (toApply > 0) {
                                boolean destroyed = maze.applyWallDamage(breakableWall, toApply);
                                remaining -= toApply;
                                applied += toApply;
                                if (!destroyed) {
                                    return applied;
                                }
                                searchStart = nextWall.distance();
                                break; // re-evaluate wall list
                            }
                        }
                    }
                    return applied; // indestructible wall
                }
                if (currentTarget.distance() >= wallDistance) {
                    break;
                }
                if (currentTarget.isPlayer()) {
                    // Pass-through: player takes remaining damage but does not block
                    if (currentTarget.playerCallback() != null && remaining > 0) {
                        currentTarget.playerCallback().accept(remaining);
                    }
                    targetIndex++;
                    continue;
                }
                int hp = Math.max(0, currentTarget.target().getHitPoints());
                if (hp > 0) {
                    int toApply = Math.min(hp, remaining);
                    currentTarget.target().subtractHitPoints(toApply);
                    remaining -= toApply;
                    applied += toApply;
                    if (hp > toApply) {
                        return applied; // enemy survived
                    }
                }
                targetIndex++;
                if (remaining <= 0) {
                    return applied;
                }
            }

            if (nextWall == null || maze == null) {
                break;
            }

            BreakableWall breakableWall = maze.findBreakableWall(nextWall.wall());
            if (breakableWall == null) {
                break;
            }

            int toApply = Math.min(remaining, Math.max(0, breakableWall.getRemainingHp()));
            if (toApply <= 0) {
                break;
            }
            boolean destroyed = maze.applyWallDamage(breakableWall, toApply);
            remaining -= toApply;
            applied += toApply;
            if (!destroyed) {
                break;
            }
            searchStart = nextWall.distance();
        }

        while (remaining > 0 && targetIndex < targets.size()) {
            DirectionalTarget current = targets.get(targetIndex);
            if (current.isPlayer()) {
                if (current.playerCallback() != null) {
                    current.playerCallback().accept(remaining);
                }
                targetIndex++;
                continue;
            }
            int hp = Math.max(0, current.target().getHitPoints());
            if (hp > 0) {
                int toApply = Math.min(hp, remaining);
                current.target().subtractHitPoints(toApply);
                remaining -= toApply;
                applied += toApply;
                if (hp > toApply) {
                    return applied;
                }
            }
            targetIndex++;
        }
        return applied;
    }

    private static Double projectedDistanceOnRay(double originX,
                                                 double originY,
                                                 double targetX,
                                                 double targetY,
                                                 int dirX,
                                                 int dirY,
                                                 double maxRange) {
        double corridorHalfWidth = PLAYER_FLAME_HALF_WIDTH;
        if (dirX != 0) {
            if (Math.abs(targetY - originY) > corridorHalfWidth) {
                return null;
            }
            double delta = targetX - originX;
            if (Math.signum(delta) != dirX) {
                return null;
            }
            double distance = Math.abs(delta);
            return distance <= maxRange ? distance : null;
        }

        if (Math.abs(targetX - originX) > corridorHalfWidth) {
            return null;
        }
        double delta = targetY - originY;
        if (Math.signum(delta) != dirY) {
            return null;
        }
        double distance = Math.abs(delta);
        return distance <= maxRange ? distance : null;
    }

    private WallHit findNextWallHit(double originX,
                                    double originY,
                                    int dirX,
                                    int dirY,
                                    List<Vector2D> walls,
                                    double searchStart,
                                    double maxRange) {
        if (walls == null || walls.isEmpty()) {
            return null;
        }

        WallHit nearest = null;
        for (Vector2D wall : walls) {
            double wallStartX = wall.getStart().getX();
            double wallStartY = wall.getStart().getY();
            double wallEndX = wall.getEnd().getX();
            double wallEndY = wall.getEnd().getY();

            if (dirX != 0 && Math.abs(wallStartX - wallEndX) < 0.001d) {
                double minY = Math.min(wallStartY, wallEndY);
                double maxY = Math.max(wallStartY, wallEndY);
                if (originY < minY || originY > maxY) {
                    continue;
                }
                double distance = wallStartX - originX;
                if (Math.signum(distance) != dirX) {
                    continue;
                }
                double absDistance = Math.abs(distance);
                if (absDistance <= searchStart || absDistance > maxRange) {
                    continue;
                }
                if (nearest == null || absDistance < nearest.distance()) {
                    nearest = new WallHit(wall, absDistance);
                }
                continue;
            }

            if (dirY != 0 && Math.abs(wallStartY - wallEndY) < 0.001d) {
                double minX = Math.min(wallStartX, wallEndX);
                double maxX = Math.max(wallStartX, wallEndX);
                if (originX < minX || originX > maxX) {
                    continue;
                }
                double distance = wallStartY - originY;
                if (Math.signum(distance) != dirY) {
                    continue;
                }
                double absDistance = Math.abs(distance);
                if (absDistance <= searchStart || absDistance > maxRange) {
                    continue;
                }
                if (nearest == null || absDistance < nearest.distance()) {
                    nearest = new WallHit(wall, absDistance);
                }
            }
        }
        return nearest;
    }

    private record DirectionalTarget(main.game.maze.characters.interfaces.ICanDie target,
                                     IntConsumer playerCallback,
                                     double distance,
                                     double centerX,
                                     double centerY,
                                     boolean isPlayer) {
    }

    private record WallHit(Vector2D wall, double distance) {
    }

    public Point2D resolveSpawnPosition(double desiredX, double desiredY, double enemySize) {
        var resolution = EnemySpawnUnstuckService.nudgeIfColliding(
                worldView, desiredX, desiredY, enemySize);
        return new Point2D(resolution.x(), resolution.y());
    }

    // -----------------------------------------------------------------------
    // Infection visuals
    // -----------------------------------------------------------------------

    public void showInfectionWarning() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::showInfectionWarning);
            return;
        }
        if (rootSupplier.get() == null) return;
        ensureInfectionWarningSign();
        infectionWarningSign.setVisible(true);
        infectionWarningSign.setManaged(false);
        infectionWarningSign.toFront();

        if (infectionWarningHideTimer == null) {
            infectionWarningHideTimer = new PauseTransition(Duration.seconds(1.2));
            infectionWarningHideTimer.setOnFinished(evt -> {
                if (infectionWarningSign != null) infectionWarningSign.setVisible(false);
            });
        }
        infectionWarningHideTimer.stop();
        infectionWarningHideTimer.playFromStart();
    }

    private void ensureInfectionWarningSign() {
        if (infectionWarningSign != null) return;

        Polygon triangle = new Polygon(0.0, 84.0, 48.0, 0.0, 96.0, 84.0);
        triangle.setFill(Color.rgb(255, 212, 77, 0.95));
        triangle.setStroke(Color.rgb(55, 40, 0, 0.95));
        triangle.setStrokeWidth(3.0);

        Label mark = new Label("!");
        mark.setTextFill(Color.rgb(35, 22, 0));
        mark.setStyle("-fx-font-size: 56px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
        mark.setTranslateY(6);

        Text infectedLabel = new Text("Infected!");
        infectedLabel.setFill(Color.rgb(51, 255, 115));
        infectedLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 22));
        infectedLabel.setMouseTransparent(true);

        StackPane signGraphic = new StackPane(triangle, mark);
        signGraphic.setAlignment(Pos.CENTER);

        infectionWarningSign = new VBox(6, signGraphic, infectedLabel);
        infectionWarningSign.setAlignment(Pos.CENTER);
        infectionWarningSign.setMouseTransparent(true);
        infectionWarningSign.setManaged(false);
        infectionWarningSign.setVisible(false);

        AnchorPane r = rootSupplier.get();
        if (r == null) return;
        infectionWarningSign.layoutXProperty().bind(r.widthProperty().subtract(96).divide(2));
        infectionWarningSign.layoutYProperty().bind(r.heightProperty().subtract(130).divide(2));
        r.getChildren().add(infectionWarningSign);
    }

    private void applyInfectiousMist(Node node, int infectionLevel) {
        if (node == null || infectionLevel <= 0) return;
        stopInfectiousMist(node);

        DropShadow mist = new DropShadow();
        mist.setBlurType(BlurType.GAUSSIAN);
        mist.setColor(Color.rgb(60, 255, 130, 0.78));
        mist.setRadius(10.0);
        mist.setSpread(0.18);
        node.setEffect(mist);

        double intensity = Math.min(1.0, Math.max(0.35, infectionLevel / 100.0));
        Timeline pulse = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(mist.radiusProperty(), 10.0 + 8.0 * intensity),
                        new KeyValue(mist.spreadProperty(), 0.14 + 0.08 * intensity)),
                new KeyFrame(Duration.seconds(0.7),
                        new KeyValue(mist.radiusProperty(), 22.0 + 10.0 * intensity),
                        new KeyValue(mist.spreadProperty(), 0.34 + 0.10 * intensity)));
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        infectiousMists.put(node, pulse);
        pulse.play();
    }

    private void stopInfectiousMist(Node node) {
        if (node == null) return;
        Timeline pulse = infectiousMists.remove(node);
        if (pulse != null) pulse.stop();
        if (node.getEffect() instanceof DropShadow) node.setEffect(null);
    }

    // -----------------------------------------------------------------------
    // Enemy debug labels
    // -----------------------------------------------------------------------

    public void showEnemyDebugLabels(boolean behaviourType) {
        clearEnemyDebugLabels();
        Pane board = gameBoardSupplier.get();
        if (board == null) return;
        for (var cc : allComputerCharacters) {
            if (!(cc instanceof ComputerCharacter computerCharacter)) continue;
            Node enemyNode = computerCharacter.getCharacterGraphics();
            if (enemyNode == null) continue;
            String labelText = behaviourType
                    ? computerCharacter.getCharacterBehaviour().name()
                    : movementTypeName(computerCharacter);
            Label label = new Label(labelText);
            label.setMouseTransparent(true);
            label.setStyle("-fx-text-fill: #f3f9ff; -fx-font-family: 'Consolas'; -fx-font-size: 12px; "
                    + "-fx-font-weight: bold; -fx-background-color: rgba(0,0,0,0.45); "
                    + "-fx-padding: 2 4 2 4; -fx-background-radius: 3;");
            label.layoutXProperty().bind(enemyNode.layoutXProperty());
            label.layoutYProperty().bind(enemyNode.layoutYProperty().subtract(ENEMY_LABEL_Y_OFFSET));
            board.getChildren().add(label);
            activeEnemyDebugLabels.add(label);
        }

        if (enemyDebugLabelHideTimer == null) {
            enemyDebugLabelHideTimer = new PauseTransition(ENEMY_LABEL_DURATION);
            enemyDebugLabelHideTimer.setOnFinished(evt -> clearEnemyDebugLabels());
        }
        enemyDebugLabelHideTimer.stop();
        enemyDebugLabelHideTimer.playFromStart();
    }

    private String movementTypeName(ComputerCharacter character) {
        BehaviorType behaviour = character.getCharacterBehaviour();
        if (behaviour == BehaviorType.AGGRESSIVE) {
            String id = enemyId(character);
            var mode = adaptiveAggressiveMovementService.modeForEnemy(id);
            if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.PATH_FOLLOW)     return "AGGRESSIVE_PATH";
            if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.WANDER_RECOVERY) return "AGGRESSIVE_WANDER";
            return "AGGRESSIVE_CHASE";
        }
        if (behaviour == BehaviorType.PATROL) {
            String id = enemyId(character);
            return patrolMovementService.modeForEnemy(id) == PatrolMovementService.PatrolMovementMode.WANDER_RECOVERY
                    ? "PATROL_WANDER" : "PATROL_PATH";
        }
        if (behaviour == BehaviorType.PASSIVE) return "WANDER";
        return behaviour.name();
    }

    private void clearEnemyDebugLabels() {
        Pane board = gameBoardSupplier.get();
        if (activeEnemyDebugLabels.isEmpty() || board == null) return;
        for (Node label : activeEnemyDebugLabels) {
            if (label != null) {
                label.layoutXProperty().unbind();
                label.layoutYProperty().unbind();
                board.getChildren().remove(label);
            }
        }
        activeEnemyDebugLabels.clear();
    }

    // -----------------------------------------------------------------------
    // Enemy path overlay
    // -----------------------------------------------------------------------

    public void showEnemyPathsOverlay() {
        model.setEnemyPathOverlayVisible(true);
        model.setEnemyPathOverlayHideAtNanos(System.nanoTime() + 10_000_000_000L);
        ensureEnemyPathOverlayTimer();
        if (pathCanvasRefreshCallback != null) pathCanvasRefreshCallback.run();
    }

    private void ensureEnemyPathOverlayTimer() {
        if (enemyPathOverlayTimer != null) {
            enemyPathOverlayTimer.start();
            return;
        }
        enemyPathOverlayTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now >= model.enemyPathOverlayHideAtNanos()) {
                    model.setEnemyPathOverlayVisible(false);
                    stop();
                }
                if (pathCanvasRefreshCallback != null) pathCanvasRefreshCallback.run();
            }
        };
        enemyPathOverlayTimer.start();
    }

    public void drawEnemyNavigationPaths(GraphicsContext gc) {
        GameMazeWorld maze = mazeSupplier.get();
        if (maze == null) return;
        gc.setLineWidth(5.0);
        gc.setStroke(Color.ORANGE);
        gc.setGlobalAlpha(0.65);

        for (var cc : allComputerCharacters) {
            if (!(cc instanceof ComputerCharacter computerCharacter)) continue;
            List<ActivePathPoint> path = activeEnemyPath(computerCharacter);
            if (path.isEmpty()) continue;
            ActivePathPoint prev = path.get(0);
            for (int i = 1; i < path.size(); i++) {
                ActivePathPoint p = path.get(i);
                gc.strokeLine(prev.x(), prev.y(), p.x(), p.y());
                prev = p;
            }
        }
    }

    private List<ActivePathPoint> activeEnemyPath(ComputerCharacter character) {
        String id = enemyId(character);
        double x  = character.getCharacterPosition().getX();
        double y  = character.getCharacterPosition().getY();
        BehaviorType behaviour = character.getCharacterBehaviour();
        if (behaviour == BehaviorType.AGGRESSIVE) return adaptiveAggressiveMovementService.currentPathForEnemy(id, x, y);
        if (behaviour == BehaviorType.PATROL)     return patrolMovementService.currentPathForEnemy(id, x, y);
        return List.of();
    }

    // -----------------------------------------------------------------------
    // Dispose
    // -----------------------------------------------------------------------

    public void dispose() {
        if (enemyPathOverlayTimer != null) {
            enemyPathOverlayTimer.stop();
            enemyPathOverlayTimer = null;
        }
        model.setEnemyPathOverlayVisible(false);

        if (enemyDebugLabelHideTimer != null) {
            enemyDebugLabelHideTimer.stop();
            enemyDebugLabelHideTimer = null;
        }
        clearEnemyDebugLabels();

        if (infectionWarningHideTimer != null) {
            infectionWarningHideTimer.stop();
            infectionWarningHideTimer = null;
        }
        if (infectionWarningSign != null) {
            VBox sign = infectionWarningSign;
            Platform.runLater(() -> { AnchorPane r = rootSupplier.get(); if (r != null) r.getChildren().remove(sign); });
            infectionWarningSign = null;
        }
        infectiousMists.values().forEach(Timeline::stop);
        infectiousMists.keySet().forEach(n -> {
            if (n != null && n.getEffect() instanceof DropShadow) n.setEffect(null);
        });
        infectiousMists.clear();

        for (var cc : allComputerCharacters) {
            if (cc instanceof ComputerCharacter computerChar) computerChar.dispose();
        }
        allComputerCharacters.clear();
    }

    // -----------------------------------------------------------------------
    // Private helpers — movement
    // -----------------------------------------------------------------------

    /**
     * Applies ghost-phasing movement if {@code cc} is currently non-tangient.
     *
     * @return {@code true} if the character was phasing and its move was handled;
     *         the caller must return immediately in that case.
     */
    private boolean applyPhasing(IMovingComputerCharacter character, ComputerCharacter cc) {
        if (!(cc instanceof INonTangientMazeGameCharacter ntcc)) return false;
        if (!drainNonTangientEnergy(ntcc)) return false;
        double size = approximateSize(cc);
        double half = size * 0.5d;
        MovementResult next = ghostPhasingMovementService.tick(
            new EnemyState(
                enemyId(cc),
                cc.getCharacterPosition().getX() + half,
                cc.getCharacterPosition().getY() + half,
                dirSign(cc.getDirectionX()),
                dirSign(cc.getDirectionY()),
                size,
                Math.max(1d, Math.max(Math.abs(cc.getDirectionX()), Math.abs(cc.getDirectionY())))),
            worldView);
        cc.setDirection(new Point2D(next.directionX(), next.directionY()));
        cc.teleportTo(next.x() - half, next.y() - half);
        return true;
    }

    private void doWanderMove(IMovingComputerCharacter computerCharacter) {
        if (!(computerCharacter instanceof ComputerCharacter cc) || mazeSupplier.get() == null) {
            if (!computerCharacter.move(false)) computerCharacter.changeDirection();
            return;
        }
        if (applyPhasing(computerCharacter, cc)) return;

        if (cc.getDirectionX() == 0 && cc.getDirectionY() == 0) computerCharacter.changeDirection();

        MovementResult next = antiLoopWanderMovementService.tick(buildEnemyState(cc), worldView);
        if (next.directionX() != 0 || next.directionY() != 0) {
            cc.setDirection(new Point2D(next.directionX(), next.directionY()));
        }
        if (!computerCharacter.move(false)) {
            computerCharacter.changeDirection();
            computerCharacter.move(false);
        }
    }

    private void doPatrolMove(IMovingComputerCharacter computerCharacter) {
        if (!(computerCharacter instanceof ComputerCharacter cc)) {
            doWanderMove(computerCharacter);
            return;
        }
        if (applyPhasing(computerCharacter, cc)) return;

        MovementResult result = patrolMovementService.tick(buildEnemyState(cc), worldView, MOVEMENT_TICK_THRESHOLD);
        if (result.directionX() == 0 && result.directionY() == 0) {
            doWanderMove(computerCharacter);
            return;
        }

        computerCharacter.setDirection(new Point2D(result.directionX(), result.directionY()));
        if (!computerCharacter.move(false)) doWanderMove(computerCharacter);
    }

    private void doAggressiveMove(IMovingComputerCharacter computerCharacter) {
        if (playerSupplier.get() == null
                || !(computerCharacter instanceof ComputerCharacter cc)
                || mazeSupplier.get() == null) {
            doWanderMove(computerCharacter);
            return;
        }
        if (applyPhasing(computerCharacter, cc)) return;

        MovementResult result = adaptiveAggressiveMovementService.tick(buildEnemyState(cc), worldView, MOVEMENT_TICK_THRESHOLD);
        if (result.directionX() == 0 && result.directionY() == 0) {
            doWanderMove(computerCharacter);
            return;
        }

        cc.setDirection(new Point2D(result.directionX(), result.directionY()));
        if (!computerCharacter.move(false)) {
            computerCharacter.changeDirection();
            computerCharacter.move(false);
        }
    }

        private boolean drainNonTangientEnergy(INonTangientMazeGameCharacter cc) {
        double energy = cc.getNonTangientEnergy();
        boolean wasPhasing = GhostNonTangibilityService.isPhasing(energy);

        if (wasPhasing) {
            double newEnergy = GhostNonTangibilityService.drainEnergy(energy, MOVEMENT_TICK_THRESHOLD);
            cc.setNonTangientEnergy(newEnergy);

            // If the ghost JUST became solid, nudge it out of any walls it might be inside.
            if (!GhostNonTangibilityService.isPhasing(newEnergy) && cc instanceof ComputerCharacter computerCharacter) {
                nudgeOutOfWalls(computerCharacter);
            }
        }

        cc.setCharacterOpacity(GhostNonTangibilityService.calculateOpacity(cc.getNonTangientEnergy(), cc.getVisibilityLevel()));
        // Return whether the ghost is STILL phasing after the drain.
        // Returning wasPhasing (old state) caused one extra wall-ignoring move after the nudge,
        // moving the ghost back into a wall on the solidification tick.
        return wasPhasing && GhostNonTangibilityService.isPhasing(cc.getNonTangientEnergy());
    }

    private void nudgeOutOfWalls(ComputerCharacter cc) {
        double size = approximateSize(cc);
        var resolution = EnemySpawnUnstuckService.nudgeIfColliding(
                worldView, cc.getCharacterPosition().getX(), cc.getCharacterPosition().getY(), size);
        if (resolution.moved()) {
            cc.teleportTo(resolution.x(), resolution.y());
            LOGGER.fine(() -> "Nudged ghost " + enemyId(cc) + " out of wall to " + resolution.x() + ", " + resolution.y());
        }
    }


    private EnemyState buildEnemyState(ComputerCharacter cc) {
        double speed = Math.max(1d, Math.max(Math.abs(cc.getDirectionX()), Math.abs(cc.getDirectionY())));
        double size  = approximateSize(cc);
        return new EnemyState(
                enemyId(cc),
                cc.getCharacterPosition().getX(),
                cc.getCharacterPosition().getY(),
                dirSign(cc.getDirectionX()),
                dirSign(cc.getDirectionY()),
                size,
                speed);
    }

    /** Builds the {@link WorldView} once; the returned object captures suppliers by reference. */
    private WorldView buildWorldView() {
        return new WorldView() {
            @Override public double playerX() {
                PlayerCharacter p = playerSupplier.get();
                return p != null ? p.getCharacterPosition().getX() : 0d;
            }
            @Override public double playerY() {
                PlayerCharacter p = playerSupplier.get();
                return p != null ? p.getCharacterPosition().getY() : 0d;
            }
            @Override public double minX() { return 0d; }
            @Override public double minY() { return 0d; }
            @Override public double maxX() { return App.getBoardMaxX(); }
            @Override public double maxY() { return App.getBoardMaxY(); }
            @Override public boolean wouldCollide(double centerX, double centerY, double size) {
                GameMazeWorld maze = mazeSupplier.get();
                if (maze == null || maze.getMazeVectors() == null) return false;
                return WallCollisionUtil.wouldCollideVectors(
                        centerX, centerY, size, App.getBoardMaxX(), App.getBoardMaxY(), maze.getMazeVectors());
            }
        };
    }

    private static double approximateSize(ComputerCharacter character) {
        if (character instanceof ZombieCharacter)        return StageConstants.ZombieCharacterXYSize;
        if (character instanceof GhostCharacter)         return StageConstants.GhostCharacterXYSize;
        if (character instanceof PumpkinBomberCharacter) return StageConstants.PumpkinBomberCharacterXYSize;
        return StageConstants.TouchDistance;
    }

    private static String enemyId(ComputerCharacter character) {
        return Integer.toHexString(System.identityHashCode(character));
    }

    private static int dirSign(double value) {
        return value > 0d ? 1 : value < 0d ? -1 : 0;
    }
}
