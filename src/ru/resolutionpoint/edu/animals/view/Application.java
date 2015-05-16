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
		environment.addEntity(new RedEntity(environment, 2, 1));
        //environment.addEntity(new RedEntity(environment, 18, 9));
        environment.addEntity(new RedEntity(environment, 10, 8));
        //environment.addEntity(new RedEntity(environment, 1, 10));
		//environment.addEntity(new RedEntity(environment, 25, 14));
		//environment.addEntity(new RedEntity(environment, 10, 6));
        //environment.addEntity(new GrayEntity(environment, 10, 7));
		new MainFrame(environment);
	}
}
