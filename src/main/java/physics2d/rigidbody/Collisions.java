package physics2d.rigidbody;

import org.joml.Vector2f;
import physics2d.primitives.Circle;
import physics2d.primitives.Collider2D;

public class Collisions {

    public static CollisionManifold findCollisionFeatures(Collider2D c1, Collider2D c2) {
        if (c1 instanceof Circle && c2 instanceof Circle) {
            return findCollisionFeatures((Circle)c1, (Circle)c2);
        } else {
            assert false : "Unknown collider '" + c1.getClass() + "' vs '" + c2.getClass() + "'";
        }

        return null;
    }

    public static CollisionManifold findCollisionFeatures(Circle a, Circle b) {
        CollisionManifold result = new CollisionManifold();
        float sumRadii = a.getRadius() + b.getRadius();
        Vector2f distance = new Vector2f(b.getCenter()).sub(a.getCenter());

        if (distance.lengthSquared() - (sumRadii * sumRadii) > 0) {
            return result;
        }

        // Multiply by 0.5 to separate the circles from the collision point by an equal amount
        // Consider updating to factor in momentum and velocity
        float depth = Math.abs(distance.length() - sumRadii) * 0.5f;
        Vector2f normal = new Vector2f(distance);
        normal.normalize();
        float distanceToPoint = a.getRadius() - depth;
        Vector2f contactPoint = new Vector2f(a.getCenter()).add(new Vector2f(normal).mul(distanceToPoint));

        result = new CollisionManifold(normal, depth);
        result.addContactPoint(contactPoint);

        return result;
    }
}
