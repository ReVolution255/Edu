package ru.resolutionpoint.edu.animals.model;

public abstract class Animal extends Entity {

    public Animal(){
        super();
    }

    protected Animal(int x, int y) {
        super(x, y);
        super.setLifeTime(Constants.getAnimalLifeTime());
        super.setBreedingTime(Constants.getNoBreedingAnimalSteps());
        super.setBreeding(false);
    }
}
