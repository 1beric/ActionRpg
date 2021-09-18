package citiesGame.cityComponents;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import citiesGame.util.Writable;
import engine.components.renderable.Mesh3;
import engine.components.updateable.UpdateableComponent;
import engine.models.RawModel;
import engine.models.creation.PlaneBuilder;
import engine.util.Color;

public class Floor extends UpdateableComponent implements Writable {

    private float m_X;
    private float m_Y;
    private float m_Z;
    private float m_Size;

    private List<WalkablePoint> m_WalkablePoints;

    public Floor(float x, float y, float z, float size) {
        m_X = x;
        m_Y = y;
        m_Z = z;
        m_Size = size;
        m_WalkablePoints = new ArrayList<>();
    }

    @Override
    public void init() {
        transform().position(new Vector3f(m_X, m_Y, m_Z));
        transform().scale(new Vector3f(m_Size / 2f, 1, m_Size / 2f));
        RawModel model = PlaneBuilder.build(false);
        Mesh3 mesh = new Mesh3(model);
        Color color = Color.parse("#224a07");
        mesh.uniform("color", color);
        mesh.uniform("reflectivity", 0.3f);
        mesh.uniform("shineDamper", 5f);
        entity().addComponent(mesh);
    }

    public float x() {
        return m_X;
    }

    public float z() {
        return m_Z;
    }

    @Override
    public void write(FileOutputStream fos) throws IOException {
        fos.write("Floor {\n".getBytes());
        fos.write(("position:" + m_X + "," + m_Y + "," + m_Z).getBytes());
        fos.write('\n');
        fos.write(("size:" + m_Size).getBytes());
        fos.write("\n}\n".getBytes());
    }

}
