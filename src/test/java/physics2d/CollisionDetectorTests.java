package physics2d;

import org.joml.Vector2f;
import org.junit.jupiter.api.Test;
import physics2d.primitives.AABB;
import physics2d.primitives.Box2D;
import physics2d.primitives.Circle;
import physics2d.rigidbody.IntersectionDetector2D;
import physics2d.rigidbody.Rigidbody2D;
import renderer.Line2D;
import util.JMath;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionDetectorTests {
    private final float EPSILON = 0.000001f;

    // ========================================================================
    // pointOnLine IntersectionDetector2d tests
    // ========================================================================

    @Test
    public void pointOnLineShouldReturnTrueTest1() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnTrueTest2() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(6, 2);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnTrueTest3() {
        Line2D line = new Line2D(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(10, 10);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnTrueTest4() {
        Line2D line = new Line2D(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(16, 12);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnTrueTest5() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnTrueTest6() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(12, 4);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnTrueTest7() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(0, 5);

        assertTrue(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnFalseTest1() {
        Line2D line = new Line2D(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(14, 12);

        assertFalse(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnFalseTest2() {
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(5, 5);

        assertFalse(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    @Test
    public void pointOnLineShouldReturnFalseTest3() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(4, 2);

        assertFalse(
                IntersectionDetector2D.pointOnLine(point, line)
        );
    }

    // ========================================================================
    // pointInCircle IntersectionDetector2d tests
    // ========================================================================

    @Test
    public void pointInCircleShouldReturnTrueTest1() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(3, -2);

        assertTrue(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointInCircleShouldReturnTrueTest2() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f, 0);

        assertTrue(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointInCircleShouldReturnFalseTest1() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-6, -6);

       assertFalse(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointInCircleShouldReturnTrueTest4() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(3 + 10, -2 + 10);

        assertTrue(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointInCircleShouldReturnTrueTest5() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f + 10, 0 + 10);

        assertTrue(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointInCircleShouldReturnFalseTest2() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-6 + 10, -6 + 10);

        assertFalse(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }

    @Test
    public void pointInCircleShouldReturnFalseTest3() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-3.9f + 10, -3.9f + 10);

        assertFalse(
                IntersectionDetector2D.pointInCircle(point, circle)
        );
    }
}
