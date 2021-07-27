package Genetics;

import Graphing.GraphPlot;

import java.io.*;
import java.util.*;

/**
 * Class to represent the population/generation of routes
 * Population is a collection of chromosomes (Routes)
 * Population is a collection of arrays containing city's in the order of travel
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added methods
 * @version 1.2 - Added get random pop methods
 * @version 1.3 - Added read from file methods
 * @version 1.31 - General code tidy
 * @version 1.4 - Fixed errors revolving around testing
 * @version 1.5 - Final bug fixes
 * @see City
 * @see Chromosome
 * @see Algorithm.GeneticAlgorithm
 */
public class Population implements Iterable<Chromosome>{
    private PriorityQueue<Chromosome> chromosomes;             //The chromosomes/routes in the population
    private int popSize;                                       //The size of the population


    /**
     * Constructor for population
     *
     * @param popSize - the population size
     */
    public Population(int popSize) {
        this.popSize = popSize;
        chromosomes = new PriorityQueue<>();
    }


    /**
     * Adds a chromosome to the population
     * @param chromosome - Chromosome to add to the pop
     */
    public void add(Chromosome chromosome) {
        //Check if pop is full
        if (chromosomes.size() == popSize) {
            System.out.println("Pop size already full");
        } else {
            chromosomes.add(chromosome);
        }
    }


    /**
     * Populates a generation with chromosomes
     *
     * @param cities  - list of cities
     * @param shuffle - Whether to shuffle the route or not
     */
    public void populateGeneration(City[] cities, boolean shuffle) {
        //Check if pop is full
        if (chromosomes.size() == popSize) {
            System.out.println("Population full!");
        } else {
            //While pop is not full keep adding chromosomes
            while (chromosomes.size() < popSize) {
                Chromosome chrome = new Chromosome(cities, shuffle);
                chromosomes.add(chrome);
            }
        }
    }


    /**
     * Clears the population
     */
    public void clear() {
        chromosomes.clear();
    }


    /**
     * Get an array of all the Cities.
     *
     * @return the array of Cities
     */
    public City[] getCities() {
        return chromosomes.peek().getArray().clone();
    }


    /**
     * Get an array of all the Chromosomes.
     *
     * @return the array of the Chromosomes
     */
    public Chromosome[] getChromosomes() {
        Chromosome[] chromeList = new Chromosome[chromosomes.size()];

        int i = 0;
        for (Chromosome chromo : chromosomes) {
            chromeList[i++] = chromo;
        }

        return chromeList;
    }


    /**
     * Returns size of chromosomes
     *
     * @return - size of population
     */
    public int size() {
        return chromosomes.size();
    }


    /**
     * Generate a Population of randomly generate Chromosomes.
     *
     * @param numOfCities the number of cities
     * @param sizeOfPop   the size of the population
     * @param shuffle     whether you will shuffle
     * @return a randomly generated Population
     */
    public static Population getRandomPopulation(int numOfCities, int sizeOfPop, Boolean shuffle) {
        City[] cities = new City[numOfCities];

        //Adds a city to the city list
        for (int i = 0; i < numOfCities; i++) {
            cities[i] = City.getRandomCity(shuffle);
        }

        //Creates a new population and adds new chromosomes/routes to the population
        //Will add shuffled routes for the size of the population
        Population population = new Population(sizeOfPop);
        for (int i = 0; i < sizeOfPop; i++) {
            population.add(new Chromosome(cities, shuffle));
        }
        return population;
    }


    /**
     * Gets a population from a file
     * @param filename - The name of the file to loaf
     * @param sizeOfPop - The size of the population
     * @param shuffle - Whether to shuffle the chromosomes
     * @return - The population
     * @throws IOException - If the file does not exist or cannot be found
     */
    public static Population getPopFromFile(String filename, int sizeOfPop, boolean shuffle) throws IOException {
        //Create storage for the x co-ordinates of the cities
        ArrayList<Integer> xCo = new ArrayList<>();
        ArrayList<Integer> yCo = new ArrayList<>();

        Population population = null;

        //Checks the file exists
        try {
            FileReader fileReader = new FileReader(new File(filename));
            BufferedReader in = new BufferedReader(fileReader);
            String line;
            while ((line = in.readLine()) != null) {
                //Add to the list the x co-ordinates and y co-ordinates in the text file
                String[] data = line.split(" ");
                xCo.add(Integer.parseInt(data[0]));
                yCo.add(Integer.parseInt(data[1]));
            }
            fileReader.close();

            //Create an array of cities with the co-ordinates gathered from the file
            City[] cities = new City[xCo.size()];
            for(int j=0; j<xCo.size(); j++){
                City city = new City(xCo.get(j), yCo.get(j));
                cities[j] = city;
            }

            //Create population from those cities
            population = new Population(sizeOfPop);
            for (int j = 0; j < sizeOfPop; j++) {
                population.add(new Chromosome(cities, shuffle));
            }
        } catch (FileNotFoundException errorMessage){
            System.out.println("FILE DOES NOT EXIST");
        }

        return population;
    }


