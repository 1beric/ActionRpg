package citiesGame.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import org.joml.Vector2f;
import citiesGame.cityComponents.Floor;
import citiesGame.cityComponents.Road;
import citiesGame.util.Direction;
import citiesGame.util.Readable;
import citiesGame.util.Writable;
import engine.Layer;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.util.MathTools;
import engine.util.lambdas.Notify;
import engine.util.string.StringTools;

public class RoadController extends UpdateableComponent implements Writable, Readable {


    private static final int MIN_ROADS = 20;

    private static final float NORTH_CHANCE = 0.7f;
    private static final float EAST_CHANCE = 0.5f;
    private static final float SOUTH_CHANCE = 0.7f;
    private static final float WEST_CHANCE = 0.5f;

    private Notify m_FinishInit;

    private Map<Float, Map<Float, Road>> m_Roads;
    private Map<Float, Map<Float, Floor>> m_Floors;
    private int m_NumRoads;

    private int m_XWidth;
    private float m_Y;
    private int m_ZWidth;
    private float m_Size;



    public RoadController(int xWidth, float y, int zWidth, float size, Notify finishInit) {
        m_XWidth = xWidth;
        m_Y = y;
        m_ZWidth = zWidth;
        m_Size = size;
        m_Roads = new HashMap<>();
        m_Floors = new HashMap<>();
        m_NumRoads = 0;
        m_FinishInit = finishInit;
    }


    @Override
    public void init() {
        initRandomRoads();
        initRandomFloors();
        m_FinishInit.handle();
    }

    private void initRandomFloors() {
        for (int x = 0; x < m_XWidth; x++) {
            for (int z = 0; z < m_ZWidth; z++) {
                initFloor((x - m_XWidth / 2.0f) * m_Size, (z - m_ZWidth / 2.0f) * m_Size);
            }
        }
    }

    private void initFloor(float x, float z) {
        if (!m_Floors.containsKey(x)) {
            m_Floors.put(x, new HashMap<>());
        }
        Map<Float, Floor> xMap = m_Floors.get(x);
        if (!xMap.containsKey(z) && road(x, z) == null) {
            Layer gameLayer = entity().layer();
            String entityName = StringTools.buildString("Floor (", x, ",", z, ")");
            Entity entity = new Entity(entityName);
            Floor floor = new Floor(x, m_Y, z, m_Size);
            entity.addComponent(floor);
            gameLayer.addEntity(entity);
            xMap.put(z, floor);
        }
    }

    public void addFloor(float x, float z) {
        initFloor(x, z);
    }

    public void removeFloor(Floor floor) {
        m_Floors.get(floor.x()).remove(floor.z());
        floor.entity().layer().removeEntity(floor.entity());
    }

    private void initRandomRoads() {
        int passes = 0;
        while (m_NumRoads < MIN_ROADS && passes < 10) {

            Queue<Vector2f> queue = new LinkedList<>();

            initRoad(0, 0);
            queue.add(new Vector2f(0, 0));
            int i = 0;
            while (!queue.isEmpty() && i < 100) {
                Vector2f next = queue.poll();
                spreadRoads(next.x, next.y, queue);
                i++;
            }
            passes++;
        }

    }

    private void initRoad(float x, float z) {
        if (!m_Roads.containsKey(x)) {
            m_Roads.put(x, new HashMap<>());
        }
        Map<Float, Road> xMap = m_Roads.get(x);
        if (!xMap.containsKey(z)) {
            Layer gameLayer = entity().layer();
            String entityName = StringTools.buildString("Road (", x, ",", z, ")");
            Entity entity = new Entity(entityName);
            Road road = new Road(x, m_Y, z, m_Size, this);
            entity.addComponent(road);
            gameLayer.addEntity(entity);
            xMap.put(z, road);
            m_NumRoads++;
        }
    }

    public void addRoad(float x, float z) {
        // add road
        initRoad(x, z);

        // recalc styles
        Road road = road(x, z, Direction.NORTH);
        if (road != null)
            road.updateMesh();
        road = road(x, z, Direction.EAST);
        if (road != null)
            road.updateMesh();
        road = road(x, z, Direction.SOUTH);
        if (road != null)
            road.updateMesh();
        road = road(x, z, Direction.WEST);
        if (road != null)
            road.updateMesh();
    }

    public void removeRoad(Road road) {
        // remove road
        m_Roads.get(road.x()).remove(road.z());
        road.entity().layer().removeEntity(road.entity());

        // recalc styles
        Road testRoad = road(road.x(), road.z(), Direction.NORTH);
        if (testRoad != null)
            testRoad.updateMesh();
        testRoad = road(road.x(), road.z(), Direction.EAST);
        if (testRoad != null)
            testRoad.updateMesh();
        testRoad = road(road.x(), road.z(), Direction.SOUTH);
        if (testRoad != null)
            testRoad.updateMesh();
        testRoad = road(road.x(), road.z(), Direction.WEST);
        if (testRoad != null)
            testRoad.updateMesh();
    }

