package Algorithm;

import Genetics.Population;
import Graphing.GraphPlot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates a genetic algorithm to run
 * Uses set values to make it easier
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Allowed for a simulation to be run
 * @version 1.2 - Added for multi sim to be run
 * @version 1.3 - Added the option menu
 * @version 1.31 - Correct code and layout
 * @version 1.4 - Fixed errors revolving around testing
 * @version 1.5 - Final bug fixes
 * @see Population
 * @see GeneticAlgorithm
 * @see GraphPlot
 */
public class Set {

    private Set(){}

    /**
     * Creates a genetic algorithm to run using set values
     * @return - A genetic algorithm
     */
    public static GeneticAlgorithm getGA(boolean multipleSimulations, boolean fullSimulation) throws IOException {
        GeneticAlgorithm ga = new GeneticAlgorithm();

        int popSize = 500;
        int maxGen = 500;
        int eliteAmount = 1;
        double mutationRate = 0.04;
        double crossOver = 0.90;

        //Gets whether the user will want to load a random set of cities or not
        System.out.print("Would you like to load a file in (Y/N)? ");
        boolean loadFile = loadFile();
        if(loadFile){
            //If load from file, load the file the user has entered
            String filename = getFileName();
            ga.setPopulation(Population.getPopFromFile(filename, popSize, true));
        } else {
            //Else load a random city set
            System.out.println("Loading random layout");
            ga.setPopulation(Population.getRandomPopulation(35, popSize, true));
        }

        if(!fullSimulation) {
            //Gets the selection method the user wants to use
            printSelectionMethods();
            System.out.print("Please select selection method: ");
            ga.setSelectionType(selectSelectionMethod());

            //Gets the crossover method the user wants to use
            printCrossoverMethods();
            System.out.print("Please select crossover method: ");
            ga.setCrossoverType(selectCrossoverMethod());

            //Set the rest of the variables
            ga.setMutationRate(mutationRate);
            ga.setNumOfElites(eliteAmount);
            ga.setNumOfGens(maxGen);
            ga.setCrossoverRate(crossOver);

            if (multipleSimulations) {
                ga.setMultiSim(true);
                runMultiSim(ga);
            } else {
                ga.setMultiSim(false);
                ga.run();
            }
        }
        return ga;
    }


    /**
     * Gets the selection method the user wants to simulate with
     * @return - The selection method the user wants to use
     */
    private static GeneticAlgorithm.SelectionType selectSelectionMethod(){
        Scanner input = new Scanner(System.in);
        String userInput = input.next();
        GeneticAlgorithm ga = new GeneticAlgorithm();
            //User selects the method they want to use
            switch (userInput) {
                case "1":
                    ga.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
                    break;
                case "2":
                    ga.setSelectionType(GeneticAlgorithm.SelectionType.ROULETTE);
                    break;
                case "3":
                    ga.setSelectionType(GeneticAlgorithm.SelectionType.RANKED);
                    break;
                default:
                    System.out.println("Invalid choice");
                    System.out.println("Default method will be used");
                    System.out.println("Tournament Selection will be used");
                    System.out.println();
            }
        return ga.getSelectionType();
    }


    /**
     * Gets the user to select a crossover method to use in the simulation
     * @return - The crossover method the user wants to use
     */
    private static GeneticAlgorithm.CrossoverType selectCrossoverMethod(){
        Scanner input = new Scanner(System.in);
        String userInput = input.next();
        GeneticAlgorithm ga = new GeneticAlgorithm();
        //User selects method they want to use
        switch (userInput) {
            case "1":
                ga.setCrossoverType(GeneticAlgorithm.CrossoverType.PMX);
                break;
            case "2":
                ga.setCrossoverType(GeneticAlgorithm.CrossoverType.CCO);
                break;
            case "3":
                ga.setCrossoverType(GeneticAlgorithm.CrossoverType.APC);
                break;
            case "4":
                ga.setCrossoverType(GeneticAlgorithm.CrossoverType.OX1);
                break;
            case "5":
                ga.setCrossoverType(GeneticAlgorithm.CrossoverType.ERC);
                break;
            default:
                System.out.println("Invalid choice");
                System.out.println("Default method will be used");
                System.out.println("PMX Crossover will be used");
                System.out.println();
        }
        return ga.getCrossoverType();
    }


    /**
     * Prints the potential selection methods
     */
    private static void printSelectionMethods(){
        System.out.println();
        System.out.println("1: Tournament Selection");
        System.out.println("2: Roulette Wheel Selection");
        System.out.println("3: Ranked Selection");
    }


    /**
     * Prints the potential crossover methods
     */
    private static void printCrossoverMethods(){
        System.out.println();
        System.out.println("1: PMX Crossover");
        System.out.println("2: CCS Crossover");
        System.out.println("3: APC Crossover");
        System.out.println("4: OX1 Crossover");
        System.out.println("5: ERC Crossover");
    }


