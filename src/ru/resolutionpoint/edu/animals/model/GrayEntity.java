package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 08.05.2015.
 */
public class GrayEntity extends Predator implements Runnable {

    public GrayEntity(Environment environment) {
        super(environment);
    }
    public GrayEntity(Environment environment, int x, int y) {
        super(environment, x, y);
        super.setLifeTime(Constants.getPredatorLifeTime());
        super.setBreedingTime(Constants.getNoBreedingPredatorSteps());
        isHungry = false;
        hungryCounter = Constants.getPredatorSatiationTime();
        predatorTime = Constants.getPredatorTime();
        position = new Point(x, y);
    }

    private boolean mustDie = false;

    private Point position;

    @Override
    public String getImagePath() {
        return "/images/gray.gif";
    }

    public void setBreedingStatus(boolean status){
        setBreeding(status);
    }

    @Override
    public void setMustDie(boolean status) {
        this.mustDie = status;
    }

    public int getX(){return getPosition().x;}

    public int getY(){return getPosition().y;}

    public Point getPosition(){
        return position;
    }

    @Override
    public void run() {
        super.run();
    }

    public void setPosition(Point position) {
        this.position = position;
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
    public int getEntityType() {
        return 1;
    }

    public void visit() {

        boolean mustDie = false;

        if (this.mustDie) mustDie = true;

        //Update current entity
        super.setLifeTime(super.getLifeTime()-1);
        boolean eatingTime = false;


        if (!isHungry) hungryCounter--;

        if(hungryCounter < 0) {
            isHungry = true;
            eatingTime = true;
        }

        if (isHungry) setLifeTime(getLifeTime()-1);

        if(getLifeTime() < 0) {
            mustDie = true;
        }

        if (!getBreeding()) setBreedingTime(getBreedingTime()-1);

        if (getBreedingTime() < 0) {
            setBreeding(true);
            setBreedingTime(Constants.getNoBreedingPredatorSteps());
        }

        //List of neighbor entities
        List<Entity> entities = new ArrayList<>();
        entities.addAll(getEnvironment().getEntities());

        //Remove yourself from entities/points array
        entities.remove(this);

        //Set minimal distance (initially max)
        double minimalDistance = Environment.WIDTH * Environment.HEIGHT;

        int neighborCounter = 0;
        int sameTypeEntityNeighborCounter = 0;

        Entity minimalDistanceEntity = this;
        Entity neighborEntity = null;

        int dx;
        int dy;
        Point nextPoint;
        //Find closest entity with same type
        do {
            if (getBreeding()) {
                for (Entity entity : entities) {
                    if (entity.getEntityType() == this.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) <= minimalDistance)
                        minimalDistanceEntity = entity;
                    if (Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) {
                        neighborCounter++;
                        if (entity.getEntityType() == getEntityType()) {
                            sameTypeEntityNeighborCounter++;
                            neighborEntity = entity;
                        }
                    }
                }
                //Find delta x for new point
                dx = Entity.getDeltaXfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

                //Find delta y for new point
                dy = Entity.getDeltaYfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

                //Next point to target
                nextPoint = new Point(getX() + dx, getY() + dy);
            } else {
                nextPoint = Entity.getRandomNeighborPoint(getPosition());
            }
        } while (nextPoint.getY() >= Environment.HEIGHT || nextPoint.getY() <= 0
                || nextPoint.getX() >= Environment.WIDTH || nextPoint.getX() <= 0);

        if(neighborCounter >= Constants.getNeighboringAnimalsLimit()) mustDie = true;


        if (super.getLifeTime() < 0) mustDie = true;

        if (mustDie) {
            //die
            super.getEnvironment().deleteEntity(this);
            EntitiesPanel.updateEntityView(this);
            super.stop();
        } else if (getBreeding() && sameTypeEntityNeighborCounter >= 1) {
            //multiply
            Point multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
            for (Entity entity : entities){
                if (getEntityType() == entity.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2){
                    while (entity.getPosition().compareTo(multiplyPoint) == 0){
                        multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
                    }
                }
            }
            Entity newGrayEntity = new GrayEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
            if (neighborEntity != null) {
                neighborEntity.setBreeding(false);
            }
            setBreedingStatus(false);
            super.getEnvironment().addEntity(newGrayEntity);
            EntitiesPanel.updateEntityView(newGrayEntity);
            newGrayEntity.start();
        }
        else if (eatingTime) {
            Entity minimalDistanceFoodEntity = this;
            for (Entity entity : entities) {
                if (entity.getEntityType() != this.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) <= minimalDistance)
                    minimalDistanceFoodEntity = entity;
                if (Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) {
                    if (entity.getEntityType() != getEntityType()) {
                        entity.setMustDie(true);
                        predatorTime = Constants.getPredatorTime();
                        isHungry = false;
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
        for (Entity entity : entities){
            if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
        }
        move(nextPoint);
    }

    @Override
    protected void move(Point point) {
        setPosition(point);
    }

}
