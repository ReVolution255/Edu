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
		Entities entities = new Entities();
		List<Red> redentity = entities.getRedentity();
		List<Gray> grayentity = entities.getGrayentity();
		StateWriter sw = new StateWriter("xmlstate.jaxb", "C:\\Users\\admin\\IdeaProjects\\Edu\\xmlstate\\xmlstate.xml");
		sw.readFile();

		System.out.println(sw.entities.toString());
		for (Red red : sw.entities.getRedentity()){
			System.out.println("Type: " + red.getType());
			System.out.println("X: " + red.getX());
			System.out.println("Y: " + red.getY());
			System.out.println("Breeding time: " + red.getBreedingTime());
			System.out.println("Lifetime: " + red.getLifeTime());
			System.out.println("Must die?: " + red.isMustDie());
			System.out.println("Can breeding?: " + red.isCanBreeding());
			environment.addEntity(new RedEntity(environment, red.getX().intValue(),  red.getY().intValue(), red.getBreedingTime().intValue(), red.getLifeTime().intValue(), red.isMustDie(), red.isCanBreeding()));
		}

		for (Gray gray : sw.entities.getGrayentity()){
			System.out.println("Type: " + gray.getType());
			System.out.println("X: " + gray.getX());
			System.out.println("Y: " + gray.getY());
			System.out.println("Breeding time:: " + gray.getBreedingTime());
			System.out.println("Lifetime: " + gray.getLifeTime());
			System.out.println("Must die?: " + gray.isMustDie());
			System.out.println("Can breeding?: " + gray.isCanBreeding());
			System.out.println("Hungry counter: " + gray.getHungryCounter());
			System.out.println("Predator time: " + gray.getPredatorTime());
			System.out.println("Eating time?: " + gray.isEatingTime());
			System.out.println("Is hungry?: " + gray.isIsHungry());
		}
/*		ObjectFactory of = new ObjectFactory();
		Red newRed = of.createRed();
		newRed.setBreedingTime(new BigInteger("11"));
		newRed.setCanBreeding(true);
		newRed.setLifeTime(new BigInteger("111"));
		newRed.setMustDie(false);
		newRed.setX(new BigInteger("21"));
		newRed.setY(new BigInteger("31"));
		sw.entities.getRedentity().add(newRed);
		sw.writeFile(sw.entities);*/
	/*	for (Red red : redentity){
			System.out.println(red.getType());
			System.out.println(red.getX());
			System.out.println(red.getY());
			System.out.println(red.getBreedingTime().intValue());//
			System.out.println(red.getLifeTime().intValue());//
			System.out.println(red.isMustDie());//
			System.out.println(red.isCanBreeding());
		}*/

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
