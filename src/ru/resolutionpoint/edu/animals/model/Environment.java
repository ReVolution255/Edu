package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static java.lang.Thread.sleep;

/**
 * Class <code>Environment</code> is main model class
 *
 * @author Denis Murashev
 */
public class Environment extends Observable {

	public static final int WIDTH = 60;
	public static final int HEIGHT = 30;
	public boolean started = true;

	private List<Entity> entities = new ArrayList<>();

    /**
     * @return list of entities
     */
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
