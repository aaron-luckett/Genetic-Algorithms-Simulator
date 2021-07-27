package Algorithm;

import Genetics.Chromosome;
import Genetics.City;

import java.util.Random;

/**
 * Class that can carry out mutation
 * Mutation method chosen: Swap mutation but looping through the whole chromosome
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added shuffle mutation
 * @version 1.2 - Changed mutation method to insertion mutation
 * @version 1.3 - Final bug checks
 * @see Chromosome
 * @see City
 */
class Mutation {

    /**
     * Basic constructor to be able to access methods
     */
    Mutation(){}

    /**
     * Carries out the mutation method
     * Used to increase diversity throughout the population
     * @param toMutate - The chromosome/ route to swap around
     * @return - The altered chromosome, with a different route
     */
    Chromosome mutateChrome(Chromosome toMutate){
        //Creates an array of cities according to the route
        City[] cities = toMutate.getArray();

        //Swaps two cities over in the route
        Random random = new Random();
        swap(cities, random.nextInt(cities.length), random.nextInt(cities.length));
        swap(cities, random.nextInt(cities.length), random.nextInt(cities.length));
        swap(cities, random.nextInt(cities.length), random.nextInt(cities.length));

        //Turn list into chromosome
        return new Chromosome(cities, false);
    }


    /**
     * Swap function to swap over the cities
     * @param cities - List of cities aka the route
     * @param firstCtyIndex - The index of the first city to swap
     * @param secondCityIndex - The index of the second city to swap
     */
    private void swap(City[] cities, int firstCtyIndex, int secondCityIndex){
        City temporary = cities[firstCtyIndex];
        cities[firstCtyIndex] = cities[secondCityIndex];
        cities[secondCityIndex] = temporary;
    }
}
