package citiesGame.cityComponents;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import citiesGame.controllers.RoadController;
import citiesGame.models.RoadBuilder;
import citiesGame.util.Direction;
import citiesGame.util.Writable;
import engine.components.renderable.Mesh3;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.models.RawModel;
import engine.util.Color;

public class Road extends UpdateableComponent implements Writable {

    private static final int LEVEL_OF_DETAIL = 1;
    private static final float CURB_WIDTH = 0.3f;
    private static final float CURB_HEIGHT = 0.2f;

    private float m_X;
    private float m_Y;
    private float m_Z;
    private float m_Size;

    private List<WalkablePoint> m_WalkablePoints;
    private RoadController m_RoadsController;


    public Road(float x, float y, float z, float size, RoadController controller) {
        m_X = x;
        m_Y = y;
        m_Z = z;
        m_Size = size;
        m_WalkablePoints = new ArrayList<>();
        m_RoadsController = controller;
    }

    @Override
    public void init() {
        entity().transform().position(new Vector3f(m_X, m_Y, m_Z));
        entity().transform().scale(new Vector3f(m_Size / 2f, m_Size / 2f, m_Size / 2f));
        RawModel model = buildMesh();
        Mesh3 mesh = new Mesh3(model);
        Color color = Color.parse("palette$background$1light");
        mesh.uniform("color", color);
        mesh.uniform("reflectivity", 0.2f);
        mesh.uniform("shineDamper", 1.5f);
        entity().addComponent(mesh);

        updateWalkablePoints();

    }

    public void updateMesh() {
        entity().<Mesh3>component(Mesh3.class).model(buildMesh());
        updateWalkablePoints();
    }

    private void rotation(float deg) {
        transform().rotation(new Vector3f(0, deg, 0));
    }

    private RawModel buildMesh() {
        boolean north = m_RoadsController.road(m_X, m_Z, Direction.NORTH) != null;
        boolean east = m_RoadsController.road(m_X, m_Z, Direction.EAST) != null;
        boolean south = m_RoadsController.road(m_X, m_Z, Direction.SOUTH) != null;
        boolean west = m_RoadsController.road(m_X, m_Z, Direction.WEST) != null;

        if (north && east && south && west) {
            // FOUR WAY
            rotation(0);
            return RoadBuilder.buildFourWay(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && east && south && west) {
            // T INTERSECTION -NORTH
            rotation(0);
            return RoadBuilder.buildTIntersection(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (north && !east && south && west) {
            // T INTERSECTION -EAST
            // ROTATE 90
            rotation(90);
            return RoadBuilder.buildTIntersection(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (north && east && !south && west) {
            // T INTERSECTION -SOUTH
            // ROTATE 180
            rotation(180);
            return RoadBuilder.buildTIntersection(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (north && east && south && !west) {
            // T INTERSECTION -WEST
            // ROTATE 270
            rotation(270);
            return RoadBuilder.buildTIntersection(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (north && !east && south && !west) {
            // STRAIGHT NORTH SOUTH
            rotation(0);
            return RoadBuilder.buildStraight(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && east && !south && west) {
            // STRAIGHT EAST WEST
            // ROTATE 90
            rotation(90);
            return RoadBuilder.buildStraight(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (north && east && !south && !west) {
            // CORNER NORTH EAST
            rotation(0);
            return RoadBuilder.buildCorner(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && east && south && !west) {
            // CORNER EAST SOUTH
            // ROTATE 90
            rotation(90);
            return RoadBuilder.buildCorner(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && !east && south && west) {
            // CORNER SOUTH WEST
            // ROTATE 180
            rotation(180);
            return RoadBuilder.buildCorner(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (north && !east && !south && west) {
            // CORNER WEST NORTH
            // ROTATE 270
            rotation(270);
            return RoadBuilder.buildCorner(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (north && !east && !south && !west) {
            // DEAD END NORTH
            rotation(0);
            return RoadBuilder.buildDeadEnd(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && east && !south && !west) {
            // DEAD END EAST
            // ROTATE 90
            rotation(90);
            return RoadBuilder.buildDeadEnd(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && !east && south && !west) {
            // DEAD END SOUTH
            // ROTATE 180
            rotation(180);
            return RoadBuilder.buildDeadEnd(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && !east && !south && west) {
            // DEAD END WEST
            // ROTATE 270
            rotation(270);
            return RoadBuilder.buildDeadEnd(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        if (!north && !east && !south && !west) {
            // SINGLE
            rotation(0);
            return RoadBuilder.buildSingle(LEVEL_OF_DETAIL, CURB_WIDTH, CURB_HEIGHT);
        }

        return null;
    }

    public void updateWalkablePoints() {
        for (WalkablePoint walkablePoint : m_WalkablePoints) {
            entity().layer().removeEntity(walkablePoint.entity());
        }
        m_WalkablePoints.clear();

        float northPos = m_Z + m_Size / 2f - CURB_WIDTH * m_Size / 4f;
        float eastPos = m_X + m_Size / 2f - CURB_WIDTH * m_Size / 4f;
        float southPos = m_Z - m_Size / 2f + CURB_WIDTH * m_Size / 4f;
        float westPos = m_X - m_Size / 2f + CURB_WIDTH * m_Size / 4f;
        float curbFloor = m_Y + CURB_HEIGHT * m_Size / 2f;

        // center
        addWalkablePoint(m_X, m_Y + .1f, m_Z);

        // corners
        addWalkablePoint(eastPos, curbFloor, northPos);
        addWalkablePoint(westPos, curbFloor, northPos);
        addWalkablePoint(westPos, curbFloor, southPos);
        addWalkablePoint(eastPos, curbFloor, southPos);


        if (hasNorthCurb()) {
            addWalkablePoint(m_X, curbFloor, northPos);
        }
        if (hasEastCurb()) {
            addWalkablePoint(eastPos, curbFloor, m_Z);
        }
        if (hasSouthCurb()) {
            addWalkablePoint(m_X, curbFloor, southPos);
        }
        if (hasWestCurb()) {
            addWalkablePoint(westPos, curbFloor, m_Z);
        }

    }

    private void addWalkablePoint(float x, float y, float z) {
        WalkablePoint centerWalkPoint = new WalkablePoint(x, y, z);
        m_WalkablePoints.add(centerWalkPoint);
        entity().layer().addEntity(
                new Entity("WalkablePoint " + entity().name()).addComponent(centerWalkPoint));

    }

    public boolean hasNorthCurb() {
        return m_RoadsController.road(m_X, m_Z, Direction.NORTH) == null;
    }

    public boolean hasEastCurb() {
        return m_RoadsController.road(m_X, m_Z, Direction.EAST) == null;
    }

    public boolean hasSouthCurb() {
        return m_RoadsController.road(m_X, m_Z, Direction.SOUTH) == null;
    }

    public boolean hasWestCurb() {
        return m_RoadsController.road(m_X, m_Z, Direction.WEST) == null;
    }

    public float x() {
        return m_X;
    }


    public float z() {
        return m_Z;
    }

    @Override
    public void write(FileOutputStream fos) throws IOException {
        fos.write("Road {\n".getBytes());
        fos.write(("position:" + m_X + "," + m_Y + "," + m_Z).getBytes());
        fos.write('\n');
        fos.write(("size:" + m_Size).getBytes());
        fos.write("\n}\n".getBytes());
    }

}
