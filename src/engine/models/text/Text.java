package engine.models.text;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;
import engine.models.RawModel;
import engine.models.Rect;
import engine.models.Texture;
import engine.util.Renderable;
import engine.util.string.Stringable;

public class Text implements Stringable, Renderable {

    private String m_Value;
    private FontGlyphs m_FontGlyphs;
    private Justification m_Justification;
    private OverflowType m_Overflow;
    private float m_Width, m_Height, m_MaxWidth;
    private List<TextLine> m_Lines;

    public Text(String value) {
        this.m_Value = value;
        m_Justification = Justification.LEFT;
        m_Overflow = OverflowType.VISIBLE;
        m_Width = 0;
        m_Height = 0;
        m_MaxWidth = -1;
        m_FontGlyphs = FontGlyphs.common();
        m_Lines = new ArrayList<>();
        recalculateLines();
    }

    public Text value(String value) {
        this.m_Value = value;
        recalculateLines();
        return this;
    }

    public void recalculateLines() {

    }


    @Override
    public Texture render(Object... args) {
		Vector2f resolution = (Vector2f) args[0];
		RawModel model = (RawModel) args[1];
        Rect rect = (Rect) args[2];

        return null;
    }

    @Override
    public String string(int indentAmt) {
        return m_Value;
    }
    
}
