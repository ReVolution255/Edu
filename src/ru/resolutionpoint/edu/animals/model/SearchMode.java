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
        searchObject.addObserver(this);
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

    private String alt_range = "Animal lifetime range: ";
    private String plt_range = "Predator lifetime range: ";
    private String nbas_range = "No breeding animal steps range: ";
    private String nbps_range = "No breeding predator steps range: ";
    private String pst_range = "Predator satiation time range: ";
    private String pt_range = "Predator time range: ";

    public boolean rangeStarted = false;
    private final int interval = 10;
    private final int rangeLimit = 500;
    //Log file
    File logfile = new File("search_log.txt");
    PrintWriter writer;

    public void startSearch(){
        searchObject.search();
    }

    public void stopSearch(){
        if (rangeStarted) rangeEnd();
        writer.close();
        searchObject.stop();
        searchObject.deleteObserver(this);
        System.exit(0);
    }

    public void rangeStart(){
        animalLifeTime = searchObject.getAnimalLifeTime();
        predatorLifeTime = searchObject.getPredatorLifeTime();
        noBreedingAnimalSteps = searchObject.getNoBreedingAnimalSteps();
        noBreedingPredatorSteps = searchObject.getNoBreedingPredatorSteps();
        predatorSatiationTime = searchObject.getPredatorSatiationTime();
        predatorTime = searchObject.getPredatorTime();
    }

    private void printToConsole(String text, int min, int max, boolean plus){
        System.out.println(text + min + " - " + max + (plus ? "+" : ""));
    }

    private void printToFile(String text, int min, int max, boolean plus){
        writer.println(text + min + " - " + max + (plus ? "+" : ""));
    }

    private void printData(String text, int min, int max, boolean plus){
        printToConsole(text, min, max, plus);
        printToFile(text, min, max, plus);
    }

    private void updateRangeVars(){
        animalLifeTimeRange = searchObject.getAnimalLifeTime() - animalLifeTime;
        predatorLifeTimeRange = searchObject.getPredatorLifeTime() - predatorLifeTime;
        noBreedingAnimalStepsRange = searchObject.getNoBreedingAnimalSteps() - noBreedingAnimalSteps;
        noBreedingPredatorStepsRange = searchObject.getNoBreedingPredatorSteps() - noBreedingPredatorSteps;
        predatorSatiationTimeRange = searchObject.getPredatorSatiationTime() - predatorSatiationTime;
        predatorTimeRange = searchObject.getPredatorTime() - predatorTime;
    }

    public void checkRange(){
        if (animalLifeTimeRange > 100) {
            if (alt_param) printData(alt_range, animalLifeTime, searchObject.getAnimalLifeTime(), true);
            alt_param = false;
            rangeStarted = false;
        }
        if (predatorLifeTimeRange > 100) {
            if (plt_param) printData(plt_range, predatorLifeTime, searchObject.getPredatorLifeTime(), true);
            plt_param = false;
            rangeStarted = false;
        }
        if (noBreedingAnimalStepsRange > 100) {
            if (nbas_param) printData(nbas_range, noBreedingAnimalSteps, searchObject.getNoBreedingAnimalSteps(), true);
            nbas_param = false;
            rangeStarted = false;
        }
        if (noBreedingPredatorStepsRange > 100) {
            if (nbps_param) printData(nbps_range, noBreedingPredatorSteps, searchObject.getNoBreedingPredatorSteps(), true);
            nbps_param = false;
            rangeStarted = false;
        }
        if (predatorSatiationTimeRange > 100) {
            if (pst_param) printData(pst_range, predatorSatiationTime, searchObject.getPredatorSatiationTime(), true);
            pst_param = false;
            rangeStarted = false;
        }
        if (predatorTimeRange > 100) {
            if (pt_param) printData(pt_range, predatorTime, searchObject.getPredatorTime(), true);
            pt_param = false;
            rangeStarted = false;
        }
    }

    public void rangeEnd(){
        if (alt_param) printData(alt_range, animalLifeTime, searchObject.getAnimalLifeTime(), false);
        if (plt_param) printData(plt_range, predatorLifeTime, searchObject.getPredatorLifeTime(), false);
        if (nbas_param) printData(nbas_range, noBreedingAnimalSteps, searchObject.getNoBreedingAnimalSteps(), false);
        if (nbps_param) printData(nbps_range, noBreedingPredatorSteps, searchObject.getNoBreedingPredatorSteps(), false);
        if (pst_param) printData(pst_range, predatorSatiationTime, searchObject.getPredatorSatiationTime(), false);
        if (pt_param) printData(pt_range, predatorTime, searchObject.getPredatorTime(), false);
    }

    private void updateParameters(){
        if (searchObject.getAnimalLifeTime() >= rangeLimit) alt_param = false;
        if (searchObject.getPredatorLifeTime() >= rangeLimit) plt_param = false;
        if (searchObject.getNoBreedingAnimalSteps() >= rangeLimit) nbas_param = false;
        if (searchObject.getNoBreedingPredatorSteps() >= rangeLimit) nbps_param = false;
        if (searchObject.getPredatorSatiationTime() >= rangeLimit) pst_param = false;
        if (searchObject.getPredatorTime() >= rangeLimit) pt_param = false;
    }

    private void updateConstants(){
        updateParameters();

        //System.out.println(alt_param + " " + plt_param + " " + nbas_param + " " + nbps_param + " " + pst_param + " " + pt_param);
        if (!alt_param && !plt_param && !nbas_param && !nbps_param && !pst_param && !pt_param) stopSearch();

        if (alt_param)
        searchObject.setAnimalLifeTime(Math.min(searchObject.getAnimalLifeTime() + interval, rangeLimit));
        if (nbas_param)
        searchObject.setNoBreedingAnimalSteps(Math.min(alt_param ? searchObject.getAnimalLifeTime() / 10 : searchObject.getNoBreedingAnimalSteps() + 10, rangeLimit));
        if (plt_param)
        searchObject.setPredatorLifeTime(Math.min(searchObject.getPredatorLifeTime() + interval, rangeLimit));
        if (nbps_param)
        searchObject.setNoBreedingPredatorSteps(Math.min(plt_param ? searchObject.getPredatorLifeTime() / 10 : searchObject.getNoBreedingPredatorSteps() + 10, rangeLimit));
        if (pst_param)
        searchObject.setPredatorSatiationTime(Math.min(searchObject.getPredatorSatiationTime() + interval, rangeLimit));
        if (pt_param)
        searchObject.setPredatorTime(Math.min(searchObject.getPredatorTime() + interval, rangeLimit));
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("New data received " + System.currentTimeMillis());
        if (rangeStarted) updateRangeVars();

        if (searchObject.check != 2) {
            //Bad result
            if (rangeStarted){
                checkRange();
                rangeEnd();
                rangeStarted = false;
                System.out.println("Range ended");
            }
            System.out.println("!= 2");
        } else {
            //Good result
            if (!rangeStarted) {
                rangeStart();
                rangeStarted = true;
                System.out.println("Range started");
            } else {
                checkRange();
            }
            System.out.println("== 2");
        }
        updateConstants();
        startSearch();
    }
}
