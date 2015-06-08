package ru.resolutionpoint.edu.animals.model;

import ru.resolutionpoint.edu.animals.model.Entity.Direction;

import java.util.*;

/**
 * Created by 1 on 09.05.2015.
 */
public class Algorithms {

    private Algorithms(){}

    public static Random random = new Random();

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
}
