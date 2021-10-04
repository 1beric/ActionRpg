package citiesGame.cityComponents;

import citiesGame.util.Locomotion;
import engine.components.renderable.Mesh3;
import engine.components.updateable.UpdateableComponent;
import engine.models.creation.CubeBuilder;
import engine.util.Color;

public class Person extends UpdateableComponent {

    private Locomotion m_Locomotion;

    public Person() {

    }


    @Override
    public void init() {

        m_Locomotion = new Locomotion();
        entity().addComponent(m_Locomotion);

        Mesh3 model = new Mesh3(CubeBuilder.build(false));
        model.uniform("color", Color.magenta());
        entity().addComponent(model);


    }



    public Locomotion locomotion() {
        return m_Locomotion;
    }
}
