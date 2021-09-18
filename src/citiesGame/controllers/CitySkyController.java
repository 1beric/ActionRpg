package citiesGame.controllers;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import engine.components.renderable.Camera;
import engine.components.renderable.Transform;
import engine.components.updateable.UpdateableComponent;
import engine.util.Easing;
import engine.util.MathTools;
import engine.util.Time;
import engine.util.input.KeyboardPicker;
import engine.util.input.MousePicker;
import engine.util.lambdas.EasingFunction;
import engine.util.string.StringTools;

public class CitySkyController extends UpdateableComponent {

	private static final float TRANSLATE_SPEED = 4f;
	private static final float MIN_ROTATE_X = 30;
	private static final float MAX_ROTATE_X = 85;
	private static final float MIN_HEIGHT = 5;
	private static final float MAX_HEIGHT = 50;


	private Camera m_Cam;
	private Transform m_Transform;
	private boolean m_Controlling;

	private boolean m_MouseDown;
	private boolean m_CtrlDown;

	private Vector3f m_PreviousWorldPoint;

	private Vector3f m_CenterPoint;

	private EasingFunction m_ZoomEase;
	private float m_ZoomFactor;

	public CitySkyController() {
		// default values
		m_ZoomFactor = .5f;
		// m_ZoomEase = Easing.EASE_IN_OUT_SINE;
		// m_ZoomEase = Easing.EASE_IN_EXP;
		m_ZoomEase = Easing.LINEAR;
	}

	@Override
	public void init() {

		m_Cam = entity().<Camera>component(Camera.class);
		if (m_Cam == null)
			entity().addComponent(m_Cam = new Camera());

		m_Transform = entity().<Transform>component(Transform.class);

		m_MouseDown = false;
		m_CtrlDown = false;

		m_PreviousWorldPoint = MousePicker.worldPointAtY(0, m_Cam);

		m_CenterPoint = calcCenterPoint();

		zoom(m_ZoomFactor);
	}

	@Override
	public void update() {
		if (!m_Controlling)
			return;

		m_MouseDown = MousePicker.button(0);
		m_CtrlDown = KeyboardPicker.key(GLFW.GLFW_KEY_LEFT_CONTROL);

		Vector3f worldPoint = MousePicker.worldPointAtY(0, m_Cam);

		Vector2f deltaPosition = MousePicker.deltaPosition();

		if (m_MouseDown && m_CtrlDown && deltaPosition.length() != 0) {
			translate(worldPoint, m_PreviousWorldPoint);
		}
		if (m_MouseDown && !m_CtrlDown && deltaPosition.length() != 0) {
			rotate(worldPoint, m_PreviousWorldPoint);
		}
		zoom();

		m_PreviousWorldPoint = worldPoint;
	}

	private void translate(Vector3f worldPoint, Vector3f prevWorldPoint) {
		// Vector3f translation =
		// new Vector3f(worldPoint.x - prevWorldPoint.x, 0, worldPoint.z - prevWorldPoint.z);
		// translation.mul(-1);

		float horizontal = -MousePicker.deltaPosition().x * Time.delta();
		float vertical = MousePicker.deltaPosition().y * Time.delta();
		Vector3f translationRight = m_Transform.right();
		translationRight = translationRight.mul(1, 0, 1, new Vector3f()).normalize();
		translationRight = translationRight.mul(horizontal, 0, horizontal, new Vector3f());
		Vector3f translationForward = m_Transform.forward();
		translationForward = translationForward.mul(1, 0, 1, new Vector3f()).normalize();
		translationForward = translationForward.mul(vertical, 0, vertical, new Vector3f());

		Vector3f translation = translationRight.add(translationForward).mul(TRANSLATE_SPEED);

		if (translation.length() > 0) {
			m_Transform.translate(translation);
			m_CenterPoint = calcCenterPoint();
		}
	}

	private void rotate(Vector3f worldPoint, Vector3f prevWorldPoint) {

		float angleChange = MousePicker.deltaPosition().x;
		m_Transform.rotate(new Vector3f(0, angleChange, 0));

		float distFromCenter = m_Transform.position() // .mul(new Vector3f(1, 0, 1), new Vector3f())
				.distance(m_CenterPoint);
		float horizontalDistance =
				(float) (distFromCenter * Math.cos(MathTools.radians(m_Transform.rotation().x)));

		float val = MathTools.radians(MathTools.clamp(180 - m_Transform.rotation().y, -180, 180));
		float dx = (float) (horizontalDistance * Math.sin(val));
		float dz = (float) (horizontalDistance * Math.cos(val));


		m_Transform.position(
				new Vector3f(m_CenterPoint.x - dx, m_Transform.position().y, m_CenterPoint.z - dz));

	}

	private void zoom() {
		float scroll = -MousePicker.scroll().y;

		if (scroll > 0 && m_ZoomFactor < 1 || scroll < 0 && m_ZoomFactor > 0) {
			zoom(MathTools.clamp(m_ZoomFactor + scroll / 25f, 0, 1));
		}
	}

	public void zoom(float factor) {
		m_ZoomFactor = factor;
		if (m_Transform != null) {
			float rotationX = m_ZoomEase.ease(MIN_ROTATE_X, MAX_ROTATE_X, m_ZoomFactor);
			Vector3f rotation = m_Transform.rotation();
			m_Transform.rotation(new Vector3f(rotationX, rotation.y, rotation.z));

			float height = m_ZoomEase.ease(MIN_HEIGHT, MAX_HEIGHT, m_ZoomFactor);
			Vector3f position = m_Transform.position();
			m_Transform.position(new Vector3f(position.x, height, position.z));

			m_CenterPoint = calcCenterPoint();
		}
	}

	private Vector3f calcCenterPoint() {
		return MathTools.findPointAtY(0, m_Transform.position(), m_Transform.forward());
	}

	public Camera camera() {
		return m_Cam;
	}

	public void reset() {
		m_Transform.position(new Vector3f(0, 0, -10));
		m_Transform.rotation(new Vector3f(0, 180, 0));
		zoom(.7f);

	}

	public String toString() {
		return string(0);
	}

	@Override
	public String string(int indentAmt) {
		return StringTools.buildString(StringTools.indent(indentAmt), "CameraController");
	}

	public CitySkyController controlling(boolean controlling) {
		m_Controlling = controlling;
		// MousePicker.cursor(!controlling);
		return this;
	}

}
