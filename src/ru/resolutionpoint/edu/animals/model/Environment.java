package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class <code>Environment</code> is main model class
 *
 * @author Denis Murashev
 */
public class Environment extends Observable {

	public final Object monitor = new Object();

	public static final int WIDTH = 30;
	public static final int HEIGHT = 20;

	private List<Entity> entities = new ArrayList<>();

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

	//normal
    /**
     * Starts entities movement
     */
    public void start() {
		for (Entity entity : entities) {
			entity.start();
		}
	}

	//normal
    /**
     * Stops entities movement
     */
	public void stop() {
		for (Entity entity : entities) {
			//System.out.println("Entity " + entity.toString() + " movement stopped");
			entity.stop();
		}
	}

	//normal
    /**
     * Called when environment status changed
     */
    public void change() {
		setChanged();
		notifyObservers();
	}
}