package Algorithm;

import Genetics.Chromosome;
import Genetics.City;

import java.util.*;

/**
 * Crossover class that will carry out a range of crossover methods depending on user input
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added pmx crossover
 * @version 1.2 - Added cycle crossover
 * @version 1.3 - Added APC crossover
 * @version 1.31 - General code tidy commenting
 * @version 1.4 - Added first order crossover
 * @version 1.5 - Added Edge Recombination crossover
 * @version 1.51 - Fixed error with ER crossover
 * @version 1.6 - Removed duplicate code and tidies crossover methods
 * @version 1.7 - Final bug fixes/checks
 * @see Chromosome
 * @see City
 */
class Crossover {

    /**
     * Basic constructor to be called by the GA
     */
    Crossover(){}


    /**
     * Create Parent in the correct format
     * @param parent - Parent to turn into the array format
     */
    private City[] createParentArray(Chromosome parent){
        return parent.getArray();
    }


    /**
     * Create the children in the correct format
     * @param parent - The parent array
     */
    private City[] createChildArray(City[] parent){
        return new City[parent.length];
    }


    /**
     * Creates the array list of the children created
     * @param childOne - First child
     * @param childTwo - Second child
     * @return - Array list containing both children
     */
    private ArrayList<Chromosome> createChildrenTogether(Chromosome childOne, Chromosome childTwo){
        ArrayList<Chromosome> children = new ArrayList<>();
        children.add(childOne);
        children.add(childTwo);
        return children;
    }


    /**
     * Calls the main crossover method twice to get two children
     * @param parentOne - First parent to use for the crossover
     * @param parentTwo - Second parent to use for the crossover
     * @return - Array list containing both of the children
     */
    ArrayList<Chromosome> edgeRecombination(Chromosome parentOne, Chromosome parentTwo){
        ArrayList<Chromosome> children = new ArrayList<>();
        children.add(carryOutEdge(parentOne, parentTwo));
        children.add(carryOutEdge(parentTwo, parentOne));
        return children;
    }


    /**
     * Carries out PMX crossover
     * @param p1 - First parent
     * @param p2 - Second Parent
     * @return - Array list containing the two children
     */
    ArrayList<Chromosome> pmxCrossover(Chromosome p1, Chromosome p2){
        //Create parents
        City[] parent1 = createParentArray(p1);
        City[] parent2 = createParentArray(p2);

        //Create children
        City[] child1 = createChildArray(parent1);
        City[] child2 = createChildArray(parent2);

        //Turn the children into chromosomes
        Chromosome childOne = carryOutPmxCrossover(parent1, parent2, child1);
        Chromosome childTwo = carryOutPmxCrossover(parent2, parent1, child2);

        //Return an array list
        return createChildrenTogether(childOne, childTwo);
    }


    /**
     * Carries out cycle crossover
     * @param p1 - First parent
     * @param p2 - Second parent
     * @return - Array list of the two children created
     */
    ArrayList<Chromosome> CCSCrossover(Chromosome p1, Chromosome p2){
        //Create parents
        City[] parentOne = createParentArray(p1);
        City[] parentTwo = createParentArray(p2);

        //Create children
        City[] child1 = createChildArray(parentOne);
        City[] child2 = createChildArray(parentTwo);

        //Turn the children into chromosomes
        Chromosome childOne = carryOutCCSCrossover(parentOne, parentTwo, child1);
        Chromosome childTwo = carryOutCCSCrossover(parentTwo, parentOne, child2);

        //Return an array list
        return createChildrenTogether(childOne, childTwo);
    }


    /**
     * Carries out APC to produce two children
     * @param parent1 - First selected parent
     * @param parent2 - Second selected parent
     * @return - Array list of the two children
     */
    ArrayList<Chromosome> alternativePosCrossover(Chromosome parent1, Chromosome parent2){
        //Create parents as arrays
        City[] p1 = createParentArray(parent1);
        City[] p2 = createParentArray(parent2);

        //Create children
        City[] child1 = createChildArray(p1);
        City[] child2 = createChildArray(p2);

        //Turn the children into chromosomes
        Chromosome childOne = carryOutAPC(child1, p1, p2);
        Chromosome childTwo = carryOutAPC(child2, p2, p1);

        return createChildrenTogether(childOne, childTwo);
    }


    /**
     * Carries out first order crossover
     * @param parent1 - First parent for breeding
     * @param parent2 - Second parent for breeding
     * @return - Array list of the children created from crossover
     */
    ArrayList<Chromosome> firstOrderCrossover(Chromosome parent1, Chromosome parent2){
        //Create parents as arrays
        City[] p1 = createParentArray(parent1);
        City[] p2 = createParentArray(parent2);

        //Create children
        City[] child1 = createChildArray(p1);
        City[] child2 = createChildArray(p2);

        //Turn the children into chromosomes
        Chromosome childOne = carryOutOX1Crossover(child1, p1, p2);
        Chromosome childTwo = carryOutOX1Crossover(child2, p2, p1);


        //return as array list
        return createChildrenTogether(childOne, childTwo);
    }


