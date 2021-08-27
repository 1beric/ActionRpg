package citiesGame.gui.mainMenu;

import engine.components.renderable.gui.Button;
import engine.util.Color;

public class MainMenuTitle extends Button {

    public MainMenuTitle(float width, float height) {
        super(width, height);
    }

    @Override
    public void init() {
        Color titleColor = Color.parse("palette$primary$2main");
		Color titleHoverColor = Color.parse("palette$primary$1light");
		color(titleColor);
		hover((obj, vec2) -> {
			color(titleHoverColor);
		});
		press((obj, btn, vec2) -> {
		});
		unhover((obj, vec2) -> {
			color(titleColor);
		});
    }

}
