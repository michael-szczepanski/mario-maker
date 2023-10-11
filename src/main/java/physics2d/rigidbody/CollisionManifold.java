package physics2d.rigidbody;

import org.joml.Vector2f;

import java.util.List;

public class CollisionManifold {

    private Vector2f normal;
    private List<Vector2f> contactPoint;
    private float depth;


    public CollisionManifold(Vector2f normal, List<Vector2f> contactPoint, float depth) {
        this.normal = normal;
        this.contactPoint = contactPoint;
        this.depth = depth;
    }

    //=========
    // Getters
    //=========

    public Vector2f getNormal() {
        return normal;
    }

    public List<Vector2f> getContactPoint() {
        return contactPoint;
    }

    public float getDepth() {
        return depth;
    }
}
