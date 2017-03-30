package com.ondrej.sgd;

import com.sun.javafx.geom.Vec3f;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ondrej on 23.6.2016.
 *
 * Class for visualisation of perceptron 2D result
 */
public class Visualize2D{

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final int width = 600;
    private static final int height = 500;

    /*
     *Opens a new window with result chart
      *  - input point (colored by classes) and perceptron separation line
      *
     *@param dataset object of Dataset class contains input point with their classification
     *@param line result weight vector of perceptron alg.
     */
    public void view(Dataset dataset, Vec3f line){
        JFrame frame = new JFrame(new String(dateFormat.format(new Date())));
        XYDataset xyDataset = createContent(dataset, line);
        final ChartPanel chartPanel = createPanel(xyDataset);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    /*
     *Creates final plot from input point in XYDataset
     *
     *@param xyDataset XYSeriesCollection to be display within final plot
     *@return JPanel with final plot
    */
    private ChartPanel createPanel(XYDataset xyDataset) {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                "Perceptron separation", "X", "Y", xyDataset,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(0, Color.green);
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setVerticalTickLabels(true);
        return new ChartPanel(jfreechart);
    }

    /*
     * Creates required XYSeriesCollection from input points and
     * points of line for separation
     *
     * @param dataset object of Dataset class contains input point with their classification
     * @param lineVec esult weight vector of perceptron alg.
     * @return XYDataset, contains all input points and points of sep. line
     */
    private XYDataset createContent(Dataset dataset, Vec3f lineVec) {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries class1 = new XYSeries("Class 1");
        XYSeries class2 = new XYSeries("Class 2");
        XYSeries line = new XYSeries("");
        float maxX = Float.MIN_VALUE;
        float minX = Float.MAX_VALUE;
        for (Dataset.SGDPoint point : dataset) {
            maxX = Math.max(maxX, point.pos.x);
            minX = Math.min(minX, point.pos.x);
            if(point.classVal == 1){
                class1.add(point.pos.x, point.pos.y);
            } else {

                class2.add(point.pos.x, point.pos.y);
            }
        }
        for(float actX = minX; actX < maxX; actX += 0.001){
            float actY = (-lineVec.x * actX - lineVec.z) / lineVec.y;
            line.add(actX, actY);
        }
        xySeriesCollection.addSeries(class1);
        xySeriesCollection.addSeries(class2);
        xySeriesCollection.addSeries(line);
        return xySeriesCollection;
    }
}
