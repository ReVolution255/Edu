package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 1 on 09.05.2015.
 */

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

    public Environment getEnvironment(){return super.getEnvironment();}

    //Common eating-time status
    public void setEatingTime(boolean eatingTime) {
        this.eatingTime = eatingTime;
    }
    @XmlElement
    public boolean isEatingTime() {
        return eatingTime;
    }
    protected boolean eatingTime = false;

    //Common visit method
    @Override
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