    /**
     * Carries out the PMX crossover method
     * @param parent1 - first parent
     * @param parent2 - Second parent
     * @param child1 - The child that will be produced
     * @return - The child as a chromosome
     */
    private Chromosome carryOutPmxCrossover(City[] parent1, City[] parent2, City[] child1){
        Random random = new Random();

        //Create a random point in the parent arrays to transfer from parent to child
        int crossOverPoint = random.nextInt(parent1.length);

        //Create a placeholder which originally is the same as parent 1
        City[] placeHolderList1 = parent1.clone();

        //Loop through the arrays up to the crossover point
        for(int i=0; i<crossOverPoint; i++){

            //insert the gene in parent 2 into child 1
            child1[i] = parent2[i];

            //Temp variable to store the city in parent 2
            City toSwap = parent2[i];

            //Loop through parent 1 to find the temporary city
            for(int j=0; j<parent1.length; j++){
                //If found, swap over the position of the temp city in parent 1 with the position it is in in parent 2
                if(placeHolderList1[j].equals(toSwap)){
                    swap(placeHolderList1, i, j);
                    break;
                }
            }
        }

        //Add the rest of the cities in the placeholder list to the child
        if (parent1.length - crossOverPoint >= 0)
            System.arraycopy(placeHolderList1, crossOverPoint, child1, crossOverPoint, parent1.length - crossOverPoint);

        //Turn the children into chromosomes
        return new Chromosome(child1, false);
    }


    /**
     * Swaps over the elements
     * @param i - First element to swap
     * @param j - Second element to swap
     */
    private void swap(City[] list, int i, int j){
        City temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }


    /**
     * Carries out cycle crossover to create two children
     * @param parentOne - The first parent used to create the children
     * @param parentTwo - The second parent used to create children
     * @param child - Child to be formed
     * @return - An array of the two children created
     */
    private Chromosome carryOutCCSCrossover(City[] parentOne, City[] parentTwo, City[] child){


        boolean isCycle = true;
        int indexToCheck = 0;
        //While we are still in the cycle
        while(isCycle){
            //Get the city from parent 1
            City chromeToAdd = parentOne[indexToCheck];
            //Check to see if city is already in the child array
            if(cityInRoute(chromeToAdd, child)){
                //If so, the cycle is over
                isCycle = false;
            } else {
                //If not, add the city to the child in the same position as it is seen in the parent
                child[indexToCheck] = parentOne[indexToCheck];
                //Look for the city you have just added in parent 2, return the index in which that city is found in parent 2
                indexToCheck = searchForCity(parentOne, parentTwo[indexToCheck]);
            }
        }

        //Once cycle is broken
        for(int i=0; i<child.length; i++){
            //Add the empty elements with the elements in parent 2
            if(child[i] == null){
                child[i] = parentTwo[i];
            }
        }

        //Add to an array list to return
        return new Chromosome(child, false);
    }


    /**
     * Carries out the alternating point crossover process
     * @param child1 - The child being created
     * @param p1 - The first parent
     * @param p2 - The second parent
     * @return - The child in chromosome format
     */
    private Chromosome carryOutAPC(City[] child1, City[] p1, City[] p2){
        //Variables to keep track of positions in each array
        int childPositionMarker = 0;
        int parentPositionMarker = 0;

        //While the child chromosome is not full
        while(child1[p1.length - 1] == null){
            //Get city from index of parent pointer
            City selectedCity = p1[parentPositionMarker];
            //Check to see if city is already in child
            if(!(cityInRoute(selectedCity, child1))){
                //If not add it to child in the same position and move along one
                child1[childPositionMarker] = selectedCity;
                childPositionMarker ++;
            }
            //Get city from parent pointer in parent 2
            selectedCity = p2[parentPositionMarker];
            //Repeat process of searching and adding city to child
            if(!(cityInRoute(selectedCity, child1))){
                child1[childPositionMarker] = selectedCity;
                childPositionMarker ++;
            }
            parentPositionMarker ++;
        }

        //Turn the children into chromosomes
        return new Chromosome(child1, false);
    }


