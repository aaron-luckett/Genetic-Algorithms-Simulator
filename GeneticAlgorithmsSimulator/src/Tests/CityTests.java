package Tests;


import Genetics.City;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Class containing tests revolving around the City class
 * @author - Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added tests to class
 * @see City
 */
public class CityTests {

    /**
     * Test for constructor
     * <p>
     * Tests a city is created properly
     */
    @Test
    public void constructorTest(){
        //Creates city
        City city1 = new City(20,20);

        //Checks the co-ordinates are correct
        assertEquals(city1.getxCoordinate(), 20);
        assertEquals(city1.getyCoordinate(), 20);
    }

    /**
     * Second test for constructor
     * <p>
     * Tests a city is created properly
     */
    @Test
    public void constructorTest2(){
        //Creates city
        City city1 = new City(20, 20);

        //Checks the co-ordinates are correct
        assertNotEquals(city1.getxCoordinate(), 25);
        assertNotEquals(city1.getyCoordinate(), 25);
    }

    /**
     * Test for distance calculating between two cities
     * <p>
     * Checks the distance between two cities is calculated correctly
     */
    @Test
    public void distanceTest(){
        //Creates two cities
        City city1 = new City(17,16);
        City city2 = new City(20,20);

        //Calculates the distance between them
        double distance = city1.calcDistance(city2);
        //checks the distance is correct
        assertEquals(distance, 5.0);
    }

    /**
     * Second test for distance calculating between two cities
     * <p>
     * Checks the distance between two cities is calculated correctly
     */
    @Test
    public void distanceTest2(){
        //Creates two cities
        City city1 = new City(17,16);
        City city2 = new City(20,20);

        //Checks the distance between them is correct
        double distance = city1.calcDistance(city2);
        assertNotEquals(distance, 5.5);
    }

    /**
     * Test to check the toString method
     * <p>
     * Checks the toString method creates the correct string
     */
    @Test
    public void toStringCheck(){
        //Creates city and string it should produce
        City city1 = new City(20,20);
        String string = (20 + ", " + 20);
        //Compares expected to resulting toString method call
        assertEquals(city1.toString(), string);
    }

    /**
     * Second test to check the toString method
     * <p>
     * Checks the toString method creates the correct string
     */
    @Test
    public void toStringCheck2(){
        //Creates city and string it should not display
        City city1 = new City(20,20);
        String string = (21 + "h " + 21);
        //Compares not expected with resulting toString method call
        assertNotEquals(city1.toString(), string);
    }

}
