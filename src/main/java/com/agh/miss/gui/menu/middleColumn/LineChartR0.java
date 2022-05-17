package com.agh.miss.gui.menu.middleColumn;

import com.agh.miss.Simulation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChartR0 {
    private final XYChart.Series<Number, Number> r0Series;

    private final LineChart<Number, Number> lineChart;

    private Simulation simulation;

    public LineChartR0(Simulation simulation){
        this.simulation = simulation;

        LineChart<Number, Number> lineChart = this.setAxisTitles("Days", "r0 value");

        r0Series = new XYChart.Series<>();

        r0Series.setName("r0");

        lineChart.getData().add(r0Series);

        lineChart.setMaxHeight(300);
        lineChart.setMaxWidth(300);
        this.lineChart = lineChart;
    }

    public void reset(Simulation simulation) {
        this.simulation = simulation;

        r0Series.getData().clear();
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }

    public LineChart<Number, Number> setAxisTitles(String xAxisName, String yAxisName){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);
        return  new LineChart<>(xAxis, yAxis);
    }

    public void update(){
        int dayOfSimulation = simulation.dayOfSimulation;
        this.r0Series.getData().add(new XYChart.Data<>(dayOfSimulation, simulation.r0));
   }
}
