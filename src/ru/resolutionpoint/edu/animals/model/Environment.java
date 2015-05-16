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
		System.out.println("Entity " + entity.toString() + " added");
	}

	public void addEntity(RedEntity entity) {
		RedEntity.redEntities.add(entity);
		entities.add(entity);
		System.out.println("RedEntity " + entity.toString() + " added"+" |||||||| Now entities: "+entities.size());
	}

	public void addEntity(GrayEntity entity) {
		GrayEntity.grayEntities.add(entity);
		entities.add(entity);
		System.out.println("GrayEntity " + entity.toString() + " added");
	}

    /**
     * Deletes entity
     *
     * @param entity entity to be deleted
     */
    public void deleteEntity(Entity entity) {
		System.out.println("Entity "+ entity.toString()+" removed");
		System.out.println("Entity list before " + entities.toString());
		entities.remove(entity);
		System.out.println("Entity list after " + entities.toString());
	}

	public void deleteEntity(RedEntity entity) {
		deleteEntity((Entity) entity);
		RedEntity.redEntities.remove(entity);
	}

	public void deleteEntity(GrayEntity entity) {
		deleteEntity((Entity) entity);
		GrayEntity.grayEntities.remove(entity);
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
			System.out.println("Entity " + entity.toString() + " movement started");
			entity.start();
		}
	}
	
    /**
     * Stops entities movement
     */
	public void stop() {
		for (Entity entity : entities) {
			//System.out.println("Entity " + entity.toString() + " movement stopped");
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
