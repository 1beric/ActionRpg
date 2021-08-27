package citiesGame;

import citiesGame.controllers.LogicController;
import engine.Application;
import engine.Driver;
import engine.Layer;
import engine.entities.Entity;
import engine.rendering.RenderType;

public class CitiesGame extends Application {

	private static final int WIDTH = 1024 + 512;
	private static final int HEIGHT = 512 + 256;

	public CitiesGame() {
		super("Cities", WIDTH, HEIGHT);
	}

	public static void main(String[] args) {
		Driver d = new Driver();
		d.app(new CitiesGame());
		d.start();
	}

	@Override
	public void initialize() {
		// initialize layers and components for game
		// essentially the main method of the application
		System.out.println("INIT");

		// always start on main menu
		Layer gameLayer = new Layer("logic", primaryWindow(), RenderType.NONE);

		Entity logicEntity = new Entity("logicEntity");
		LogicController logicComponent = new LogicController();
		logicEntity.addComponent(logicComponent);
		gameLayer.addEntity(logicEntity);

		pushLayer(gameLayer);
	}

}
