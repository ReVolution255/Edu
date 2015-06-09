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
		state = new boolean[4];
		state[0] = false; //mustDie
		state[1] = false; //shouldMultiply
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

		Direction multiplyDirection = Direction.NONE;

		animalLifeTime--;

		if (!canBreeding) breedingCounter--;

		if (breedingCounter < 0) {
			canBreeding = true;
			breedingCounter = Constants.getNoBreedingAnimalSteps();
		}

		if (animalLifeTime < 0) state[0] = true;

		int neighborCounter = 0;

		int[] busyDirections = {0,0,0,0,0,0,0,0,0};

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
							multiplyDirection = Algorithms.getDirectionFromInt(entity.getX(), entity.getY(), getX(), getY());
							minimalDistanceEntity = entity;
							state[1] = true;
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
			int x = getX();
			int y = getY();
			if(multiplyDirection == Direction.NORTH) y += 1;
			else if (multiplyDirection == Direction.EAST) x += 1;
			else if (multiplyDirection == Direction.WEST) x -= 1;
			else if (multiplyDirection == Direction.SOUTH) y -= 1;
			else if (multiplyDirection == Direction.NORTHEAST) {x += 1; y += 1;}
			else if (multiplyDirection == Direction.SOUTHEAST) {x += 1; y -= 1;}
			else if (multiplyDirection == Direction.SOUTHWEST) {x -= 1; y -= 1;}
			else if (multiplyDirection == Direction.NORTHWEST) {x -= 1; y += 1;}
			else { x += 1; y += 1; }
			EntitiesPanel.updateEntityView(new RedEntity(getEnvironment(),x, y));

		} else if (state[2]){
			//move
			x = minimalDistanceEntity.getX();
			y = minimalDistanceEntity.getY();
			direction = Algorithms.getDirectionFromInt(x, y, getX(), getY());
			move(direction);
		}


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

    @Override
	protected void move(Direction direction) {

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
