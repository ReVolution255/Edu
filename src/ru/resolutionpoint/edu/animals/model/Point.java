package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.annotation.XmlElement;

public class Point implements Comparable<Point> {
    public Point(){

    }
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    int x;
    @XmlElement
    public int getX(){
        return x;
    }
    @XmlElement
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
