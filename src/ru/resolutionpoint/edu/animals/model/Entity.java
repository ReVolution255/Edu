package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

/**
 * Class <code>Entity</code> represents abstract entity
 *
 * @author Denis Murashev
 */
public abstract class Entity implements Runnable {

    private static int TIME_DELAY = Constants.getTimeDelay();

    public Entity(Environment environment, int x, int y){
        this.environment = environment;
        this.position = new Point (x, y);
    }

    protected abstract int getEntityType(); //0 if redentity, 1 if grayentity

    protected int entityType;

    //Common lifetime
    public int getLifeTime() {
        return lifeTime;
    }
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    private int lifeTime;

    //Common breeding counter
    public int getBreedingTime() {
        return breedingTime;
    }
    public void setBreedingTime(int breedingTime) {
        this.breedingTime = breedingTime;
    }
    private int breedingTime;

    //Common breeding key
    public boolean getBreeding(){return canBreeding;}
    public void setBreeding(boolean canBreeding){this.canBreeding = canBreeding;}
    private boolean canBreeding;

    //Common life status
    public boolean getMustDie(){return mustDie;}
    public void setMustDie(boolean mustDie){this.mustDie = mustDie;}
    private boolean mustDie;

    //Common position
    public Point getPosition(){
        return position;
    }
    public void setPosition(Point position) {this.position = position;}
    private Point position;

    //Common visit method
    protected abstract void visit();

    //Common moving method
    protected void move(Point point){
        setPosition(point);
    }

    //Common getters
    public abstract int getX();
    public abstract int getY();

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

    //Unique abstract imagepath
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
