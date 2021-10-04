package citiesGame.cityComponents;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import engine.components.renderable.Mesh3;
import engine.components.updateable.UpdateableComponent;
import engine.models.creation.CubeBuilder;

public class WalkablePoint extends UpdateableComponent {

    private List<WalkablePoint> m_Edges;

    private Vector3f m_DefaultPosition;

    public WalkablePoint(float x, float y, float z) {
        m_DefaultPosition = new Vector3f(x, y, z);
        m_Edges = new ArrayList<>();
    }

    @Override
    public void init() {
        transform().position(m_DefaultPosition);
        entity().addComponent(new Mesh3(CubeBuilder.build(false)));
        transform().scale(new Vector3f(.1f, .1f, .1f));
    }



    public void addEdge(WalkablePoint point) {
        m_Edges.add(point);
    }



}
