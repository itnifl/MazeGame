package main.game.maze;

import javafx.geometry.Point2D;

public class Vector2D {
    private Point2D start;
    private Point2D end;

    /**
     * Constructs a new Vector2D object with the given start and end points.
     *
     * @param start the starting point of the vector
     * @param end   the ending point of the vector
     */
    public Vector2D(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a new Vector2D object with the given start and end points.
     * 
     * @param startX the x coordinate of the start point
     * @param startY the y coordinate of the start point
     * @param endX   the x coordinate of the end point
     * @param endY   the y coordinate of the end point
     */
    public Vector2D(double startX, double startY, double endX, double endY) {
        this.start = new Point2D(startX, startY);
        this.end = new Point2D(endX, endY);
    }

    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }

    /**
     * Calculates and returns the magnitude (length) of this vector.
     * 
     * The magnitude of a vector is a scalar value that represents the length or
     * size of the vector.
     * It is calculated as the square root of the sum of the squares of its
     * components.
     * In two-dimensional space, the magnitude of a vector with components (x, y) is
     * given by the formula:
     * magnitude = sqrt(x^2 + y^2)
     *
     * @return the magnitude of this vector
     */
    public double magnitude() {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Normalizes this vector to a unit vector (magnitude of 1) and returns the
     * result.
     * 
     * Normalizing a vector means scaling it to a length of 1 while preserving its
     * direction. In other words,
     * it involves dividing each component of the vector by the vector's magnitude
     * (length), resulting in a unit vector.
     * Normalized vectors are often used in mathematical calculations,
     * because they simplify the computation of dot products and other operations.
     *
     * @return the normalized vector
     */
    public Vector2D normalize(int factor) {
        double mag = magnitude();
        double dx = (end.getX() - start.getX()) / mag;
        double dy = (end.getY() - start.getY()) / mag;
        return new Vector2D(start, new Point2D(start.getX() + dx * factor, start.getY() + dy * factor));
    }

    /**
     * Adds the given vector to this vector and returns the result.
     *
     * @param other the vector to add to this vector
     * @return the resulting vector after addition
     */
    public Vector2D add(Vector2D other) {
        double startX = start.getX() + other.getStart().getX();
        double startY = start.getY() + other.getStart().getY();
        double endX = end.getX() + other.getEnd().getX();
        double endY = end.getY() + other.getEnd().getY();
        return new Vector2D(new Point2D(startX, startY), new Point2D(endX, endY));
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param other the vector to subtract from this vector
     * @return a new Vector2D representing the result of the subtraction
     */
    public Vector2D subtract(Vector2D other) {
        double startX = start.getX() - other.getStart().getX();
        double startY = start.getY() - other.getStart().getY();
        double endX = end.getX() - other.getEnd().getX();
        double endY = end.getY() - other.getEnd().getY();
        return new Vector2D(new Point2D(startX, startY), new Point2D(endX, endY));
    }

    /**
     * Calculates the dot product of this vector and the given vector.
     * 
     * The dot product of two vectors is a scalar (single number) that represents
     * the projection of one vector onto the other.
     * Geometrically, the dot product is a scalar value that indicates how "aligned"
     * two vectors are with each other: if the dot product is positive,
     * the vectors are pointing in roughly the same direction, if it is negative,
     * they are pointing in roughly opposite directions, and if it is zero, they are
     * perpendicular to each other.
     * Mathematically, if two vectors are represented as A = (A1, A2) and B = (B1,
     * B2),
     * then their dot product is given by the formula A1 * B1 + A2 * B2.
     *
     * @param other the other vector to calculate the dot product with
     * @return the dot product of this vector and the given vector
     */
    public double dotProduct(Vector2D other) {
        double dx1 = end.getX() - start.getX();
        double dy1 = end.getY() - start.getY();
        double dx2 = other.getEnd().getX() - other.getStart().getX();
        double dy2 = other.getEnd().getY() - other.getStart().getY();
        return dx1 * dx2 + dy1 * dy2;
    }

    /**
     * Calculates the magnitude of the cross product of this vector and the given
     * vector.
     * 
     * The cross product of two vectors is a vector that is perpendicular to both
     * vectors, and its magnitude
     * is equal to the area of the parallelogram that they form. In two-dimensional
     * space, the cross product
     * of two vectors (x1, y1) and (x2, y2) is given by the formula x1 * y2 - y1 *
     * x2.
     *
     * @param other the other vector to calculate the cross product with
     * @return the magnitude of the cross product of this vector and the given
     *         vector
     */
    public double getCrossProductMagnitude(Vector2D other) {
        double dx1 = end.getX() - start.getX();
        double dy1 = end.getY() - start.getY();
        double dx2 = other.getEnd().getX() - other.getStart().getX();
        double dy2 = other.getEnd().getY() - other.getStart().getY();
        return dx1 * dy2 - dy1 * dx2;
    }

    /**
     * Multiplies this vector by the given scalar value and returns the result.
     *
     * @param scalar the scalar value to multiply this vector by
     * @return the resulting vector after multiplication
     */
    public Vector2D multiply(double scalar) {
        double newX = end.getX() - start.getX();
        double newY = end.getY() - start.getY();
        newX *= scalar;
        newY *= scalar;
        return new Vector2D(start.getX(), start.getY(), start.getX() + newX, start.getY() + newY);
    }

    // Returns the vertical distance of a vector, calculated by subtracting the Y
    // coordinate of the starting point from the Y coordinate of the ending point
    public double getYVectorCoordinate() {
        return end.getY() - start.getY();
    }

    // Returns the horizontal distance of a vector, calculated by subtracting the X
    // coordinate of the starting point from the X coordinate of the ending point
    public double getXVectorCoordinate() {
        return end.getX() - start.getX();
    }

    // Given three collinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    private static boolean onSegment(Point2D p, Point2D q, Point2D r) {
        if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY())) {
            return true;
        }
        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    private static int orientation(Point2D p, Point2D q, Point2D r) {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0)
            return 0; // collinear

        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    // Thank you Geeks for Geeks -
    // https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
    public boolean doIntersect(Vector2D other, int xyOffset) {

        Point2D p1 = this.getStart();
        Point2D p2 = other.getStart();
        Point2D q1 = this.getEnd();
        Point2D q2 = other.getEnd();

        var directionDown = q1.getX() - p1.getX() > 0 || q1.getY() - p1.getY() > 0;
        if (directionDown) {
            p1 = p1.add(xyOffset, xyOffset);
            q1 = q1.add(xyOffset, xyOffset);
        }

        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;

        // p1, q1 and q2 are collinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;

        return false; // Doesn't fall in any of the above cases
    }

    @Override
    public String toString() {
        return "[" + this.start.getX() + ", " + this.start.getY() + "], [" + this.end.getX() + ", " + this.end.getY()
                + "]";
    }

}
