package Algorithm;

import Genetics.Chromosome;
import Genetics.Population;
import Graphing.GraphPlot;
import Graphing.TSPGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Class to run the genetic algorithms for a set amount of generations
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial creation
 * @version 1.1 - Added methods to create the initial population
 * @version 1.2 - Added so elitism can be done
 * @version 1.3 - Added Selection types
 * @version 1.4 - Added Crossover types
 * @version 1.5 - Added graphing
 * @version 1.51 - General code tidy and commenting
 * @version 1.6 - Final bug checks
 * @see Population
 * @see Crossover
 * @see Mutation
 * @see Selection
 * @see Set
 */
public class GeneticAlgorithm {
    //Parameters
    private Population population;                          //Current Population for current generation
    private Population initialPopulation;                   //Initial population assigned at start
    private int numOfGens;                                  //Maximum number of generations/epochs
    private int numOfElites;                                //Number of elites to pass directly down from current population to next population
    private double crossoverRate;                           //The chance of a crossover function being carried out
    private double mutationRate;                            //The chance of a mutation occurring
    private SelectionType selectionType;                    //Selection method to use
    private CrossoverType crossoverType;                    //Crossover method being used
    private boolean multiSim;

    //Results
    private int bestFirstGenDistance;                       //The shortest distance in the first generation
    private int bestLastDistance;                           //The shortest distance in the final generation
    private int averageFirstGenDistance;                    //The average distance in the first generations population
    private int averageLastGenDistance;                     //The average distance in the final generations population
    private ArrayList<Integer> averageDistOfEachGen;        //Array containing the average distance of each generations population
    private ArrayList<Integer> bestDistanceOfEachGen;       //Array containing the best distance of each generations population


    /**
     * Creates a constructor for a genetic algorithm
     * Assigns values that it will use
     */
    public GeneticAlgorithm(){
        //Sets values
        //Creates default values at first
        initialPopulation = Population.getRandomPopulation(10, 10, true);
        population = initialPopulation.copyPopulation();
        numOfGens = 10;
        numOfElites = 10;
        mutationRate = 0.05;
        crossoverRate = 0.8;
        selectionType = SelectionType.TOURNAMENT;
        crossoverType = CrossoverType.PMX;
        multiSim = true;

        averageDistOfEachGen = new ArrayList<>();
        bestDistanceOfEachGen = new ArrayList<>();
    }


    /**
     * Returns the current population being worked on
     * @return - Current population
     */
    public Population getPopulation() {
        return population;
    }


    /**
     * Sets the population to desired values
     * Overrides the values assigned by the constructor
     * @param population - Population you wish to be working on
     */
    public void setPopulation(Population population) {
        if(population != null) {
            //Sets the initial population as well as the current population to the initial gen
            initialPopulation = population;
            this.population = initialPopulation.copyPopulation();
        } else {
            population = Population.getRandomPopulation(35, 500, true);
            initialPopulation = population;
            this.population = initialPopulation.copyPopulation();
            System.out.println("Error loading population, loading random population");
        }

        //Calculates the best and average distances of the first generation
        bestFirstGenDistance = population.getFittest();
        averageFirstGenDistance = population.getAverageFitness();

        //Adds them to the list of generations
        averageDistOfEachGen.add(averageFirstGenDistance);
        bestDistanceOfEachGen.add(bestFirstGenDistance);
    }


