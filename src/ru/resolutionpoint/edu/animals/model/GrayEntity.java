package ru.resolutionpoint.edu.animals.model;


/**
 * Created by 1 on 08.05.2015.
 */
public class GrayEntity extends Predator implements Runnable {

    public GrayEntity(Environment environment, int x, int y) {
        super(environment, x, y);
        setLifeTime(Constants.getPredatorLifeTime());
        setBreedingTime(Constants.getNoBreedingPredatorSteps());
    }

    @Override
    public String getImagePath() {
        return "/images/gray.gif";
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

    public int getNoBreedingSteps(){
        return Constants.getNoBreedingPredatorSteps();
    }

    public Entity bornChild(Point multiplyPoint){
        return new GrayEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
    }

    @Override
    public int getEntityType() {
        return 1;
    }

    @Override
    public void visit() {
        super.visit();
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
