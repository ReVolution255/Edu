package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class RedEntity extends Animal implements Runnable {

    public RedEntity(Environment environment, int x, int y) {
        super(environment, x, y);
		setBreeding(false);
		setMustDie(false);
    }

    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

	//Thread-management
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
	public void visit() {
		super.visit();
		//Init. values

		//List of neighbor entities
		List<Entity> entities = new ArrayList<>();
		entities.addAll(getEnvironment().getEntities());
		//Remove yourself from entities/points array
		entities.remove(this);

		//Set minimal distance (initially max)
		int neighborCounter = 0;
		int sameTypeEntityNeighborCounter = 0;

		boolean mustDie = false;

		//Update values

		//Update current entity
		if (getMustDie()) mustDie = true;
		if (getBreedingTime() < 0) {
			setBreeding(true);
			setBreedingTime(Constants.getNoBreedingAnimalSteps());
		}


		//Find closest entity with same type
		do {
			if (getBreeding()){
				for (Entity entity : entities){
					if (entity.getEntityType() == this.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) <= minimalDistance)
						minimalDistanceEntity = entity;
					if (Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2) {
						neighborCounter++;
						if (entity.getEntityType() == getEntityType()) {
							sameTypeEntityNeighborCounter++;
							neighborEntity = entity;
						}
					}
				}
				//Find delta x for new point
				dx = Entity.getDeltaXfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

				//Find delta y for new point
				dy = Entity.getDeltaYfromPoints(this.getPosition(), minimalDistanceEntity.getPosition());

				//Next point to target
				nextPoint = new Point(getX() + dx, getY() + dy);
			} else {
				nextPoint = Entity.getRandomNeighborPoint(getPosition());
			}

			if(neighborCounter >= Constants.getNeighboringAnimalsLimit()) mustDie = true;


			//If next point is busy not move
			for (Entity entity : entities){
				if (entity.getPosition().compareTo(nextPoint) == 0) nextPoint = getPosition();
			}
		} while (nextPoint.getY() >= Environment.HEIGHT || nextPoint.getY() <= 0
				|| nextPoint.getX() >= Environment.WIDTH || nextPoint.getX() <= 0);

		if (super.getLifeTime() < 0) mustDie = true;

		if (mustDie) {
			//die
			super.getEnvironment().deleteEntity(this);
			EntitiesPanel.updateEntityView(this);
			super.stop();
		} else if (getBreeding() && sameTypeEntityNeighborCounter >= 1) {
			//multiply
			Point multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
			for (Entity entity : entities){
				if (getEntityType() == entity.getEntityType() && Entity.getDistanceBetweenPoints(this.getPosition(), entity.getPosition()) < 2){
					while (entity.getPosition().compareTo(multiplyPoint) == 0){
						multiplyPoint = Entity.getRandomNeighborPoint(this.getPosition());
					}
				}
			}
			Entity newRedEntity = new RedEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
			if (neighborEntity != null) {
				neighborEntity.setBreeding(false);
			}
			setBreeding(false);
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
	public int getEntityType() {
		return 0;
	}
}
