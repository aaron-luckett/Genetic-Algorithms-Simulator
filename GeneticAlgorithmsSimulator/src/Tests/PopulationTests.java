package Tests;

import Genetics.Chromosome;
import Genetics.City;
import Genetics.Population;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing tests revolving around the Population class
 * @author - Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added tests to class
 * @see Chromosome
 * @see Population
 * @see City
 */
public class PopulationTests {

    /**
     * Test for the constructor
     * <p>
     * Test to see if the population is constructed properly
     * Will check the population size is as expected
     */
    @Test
    public void constructorChecker(){
        //Create population
        Population pop1 = new Population(500);

        //Checks the population size is correct
        assertEquals(pop1.getPopSize(), 500);
        assertNotEquals(pop1.getPopSize(), 0);
    }

    /**
     * Test for a new population
     * <p>
     * Will test when a new population is created it is initially empty
     */
    @Test
    public void freshPopArrayChecker(){
        //Create population
        Population pop2 = new Population(500);

        //Checks the population is empty when created
        assertEquals(pop2.getChromosomes().length, 0);
        assertNotEquals(pop2.getChromosomes().length, 500);
    }

    /**
     * Test for random population creation
     * <p>
     * Will check that when a random population is created it is created properly
     */
    @Test
    public void randomPopulationChecker(){
        //Create population
        Population pop3 = Population.getRandomPopulation(5, 20, false);

        //Checks the random population is the correct size
        assertEquals(pop3.size(), 20);
        assertNotEquals(pop3.size(), 500);
    }

    /**
     * Test for adding a chromosome
     * <p>
     * Will check that a chromosome is added properly
     * Will also check when the population is full it does not add any more chromosomes
     */
    @Test
    public void addingChromosomeChecker(){
        //Create population
        Population pop4 = new Population(1);
        pop4.add(new Chromosome(new City[] {new City(50,50)}, false));

        //Checks the chromosome has been added
        assertEquals(pop4.size(),1);
        assertNotEquals(pop4.size(), 0);

        //Try to add another chromosome
        pop4.add(new Chromosome(new City[] {new City(50,50)}, false));

        //Check that it doesn't get added because population is full
        assertEquals(pop4.size(),1);
        assertNotEquals(pop4.size(), 0);

    }

    /**
     * Tes for generating a population
     * <p>
     * Will check that a population is generated properly
     * Will also check that once full, no more generation can be done
     */
    @Test
    public void generatePopulationChecker(){
        //Create population
        Population pop5 = new Population(3);
        pop5.populateGeneration(new City[] {new City(20,20), new City(30,30), new City(40,40)}, true);

        //Checks it has been populated correctly
        assertEquals(pop5.size(), 3);
        assertNotEquals(pop5.size(), 0);

        //Try to add more chromosomes
        pop5.populateGeneration(new City[] {new City(21,20), new City(31,30), new City(41,40)}, true);

        //Checks that they couldn't be added
        assertEquals(pop5.size(), 3);
        assertNotEquals(pop5.size(), 0);
    }

    /**
     * Test for clearing the population
     * <p>
     * Will check that the population clears all chromosomes when called
     */
    @Test
    public void clearPopulationChecker(){
        //Create population
        Population pop6 = new Population(1);
        //Add chromosome and clear
        pop6.add(new Chromosome(new City[] {new City(50,50)}, false));
        pop6.clear();

        //Check population has been cleared
        assertEquals(pop6.size(), 0);
        assertNotEquals(pop6.size(), 1);
    }

    /**
     * Test for the get cities method
     * <p>
     * Will check that the city list requested is the correct one
     */
    @Test
    public void getCitiesChecker(){
        //Create population
        Population pop7 = new Population(1);
        City[] cityList = {new City(50,50)};
        pop7.add(new Chromosome(cityList, false));

        //Checks city list has been retrieved correctly
        City[] cities = pop7.getCities();
        assertEquals(cities[0], cityList[0]);
    }

    /**
     * Test for the get chromosome method
     * <p>
     * Will check the correct chromosomes are being retrieved when the method is called
     */
    @Test
    public void getChromeChecker(){
        //Create population
        Population pop8 = new Population(1);
        Chromosome chrome = new Chromosome(new City[] {new City(50,50)}, false);
        pop8.add(chrome);

        //Checks the chromosomes have been retrieved correctly
        Chromosome[] chromeList = pop8.getChromosomes();
        assertEquals(chromeList[0], chrome);
        assertEquals(chromeList.length, 1);
    }

    /**
     * Test for getting the best distance
     * <p>
     * Will check the fittest distance is collected when called
     */
    @Test
    public void bestDistanceChecker(){
        //Create population
        Population pop9 = new Population(1);
        Chromosome chrome = new Chromosome(new City[] {new City(50,50), new City(55,55)}, false);
        pop9.add(chrome);
        int distance = pop9.getFittest();

        //Checks the best distance has been retrieved correctly
        assertEquals(distance, 14);
    }

    /**
     * Test for the population copy method
     * <p>
     * Will check the population is copied over correctly
     */
    @Test
    public void populationCopyChecker(){
        //Create population
        Population pop9 = new Population(1);
        Chromosome chrome = new Chromosome(new City[] {new City(50,50), new City(55,55)}, false);
        pop9.add(chrome);

        //Checks the population has been copied correctly
        assertEquals(pop9.size(), pop9.size());
    }

    /**
     * Test for getting the best chromosome of the population
     * <p>
     * Will check the best chromosome is returned when method is called
     */
    @Test
    public void bestChromeChecker(){
        //Create population
        Population pop11 = new Population(1);
        Chromosome chrome = new Chromosome(new City[] {new City(50,50), new City(55,55)}, false);
        pop11.add(chrome);

        //Checks the fittest chrome is returned
        assertEquals(pop11.getTheFittest(), chrome);
    }
}
