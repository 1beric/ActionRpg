package citiesGame.gui.mainMenu;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import citiesGame.controllers.LogicController;
import citiesGame.states.MainMenuPage;
import engine.Application;
import engine.components.renderable.gui.FullscreenRectangle;
import engine.entities.Entity;
import engine.events.keyboard.KeyPressedEvent;
import engine.util.Color;

public class MainMenuController extends FullscreenRectangle {

    private boolean m_ShowingMenu;
    private MainMenuPage m_Page;

    public MainMenuController(Vector2f screenResolution) {
        super(screenResolution);
        m_ShowingMenu = true;
        m_Page = MainMenuPage.MAIN;
    }

    @Override
    public void init() {
        super.init();

        Vector2f resolution = Application.instance().primaryWindow().resolution();

        LogicController logic = Application.instance().layer("logic").component(LogicController.class);
        
      
		this.color(Color.parse("palette$background$2main"));
      
        // TITLE
		Entity titleEntity = new Entity("titleEntity");
		MainMenuTitle titleComponent = new MainMenuTitle(resolution.x * .5f, resolution.y * .25f);
		titleComponent.translate(new Vector2f(resolution.x * .25f, resolution.y * .05f));
		titleEntity.addComponent(titleComponent);
		entity().layer().addEntity(titleEntity);

        // NEW
		Entity newGameButton = new Entity("newGameButton");
		MainMenuButton newGameComponent = new MainMenuButton(
            resolution.x * .3f,
            resolution.y * .1f,
            Color.parse("palette$secondary$2main"),
            Color.parse("palette$secondary$1light"));
		newGameComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .4f));
        newGameComponent.press((obj, btn, pos) -> {
            System.out.println("CREATE NEW GAME");
            logic.hideMenu();
        });
		newGameButton.addComponent(newGameComponent);
		entity().layer().addEntity(newGameButton);

        // LOAD
		Entity loadGameButton = new Entity("loadGameButton");
		MainMenuButton loadGameComponent = new MainMenuButton(
            resolution.x * .3f,
            resolution.y * .1f,
            Color.parse("palette$secondary$2main"),
            Color.parse("palette$secondary$1light"));
		loadGameComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .55f));
        loadGameComponent.press((obj, btn, pos) -> {
            System.out.println("LOAD GAME");
            logic.hideMenu();
        });
		loadGameButton.addComponent(loadGameComponent);
		entity().layer().addEntity(loadGameButton);

        // SETTINGS
		Entity settingsButton = new Entity("settingsButton");
		MainMenuButton settingsComponent = new MainMenuButton(
            resolution.x * .3f,
            resolution.y * .1f,
            Color.parse("palette$secondary$2main"),
            Color.parse("palette$secondary$1light"));
		settingsComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .7f));
        settingsComponent.press((obj, btn, pos) -> {
            System.out.println("OPEN SETTINGS");
        });
		settingsButton.addComponent(settingsComponent);
		entity().layer().addEntity(settingsButton);

        // QUIT
		Entity quitButton = new Entity("quitButton");
		MainMenuButton quitComponent = new MainMenuButton(
            resolution.x * .3f,
            resolution.y * .1f,
            Color.parse("palette$secondary$2main"),
            Color.parse("palette$secondary$1light"));
		quitComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .85f));
        quitComponent.press((obj, btn, pos) -> {
            System.out.println("QUIT"); 
            Application.instance().primaryWindow().close();
        });
		quitButton.addComponent(quitComponent);
		entity().layer().addEntity(quitButton);


    }

    public void showMenu() {
        m_ShowingMenu = true;
        entity().layer().enable();
    }
    
    public void hideMenu() {
        m_ShowingMenu = false;
        entity().layer().disable();
    }
    
}
