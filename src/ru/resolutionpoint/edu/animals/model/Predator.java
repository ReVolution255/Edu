package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public abstract class Predator extends Animal implements Runnable {

    protected int predatorLifeTime;
    protected int breedingCounter;
    protected boolean isHungry;
    protected int hungryCounter;
    protected int predatorTime;

    protected Predator(Environment environment) {
        super(environment);
    }

    protected Predator(Environment environment, int x, int y) {
        super(environment, x, y);
    }

    @Override
    public boolean isCanPaint() {
        return super.isCanPaint();
    }

    @Override
    public void setCanPaint(boolean canPaint) {
        super.setCanPaint(canPaint);
    }

    public Environment getEnvironment(){return super.getEnvironment();}

    @Override
    protected void move(Direction direction) {super.move(direction);
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
}
