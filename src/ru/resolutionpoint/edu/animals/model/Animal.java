package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public abstract class Animal extends Entity implements Runnable {

    protected int animalLifeTime;
    protected int overCrowdingCounter;
    protected boolean isDead;
    protected int breedingCounter;
    protected boolean canBreeding;
    protected int x;
    protected int y;
    protected static int dx = 1;
    protected static int dy = 1;

    protected Animal(Environment environment) {
        super(environment);
    }

    protected Animal(Environment environment, int x, int y) {
        super(environment);
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

    public boolean isDead(){
        return isDead;
    }

    public void isDead(boolean status){
        this.isDead = status;
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
    protected void move(Direction direction){
    }
}
