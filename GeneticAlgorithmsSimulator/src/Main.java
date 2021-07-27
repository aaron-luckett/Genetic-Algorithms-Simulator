import Algorithm.GeneticAlgorithm;
import Algorithm.Set;
import SimAll.SimAll;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main class for initial startup of simulator
 * @author - Aaron Luckett (aal16)
 */
public class Main {

    /**
     * Prints the menu options
     */
    private static void printMenu(){
        System.out.println("WELCOME, PRESS CORRECT KEY TO START");
        System.out.println("1:\t Simulation Mode");
        System.out.println("2:\t Individual Test");
        System.out.println("3:\t Full Simulation");
        System.out.println("4:\t More information");
        System.out.println("Q:\t Quit");
    }

    /**
     * Read what the user wants to do with the program
     * @param userInput - The user input for what they want to run
     * @param input - Scanner to read the input
     * @throws IOException - Invalid input is detected
     */
    private static void readInput(String userInput, Scanner input) throws IOException {
        while(!userInput.equals("Q") && !userInput.equals("q")){
            switch (userInput) {
                case "1":
                    System.out.println("Simulation Mode");
                    Set.getGA(true, false);
                    System.out.println();
                    break;
                case "2":
                    System.out.println("Individual Test");
                    Set.getGA(false, false);
                    //geneticAlgorithm.run();
                    System.out.println();
                    break;
                case "3":
                    System.out.println("Full Simulation");
                    GeneticAlgorithm geneticAlgorithm1 = Set.getGA(true, true);
                    SimAll fullSim = new SimAll();
                    fullSim.runAllSims(geneticAlgorithm1.getPopulation());
                    break;
                case "4":
                    System.out.println("Help");
                    printHelp();
                    break;
                default:
                    System.out.println("Invalid Choice");
                    System.out.println();
            }
            System.out.print("Please Select Your Choice: ");
            userInput = input.next();
        }
    }

    /**
     * Prints out the help menu to give more details about the options
     */
    private static void printHelp(){
        System.out.println();
        System.out.println("Simulation Mode");
        System.out.println("Run a combination of a selection method and crossover method multiple times to get an average");
        System.out.println();
        System.out.println("Individual Test");
        System.out.println("Select a combo and run one single test");
        System.out.println();
        System.out.println("Full Simulation");
        System.out.println("Run all coded combinations over a set amount of runs");
        System.out.println();
    }

    public static void main (String[] args) throws IOException {
        //GeneticAlgorithm geneticAlgorithm = Set.getSetCityGA();
        //System.out.print(geneticAlgorithm.getPopulation());

        printMenu();
        System.out.print("Please Select Your Choice: ");

        Scanner input = new Scanner(System.in);
        String userInput = input.next();
        readInput(userInput, input);
    }
}
