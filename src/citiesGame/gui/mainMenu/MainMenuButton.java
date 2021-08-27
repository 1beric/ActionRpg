package citiesGame.gui.mainMenu;

import engine.components.renderable.gui.Button;
import engine.util.Color;

public class MainMenuButton extends Button {

	private Color m_MainColor;
	private Color m_HoverColor;

    public MainMenuButton(float width, float height) {
        super(width, height);
		m_MainColor = Color.parse("palette$primary$2main");
		m_HoverColor = Color.parse("palette$primary$1light");
    }

    public MainMenuButton(float width, float height, Color main, Color hover) {
        super(width, height);
		m_MainColor = main;
		m_HoverColor = hover;
    }

    @Override
    public void init() {
		color(m_MainColor);
		hover((obj, vec2) -> {
			color(m_HoverColor);
		});
		unhover((obj, vec2) -> {
			color(m_MainColor);
		});
    }

}
