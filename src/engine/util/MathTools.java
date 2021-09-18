package engine.util;

import java.util.Random;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import engine.components.renderable.Camera;
import engine.rendering.ModelRenderer;

public class MathTools {

	public static Random s_Random = new Random();

	public static Vector2f clamp(Vector2f v, Vector2f l, Vector2f u) {
		return new Vector2f(clamp(v.x, u.x, l.x), clamp(v.y, u.y, l.y));
	}

	public static float clamp(float v, float l, float u) {
		return Math.max(Math.min(v, u), l);
	}

	public static int rInt(int bound) {
		return s_Random.nextInt(bound) - bound / 2;
	}

	public static float rFloat() {
		return s_Random.nextFloat();
	}

	public static float rFloat(float a, float b) {
		return a + s_Random.nextFloat() * (b - a);
	}

	public static float rFloat(Vector2f bounds) {
		return rFloat(bounds.x, bounds.y);
	}

	public static Vector3f randomVec3() {
		return new Vector3f(rFloat(), rFloat(), rFloat());
	}

	public static Vector3f randomVec3(float a, float b) {
		return new Vector3f(rFloat(a, b), rFloat(a, b), rFloat(a, b));
	}

	public static Vector3f randomVec3(float xa, float xb, float ya, float yb, float za, float zb) {
		return new Vector3f(rFloat(xa, xb), rFloat(ya, yb), rFloat(za, zb));
	}

	public static Vector3f randomVec3(Vector2f bounds) {
		return randomVec3(bounds.x, bounds.y);
	}

	public static Vector3f randomVec3(Vector2f x, Vector2f y, Vector2f z) {
		return randomVec3(x.x, x.y, y.x, y.y, z.x, z.y);
	}

	public static Vector3f randomVec3(Vector3f lb, Vector3f ub) {
		return randomVec3(lb.x, ub.x, lb.y, ub.y, lb.z, ub.z);
	}

	public static Vector2f randomVec2() {
		return new Vector2f(rFloat(), rFloat());
	}

	public static Vector2f randomVec2(float a, float b) {
		return new Vector2f(rFloat(a, b), rFloat(a, b));
	}

	public static Vector2f randomVec2(float xa, float xb, float ya, float yb) {
		return new Vector2f(rFloat(xa, xb), rFloat(ya, yb));
	}

	public static Vector2f randomVec2(Vector2f x, Vector2f y) {
		return randomVec2(x.x, x.y, y.x, y.y);
	}

	public static Vector2f randomVec2bounds(Vector2f lb, Vector2f ub) {
		return randomVec2(lb.x, ub.x, lb.y, ub.y);
	}

	public static String randomFilename() {
		String out = "";
		for (int i = 0; i < 12; i++) {
			out += (char) rFloat(65, 90);
		}
		return out;
	}

	/**
	 * @return the projectionMatrix
	 */
	public static Matrix4f getProjectionMatrix(float aspectRatio) {
		return new Matrix4f().identity().perspective(ModelRenderer.FOV, aspectRatio,
				ModelRenderer.PERSPECTIVE_NEAR, ModelRenderer.PERSPECTIVE_FAR);
	}

	public static Vector2f getNDC(float mouseX, float mouseY, Vector2f resolution) {
		float x = (2f * mouseX) / resolution.x - 1;
		float y = (2f * mouseY) / resolution.y - 1;
		return new Vector2f(x, y);
	}

	public static Vector3f calculateRay(Vector2f screenRay, Camera camera) {
		Vector4f clipCoords = new Vector4f(screenRay.x, screenRay.y, -1f, 1f);
		Vector4f eyeCoords = getESC(clipCoords);
		Vector3f worldRay = getWorld(eyeCoords, camera);
		return worldRay;
	}

	public static Vector4f getESC(Vector4f clipCoords) {
		// 1 IS HARDCODED!! MAKE IT THE ASPECT RATIO
		Matrix4f invertedProjection = getProjectionMatrix(1).invert();
		Vector4f eyeCoords = multiply(invertedProjection, clipCoords);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	public static Vector3f getWorld(Vector4f eyeCoords, Camera camera) {
		Matrix4f invertedView = camera.viewMatrix().invert();
		Vector4f worldCoords = multiply(invertedView, eyeCoords);
		Vector3f ray = new Vector3f(worldCoords.x, worldCoords.y, worldCoords.z);
		ray.normalize();
		return ray;
	}

	/**
	 * Multiplies this matrix to a vector.
	 *
	 * @param vector The vector
	 *
	 * @return Vector product of this * other
	 */
	public static Vector4f multiply(Matrix4f matrix, Vector4f vector) {
		float x = matrix.m00() * vector.x + matrix.m01() * vector.y + matrix.m02() * vector.z
				+ matrix.m03() * vector.w;
		float y = matrix.m10() * vector.x + matrix.m11() * vector.y + matrix.m12() * vector.z
				+ matrix.m13() * vector.w;
		float z = matrix.m20() * vector.x + matrix.m21() * vector.y + matrix.m22() * vector.z
				+ matrix.m23() * vector.w;
		float w = matrix.m30() * vector.x + matrix.m31() * vector.y + matrix.m32() * vector.z
				+ matrix.m33() * vector.w;
		return new Vector4f(x, y, z, w);
	}

	/**
	 * Creates a rotation matrix. Similar to <code>glRotate(angle, x, y, z)</code>.
	 *
	 * @param angle Angle of rotation in degrees
	 * @param x x coordinate of the rotation vector
	 * @param y y coordinate of the rotation vector
	 * @param z z coordinate of the rotation vector
	 *
	 * @return Rotation matrix
	 */
	public static Matrix4f rotate(float angle, float x, float y, float z) {
		Matrix4f rotation = new Matrix4f();

		float c = (float) Math.cos(Math.toRadians(angle));
		float s = (float) Math.sin(Math.toRadians(angle));
		Vector3f vec = new Vector3f(x, y, z);
		if (vec.lengthSquared() != 1f) {
			vec = vec.normalize();
			x = vec.x;
			y = vec.y;
			z = vec.z;
		}

		rotation.m00(x * x * (1f - c) + c);
		rotation.m10(y * x * (1f - c) + z * s);
		rotation.m20(x * z * (1f - c) - y * s);
		rotation.m01(x * y * (1f - c) - z * s);
		rotation.m11(y * y * (1f - c) + c);
		rotation.m21(y * z * (1f - c) + x * s);
		rotation.m02(x * z * (1f - c) + y * s);
		rotation.m12(y * z * (1f - c) - x * s);
		rotation.m22(z * z * (1f - c) + c);

		return rotation;
	}

	public static float radians(float deg) {
		return deg * (float) Math.PI / 180f;
	}

	public static float degrees(float radians) {
		return radians * 180f / (float) Math.PI;
	}

	/**
	 * Creates a translation matrix. Similar to <code>glTranslate(x, y, z)</code>.
	 *
	 * @param translateBy the BerylVector to translate by.
	 *
	 * @return Translation matrix
	 */
	public static Matrix4f translate(Vector3f translateBy) {
		Matrix4f translation = new Matrix4f();

		translation.m03(translateBy.x);
		translation.m13(translateBy.y);
		translation.m23(translateBy.z);

		return translation;
	}

	public static Vector3f findPointAtY(float y, Vector3f origin, Vector3f ray) {
		float t = (y - origin.y) / ray.y;
		return new Vector3f(origin.x + t * ray.x, y, origin.z + t * ray.z);
	}

}
