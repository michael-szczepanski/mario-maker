package physics2d;

import org.joml.Vector2f;
import org.junit.jupiter.api.Test;
import physics2d.primitives.AABB;
import physics2d.primitives.Circle;
import physics2d.rigidbody.IntersectionDetector2D;
import renderer.Line2D;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionDetectorTests {
    private final float EPSILON = 0.000001f;

    // ==================
    // pointInAABB tests
    // ==================

    @Test
    public void pointInAABBShouldReturnTrue() {
        AABB aabb = new AABB(new Vector2f(0.0f,  0.0f), new Vector2f(1.0f, 1.0f));
        Vector2f point = new Vector2f(0.2f, 0.2f);

        assertTrue(
                IntersectionDetector2D.pointInAABB(point, aabb)
        );
    }

    @Test
    public void pointOnAABBShouldReturnTrue() {
        AABB aabb = new AABB(new Vector2f(0.0f,  0.0f), new Vector2f(1.0f, 1.0f));
        Vector2f point = new Vector2f(0.5f, 0.5f);

        assertTrue(
                IntersectionDetector2D.pointInAABB(point, aabb)
        );
    }

    @Test
    public void pointNotInAABBShouldReturnFalse() {
        AABB aabb = new AABB(new Vector2f(0.0f,  0.0f), new Vector2f(1.0f, 1.0f));
        Vector2f point = new Vector2f(1.5f, 1.5f);

        assertFalse(
                IntersectionDetector2D.pointInAABB(point, aabb)
        );
    }

    // ====================
    // pointInCircle tests
    // ====================

    @Test
    public void pointInCircleShouldReturnTrue() {
        Circle circle = new Circle();
        Vector2f point = new Vector2f(0.5f, 0.0f);

        assertTrue(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointOnCircleShouldReturnTrue() {
        Circle circle = new Circle();
        Vector2f point = new Vector2f(1.0f, 0.0f);

        assertTrue(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointOutsideOfCircleShouldReturnFalse() {
        Circle circle = new Circle();
        Vector2f point = new Vector2f(1.0f, 1.0f);

        assertFalse(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    // ==================
    // pointOnLine tests
    // ==================

    @Test
    public void startPointOnLine2DShouldReturnTrue() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void endPointOnLine2DShouldReturnTrue() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(12, 4);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnVerticalLine2DShouldReturnTrue() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(0, 5);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointNotOnLineShouldReturnFalse() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(5, 5);

        assertFalse(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }
}
