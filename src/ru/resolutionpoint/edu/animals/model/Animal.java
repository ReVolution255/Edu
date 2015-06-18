package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 1 on 09.05.2015.
 */

public abstract class Animal extends Entity {

    public Animal(){
        super();
    }

    protected Animal(Environment environment, int x, int y) {
        super(environment, x, y);
        super.setLifeTime(Constants.getAnimalLifeTime());
        super.setBreedingTime(Constants.getNoBreedingAnimalSteps());
        super.setBreeding(false);
    }
}
