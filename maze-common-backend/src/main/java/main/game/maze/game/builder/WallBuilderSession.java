package main.game.maze.game.builder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import main.game.maze.game.builder.CellGrid.Cell;
import main.game.maze.game.builder.CellGrid.Edge;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;

/**
 * What the wall builders of a level do, decided once for both frontends.
 *
 * <p>The builders start round the heart and wander <b>randomly outwards</b> through the corridors until they are
 * {@link WallBuilderSpec#startRadiusCells()} cells away: the first ring. There they wall up the corridors that lead into
 * the block of cells inside the ring (the heart can be left through them), a corridor width every
 * {@link WallBuilderSpec#buildSecondsPerCorridor()} seconds, one piece at a time. When every corridor of the ring is
 * closed they <b>move one corridor width closer</b> and wall up the next ring, and so on down to
 * {@link WallBuilderSpec#blockRadiusCells()}. Two builders never take the same corridor, a corridor the player breaks
 * open is built again (the outermost ring with an open corridor is always the one being worked on), and a half-built
 * wall of a killed builder is finished by another.
 *
 * <p>The frontend keeps the builder enemies (health, sprites, being killed) and calls {@link #tick} with where they are
 * and the live walls; the session answers where each builder is now and which pieces of wall went up, and the frontend
 * moves the enemy and adds the pieces to its world. World space, y-down.
 */
public final class WallBuilderSession {

    /** A builder as the frontend sees it; a dead one is dropped (its half-built wall stays for the others). */
    public record Builder(String id, double x, double y, boolean alive) {
    }

    /** Where a builder is now. */
    public record Move(String id, double x, double y) {
    }

    /** A piece of wall that went up. */
    public record Piece(String builderId, Vector2D wall) {
    }

    public record Result(List<Move> moves, List<Piece> pieces) {
        public static final Result NOTHING = new Result(List.of(), List.of());
    }

    /** The longest stretch one tick may advance (after a pause the crew carries on instead of jumping ahead). */
    public static final double MAX_TICK_SECONDS = 0.25;
    private static final double PLAN_INTERVAL_SECONDS = 0.5;
    /** A piece is not put up on top of the player. */
    private static final double PLAYER_CLEARANCE = 28.0;
    private static final double ARRIVED = 0.5;
    /** When wandering, a step away from the heart is this many times likelier than a step back towards it. */
    private static final int OUTWARD_WEIGHT = 6;
    private static final int SIDEWAYS_WEIGHT = 2;
    private static final int INWARD_WEIGHT = 1;

    private enum Phase { IDLE, WALKING, BUILDING }

    private static final class State {
        double x;
        double y;
        Phase phase = Phase.IDLE;
        final Deque<Point2D> path = new ArrayDeque<>();
        Edge site;
        Cell previous;
        double timer;
        double planWait;

