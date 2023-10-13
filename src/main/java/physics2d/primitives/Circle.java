package physics2d.primitives;

import org.joml.Vector2f;
import physics2d.rigidbody.Rigidbody2D;

public class Circle extends Collider2D {

    private float radius = 1.0f;
    private Rigidbody2D rigidbody = new Rigidbody2D();

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public Vector2f getCenter() {
        return rigidbody.getPosition();
    }

    public void setRigidbody(Rigidbody2D rb) {
        this.rigidbody = rb;
    }
}
