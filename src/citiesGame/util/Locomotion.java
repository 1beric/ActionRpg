package citiesGame.util;

import org.joml.Vector3f;
import engine.components.renderable.Transform;
import engine.components.updateable.UpdateableComponent;
import engine.util.Time;

public class Locomotion extends UpdateableComponent {

    private static final float DEFAULT_SPEED = 20f;

    private Vector3f m_Acceleration;
    private Vector3f m_Velocity;

    private Vector3f m_RotationAcceleration;
    private Vector3f m_RotationVelocity;

    private Transform m_Target;

    private float m_Speed;


    public Locomotion(float speed, Transform target) {
        m_Speed = speed;
        m_Target = target;
        m_Acceleration = new Vector3f(0);
        m_Velocity = new Vector3f(0);
        m_RotationAcceleration = new Vector3f(0);
        m_RotationVelocity = new Vector3f(0);
    }

    public Locomotion(float speed) {
        this(speed, null);
    }

    public Locomotion(Transform target) {
        this(DEFAULT_SPEED, target);
    }

    public Locomotion() {
        this(DEFAULT_SPEED, null);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

        updatePosition();
        updateRotation();

    }

    private void updatePosition() {
        // check forces ?

        // acc -> vel -> pos
        m_Velocity.add(m_Acceleration.mul(Time.delta(), new Vector3f()));
        transform()
                .position(transform().position().add(m_Velocity.mul(Time.delta(), new Vector3f())));

    }

    private void updateRotation() {
        // check forces ?

        // acc -> vel

        // vel -> position
    }

    public void addForce(Vector3f force) {
        m_Acceleration.add(force);
    }

    public void acceleration(Vector3f acceleration) {
        m_Acceleration = acceleration;
    }

    public void velocity(Vector3f velocity) {
        m_Velocity = velocity;
    }

    public Transform target() {
        return m_Target;
    }


}
