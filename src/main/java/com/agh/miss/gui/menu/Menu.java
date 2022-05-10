package com.agh.miss.gui.menu;

import com.agh.miss.Simulation;
import com.agh.miss.gui.Visualization;
import com.agh.miss.gui.menu.leftColumn.Legend;
import com.agh.miss.gui.menu.leftColumn.Parameters;
import com.agh.miss.gui.menu.rightColumn.LineChartPeople;
import com.agh.miss.gui.menu.rightColumn.PieChartPeople;
import com.agh.miss.gui.menu.leftColumn.Statistics;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends HBox {

    private final Simulation simulation;

    private final Statistics stats;
    private final Parameters parameters;
    private final PieChartPeople pieChartPeople;
    private final LineChartPeople lineChartPeople;

    public Menu(Simulation simulation, Visualization visualization){
        this.simulation = simulation;

        this.stats = new Statistics(visualization);
        this.parameters = new Parameters(visualization);
        Legend legend = new Legend();
        this.pieChartPeople = new PieChartPeople(simulation);
        this.lineChartPeople = new LineChartPeople(simulation);

        PieChart pieChart = pieChartPeople.getPieChart();
        LineChart<Number, Number> lineChart = lineChartPeople.getLineChart();

        VBox leftColumn = new VBox();
        leftColumn.getChildren().addAll(stats, parameters, legend);

        VBox rightColumn = new VBox();
        rightColumn.getChildren().addAll(pieChart, lineChart);

        getChildren().addAll(leftColumn, rightColumn);
    }

    public void update(){
        stats.update();

        // Charts update every 10 days
        if(simulation.dayOfSimulation % Simulation.NUMBER_OF_DAYS_AFTER_CHARTS_UPDATE == 0){
            pieChartPeople.update();
            lineChartPeople.update();
        }
    }

    public boolean isApplicationRun(){
        return parameters.isApplicationRun();
    }

    public Parameters getParameters() {
        return parameters;
    }

    public PieChartPeople getPieChartPeople() {
        return pieChartPeople;
    }
}