        State(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private final WallBuilderSpec spec;
    private final int widthPx;
    private final int heightPx;
    private final Cell heart;
    private final Random random;
    private final Map<String, State> states = new HashMap<>();
    /**
     * Every corridor a builder has worked on, kept for the whole level: a half-built wall (its builder was killed, or
     * the player broke part of it) is finished by another builder, while a corridor that is partly walled by the maze
     * itself is left alone.
     */
    private final List<Edge> started = new ArrayList<>();

    public WallBuilderSession(WallBuilderSpec spec, int widthPx, int heightPx, double heartX, double heartY) {
        this(spec, widthPx, heightPx, heartX, heartY, new Random());
    }

    public WallBuilderSession(WallBuilderSpec spec, int widthPx, int heightPx, double heartX, double heartY, Random random) {
        this.spec = spec;
        this.widthPx = widthPx;
        this.heightPx = heightPx;
        this.heart = new CellGrid(List.of(), widthPx, heightPx).cellOf(heartX, heartY);
        this.random = random;
    }

    public WallBuilderSpec spec() {
        return spec;
    }

    /**
     * Advances the crew.
     *
     * @param walls the live walls (world space), so what has been built or broken is seen
     */
    public Result tick(double dt, List<Builder> builders, List<Vector2D> walls, double playerX, double playerY) {
        dt = Math.min(Math.max(0, dt), MAX_TICK_SECONDS);
        CellGrid grid = new CellGrid(walls, widthPx, heightPx);
        List<Move> moves = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        List<Vector2D> placedNow = new ArrayList<>();

        dropTheDead(builders);
        for (Builder builder : builders) {
            if (!builder.alive()) {
                continue;
            }
            State state = states.computeIfAbsent(builder.id(), id -> new State(builder.x(), builder.y()));
            switch (state.phase) {
                case IDLE -> idle(state, grid, dt);
                case WALKING -> walk(state, grid, dt);
                case BUILDING -> build(builder.id(), state, grid, dt, playerX, playerY, placedNow, pieces);
                default -> { }
            }
            moves.add(new Move(builder.id(), state.x, state.y));
        }
        return moves.isEmpty() ? Result.NOTHING : new Result(List.copyOf(moves), List.copyOf(pieces));
    }

    /** The corridor this builder is heading for or working on, if any. */
    public Optional<Edge> siteOf(String builderId) {
        State state = states.get(builderId);
        return state == null ? Optional.empty() : Optional.ofNullable(state.site);
    }

    /** The ring (cells from the heart) the builders are working on now, or -1 when every ring is closed. */
    public int ringInProgress(List<Vector2D> walls) {
        return targetRing(new CellGrid(walls, widthPx, heightPx));
    }

    /** The corridors that are not closed yet in any ring: what is left for the builders to do. */
    public List<Edge> openingsLeft(List<Vector2D> walls) {
        CellGrid grid = new CellGrid(walls, widthPx, heightPx);
        List<Edge> left = new ArrayList<>();
        for (int ring = spec.startRadiusCells(); ring >= spec.blockRadiusCells(); ring--) {
            for (Edge edge : entrances(grid, ring)) {
                if (!grid.isClosed(edge)) {
                    left.add(edge);
                }
            }
        }
        return left;
    }

    private void dropTheDead(List<Builder> builders) {
        Iterator<Map.Entry<String, State>> iterator = states.entrySet().iterator();
        while (iterator.hasNext()) {
            String id = iterator.next().getKey();
            boolean alive = builders.stream().anyMatch(b -> b.id().equals(id) && b.alive());
            if (!alive) {
                iterator.remove();   // its site is free again: the reservation was only this state
            }
        }
    }

    // ---------------------------------------------------------------- rings

    /** The outermost ring that still has a corridor to close; -1 when every ring is closed. */
    private int targetRing(CellGrid grid) {
        for (int ring = spec.startRadiusCells(); ring >= spec.blockRadiusCells(); ring--) {
            for (Edge edge : entrances(grid, ring)) {
                if (!grid.isClosed(edge) && (grid.isOpen(edge) || isStarted(edge))) {
                    return ring;
                }
            }
        }
        return -1;
    }

    /**
     * The corridors the heart can be left through at this ring: between the block of cells within {@code ring} cells
     * of the heart and the cells just outside it, whose inside cell the heart reaches without leaving the block.
     * Sealing all of them shuts the heart in; a corridor whose inside cell is cut off from the heart inside the block
     * needs no wall.
     */
    private List<Edge> entrances(CellGrid grid, int ring) {
        Set<Cell> reach = new HashSet<>();
        Deque<Cell> queue = new ArrayDeque<>();
        reach.add(heart);
        queue.add(heart);
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            for (Cell next : grid.neighbours(cell)) {
                if (distance(next) <= ring && !reach.contains(next) && grid.isOpen(new Edge(cell, next))) {
                    reach.add(next);
                    queue.add(next);
                }
            }
        }
        List<Edge> edges = new ArrayList<>();
        for (Cell inside : reach) {
            if (distance(inside) != ring) {
                continue;
            }
            for (Cell outside : grid.neighbours(inside)) {
                if (distance(outside) == ring + 1) {
                    edges.add(new Edge(inside, outside));
                }
            }
        }
        return edges;
    }