    /**
     * Runs the genetic algorithm
     */
    public void run(){
        //Print details
        System.out.println();
        System.out.println("SELECTION METHOD: " + getSelectionType());
        System.out.println("CROSSOVER METHOD USED: " + getCrossoverType());
        System.out.println();

        System.out.println("START BEST FIT: " + getBestFirstGenDistance());
        System.out.println("START AVERAGE FIT: " + getAverageFirstGenDistance());
        System.out.println();

        Chromosome bestRoute = null;
        TSPGraph displayRoute = null;
        //If single sim, load up the visual solver
        if(!multiSim) {
            displayRoute = new TSPGraph(population.getCities());
            bestRoute = population.getTheFittest();
            displayRoute.drawRoute(bestRoute);
            delay(5000);
        }

        //Start timer
        double startTime = System.currentTimeMillis();

        for(int i=0; i<getNumOfGens(); i++){
            System.out.println("Generation: " + (i+1));
            population = createNextGen();
            //System.out.println(newPop);
            //System.out.println(newPop.size());

            //Calculate the best distance found
            System.out.println("Best: " + population.getFittest());
            //calculate average distance of pop
            System.out.println("Average: " + population.getAverageFitness());
            System.out.println();

            //Append to the relating list
            averageDistOfEachGen.add(population.getAverageFitness());
            //Append to relating list
            bestDistanceOfEachGen.add(population.getFittest());

            if(!multiSim) {
                //Gets the best route in the population
                Chromosome bestRouteLatestPop = population.getTheFittest();
                //Checks to see if the new route is better than previous
                if (!bestRouteLatestPop.equals(bestRoute)) {
                    //If better update the window to show the new better route
                    assert displayRoute != null;
                    displayRoute.drawRoute(bestRouteLatestPop);
                }
            }

            //If final generation, bet the best route fitness and average fitness
            if((i+1) == getNumOfGens()){
                bestLastDistance = population.getFittest();
                averageLastGenDistance = population.getAverageFitness();
            }
        }

        if(!multiSim) {
            finalResultPrint(startTime);
            graphResult();
        } else {
            System.out.println("Finished");
            //Get the average dist from the final pop
            System.out.println("FINAL AVERAGE DISTANCE FOUND: " + getAverageLastGenDistance());
            //Get the best distance from the final pop
            System.out.println("BEST DISTANCE FOUND: " + getBestLastDistance());
        }
    }


    /**
     * Creates the next generation using the current population/generation
     * @return - The next generation/population
     */
    private Population createNextGen(){
        //Creates the next population ready to assign values
        Population nexGeneration = new Population(population.size());

        //Gets the elites from the previous population
        carryOutElitism(nexGeneration);

        //ArrayList<Chromosome> children = new ArrayList<Chromosome>();
        HashSet<Chromosome> children = new HashSet<>();
        while(nexGeneration.size() < population.size()){

            //Select parents from previous population
            Chromosome parentOne = carryOutSelection();
            Chromosome parentTwo = carryOutSelection();

            //Stops a parent being selected twice
            boolean sameParent = true;
            while(sameParent){
                if(!(parentOne.equals(parentTwo))){
                    sameParent = false;
                } else {
                    parentTwo = carryOutSelection();
                }

            }

            //Perform crossover
            double chanceOfCrossOver = Math.random();
            if(chanceOfCrossOver <= crossoverRate){
                ArrayList<Chromosome> newChildren = carryOutCrossover(parentOne,parentTwo);
                parentOne = newChildren.get(0);
                parentTwo = newChildren.get(1);
            }

            //Perform mutation
            double chanceOfMutation1 = Math.random();
            double chanceOfMutation2 = Math.random();
            if(chanceOfMutation1 <= mutationRate){
                Mutation mutate = new Mutation();
                parentOne = mutate.mutateChrome(parentOne);
            }
            if(chanceOfMutation2 <= mutationRate){
                Mutation mutate = new Mutation();
                parentTwo = mutate.mutateChrome(parentTwo);
            }

            //Increases diversity by only allowing for different routes to be in the population
            //if(!children.contains(parentOne)){
                //children.add(parentOne);
                //nexGeneration.add(parentOne);
            //}

            children.add(parentOne);
            //Check to see if full
            if(nexGeneration.size() < population.size()){
                //if(!children.contains(parentTwo)){
                    children.add(parentTwo);
                    nexGeneration.add(parentTwo);
                //}
            }
        }
        return nexGeneration;
    }


