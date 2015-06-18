package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>Entity</code> represents abstract entity
 *
 * @author Denis Murashev
 */
@XmlRootElement
public abstract class Entity {
    public static double minimalDistance = Environment.WIDTH * Environment.HEIGHT;
    protected int dx;
    protected int dy;
    protected Entity minimalDistanceEntity = this;
    protected Entity neighborEntity = null;
    protected Point nextPoint;
    int neighborCounter;
    int sameTypeEntityNeighborCounter;

    public Entity(){

    }
    public Entity(Environment environment, int x, int y){
        this.environment = environment;
        this.position = new Point (x, y);
        this.mustDie = false;
    }

    //Unique entity type (must have)
    @XmlElement
    protected abstract int getEntityType(); //0 if redentity, 1 if grayentity

    //Common lifetime
    @XmlElement
    protected int getLifeTime() {return lifeTime;}
    protected void setLifeTime(int lifeTime){this.lifeTime = lifeTime;}
    private int lifeTime;

    //Common breeding counter
    @XmlElement
    protected int getBreedingTime() {return breedingTime;}
    protected void setBreedingTime(int breedingTime){this.breedingTime = breedingTime;}
    private int breedingTime;

    //Common breeding key
    @XmlElement
    protected boolean getBreeding(){return canBreeding;}
    protected void setBreeding(boolean canBreeding){this.canBreeding = canBreeding;}
    private boolean canBreeding;

    //Common life status
    @XmlElement
    protected boolean getMustDie(){return mustDie;}
    protected void setMustDie(boolean mustDie){this.mustDie = mustDie;}
    private boolean mustDie;

    //Common position
    @XmlElement
    protected Point getPosition(){return position;}
    protected void setPosition(Point position) {this.position = position;}
    private Point position;

    //Common entities
    @XmlElement
    protected List<Entity> entities;

    //Common visit method
    public void visit(){
        System.out.println("Visited " + toString());
        //Init. values: counters, entities
        initValues();

        //Update the values on this step: lifetime, breeding time
        updateValues();

        //Find next point
        do {
            if (getBreeding()){
                //Next point to same type entity
                nextPoint = getClosestNeighborPoint();
            } else {
                //Next random point
                nextPoint = Entity.getRandomNeighborPoint(getPosition());
            }
            if (neighborCounter >= Constants.getNeighboringAnimalsLimit()) mustDie = true;
        } while (checkConstraints(nextPoint));

        //Behavior of the entity in this step
        if (mustDie) {
            //die
            die();
        } else if (getBreeding() && sameTypeEntityNeighborCounter >= 1) {
            //multiply
            multiply();
        }
        //If next point is busy not move
        checkBusyPoint();
        move(nextPoint);
    }

    //Common moving method
    protected void move(Point point){
        setPosition(point);
    }

    //Common x,y getters
    @XmlElement
    public int getX(){
        return getPosition().getX();
    }
    @XmlElement
    public int getY(){
        return getPosition().getY();
    }

	private Environment environment;

    public Environment getEnvironment(){return environment;}

    //Unique abstract image path
    @XmlElement
    public abstract String getImagePath();

    //Utility methods for visit()
    private void die(){
        getEnvironment().deletedEntities.add(this);
        EntitiesPanel.deleteEntityView(this);
    }
    private void multiply(){
        Point multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
        do {
            for (Entity entity : entities) {
                if (getEntityType() == entity.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) {
                    while (entity.getPosition().compareTo(multiplyPoint) == 0) {
                        multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
                    }
                }
            }
        } while (checkConstraints(multiplyPoint));
        Entity newEntity = bornChild(multiplyPoint);
        if (neighborEntity != null) {
            neighborEntity.setBreeding(false);
        }
        setBreeding(false);
        getEnvironment().addedEntities.add(newEntity);
        EntitiesPanel.addEntityView(newEntity);
    }
    private void initValues(){
        //Set minimal distance (initially max)
        neighborCounter = 0;
        sameTypeEntityNeighborCounter = 0;

        //List of neighbor entities
        entities = new ArrayList<>();
        entities.addAll(getEnvironment().getEntities());

        //Remove yourself from entities/points array
        entities.remove(this);
    }
    private void updateValues() {
        //Update current entity
        setLifeTime(getLifeTime() - 1);
        if (getLifeTime() < 0) mustDie = true;
        if (!getBreeding()) setBreedingTime(getBreedingTime() - 1);

        if (getBreedingTime() < 0) {
            setBreeding(true);
            setBreedingTime(getNoBreedingSteps());
        }
    }
    protected void checkBusyPoint(){
        for (Entity entity : entities){
            if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
        }
    }
    protected static boolean checkConstraints(Point point){
        return point.getY() >= Environment.HEIGHT || point.getY() < 0
                || point.getX() >= Environment.WIDTH || point.getX() < 0;
    }
    private Point getClosestNeighborPoint(){
        for (Entity entity : entities){
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
        return new Point(getX() + dx, getY() + dy);
    }
    public abstract int getNoBreedingSteps();
    public abstract Entity bornChild(Point multiplyPoint);

    //Static methods
    public static double getDistanceBetweenPoints(Point a, Point b){
        return Math.sqrt( Math.pow(a.getX() - b.getX(),2) + Math.pow(a.getY() - b.getY(),2) );
    }
    public static int getDeltaXfromPoints(Point current, Point target){
        int x;
        if (current.getX() < target.getX()) x = 1;
        else if (current.getX() == target.getX()) x = 0;
        else x = -1;
        return x;
    }
    public static int getDeltaYfromPoints(Point current, Point target){
        int y;
        if (current.getY() < target.getY()) y = 1;
        else if (current.getY() == target.getY()) y = 0;
        else y = -1;
        return y;
    }
    public static Point getRandomNeighborPoint(Point current){
        int dx;
        int dy;
        Point point;
        do {
            int random = (int)(Math.random()*100)%3;
            if (random == 0){
                dx = 1;
            } else if ( random == 1) {
                dx = 0;
            } else dx = -1;
            random = (int)(Math.random()*100)%3;
            if (random == 0){
                dy = 1;
            } else if ( random == 1) {
                dy = 0;
            } else dy = -1;
            point = new Point(current.getX() + dx, current.getY() + dy);
        } while (checkConstraints(point));
        return point;
    }
}
