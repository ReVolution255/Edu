package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public abstract class Animal extends Entity implements Runnable {

    protected Animal(Environment environment) {
        super(environment);
    }
    protected Animal(Environment environment, int x, int y) {
        super(environment, x, y);
        super.setLifeTime(Constants.getAnimalLifeTime());
        super.setBreedingTime(Constants.getNoBreedingAnimalSteps());
        super.setBreeding(false);
    }

    public Environment getEnvironment(){return super.getEnvironment();}

    //Common lifetime
    public int getLifeTime() {
        return super.getLifeTime();
    }
    public void setLifeTime(int lifeTime) {
        super.setLifeTime(lifeTime);
    }

    //Common breeding counter
    public int getBreedingTime() {
        return super.getBreedingTime();
    }
    public void setBreedingTime(int breedingTime) {
        super.setBreedingTime(breedingTime);
    }

    //Common position
    public void setPosition(Point position) {
        setPosition(position);
    }
    public Point getPosition(){
        return super.getPosition();
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void stop() {
        super.stop();
    }

    @Override
    protected abstract void move(Point point);
}
