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

    private Point position;

    @Override
    public String getImagePath() {
        return "/images/gray.gif";
    }

    public int getX(){return getPosition().x;}

    public int getY(){return getPosition().y;}

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

    /**protected Direction getEntityDirection(Entity currentEntity, int algorithm) {
        //Direction, that must be returned
        Direction direction;
        Environment environment = getEnvironment();
        int currentX = currentEntity.getX();
        int currentY = currentEntity.getY();
        if (algorithm == 0) {
            direction = Algorithms.getRandomDirection();
            for (Entity entity : environment.getEntities()) {
                int x = entity.getX();
                int y = entity.getY();
                if (Math.abs(x - currentX) <= 1 && Math.abs(y - currentY) <= 1){
                    Direction dir = Algorithms.getDirectionFromInt(x ,y, currentX, currentY);
                    if (dir != direction) break;
                }
            }
        }
        else if (algorithm == 1){
            int x;
            int y;
            int currX = currentEntity.getX();
            int currY = currentEntity.getY();
            double minimalDistance = Environment.WIDTH * Environment.HEIGHT;
            double temp;
            Entity minimalDistanceEntity = currentEntity;
            for(Entity entity : getEnvironment().getEntities()){
                if(!entity.equals(currentEntity) && entity.getEntityType() == 1){
                    x = entity.getX();
                    y = entity.getY();
                    temp = Math.sqrt(Math.pow(x-currX,2)+Math.pow(y-currY,2));
                    if (temp < minimalDistance) {minimalDistance = temp; minimalDistanceEntity = entity;}
                }
            }
            x = minimalDistanceEntity.getX();
            y = minimalDistanceEntity.getY();
            direction = Algorithms.getDirectionFromInt(x, y, currX, currY);
        }
        else direction = Algorithms.getRandomDirection();
        return direction;
    }*/

    @Override
    protected int getEntityType() {
        return 1;
    }

    public void visit() {

        System.out.println();
        System.out.println("Visited Entity: " + this.toString() + " x = " + getX() + " y = " + getY());
        //Update current entity
        animalLifeTime--;

        boolean mustDie = false;
        System.out.println("Lifetime: " + animalLifeTime);

        if (!canBreeding) breedingCounter--;

        if (breedingCounter < 0) {
            canBreeding = true;
            breedingCounter = Constants.getNoBreedingAnimalSteps();
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

        int dx;
        int dy;
        Point nextPoint;
        //Find closest entity with same type
        if (canBreeding){
            for (Entity entity : entities){
                if (entity.getEntityType() == this.getEntityType() && Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) <= minimalDistance)
                    minimalDistanceEntity = entity;
                if (Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) {
                    neighborCounter++;
                    if (entity.getEntityType() == getEntityType()) sameTypeEntityNeighborCounter++;
                }
            }
            //Find delta x for new point
            dx = Algorithms.getDeltaXfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

            //Find delta y for new point
            dy = Algorithms.getDeltaYfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

            //Next point to target
            nextPoint = new Point(getX() + dx, getY() + dy);
        } else {
            nextPoint = Algorithms.getRandomNeighborPoint(getPosition());
        }

        if(neighborCounter >= Constants.getNeighboringAnimalsLimit()) mustDie = true;


        //If next point is busy not move
        for (Entity entity : entities){
            if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
        }

        //TODO make test for max X and max Y

        if (nextPoint.getY() >= Environment.HEIGHT)
            nextPoint.setY(position.getY());
        else if (nextPoint.getY() <= 0)
            nextPoint.setY(position.getY());
        else {
            // do nothing
        }
        if (nextPoint.getX() >= Environment.WIDTH)
            nextPoint.setX(position.getX());
        else if (nextPoint.getX() <= 0)
            nextPoint.setX(position.getX());
        else {
            // do nothing
        }

        move(nextPoint);

        if (animalLifeTime < 0) mustDie = true;

        System.out.println("Neighbor Counter: " + neighborCounter);

        if (mustDie) {
            //die
            System.out.println("And must die");
            super.getEnvironment().deleteEntity(this);
            EntitiesPanel.updateEntityView(this);
            super.stop();
        } else if (canBreeding && sameTypeEntityNeighborCounter > 1) {
            //multiply
            System.out.println("And must multiply");
            Point multiplyPoint = Algorithms.getRandomNeighborPoint(this.getPosition());
            for (Entity entity : entities){
                if (getEntityType() == entity.getEntityType() && Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2){
                    while (entity.getPosition().compareTo(multiplyPoint) == 0){
                        multiplyPoint = Algorithms.getRandomNeighborPoint(this.getPosition());
                    }
                }
            }
            Entity newRedEntity = new RedEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
            System.out.println("\n\n\nCREATING NEW ENTITY AT POINT: " + newRedEntity.getX() + " " + newRedEntity.getY());
            super.getEnvironment().addEntity(newRedEntity);
            EntitiesPanel.updateEntityView(newRedEntity);
            newRedEntity.start();
        }
    }

    @Override
    protected void move(Point point) {
        setPosition(point);
    }

}
