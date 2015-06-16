package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public abstract class Animal extends Entity implements Runnable {

    protected Animal(Environment environment, int x, int y) {
        super(environment, x, y);
        super.setLifeTime(Constants.getAnimalLifeTime());
        super.setBreedingTime(Constants.getNoBreedingAnimalSteps());
        super.setBreeding(false);
    }

    public Environment getEnvironment(){return super.getEnvironment();}

/*    //Common moving method
    @Override
    protected void move(Point point){
        super.move(point);
    };*/

    //Thread management
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
}
