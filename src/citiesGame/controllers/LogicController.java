package citiesGame.controllers;

import org.lwjgl.glfw.GLFW;
import citiesGame.TextGen;
import engine.Application;
import engine.Layer;
import engine.components.renderable.gui.FullscreenRectangle;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.events.keyboard.KeyPressedEvent;
import engine.rendering.RenderType;
import engine.util.Color;

public class LogicController extends UpdateableComponent {

    private boolean m_ShowingMenu;

    private MainMenuController m_MainMenuController;

    public LogicController() {
        m_ShowingMenu = true;
    }

    @Override
    public void init() {
        Application app = Application.instance();

        Layer backgroundLayer = new Layer("background", app.primaryWindow(), RenderType.GUI);
        Entity backgroundEntity = new Entity("background");
        FullscreenRectangle background = new FullscreenRectangle(app.primaryWindow().resolution());
        background.color(Color.parse("#700b0b"));
        background.color(Color.parse("palette$info$1light"));
        backgroundEntity.addComponent(background);
        backgroundLayer.addEntity(backgroundEntity);
        app.pushLayer(backgroundLayer);

        app.pushLayer(new Layer("model", app.primaryWindow(), RenderType.MODEL));
        app.pushLayer(new Layer("hud", app.primaryWindow(), RenderType.GUI));

        Layer mainMenuLayer = new Layer("main-menu", app.primaryWindow(), RenderType.GUI);
        Entity mainMenuEntity = new Entity("mainMenuEntity");
        m_MainMenuController = new MainMenuController();
        mainMenuEntity.addComponent(m_MainMenuController);
        mainMenuLayer.addEntity(mainMenuEntity);
        app.pushLayer(mainMenuLayer);

    }

    public void showMenu() {
        m_ShowingMenu = true;
        m_MainMenuController.showMenu();
    }

    public void hideMenu() {
        m_ShowingMenu = false;
        m_MainMenuController.hideMenu();
    }

    @Override
    public boolean handleKeyPressed(KeyPressedEvent kpe) {
        if (super.handleKeyPressed(kpe))
            return true;

        if (kpe.key() == GLFW.GLFW_KEY_ESCAPE) {
            Application.instance().primaryWindow().close();
            return true;
            // if (m_ShowingMenu)
            // hideMenu();
            // else
            // showMenu();
            // return true;
        }

        return false;
    }

}
