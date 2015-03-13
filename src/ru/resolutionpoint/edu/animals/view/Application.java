package ru.resolutionpoint.edu.animals.view;

import ru.resolutionpoint.edu.animals.model.Environment;
import ru.resolutionpoint.edu.animals.model.RedEntity;

/**
 * Class <code>Application</code> is main application class.
 *
 * @author Denis Murashev
 */
public class Application {

	/**
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Environment environment = new Environment();
		environment.addEntity(new RedEntity(environment));
        environment.addEntity(new RedEntity(environment, 1, 5));
		new MainFrame(environment);
	}
}
