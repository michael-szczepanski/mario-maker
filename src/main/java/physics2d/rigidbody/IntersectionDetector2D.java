package physics2d.rigidbody;

import org.joml.Vector2f;
import physics2d.primitives.*;
import renderer.Line2D;
import util.JMath;

public class IntersectionDetector2D {
    // =================================
    // Point vs Primitives
    // =================================

    public static boolean pointOnLine(Vector2f point, Line2D line) {
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        if (dx == 0.f) {
            return JMath.compare(point.x, line.getStart().x);
        }
        float slope = dy / dx;
        float intercept = line.getEnd().y - (slope * line.getEnd().x);

        // Check the line equation
        return point.y == slope * point.x + intercept;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle) {
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToPoint = new Vector2f(point).sub(circleCenter);

        // Check if length of vector is smaller than radius length
        return centerToPoint.lengthSquared() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean pointInAABB(Vector2f point, AABB box) {
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        // check if point x and y are both in the bounds of square x and y
        return point.x <= max.x && min.x <= point.x &&
                point.y <= max.y && min.y <= point.y;
    }

    // TODO: Expand on Unit tests
    public static boolean pointInBox2D(Vector2f point, Box2D box) {
        // Translate the point into local space
        Vector2f pointLocalBoxSpace = new Vector2f(point);
        JMath.rotate(pointLocalBoxSpace, box.getRigidbody().getRotation(), box.getRigidbody().getPosition());

        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        // check if point x and y are both in the bounds of square x and y
        return pointLocalBoxSpace.x <= max.x && min.x <= pointLocalBoxSpace.x &&
                pointLocalBoxSpace.y <= max.y && min.y <= pointLocalBoxSpace.y;
    }

    // =================================
    // Line vs Primitives
    // =================================

    // TODO: Unit tests
    public static boolean lineAndCircle(Line2D line, Circle circle) {
        if (pointInCircle(line.getStart(), circle) || pointInCircle(line.getEnd(), circle)) {
            return true;
        }

        Vector2f ab = new Vector2f(line.getEnd()).sub(line.getStart());

        // Project point (circle position) onto ab (line segment)
        // parametrized position t
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToLineStart = new Vector2f(circleCenter).sub(line.getStart());
        float t = centerToLineStart.dot(ab) / ab.dot(ab);

        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        // Find the closest point to the line segment
        Vector2f closestPoint = new Vector2f(line.getStart()).add(ab.mul(t));

        return pointInCircle(closestPoint, circle);
    }

    // TODO: Unit tests
    public static boolean lineAndAABB(Line2D line, AABB box) {
        if (pointInAABB(line.getStart(), box) || pointInAABB(line.getEnd(), box)) {
            return true;
        }

        Vector2f unitVector = new Vector2f(line.getEnd()).sub(line.getStart());
        unitVector.normalize();
        unitVector.x = unitVector.x != 0 ? 1.0f / unitVector.x : 0f;
        unitVector.y = unitVector.y != 0 ? 1.0f / unitVector.y : 0f;

        Vector2f min = box.getMin();
        min.sub(line.getStart()).mul(unitVector);
        Vector2f max = box.getMax();
        max.sub(line.getStart()).mul(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));

        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        float t = (tmin < 0f) ? tmax : tmin;
        return t > 0f && t * t < line.lengthSquared();
    }

    // TODO: Unit tests
    public static boolean lineAndBox2D(Line2D line, Box2D box) {
        float theta = -box.getRigidbody().getRotation();
        Vector2f center = box.getRigidbody().getPosition();
        Vector2f localStart = new Vector2f(line.getStart());
        Vector2f localEnd = new Vector2f(line.getEnd());
        JMath.rotate(localStart, theta, center);
        JMath.rotate(localEnd, theta, center);

        Line2D localLine = new Line2D(localStart, localEnd);
        AABB aabb = new AABB(box.getMin(), box.getMax());

        return lineAndAABB(localLine, aabb);
    }

    // =================================
    // Raycasts
    // =================================

