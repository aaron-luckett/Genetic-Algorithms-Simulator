package SimAll;

import Algorithm.GeneticAlgorithm;
import Genetics.Population;
import Graphing.BarChart;

import java.util.HashMap;

/**
 * Class to run all possible combinations over a set number of runs in order to calculate
 * an average
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Fixed a bug in which it did not switch the combination over
 * @version 1.11 - Fixed a bug in which you couldn't load a file properly
 * @version 1.2 - Final bug checks
 * @see GeneticAlgorithm
 * @see Population
 * @see BarChart
 */
public class SimAll {
    //Parameters
    private HashMap<String, Integer> tournamentAverageBestDistances = new HashMap<>();   //Hash map of the tournament combinations
    private HashMap<String, Integer> rouletteAverageBestDistances = new HashMap<>();     //Hash map for the roulette combinations
    private HashMap<String, Integer> rankedAverageBestDistances = new HashMap<>();       //Hash map for the ranked combinations


    /**
     * Constructor for the class
     */
    public SimAll(){}


    /**
     * Runs the simulation using all possible combinations
     * @param pop - The population to run the sims on
     */
    public void runAllSims(Population pop){
        //Run the combinations based on the selection methods
        runTournament(pop);
        runRoulette(pop);
        runRanked(pop);

        //Create the bar chart and pass the hash maps over
        BarChart chart = new BarChart("Bar Chart",
                "Average Best Distances of Combinations", tournamentAverageBestDistances, rouletteAverageBestDistances, rankedAverageBestDistances);
        chart.pack( );
        chart.setVisible( true );
    }


    /**
     * Creates a set GA to run for all of the combinations
     * Can be altered to test out other things in the future potentially
     * (This is not relevant to my project currently)
     * @param pop - The population that the algorithm will start off with
     * @return - A genetic algorithm with set parameters
     */
    private GeneticAlgorithm createGeneticAlgorithm(Population pop){
        //Set parameters
        double mutationRate = 0.04;
        int eliteAmount = 1;
        int maxGen = 500;
        double crossOver = 0.9;

        //Create the Ga parameters
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setPopulation(pop);
        ga.setMutationRate(mutationRate);
        ga.setNumOfElites(eliteAmount);
        ga.setNumOfGens(maxGen);
        ga.setCrossoverRate(crossOver);
        ga.setMultiSim(true);

        return ga;
    }


    /**
     * Runs the GA using tournament selection
     * Will run for each of the crossover methods as well
     * @param pop - The population to use
     */
    private void runTournament(Population pop){
        //Runs GA using tournament selection and PMX crossover
        GeneticAlgorithm tournamentGa = createGeneticAlgorithm(pop);
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.PMX);
        tournamentAverageBestDistances.put("PMX", run(tournamentGa));

        //Runs GA using tournament selection and APC crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.APC);
        tournamentAverageBestDistances.put("APC", run(tournamentGa));

        //Runs GA using tournament selection and CCO crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.CCO);
        tournamentAverageBestDistances.put("CCO", run(tournamentGa));

        //Runs GA using tournament selection and OX1 crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.OX1);
        tournamentAverageBestDistances.put("OX1", run(tournamentGa));

        //Runs GA using tournament selection and ERC crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.ERC);
        tournamentAverageBestDistances.put("ERC", run(tournamentGa));
    }


    /**
     * Runs the GA using roulette wheel selection
     * Will run for each of the crossover methods
     * @param pop - The population to use
     */
    private void runRoulette(Population pop){
        //Runs GA using roulette wheel selection and PMX crossover
        GeneticAlgorithm tournamentGa = createGeneticAlgorithm(pop);
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.ROULETTE);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.PMX);
        rouletteAverageBestDistances.put("PMX", run(tournamentGa));

        //Runs GA using roulette wheel selection and APC crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.ROULETTE);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.APC);
        rouletteAverageBestDistances.put("APC", run(tournamentGa));

        //Runs GA using roulette wheel selection and CCO crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.ROULETTE);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.CCO);
        rouletteAverageBestDistances.put("CCO", run(tournamentGa));

        //Runs GA using roulette wheel selection and OX1 crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.ROULETTE);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.OX1);
        rouletteAverageBestDistances.put("OX1", run(tournamentGa));

        //Runs GA using roulette wheel selection and ERC crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.ROULETTE);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.ERC);
        rouletteAverageBestDistances.put("ERC", run(tournamentGa));

    }


    /**
     * Runs the GA using ranked selection
     * Will run for each of the crossover methods as well
     * @param pop - The population that is being used
     */
    private void runRanked(Population pop){
        //Runs GA using ranked based selection and RMX crossover
        GeneticAlgorithm tournamentGa = createGeneticAlgorithm(pop);
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.RANKED);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.PMX);
        rankedAverageBestDistances.put("PMX", run(tournamentGa));

        //Runs GA using ranked based selection and APC crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.RANKED);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.APC);
        rankedAverageBestDistances.put("APC", run(tournamentGa));

        //Runs GA using ranked based selection and CCO crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.RANKED);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.CCO);
        rankedAverageBestDistances.put("CCO", run(tournamentGa));

        //Runs GA using ranked based selection and OX1 crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.RANKED);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.OX1);
        rankedAverageBestDistances.put("OX1", run(tournamentGa));

        //Runs GA using ranked based selection and ERC crossover
        tournamentGa.setSelectionType(GeneticAlgorithm.SelectionType.RANKED);
        tournamentGa.setCrossoverType(GeneticAlgorithm.CrossoverType.ERC);
        rankedAverageBestDistances.put("ERC", run(tournamentGa));

    }


    /**
     * Runs each genetic algorithm over a set amount of runs
     * Will record the best distance at the end of the run and work out the average
     * for the amount of runs carried out so far. Will update after each run
     * @param ga - The genetic algorithm being used
     * @return - The best distance found at the end of a GA
     * This will be the average when the GA is ran over a number of iterations
     */
    private int run(GeneticAlgorithm ga){
        //Create The total distance over all of the runs
        int totalOfBestDistance = 0;
        //Keeps track of the number of runs it has been run for
        int numOfRuns = 0;
        //Loop through the set amount of runs
        for(int i=0; i<120; i++){
            //Run the GA
            ga.run();
            //Add to the total distance
            totalOfBestDistance += ga.getBestLastDistance();
            numOfRuns++;
        }
        //Work out the average and reset the GA for the next run
        int averageOfBestDistances = (totalOfBestDistance/numOfRuns);
        ga.resetGA();

        return averageOfBestDistances;
    }

}
