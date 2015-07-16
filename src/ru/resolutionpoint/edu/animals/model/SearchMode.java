package ru.resolutionpoint.edu.animals.model;

import java.io.*;
import java.util.*;


public class SearchMode implements Observer {
    public SearchMode(){
        Constants.setTimeDelay(TIME_DELAY);
        try {
            writer = new PrintWriter(logfile);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private int TIME_DELAY = 0;

    SearchObject searchObject = new SearchObject();

    //Temp var's
    private int animalLifeTime;
    private int predatorLifeTime;
    private int noBreedingAnimalSteps;
    private int noBreedingPredatorSteps;
    private int predatorSatiationTime;
    private int predatorTime;

    //Range var's
    private int animalLifeTimeRange;
    private int predatorLifeTimeRange;
    private int noBreedingAnimalStepsRange;
    private int noBreedingPredatorStepsRange;
    private int predatorSatiationTimeRange;
    private int predatorTimeRange;

    private boolean alt_param = true;
    private boolean plt_param = true;
    private boolean nbas_param = true;
    private boolean nbps_param = true;
    private boolean pst_param = true;
    private boolean pt_param = true;

    private final int interval = 10;
    private final int rangeLimit = 500;
    //Log file
    File logfile = new File("search_log.txt");
    PrintWriter writer;

    public void startSearch(){
        searchObject.addObserver(this);
        searchObject.search();
    }

    public void stopSearch(){
        if (rangeStarted) rangeEnd();
        writer.close();
        searchObject.deleteObserver(this);
        searchObject.stop();
        System.out.println("Search stopped");
    }

    public boolean rangeStarted = false;

    public void rangeStart(){
        animalLifeTime = searchObject.getAnimalLifeTime();
        predatorLifeTime = searchObject.getPredatorLifeTime();
        noBreedingAnimalSteps = searchObject.getNoBreedingAnimalSteps();
        noBreedingPredatorSteps = searchObject.getNoBreedingPredatorSteps();
        predatorSatiationTime = searchObject.getPredatorSatiationTime();
        predatorTime = searchObject.getPredatorTime();
    }

    public void rangeEnd(){
        //Current range
        animalLifeTimeRange = searchObject.getAnimalLifeTime() - animalLifeTime;
        predatorLifeTimeRange = searchObject.getPredatorLifeTime() - predatorLifeTime;
        noBreedingAnimalStepsRange = searchObject.getNoBreedingAnimalSteps() - noBreedingAnimalSteps;
        noBreedingPredatorStepsRange = searchObject.getNoBreedingPredatorSteps() - noBreedingPredatorSteps;
        predatorSatiationTimeRange = searchObject.getPredatorSatiationTime() - predatorSatiationTime;
        predatorTimeRange = searchObject.getPredatorTime() - predatorTime;

        if (animalLifeTimeRange > 100) {
            System.out.println("Animal lifetime range: " + (animalLifeTime + " - " + searchObject.getAnimalLifeTime()) + " ");
            writer.println("Animal lifetime range: " + (animalLifeTime + " - " + searchObject.getAnimalLifeTime()) + " ");
        }
        if (predatorLifeTimeRange > 100) {
            System.out.println("Predator lifetime range: " + (predatorLifeTime + " - " + searchObject.getPredatorLifeTime()) + " ");
            writer.println("Predator lifetime range: " + (predatorLifeTime + " - " + searchObject.getPredatorLifeTime()) + " ");
        }
        if (noBreedingAnimalStepsRange > 100) {
            System.out.println("No breeding animal steps range: " + (noBreedingAnimalSteps + " - " + searchObject.getNoBreedingAnimalSteps()) + " ");
            writer.println("No breeding animal steps range: " + (noBreedingAnimalSteps + " - " + searchObject.getNoBreedingAnimalSteps()) + " ");
        }
        if (noBreedingPredatorStepsRange > 100) {
            System.out.println("No breeding predator steps range: " + (noBreedingPredatorSteps + " - " + searchObject.getNoBreedingPredatorSteps()) + " ");
            writer.println("No breeding predator steps range: " + (noBreedingPredatorSteps + " - " + searchObject.getNoBreedingPredatorSteps()) + " ");
        }
        if (predatorSatiationTimeRange > 100) {
            System.out.println("Predator satiation time range: " + (predatorSatiationTime + " - " + searchObject.getPredatorSatiationTime()) + " ");
            writer.println("Predator satiation time range: " + (predatorSatiationTime + " - " + searchObject.getPredatorSatiationTime()) + " ");
        }
        if (predatorTimeRange > 100) {
            System.out.println("Predator time range: " + (predatorTime + " - " + searchObject.getPredatorTime()) + " ");
            writer.println("Predator time range: " + (predatorTime + " - " + searchObject.getPredatorTime()) + " ");
        }
    }

    private void updateConstants(){

        if (searchObject.getAnimalLifeTime() > 500) alt_param = false;
        if (searchObject.getPredatorLifeTime() > 500) plt_param = false;
        if (searchObject.getNoBreedingAnimalSteps() > 500) nbas_param = false;
        if (searchObject.getNoBreedingPredatorSteps() > 500) nbps_param = false;
        if (searchObject.getPredatorSatiationTime() > 500) pst_param = false;
        if (searchObject.getPredatorTime() > 500) pt_param = false;

        if (!alt_param && !plt_param && !nbas_param && !nbps_param && !pst_param && !pt_param) stopSearch();
        System.out.println(alt_param + " " + plt_param + " " + nbas_param + " " + nbps_param + " " + pst_param + " " + pt_param);

        if (alt_param)
        searchObject.setAnimalLifeTime(searchObject.getAnimalLifeTime() + interval);
        if (nbas_param)
        searchObject.setNoBreedingAnimalSteps(alt_param ? searchObject.getAnimalLifeTime() / 10 : searchObject.getNoBreedingAnimalSteps() + 10);
        if (plt_param)
        searchObject.setPredatorLifeTime(searchObject.getPredatorLifeTime() + interval);
        if (nbps_param)
        searchObject.setNoBreedingPredatorSteps(plt_param ? searchObject.getPredatorLifeTime() / 10 : searchObject.getNoBreedingPredatorSteps() + 10);
        if (pst_param)
        searchObject.setPredatorSatiationTime(searchObject.getPredatorSatiationTime() + interval);
        if (pt_param)
        searchObject.setPredatorTime(searchObject.getPredatorTime() + interval);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("New data received " + System.currentTimeMillis());
        if (searchObject.check != 2) {
            if (rangeStarted){
                rangeEnd();
                rangeStarted = false;
                System.out.println("Range ended");
            }
            System.out.println("!= 2");
            updateConstants();
            startSearch();
        } else {
            if (!rangeStarted) {
                rangeStart();
                rangeStarted = true;
                System.out.println("Range started");
            }
            System.out.println("== 2");
            updateConstants();
            startSearch();
        }
    }
}
