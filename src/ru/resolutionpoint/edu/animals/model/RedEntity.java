package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.List;
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
		position = new Point(x, y);
    }

	public void setPosition(Point position) {
		this.position = position;
	}

	private Point position;

	public Point getPosition(){
		return position;
	}

	public Direction getDirection(){
		return direction;
	}

    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

	public int getX(){return getPosition().x;}

	public int getY(){return getPosition().y;}

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

	public void visit() {
		System.out.println();
		System.out.println("Visited Entity: " + this.toString() + " x = " + getX() + " y = " + getY());
		//Update current entity
		animalLifeTime--;

		boolean mustDie = false;
		System.out.println("Lifetime: " + animalLifeTime);

		if (!canBreeding) breedingCounter--;

		if (breedingCounter < 0) {
			canBreeding = true;
			breedingCounter = Constants.getNoBreedingAnimalSteps();
		}

		System.out.println("Breeding counter: " + breedingCounter);

		//List of neighbor entities
		List<Entity> entities = new ArrayList<>();
		entities.addAll(getEnvironment().getEntities());

		//Remove yourself from entities/points array
		entities.remove(this);

		//Set minimal distance (initially max)
		double minimalDistance = Environment.WIDTH * Environment.HEIGHT;

		int neighborCounter = 0;

		Entity minimalDistanceEntity = this;

		//Find closest entity with same type
		for (Entity entity : entities){
			if (entity.getEntityType() == this.getEntityType() && Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) <= minimalDistance)
				minimalDistanceEntity = entity;
			if (Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) neighborCounter++;
		}

		if(neighborCounter >= Constants.getNeighboringAnimalsLimit()) mustDie = true;

		//Find delta x for new point
		int dx = Algorithms.getDeltaXfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

		//Find delta y for new point
		int dy = Algorithms.getDeltaYfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

		//Next point to target
		Point nextPoint = new Point(getX() + dx, getY() + dy);

		//If next point is busy not move
		for (Entity entity : entities){
			if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
		}

		move(nextPoint);

		if (animalLifeTime < 0) mustDie = true;

		System.out.println("Neighbor Counter: " + neighborCounter);

		if (mustDie) {
			//die
			System.out.println("And must die");
			super.getEnvironment().deleteEntity(this);
			EntitiesPanel.updateEntityView(this);
			super.stop();
		} else {
			//multiply
			System.out.println("And must multiply");
		}
	}

	//Deprecated
	/**protected Direction getEntityDirection(Entity currentEntity, int algorithm) {
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
	}*/

	@Override
	protected int getEntityType() {
		return 0;
	}

    @Override
	protected void move(Point point) {
		setPosition(point);
	}
}
