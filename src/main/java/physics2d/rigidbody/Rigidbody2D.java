package physics2d.rigidbody;

import components.Component;
import jade.Transform;
import org.joml.Vector2f;
import physics2d.primitives.Collider2D;

public class Rigidbody2D extends Component {
    private Transform rawTransform;
    private Collider2D collider;

    private Vector2f position = new Vector2f();
    private float rotation = 0.0f;
    private float mass = 0.0f;
    private float inverseMass = 0.0f;

    private Vector2f forceAccum = new Vector2f();
    private Vector2f linearVelocity = new Vector2f();
    private float angularVelocity = 0.0f;
    private float linearDamping = 0.0f;
    private float angularDamping = 0.0f;

    private boolean fixedRotation = false;

    public void physicsUpdate(float dt) {
        if (this.mass == 0.0f) return;

        // Calculate linear velocity
        Vector2f acceleration = new Vector2f(forceAccum).mul(this.inverseMass);
        linearVelocity.add(acceleration.mul(dt));

        // Update linear position
        this.position.add(new Vector2f(linearVelocity).mul(dt));

        synchCollisionTransforms();
        clearAccumulators();
    }

    public void synchCollisionTransforms() {
        if (rawTransform != null) {
            rawTransform.position.set(this.position);
        }
    }

    public void clearAccumulators() {
        this.forceAccum.zero();
    }

    //================
    // Helper methods
    //================

    public void addForce(Vector2f force) {
        this.forceAccum.add(force);
    }

    public boolean hasInfiniteMass() {
        return this.mass == 0.0f;
    }

    //=====================
    // Getters and Setters
    //=====================
    public Vector2f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setTransform(Vector2f position, float rotation) {
        this.position.set(position);
        this.rotation = rotation;
    }

    public void setTransform(Vector2f position) {
        this.position.set(position);
    }

    public void setRawTransform(Transform rawTransform) {
        this.rawTransform = rawTransform;
        this.position.set(rawTransform.position);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
    }

    public Collider2D getCollider() {
        return this.collider;
    }

    public void setCollider(Collider2D collider) {
        this.collider = collider;
    }
}
