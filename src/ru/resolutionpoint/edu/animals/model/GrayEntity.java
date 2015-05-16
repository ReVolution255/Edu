package ru.resolutionpoint.edu.animals.model;

import java.util.ArrayList;

/**
 * Created by 1 on 08.05.2015.
 */
public class GrayEntity extends Predator implements Runnable {

    public static ArrayList<GrayEntity> grayEntities = new ArrayList<>();

    private int x;
    private int y;

    /**
     * Constructs new entity
     *
     * @param environment environment
     */
    public GrayEntity(Environment environment) {
        super(environment);
    }

    /**
     * Constructs new entity
     *
     * @param environment environment
     * @param x           initial x-coordinate
     * @param y           initial y-coordinate
     */
    public GrayEntity(Environment environment, int x, int y) {
        super(environment, x, y);
        predatorLifeTime = Constants.getPredatorLifeTime();
        breedingCounter = Constants.getNoBreedingPredatorSteps();
        isHungry = false;
        hungryCounter = Constants.getPredatorSatiationTime();
        predatorTime = Constants.getPredatorTime();
        this.x = x;
        this.y = y;
    }

    @Override
    public String getImagePath() {
        return "/images/gray.gif";
    }

    private void checkValues(){
        if(predatorLifeTime < 0 || predatorTime < 0) {
            System.out.println("Entity " +this.toString() +" life is ended");
            super.setCanPaint(false);
            super.getEnvironment().deleteEntity(this);
            super.stop();
        }
    }

    private void updateValues(){
        if(isHungry) predatorTime--;
        if(!isHungry && hungryCounter < 0) {isHungry = true;super.hungryCounter = Constants.getPredatorSatiationTime();}
        else hungryCounter--;
        breedingCounter--;
        predatorLifeTime--;
    }

    /**
     * @return x-coordinate
     */
    public int getX(){return x;}

    /**
     * @return y-coordinate
     */
    public int getY(){return y;}

    @Override
    protected void move(Direction direction) {
        checkValues();
        updateValues();
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
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void stop() {
        super.stop();
    }

}
