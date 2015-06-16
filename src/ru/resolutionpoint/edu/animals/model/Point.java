package ru.resolutionpoint.edu.animals.model;

public class Point implements Comparable<Point> {
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    int x;
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    int y;

    @Override
    public int compareTo(Point o) {
        if (getX() == o.getX() && getY() == o.getY())
        return 0;
        else return -1;
    }
}
