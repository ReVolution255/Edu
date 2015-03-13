package ru.resolutionpoint.edu.animals.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class <code>Environment</code> is main model class
 *
 * @author Denis Murashev
 */
public class Environment extends Observable {

	public static final int WIDTH = 30;
	public static final int HEIGHT = 20;

	private List<Entity> entities = new ArrayList<Entity>();

    /**
     * @return list of entities
     */
    public List<Entity> getEntities() {
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

    /**
     * Deletes entity
     *
     * @param entity entity to be deleted
     */
    public void deleteEntity(Entity entity) {
		entities.remove(entity);
	}

    /**
     * Sets list of entities
     *
     * @param entities new list of entities
     */
    public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

    /**
     * Starts entities movement
     */
    public void start() {
		for (Entity entity : entities) {
			entity.start();
		}
	}
	
    /**
     * Stops entities movement
     */
	public void stop() {
		for (Entity entity : entities) {
			entity.stop();
		}
	}

    /**
     * Called when environment status changed
     */
    public void change() {
		setChanged();
		notifyObservers();
	}
}