    // TODO: Unit tests
    public static boolean raycast(Circle circle, Ray2D ray, RaycastResult result) {
        RaycastResult.reset(result);
        Vector2f originToCircle = new Vector2f(circle.getCenter()).sub(ray.getOrigin());
        float radiusSq = circle.getRadius() * circle.getRadius();
        float originToCircleLengthSq = originToCircle.lengthSquared();

        // Project the vector from the ray origin onto the direction of the ray
        float a = originToCircle.dot(ray.getDirection());
        float bSq = originToCircleLengthSq - (a * a);
        if (radiusSq - bSq < 0.0f) {
            return false;
        }

        float f = (float)Math.sqrt(radiusSq - bSq);
        float t = 0;
        if (originToCircleLengthSq < radiusSq) {
            // Ray starts inside the circle
            t = a + f;
        } else {
            t = a - f;
        }

        if (result != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(point).sub(circle.getCenter());
            normal.normalize();
            result.init(point, normal, t, true);
        }

        return true;
    }

    // TODO: Unit tests
    public static boolean raycast(AABB box, Ray2D ray, RaycastResult result) {
        RaycastResult.reset(result);
        Vector2f unitVector = ray.getDirection();
        unitVector.normalize();
        unitVector.x = unitVector.x != 0 ? 1.0f / unitVector.x : 0f;
        unitVector.y = unitVector.y != 0 ? 1.0f / unitVector.y : 0f;

        Vector2f min = box.getMin();
        min.sub(ray.getOrigin()).mul(unitVector);
        Vector2f max = box.getMax();
        max.sub(ray.getOrigin()).mul(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));

        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        float t = (tmin < 0f) ? tmax : tmin;
        boolean hit = t > 0f;

        if (!hit) {
            return false;
        }

        if (result != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(ray.getOrigin()).sub(point);
            normal.normalize();

            result.init(point, normal, t, true);
        }

        return true;
    }

    public static boolean raycast(Box2D box, Ray2D ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2f halfSize = box.getHalfSize();
        Vector2f xAxis = new Vector2f(1, 0);
        Vector2f yAxis = new Vector2f(0, 1);
        JMath.rotate(xAxis, -box.getRigidbody().getRotation(), new Vector2f(0, 0));
        JMath.rotate(yAxis, -box.getRigidbody().getRotation(), new Vector2f(0, 0));

        Vector2f p = new Vector2f(box.getRigidbody().getPosition()).sub(ray.getOrigin());
        // Project direction of the ray onto each axist of the box
        Vector2f f = new Vector2f(
                xAxis.dot(ray.getDirection()),
                yAxis.dot(ray.getDirection())
        );

        // Next, project p onto every axis of the box
        Vector2f e = new Vector2f(
                xAxis.dot(p),
                yAxis.dot(p)
        );

        float[] tArr = {0, 0, 0, 0};
        for (int i = 0; i < 2; i++) {
            if (JMath.compare(f.get(i), 0)) {
                // If the ray is parallel to the current axis, and the origin of the ray is not inside, we have no hit
                if (-e.get(i) - halfSize.get(i) > 0 || -e.get(i) + halfSize.get(i) < 0) {
                    return false;
                }
                f.setComponent(i, 0.00001f); // Set to small value to avoid division by 0
            }
            tArr[i * 2] = (e.get(i) + halfSize.get(i) / f.get(i));     // tmax
            tArr[i * 2 + 1] = (e.get(i) - halfSize.get(i) / f.get(i)); // tmin
        }

        float tmin = Math.max(Math.min(tArr[0], tArr[1]), Math.min(tArr[2], tArr[3]));
        float tmax = Math.min(Math.max(tArr[0], tArr[1]), Math.max(tArr[2], tArr[3]));

        float t = (tmin < 0f) ? tmax : tmin;
        boolean hit = t > 0f;

        if (!hit) {
            return false;
        }

        if (result != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(ray.getOrigin()).sub(point);
            normal.normalize();

            result.init(point, normal, t, true);
        }

        return true;
    }

    // =================================
    // Circle vs Primitive
    // =================================

    public static boolean circleAndLine(Circle circle, Line2D line) {
       return lineAndCircle(line, circle);
    }

    // TODO: unit tests
    public static boolean circleAndCircle(Circle c1, Circle c2) {
        Vector2f vecBetweenCenters = new Vector2f(c1.getCenter()).sub(c2.getCenter());
        float radiiSum = c1.getRadius() + c2.getRadius();
        return vecBetweenCenters.lengthSquared() <= radiiSum * radiiSum;
    }
}
