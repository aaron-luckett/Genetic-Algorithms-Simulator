package Graphing;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class for plotting the line graphs after a simulation is run
 * @author Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added correct code to create graph and allow for class to be called by the GA Class
 * @version 1.11 - Fixed x axis bug
 * @version 1.2 - Added the average distances from each generation to the graph
 * @version 1.21 - Fixed bug with axis increments
 * @version 1.3 - Final bug checks
 * @see Algorithm.GeneticAlgorithm
 */
public class GraphPlot extends JFrame {

    private static final long serialVersionUID = 1L;
    private ArrayList<Integer> bestDistOfEachGen;                 //Array list of the best distance found at each generation
    private ArrayList<Integer> averageDistOfEachGen;              //Array list for the average distance found at each generation
    private int maxNumOfGens;                                     //The maximum number of generations the GA will run for

    /**
     * Constructor fot graph plotting
     * Will set the variables of the class and set the chart up
     * @param title - Title of the window
     * @param distances - The array of best distances of each generation
     * @param generations - The number of generations the simulation was ran for
     */
    public GraphPlot(String title, ArrayList<Integer> distances, int generations, ArrayList<Integer> averageDistances){
        super(title);

        bestDistOfEachGen = distances;
        maxNumOfGens = generations;

        XYSeriesCollection dataset;
        averageDistOfEachGen = averageDistances;
        //Create dataset
        dataset = createDataSet2();


        //Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Best and average distance of each gen",
                "Generation",
                "Distance",
                dataset
        );

        //Sets the x axis increments to 50
        XYPlot plot = chart.getXYPlot();
        NumberAxis axis = (NumberAxis) plot.getDomainAxis();
        axis.setTickUnit(new NumberTickUnit(20));

        //Sets the y increment to 5000
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        int axisRatio = (distances.get(0)/30);
        yAxis.setTickUnit(new NumberTickUnit(axisRatio));

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(1000, 1350));
        setContentPane(panel);
    }


    /**
     * Creates the dataset and adds it to the chart in order to display the line graph correctly
     * @return - The dataset containing the x and y values in the correct format
     */
    private XYSeriesCollection createDataSet2(){
        XYSeriesCollection dataSet = new XYSeriesCollection();
        XYSeries bestDistances = new XYSeries("Best Distance Found");
        XYSeries averageDistances = new XYSeries("Average Distance of the population");

        //Sets up the x axis values to put as labels and get points
        int[] gens = new int[maxNumOfGens];
        for(int i=0; i<maxNumOfGens - 1; i++){
            gens[i] = i+1;
        }

        //add data to dataset
        for(int i=0; i<(bestDistOfEachGen.size() -1); i++){
            bestDistances.add(gens[i], bestDistOfEachGen.get(i));
        }

        //Adds the average distance data
        for(int i=0; i<(averageDistOfEachGen.size() -1); i++){
            averageDistances.add(gens[i], averageDistOfEachGen.get(i));
        }

        //Adds the two lines to the graph and chart
        dataSet.addSeries(bestDistances);
        dataSet.addSeries(averageDistances);
        return dataSet;
    }
}

