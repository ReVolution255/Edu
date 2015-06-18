package ru.resolutionpoint.edu.animals.view;

import ru.resolutionpoint.edu.animals.model.Environment;
import ru.resolutionpoint.edu.animals.model.RedEntity;
import ru.resolutionpoint.edu.animals.model.GrayEntity;
import ru.resolutionpoint.edu.animals.model.StateWriter;
import xmlstate.jaxb.Entities;
import xmlstate.jaxb.Gray;
import xmlstate.jaxb.ObjectFactory;
import xmlstate.jaxb.Red;

import javax.xml.bind.JAXBException;
import java.math.BigInteger;
import java.util.List;

/**
 * Class <code>Application</code> is main application class.
 *
 * @author Denis Murashev
 */
public class Application {

	/**
	 * @param args command line arguments
	 */
	public static void main(String[] args) throws JAXBException {
		Environment environment = new Environment();
		environment.addEntity(new RedEntity(environment, 12, 1));
		environment.addEntity(new RedEntity(environment, 1, 10));
		environment.addEntity(new GrayEntity(environment, 16, 14));
		StateWriter sw = new StateWriter("xmlstate.jaxb", "C:\\Users\\admin\\IdeaProjects\\Edu\\xmlstate\\xmlstate.xml");
		sw.writeFile(environment);
		environment = sw.readFile();



		//x, y (max 15, max 15)
/*		environment.addEntity(new RedEntity(environment, 12, 1));
		environment.addEntity(new RedEntity(environment, 1, 10));*/
/*		//Only for big environment
		environment.addEntity(new RedEntity(environment, 22, 6));
		environment.addEntity(new RedEntity(environment, 36, 27));
		environment.addEntity(new RedEntity(environment, 48, 10));
		environment.addEntity(new RedEntity(environment, 54, 15));
		environment.addEntity(new RedEntity(environment, 43, 11));
		environment.addEntity(new RedEntity(environment, 15, 10));
		environment.addEntity(new RedEntity(environment, 22, 1));
		environment.addEntity(new RedEntity(environment, 56, 25));
		environment.addEntity(new RedEntity(environment, 52, 20));

		environment.addEntity(new GrayEntity(environment, 24, 1));
        environment.addEntity(new GrayEntity(environment, 7, 14));
        environment.addEntity(new GrayEntity(environment, 10, 1));
		environment.addEntity(new GrayEntity(environment, 1, 15));
		environment.addEntity(new GrayEntity(environment, 16, 14));
		environment.addEntity(new GrayEntity(environment, 1, 1));
		environment.addEntity(new GrayEntity(environment, 22, 15));
		environment.addEntity(new GrayEntity(environment, 27, 15));
		environment.addEntity(new GrayEntity(environment, 56, 12));
		environment.addEntity(new GrayEntity(environment, 24, 15));*/
		new MainFrame(environment);
	}
}
