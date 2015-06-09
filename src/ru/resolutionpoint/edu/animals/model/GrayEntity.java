package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.view.EntitiesPanel;

/**
 * Created by 1 on 08.05.2015.
 */
public class GrayEntity extends Predator implements Runnable {

    private int x;
    private int y;

    /*protected Direction getEntityDirection(Entity currentEntity, int algorithm) {
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
            double minimalDistance = Environment.WIDTH * Environment.HEIGHT;
            double temp;
            Entity minimalDistanceEntity = currentEntity;
            for(Entity entity : getEnvironment().getEntities()){
                if(!entity.equals(currentEntity) && entity.getEntityType() == 1){
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
        return 1;
    }

    @Override
    protected void visit() {

    }

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
