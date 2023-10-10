package physics2d;

import org.joml.Vector2f;
import physics2d.forces.ForceRegistry;
import physics2d.rigidbody.Rigidbody2D;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem2D {
    private ForceRegistry forceRegistry;
    private List<Rigidbody2D> rigidbodies;
//    private Gravity2D gravity;
    private float fixedUpdate;

    public PhysicsSystem2D(float fixedUpdateDt, Vector2f gravity) {
        forceRegistry = new ForceRegistry();
        rigidbodies = new ArrayList<>();

        fixedUpdate = fixedUpdateDt;
    }

    public void update(float dt) {

    }

    public void fixedUpdate() {
        forceRegistry.updateForces(fixedUpdate);

        // Update the velocities of all rigidbodies
    }

    public void addRigidbody(Rigidbody2D body) {
        this.rigidbodies.add(body);
        // Register gravity
    }
}
