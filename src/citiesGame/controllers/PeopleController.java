package citiesGame.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import org.joml.Vector3f;
import citiesGame.cityComponents.Person;
import citiesGame.util.Readable;
import citiesGame.util.Writable;
import engine.components.updateable.UpdateableComponent;
import engine.entities.Entity;
import engine.util.Easing;
import engine.util.Time;

public class PeopleController extends UpdateableComponent implements Readable, Writable {


    private GameController m_GameController;

    private Person m_Leader;

    private float m_Lower;
    private float m_Upper;
    private float m_MoveFactor;
    private float m_MoveInverse;

    public PeopleController(GameController gameController) {
        m_GameController = gameController;
        m_Lower = -5;
        m_Upper = 5;
        m_MoveFactor = 0.5f;
        m_MoveInverse = 1;
    }

    @Override
    public void init() {
        initRandomPeople();
    }

    @Override
    public void update() {
        // leaderTest();
    }

    private void leaderTest() {
        if (m_Leader.locomotion() != null) {
            float val = Easing.LINEAR.ease(m_Lower, m_Upper, m_MoveFactor);
            m_Leader.locomotion().velocity(new Vector3f(val, 0, val));
        }
        m_MoveFactor += Time.delta() * m_MoveInverse;
        if (m_MoveFactor > 1) {
            m_MoveInverse = -1;
            m_MoveFactor = 1;
        }
        if (m_MoveFactor < 0) {
            m_MoveInverse = 1;
            m_MoveFactor = 0;
        }
    }


    public void initRandomPeople() {
        m_Leader = initPerson(0, 0);
    }

    private Person initPerson(float x, float z) {
        Entity entity = new Entity("Person " + x + " " + z, new Vector3f(x, .5f, z));
        Person person = new Person();
        entity.addComponent(person);
        entity().layer().addEntity(entity);
        return person;
    }

    @Override
    public void write(FileOutputStream fos) throws IOException {

    }

    @Override
    public void read(Scanner scanner) {

    }

}
