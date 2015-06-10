package ru.resolutionpoint.edu.animals.view;

import ru.resolutionpoint.edu.animals.model.Environment;
import ru.resolutionpoint.edu.animals.model.RedEntity;
import ru.resolutionpoint.edu.animals.model.GrayEntity;

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
		environment.addEntity(new GrayEntity(environment, 1, 1));
        //environment.addEntity(new RedEntity(environment, 1, 10));
        //environment.addEntity(new RedEntity(environment, 7, 14));
        //environment.addEntity(new RedEntity(environment, 10, 1));
		//environment.addEntity(new RedEntity(environment, 1, 15));
		//environment.addEntity(new RedEntity(environment, 16, 14));
		//environment.addEntity(new RedEntity(environment, 19, 1));
		//environment.addEntity(new RedEntity(environment, 22, 1));
		environment.addEntity(new GrayEntity(environment, 22, 15));
		new MainFrame(environment);
	}
}
