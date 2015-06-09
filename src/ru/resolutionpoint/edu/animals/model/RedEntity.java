package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.TreeSet;


public class RedEntity extends Animal implements Runnable, Comparable<RedEntity> {

    public RedEntity(Environment environment) {
		super(environment);
	}

    public RedEntity(Environment environment, int x, int y) {
        super(environment, x, y);
		animalLifeTime = Constants.getAnimalLifeTime();
		overCrowdingCounter = Constants.getNeighboringAnimalsLimit();
		breedingCounter = Constants.getNoBreedingAnimalSteps();
		canBreeding = false;
		this.x = x;
		this.y = y;
		state[0] = false; //mustDie
		state[1] = true; //shouldMultiply
		state[2] = true; //mustMove
		state[3] = false; //mustHunt
    }

	private boolean[] state;

	public boolean[] getState(){
		return state;
	}

    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

	public int getX(){return x;}

	public int getY(){return y;}

	@Override
	public void run() {super.run();
	}

	@Override
	public synchronized void start() {super.start();
	}

	@Override
	public synchronized void stop() {super.stop();
	}

	@Override
	public int compareTo(RedEntity o) {
		if(this.equals(o))
			return 0;
		else return 1;
	}



	Direction direction;

	public void visit(){

		animalLifeTime--;

		if (!canBreeding) breedingCounter--;

		if (breedingCounter < 0) {
			canBreeding = true;
			breedingCounter = Constants.getNoBreedingAnimalSteps();
		}

		if (animalLifeTime < 0) state[0] = true;

		if (canBreeding) state[1] = true;

		int neighborCounter = 0;

		double minimalDistance = Environment.WIDTH * Environment.HEIGHT;
		double temp;
		Entity minimalDistanceEntity = this;

		for (Entity entity : getEnvironment().getEntities()){
			int x = entity.getX();
			int y = entity.getY();
			int entityType = entity.getEntityType();
			boolean[] entityState = getState();

			boolean isNeighbor = false;
			if (Math.abs(Math.abs(x)-Math.abs(getX())) <= 1 && Math.abs(Math.abs(y)-Math.abs(getY())) <= 1) {
				isNeighbor = true;
				neighborCounter++;
			} else {
				temp = Math.sqrt(Math.pow(x-getX(),2)+Math.pow(y-getY(),2));
				if (temp < minimalDistance) {minimalDistance = temp; minimalDistanceEntity = entity;}
			}

			if (neighborCounter >= Constants.getNeighboringAnimalsLimit()+1) {
				state[0] = true;
			}

			if (canBreeding) {
				if (entityType == getEntityType() && entityState[1]) {
					if (isNeighbor){
							//TODO create entity here
							state[1] = false;
					}
					else {
						state[2] = true;
					}
				}
			}

			}


		if (state[0]) {
			//die
			super.getEnvironment().deleteEntity(this);
			EntitiesPanel.updateEntityView(this);
			super.stop();
		} else if (state[1]){
			//multiply
			direction = Algorithms.getRandomDirection();
		} else if (state[2]){
			//move
			x = minimalDistanceEntity.getX();
			y = minimalDistanceEntity.getY();
			direction = Algorithms.getDirectionFromInt(x, y, getX(), getY());
		}


	}

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
			//super.getEnvironment().addEntity(new RedEntity(super.getEnvironment(),getX()+1,getY()+1));
		}
		System.out.println("Entity: " + this.toString() + " Neighbors: " + neighborCounter);
		if (animalLifeTime < 0 || neighborCounter >= Constants.getNeighboringAnimalsLimit()+1) {
			System.out.println("Entity " +this.toString() +" life is ended with lifetime " + animalLifeTime +
					" and neighbor counter " + neighborCounter);
			super.getEnvironment().deleteEntity(this);
			EntitiesPanel.updateEntityView(this);
			super.stop();}
	}

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
				if(!entity.equals(currentEntity) && entity.getEntityType() == 0){
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

	@Override
	protected int getEntityType() {
		return 0;
	}

	private void updateValues(){
		if(!canBreeding)
			breedingCounter--;
		animalLifeTime--;
	}

    @Override
	protected void move(Direction direction) {
		updateValues();
		checkValues();

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
}
