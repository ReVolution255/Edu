package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.TreeSet;


public class RedEntity extends Animal implements Runnable, Comparable<RedEntity> {

    public RedEntity(Environment environment) {
		super(environment);
	}
	//normal
    public RedEntity(Environment environment, int x, int y) {
        super(environment, x, y);
		animalLifeTime = Constants.getAnimalLifeTime();
		overCrowdingCounter = Constants.getNeighboringAnimalsLimit();
		breedingCounter = Constants.getNoBreedingAnimalSteps();
		canBreeding = true;
		this.x = x;
		this.y = y;
    }

	//normal
    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

	//normal
	private void checkValues(){
		int neighborCounter = 0;
		for (Entity entity : getEnvironment().getEntities()){
			int x = entity.getX();
			int y = entity.getY();
			if (Math.abs(x-getX()) <= 1 || Math.abs(y-getY()) <= 1) neighborCounter++;
		}
		if(animalLifeTime < 0 || neighborCounter >= Constants.getNeighboringAnimalsLimit()) {
			super.getEnvironment().deleteEntity(this);
			EntitiesPanel.updateEntityView(super.getEnvironment());
			super.stop();}
	}

	/**
	 * @param currentEntity current entity
	 * @param algorithm 0 for random, 1 for breeding
	 * @return
	 */
	@Override
	protected Direction getEntityDirection(Entity currentEntity, int algorithm) {
		//Direction, that must be returned
		Direction direction;
		Environment environment = getEnvironment();
		int currentX = currentEntity.getX();
		int currentY = currentEntity.getY();
		if (algorithm == 0) {
			direction = Algorithms.getRandomDirection();
			for (Entity entity : environment.getEntities()) {
				int x = entity.getX();
				int y = entity.getY();
				if (Math.abs(x - currentX) <= 1 & Math.abs(y - currentY) <= 1){
					Direction dir = Algorithms.getDirectionFromInt(x ,y, currentX, currentY);
					if (dir != direction) break;
				}
			}
		}
		else if (algorithm == 1){
			direction = Algorithms.getRandomDirection();
		}
		else direction = Algorithms.getRandomDirection();
		return direction;
	}

	private void updateValues(){
		if(!canBreeding)breedingCounter--;{
		//do nothing
		}
		animalLifeTime--;
	}

	//normal
	/**
	 * @return x-coordinate
	 */
	public int getX(){return x;}

	//normal
	/**
	 * @return y-coordinate
	 */
	public int getY(){return y;}

    @Override
	protected void move() {
		updateValues();
		checkValues();
		Direction direction = getEntityDirection(this, 0);
		if(direction == Direction.NORTH) {
			y -= checkVertical(y -= dy) ? dy : 0;
		}
		else if (direction == Direction.EAST) {
			x -= checkHorizontal(x -= dx) ? dx : 0;
		}
		else if (direction == Direction.WEST) {
			x += checkHorizontal(x += dx) ? dx : 0;
		}
		else if (direction == Direction.SOUTH){
			y += checkVertical(y += dy) ? dy : 0;
		}
		else if (direction == Direction.NORTHWEST) {
			y -= checkVertical(y -= dy) ? dy : 0;
			x += checkHorizontal(x += dx) ? dx : 0;
		}
		else if (direction == Direction.NORTHEAST) {
			y -= checkVertical(y -= dy) ? dy : 0;
			x -= checkHorizontal(x -= dx) ? dx : 0;
		}
		else if (direction == Direction.SOUTHWEST){
			y += checkVertical(y += dy) ? dy : 0;
			x += checkHorizontal(x += dx) ? dx : 0;
		}
		else if (direction == Direction.SOUTHEAST){
			y += checkVertical(y += dy) ? dy : 0;
			x -= checkHorizontal(x -= dx) ? dx : 0;
		}
		else if (direction == Direction.NONE){
		}
	}

	//normal
	@Override
	public void run() {super.run();
	}

	//normal
	@Override
	public synchronized void start() {super.start();
	}

	//normal
	@Override
	public synchronized void stop() {super.stop();
	}

	//normal
	@Override
	public int compareTo(RedEntity o) {
		if(this.equals(o))
		return 0;
		else return 1;
	}
}
