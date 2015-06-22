package ru.resolutionpoint.edu.animals.model;

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
