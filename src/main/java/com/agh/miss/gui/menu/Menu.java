package com.agh.miss.gui.menu;

import com.agh.miss.Simulation;
import com.agh.miss.gui.Visualization;
import com.agh.miss.gui.menu.leftColumn.Legend;
import com.agh.miss.gui.menu.leftColumn.Parameters;
import com.agh.miss.gui.menu.rightColumn.PieChartPeople;
import com.agh.miss.gui.menu.leftColumn.Statistics;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends HBox {

    private final Simulation simulation;

    private final Statistics stats;
    private final Parameters parameters;
    private final Legend legend;
    private final PieChartPeople pieChartPeople;

    public Menu(Simulation simulation, Visualization visualization){
        this.simulation = simulation;

        this.stats = new Statistics(visualization);
        this.parameters = new Parameters(visualization);
        this.legend = new Legend();
        this.pieChartPeople = new PieChartPeople(simulation);

        PieChart pieChart = pieChartPeople.getPieChart();

        VBox leftColumn = new VBox();
        leftColumn.getChildren().addAll(stats, parameters, legend);

        VBox rightColumn = new VBox();
        rightColumn.getChildren().addAll(pieChart);

        getChildren().addAll(leftColumn, rightColumn);
    }

    public void update(){
        stats.update();
        pieChartPeople.update();
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
