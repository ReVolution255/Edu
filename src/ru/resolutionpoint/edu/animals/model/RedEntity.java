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
		canBreeding = false;
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
			if (Math.abs(Math.abs(x)-Math.abs(getX())) <= 1 && Math.abs(Math.abs(y)-Math.abs(getY())) <= 1) neighborCounter++;
		}
		if (neighborCounter > 1 && canBreeding)
		{
			canBreeding = false;
			super.getEnvironment().addEntity(new RedEntity(super.getEnvironment(),getX()+1,getY()+1));
		}
		System.out.println("Entity: " + this.toString() + " Neighbors: " + neighborCounter);
		if (animalLifeTime < 0 || neighborCounter >= Constants.getNeighboringAnimalsLimit()+1) {
			System.out.println("Entity " +this.toString() +" life is ended with lifetime " + animalLifeTime +
					" and neighbor counter " + neighborCounter);
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
				if (Math.abs(x - currentX) <= 1 && Math.abs(y - currentY) <= 1){
					Direction dir = Algorithms.getDirectionFromInt(x ,y, currentX, currentY);
					if (dir != direction) break;
				}
			}
		}
		else if (algorithm == 1){
			int x;
			int y;
			int currX = currentEntity.getX();
			int currY = currentEntity.getY();
			double minimalDistance = Environment.WIDTH*Environment.HEIGHT;
			double temp;
			Entity minimalDistanceEntity = currentEntity;
			for(Entity entity : getEnvironment().getEntities()){
				if(!entity.equals(currentEntity)){
					x = entity.getX();
					y = entity.getY();
					temp = Math.sqrt(Math.pow(x-currX,2)+Math.pow(y-currY,2));
					if (temp < minimalDistance) {minimalDistance = temp; minimalDistanceEntity = entity;}
				}
			}
			x = minimalDistanceEntity.getX();
			y = minimalDistanceEntity.getY();
			direction = Algorithms.getDirectionFromInt(x, y, currX, currY);
		}
		else direction = Algorithms.getRandomDirection();
		return direction;
	}

	private void updateValues(){
		if(!canBreeding)
			breedingCounter--;
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

		int algorithm;

		Direction direction;

		if (!canBreeding) direction = getEntityDirection(this, 0);
		else direction = getEntityDirection(this, 1);

		if (breedingCounter < 0) {
			canBreeding = true;
			breedingCounter = Constants.getNoBreedingAnimalSteps();
		}

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
