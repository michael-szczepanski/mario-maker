package physics2d.rigidbody;

import org.joml.Vector2f;
import physics2d.primitives.Circle;
import renderer.Line2D;

public class IntersectionDetector2D {
    // =================================
    // Point vs Primitive Tests
    // =================================
    public static boolean pointOnLine(Vector2f point, Line2D line) {
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        float slope = dy / dx;
        float intercept = line.getEnd().y - (slope * line.getEnd().x);

        // Check the line equation
        // y = mx + b
        return point.y == slope * point.x + intercept;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle) {

    }

    // =================================
    // Line vs Primitive Tests
    // =================================

}
