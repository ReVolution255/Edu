package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.model.Entity.Direction;

import java.util.*;

/**
 * Created by 1 on 09.05.2015.
 */
public class Algorithms {

    private Algorithms(){}

    public static Random random = new Random();

    public static Direction getNextStepDirection(int movingAlgorithm){
        switch(movingAlgorithm){
            case 0: return getRandomDirection();
            case 1: return getNoDirection();
            default: return getNoDirection();
        }
    }

    public static Direction getDirectionFromInt(int i) {
        switch (i) {
            case 0:
                return Direction.NORTH;
            case 1:
                return Direction.EAST;
            case 2:
                return Direction.WEST;
            case 3:
                return Direction.SOUTH;
            case 4:
                return Direction.NORTHWEST;
            case 5:
                return Direction.NORTHEAST;
            case 6:
                return Direction.SOUTHWEST;
            case 7:
                return Direction.SOUTHEAST;
            default:
                return getNoDirection();
        }
    }

    public static Direction getDirectionFromInt(int x, int y, int currX, int currY) {
        Direction d;
        if(x == currX && y < currY) d = Direction.NORTH;
        else if (x < currX && y == currY) d = Direction.EAST;
        else if (x > currX && y == currY) d = Direction.WEST;
        else if (x < currX && y < currY) d = Direction.NORTHEAST;
        else if (x < currX && y > currY) d = Direction.SOUTHEAST;
        else if (x == currX && y > currY) d = Direction.SOUTH;
        else if (x > currX && y > currY) d = Direction.SOUTHWEST;
        else if (x > currX && y < currY) d = Direction.NORTHWEST;
        else d = Direction.NONE;
        return d;
    }

    public static Direction getRandomDirection(){
        return getDirectionFromInt(random.nextInt(8));}

    public static Direction getNoDirection(){return Direction.NONE;}

    public static Direction getRandomFreeDirection(ArrayList<RedEntity> animals, RedEntity currentEntity){
        //Directions here as NORTH, EAST, WEST, SOUTH, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST
        boolean[] Directions = {true, true, true, true, true, true, true, true};
        for (RedEntity entity : animals){
            if (currentEntity.y-1 == entity.getX() && currentEntity.x == entity.getX()) Directions[0] = false;
            else if (currentEntity.x-1 == entity.getX() && currentEntity.y == entity.getY()) Directions[1] = false;
            else if (currentEntity.x+1 == entity.getX() && currentEntity.y == entity.getY()) Directions[2] = false;
            else if (currentEntity.y+1 == entity.getX() && currentEntity.x == entity.getX()) Directions[3] = false;
            else if (currentEntity.x+1 == entity.getX() && currentEntity.y-1 == entity.getY()) Directions[4] = false;
            else if (currentEntity.x-1 == entity.getX() && currentEntity.y-1 == entity.getY()) Directions[5] = false;
            else if (currentEntity.x+1 == entity.getX() && currentEntity.y+1 == entity.getY()) Directions[6] = false;
            else if (currentEntity.x-1 == entity.getX() && currentEntity.y+1 == entity.getY()) Directions[7] = false;}


        for (int i = 0; i<Directions.length; i++)
            if(!Directions[i]) { return getDirectionFromInt(i); }
        return Direction.NONE;
    }

    public static Direction getDirectionToRedEntity(ArrayList<RedEntity> animals, RedEntity currentEntity){
        Direction d;
        int x;
        int y;
        int currX = currentEntity.getX();
        int currY = currentEntity.getY();
        double minimalDistance = Environment.WIDTH*Environment.HEIGHT;
        double temp;
        RedEntity minimalDistanceEntity = currentEntity;
        for(RedEntity entity : animals){
            if(!entity.equals(currentEntity)){
                x = entity.getX();
                y = entity.getY();
                temp = Math.sqrt(Math.pow(x-currX,2)+Math.pow(y-currY,2));
                if (temp < minimalDistance) {minimalDistance = temp; minimalDistanceEntity = entity;}
            }
        }
        //System.out.println("Current entity " + currentEntity.toString() + " X: " + currentEntity.getX() + " Y: " + currentEntity.getY());
        //System.out.println(minimalDistanceEntity.toString()+ " X: "+minimalDistanceEntity.getX() + " Y: "+ minimalDistanceEntity.getY());
        x = minimalDistanceEntity.getX();
        y = minimalDistanceEntity.getY();

        if(x == currX && y < currY) d = Direction.NORTH;
        else if (x < currX && y == currY) d = Direction.EAST;
        else if (x > currX && y == currY) d = Direction.WEST;
        else if (x < currX && y < currY) d = Direction.NORTHEAST;
        else if (x < currX && y > currY) d = Direction.SOUTHEAST;
        else if (x == currX && y > currY) d = Direction.SOUTH;
        else if (x > currX && y > currY) d = Direction.SOUTHWEST;
        else if (x > currX && y < currY) d = Direction.NORTHWEST;
        else d = Direction.NONE;
        if(minimalDistance < 2) d = Direction.NONE;

        //System.out.println(d.toString()+" Distance: "+minimalDistance);
        //System.out.println();
        return d;
    }
}
