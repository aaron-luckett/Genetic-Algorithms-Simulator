package Graphing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.HashMap;
import java.util.Map;


/**
 * Class that creates a bar chart relating to the average best distances of all
 * of the combinations of the GAs
 * @author - Aaron Luckett (aal16)
 * @version 1.0 - Initial Creation
 * @version 1.1 - Added it to suit the GA displaying as oppose to random values
 * @version 1.2 - Final bug checks
 */
public class BarChart extends ApplicationFrame {


    /**
     * Constructor for the class, creates the bar chart and sets the necessary parameters
     * @param applicationTitle - Title of the window
     * @param chartTitle - Title of the chart
     * @param tournament - The values relating to tournament selection
     * @param roulette - The values relating to roulette wheel selection
     * @param ranked - The values relating to ranked selection
     */
    public BarChart(String applicationTitle , String chartTitle, HashMap<String, Integer> tournament, HashMap<String, Integer> roulette, HashMap<String, Integer> ranked) {
        super( applicationTitle );
        //Create the parts of the graph bar the dataset
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Selection and Crossover Combo",
                "Average Best Distance",
                createDataset(tournament, roulette, ranked),
                PlotOrientation.VERTICAL,
                true, true, false);

        //Create panel to put the chart in
        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new java.awt.Dimension( 900 , 700 ) );
        setContentPane( chartPanel );
    }


    /**
     * Creates and sets up the data that the chart will display
     * @param tournament - The values relating to tournament selection
     * @param roulette - The values relating to roulette wheel selection
     * @param ranked - The values relating to ranked based selection
     * @return - The dataset to put into the bar chart
     */
    private CategoryDataset createDataset(HashMap<String, Integer> tournament, HashMap<String, Integer> roulette, HashMap<String, Integer> ranked) {
        //Set up the labels relating to the selection methods
        final String tournamentLabel = "Tournament";
        final String rouletteLabel = "Roulette";
        final String rankedLabel = "Ranked";
        //Create the dataset
        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );

        //Adds the data for all the combinations to the dataset and returns
        addToDataSet(tournament, tournamentLabel, dataset);
        addToDataSet(roulette, rouletteLabel, dataset);
        addToDataSet(ranked, rankedLabel, dataset);

        return dataset;
    }


    /**
     * Function to loop through a hash map and add the values stored relating to the current key
     * @param hashMap - The hash map to view into
     * @param label - The label relating to the key of the hash map
     * @param dataset - The dataset the values will be added to
     */
    private void addToDataSet(HashMap<String, Integer> hashMap, String label, DefaultCategoryDataset dataset){
        //Loop through the hash map adding the corresponding values to the dataset
        for(Map.Entry<String, Integer> set : hashMap.entrySet()){
            dataset.addValue(set.getValue(), label, set.getKey());
        }
    }
}
