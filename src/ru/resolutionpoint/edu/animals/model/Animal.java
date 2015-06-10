package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public abstract class Animal extends Entity implements Runnable {

    protected int animalLifeTime;
    protected int overCrowdingCounter;
    protected int breedingCounter;
    protected boolean canBreeding;

    protected Animal(Environment environment) {
        super(environment);
    }
    protected Animal(Environment environment, int x, int y) {
        super(environment);
    }

    public Environment getEnvironment(){return super.getEnvironment();}

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