    public void roadClicked(Road road) {
        removeRoad(road);
        addFloor(road.x(), road.z());
    }

    public void floorClicked(Floor floor) {
        removeFloor(floor);
        addRoad(floor.x(), floor.z());
    }

    public void spreadRoads(float x, float z, Queue<Vector2f> queue) {
        float tempX;
        float tempZ;

        tempX = x + Direction.NORTH.x * m_Size;
        tempZ = z + Direction.NORTH.z * m_Size;
        if (MathTools.rFloat() < NORTH_CHANCE && inBounds(tempX, tempZ)
                && road(tempX, tempZ) == null && connections(tempX, tempZ).size() < 2) {
            initRoad(tempX, tempZ);
            queue.add(new Vector2f(tempX, tempZ));
        }

        tempX = x + Direction.EAST.x * m_Size;
        tempZ = z + Direction.EAST.z * m_Size;
        if (MathTools.rFloat() < EAST_CHANCE && inBounds(tempX, tempZ) && road(tempX, tempZ) == null
                && connections(tempX, tempZ).size() < 2) {
            initRoad(tempX, tempZ);
            queue.add(new Vector2f(tempX, tempZ));
        }

        tempX = x + Direction.SOUTH.x * m_Size;
        tempZ = z + Direction.SOUTH.z * m_Size;
        if (MathTools.rFloat() < SOUTH_CHANCE && inBounds(tempX, tempZ)
                && road(tempX, tempZ) == null && connections(tempX, tempZ).size() < 2) {
            initRoad(tempX, tempZ);
            queue.add(new Vector2f(tempX, tempZ));
        }

        tempX = x + Direction.WEST.x * m_Size;
        tempZ = z + Direction.WEST.z * m_Size;
        if (MathTools.rFloat() < WEST_CHANCE && inBounds(tempX, tempZ) && road(tempX, tempZ) == null
                && connections(tempX, tempZ).size() < 2) {
            initRoad(tempX, tempZ);
            queue.add(new Vector2f(tempX, tempZ));
        }

    }

    public List<Road> connections(Road road) {
        return connections(road.x(), road.z());
    }

    public List<Road> connections(float x, float z) {
        List<Road> out = new ArrayList<>();
        Road temp;

        temp = road(x, z, Direction.NORTH);
        if (temp != null)
            out.add(temp);

        temp = road(x, z, Direction.EAST);
        if (temp != null)
            out.add(temp);

        temp = road(x, z, Direction.SOUTH);
        if (temp != null)
            out.add(temp);

        temp = road(x, z, Direction.WEST);
        if (temp != null)
            out.add(temp);

        return out;
    }

    public Floor floor(float x, float z) {
        if (m_Floors.containsKey(x)) {
            Map<Float, Floor> xMap = m_Floors.get(x);
            if (xMap.containsKey(z)) {
                return xMap.get(z);
            }
        }
        return null;
    }

    public Road road(float x, float z) {
        if (m_Roads.containsKey(x)) {
            Map<Float, Road> xMap = m_Roads.get(x);
            if (xMap.containsKey(z)) {
                return xMap.get(z);
            }
        }
        return null;
    }

    public Road road(Road road, Direction d) {
        float x = road.x() + d.x * m_Size;
        float z = road.z() + d.z * m_Size;
        return road(x, z);
    }

    public Road road(float x, float z, Direction d) {
        x = x + d.x * m_Size;
        z = z + d.z * m_Size;
        return road(x, z);
    }

    public boolean inBounds(float x, float z) {
        return x > -m_XWidth * m_Size / 2f && x < m_XWidth * m_Size / 2f
                && z > -m_ZWidth * m_Size / 2f && z < m_ZWidth * m_Size / 2f;
    }

    public Road randomRoad() {
        List<Float> xs = new ArrayList<>(m_Roads.keySet());
        Collections.shuffle(xs, MathTools.s_Random);
        float x = xs.get(0);
        List<Float> ys = new ArrayList<>(m_Roads.get(x).keySet());
        Collections.shuffle(ys, MathTools.s_Random);
        float y = ys.get(0);
        return m_Roads.get(x).get(y);
    }

    public Vector2f directionValue(float x, float z, Direction d) {
        x = x + d.x * m_Size;
        z = z + d.z * m_Size;
        return new Vector2f(x, z);
    }

    public void handleClick(int button, float x, float z) {
        Road road = road(x, z);
        if (road != null) {
            roadClicked(road);
        } else {
            floorClicked(floor(x, z));
        }
    }

    @Override
    public void write(FileOutputStream fos) throws IOException {

        fos.write("Roads {\n".getBytes());

        for (Float x : m_Roads.keySet()) {
            for (Float z : m_Roads.get(x).keySet()) {
                road(x, z).write(fos);
            }
        }

        fos.write("}\nFloors {\n".getBytes());

        for (Float x : m_Floors.keySet()) {
            for (Float z : m_Floors.get(x).keySet()) {
                floor(x, z).write(fos);
            }
        }

        fos.write("}\n".getBytes());


    }


    @Override
    public void read(Scanner scanner) {

    }

}
