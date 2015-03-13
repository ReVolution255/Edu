package ru.resolutionpoint.edu.animals.model;

/**
 * Class <code>Entity</code> represents abstract entity
 *
 * @author Denis Murashev
 */
public abstract class Entity implements Runnable {

	private static final int TIME_DELAY = 200;
	
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
        notify();     
    }
    
    /**
     * Stop moving
     */
    public void stop() {
        moveFlag = false;     
    }

    protected boolean checkHorizontal(int x) {
    	return x >= 0 && x < Environment.WIDTH;
    }
    
    protected boolean checkVertical(int y) {
		return y >= 0 && y < Environment.HEIGHT;
	}
    
    protected abstract void move();
}
