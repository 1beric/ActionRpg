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

public class LeaderController extends UpdateableComponent {

	private static final float TRANSLATE_SPEED = 4f;
	private static final float MIN_ROTATE_X = -90;
	private static final float MAX_ROTATE_X = 90;
	private static final int FORWARD_KEY = GLFW.GLFW_KEY_W;
	private static final int LEFT_KEY = GLFW.GLFW_KEY_A;
	private static final int BACK_KEY = GLFW.GLFW_KEY_S;
	private static final int RIGHT_KEY = GLFW.GLFW_KEY_D;

	private Camera m_Cam;
	private boolean m_Controlling;


	public LeaderController() {}

	@Override
	public void init() {

		m_Cam = entity().<Camera>component(Camera.class);
		if (m_Cam == null)
			entity().addComponent(m_Cam = new Camera());

	}

	@Override
	public void update() {
		if (!m_Controlling)
			return;

		Vector2f deltaPosition = MousePicker.deltaPosition();

		translate();
		rotate(deltaPosition.x, deltaPosition.y);
	}

	private void translate() {

	}

	private void rotate(float deltaX, float deltaY) {


	}


	public Camera camera() {
		return m_Cam;
	}

	public void reset() {}

	public String toString() {
		return string(0);
	}

	@Override
	public String string(int indentAmt) {
		return StringTools.buildString(StringTools.indent(indentAmt), "LeaderController");
	}

	public LeaderController controlling(boolean controlling) {
		m_Controlling = controlling;
		MousePicker.cursor(!controlling);
		return this;
	}

}
