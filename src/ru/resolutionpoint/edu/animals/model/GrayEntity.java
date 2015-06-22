package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.annotation.XmlElement;

public class GrayEntity extends Predator {

    public GrayEntity(){
        super();
    }

    public GrayEntity(Environment environment, int x, int y) {
        super(environment, x, y);
        setLifeTime(Constants.getPredatorLifeTime());
        setBreedingTime(Constants.getNoBreedingPredatorSteps());
    }

    public GrayEntity(Environment environment, int x, int y, int breedingTime, int lifeTime, boolean mustDie, boolean canBreeding, int hungryCounter, int predatorTime, boolean eatingTime, boolean isHungry) {
        super(environment, x, y);
        setMustDie(mustDie);
        setBreeding(canBreeding);
        setBreedingTime(breedingTime);
        setLifeTime(lifeTime);
        setEatingTime(eatingTime);
        setPredatorTime(predatorTime);
        setHungryCounter(hungryCounter);
        setIsHungry(isHungry);
    }

    @Override
    @XmlElement
    public String getImagePath() {
        return "/images/gray.gif";
    }

    public int getNoBreedingSteps(){
        return Constants.getNoBreedingPredatorSteps();
    }

    public Entity bornChild(Point multiplyPoint, Environment environment){
        return new GrayEntity(environment, multiplyPoint.getX(), multiplyPoint.getY());
    }

    @Override
    public int getEntityType() {
        return 1;
    }

    @Override
    public void visit(Environment environment) {
        super.visit(environment);
        if (eatingTime) {
            Entity minimalDistanceFoodEntity = this;
            for (Entity entity : entities) {
                if (entity.getEntityType() != this.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) <= minimalDistance)
                    minimalDistanceFoodEntity = entity;
                if (Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) {
                    if (entity.getEntityType() != getEntityType()) {
                        entity.setMustDie(true);
                        setPredatorTime(Constants.getPredatorTime());
                        setIsHungry(false);
                        eatingTime = false;
                        break;
                    }
                }
            }
            //Find delta x for new point
            dx = Entity.getDeltaXfromPoints(this.getPosition(), minimalDistanceFoodEntity.getPosition());

            //Find delta y for new point
            dy = Entity.getDeltaYfromPoints(this.getPosition(), minimalDistanceFoodEntity.getPosition());

            //Next point to target
            nextPoint = new Point(getX() + dx, getY() + dy);

            if (minimalDistanceFoodEntity.equals(this)){
                nextPoint = Entity.getRandomNeighborPoint(getPosition());
            }
        }
        //If next point is busy not move
        checkBusyPoint();
        move(nextPoint);
    }
}
