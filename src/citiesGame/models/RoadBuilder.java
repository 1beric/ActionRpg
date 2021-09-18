package citiesGame.models;

import org.joml.Vector3f;
import engine.models.RawModel;

public class RoadBuilder {

	public static RawModel buildFourWay(float levelOfDetail, float curbWidth, float curbHeight) {
		MeshBuilder builder = new MeshBuilder();
		float negativeOuter = -1;
		float negativeInner = negativeOuter + curbWidth;
		float positiveOuter = 1;
		float positiveInner = positiveOuter - curbWidth;

		// curb -x-z x edge
		builder.addRect(new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, 0, negativeOuter),
				new Vector3f(negativeInner, 0, negativeInner));

		// curb -x-z z edge
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeOuter, 0, negativeInner),
				new Vector3f(negativeOuter, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner));

		// curb -x-z top
		builder.addRect(new Vector3f(negativeOuter, curbHeight, negativeInner),
				new Vector3f(negativeOuter, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, negativeInner));

		// curb -x+z x edge
		builder.addRect(new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeInner, curbHeight, positiveInner),
				new Vector3f(negativeInner, 0, positiveInner),
				new Vector3f(negativeInner, 0, positiveOuter));

		// curb -x+z z edge
		builder.addRect(new Vector3f(negativeOuter, 0, positiveInner),
				new Vector3f(negativeInner, 0, positiveInner),
				new Vector3f(negativeInner, curbHeight, positiveInner),
				new Vector3f(negativeOuter, curbHeight, positiveInner));

		// curb -x+z top
		builder.addRect(new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, positiveInner),
				new Vector3f(negativeInner, curbHeight, positiveInner),
				new Vector3f(negativeInner, curbHeight, positiveOuter));

		// inner
		builder.addRect(new Vector3f(negativeOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter));

		// curb +x-z x edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, negativeOuter),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(positiveInner, 0, negativeOuter));

		// curb +x-z z edge
		builder.addRect(new Vector3f(positiveOuter, 0, negativeInner),
				new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveOuter, curbHeight, negativeInner));

		// curb +x-z top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeOuter));

		// curb +x+z x edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, positiveInner),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, 0, positiveOuter),
				new Vector3f(positiveInner, 0, positiveInner));

		// curb +x+z z edge
		builder.addRect(new Vector3f(positiveInner, 0, positiveInner),
				new Vector3f(positiveOuter, 0, positiveInner),
				new Vector3f(positiveOuter, curbHeight, positiveInner),
				new Vector3f(positiveInner, curbHeight, positiveInner));

		// curb +x+z top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, positiveInner),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, positiveInner));


		// convert lists to arrays
		return builder.build();
	}

	public static RawModel buildTIntersection(float levelOfDetail, float curbWidth,
			float curbHeight) {
		MeshBuilder builder = new MeshBuilder();
		float negativeOuter = -1;
		float negativeInner = negativeOuter + curbWidth;
		float positiveOuter = 1;
		float positiveInner = positiveOuter - curbWidth;

		// curb -x-z x edge
		builder.addRect(new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, 0, negativeOuter),
				new Vector3f(negativeInner, 0, negativeInner));

		// curb -x-z z edge
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeOuter, 0, negativeInner),
				new Vector3f(negativeOuter, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner));

		// curb -x-z top
		builder.addRect(new Vector3f(negativeOuter, curbHeight, negativeInner),
				new Vector3f(negativeOuter, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, negativeInner));


		// curb +x-z x edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, negativeOuter),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(positiveInner, 0, negativeOuter));

		// curb +x-z z edge
		builder.addRect(new Vector3f(positiveOuter, 0, negativeInner),
				new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveOuter, curbHeight, negativeInner));

		// curb +x-z top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeOuter));

		// inner
		builder.addRect(new Vector3f(negativeOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter));

		// curb +z inner edge
		builder.addRect(new Vector3f(positiveOuter, curbHeight, positiveInner),
				new Vector3f(negativeOuter, curbHeight, positiveInner),
				new Vector3f(negativeOuter, 0, positiveInner),
				new Vector3f(positiveOuter, 0, positiveInner));

		// curb +z top
		builder.addRect(new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, positiveInner),
				new Vector3f(positiveOuter, curbHeight, positiveInner),
				new Vector3f(positiveOuter, curbHeight, positiveOuter));

		// curb +z outer edge
		builder.addRect(new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter));


		// convert lists to arrays
		return builder.build();
	}

	public static RawModel buildStraight(float levelOfDetail, float curbWidth, float curbHeight) {

		MeshBuilder builder = new MeshBuilder();
		float negativeOuter = -1;
		float negativeInner = negativeOuter + curbWidth;
		float positiveOuter = 1;
		float positiveInner = positiveOuter - curbWidth;

		// curb -x outer edge
		builder.addRect(new Vector3f(negativeOuter, curbHeight, negativeOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, negativeOuter));

		// curb -x top
		builder.addRect(new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, negativeOuter));

		// curb -x inner edge
		builder.addRect(new Vector3f(negativeInner, 0, negativeOuter),
				new Vector3f(negativeInner, 0, positiveOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeInner, curbHeight, negativeOuter));

		// inner
		builder.addRect(new Vector3f(negativeInner, 0, negativeOuter),
				new Vector3f(positiveInner, 0, negativeOuter),
				new Vector3f(positiveInner, 0, positiveOuter),
				new Vector3f(negativeInner, 0, positiveOuter));

		// curb +x inner edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, negativeOuter),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, 0, positiveOuter),
				new Vector3f(positiveInner, 0, negativeOuter));

		// curb +x top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, negativeOuter));

		// curb +x outer edge
		builder.addRect(new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, negativeOuter));

		// convert lists to arrays
		return builder.build();
	}

	public static RawModel buildCorner(float levelOfDetail, float curbWidth, float curbHeight) {
		MeshBuilder builder = new MeshBuilder();
		float negativeOuter = -1;
		float negativeInner = negativeOuter + curbWidth;
		float positiveOuter = 1;
		float positiveInner = positiveOuter - curbWidth;

		// curb -x outer edge
		builder.addRect(new Vector3f(negativeOuter, curbHeight, negativeOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, negativeOuter));

		// curb -x top
		builder.addRect(new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, negativeOuter));

		// curb -x inner edge
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeInner, 0, positiveOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeInner, curbHeight, negativeInner));

		// curb -z outer edge
		builder.addRect(new Vector3f(negativeOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(negativeOuter, curbHeight, negativeOuter));

		// curb -z top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeOuter));

		// curb -z inner edge
		builder.addRect(new Vector3f(positiveOuter, 0, negativeInner),
				new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(positiveOuter, curbHeight, negativeInner));

		// inner
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(positiveOuter, 0, negativeInner),
				new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(negativeInner, 0, positiveOuter));

		// curb +x inner edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, positiveInner),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, 0, positiveOuter),
				new Vector3f(positiveInner, 0, positiveInner));

		// curb +z inner edge
		builder.addRect(new Vector3f(positiveInner, 0, positiveInner),
				new Vector3f(positiveOuter, 0, positiveInner),
				new Vector3f(positiveOuter, curbHeight, positiveInner),
				new Vector3f(positiveInner, curbHeight, positiveInner));

		// curb +x +z top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, positiveInner),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, positiveInner));

		// convert lists to arrays
		return builder.build();
	}

	public static RawModel buildDeadEnd(float levelOfDetail, float curbWidth, float curbHeight) {
		MeshBuilder builder = new MeshBuilder();
		float negativeOuter = -1;
		float negativeInner = negativeOuter + curbWidth;
		float positiveOuter = 1;
		float positiveInner = positiveOuter - curbWidth;

		// curb -x outer edge
		builder.addRect(new Vector3f(negativeOuter, curbHeight, negativeOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, negativeOuter));

		// curb -x top
		builder.addRect(new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, negativeOuter));

		// curb -x inner edge
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeInner, 0, positiveOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeInner, curbHeight, negativeInner));

		// curb -z outer edge
		builder.addRect(new Vector3f(negativeOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(negativeOuter, curbHeight, negativeOuter));

		// curb -z top
		builder.addRect(new Vector3f(positiveInner, curbHeight, negativeOuter),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeOuter));

		// curb -z inner edge
		builder.addRect(new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeInner));

		// inner
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(positiveInner, 0, positiveOuter),
				new Vector3f(negativeInner, 0, positiveOuter));

		// curb +x inner edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, 0, positiveOuter),
				new Vector3f(positiveInner, 0, negativeInner));

		// curb +x top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, negativeOuter));

		// curb +x outer edge
		builder.addRect(new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, negativeOuter));

		// convert lists to arrays
		return builder.build();
	}

	public static RawModel buildSingle(float levelOfDetail, float curbWidth, float curbHeight) {
		MeshBuilder builder = new MeshBuilder();
		float negativeOuter = -1;
		float negativeInner = negativeOuter + curbWidth;
		float positiveOuter = 1;
		float positiveInner = positiveOuter - curbWidth;

		// curb -x outer edge
		builder.addRect(new Vector3f(negativeOuter, curbHeight, negativeOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, negativeOuter));

		// curb -x top
		builder.addRect(new Vector3f(negativeInner, curbHeight, negativeOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, negativeOuter));

		// curb -x inner edge
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeInner, 0, positiveInner),
				new Vector3f(negativeInner, curbHeight, positiveInner),
				new Vector3f(negativeInner, curbHeight, negativeInner));

		// curb -z outer edge
		builder.addRect(new Vector3f(negativeOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(negativeOuter, curbHeight, negativeOuter));

		// curb -z top
		builder.addRect(new Vector3f(positiveInner, curbHeight, negativeOuter),
				new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeOuter));

		// curb -z inner edge
		builder.addRect(new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(negativeInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, negativeInner));

		// inner
		builder.addRect(new Vector3f(negativeInner, 0, negativeInner),
				new Vector3f(positiveInner, 0, negativeInner),
				new Vector3f(positiveInner, 0, positiveInner),
				new Vector3f(negativeInner, 0, positiveInner));

		// curb +x inner edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, negativeInner),
				new Vector3f(positiveInner, curbHeight, positiveInner),
				new Vector3f(positiveInner, 0, positiveInner),
				new Vector3f(positiveInner, 0, negativeInner));

		// curb +x top
		builder.addRect(new Vector3f(positiveOuter, curbHeight, negativeOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(positiveInner, curbHeight, negativeOuter));

		// curb +x outer edge
		builder.addRect(new Vector3f(positiveOuter, 0, negativeOuter),
				new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, negativeOuter));

		// curb +z inner edge
		builder.addRect(new Vector3f(positiveInner, curbHeight, positiveInner),
				new Vector3f(negativeInner, curbHeight, positiveInner),
				new Vector3f(negativeInner, 0, positiveInner),
				new Vector3f(positiveInner, 0, positiveInner));

		// curb +z top
		builder.addRect(new Vector3f(negativeInner, curbHeight, positiveInner),
				new Vector3f(positiveInner, curbHeight, positiveInner),
				new Vector3f(positiveInner, curbHeight, positiveOuter),
				new Vector3f(negativeInner, curbHeight, positiveOuter));

		// curb +z outer edge
		builder.addRect(new Vector3f(positiveOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, 0, positiveOuter),
				new Vector3f(negativeOuter, curbHeight, positiveOuter),
				new Vector3f(positiveOuter, curbHeight, positiveOuter));

		// convert lists to arrays
		return builder.build();
	}
}
