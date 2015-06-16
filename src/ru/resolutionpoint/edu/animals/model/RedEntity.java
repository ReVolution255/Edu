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

	public int getNoBreedingSteps(){
		return Constants.getNoBreedingAnimalSteps();
	}
	public Entity bornChild(Point multiplyPoint){
		return new RedEntity(getEnvironment(), multiplyPoint.getX(), multiplyPoint.getY());
	}

	public void visit() {
		super.visit();
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
