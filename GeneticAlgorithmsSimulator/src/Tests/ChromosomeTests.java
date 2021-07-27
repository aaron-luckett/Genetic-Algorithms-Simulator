package Tests;

import Genetics.Chromosome;
import Genetics.City;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing tests revolving around the Chromosome class
 * @author - Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added tests to class
 * @see Chromosome
 */
public class ChromosomeTests {

    /**
     * Method to create a city list
     * @return the city list with set coordinates
     */
    private City[] createRoute(){
        City city1 = new City(10,10);
        City city2 = new City(20,20);
        return new City[]{city1, city2};
    }

    /**
     * Test for the constructor
     * <p>
     * Checks that the array of cities is correct
     */
    @Test
    public void constructorTest(){
        //Creates array of cities
        City[] cities = createRoute();
        //Creates chromosome
        Chromosome chrome1 = new Chromosome(cities, false);

        //Checks for matching array list
        assertEquals(chrome1.getArray()[0], cities[0]);
        assertEquals(chrome1.getArray()[1], cities[1]);

        //Checks that array list is in correct order
        assertNotEquals(chrome1.getArray()[1], cities[0]);
        assertNotEquals(chrome1.getArray()[0], cities[1]);
    }

    /**
     * Test for the route distance calculator
     * <p>
     * Checks that the route distance of a chromosome is calculated correctly
     */
    @Test
    public void routeDistanceChecker(){
        //Creates array of cities
        City[] cities = createRoute();
        Chromosome chrome1 = new Chromosome(cities, false);

        //Calculates the distance of the chromosome
        int distance = chrome1.getDistance();
        //Checks it equals the correct value
        assertEquals(distance, 28);
        //Checks it does not equal the expected value
        assertNotEquals(20, distance);
    }

    /**
     * Test for the compare to method
     * <p>
     * Checks that when comparing two chromosomes distances, the correct value is returned
     */
    @Test
    public void compareToChecker(){
        //Creates array of cities
        City[] cities = createRoute();
        Chromosome chrome1 = new Chromosome(cities, false);

        //Creates second chromosome
        City city3 = new City(9,9);
        City city4 = new City(21,21);
        City[] cities2 = {city3, city4};
        Chromosome chrome2 = new Chromosome(cities2, false);

        //Compares distances
        int result = chrome1.compareTo(chrome2);
        //Checks the fist chrome is smaller
        assertTrue(result < 0);

        //Checks the second chrome is larger
        result = chrome2.compareTo(chrome1);
        assertTrue(result > 0);

        //Checks for an equals possibility if it were to occur
        result = chrome1.compareTo(chrome1);
        assertEquals(0, result);

    }

    /**
     * Test for the toString method
     * <p>
     * Checks that the toString method creates the correct string value
     */
    @Test
    public void toStringChecker(){
        //Creates array of cities
        City[] cities = createRoute();
        Chromosome chrome1 = new Chromosome(cities, false);

        //Checks the toString method returns the expected string layout
        assertEquals(chrome1.toString(), "[(" + 10 + "," + 10 + ")(" + 20 + "," + 20 + ")]");
    }

    /**
     * Second test for the toString method
     * <p>
     * Checks that the toString method creates the correct string value
     */
    @Test
    public void toStringChecker2(){
        //Creates array of cities
        City[] cities = createRoute();
        Chromosome chrome1 = new Chromosome(cities, false);

        //Checks for expected toString method call layout
        assertNotEquals(chrome1.toString(), "([" + 15 + "," + 15 + ")(" + 20 + "," + 20 + ")]");
    }
}
