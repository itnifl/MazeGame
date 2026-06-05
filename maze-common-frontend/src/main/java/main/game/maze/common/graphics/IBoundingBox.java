package main.game.maze.common.graphics;

/**
 * Backend-neutral axis-aligned bounding box used for collision checks.
 */
public interface IBoundingBox {
    double minX();
    double minY();
    double width();
    double height();

    default double maxX() {
        return minX() + width();
    }

    default double maxY() {
        return minY() + height();
    }

    default boolean intersects(IBoundingBox other) {
        if (other == null) return false;
        return minX() < other.maxX()
            && maxX() > other.minX()
            && minY() < other.maxY()
            && maxY() > other.minY();
    }

    static IBoundingBox of(double minX, double minY, double width, double height) {
        return new IBoundingBox() {
            @Override public double minX() { return minX; }
            @Override public double minY() { return minY; }
            @Override public double width() { return width; }
            @Override public double height() { return height; }
        };
    }
}