    /**
     * Creates a population using set cities
     * @param numOfCities - The number of cities to create
     * @param sizeOfPop - The desired size of the population
     * @param shuffle - Whether to shuffle the chromosomes
     * @return - The created population
     */
    public static Population getSetPopulation(int numOfCities, int sizeOfPop, boolean shuffle){
        City[] cities = new City[numOfCities];

        City city = new City(60, 200);
        cities[0] = city;
        City city2 = new City(180, 200);
        cities[1] = city2;
        City city3 = new City(80, 180);
        cities[2] = city3;
        City city4 = new City(140, 180);
        cities[3] = city4;
        City city5 = new City(20, 160);
        cities[4] = city5;
        City city6 = new City(100, 160);
        cities[5] = city6;
        City city7 = new City(200, 160);
        cities[6] = city7;
        City city8 = new City(140, 140);
        cities[7] = city8;
        City city9 = new City(40, 120);
        cities[8] = city9;
        City city10 = new City(100, 120);
        cities[9] = city10;
        City city11 = new City(180, 100);
        cities[10] = city11;
        City city12 = new City(60, 80);
        cities[11] = city12;
        City city13 = new City(120, 80);
        cities[12] = city13;
        City city14 = new City(180, 60);
        cities[13] = city14;
        City city15 = new City(20, 40);
        cities[14] = city15;
        City city16 = new City(100, 40);
        cities[15] = city16;
        City city17 = new City(200, 40);
        cities[16] = city17;
        City city18 = new City(20, 20);
        cities[17] = city18;
        City city19 = new City(60, 20);
        cities[18] = city19;
        City city20 = new City(160, 20);
        cities[19] = city20;

        //Creates a new population and adds new chromosomes/routes to the population
        //Will add shuffled routes for the size of the population
        Population population = new Population(sizeOfPop);
        for (int i = 0; i < sizeOfPop; i++) {
            population.add(new Chromosome(cities, shuffle));
        }
        return population;

    }


    /**
     * Gets the population
     * @return - The population
     */
    public Population getPopulation() {
        Population population = new Population(popSize);
        chromosomes.forEach((chromosome) -> population.add(chromosome));
        return population;
    }


    /**
     * Sets the list of chromosomes/routes
     * @param chromosomes - List of chromosomes
     */
    public void setChromosomes(PriorityQueue<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }


    /**
     * Gets the population size
     * @return - Size of the population
     */
    public int getPopSize() {
        return popSize;
    }


    /**
     * Sets the population size
     * @param popSize - The size of the population
     */
    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }


    /**
     * Ways of printing out the data about the population
     * @return - String containing population values
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Population:");

        //Print out the chromosomes in the population
        //Prints out the distances relating to them as well
        for (Chromosome chromosome : chromosomes) {
            sb.append("\n");
            sb.append(chromosome);
            sb.append(" Distance: ");
            sb.append(chromosome.getDistance());
        }
        return new String(sb);
    }


    /**
     * Gets the best route/chromosome from the population
     * @return - The distance for the best chromosome
     */
    public int getFittest(){
        return chromosomes.peek().getDistance();
    }


    /**
     * Iterator for the population
     * @return - iterator for the population
     */
    public Iterator<Chromosome> iterator(){
        return chromosomes.iterator();
    }


    /**
     * Gets the average fitness for the population
     * @return - The average distance for that population
     */
    public int getAverageFitness(){
        int totalDistance = 0;
        //Loops through the population's chromosomes and adds the distance together
        for(Chromosome chromosome : chromosomes){
            totalDistance += chromosome.getDistance();
        }
        //Gets the average distance
        return totalDistance/chromosomes.size();
    }


    /**
     * Gets a random chromosome from the population
     * @return - A chosen chromosome
     */
    public Chromosome getRandomChromosome(){
        Chromosome[] array = new Chromosome[chromosomes.size()];

        int i = 0;
        for (Chromosome chromo : chromosomes) {
            array[i++] = chromo;
        }

        Random r=new Random();
        int randomNumber=r.nextInt(chromosomes.size());
        return array[randomNumber];
    }


    /**
     * Removes the best value from the current generation to add to the next generation
     * @return - The best chromosome to add
     */
    public Chromosome removeEliteValue(){
        return chromosomes.poll();
    }


    /**
     * Gets and returns a copy of the current population
     * @return - A copy of the current population
     */
    public Population copyPopulation () {
        Population population = new Population(popSize);
        chromosomes.forEach(population::add);
        return population;
    }


    /**
     * Returns the fittest chromosome
     * @return - The best chromosome/route
     */
    public Chromosome getTheFittest(){
        return chromosomes.peek();
    }
}
