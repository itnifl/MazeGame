package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Sergeant posts: close to the heart, apart from each other")
class SergeantPostsTest {

    private static final SergeantSpec SPEC = LevelCatalogLoader.loadDefault().level(2).sergeant();

    private static List<Point2D> grid() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 15; x < 800; x += 30) {
            for (int y = 15; y < 660; y += 30) {
                points.add(new Point2D(x, y));
            }
        }
        return points;
    }

    @Test
    @DisplayName("picks the requested number of posts near the post distance from the heart")
    void picksCount() {
        for (int count : new int[] {1, 2, 4}) {
            List<Point2D> posts = SergeantPosts.choose(grid(), 400, 330, SPEC.postDistanceFromHeart(), count, 60);
            assertEquals(count, posts.size());
            for (Point2D post : posts) {
                double distance = Math.hypot(post.getX() - 400, post.getY() - 330);
                assertTrue(Math.abs(distance - SPEC.postDistanceFromHeart()) <= 45,
                        "close to the heart ring: " + distance);
            }
        }
    }

    @Test
    @DisplayName("posts keep their distance from each other")
    void spaced() {
        List<Point2D> posts = SergeantPosts.choose(grid(), 400, 330, SPEC.postDistanceFromHeart(), 4, 60);
        for (int a = 0; a < posts.size(); a++) {
            for (int b = a + 1; b < posts.size(); b++) {
                assertTrue(Math.hypot(posts.get(a).getX() - posts.get(b).getX(),
                        posts.get(a).getY() - posts.get(b).getY()) >= 60);
            }
        }
    }

    @Test
    @DisplayName("fewer free points than posts gives fewer posts, no points gives none")
    void notEnoughPoints() {
        assertEquals(1, SergeantPosts.choose(List.of(new Point2D(10, 10)), 0, 0, 100, 4, 60).size());
        assertEquals(0, SergeantPosts.choose(List.of(), 0, 0, 100, 4, 60).size());
        assertEquals(0, SergeantPosts.choose(grid(), 400, 330, 110, 0, 60).size());
    }

    @Test
    @DisplayName("the same input gives the same posts")
    void deterministic() {
        assertEquals(SergeantPosts.choose(grid(), 400, 330, 110, 4, 60).toString(),
                SergeantPosts.choose(grid(), 400, 330, 110, 4, 60).toString());
    }
}
