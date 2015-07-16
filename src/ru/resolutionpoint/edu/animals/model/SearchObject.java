package ru.resolutionpoint.edu.animals.model;

import java.util.*;

/**
 * Created by 1 on 14.07.2015.
 */
public class SearchObject extends Observable implements Observer {
    Environment environment;

    //Entities number
    private int redentities = 4;
    private int grayentities = 4;

    private List<Point> busyPoints;

    private int stepCounter;

    public int check;

    public int getAnimalLifeTime() {
        return animalLifeTime;
    }
    public void setAnimalLifeTime(int animalLifeTime) {
        this.animalLifeTime = animalLifeTime;
    }
    public int getPredatorLifeTime() {
        return predatorLifeTime;
    }
    public void setPredatorLifeTime(int predatorLifeTime) {
        this.predatorLifeTime = predatorLifeTime;
    }
    public int getNoBreedingAnimalSteps() {
        return noBreedingAnimalSteps;
    }
    public void setNoBreedingAnimalSteps(int noBreedingAnimalSteps) {
        this.noBreedingAnimalSteps = noBreedingAnimalSteps;
    }
    public int getNoBreedingPredatorSteps() {
        return noBreedingPredatorSteps;
    }
    public void setNoBreedingPredatorSteps(int noBreedingPredatorSteps) {
        this.noBreedingPredatorSteps = noBreedingPredatorSteps;
    }
    public int getPredatorSatiationTime() {
        return predatorSatiationTime;
    }
    public void setPredatorSatiationTime(int predatorSatiationTime) {
        this.predatorSatiationTime = predatorSatiationTime;
    }
    public int getPredatorTime() {
        return predatorTime;
    }
    public void setPredatorTime(int predatorTime) {
        this.predatorTime = predatorTime;
    }

    private int animalLifeTime = 1;
    private int predatorLifeTime = 10;
    private int noBreedingAnimalSteps = 10;
    private int noBreedingPredatorSteps = 10;
    private int predatorSatiationTime = 100;
    private int predatorTime = 50;

    private void resetConstants(){
        Constants.setPredatorTime(animalLifeTime);
        Constants.setAnimalLifeTime(predatorLifeTime);
        Constants.setPredatorLifeTime(noBreedingAnimalSteps);
        Constants.setNoBreedingAnimalSteps(noBreedingPredatorSteps);
        //Constants.setNoBreedingPredatorSteps(neighboringAnimalsLimit);
        Constants.setNeighboringAnimalsLimit(predatorSatiationTime);
        Constants.setPredatorSatiationTime(predatorTime);
    }

    public void search() {
        //Initial state
        resetConstants();
        environment = new Environment();
        stepCounter = 0;

        busyPoints = new ArrayList<>();

        fillArrayWithRandomPoints();

        for (int i = 0; i < redentities; i++){
            environment.addEntity(new RedEntity(busyPoints.get(i).getX(), busyPoints.get(i).getY()));
        }
        for (int i = 0; i < grayentities; i++){
            environment.addEntity(new GrayEntity(busyPoints.get(i).getX(), busyPoints.get(i).getY()));
        }

        environment.addObserver(this);

        environment.start();
    }

    public void stop() {
        environment.deleteObserver(this);
        environment.stop();
        environment = null;
        busyPoints = null;

    }
    //0: 0 reds, 0 grays; 1: <2 reds, <2 grays; 2: >2 reds, >2 grays;
    private int checkCondition(){
        int gray = 0;
        int red = 0;
        for (Entity entity : environment.getEntities()){
            if (entity != null) {
                if (entity.getEntityType() == 0) red++;
                else gray++;
            }
        }
        return red <= 0 || gray <= 0 ? 0 : red < 2 || gray < 2 ? 1 : 2;
    }

    private void fillArrayWithRandomPoints(){
        for(int i =0; i < redentities; i++){
            Point point;
            Random random = new Random();
            do{
                point = new Point(random.nextInt(Environment.WIDTH), random.nextInt(Environment.HEIGHT));
            }while (busyPoints.contains(point));
        }
        for(int i =0; i < grayentities; i++){
            Point point;
            Random random = new Random();
            do{
                point = new Point(random.nextInt(Environment.WIDTH), random.nextInt(Environment.HEIGHT));
            }while (busyPoints.contains(point));
            busyPoints.add(point);
        }
    }

    public void update(Observable o, Object arg) {
        stepCounter++;
        check = checkCondition();
        if (check == 0 && stepCounter <= 1000) {
            environment.stop();
            change();
        }
        else if (check == 2 && stepCounter >= 1000) {
            environment.stop();
            change();
        }
        else if (check == 1 && stepCounter >= 1000) {
            environment.stop();
            change();
        }
    }

    public void change() {
        setChanged();
        notifyObservers();
    }
}
