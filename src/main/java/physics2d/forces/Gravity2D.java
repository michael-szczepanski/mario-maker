package physics2d.forces;

import org.joml.Vector2f;
import physics2d.rigidbody.Rigidbody2D;

public class Gravity2D implements ForceGenerator {

    public Vector2f gravity;

    public Gravity2D(Vector2f force) {
        this.gravity.set(force);
    }

    @Override
    public void updateForce(Rigidbody2D body, float dt) {
        body.addForce(new Vector2f(gravity).mul(body.getMass()));
    }
}
