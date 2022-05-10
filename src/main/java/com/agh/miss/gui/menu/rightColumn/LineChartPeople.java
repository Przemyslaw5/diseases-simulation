package com.agh.miss.gui.menu.rightColumn;

import com.agh.miss.Simulation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChartPeople {

    private final XYChart.Series<Number, Number> healthyPeopleSeries;
    private final XYChart.Series<Number, Number> infectedPeopleSeries;
    private final XYChart.Series<Number, Number> curedPeopleSeries;
    private final XYChart.Series<Number, Number> deadPeopleSeries;

    private final LineChart<Number, Number> lineChart;

    private final Simulation simulation;

    public LineChartPeople(Simulation simulation){
        this.simulation = simulation;

        LineChart<Number, Number> lineChart = this.setAxisTitles("Days", "Number of people by state");

        healthyPeopleSeries = new XYChart.Series<>();
        infectedPeopleSeries = new XYChart.Series<>();
        curedPeopleSeries = new XYChart.Series<>();
        deadPeopleSeries = new XYChart.Series<>();

        healthyPeopleSeries.setName("Healthy");
        infectedPeopleSeries.setName("Infected");
        curedPeopleSeries.setName("Cured");
        deadPeopleSeries.setName("Dead");

        lineChart.getData().addAll(healthyPeopleSeries, infectedPeopleSeries, curedPeopleSeries, deadPeopleSeries);

        lineChart.setMaxHeight(300);
        lineChart.setMaxWidth(300);
        this.lineChart = lineChart;
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
        this.healthyPeopleSeries.getData().add(new XYChart.Data<>(dayOfSimulation, simulation.world.numberHealthyPeopleOnMap()));
        this.infectedPeopleSeries.getData().add(new XYChart.Data<>(dayOfSimulation, simulation.world.numberInfectedPeopleOnMap()));
        this.curedPeopleSeries.getData().add(new XYChart.Data<>(dayOfSimulation, simulation.world.numberCuredPeopleOnMap()));
        this.deadPeopleSeries.getData().add(new XYChart.Data<>(dayOfSimulation, simulation.world.numberDeadPeople()));
    }
}
