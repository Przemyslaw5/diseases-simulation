package com.agh.miss.gui.menu;

import com.agh.miss.Simulation;
import com.agh.miss.gui.Visualization;
import com.agh.miss.gui.menu.leftColumn.Legend;
import com.agh.miss.gui.menu.leftColumn.Parameters;
import com.agh.miss.gui.menu.middleColumn.LineChartPeople;
import com.agh.miss.gui.menu.middleColumn.LineChartR0;
import com.agh.miss.gui.menu.middleColumn.PieChartPeople;
import com.agh.miss.gui.menu.leftColumn.Statistics;
import com.agh.miss.gui.menu.rightColumn.SelectedPerson;
import com.agh.miss.mapElements.person.Person;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends HBox {

    private Simulation simulation;

    private final Statistics stats;
    private final Parameters parameters;
    private final PieChartPeople pieChartPeople;
    private final LineChartPeople lineChartPeople;
    private final LineChartR0 lineChartR0;

    private final SelectedPerson selectedPersonParameters;

    private Person selectedPerson = null;

    public Menu(Simulation simulation, Visualization visualization){
        this.simulation = simulation;

        this.stats = new Statistics(visualization);
        this.parameters = new Parameters(visualization);
        Legend legend = new Legend();
        this.pieChartPeople = new PieChartPeople(simulation);
        this.lineChartPeople = new LineChartPeople(simulation);
        this.lineChartR0 = new LineChartR0(simulation);
        this.selectedPersonParameters = new SelectedPerson();

        PieChart pieChart = pieChartPeople.getPieChart();
        LineChart<Number, Number> lineChart = lineChartPeople.getLineChart();
        LineChart<Number, Number> lineChart2 = lineChartR0.getLineChart();

        VBox leftColumn = new VBox();
        leftColumn.getChildren().addAll(stats, parameters, legend);

        VBox middleColumn = new VBox();
        middleColumn.getChildren().addAll(pieChart, lineChart, lineChart2, selectedPersonParameters);

        VBox rightColumn = new VBox();
        rightColumn.getChildren().addAll(selectedPersonParameters);

        getChildren().addAll(leftColumn, middleColumn, rightColumn);
    }

    public void reset(Simulation simulation) {
        this.simulation = simulation;
        parameters.resetPausePlayButton();
        selectedPerson = null;
        selectedPersonParameters.reset();
        pieChartPeople.reset(simulation);
        lineChartPeople.reset(simulation);
        lineChartR0.reset(simulation);
    }

    public void update(){
        stats.update();
        lineChartR0.update();

        // Charts update every 10 days
        if(simulation.dayOfSimulation % Simulation.NUMBER_OF_DAYS_AFTER_CHARTS_UPDATE == 0){
            pieChartPeople.update();
            lineChartPeople.update();
        }

        if(selectedPerson != null) selectedPersonParameters.update(selectedPerson, simulation);
    }

    public boolean isApplicationRun(){
        return parameters.isApplicationRun();
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

}
