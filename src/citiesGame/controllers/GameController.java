package citiesGame.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import citiesGame.gui.mainMenu.StyledButton;
import citiesGame.states.GameLocation;
import citiesGame.states.MouseState;
import citiesGame.util.Readable;
import citiesGame.util.Writable;
import engine.Application;
import engine.Layer;
import engine.components.renderable.Light;
import engine.components.renderable.Mesh3;
import engine.components.renderable.PointLight;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.events.keyboard.KeyPressedEvent;
import engine.events.mouse.MouseButtonPressedEvent;
import engine.events.mouse.MouseMovedEvent;
import engine.models.creation.CubeBuilder;
import engine.util.Color;
import engine.util.input.KeyboardPicker;
import engine.util.input.MousePicker;
import engine.util.string.StringTools;

public class GameController extends UpdateableComponent implements Writable, Readable {

    private static final int X_WIDTH = 50;
    private static final int Z_WIDTH = 50;
    private static final float SIZE = 2;

    private static final float FLOOR_Y = 0;

    private String m_GameFile;
    private boolean m_NewFile;

    private MainMenuController m_MainMenuController;
    private CitySkyController m_CitySkyController;
    private RoadController m_RoadController;
    private BuildingController m_BuildingController;

    private GameLocation m_CurrentlyViewing;

    private MouseState m_MouseState;

    private Mesh3 m_HoverMesh;

    public GameController(String gameFile, boolean isNew) {
        m_GameFile = gameFile;
        m_NewFile = isNew;
        m_CurrentlyViewing = GameLocation.CITY;
        m_MouseState = MouseState.CAMERA;
    }

    @Override
    public void init() {
        Layer gameLayer = entity().layer();

        Entity hoverEntity = new Entity("hovering", new Vector3f(0, -100, 0));
        m_HoverMesh = new Mesh3(CubeBuilder.build(false));
        m_HoverMesh.uniform("color", Color.red());
        hoverEntity.addComponent(m_HoverMesh);
        hoverEntity.transform().scale(new Vector3f(SIZE / 3, SIZE / 3, SIZE / 3));
        gameLayer.addEntity(hoverEntity);

        initCity();


        m_CitySkyController = new CitySkyController();
        Entity cameraEntity = gameLayer.entity("Main Camera");
        cameraEntity.addComponent(m_CitySkyController);
        cameraEntity.transform().position(new Vector3f(0, 0, 0));
        cameraEntity.transform().rotate(new Vector3f(0, 180, 0));
        m_CitySkyController.zoom(.7f);

        m_CitySkyController.controlling(true);


        Light dirLight = gameLayer.component(Light.class);
        Entity lightEntity = dirLight.entity();
        PointLight light = new PointLight();
        light.attenuation(new Vector3f(10f, 0.1f, 0.01f));
        System.out.println(light.color());
        lightEntity.addComponent(light);
        lightEntity.removeComponent(dirLight);
        lightEntity.transform().position(new Vector3f(0, 40, 0));

        initHud();

    }


