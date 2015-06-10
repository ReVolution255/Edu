package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

/**
 * Class <code>Entity</code> represents abstract entity
 *
 * @author Denis Murashev
 */
public abstract class Entity implements Runnable {

    protected abstract int getEntityType(); //0 if redentity, 1 if grayentity
    protected int entityType;
    protected Point position;
    protected abstract void visit();

    public Point getPosition(){
        return position;
    }

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

	private static int TIME_DELAY = Constants.getTimeDelay();

	private Environment environment;
	private Thread thread = new Thread(this);
	private boolean moveFlag = false;

    protected Entity(Environment environment) {
		this.environment = environment;
        System.out.println("Thread "+thread.getName()+" created and ready to start");
		thread.start();
	}

    public abstract int getX();
    public abstract void setBreedingStatus(boolean status);
    public abstract void setMustDie(boolean status);
    public abstract int getY();

    public Environment getEnvironment(){return environment;}

    public abstract String getImagePath();


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
    
    protected abstract void move(Point point);
}
