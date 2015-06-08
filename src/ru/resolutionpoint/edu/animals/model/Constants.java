package ru.resolutionpoint.edu.animals.model;

/**
 * Created by 1 on 09.05.2015.
 */
public class Constants {
    private Constants(){}

    private static int animalLifeTime = 10000;
    private static int predatorLifeTime = 10000;
    private static int noBreedingAnimalSteps = 50;
    private static int noBreedingPredatorSteps = 3000;
    private static int neighboringAnimalsLimit = 5;
    private static int predatorSatiationTime = 10;
    private static int predatorTime = 10;
    private static int TIME_DELAY = 50;

    public static int getAnimalLifeTime() {
        return animalLifeTime;
    }

    public static void setAnimalLifeTime(int animalLifeTime) {
        Constants.animalLifeTime = animalLifeTime;
    }

    public static int getPredatorLifeTime() {
        return predatorLifeTime;
    }

    public static void setPredatorLifeTime(int predatorLifeTime) {
        Constants.predatorLifeTime = predatorLifeTime;
    }

    public static int getNoBreedingAnimalSteps() {
        return noBreedingAnimalSteps;
    }

    public static void setNoBreedingAnimalSteps(int noBreedingSteps) {
        Constants.noBreedingAnimalSteps = noBreedingSteps;
    }

    public static int getNoBreedingPredatorSteps() {
        return noBreedingPredatorSteps;
    }

    public static void setNoBreedingPredatorSteps(int noBreedingPredatorSteps) {
        Constants.noBreedingPredatorSteps = noBreedingPredatorSteps;
    }

    public static int getNeighboringAnimalsLimit() {
        return neighboringAnimalsLimit;
    }
    //Here 8 is limit because each entity have only 8 positions around itself
    public static void setNeighboringAnimalsLimit(int neighboringAnimalsLimit) {
        if (neighboringAnimalsLimit <= 8 && neighboringAnimalsLimit >= 0) {
            Constants.neighboringAnimalsLimit = neighboringAnimalsLimit;
        }
        else if (neighboringAnimalsLimit > 8){
            Constants.neighboringAnimalsLimit = 8;
        }
        else if (neighboringAnimalsLimit < 0){
            Constants.neighboringAnimalsLimit = 0;
        }
    }

    public static int getPredatorSatiationTime() {
        return predatorSatiationTime;
    }

    public static void setPredatorSatiationTime(int predatorSatiationTime) {
        Constants.predatorSatiationTime = predatorSatiationTime;
    }

    public static int getTimeDelay() {
        return TIME_DELAY;
    }

    public static void setTimeDelay(int timeDelay) {
        TIME_DELAY = timeDelay;
    }

    public static int getPredatorTime() {
        return predatorTime;
    }

    public static void setPredatorTime(int predatorTime) {
        Constants.predatorTime = predatorTime;
    }

}
