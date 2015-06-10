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
    protected Point position;
    protected abstract void visit();

    public Point getPosition(){
        return position;
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
