package Tests;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import Algorithm.GeneticAlgorithm;

/**
 * Class containing tests revolving around the GeneticAlgorithm class
 * @author - Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added tests to class
 * @see GeneticAlgorithm
 */
public class GATests {

    /**
     * Test for number of gens
     * <p>
     * Tests the correct number of gens is created
     */
    @Test
    public void testNumberOfGens(){
        GeneticAlgorithm g1 = new GeneticAlgorithm();
        //Sets the max number of gens
        g1.setNumOfGens(500);

        //Checks they are equal
        assertEquals(g1.getNumOfGens(), 500);
    }

    /**
     * Test for number of elites
     * <p>
     * Tests the correct number of elites are created
     */
    @Test
    public void testNumberOfElites(){
        GeneticAlgorithm g2 = new GeneticAlgorithm();
        //sets the number of elites
        g2.setNumOfElites(5);

        //Checks it was set correctly
        assertEquals(g2.getNumOfElites(), 5);
    }

    /**
     * Test for crossover rate
     * <p>
     * Tests the correct value for crossover rate is assigned
     */
    @Test
    public void testCrossoverRate(){
        GeneticAlgorithm g3 = new GeneticAlgorithm();
        //Sets the crossover rate
        g3.setCrossoverRate(0.95);

        //checks it has been set correctly
        assertEquals(g3.getCrossoverRate(), 0.95);
    }

    /**
     * Test for mutation rate
     * <p>
     * Tests the correct value for mutation rate is assigned
     */
    @Test
    public void testMutationRate(){
        GeneticAlgorithm g4 = new GeneticAlgorithm();
        //sets the mutation rate
        g4.setMutationRate(0.02);

        //Checks it has been set correctly
        assertEquals(g4.getMutationRate(), 0.02);
    }

    /**
     * Test for selection type
     * <p>
     * Tests the correct selection types
     */
    @Test
    public void testSelectionType(){
        GeneticAlgorithm g5 = new GeneticAlgorithm();
        //Sets the selection type and checks it has been set correctly
        g5.setSelectionType(GeneticAlgorithm.SelectionType.TOURNAMENT);
        assertEquals(g5.getSelectionType(), GeneticAlgorithm.SelectionType.TOURNAMENT);

        //Repeats for all selection types
        g5.setSelectionType(GeneticAlgorithm.SelectionType.ROULETTE);
        assertEquals(g5.getSelectionType(), GeneticAlgorithm.SelectionType.ROULETTE);

        g5.setSelectionType(GeneticAlgorithm.SelectionType.RANKED);
        assertEquals(g5.getSelectionType(), GeneticAlgorithm.SelectionType.RANKED);

    }

    /**
     * Test for crossover types
     * <p>
     * Tests the correct crossover types
     */
    @Test
    public void testCrossoverType(){
        GeneticAlgorithm g6 = new GeneticAlgorithm();
        //Sets the crossover type and checks it has been set correctly
        g6.setCrossoverType(GeneticAlgorithm.CrossoverType.PMX);
        assertEquals(g6.getCrossoverType(), GeneticAlgorithm.CrossoverType.PMX);

        //Repeats for all crossover methods
        g6.setCrossoverType(GeneticAlgorithm.CrossoverType.CCO);
        assertEquals(g6.getCrossoverType(), GeneticAlgorithm.CrossoverType.CCO);

        g6.setCrossoverType(GeneticAlgorithm.CrossoverType.ERC);
        assertEquals(g6.getCrossoverType(), GeneticAlgorithm.CrossoverType.ERC);

        g6.setCrossoverType(GeneticAlgorithm.CrossoverType.OX1);
        assertEquals(g6.getCrossoverType(), GeneticAlgorithm.CrossoverType.OX1);

        g6.setCrossoverType(GeneticAlgorithm.CrossoverType.APC);
        assertEquals(g6.getCrossoverType(), GeneticAlgorithm.CrossoverType.APC);

    }
}
