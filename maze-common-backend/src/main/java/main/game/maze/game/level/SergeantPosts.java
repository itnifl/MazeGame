package main.game.maze.game.level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.game.maze.mazeworld.Point2D;

/** Where the sergeants stand: free corridor points near the heart, kept apart from each other. */
public final class SergeantPosts {

    private SergeantPosts() {
    }

    /**
     * Chooses up to {@code count} posts from {@code corridorPoints}: the points whose distance from the
     * heart is closest to {@code distanceFromHeart}, each at least {@code minSpacing} from the ones already
     * chosen. Deterministic; fewer posts come back when there are not enough suitable points.
     */
    public static List<Point2D> choose(List<Point2D> corridorPoints, double heartX, double heartY,
                                       double distanceFromHeart, int count, double minSpacing) {
        List<Point2D> posts = new ArrayList<>();
        if (count <= 0 || corridorPoints == null) {
            return posts;
        }
        List<Point2D> byCloseness = new ArrayList<>(corridorPoints);
        byCloseness.sort(Comparator
                .comparingDouble((Point2D p) -> Math.abs(Math.hypot(p.getX() - heartX, p.getY() - heartY) - distanceFromHeart))
                // spread the posts round the heart: ties go by angle
                .thenComparingDouble(p -> Math.atan2(p.getY() - heartY, p.getX() - heartX)));
        for (Point2D candidate : byCloseness) {
            if (posts.size() == count) {
                break;
            }
            boolean apart = true;
            for (Point2D post : posts) {
                if (Math.hypot(candidate.getX() - post.getX(), candidate.getY() - post.getY()) < minSpacing) {
                    apart = false;
                    break;
                }
            }
            if (apart) {
                posts.add(candidate);
            }
        }
        return posts;
    }
}
