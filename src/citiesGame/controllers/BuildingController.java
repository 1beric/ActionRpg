package citiesGame.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.joml.Vector2f;
import citiesGame.cityComponents.Building;
import citiesGame.cityComponents.Road;
import citiesGame.util.Direction;
import citiesGame.util.Readable;
import citiesGame.util.Writable;
import engine.Layer;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.util.MathTools;
import engine.util.string.StringTools;

public class BuildingController extends UpdateableComponent implements Writable, Readable {

    private static final int BUILDING_PASSES = 10;


    private RoadController m_RoadController;


    private Map<Float, Map<Float, Building>> m_Buildings;
    private int m_NumBuildings;

    private int m_XWidth;
    private float m_Y;
    private int m_ZWidth;
    private float m_Size;

    public BuildingController(int xWidth, float y, int zWidth, float size,
            RoadController roadController) {
        m_XWidth = xWidth;
        m_Y = y;
        m_ZWidth = zWidth;
        m_Size = size;
        m_Buildings = new HashMap<>();
        m_NumBuildings = 0;
        m_RoadController = roadController;
    }

    @Override
    public void init() {
        initRandomBuildings();
    }

    private void initRandomBuildings() {
        for (int i = 0; i < BUILDING_PASSES; i++) {
            Road road = m_RoadController.randomRoad();
            float randDirection = MathTools.rFloat(0, 4);
            Direction direction = Direction.WEST;
            float rotation = 270;
            if (randDirection < 1) {
                // north
                direction = Direction.NORTH;
                rotation = 0;
            } else if (randDirection < 2) {
                // east
                direction = Direction.EAST;
                rotation = 90;
            } else if (randDirection < 3) {
                // south
                direction = Direction.SOUTH;
                rotation = 180;
            }

            Vector2f pos = m_RoadController.directionValue(road.x(), road.z(), direction);
            initBuilding(pos.x, pos.y, rotation);
        }
    }

    private void initBuilding(float x, float z, float rotation) {
        if (!m_Buildings.containsKey(x)) {
            m_Buildings.put(x, new HashMap<>());
        }
        Map<Float, Building> xMap = m_Buildings.get(x);
        if (!xMap.containsKey(z) && m_RoadController.road(x, z) == null) {
            Layer gameLayer = entity().layer();
            String entityName = StringTools.buildString("Building (", x, ",", z, ")");
            Entity entity = new Entity(entityName);
            Building building = new Building(x, m_Y, z, rotation, m_Size, m_RoadController);
            entity.addComponent(building);
            gameLayer.addEntity(entity);
            xMap.put(z, building);
            m_NumBuildings++;
        }
    }

    public void addBuilding(float x, float z) {
        initBuilding(x, z, 0);
    }

    public void removeBuilding(Building building) {
        m_Buildings.get(building.x()).remove(building.z());
        building.entity().layer().removeEntity(building.entity());
    }

    public Building building(float x, float z) {
        if (m_Buildings.containsKey(x)) {
            Map<Float, Building> xMap = m_Buildings.get(x);
            if (xMap.containsKey(z)) {
                return xMap.get(z);
            }
        }
        return null;
    }

    public void handleClick(int button, float x, float z) {
        Building building = building(x, z);
        if (building != null && button == 1) {
            removeBuilding(building);
        } else if (building != null && button == 0) {
            building.rotate();
        } else if (building == null) {
            addBuilding(x, z);
        }
    }

    @Override
    public void write(FileOutputStream fos) throws IOException {

        fos.write("Buildings {\n".getBytes());

        for (Float x : m_Buildings.keySet()) {
            for (Float z : m_Buildings.get(x).keySet()) {
                building(x, z).write(fos);
                fos.write(",\n".getBytes());
            }
        }

        fos.write("}\n".getBytes());

    }

    @Override
    public void read(Scanner scanner) {
        String buildingsLine = scanner.nextLine();
        String next = scanner.nextLine();
        while (next.equals(",")) {

        }
    }

}
