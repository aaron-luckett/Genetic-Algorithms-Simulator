package Algorithm;

import Genetics.Chromosome;
import Genetics.Population;
import java.util.*;

/**
 * Selection class that will carry out a range of different selection functions depending on user input
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial creation and tournament selection
 * @version 1.1 - Added roulette wheel selection
 * @version 1.11 - Fixed roulette wheel selection
 * @version 1.12 - Fixed tournament selection by adding odds of not picking fittest constant
 * @version 1.2 - Added ranked selection
 * @version 1.3 - Improved roulette wheel selection to get better results
 * @version 1.4 - Improvements to roulette selection
 * @version 1.5 - More improvements to roulette selection
 * @version 1.6 - More improvements to roulette selection
 * @version 1.7 - Final bug checks
 * @see Chromosome
 * @see Population
 */
class Selection {

    private static final int oddsOfUnderdog = 5;        //One in five chance of not actually picking the tournament winner

    /**
     * Basic constructor for selection class
     */
    Selection(){}


    /**
     * Carries out tournament selection
     * @param population - The current population to select from
     * @param sampleSize - The size of the tournament
     * @return - The chromosome chosen to be a parent
     */
    Chromosome tournamentSelection(Population population, int sampleSize) {
        Chromosome[] chromes = population.getChromosomes();
        Random random = new Random();

        //Create a list of random participants
        ArrayList<Chromosome> tournamentParticipants = new ArrayList<>();
        //According to the size of the sample size, currently set to 2
        for (int i = 0; i < sampleSize; i++) {
            //Select as random route to enter into the tournament and add to list
            Chromosome toCompete = chromes[random.nextInt(chromes.length)];
            tournamentParticipants.add(toCompete);
        }

        //Loop through the participants and pick the one with the best fitness
        Chromosome best = null;
        for (Chromosome c : tournamentParticipants) {
            if (best == null) {
                best = c;
            } else if (best.getDistance() > c.getDistance()) {
                best = c;
            }
        }

        //Small chance of not picking the fittest
        //This is done to increase diversity for the next generation as without it you get premature convergence
        if (random.nextInt(oddsOfUnderdog) == 0 && tournamentParticipants.size() != 1) {
            //Removes the fittest
            tournamentParticipants.remove(best);
            //Gets a random participant instead
            return tournamentParticipants.get(random.nextInt(tournamentParticipants.size()));
        }
        return best;
    }


    /**
     * Carries out roulette wheel selection
     * Selected biased around the fitness of the chromosome
     * @param population - The population to choose from
     * @return - the selected chromosome
     */
    Chromosome rouletteWheelSelection(Population population){
        //Turn population into an array list
        Chromosome[] chromes = population.getChromosomes();

        //Create array relating to the fitness of the chromosomes
        double[] fitnessValues = new double[chromes.length];
        for(int i=0; i<fitnessValues.length; i++){
            fitnessValues[i] = (chromes[i].getChromeFitness());
        }
        //System.out.println("Fitness array: " + Arrays.toString(fitnessValues));

        //get the total fitness of all of the chromosomes in the population
        double totalFitness = 0;
        for (double fitnessValue : fitnessValues) {
            totalFitness += fitnessValue;
        }
        //System.out.println("Total fitness: " + totalFitness);

        //Create an array of the ratio of the pie chart each chromosome will have
        double runningFitness = 0;
        double[] fitnessRatio = new double[fitnessValues.length];
        for(int i=0; i<fitnessRatio.length; i++){
            //Probability is the fitness of the chrome over the total fitness
            double probability = ((fitnessValues[i] / totalFitness));
            //The ratio is a running total, will go up from 0 to 1
            fitnessRatio[i] = (runningFitness + probability);
            runningFitness += probability;
        }
        //System.out.println("Ratios: " + Arrays.toString(fitnessRatio));

        //Randomly assign a stopping point
        double selectionPoint = Math.random();
        Chromosome selectedChrome = null;
        //System.out.println("selection point: " + selectionPoint);

        //Loop until the running total is is equal to or greater than the selection point
        //Loop through the population adding the fitness of the chromosome to the running total
        for(int i=0; i<fitnessRatio.length; i++){
            if(selectionPoint < fitnessRatio[0]){
                selectedChrome = chromes[0];
                break;
            } else {
                if(fitnessRatio[i] < selectionPoint){
                    if(fitnessRatio[i+1] >= selectionPoint) {
                        selectedChrome = chromes[i+1];
                        break;
                    }
                }
            }
        }

        //int[] distanceArray = new int[fitnessRatio.length];
        //for(int i=0; i<distanceArray.length; i++){
            //distanceArray[i] = chromes[i].getDistance();
        //}
        //System.out.println("Chrome Distance: " + Arrays.toString(distanceArray));
        //System.out.println("Selected chrome: " + selectedChrome.getDistance());

        return selectedChrome;
    }


    /**
     * Carries out ranked selection to choose a parent
     * @param population - the population to choose from
     * @return - The selected chromosome to be a parent
     */
    Chromosome rankedSelection(Population population){
        //Turn population into array
        Chromosome[] chromes = population.getChromosomes();
        //Turn into priority queue in order to order by best distance
        PriorityQueue<Chromosome> reverseRank = new PriorityQueue<>(Arrays.asList(chromes));

        //Create an array in the order of best distance first
        Chromosome[] chrome = new Chromosome[population.size()];
        for(int i=0; i<population.size(); i++){
            chrome[i] = reverseRank.poll();
        }

        //Flips so worst distance at start of array
        //Easier to get rank values
        Collections.reverse(Arrays.asList(chrome));

        //Get the total rank score of the array
        int rankedTotal = 0;
        for(int i=0; i<chrome.length; i++){
            rankedTotal += ((i) + 1);
        }

        //Create random stopping point
        Random random = new Random();
        int stoppingPoint = random.nextInt(rankedTotal);

        Chromosome selectedChrome = null;
        int runningTotal = 0;
        //Loop through flipped array and keep track of running total
        for(int j=0; j<chrome.length; j++){
            runningTotal += (j+1);
            //If running total reached stopping point, select the chrome that pushed it over the edge
            if(runningTotal >= stoppingPoint){
                selectedChrome = chrome[j];
                break;
            }
        }
        return selectedChrome;
    }
}
