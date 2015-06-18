package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RedEntity extends Animal {

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

	@XmlElement
    @Override
	public String getImagePath() {
        return "/images/red.gif";
    }

	public int getNoBreedingSteps(){
		return Constants.getNoBreedingAnimalSteps();
	}
	@Override
	public void visit() {
		super.visit();
	}

	public Entity bornChild(Point multiplyPoint){
		return new RedEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
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
