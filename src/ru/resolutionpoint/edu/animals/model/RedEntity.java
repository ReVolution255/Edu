package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

import java.util.ArrayList;
import java.util.TreeSet;


public class RedEntity extends Animal implements Runnable, Comparable<RedEntity> {

	public static ArrayList<RedEntity> redEntities = new ArrayList<>();
	public TreeSet<RedEntity> neighbors = new TreeSet<>();
	public static final Object monitor = new Object();

    public RedEntity(Environment environment) {super(environment);
	}

    public RedEntity(Environment environment, int x, int y) {
        super(environment, x, y);
		animalLifeTime = Constants.getAnimalLifeTime();
		overCrowdingCounter = Constants.getNeighboringAnimalsLimit();
		breedingCounter = Constants.getNoBreedingAnimalSteps();
		canBreeding = true;
		isDead = false;
		this.x = x;
		this.y = y;
    }

    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

	private synchronized RedEntity bornRedEntity(RedEntity entity){
		int newX;
		int newY;
		int x = entity.getX();
		int y = entity.getY();
		RedEntity newEntity;
		Direction d = Algorithms.getRandomFreeDirection(redEntities, entity);
		switch (d){
			case NORTH:newX=x;newY=y-1;break;
			case NORTHEAST:newX=x-1;newY=y-1;break;
			case EAST:newX=x-1;newY=y;break;
			case SOUTHEAST:newX=x-1;newY=y+1;break;
			case SOUTH:newX=x;newY=y+1;break;
			case SOUTHWEST:newX=x+1;newY=y+1;break;
			case WEST:newX=x+1;newY=y;break;
			case NORTHWEST:newX=x+1;newY=y-1;break;
			//TODO: Fix to avoid errors
			case NONE:newX=x;newY=y;break;
			default:newX=x;newY=y;break;
		}
		newEntity = new RedEntity(getEnvironment(),newX,newY);
		newEntity.canBreeding = false;
		this.canBreeding = false;
		entity.canBreeding = false;
		newEntity.breedingCounter = Constants.getNoBreedingAnimalSteps();
		this.breedingCounter = Constants.getNoBreedingAnimalSteps();
		entity.breedingCounter = Constants.getNoBreedingAnimalSteps();
		EntitiesPanel.updateEntityView(getEnvironment());
		newEntity.start();
		return newEntity;
	}

	private void checkValues(){
		if(animalLifeTime < 0) {
			System.out.println("Entity " +this.toString() +" life is ended");
			super.setCanPaint(false);
			super.getEnvironment().deleteEntity(this);
			super.stop();
		}
	}

	private void updateValues(){
		if(!canBreeding)breedingCounter--;
		else if (!neighbors.isEmpty()) {
			for(RedEntity entity : neighbors){
				if(entity.neighbors.contains(this))
					if(this.neighbors.contains(entity))
						getEnvironment().addEntity(bornRedEntity(entity));
			}
		}
		animalLifeTime--;
	}

	/**
	 * @return x-coordinate
	 */
	public int getX(){return x;}

	/**
	 * @return y-coordinate
	 */
	public int getY(){return y;}

	private void checkNeighbors(ArrayList<RedEntity> animals, RedEntity currentEntity){
		int x;
		int y;
		int currX = currentEntity.getX();
		int currY = currentEntity.getY();
		double temp;
		for(RedEntity entity : animals){
			if(!entity.equals(currentEntity)){
				x = entity.getX();
				y = entity.getY();
				temp = Math.sqrt(Math.pow(x - currX, 2) + Math.pow(y - currY, 2));
				System.out.println("Distance between is "+temp);
				if(temp < 1.5) currentEntity.neighbors.add(entity);
				else if (currentEntity.neighbors.contains(entity)){currentEntity.neighbors.remove(entity);
					if(entity.neighbors.contains(currentEntity))entity.neighbors.remove(currentEntity);}
			}
		}
	}

    @Override
	protected void move(Direction direction) {
		synchronized (monitor){checkNeighbors(redEntities, this);
		checkValues();
		updateValues();}
		if(canBreeding) direction=Algorithms.getDirectionToRedEntity(redEntities, this);
		System.out.println("Info about entity: " + this.toString() + " X: " + this.getX() + " Y: " + this.getY()+"\n"
		+"Lifetime: "+ animalLifeTime+" Can breeding? " + canBreeding +" Breeding counter: "+breedingCounter+"\n"
		+"Neighbors: "+neighbors.toString()+"\n"
		+"Direction: "+direction+"\n"
		+"Entities: "+redEntities.toString()+"\n\n");
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
}
