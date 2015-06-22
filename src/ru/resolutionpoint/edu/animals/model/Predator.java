package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.annotation.XmlElement;

public abstract class Predator extends Animal {

    public Predator(){

    }

    protected Predator(Environment environment, int x, int y) {
        super(environment, x, y);
        setLifeTime(Constants.getPredatorLifeTime());
        setBreedingTime(Constants.getNoBreedingPredatorSteps());
        setBreeding(false);
        setHungryCounter(Constants.getPredatorSatiationTime());
        setPredatorTime(Constants.getPredatorTime());
        setIsHungry(false);
    }

    //Common eating-time status
    @XmlElement
    public boolean isEatingTime() {
        return eatingTime;
    }
    public void setEatingTime(boolean eatingTime) {
        this.eatingTime = eatingTime;
    }
    protected boolean eatingTime = false;

    //Common visit method
    @Override
    public void visit(Environment environment){
        super.visit(environment);

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

    //Common hungry status
    @XmlElement
    public boolean isHungry() {
        return isHungry;
    }
    public void setIsHungry(boolean isHungry) {
        this.isHungry = isHungry;
    }
    protected boolean isHungry;

    //Common hungry counter
    @XmlElement
    public int getHungryCounter() {
        return hungryCounter;
    }
    public void setHungryCounter(int hungryCounter) {
        this.hungryCounter = hungryCounter;
    }
    protected int hungryCounter;

    //Common predator time
    @XmlElement
    public int getPredatorTime() {
        return predatorTime;
    }
    public void setPredatorTime(int predatorTime) {
        this.predatorTime = predatorTime;
    }
    protected int predatorTime;
}
