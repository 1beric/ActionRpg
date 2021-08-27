package citiesGame.controllers;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import citiesGame.gui.mainMenu.MainMenuController;
import engine.Application;
import engine.Layer;
import engine.components.renderable.Mesh3;
import engine.components.renderable.gui.FullscreenRectangle;
import engine.components.updateable.CameraController;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.events.keyboard.KeyPressedEvent;
import engine.models.RawModel;
import engine.models.creation.CubeBuilder;
import engine.rendering.RenderType;
import engine.util.Color;
import engine.util.MathTools;
import engine.util.Time;

public class LogicController extends UpdateableComponent {

    private boolean m_ShowingMenu;

    private MainMenuController m_MainMenuController;
    private CameraController m_CameraController;

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
        backgroundEntity.addComponent(background);
        backgroundLayer.addEntity(backgroundEntity);

        app.pushLayer(backgroundLayer);

        Layer gameLayer = new Layer("model", app.primaryWindow(), RenderType.MODEL);

        Entity boxEntity = new Entity("box", new Vector3f(0, 0, -10));
        RawModel boxModel = CubeBuilder.build(false);
        Mesh3 boxMesh = new Mesh3(boxModel);
        boxEntity.addComponent(boxMesh);
        UpdateableComponent boxChanger = new UpdateableComponent() {
            private final float m_DefaultSeconds = 2;
            private float m_CurrentSeconds = 2;

            @Override
            public void update() {
                m_CurrentSeconds -= Time.delta();
                if (m_CurrentSeconds <= 0) {
                    m_CurrentSeconds += m_DefaultSeconds;
                    boxMesh.uniform("color", Color.rand());
                    System.out.println("color");
                }
                transform().rotate(MathTools.randomVec3(0, 20).mul(Time.delta()));
            }
        };
        boxEntity.addComponent(boxChanger);
        gameLayer.addEntity(boxEntity);

        m_CameraController = new CameraController();
        Entity cameraEntity = gameLayer.entity("Main Camera");
        cameraEntity.addComponent(m_CameraController);

        app.pushLayer(gameLayer);

        Layer mainMenuLayer = new Layer("main-menu", app.primaryWindow(), RenderType.GUI);

        Entity mainMenuEntity = new Entity("mainMenuEntity");
        m_MainMenuController = new MainMenuController(app.primaryWindow().resolution());
        mainMenuEntity.addComponent(m_MainMenuController);
        mainMenuLayer.addEntity(mainMenuEntity);

        app.pushLayer(mainMenuLayer);

    }

    public void showMenu() {
        m_ShowingMenu = true;
        m_MainMenuController.showMenu();
        m_CameraController.controlling(false);
    }

    public void hideMenu() {
        m_ShowingMenu = false;
        m_MainMenuController.hideMenu();
        m_CameraController.controlling(true);
    }

    @Override
    public boolean handleKeyPressed(KeyPressedEvent kpe) {
        if (super.handleKeyPressed(kpe))
            return true;

        if (kpe.key() == GLFW.GLFW_KEY_ESCAPE) {
            LogicController logic =
                    Application.instance().layer("logic").component(LogicController.class);
            if (m_ShowingMenu)
                logic.hideMenu();
            else
                logic.showMenu();
            return true;
        }

        return false;
    }

}