    private int distance(Cell cell) {
        return Math.max(Math.abs(cell.row() - heart.row()), Math.abs(cell.col() - heart.col()));
    }

    // ---------------------------------------------------------------- planning

    private void idle(State state, CellGrid grid, double dt) {
        state.planWait -= dt;
        if (state.planWait > 0) {
            return;
        }
        state.planWait = PLAN_INTERVAL_SECONDS;
        int ring = targetRing(grid);
        if (ring < 0) {
            return;   // every ring is closed
        }
        Cell here = grid.cellOf(state.x, state.y);
        if (distance(here) < ring) {
            wanderOutwards(state, grid, here);   // not at the ring diameter yet: walk on, outwards
            return;
        }
        List<Edge> wanted = new ArrayList<>();
        for (Edge edge : entrances(grid, ring)) {
            if (!grid.isClosed(edge) && !reserved(edge) && (grid.isOpen(edge) || isStarted(edge))) {
                wanted.add(edge);
            }
        }
        if (wanted.isEmpty()) {
            return;
        }
        // breadth first through the open corridors to the nearest inside cell of a corridor that needs a wall
        Map<Cell, Cell> cameFrom = new HashMap<>();
        Deque<Cell> queue = new ArrayDeque<>();
        queue.add(here);
        cameFrom.put(here, here);
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            for (Edge edge : wanted) {
                if (edge.a().equals(cell)) {   // always from the heart side: after the wall is up the builder is still inside it
                    begin(state, grid, edge, cell, cameFrom, here);
                    return;
                }
            }
            for (Cell next : grid.neighbours(cell)) {
                if (!cameFrom.containsKey(next) && grid.isOpen(new Edge(cell, next))) {
                    cameFrom.put(next, cell);
                    queue.add(next);
                }
            }
        }
    }

    /** One random step through an open corridor, mostly away from the heart (not straight back when it can be helped). */
    private void wanderOutwards(State state, CellGrid grid, Cell here) {
        List<Cell> options = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        int total = 0;
        for (Cell next : grid.neighbours(here)) {
            if (!grid.isOpen(new Edge(here, next))
                    || (next.equals(state.previous) && hasOtherOpening(grid, here, state.previous))) {
                continue;
            }
            int weight = distance(next) > distance(here) ? OUTWARD_WEIGHT
                    : distance(next) == distance(here) ? SIDEWAYS_WEIGHT : INWARD_WEIGHT;
            options.add(next);
            weights.add(weight);
            total += weight;
        }
        if (options.isEmpty()) {
            return;
        }
        int pick = random.nextInt(total);
        Cell chosen = options.get(options.size() - 1);
        for (int i = 0; i < options.size(); i++) {
            pick -= weights.get(i);
            if (pick < 0) {
                chosen = options.get(i);
                break;
            }
        }
        state.previous = here;
        state.path.clear();
        state.path.add(grid.centerOf(here));   // to the middle of this corridor first (a builder may start off-centre)
        state.path.add(grid.centerOf(chosen));
        state.site = null;
        state.phase = Phase.WALKING;
        state.planWait = 0;
    }

    private static boolean hasOtherOpening(CellGrid grid, Cell cell, Cell except) {
        for (Cell next : grid.neighbours(cell)) {
            if (!next.equals(except) && grid.isOpen(new Edge(cell, next))) {
                return true;
            }
        }
        return false;
    }

    private void begin(State state, CellGrid grid, Edge site, Cell standAt, Map<Cell, Cell> cameFrom, Cell here) {
        Deque<Cell> route = new ArrayDeque<>();
        for (Cell cell = standAt; ; cell = cameFrom.get(cell)) {
            route.addFirst(cell);
            if (cell.equals(here)) {
                break;
            }
        }
        state.path.clear();
        for (Cell cell : route) {
            state.path.add(grid.centerOf(cell));
        }
        state.site = site;
        state.phase = Phase.WALKING;
        state.timer = 0;
        if (!isStarted(site)) {
            started.add(site);
        }
    }

    // ---------------------------------------------------------------- walking and building

