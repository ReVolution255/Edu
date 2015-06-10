package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public abstract class Predator extends Animal implements Runnable {

    protected Predator(Environment environment) {
        super(environment);
    }

    protected Predator(Environment environment, int x, int y) {
        super(environment, x, y);
        super.setLifeTime(Constants.getPredatorLifeTime());
        super.setBreedingTime(Constants.getNoBreedingPredatorSteps());
        super.setBreeding(false);
        setHungryCounter(Constants.getPredatorSatiationTime());
        setPredatorTime(Constants.getPredatorTime());
        setIsHungry(false);
    }

    public Environment getEnvironment(){return super.getEnvironment();}

    //Common eating-time status
    public boolean isEatingTime() {
        return eatingTime;
    }
    public void setEatingTime(boolean eatingTime) {
        this.eatingTime = eatingTime;
    }
    private boolean eatingTime = false;

    //Common visit method
    public void visit(){
        super.visit();

        if (!isHungry()) setHungryCounter(getHungryCounter() - 1);

        if(getHungryCounter() < 0) {
            setIsHungry(true);
            setEatingTime(true);
        }

        if (isHungry()) setPredatorTime((getPredatorTime()-1));

        if(getPredatorTime() < 0) {
            setMustDie(true);
        }
    }

    //Common moving method
    protected void move(Point point){
        super.move(point);
    }

    //Thread-management
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

    //Common hungry status
    public boolean isHungry() {
        return isHungry;
    }
    public void setIsHungry(boolean isHungry) {
        this.isHungry = isHungry;
    }
    protected boolean isHungry;

    //Common hungry counter
    public int getHungryCounter() {
        return hungryCounter;
    }
    public void setHungryCounter(int hungryCounter) {
        this.hungryCounter = hungryCounter;
    }
    protected int hungryCounter;

    //Common predator time
    public int getPredatorTime() {
        return predatorTime;
    }
    public void setPredatorTime(int predatorTime) {
        this.predatorTime = predatorTime;
    }
    protected int predatorTime;
}
