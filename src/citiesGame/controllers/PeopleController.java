package citiesGame.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import citiesGame.util.Readable;
import citiesGame.util.Writable;
import engine.components.updateable.UpdateableComponent;

public class PeopleController extends UpdateableComponent implements Readable, Writable {

    private float m_RoundedX;
    private float m_RoundedZ;

    private GameController m_GameController;

    public PeopleController(float defaultX, float defaultZ, GameController gameController) {
        m_RoundedX = defaultX;
        m_RoundedZ = defaultZ;
        m_GameController = gameController;
    }

    @Override
    public void init() {
        initRandomPeople();
    }


    public void initRandomPeople() {

    }

    @Override
    public void write(FileOutputStream fos) throws IOException {

    }

    @Override
    public void read(Scanner scanner) {

    }

}
