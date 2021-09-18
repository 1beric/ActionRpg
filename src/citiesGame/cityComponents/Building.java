package citiesGame.cityComponents;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import citiesGame.controllers.RoadController;
import citiesGame.util.Writable;
import engine.components.renderable.Mesh3;
import engine.components.updateable.UpdateableComponent;
import engine.models.RawModel;
import engine.models.creation.OBJFileLoader;
import engine.util.Color;

public class Building extends UpdateableComponent implements Writable {

    private float m_X;
    private float m_Y;
    private float m_Z;
    private float m_Rotation;
    private float m_Size;

    private List<WalkablePoint> m_WalkablePoints;
    private RoadController m_RoadsController;


    public Building(float x, float y, float z, float rotation, float size,
            RoadController controller) {
        m_X = x;
        m_Y = y;
        m_Z = z;
        m_Rotation = rotation;
        m_Size = size;
        m_WalkablePoints = new ArrayList<>();
        m_RoadsController = controller;
    }

    @Override
    public void init() {

        entity().transform().position(new Vector3f(m_X, m_Y, m_Z));
        entity().transform().rotation(new Vector3f(0, m_Rotation, 0));
        entity().transform().scale(new Vector3f(m_Size / 2f, m_Size / 2f, m_Size / 2f));
        RawModel model = OBJFileLoader.loadOBJ("res/models/buildings/building_1.obj");
        Mesh3 mesh = new Mesh3(model);
        Color color = Color.parse("palette$info$2main");
        mesh.uniform("color", color);
        mesh.uniform("reflectivity", 0.3f);
        mesh.uniform("shineDamper", 5f);
        entity().addComponent(mesh);
    }

    private void rotation(float deg) {
        transform().rotation(new Vector3f(0, deg, 0));
    }

    public void rotate() {
        rotation(transform().rotation().y + 90);
    }


    public float x() {
        return m_X;
    }


    public float z() {
        return m_Z;
    }

    @Override
    public void write(FileOutputStream fos) throws IOException {
        fos.write("Building {\n".getBytes());
        fos.write(("position:" + m_X + "," + m_Y + "," + m_Z).getBytes());
        fos.write('\n');
        fos.write(("size:" + m_Size).getBytes());
        fos.write('\n');
        fos.write(("rotation:" + m_Rotation).getBytes());
        fos.write("\n}\n".getBytes());
    }


}
