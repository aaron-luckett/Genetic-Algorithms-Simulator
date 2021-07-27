package Graphing;

import Genetics.Chromosome;
import Genetics.City;

import javax.swing.*;
import java.awt.*;


/**
 * Class to plot the visual process of finding the best path
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added Scaling
 * @version 1.11 - Fixed scaling error bug
 * @version 1.2 - Final bug checks
 * @see Chromosome
 * @see City
 */
public class TSPGraph extends JFrame {
    private JPanel jp;                         //Panel to put graph in
    private int windowHeight = 800;            //Window height
    private int windowWidth = 1250;            //Window width
    private Chromosome routeToShow;            //Current best route found/ the current route being shown in the window
    private City[] listOfCities;               //The cities in the best route
    private double scaleX, scaleY;             //How much to scale the x and y values up and down to fit in the window properly
    private int negativeBalanceX = 0;           //City in the negative co-ordinates
    private int negativeBalanceY = 0;


    /**
     * Constructor for the class
     * @param cities - The cities in the route
     */
    public TSPGraph(City[] cities){
        //Sets the title and size of the window
        //Also sets so when closed the program will stop
        super("Best Route Found");
        super.setSize(windowWidth,windowHeight);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Gets the list of cities
        listOfCities = cities;

        //Sets up the graph to show
        jp = new GPanel();
        fitCities();
        super.add(jp);
        setVisible(true);
    }


    /**
     * Fits the cities so that they are scaled down to fit in the window
     */
    private void fitCities(){
        //Sets the max x and y value in the list of cities
        int maxX = 1;
        int maxY = 1;
        //Finds the highest x value in the list
        //As well as the y value
        for (City city : listOfCities) {
            if(city.getxCoordinate() < negativeBalanceX){
                negativeBalanceX = city.getxCoordinate();
            }
            if(city.getyCoordinate() < negativeBalanceY){
                negativeBalanceY = city.getyCoordinate();
            }
            if (city.getxCoordinate() > maxX) {
                maxX = city.getxCoordinate();
            }
            if (city.getyCoordinate() > maxY) {
                maxY = city.getyCoordinate();
            }
        }
        //Divides the max values by the window height in order to make the city fit in the window
        scaleX = (((double)maxX - negativeBalanceX) / (double)windowWidth) + 0.5;
        scaleY = (((double)maxY - negativeBalanceY) / (double)windowHeight) + 0.5;

    }


    //Draws the best route when a new one is found
    public void drawRoute(Chromosome chrome){
        this.routeToShow = chrome;
        jp.repaint();
    }


    /**
     * Class to help draw the graph
     * @author Aaron Luckett (aal16)
     * @version 1.0 - Initial creation
     * @version 1.1 - Made the drawing of the route possible
     */
    class GPanel extends JPanel {
        GPanel(){
            super.setPreferredSize(new Dimension(windowWidth,windowHeight));
        }


        /**
         * Paints the cities and edges/route
         * @param g - the graphics which draws the components onto the window
         */
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            paintCities(g);
            paintEdges(g);
        }


        /**
         * Paints the cities onto the screen
         * @param g - The graphics window
         */
        private void paintCities(Graphics g){
            //Loop through the the list of cities
            for (City cityToPaint : listOfCities) {
                //Draw and fill in the circles which represent the cities
                //Uses the x and u co-ordinates of the cities and divides by the scale in order to fit them in the window
                int xPosition = (int)((cityToPaint.getxCoordinate() - negativeBalanceX) / scaleX);
                int yPosition = (int)((cityToPaint.getyCoordinate() - negativeBalanceY) / scaleY);
                int cityNodeSize = 6;
                g.drawOval(xPosition, yPosition, cityNodeSize, cityNodeSize);
                g.fillOval(xPosition, yPosition, cityNodeSize, cityNodeSize);
            }
        }


        /**
         * Connects the cities together via the best route found so far
         * @param g - The graphics window and tool
         */
        private void paintEdges(Graphics g) {
            //Creates the route in the form of an array
            City[] toPaint = routeToShow.getArray();

            int xStart;
            int xEnd;
            int yStart;
            int yEnd;

            //Loops through the route
           for(int i=0; i<toPaint.length; i++){
               //Checks it is not at the end of the array
               if(i+1 != toPaint.length){
                   //Gets the cities to connect
                   City fromCity = toPaint[i];
                   City toCity = toPaint[i+1];

                   //Draws the edge connecting the cities also adding the scale values to fit in the window
                   xStart = (int)((fromCity.getxCoordinate() - negativeBalanceX) / scaleX);
                   yStart = (int)((fromCity.getyCoordinate() - negativeBalanceY) / scaleY);
                   xEnd = (int)((toCity.getxCoordinate() - negativeBalanceX) / scaleX);
                   yEnd = (int)((toCity.getyCoordinate() - negativeBalanceY) / scaleY);
                   g.drawLine(xStart ,yStart, xEnd ,yEnd);
               }
           }
           //Connects the final element in the route back to the start to complete the route
           City finalCity = toPaint[toPaint.length - 1];
           xStart = (int)((finalCity.getxCoordinate() - negativeBalanceX) / scaleX) ;
           yStart = (int)((finalCity.getyCoordinate() - negativeBalanceY) / scaleY);
           xEnd = (int)((toPaint[0].getxCoordinate() - negativeBalanceX) / scaleX);
           yEnd = (int)((toPaint[0].getyCoordinate() - negativeBalanceY) / scaleY);
           g.drawLine(xStart , yStart, xEnd, yEnd);
        }
    }

}
