package Genetics;

import java.util.Random;

/**
 * Class for the city that the agent must visit on its travels.
 * Will only visit the city once during its journey
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added methods
 * @version 1.11 - General code tidy
 * @version 1.2 - Final bug fixes
 */
public class City {
    private int xCoordinate;     //X Coordinate of the city
    private int yCoordinate;     //Y coordinate of the city


    /**
     * Creates city with random coordinates
     */
    public City(){
        this.xCoordinate = (int)(Math.random()* 800);
        this.yCoordinate = (int)(Math.random()* 1200);
    }


    /**
     * Creates a city with set co-ordinates
     * @param x - Wanted x co-ordinate
     * @param y - Wanted y co-ordinate
     */
    public City(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }


    /**
     * Gets the x co-ordinate of the city
     * @return - The x co-ordinate
     */
    public int getxCoordinate() {
        return this.xCoordinate;
    }


    /**
     * Gets the y co-ordinate of the city
     * @return - The y co-ordinate
     */
    public int getyCoordinate() {
        return this.yCoordinate;
    }


    /**
     * Calculates the distance to the next city in the path
     * @param city - city to calculate distance
     * @return - The distance between the two cities
     */
    public double calcDistance(City city){
        int xDis = Math.abs((getxCoordinate()) - (city.getxCoordinate()));
        int yDis = Math.abs(((getyCoordinate())) - (city.getyCoordinate()));

        return Math.sqrt( (xDis * xDis) + (yDis * yDis));
    }


    /**
     * Prints out the city co-ordinates in a tidy format
     * @return - Genetics.City co-ordinates
     */
    @Override
    public String toString() {
        return(getxCoordinate() + ", " + getyCoordinate());
    }


    /**
     * Create a City with a random name and random location.
     * @return - a Randomly generated City
     */
    static City getRandomCity(boolean shuffle){
        Random rand1 = new Random(); //instance of random class
        Random rand2 = new Random(); //instance of random class
        int upperLimit = 5000;
        int xCo = rand1.nextInt(upperLimit);
        int yCo = rand2.nextInt(upperLimit);
        return new City(xCo,yCo);

    }
}
