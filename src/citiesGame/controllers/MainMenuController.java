package citiesGame.controllers;

import org.joml.Vector2f;
import citiesGame.gui.mainMenu.StyledButton;
import citiesGame.gui.mainMenu.MainMenuTitle;
import citiesGame.states.MenuPage;
import engine.Application;
import engine.Layer;
import engine.components.renderable.PostProcessor;
import engine.components.renderable.gui.FullscreenRectangle;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.models.Texture;
import engine.util.Color;

public class MainMenuController extends UpdateableComponent {

    private LogicController m_LogicController;
    private GameController m_GameController;

    private boolean m_ShowingMenu;
    private MenuPage m_Page;

    public MainMenuController() {
        m_ShowingMenu = true;
        m_Page = MenuPage.MAIN;
    }

    @Override
    public void init() {
        super.init();


        m_LogicController = Application.instance().layer("logic").component(LogicController.class);

        initMainMenuComponents();

        // // X top right
        // Entity xButton = new Entity("quitButton");
        // StyledButton xComponent = new StyledButton(50, 50,
        // Color.parse("palette$background$2main"),
        // Color.parse("palette$error$2main"));
        // xComponent.translate(new Vector2f(resolution.x - 50, 0));
        // xComponent.press((obj, btn, pos) -> {
        // Application.instance().primaryWindow().close();
        // });
        // xButton.addComponent(xComponent);
        // entity().layer().addEntity(xButton);


    }

    private void initMainMenuComponents() {

        Vector2f resolution = Application.instance().primaryWindow().resolution();

        // BACKGROUND
        Entity backgroundEntity = new Entity("background");
        FullscreenRectangle backgroundComponent = new FullscreenRectangle(resolution);
        // backgroundComponent.color(Color.parse("palette$background$2main"));
        backgroundComponent.image(Texture.texture("jungleBridge"));
        backgroundEntity.addComponent(backgroundComponent);
        entity().layer().addEntity(backgroundEntity);

        // TITLE
        Entity titleEntity = new Entity("titleEntity");
        MainMenuTitle titleComponent = new MainMenuTitle(600, 250);
        titleComponent.translate(new Vector2f(resolution.x * .5f - 300, 50));
        titleEntity.addComponent(titleComponent);
        entity().layer().addEntity(titleEntity);

        // NEW
        Entity newGameButton = new Entity("newGameButton");
        StyledButton newGameComponent = new StyledButton(resolution.x * .3f, resolution.y * .1f,
                Color.parse("palette$secondary$2main"), Color.parse("palette$secondary$1light"));
        newGameComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .4f));
        newGameComponent.press((obj, btn, pos) -> {
            handleNewGame();
        });
        newGameButton.addComponent(newGameComponent);
        entity().layer().addEntity(newGameButton);

        // LOAD
        Entity loadGameButton = new Entity("loadGameButton");
        StyledButton loadGameComponent = new StyledButton(resolution.x * .3f, resolution.y * .1f,
                Color.parse("palette$secondary$2main"), Color.parse("palette$secondary$1light"));
        loadGameComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .55f));
        loadGameComponent.press((obj, btn, pos) -> {
            handleLoadGame();
        });
        loadGameButton.addComponent(loadGameComponent);
        entity().layer().addEntity(loadGameButton);

        // SETTINGS
        Entity settingsButton = new Entity("settingsButton");
        StyledButton settingsComponent = new StyledButton(resolution.x * .3f, resolution.y * .1f,
                Color.parse("palette$secondary$2main"), Color.parse("palette$secondary$1light"));
        settingsComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .7f));
        settingsComponent.press((obj, btn, pos) -> {
            handleSettings();
        });
        settingsButton.addComponent(settingsComponent);
        entity().layer().addEntity(settingsButton);

        // QUIT
        Entity quitButton = new Entity("quitButton");
        StyledButton quitComponent = new StyledButton(resolution.x * .3f, resolution.y * .1f,
                Color.parse("palette$secondary$2main"), Color.parse("palette$secondary$1light"));
        quitComponent.translate(new Vector2f(resolution.x * .1f, resolution.y * .85f));
        quitComponent.press((obj, btn, pos) -> {
            System.out.println("QUIT");
            Application.instance().primaryWindow().close();
        });
        quitButton.addComponent(quitComponent);
        entity().layer().addEntity(quitButton);
    }

    public void handleNewGame() {
        // change to MenuPage.NEW_GAME
        System.out.println("CREATE NEW GAME");

        Layer gameLayer = Application.instance().layer("model");
        Entity gameEntity = new Entity("Game Controller");
        m_GameController = new GameController("res/saves/game_one.dat", true);
        gameEntity.addComponent(m_GameController);
        gameLayer.addEntity(gameEntity);

        m_LogicController.hideMenu();
    }

    public void handleLoadGame() {
        // change to MenuPage.LOAD_GAME
        System.out.println("LOAD GAME");

        Layer gameLayer = Application.instance().layer("model");
        Entity gameEntity = new Entity("Game Controller");
        m_GameController = new GameController("res/saves/game_one.dat", false);
        gameEntity.addComponent(m_GameController);
        gameLayer.addEntity(gameEntity);

        m_LogicController.hideMenu();
    }

    public void handleSettings() {
        System.out.println("OPEN SETTINGS");
        m_Page = MenuPage.SETTINGS;
    }

    public void showMenu() {
        m_ShowingMenu = true;
        entity().layer().enable();
        m_GameController.showMenu();
        // m_PostProcessor.enable();
    }

    public void hideMenu() {
        m_ShowingMenu = false;
        entity().layer().disable();
        m_GameController.hideMenu();
        // m_PostProcessor.disable();
    }

}
