package ru.resolutionpoint.edu.animals.view;

import ru.resolutionpoint.edu.animals.model.*;
import xmlstate.jaxb.Entities;
import xmlstate.jaxb.Gray;
import xmlstate.jaxb.ObjectFactory;
import xmlstate.jaxb.Red;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

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
		//Run modes
		Object[] modes = {"Play mode", "Search mode"};

		JFrame frame = new JFrame("Run mode");

		int mode = JOptionPane.showOptionDialog(frame, "Select run mode", "Run mode", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, modes, modes[0]);
		//Mode: play
		if (mode == 0){
			Properties property = new Properties();
			try {
				FileInputStream propertyFile = new FileInputStream(new File("config/config.properties"));
				property.load(propertyFile);
				Constants.setPredatorTime(Integer.parseInt(property.getProperty("predatorTime")));
				Constants.setAnimalLifeTime(Integer.parseInt(property.getProperty("animalLifeTime")));
				Constants.setPredatorLifeTime(Integer.parseInt(property.getProperty("predatorLifeTime")));
				Constants.setNoBreedingAnimalSteps(Integer.parseInt(property.getProperty("noBreedingAnimalSteps")));
				Constants.setNoBreedingPredatorSteps(Integer.parseInt(property.getProperty("noBreedingPredatorSteps")));
				Constants.setNeighboringAnimalsLimit(Integer.parseInt(property.getProperty("neighboringAnimalsLimit")));
				Constants.setPredatorSatiationTime(Integer.parseInt(property.getProperty("predatorSatiationTime")));
				Constants.setTimeDelay(Integer.parseInt(property.getProperty("TIME_DELAY")));
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Property load error");
				e.printStackTrace();
			}

			StateWriter sw = new StateWriter("xmlstate.jaxb", "xmlstate/xmlstate.xml");

			//Reading init-xml
			Environment environment = sw.readFile();

			new MainFrame(environment);}
		//Mode: search
		else {
			//JOptionPane.showMessageDialog(frame, "This function in develop, application will be closed.");
			//Future method
			SearchMode sm = new SearchMode(new Environment());
			sm.search();
		}
	}
}
