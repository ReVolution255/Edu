package ru.resolutionpoint.edu.animals.view;

import ru.resolutionpoint.edu.animals.model.*;
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

		//Creating init-xml
		environment.addEntity(new RedEntity(environment, 12, 1));
		environment.addEntity(new RedEntity(environment, 1, 10));
		environment.addEntity(new GrayEntity(environment, 16, 14));
		StateWriter sw = new StateWriter("xmlstate.jaxb", "xmlstate/xmlstate.xml");
		sw.writeFile(environment);

		//Reading init-xml
		environment = sw.readFile();

		new MainFrame(environment);
	}
}
