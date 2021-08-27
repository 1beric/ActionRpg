package citiesGame.gui.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.joml.Vector2f;
import engine.components.renderable.Mesh2;
import engine.models.Rect;
import engine.shaders.uniforms.Uniform;
import engine.util.Color;
import engine.util.Palette;

public class PalettesDisplay extends Mesh2 {

    private static int RECT_SIZE = 100;

    private List<Rect> m_Rects;

    private Vector2f m_HorizontalBounds;
    private Vector2f m_VerticalBounds;

    private float m_Width;
    private float m_Height;

    public PalettesDisplay(float x, float y) {
        m_Rects = new ArrayList<>();
        m_Width = RECT_SIZE * Palette.names().size();
        m_Height = RECT_SIZE * 4;
        m_HorizontalBounds = new Vector2f(x, x + m_Width);
        m_VerticalBounds = new Vector2f(y, y + m_Height);

        // Entity paletteEntity = new Entity("paletteEntity");
        // PalettesDisplay paletteComponent = new PalettesDisplay(0, 0);
        // paletteEntity.addComponent(paletteComponent);
        // entity().layer().addEntity(paletteEntity);

    }

    @Override
    public void init() {
        super.init();
        int x = 0;
        int y = 0;

        Set<String> paletteNames = Palette.names();
        for (String paletteName : paletteNames) {
            Palette palette = Palette.get(paletteName);

            for (String colorName : palette.colors().keySet()) {
                Color color = new Color(palette.color(colorName));
                Vector2f hb = new Vector2f(x, x + RECT_SIZE);
                Vector2f vb = new Vector2f(y, y + RECT_SIZE);
                Rect rect = new Rect(hb, vb);
                rect.material().uniform("color", color);
                m_Rects.add(rect);
                y += RECT_SIZE;
            }
            y = 0;
            x += RECT_SIZE;

        }

    }

    @Override
    public <T extends Uniform> T uniform(String name) {
        // NO IMPLEMENTATION
        throw new RuntimeException("UNIMPLEMENTED");
    }

    @Override
    public List<Rect> allRects() {
        return m_Rects;
    }

    @Override
    public Vector2f verticalBounds() {
        return new Vector2f().set(m_VerticalBounds);
    }

    @Override
    public Vector2f horizontalBounds() {
        return new Vector2f().set(m_HorizontalBounds);
    }

    @Override
    public void translate(Vector2f translation) {
        m_HorizontalBounds = (horizontalBounds().add(new Vector2f(translation.x)));
        m_VerticalBounds = (verticalBounds().add(new Vector2f(translation.y)));
    }

    @Override
    public Mesh2 clone() {
        // NO IMPLEMENTATION
        throw new RuntimeException("UNIMPLEMENTED");
    }



}
