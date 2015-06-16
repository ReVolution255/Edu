package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public abstract class Animal extends Entity {

    protected Animal(Environment environment, int x, int y) {
        super(environment, x, y);
        super.setLifeTime(Constants.getAnimalLifeTime());
        super.setBreedingTime(Constants.getNoBreedingAnimalSteps());
        super.setBreeding(false);
    }

    public Environment getEnvironment(){return super.getEnvironment();}
}