    /**
     * Function to take the best chromosomes from the previous generation forward to the next one
     * Used to keep the best genes in the gene pool
     * @param nextGeneration - The next population being created
     */
    private void carryOutElitism(Population nextGeneration){
        PriorityQueue<Chromosome> priorityQueue = new PriorityQueue<>();
        //Add population to priority queue to order chromosomes
        for (Chromosome chromosome : population) {
            priorityQueue.add(chromosome);
        }

        //Put elites in new population
        for (int i = 0; i < numOfElites; i++) {

            Chromosome chromosome = priorityQueue.poll();

            nextGeneration.add(chromosome);
        }
    }


    /**
     * Carries out the chosen selection method
     * @return - The parent selected
     */
    private Chromosome carryOutSelection(){
        Selection selection = new Selection();
        Chromosome parentToBe = null;
        if(selectionType == SelectionType.TOURNAMENT){
            //Sample size for tournament selection
            int tournamentSampleSize = 3;
            parentToBe = selection.tournamentSelection(population, tournamentSampleSize);
        } else if (selectionType == SelectionType.ROULETTE){
            parentToBe = selection.rouletteWheelSelection(population);
        } else if (selectionType == SelectionType.RANKED){
            parentToBe = selection.rankedSelection(population);
        }
        return parentToBe;
    }


    /**
     * Carries out the selected crossover method
     * @param parentOne - First parent to be involved in crossover
     * @param parentTwo - The second parent to be involved in crossover
     * @return - The children produced
     */
    private ArrayList<Chromosome> carryOutCrossover(Chromosome parentOne, Chromosome parentTwo){
        Crossover crossover = new Crossover();
        ArrayList<Chromosome> children = null;
        if(crossoverType == CrossoverType.CCO){
            children = crossover.CCSCrossover(parentOne, parentTwo);
        } else if (crossoverType == CrossoverType.PMX){
            children = crossover.pmxCrossover(parentOne, parentTwo);
        } else if (crossoverType == CrossoverType.APC){
            children = crossover.alternativePosCrossover(parentOne, parentTwo);
        } else if (crossoverType == CrossoverType.OX1){
            children = crossover.firstOrderCrossover(parentOne, parentTwo);
        } else if (crossoverType == CrossoverType.ERC){
            children = crossover.edgeRecombination(parentOne, parentTwo);
        }
        return children;
    }


    /**
     * Gets the initial population
     * @return - The initial population
     */
    public Population getInitialPopulation() {
        return initialPopulation;
    }


    /**
     * Sets the initial population
     * @param initialPopulation - The first generation
     */
    public void setInitialPopulation(Population initialPopulation) {
        this.initialPopulation = initialPopulation;
    }


    /**
     * Gets the number of generations that will be run
     * @return - Max number of generations
     */
    public int getNumOfGens() {
        return numOfGens;
    }


    /**
     * Sets the number of max generations
     * @param numOfGens - The number of generations the simulation will be run for
     */
    public void setNumOfGens(int numOfGens) {
        this.numOfGens = numOfGens;
    }


    /**
     * Gets the number of elite chromosomes
     * @return - Number of elites
     */
    public int getNumOfElites() {
        return numOfElites;
    }


    /**
     * Sets the number of elite chromosomes
     * @param numOfElites - Number of elite members per population
     */
    public void setNumOfElites(int numOfElites) {
        this.numOfElites = numOfElites;
    }


    /**
     * Gets the mutation rates
     * @return - The mutation rate
     */
    public double getMutationRate() {
        return mutationRate;
    }


    /**
     * Gets the crossover rate
     * @return - The crossover rate
     */
    public double getCrossoverRate(){
        return crossoverRate;
    }


