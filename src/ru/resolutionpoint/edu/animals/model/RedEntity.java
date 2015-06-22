package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.annotation.XmlElement;

public class RedEntity extends Animal {

	public RedEntity(){ super();
	}

    public RedEntity(Environment environment, int x, int y) {
        super(environment, x, y);
		setBreeding(false);
		setMustDie(false);
    }

	public RedEntity(Environment environment, int x, int y, int breedingTime, int lifeTime, boolean mustDie, boolean canBreeding) {
		super(environment, x, y);
		setMustDie(mustDie);
		setBreeding(canBreeding);
		setBreedingTime(breedingTime);
		setLifeTime(lifeTime);
	}

    @Override
	@XmlElement
	public String getImagePath() {
        return "/images/red.gif";
    }

	public int getNoBreedingSteps(){
		return Constants.getNoBreedingAnimalSteps();
	}
	@Override
	public void visit(Environment environment) {
		super.visit(environment);
	}

	public Entity bornChild(Point multiplyPoint, Environment environment){
		return new RedEntity(environment, multiplyPoint.getX(), multiplyPoint.getY());
	}

	@Override
	public int getEntityType() {
		return 0;
	}

    @Override
	protected void move(Point point) {
		super.setPosition(point);
	}
}
