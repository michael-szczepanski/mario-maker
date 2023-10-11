package physics2d.rigidbody;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class CollisionManifold {

    private boolean isColliding;
    private Vector2f normal;
    private List<Vector2f> contactPoints;
    private float depth;

    public CollisionManifold() {
        this.normal = new Vector2f();
        this.depth = 0.0f;
        this.isColliding = false;
    }

    public CollisionManifold(Vector2f normal, float depth) {
        this.normal = normal;
        this.contactPoints = new ArrayList<>();
        this.depth = depth;
        this.isColliding = true;
    }

    //==================
    // Helper functions
    //==================

    public void addContactPoint(Vector2f contact) {
        this.contactPoints.add(contact);
    }

    //=========
    // Getters
    //=========

    public Vector2f getNormal() {
        return normal;
    }

    public List<Vector2f> getContactPoints() {
        return contactPoints;
    }

    public float getDepth() {
        return depth;
    }
}