    /**
     * Carries out first order crossover
     * @param child1 - The child to be created
     * @param p1 - THe first parent
     * @param p2 - The second parent
     * @return - The child in the chromosome format
     */
    private Chromosome carryOutOX1Crossover(City[] child1, City[] p1, City[] p2){
        Random random = new Random();
        //Create first and second selection points
        //Cities in-between these values will be directly passed down from the parent in the same order
        int firstSelectionPoint = random.nextInt(p1.length);
        int secondSelectionPoint = -1;
        //Makes sure second point is greater than first for error checking
        while(secondSelectionPoint == -1){
            int point = random.nextInt(p1.length);
            if(point >= firstSelectionPoint){
                secondSelectionPoint = point;
            }
        }

        //Add the cities from parent directly into child in-between the selection points
        if (secondSelectionPoint + 1 - firstSelectionPoint >= 0)
            System.arraycopy(p1, firstSelectionPoint, child1, firstSelectionPoint, secondSelectionPoint + 1 - firstSelectionPoint);

        //Will fill up the child starting from the the right side after the second selection point
        if((secondSelectionPoint+1) < p1.length){
            for(int i=(secondSelectionPoint+1); i<p1.length; i++){
                int index = i;
                //While there is no city stored
                while(child1[i] == null){
                    City city = p2[index];
                    //Check to see if the city is in the child
                    if(!cityInRoute(city, child1)){
                        child1[i] = city;
                    }
                    //Will move the index along to get the next value from the parent, but keep the child index the same
                    index ++;
                    //If the index reaches the end, go to the start of the parent
                    if(index == p1.length){
                        index = 0;
                    }
                }
            }
        }

        int checkIndex = 0;
        //Go through the parent up until the second selection point adding the cities that do not appear yet
        for(int i=0; i<=secondSelectionPoint; i++){
            City cityToCheck = p2[i];
            //Add city if not in the child yet
            if(!cityInRoute(cityToCheck, child1)){
                child1[checkIndex] = cityToCheck;
                //Keep separate pointer for child array
                checkIndex ++;
            }
        }

        //Return child
        return new Chromosome(child1, false);
    }


    /**
     * Function to see if a city is already in a child
     * @param chromeToAdd - City to search for
     * @param child1 - The current route of child 1
     * @return - Whether the city is in the route already
     */
    private boolean cityInRoute(City chromeToAdd, City[] child1){
        boolean isCityInRoute = false;
        //Loop through child array
        for (City city : child1) {
            if (!(city == null)) {
                //If city found return true
                if (city.equals(chromeToAdd)) {
                    isCityInRoute = true;
                    break;
                }
            }
        }
        return isCityInRoute;
    }


    /**
     * Searches for the position of a city in a parent
     * @param parent - The parent to search in
     * @param cityToFind - The city to search for
     * @return - The index the city is in
     */
    private int searchForCity(City[] parent, City cityToFind){
        int indexOfCityInFirstParent = -1;
        for(int i=0; i<parent.length; i++){
            if(parent[i].equals(cityToFind)){
                indexOfCityInFirstParent = i;
                break;
            }
        }
        //Error check
        if(indexOfCityInFirstParent == -1){
            System.out.println(cityToFind.getxCoordinate() + " " + cityToFind.getyCoordinate());
            System.out.println(Arrays.toString(parent));
            System.out.println(parent.length);
        }
        return indexOfCityInFirstParent;
    }


