package Genetics;

import java.util.Random;

/**
 * Class to represent a chromosome
 * Chromosome is a route that will be taken
 * There are many chromosomes in the population
 * Each chromosome contains a set amount of genes (Cities)
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added to string method
 * @version 1.11 - General code tidy
 * @version 1.2 - Final bug checks
 * @see City
 *
 */
public class Chromosome implements Comparable<Chromosome> {
    private int distance = -1; //Will update in the code. The distance to travel the entire route
    private City[] cities; //Array of the cities in order of the path


    /**
     * Creates a chromosome which is a set of cities in order of travel
     * @param cities - The list of cities
     */
    public Chromosome(City[] cities , boolean shuffle){
        if(!shuffle){
            //Clones the list of cities passed to it
            this.cities = cities.clone();
        } else {
            this.cities = cities.clone();
            mixCities();
        }
    }


    /**
     * Essentially shuffles the array around
     */
    private void mixCities(){
        //Swaps over the cities to get different chromosomes
        for(int i=0; i< cities.length; i++){
            int index = new Random().nextInt(cities.length);
            swapGenes(i,index);
        }
    }


    /**
     * Swaps over the elements
     * @param i - First element to swap
     * @param j - Second element to swap
     */
    private void swapGenes(int i, int j){
        City temp = cities[i];
        cities[i] = cities[j];
        cities[j] = temp;
    }


    /**
     * Compares the distance of two different chromosomes
     * @param comparisionChrome - Second chromosome
     * @return - The difference in distance for the two paths/chromosomes
     */
    @Override
    public int compareTo(Chromosome comparisionChrome){
        return getDistance() - comparisionChrome.getDistance();
    }


    /**
     * Gets the array of cities
     * @return - The cities in the order they are travelled in
     */
    public City[] getArray () {
        return cities.clone();
    }


    /**
     * Gets the distance of a chromosome/route
     * @return - The distance of that route.chromosome
     */
    public int getDistance(){
        if(distance != -1){
            return distance;
        } else {
            double routeDistance = 0;
            //Works out the distance from the first city to the last in the array
            for(int i=0; (i<cities.length - 1); i++){
                routeDistance += cities[i].calcDistance(cities[i+1]);
            }
            //Adds the final distance between the last city in the array and the first
            routeDistance += cities[cities.length-1].calcDistance(cities[0]);
            this.distance = (int)routeDistance;
        }
        return distance;
    }


    /**
     * String constructor for chromosome
     * Allows to see the contents of the chromosome in a nice form
     * @return - String with chromosome contents
     */
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("[");
        for (City item : cities) {
            sb.append("(");
            sb.append(item.getxCoordinate());
            sb.append(",");
            sb.append(item.getyCoordinate());
            sb.append(")");
        }
        sb.append("]");
        return new String(sb);
    }


    /**
     * Method to return fitness for roulette selection
     * Roulette fitness is the inverse of the route distance
     * @return - Inverse of route distance
     */
    public double getChromeFitness(){
        return (1.00/getDistance());
    }
}