    /**
     * Gets whether the user wants to open the file to load cities
     * Or load a random set of cities
     * @return - Whether the user wants to open a file or not
     */
    private  static boolean loadFile(){
        Scanner input = new Scanner(System.in);
        String userInput = input.next();

        //If Y load a file of not load a random city set
        boolean loadFile = false;
        switch (userInput) {
            case "Y":
                loadFile = true;
                break;
            case "N":
                break;
            default:
                System.out.println("Invalid choice");
                System.out.println("Random city set will be loaded");
                System.out.println();
        }
        return loadFile;
    }


    /**
     * Gets the file name the user wants to try to open
     * File should contain city names
     * @return - The file name the user has entered
     */
    private static String getFileName(){
        System.out.print("Please enter filename: ");
        Scanner input = new Scanner(System.in);
        return input.next();
    }


    /**
     * Creates a population from 20 cities with set co-ordinates
     * USed for testing as cities always in same spot
     * @return - The genetic algorithm to run
     */
    public static GeneticAlgorithm getSetCityGA(){
        int popSize = 500;
        int maxGen = 100;
        int eliteAmount = 1;
        double mutationRate = 0.04;
        double crossOver = 0.90;

        //Set the parameters
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setPopulation(Population.getSetPopulation(20, popSize, true));
        ga.setMutationRate(mutationRate);
        ga.setNumOfElites(eliteAmount);
        ga.setNumOfGens(maxGen);
        ga.setCrossoverRate(crossOver);
        ga.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
        ga.setCrossoverType(GeneticAlgorithm.CrossoverType.PMX);

        return ga;
    }


    /**
     * Runs the multi sim over a set number of iterations
     * Used in order to get an average for a select combination of selection and crossover methods
     * @param ga - The genetic algorithm so far that will be run
     */
    private static void runMultiSim(GeneticAlgorithm ga){
        int numberOfRuns = 120;
        ArrayList<Integer> averageFinalDistance = new ArrayList<>();
        ArrayList<Integer> generationByGeneration = new ArrayList<>();

        ArrayList<Integer> averageFinalAverageDistance = new ArrayList<>();
        ArrayList<Integer> generationByGenerationAverageDistances = new ArrayList<>();

        boolean firstRun = true;

        //Gets time
        double startTime = System.currentTimeMillis();

        //Run the ga and record the best final result
        for(int i=0; i<numberOfRuns; i++){
            ga.run();
            averageFinalDistance.add(ga.getBestLastDistance());
            ArrayList<Integer> bestDistances = ga.getBestDistanceOfEachGen();
            generationByGeneration = calculateAverages(generationByGeneration, bestDistances, firstRun, (i+1));

            averageFinalAverageDistance.add(ga.getAverageLastGenDistance());
            ArrayList<Integer> averageDistances = ga.getAverageDistOfEachGen();
            generationByGenerationAverageDistances = calculateAverages(generationByGenerationAverageDistances, averageDistances, firstRun, (i+1));

            firstRun = false;

            ga.resetGA();
        }

        //Gets the average time of one algorithm run
        double runTime = workOutRunTime(startTime, numberOfRuns);

        //Gets the average distance of the final best distances of each run
        int totalDistance = 0;
        for (Integer integer : averageFinalDistance) {
            totalDistance += integer;
        }
        int averageDistance = (totalDistance / averageFinalDistance.size());

        //Gets the average distance for the final average distances of each run
        int totalAverageDistance = 0;
        for (Integer integer : averageFinalAverageDistance) {
            totalAverageDistance += integer;
        }
        int averageAverageDistance = (totalAverageDistance / averageFinalAverageDistance.size());

        //Prints result
        System.out.println();
        System.out.println();
        System.out.println("Average Distance of final best distance: " + averageDistance);
        System.out.println("Average Distance of the final average distance: " + averageAverageDistance);
        System.out.println("Average runtime: " + runTime + " Seconds");

        //Plots graph to show results
        GraphPlot plot = new GraphPlot("Graph For Distances", generationByGeneration, generationByGeneration.size(), generationByGenerationAverageDistances);
        plot.pack();
        plot.setVisible(true);
    }


    /**
     * Calculates the average best distance of each generation to plot at the end of a large simulation
     * @param generationByGeneration - The current averages of each generation
     * @param bestDistances - The best distances from the current iteration
     * @param firstRun - Whether it is the first run
     * @param iteration - Which iteration to process is on
     * @return - The updated array list containing the averages
     */
    private static ArrayList<Integer> calculateAverages(ArrayList<Integer> generationByGeneration, ArrayList<Integer> bestDistances, boolean firstRun, int iteration){
        ArrayList<Integer> list = new ArrayList<>();
        if(firstRun) {
            list.addAll(bestDistances);
        } else {
            for(int i=0; i<bestDistances.size(); i++){
                int currentAverage = (generationByGeneration.get(i) * (iteration-1));
                int valueToAdd = bestDistances.get(i);
                int newAverage = ((currentAverage + valueToAdd)/iteration);
                list.add(i, newAverage);
            }
        }
        return list;
    }


    /**
     * Calculates the run time of a sim
     * @param startTime - The time the sim started
     * @return - The average runtime
     */
    private static double workOutRunTime(double startTime, int numberOfRuns){
        double endTime = System.currentTimeMillis();
        double totalRuntime = ((endTime - startTime) / 1000);
        return (totalRuntime/numberOfRuns);
    }

}
