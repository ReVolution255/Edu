package ru.resolutionpoint.edu.animals.model;

/**
 * Class <code>Entity</code> represents abstract entity
 *
 * @author Denis Murashev
 */
public abstract class Entity implements Runnable {

    protected abstract Direction getEntityDirection(Entity entity, int alogirthm);

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


    /**
     * Initializes entity. Starts entity's thread
     *
     * @param environment environment
     */
    protected Entity(Environment environment) {
		this.environment = environment;
        System.out.println("Thread "+thread.getName()+" created and ready to start");
		thread.start();
	}

    /**
     * @return x-coordinate
     */
    public abstract int getX();

    /**
     * @return y-coordinate
     */
    public abstract int getY();

    public int getMovingAlgorithm() {
        return movingAlgorithm;
    }

    public void setMovingAlgorithm(int movingAlgorithm) {
        this.movingAlgorithm = movingAlgorithm;
    }

    public Environment getEnvironment(){return environment;}

    /**
     * @return path to image file
     */
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
                move();
                environment.change();
            }
        }     
	}

    /**
     * Start moving
     */
    public synchronized void start() {
        moveFlag = true;
        System.out.println("Thread "+thread.getName()+" started");
        notify();     
    }
    
    /**
     * Stop moving
     */
    public void stop() {
        moveFlag = false;
        //System.out.println("Thread "+thread.getName()+" stopped");
    }

    //normal
    protected boolean checkHorizontal(int x) {
        return x >= 0 && x < Environment.WIDTH;
    }

    //normal
    protected boolean checkVertical(int y) {
		return y >= 0 && y < Environment.HEIGHT;
	}
    
    protected abstract void move();
}
