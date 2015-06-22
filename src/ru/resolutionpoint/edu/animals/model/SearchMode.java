package ru.resolutionpoint.edu.animals.model;

import java.io.File;
import java.util.*;


public class SearchMode implements Observer {
    public SearchMode(Environment environment){
        this.environment = environment;
        Constants.setTimeDelay(TIME_DELAY);
    }

    Environment environment;

    //Initial state
    private int animalLifeTime = 10;
    private int predatorLifeTime = 10;
    private int noBreedingAnimalSteps = 1;
    private int noBreedingPredatorSteps = 1;
    private int neighboringAnimalsLimit = 4;
    private int predatorSatiationTime = 100;
    private int predatorTime = 1;
    private int TIME_DELAY = 1;

    //Entities number
    private int redentities = 4;
    private int grayentities = 4;

    private List<Point> busyPoints;

    private int stepCounter;

    //Log file
    File logfile = new File("search-log.txt");

    public void search() {
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
        while (true){
            System.out.println(stepCounter);
            int check = checkCondition();
            if (check == 0 && stepCounter <= 1000) {environment.stop(); System.out.println("Steps not over and condition is " + checkCondition()); break;}
            else if (stepCounter >= 1000 && check == 2) {environment.stop(); System.out.println("Steps over and condition is (normal = 2)" + checkCondition()); break;}
            else if (stepCounter >= 1000 && check == 1) {environment.stop(); System.out.println("Steps over and condition is (normal = 1)" + checkCondition()); break;}
        }
        System.out.println("Search over");
        //
    }
    private void resetConstants(){
        Constants.setPredatorTime(animalLifeTime);
        Constants.setAnimalLifeTime(predatorLifeTime);
        Constants.setPredatorLifeTime(noBreedingAnimalSteps);
        Constants.setNoBreedingAnimalSteps(noBreedingPredatorSteps);
        Constants.setNoBreedingPredatorSteps(neighboringAnimalsLimit);
        Constants.setNeighboringAnimalsLimit(predatorSatiationTime);
        Constants.setPredatorSatiationTime(predatorTime);
    }
    // 0 if all grays or red die, 1 if red/gray < 2 and 2 else (is normal for 1000 steps)
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

    @Override
    public void update(Observable o, Object arg) {
        stepCounter++;
    }
}
