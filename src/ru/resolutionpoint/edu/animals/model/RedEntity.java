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

	private Point position;

	private boolean mustDie = false;

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition(){
		return position;
	}

    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

	public int getX(){return getPosition().x;}

	public int getY(){return getPosition().y;}

	public void setMustDie(boolean status){
		this.mustDie = status;
	}

	@Override
	public void run() {super.run();
	}

	public void setBreedingStatus(boolean status){
		this.canBreeding = status;
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

	public void visit() {
		System.out.println();
		System.out.println("Visited Entity: " + this.toString() + " x = " + getX() + " y = " + getY());
		//Update current entity
		animalLifeTime--;

		boolean mustDie = false;

		if (this.mustDie) mustDie = true;
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
		int sameTypeEntityNeighborCounter = 0;

		Entity minimalDistanceEntity = this;
		Entity neighborEntity = null;

		int dx;
		int dy;
		Point nextPoint;
		//Find closest entity with same type
		do {
			if (canBreeding){
				for (Entity entity : entities){
					if (entity.getEntityType() == this.getEntityType() && Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) <= minimalDistance)
						minimalDistanceEntity = entity;
					if (Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) {
						neighborCounter++;
						if (entity.getEntityType() == getEntityType()) {
							sameTypeEntityNeighborCounter++;
							neighborEntity = entity;
						}
					}
				}
				//Find delta x for new point
				dx = Algorithms.getDeltaXfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

				//Find delta y for new point
				dy = Algorithms.getDeltaYfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

				//Next point to target
				nextPoint = new Point(getX() + dx, getY() + dy);
			} else {
				nextPoint = Algorithms.getRandomNeighborPoint(getPosition());
			}

			if(neighborCounter >= Constants.getNeighboringAnimalsLimit()) mustDie = true;


			//If next point is busy not move
			for (Entity entity : entities){
				if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
			}
		} while (nextPoint.getY() >= Environment.HEIGHT || nextPoint.getY() <= 0
				|| nextPoint.getX() >= Environment.WIDTH || nextPoint.getX() <= 0);

		//Uncomment and remove while-cycle if need fast job of algorithm

/*		if (nextPoint.getY() >= Environment.HEIGHT)
			nextPoint.setY(position.getY());
		else if (nextPoint.getY() <= 0)
			nextPoint.setY(position.getY());
		else {
			// do nothing
		}
		if (nextPoint.getX() >= Environment.WIDTH)
			nextPoint.setX(position.getX());
		else if (nextPoint.getX() <= 0)
			nextPoint.setX(position.getX());
		else {
			// do nothing
		}*/

		if (animalLifeTime < 0) mustDie = true;

		System.out.println("Neighbor Counter: " + neighborCounter);

		if (mustDie) {
			//die
			System.out.println("And must die");
			super.getEnvironment().deleteEntity(this);
			EntitiesPanel.updateEntityView(this);
			super.stop();
		} else if (canBreeding && sameTypeEntityNeighborCounter >= 1) {
			//multiply
			System.out.println("And must multiply");
			Point multiplyPoint = Algorithms.getRandomNeighborPoint(this.getPosition());
			for (Entity entity : entities){
				if (getEntityType() == entity.getEntityType() && Algorithms.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2){
					while (entity.getPosition().compareTo(multiplyPoint) == 0){
						multiplyPoint = Algorithms.getRandomNeighborPoint(this.getPosition());
					}
				}
			}
			Entity newRedEntity = new RedEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
			System.out.println("\n\n\nCREATING NEW ENTITY AT POINT: " + newRedEntity.getX() + " " + newRedEntity.getY());
			if (neighborEntity != null) {
				neighborEntity.setBreedingStatus(false);
			}
			setBreedingStatus(false);
			super.getEnvironment().addEntity(newRedEntity);
			EntitiesPanel.updateEntityView(newRedEntity);
			newRedEntity.start();
		}
		//If next point is busy not move
		for (Entity entity : entities){
			if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
		}
		move(nextPoint);
	}

	@Override
	protected int getEntityType() {
		return 0;
	}

    @Override
	protected void move(Point point) {
		setPosition(point);
	}
}
