package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>Entity</code> represents abstract entity
 *
 * @author Denis Murashev
 */
public abstract class Entity implements Runnable {

    private static int TIME_DELAY = Constants.getTimeDelay();
    public static double minimalDistance = Environment.WIDTH * Environment.HEIGHT;
    protected int dx;
    protected int dy;
    protected Entity minimalDistanceEntity = this;
    protected Entity neighborEntity = null;
    protected Point nextPoint;

    public Entity(Environment environment, int x, int y){
        this.environment = environment;
        this.position = new Point (x, y);
        thread.start();
    }

    //Unique entity type (must have)
    protected abstract int getEntityType(); //0 if redentity, 1 if grayentity

    //Common lifetime
    protected int getLifeTime() {
        return lifeTime;
    }
    protected void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    private int lifeTime;

    //Common breeding counter
    protected int getBreedingTime() {
        return breedingTime;
    }
    protected void setBreedingTime(int breedingTime) {
        this.breedingTime = breedingTime;
    }
    private int breedingTime;

    //Common breeding key
    protected boolean getBreeding(){return canBreeding;}
    protected void setBreeding(boolean canBreeding){this.canBreeding = canBreeding;}
    private boolean canBreeding;

    //Common life status
    protected boolean getMustDie(){return mustDie;}
    protected void setMustDie(boolean mustDie){this.mustDie = mustDie;}
    private boolean mustDie;

    //Common position
    protected Point getPosition(){
        return position;
    }
    protected void setPosition(Point position) {this.position = position;}
    private Point position;

    //Common entities
    protected List<Entity> entities;

    //Common visit method
    public void visit(){
        //Init. values

        //Set minimal distance (initially max)
        int neighborCounter = 0;
        int sameTypeEntityNeighborCounter = 0;

        //List of neighbor entities
        entities = new ArrayList<>();
        entities.addAll(getEnvironment().getEntities());

        //Remove yourself from entities/points array
        entities.remove(this);

        //Update current entity
        setLifeTime(getLifeTime() - 1);
        if (!getBreeding()) setBreedingTime(getBreedingTime() - 1);

        boolean mustDie = false;

        //Update values

        //Update current entity
        if (getMustDie()) mustDie = true;
        if (getBreedingTime() < 0) {
            setBreeding(true);
            setBreedingTime(getNoBreedingSteps());
        }


        //Find closest entity with same type
        do {
            if (getBreeding()){
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
                nextPoint = new Point(getX() + dx, getY() + dy);
            } else {
                nextPoint = Entity.getRandomNeighborPoint(getPosition());
            }

            if(neighborCounter >= Constants.getNeighboringAnimalsLimit()) mustDie = true;


            //If next point is busy not move
            for (Entity entity : entities){
                if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
            }
        } while (nextPoint.getY() >= Environment.HEIGHT || nextPoint.getY() <= 0
                || nextPoint.getX() >= Environment.WIDTH || nextPoint.getX() <= 0);

        if (getLifeTime() < 0) mustDie = true;

        if (mustDie) {
            //die
            getEnvironment().deleteEntity(this);
            EntitiesPanel.deleteEntityView(this);
            stop();
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
            Entity newEntity = bornChild(multiplyPoint);
            if (neighborEntity != null) {
                neighborEntity.setBreeding(false);
            }
            setBreeding(false);
            getEnvironment().addEntity(newEntity);
            EntitiesPanel.addEntityView(newEntity);
            newEntity.start();
        }
        //If next point is busy not move
        for (Entity entity : entities){
            if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
        }
        move(nextPoint);
    }

    //Common moving method
    protected void move(Point point){
        setPosition(point);
    }

    //Common x,y getters
    public int getX(){
        return getPosition().getX();
    }
    public int getY(){
        return getPosition().getY();
    }

	private Environment environment;

    //Multi-threading
	private Thread thread = new Thread(this);
	private boolean moveFlag = false;

    protected Entity(Environment environment) {
		this.environment = environment;
        System.out.println("Thread "+thread.getName()+" created and ready to start");
		thread.start();
	}

    public Environment getEnvironment(){return environment;}

    //Unique abstract image path
    public abstract String getImagePath();

    //Thread management
    @Override
	public void run() {
        while (true) {
            try {
                Thread.sleep(TIME_DELAY);
                synchronized (this) {
                    if (!moveFlag) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
            	// Nothing to do
            }
            if (moveFlag) {
                visit();
                environment.change();
            }
        }     
	}
    public synchronized void start() {
        moveFlag = true;
        System.out.println("Thread "+thread.getName()+" started");
        notify();     
    }
    public void stop() {
        moveFlag = false;
        //System.out.println("Thread "+thread.getName()+" stopped");
    }

    public abstract int getNoBreedingSteps();
    public abstract Entity bornChild(Point multiplyPont);
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
        int random = (int)(Math.random()*100)%3;
        if (random == 0){
            dx = 1;
        } else if ( random == 1) {
            dx = 0;
        } else dx = -1;
        int dy;
        random = (int)(Math.random()*100)%3;
        if (random == 0){
            dy = 1;
        } else if ( random == 1) {
            dy = 0;
        } else dy = -1;
        return new Point(current.getX() + dx,current.getY() + dy);
    }
}