    /**
     * Sets the crossover rate
     * @param crossoverRate - The crossover rate
     */
    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }


    /**
     * Sets the mutation rate
     * @param mutationRate - The desired mutation rate
     */
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }


    /**
     * Gets the best distance from the best chromosome
     * @return - The smallest distance
     */
    private int getBestFirstGenDistance() {
        return bestFirstGenDistance;
    }


    /**
     * Gets the average distance from the first generation
     * @return - The average distance from the first generation
     */
    private int getAverageFirstGenDistance(){
        return averageFirstGenDistance;
    }


    /**
     * Gets the best distance from the final generation
     * @return - The smallest from the final generation
     */
    public int getBestLastDistance() {
        return bestLastDistance;
    }


    /**
     * Gets the average distance from each of the generations
     * @return - A list of the average distances from each generation
     */
    ArrayList<Integer> getAverageDistOfEachGen() {
        return averageDistOfEachGen;
    }


    /**
     * Gets the best distance of each generation
     * @return - A list of best distances for each generation
     */
    ArrayList<Integer> getBestDistanceOfEachGen() {
        return bestDistanceOfEachGen;
    }


    /**
     * The range of selection types available
     */
    public enum SelectionType{
        TOURNAMENT,
        RANKED,
        ROULETTE,
    }


    /**
     * The range of crossover types available
     */
    public enum CrossoverType{
        PMX,
        APC,
        OX1,
        CCO,
        ERC
    }


    /**
     * Sets the crossover type
     * @param crossoverType - The crossover type
     */
    public void setCrossoverType(CrossoverType crossoverType) {
        this.crossoverType = crossoverType;
    }


    /**
     * Sets the selection type
     * @param selectionType - The selection type
     */
    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    /**
     * Gets the crossover type
     * @return - The crossover type
     */
    public CrossoverType getCrossoverType() {
        return crossoverType;
    }


    /**
     * Gets the selection type
     * @return - The selection type
     */
    public SelectionType getSelectionType() {
        return selectionType;
    }


    /**
     * Resets the GA for next simulation
     */
    public void resetGA(){
        population = initialPopulation.copyPopulation();
        bestLastDistance = 0;
        bestDistanceOfEachGen = new ArrayList<>();
        averageLastGenDistance = 0;
        averageDistOfEachGen = new ArrayList<>();
    }


    /**
     * Calls the graph plot class to display graph of the decrease in distance
     */
    private void graphResult(){
        GraphPlot graphPlot = new GraphPlot("Graph For Distances", bestDistanceOfEachGen, numOfGens, averageDistOfEachGen);
        graphPlot.pack();
        graphPlot.setVisible(true);

    }


    /**
     * Provides a delay between computing and drawing the best route
     * @param ms - The amount of milliseconds to delay by
     */
    private static void delay (int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Sets whether the simulation will be a multi simulation
     * @param multiSim - Whether or not it will be a multi simulation
     */
    public void setMultiSim(boolean multiSim) {
        this.multiSim = multiSim;
    }


    /**
     * Get the average last generation distance
     * @return - THe average distance of the last generation
     */
    int getAverageLastGenDistance() {
        return averageLastGenDistance;
    }


    /**
     * Prints out the final results and sections of the GA used
     */
    private void finalResultPrint(double startTime){
        double runTime = workOutRunTime(startTime);
        System.out.println("*******************************************");
        System.out.println("Genetic Algorithm Finished");
        System.out.println("Genetic Algorithm Stats");
        System.out.println("Selection Method: " + getSelectionType());
        System.out.println("Crossover Method: " + getCrossoverType());
        System.out.println("Crossover Rate: " + getCrossoverRate());
        System.out.println("Mutation Rate: " + getMutationRate());
        System.out.println("Number of Elites: " + getNumOfElites());
        System.out.println("Number of Generations: " + getNumOfGens());
        System.out.println();
        System.out.println("Starting Best Distance: " + getBestFirstGenDistance());
        System.out.println("Ending Best Distance: " + getBestLastDistance());
        System.out.println();
        System.out.println("Starting Average Distance: " + getAverageFirstGenDistance());
        System.out.println("Ending Average Distance: " + getAverageLastGenDistance());
        System.out.println();
        System.out.println("Runtime: " + runTime + " Seconds");
        System.out.println("*******************************************");
        System.out.println();
    }

    private double workOutRunTime(double startTime){
        double endTime = System.currentTimeMillis();
        return ((endTime - startTime) / 1000);
    }

}