    private void walk(State state, CellGrid grid, double dt) {
        if (state.site != null && grid.isClosed(state.site)) {
            giveUp(state);
            return;
        }
        if (routeBlocked(state, grid)) {
            giveUp(state);   // a wall went up across the way (another builder's): plan again
            return;
        }
        double budget = spec.speed() * dt;
        while (budget > 0 && !state.path.isEmpty()) {
            Point2D target = state.path.peek();
            double dx = target.getX() - state.x;
            double dy = target.getY() - state.y;
            double distance = Math.hypot(dx, dy);
            if (distance <= Math.max(budget, ARRIVED)) {
                state.x = target.getX();
                state.y = target.getY();
                budget -= distance;
                state.path.poll();
            } else {
                state.x += dx / distance * budget;
                state.y += dy / distance * budget;
                budget = 0;
            }
        }
        if (state.path.isEmpty()) {
            if (state.site == null) {
                state.phase = Phase.IDLE;   // a wandering step is done: look again
                state.planWait = 0;
            } else {
                state.phase = Phase.BUILDING;
                state.timer = 0;
            }
        }
    }

    /** Whether the next leg of the route now runs through a wall: from the cell the builder is in to the next one. */
    private static boolean routeBlocked(State state, CellGrid grid) {
        Point2D next = state.path.peek();
        if (next == null) {
            return false;
        }
        Cell from = grid.cellOf(state.x, state.y);
        Cell to = grid.cellOf(next.getX(), next.getY());
        if (from.equals(to) || Math.abs(from.row() - to.row()) + Math.abs(from.col() - to.col()) != 1) {
            return false;
        }
        return !grid.isOpen(new Edge(from, to));
    }

    private void build(String id, State state, CellGrid grid, double dt, double playerX, double playerY,
                       List<Vector2D> placedNow, List<Piece> pieces) {
        if (grid.isClosed(state.site)) {
            giveUp(state);
            return;
        }
        state.timer = Math.min(state.timer + dt, spec.secondsPerSegment());
        if (state.timer < spec.secondsPerSegment()) {
            return;
        }
        Vector2D next = null;
        for (Vector2D piece : grid.pieces(state.site)) {
            if (!grid.stands(piece) && !containsPiece(placedNow, piece)) {
                next = piece;
                break;
            }
        }
        if (next == null) {
            finish(state);
            return;
        }
        double midX = (next.getStart().getX() + next.getEnd().getX()) / 2.0;
        double midY = (next.getStart().getY() + next.getEnd().getY()) / 2.0;
        if (Math.hypot(playerX - midX, playerY - midY) < PLAYER_CLEARANCE) {
            return;   // wait until the player has moved: no wall on top of them
        }
        placedNow.add(next);
        pieces.add(new Piece(id, next));
        state.timer = 0;
        boolean allUp = true;
        for (Vector2D piece : grid.pieces(state.site)) {
            allUp &= grid.stands(piece) || containsPiece(placedNow, piece);
        }
        if (allUp) {
            finish(state);
        }
    }

    private void finish(State state) {
        giveUp(state);   // the corridor stays in `started`, so a wall the player breaks only in part is repaired too
    }

    private void giveUp(State state) {
        state.site = null;
        state.phase = Phase.IDLE;
        state.path.clear();
        state.planWait = 0;
    }

    private boolean reserved(Edge edge) {
        for (State state : states.values()) {
            if (state.site != null && state.site.sameAs(edge)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStarted(Edge edge) {
        for (Edge s : started) {
            if (s.sameAs(edge)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsPiece(List<Vector2D> list, Vector2D piece) {
        for (Vector2D other : list) {
            if (Math.round(other.getStart().getX()) == Math.round(piece.getStart().getX())
                    && Math.round(other.getStart().getY()) == Math.round(piece.getStart().getY())
                    && Math.round(other.getEnd().getX()) == Math.round(piece.getEnd().getX())
                    && Math.round(other.getEnd().getY()) == Math.round(piece.getEnd().getY())) {
                return true;
            }
        }
        return false;
    }

    /** New level: no builders, nothing started. */
    public void reset() {
        states.clear();
        started.clear();
    }
}
