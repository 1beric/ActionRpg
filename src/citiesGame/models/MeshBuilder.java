package citiesGame.models;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import engine.models.ModelData;
import engine.models.RawModel;
import engine.util.Loader;

public class MeshBuilder {

    List<Float> m_Vertices;
    List<Float> m_TextureCoords;
    List<Float> m_Normals;
    List<Integer> m_Indices;

    public MeshBuilder() {
        m_Vertices = new ArrayList<>();
        m_TextureCoords = new ArrayList<>();
        m_Normals = new ArrayList<>();
        m_Indices = new ArrayList<>();
    }

    public void addRect(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4) {
        addTri(v1, v2, v3);
        addTri(v3, v4, v1);
    }

    public void addTri(Vector3f v1, Vector3f v2, Vector3f v3) {

        m_Vertices.add(v1.x);
        m_Vertices.add(v1.y);
        m_Vertices.add(v1.z);
        m_Vertices.add(v2.x);
        m_Vertices.add(v2.y);
        m_Vertices.add(v2.z);
        m_Vertices.add(v3.x);
        m_Vertices.add(v3.y);
        m_Vertices.add(v3.z);

        m_TextureCoords.add(0f);
        m_TextureCoords.add(0f);
        m_TextureCoords.add(1f);
        m_TextureCoords.add(1f);
        m_TextureCoords.add(1f);
        m_TextureCoords.add(0f);

        Vector3f oneTwo = v2.sub(v1, new Vector3f());
        Vector3f oneThree = v3.sub(v1, new Vector3f());
        Vector3f normal = oneTwo.cross(oneThree, new Vector3f()).mul(-1);
        m_Normals.add(normal.x);
        m_Normals.add(normal.y);
        m_Normals.add(normal.z);
        m_Normals.add(normal.x);
        m_Normals.add(normal.y);
        m_Normals.add(normal.z);
        m_Normals.add(normal.x);
        m_Normals.add(normal.y);
        m_Normals.add(normal.z);

        m_Indices.add(m_Indices.size());
        m_Indices.add(m_Indices.size());
        m_Indices.add(m_Indices.size());

    }

    public RawModel build() {

        float[] verticesArray = new float[m_Vertices.size()];
        float[] textureCoordsArray = new float[m_TextureCoords.size()];
        float[] normalsArray = new float[m_Normals.size()];
        int[] indicesArray = new int[m_Indices.size()];

        for (int i = 0; i < m_Vertices.size(); i++) {
            verticesArray[i] = m_Vertices.get(i);
        }

        for (int i = 0; i < m_TextureCoords.size(); i++) {
            textureCoordsArray[i] = m_TextureCoords.get(i);
        }

        for (int i = 0; i < m_Normals.size(); i++) {
            normalsArray[i] = m_Normals.get(i);
        }

        for (int i = 0; i < m_Indices.size(); i++) {
            indicesArray[i] = m_Indices.get(i);
        }

        return Loader.loadToVAO(
                new ModelData(verticesArray, textureCoordsArray, normalsArray, indicesArray));
    }


}
