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
        if (Math.abs(Math.abs(x) - Math.abs(currX)) <= 0 & Math.abs(Math.abs(y) - Math.abs(currY)) <= 0)
        {d = getRandomDirection(); return d;}
        if (Math.abs(Math.abs(x) - Math.abs(currX)) <= 1 & Math.abs(Math.abs(y) - Math.abs(currY)) <= 1)
        {System.out.println(" Bad Point");d = Direction.NONE; return d;}
        if(x == currX && y < currY) d = Direction.NORTH;
        else if (x < currX && y == currY) d = Direction.EAST;
        else if (x > currX && y == currY) d = Direction.WEST;
        else if (x < currX && y < currY) d = Direction.NORTHEAST;
        else if (x < currX && y > currY) d = Direction.SOUTHEAST;
        else if (x == currX && y > currY) d = Direction.SOUTH;
        else if (x > currX && y > currY) d = Direction.SOUTHWEST;
        else if (x > currX && y < currY) d = Direction.NORTHWEST;
        else {d = Direction.NONE;}
        return d;
    }

    public static Direction getRandomDirection(){
        return getDirectionFromInt(random.nextInt(8));}

    public static Direction getNoDirection(){return Direction.NONE;}

    public static double getDistanceBetweenPoints(Point a, Point b){
        return Math.sqrt( Math.pow(a.getX() - b.getX(),2) + Math.pow(a.getY() - b.getY(),2) );
    }

    public static int getDeltaXfromPoints(Point current, Point target){
        int x;
        if (current.getX() < target.getX()) x = 1;
        else if (current.getX() == target.getX()) x = 0;
        else x = -1;
        return x;
    }

    public static int getDeltaYfromPoints(Point current, Point target){
        int y;
        if (current.getY() < target.getY()) y = 1;
        else if (current.getY() == target.getY()) y = 0;
        else y = -1;
        return y;
    }

    public static Point getRandomNeighborPoint(Point current){
        int dx;
        int random = (int)(Math.random()*10)%3;
        if (random == 0){
            dx = 1;
        } else if ( random == 1) {
            dx = 0;
        } else dx = -1;
        int dy;
        random = (int)Math.random()*10%3;
        if (random == 0){
            dy = 1;
        } else if ( random == 1) {
            dy = 0;
        } else dy = -1;
        return new Point(current.getX()+dx,current.getY()+dy);
    }
}
