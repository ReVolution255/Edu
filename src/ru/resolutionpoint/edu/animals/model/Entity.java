package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

/**
 * Class <code>Entity</code> represents abstract entity
 *
 * @author Denis Murashev
 */
public abstract class Entity implements Runnable {

    //protected abstract Direction getEntityDirection(Entity entity, int algorithm);
    protected abstract int getEntityType(); //0 if redentity, 1 if grayentity
    protected int entityType;
    protected abstract void visit();

	private static int TIME_DELAY = Constants.getTimeDelay();
    private int movingAlgorithm = 0;

    /*
    Directions enumeration with such mapping:
                NORTH
        NORTHWEST   NORTHEAST
    WEST                    EAST
        SOUTHWEST   SOUTHEAST
                SOUTH
     */
    public enum Direction {NORTH, NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST, NONE}

	private Environment environment;
	private Thread thread = new Thread(this);
	private boolean moveFlag = false;

    protected Entity(Environment environment) {
		this.environment = environment;
        System.out.println("Thread "+thread.getName()+" created and ready to start");
		thread.start();
	}

    public abstract int getX();

    public abstract int getY();

    public Environment getEnvironment(){return environment;}

    public abstract String getImagePath();

    public abstract int getNextX();

    public abstract int getNextY();

    public abstract void setNextX(int nextX);

    public abstract void setNextY(int nextY);

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

    protected boolean checkHorizontal(int x) {
        return x >= 0 && x < Environment.WIDTH;
    }

    protected boolean checkVertical(int y) {
		return y >= 0 && y < Environment.HEIGHT;
	}
    
    protected abstract void move(Direction direction);
}
