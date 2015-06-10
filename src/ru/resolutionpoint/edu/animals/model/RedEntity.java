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
		this.position.x = x;
		this.position.y = y;
		state = new boolean[4];
		state[0] = false; //mustDie
		state[1] = false; //shouldMultiply
		state[2] = true; //mustMove
		state[3] = false; //mustHunt
    }

	private Point position;

	public Point getPosition(){
		return position;
	}

	public int getNextX() {
		return nextX;
	}

	public int getNextY() {
		return nextY;
	}

	public Direction getDirection(){
		return direction;
	}

	public void setNextX(int nextX) {
		this.nextX = nextX;
	}

	public void setNextY(int nextY) {
		this.nextY = nextY;
	}

	private int nextX;

	private int nextY;

	private boolean[] state;

	public boolean[] getState(){
		return state;
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

	private int x;
	private int y;

	@Override
	public int compareTo(RedEntity o) {
		if(this.equals(o))
			return 0;
		else return 1;
	}

	List<Direction> busyDirections = new ArrayList<>();

	Direction direction;

	public void visit() {
		System.out.println();
		System.out.println("Visited Entity: " + this.toString() + " x = " + getX() + " y = " + getY());

		Direction multiplyDirection = Direction.NORTH;

		animalLifeTime--;
		System.out.println("Lifetime: " + animalLifeTime);
		if (!canBreeding) breedingCounter--;

		if (breedingCounter < 0) {
			canBreeding = true;
			breedingCounter = Constants.getNoBreedingAnimalSteps();
		}
		System.out.println("Breeding counter: " + breedingCounter);
		if (animalLifeTime < 0) state[0] = true;

		int neighborCounter = 0;


		double minimalDistance = Environment.WIDTH * Environment.HEIGHT;
		double temp;
		Entity minimalDistanceEntity = this;
		boolean first = true;

		for (Entity entity : getEnvironment().getEntities()){
			int entityType = entity.getEntityType();
			if (first && !this.equals(entity) && entityType == getEntityType()) {minimalDistanceEntity = entity; first = false;}
			int x = entity.getX();
			int y = entity.getY();
			boolean[] entityState = getState();

			boolean isNeighbor = false;
			if (entityType == getEntityType() && entityState[1] || (Math.abs(Math.abs(x) - Math.abs(getX())) <= 1 && Math.abs(Math.abs(y) - Math.abs(getY())) <= 1)) {
				isNeighbor = true;
				neighborCounter++;
			} else {
				temp = Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
				if (temp < minimalDistance) {
					minimalDistance = temp;
					minimalDistanceEntity = entity;
				}
			}
			if (neighborCounter >= Constants.getNeighboringAnimalsLimit() + 1) {
				state[0] = true;
			}

			if (canBreeding) {
				if (entityType == getEntityType() && entityState[1]) {
					if (isNeighbor){
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

		System.out.println("Neighbor Counter: " + neighborCounter);

		if (state[0]) {
			//die
			System.out.println("And must die");
			super.getEnvironment().deleteEntity(this);
			EntitiesPanel.updateEntityView(this);
			super.stop();
		} else if (state[1]){
			//multiply
			System.out.println("And must multiply");
			int x = getX();
			int y = getY();
			if (multiplyDirection == Direction.NORTH) y += 1;
			else if (multiplyDirection == Direction.EAST) x += 1;
			else if (multiplyDirection == Direction.WEST) x -= 1;
			else if (multiplyDirection == Direction.SOUTH) y -= 1;
			else if (multiplyDirection == Direction.NORTHEAST) {x += 1; y += 1;}
			else if (multiplyDirection == Direction.SOUTHEAST) {x += 1; y -= 1;}
			else if (multiplyDirection == Direction.SOUTHWEST) {x -= 1; y -= 1;}
			else if (multiplyDirection == Direction.NORTHWEST) {x -= 1; y += 1;}
			else { x += 1; y += 1; }
			System.out.println("Multiply direction: " + multiplyDirection);
			EntitiesPanel.updateEntityView(new RedEntity(getEnvironment(),x, y));

		} else if (state[2]){
			//move
			int x = minimalDistanceEntity.getX();
			int y = minimalDistanceEntity.getY();
			direction = Algorithms.getDirectionFromInt(x, y, getX(), getY());
			for (Entity entity : getEnvironment().getEntities()){
				if(!this.equals(entity)){
					int temp_x = getX() + Algorithms.getDeltaXfromDirection(getDirection());
					int temp_y = getY() + Algorithms.getDeltaYfromDirection(getDirection());
					int _x = entity.getX() + Algorithms.getDeltaXfromDirection(entity.getDirection());
					int _y = entity.getY() + Algorithms.getDeltaYfromDirection(entity.getDirection());
					if (_x == temp_x && _y == temp_y) direction = Direction.NONE;
				}
			}
			System.out.println("And must move to " + minimalDistanceEntity.toString() + ", to direction ");
			System.out.print(direction.toString() + " direction and x = " + x + " y = " + y);
			setNextX(Algorithms.getDeltaXfromDirection(direction));
			setNextY(Algorithms.getDeltaYfromDirection(direction));
			move(direction);
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
	protected void move(Direction direction) {


		System.out.println("\nX current: " + (getX() + Algorithms.getDeltaXfromDirection(direction)));
		System.out.println("\nY current: " + (getY() + Algorithms.getDeltaYfromDirection(direction)));
		/*for (Entity entity : getEnvironment().getEntities()){
			System.out.println("X: " + (entity.getNextX() + entity.getX()));
			System.out.println("Y: " + (entity.getNextY() + entity.getY()));
			if (getX() + Algorithms.getDeltaXfromDirection(direction) == entity.getNextX() + entity.getX() &&
					getY() + Algorithms.getDeltaYfromDirection(direction) == entity.getNextY() + entity.getY()){
				direction = Direction.NONE;
			}
		}*/

		//System.out.println();

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