    /**
     * Carries out edge recombination crossover
     * @param parent1 - The first parent to use for crossover
     * @param parent2 - The second parent to use for crossover
     * @return - The child generated from the crossover
     */
    private Chromosome carryOutEdge(Chromosome parent1, Chromosome parent2){
        //Create parents as arrays
        City[] p1 = createParentArray(parent1);
        City[] p2 = createParentArray(parent2);

        //Create children
        City[] child = createChildArray(p1);

        HashMap<City, City[]> edges = new HashMap<>();

        //Put the cities in the map keys
        for(int i=0; i<p1.length; i++){
            City city = p1[i];
            City[] neighbours = getIndexes(i, p1, p2);
            edges.put(city, neighbours);
        }

        //Keep track of child array index
        int childIndex = 0;

        //Create random values and pick between 1 and 2
        Random random = new Random();
        int pickParent = random.nextInt(2)+1;
        City cityToRemove;
        //If 1, get the first element from parent 1
        if(pickParent == 1){
            cityToRemove = p1[childIndex];
        } else {
            //Else get it from parent 2
            cityToRemove = p2[childIndex];
        }

        //Append to child and move along
        child[childIndex] = cityToRemove;
        childIndex++;

        while (childIndex < p1.length){

            //Loop through map
            for(Map.Entry<City, City[]> set : edges.entrySet()){
                //Create list
                ArrayList<City> neighbourArray = new ArrayList<>();
                //Get values of the map relating to the key in the loop (Similar to i)
                City[] cityArray = set.getValue();

                //Add values to array list
                for(int i=0; i<cityArray.length; i++){
                    neighbourArray.add(i, cityArray[i]);
                }

                //Loop through array and search for city to remove
                for(City city : neighbourArray){
                    //If found remove from array list
                    if(city.equals(cityToRemove)){
                        neighbourArray.remove(cityToRemove);
                        break;
                    }
                }

                //Create new array and add the remaining values back from the array list
                City[] updatedCityList = new City[neighbourArray.size()];
                for (int i=0; i<neighbourArray.size(); i++){
                    updatedCityList[i] = neighbourArray.get(i);
                }

                //Put the new array with the current key, update the map essentially
                edges.put(set.getKey(), updatedCityList);
            }

            //Get the array of the value that has been added to the child
            City[] removedCityArray = edges.get(cityToRemove);

            //Set the best neighbour parameter
            City bestNeighbour = null;
            //If array is empty, pick a random value to be next gene
            if(removedCityArray.length == 0){
                //Pick random city
                bestNeighbour = p1[random.nextInt(p1.length)];
                while(cityInRoute(bestNeighbour, child)){
                    bestNeighbour = p1[random.nextInt(p1.length)];
                }
            } else {
                //Loop through array
                for(City neighbourToCheck : removedCityArray) {
                    //Get array associated with current variable in map using it as key
                    City[] neighToCheckArray = edges.get(neighbourToCheck);

                    //If first value, make it the neighbour
                    if(bestNeighbour == null){
                        bestNeighbour = neighbourToCheck;
                    } else {
                        //If the array is smaller, it becomes the best neighbour
                        if(neighToCheckArray.length < edges.get(bestNeighbour).length){
                            bestNeighbour = neighbourToCheck;
                        } else if (neighToCheckArray.length == edges.get(bestNeighbour).length){
                            //If it is the same, pick a random winner
                            int value = random.nextInt(2)+1;
                            if(value == 1){
                                bestNeighbour = neighbourToCheck;
                            }
                        }
                    }
                }
            }
            //Add to child chromosome
            child[childIndex] = bestNeighbour;
            //Make it the next value for the loop
            cityToRemove = bestNeighbour;
            //Increase child index
            childIndex++;
        }

        //Turn the children into chromosomes
        return new Chromosome(child, false);
    }


    /**
     * Gets the neighbours of each city from the two parents
     * @param index - Index of the city in the first parent
     * @param p1 - Parent 1
     * @param p2 - Parent 2
     * @return - Array of cities that neighbour the chosen city in both of the parents' routes
     */
    private City[] getIndexes(int index, City[] p1, City[] p2){
        //Create array
        ArrayList<City> neighbours = new ArrayList<>();
        //Add the left and right neighbours of parent 1
        neighbours.add(getLeftNeighbour(index, p1));
        neighbours.add(getRightNeighbours(index,p1));

        City city = p1[index];
        //Look for the city in the other parent and return the index
        int otherParentIndex = searchForCity(p2, city);

        //Get the left and right neighbours for the other parent
        City leftNeigh = getLeftNeighbour(otherParentIndex, p2);
        City rightNeigh = getRightNeighbours(otherParentIndex, p2);

        //Check if it has been added
        if(!neighbours.contains(leftNeigh)){
            neighbours.add(leftNeigh);
        }

        //Check if right neighbour has been added
        if(!neighbours.contains(rightNeigh)){
            neighbours.add(rightNeigh);
        }

        //Create array, append and return
        City[] neighboursArray = new City[neighbours.size()];
        for(int i=0; i< neighboursArray.length; i++){
            neighboursArray[i] = neighbours.get(i);
        }

        return neighboursArray;
    }


    /**
     * Gets the city to the left of the index values passed
     * @param index - Index of the current city
     * @param parent - The route of the parent
     * @return - The city to the left of the element passed
     */
    private City getLeftNeighbour(int index, City[] parent){
        City leftNeighbour;
        //If element is first in the array, loop to the end
        if(index == 0){
            int endOfArray = (parent.length - 1);
            leftNeighbour = parent[endOfArray];
        } else {
            //Else get the city to the left element of current index
            leftNeighbour = parent[index - 1];
        }
        return leftNeighbour;
    }


    /**
     * Gets the city to the right of the index value passed
     * @param index - Index of current city
     * @param parent - The route of the parent
     * @return - The element to the right of the index value
     */
    private City getRightNeighbours(int index, City[] parent){
        City rightNeighbour;
        //If element is at the end of the array, get first element
        if(index == (parent.length-1)){
            rightNeighbour = parent[0];
        } else {
            //If not, return city in the next index to the left
            rightNeighbour = parent[index+1];
        }
        return rightNeighbour;
    }
}