    public void initCity() {
        if (m_NewFile) {
            // roads
            initRoadsController();
        } else {
            try {
                Scanner scanner = new Scanner(new File(m_GameFile));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }


    private void initRoadsController() {
        Layer gameLayer = entity().layer();
        String entityName = StringTools.buildString("RoadsController");
        Entity entity = new Entity(entityName);
        m_RoadController = new RoadController(X_WIDTH, FLOOR_Y, Z_WIDTH, SIZE, () -> {
            // buildings
            initBuildingController();

            // people

        });
        entity.addComponent(m_RoadController);
        gameLayer.addEntity(entity);
    }

    private void initBuildingController() {
        Layer gameLayer = entity().layer();
        String entityName = StringTools.buildString("BuildingController");
        Entity entity = new Entity(entityName);
        m_BuildingController =
                new BuildingController(X_WIDTH, FLOOR_Y, Z_WIDTH, SIZE, m_RoadController);
        entity.addComponent(m_BuildingController);
        gameLayer.addEntity(entity);
    }

    private void initHud() {

        Layer hudLayer = Application.instance().layer("hud");
        Vector2f resolution = hudLayer.window().resolution();

        Entity addRoadEntity = new Entity("addRoadEntity");
        StyledButton addRoadComponent = new StyledButton(100, 60);
        addRoadComponent.translate(new Vector2f(25, resolution.y - 25 - 60));
        addRoadComponent.press((obj, btn, pos) -> {
            if (m_MouseState != MouseState.ADD_ROAD) {
                changeMouseState(MouseState.ADD_ROAD);
                m_HoverMesh.uniform("color", Color.red());
            } else {
                m_HoverMesh.transform().position(new Vector3f(0, -100, 0));
                changeMouseState(MouseState.CAMERA);
            }
        });
        addRoadEntity.addComponent(addRoadComponent);
        hudLayer.addEntity(addRoadEntity);

        Entity addBuildingEntity = new Entity("addBuildingEntity");
        StyledButton addBuildingComponent = new StyledButton(100, 60);
        addBuildingComponent.translate(new Vector2f(25, resolution.y - 100 - 60));
        addBuildingComponent.press((obj, btn, pos) -> {
            if (m_MouseState != MouseState.ADD_BUILDING) {
                changeMouseState(MouseState.ADD_BUILDING);
                m_HoverMesh.uniform("color", Color.yellow());
            } else {
                m_HoverMesh.transform().position(new Vector3f(0, -100, 0));
                changeMouseState(MouseState.CAMERA);
            }
        });
        addBuildingEntity.addComponent(addBuildingComponent);
        hudLayer.addEntity(addBuildingEntity);

    }


    public void changeMouseState(MouseState mouseState) {
        if (m_MouseState == MouseState.CAMERA) {
            m_CitySkyController.controlling(false);
        }
        if (mouseState == MouseState.CAMERA) {
            m_CitySkyController.controlling(true);
        }
        m_MouseState = mouseState;
    }

    private void handleMouseMoved() {
        Vector3f mouseAtFloor = MousePicker.worldPointAtY(FLOOR_Y, m_CitySkyController.camera());
        Vector2f roundedPosition = roundWorldPosition(mouseAtFloor);
        m_HoverMesh.transform()
                .position(new Vector3f(roundedPosition.x, FLOOR_Y + SIZE / 2f, roundedPosition.y));
    }

    private void handleClick(int button) {
        Vector3f mouseAtFloor = MousePicker.worldPointAtY(FLOOR_Y, m_CitySkyController.camera());
        Vector2f roundedPosition = roundWorldPosition(mouseAtFloor);

        switch (m_MouseState) {
            case ADD_ROAD:
                m_RoadController.handleClick(button, roundedPosition.x, roundedPosition.y);
                break;
            case ADD_BUILDING:
                m_BuildingController.handleClick(button, roundedPosition.x, roundedPosition.y);
                break;
            default:
                break;
        }

        // m_HoverMesh.transform().position(new Vector3f(0, -100, 0));
        // changeMouseState(MouseState.CAMERA);
    }



    private Vector2f roundWorldPosition(Vector3f worldPosition) {
        float x = worldPosition.x;
        float z = worldPosition.z;
        x = Math.round(x / SIZE);
        z = Math.round(z / SIZE);
        return new Vector2f(x * SIZE, z * SIZE);
    }


    private void saveGame() {
        System.out.println("SAVE");
        try {
            FileOutputStream fos = new FileOutputStream(new File(m_GameFile));
            this.write(fos);
            fos.flush();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void showMenu() {
        if (m_CitySkyController != null)
            m_CitySkyController.controlling(false);
    }

    public void hideMenu() {
        if (m_CitySkyController != null)
            m_CitySkyController.controlling(true);
    }

    @Override
    public boolean handleKeyPressed(KeyPressedEvent kpe) {
        if (super.handleKeyPressed(kpe))
            return true;

        if (kpe.key() == GLFW.GLFW_KEY_Y) {
            m_CitySkyController.reset();
            return true;
        }

        if (kpe.key() == GLFW.GLFW_KEY_S && KeyboardPicker.key(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            saveGame();
            return true;
        }

        // if (kpe.key() == GLFW.GLFW_KEY_L) {
        // System.out.println(entity().layer());
        // return true;
        // }

        return false;
    }

    @Override
    public boolean handleMouseButtonPressed(MouseButtonPressedEvent mbpe) {
        if (super.handleMouseButtonPressed(mbpe))
            return true;

        if (mbpe.button() == 0 && (m_MouseState == MouseState.ADD_BUILDING
                || m_MouseState == MouseState.ADD_ROAD)) {
            handleClick(mbpe.button());
            return true;
        }

        return true;
    }

    @Override
    public boolean handleMouseMoved(MouseMovedEvent mme) {
        if (super.handleMouseMoved(mme))
            return true;

        if (m_MouseState == MouseState.ADD_BUILDING || m_MouseState == MouseState.ADD_ROAD) {
            handleMouseMoved();
            return true;
        }

        return false;
    }

    @Override
    public void write(FileOutputStream fos) throws IOException {
        fos.write(m_GameFile.getBytes());
        fos.write('\n');
        m_RoadController.write(fos);
        m_BuildingController.write(fos);
    }

    @Override
    public void read(Scanner scanner) {
        String fileName = scanner.nextLine();
        m_RoadController.read(scanner);
        m_BuildingController.read(scanner);
    }



}
