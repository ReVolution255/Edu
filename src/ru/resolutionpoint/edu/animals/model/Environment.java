package ru.resolutionpoint.edu.animals.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.xml.bind.annotation.*;

import static java.lang.Thread.sleep;

/**
 * Class <code>Environment</code> is main model class
 *
 * @author Denis Murashev
 */
@XmlRootElement
public class Environment extends Observable implements Runnable {

	@XmlElement
	public static final int WIDTH = 60;
	@XmlElement
	public static final int HEIGHT = 30;
	@XmlElement
	boolean moveFlag = false;

	private List<Entity> entities = new ArrayList<>();
	public List<Entity> deletedEntities = new ArrayList<>();
	public List<Entity> addedEntities = new ArrayList<>();

	Thread thread;

	public Environment(){
		thread = new Thread(this);
		thread.start();
	}
	@XmlElements({
			@XmlElement(name="redentity",type=RedEntity.class),
			@XmlElement(name="grayentity",type=GrayEntity.class)
	})

    /**
     * @return list of entities
     */
	@XmlElementWrapper
    public synchronized List<Entity> getEntities() {
		return entities;
	}

    /**
     * Adds new entity
     *
     * @param entity entity to be added
     */
    public void addEntity(Entity entity) {
		entities.add(entity);
	}

	//normal
    /**
     * Starts entities movement
     */
    public synchronized void start() {
		moveFlag = true;
		notify();
	}

	//normal
    /**
     * Stops entities movement
     */
	public void stop() {
		moveFlag = false;
	}

	//normal
    /**
     * Called when environment status changed
     */
    public void change() {
		setChanged();
		notifyObservers();
	}

	@Override
	public void run() {
		while (true){
			if (moveFlag) {
				for (Entity entity : entities) {
					entity.visit(this);
					change();
				}
				if (addedEntities.size() != 0 || deletedEntities.size() != 0) {
					entities.addAll(addedEntities);
					entities.removeAll(deletedEntities);
					addedEntities.clear();
					deletedEntities.clear();
					change();
				}
			}
			try {
				sleep(Constants.getTimeDelay());
				synchronized (this){
					if (!moveFlag)
						wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
