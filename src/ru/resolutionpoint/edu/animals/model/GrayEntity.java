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
        predatorLifeTime = Constants.getPredatorLifeTime();
        breedingCounter = Constants.getNoBreedingPredatorSteps();
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
        this.canBreeding = status;
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
    protected int getEntityType() {
        return 1;
    }

    public void visit() {

        boolean mustDie = false;

        if (this.mustDie) mustDie = true;

        System.out.println();
        System.out.println("Visited Entity: " + this.toString() + " x = " + getX() + " y = " + getY());
        //Update current entity
        predatorLifeTime--;
        boolean eatingTime = false;


        if (!isHungry) hungryCounter--;

        if(hungryCounter < 0) {
            isHungry = true;
            eatingTime = true;
        }

        if (isHungry) predatorTime--;

        if(predatorTime < 0) {
            mustDie = true;
        }

        System.out.println("Lifetime: " + predatorLifeTime);

        if (!canBreeding) breedingCounter--;

        if (breedingCounter < 0) {
            canBreeding = true;
            breedingCounter = Constants.getNoBreedingPredatorSteps();
        }

        System.out.println("Breeding counter: " + breedingCounter);

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
            if (canBreeding) {
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


        if (predatorLifeTime < 0) mustDie = true;

        System.out.println("Neighbor Counter: " + neighborCounter);
        System.out.println("Same type neighbor counter: " + sameTypeEntityNeighborCounter);
        System.out.println("Can breeding? " + canBreeding);
        System.out.println("Eating time? " + eatingTime);
        System.out.println("Hungry Counter? " + hungryCounter);
        System.out.println("Predator Counter? " + predatorTime);

        if (mustDie) {
            //die
            System.out.println("And must die");
            super.getEnvironment().deleteEntity(this);
            EntitiesPanel.updateEntityView(this);
            super.stop();
        } else if (canBreeding && sameTypeEntityNeighborCounter >= 1) {
            //multiply
            System.out.println("And must multiply");
            Point multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
            for (Entity entity : entities){
                if (getEntityType() == entity.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2){
                    while (entity.getPosition().compareTo(multiplyPoint) == 0){
                        multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
                    }
                }
            }
            Entity newGrayEntity = new GrayEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
            System.out.println("\n\n\nCREATING NEW ENTITY AT POINT: " + newGrayEntity.getX() + " " + newGrayEntity.getY());
            if (neighborEntity != null) {
                neighborEntity.setBreedingStatus(false);
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
