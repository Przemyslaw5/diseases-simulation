package com.agh.miss.gui.menu.middleColumn;

import com.agh.miss.Simulation;
import com.agh.miss.mapElements.person.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class PieChartPeople extends VBox {

    private final ObservableList<PieChart.Data> pieChartData;
    private final PieChart pieChart;
    private Simulation simulation;
    private int startPeopleNumber;

    public PieChartPeople(Simulation simulation) {
        this.simulation = simulation;
        this.startPeopleNumber = simulation.world.getStartPeopleNumber();

        pieChartData = FXCollections.observableArrayList();

        Arrays.stream(Person.HealthState.values()).forEach(healthState ->
                pieChartData.add(new PieChart.Data(healthState.toString(), 10)));

        pieChart = new PieChart(pieChartData);
        pieChart.setMaxHeight(200);
        pieChart.setMaxWidth(300);
        pieChart.setTitle("Percentage of people by state");
    }

    public void reset(Simulation simulation) {
        this.simulation = simulation;
        this.startPeopleNumber = simulation.world.getStartPeopleNumber();

        pieChartData.clear();

        Arrays.stream(Person.HealthState.values()).forEach(healthState ->
                pieChartData.add(new PieChart.Data(healthState.toString(), 10)));
    }

    private int getNumberAppropriatePeople(String healthStateName) {
        return switch (healthStateName) {
            case "Healthy"  -> simulation.world.numberHealthyPeopleOnMap();
            case "Infected" -> simulation.world.numberInfectedPeopleOnMap();
            case "Cured"    -> simulation.world.numberCuredPeopleOnMap();
            case "Dead"     -> simulation.world.numberDeadPeople();
            default         -> throw new IllegalStateException("Unexpected healthState: " + healthStateName);
        };
    }

    public void update(){
        for(PieChart.Data data : pieChartData){
            String healthStateName = data.getName().split(" ")[0];
            data.setName(healthStateName + " (" + Math.round((float) getNumberAppropriatePeople(healthStateName) * 100 / startPeopleNumber) + "%) ");
            data.setPieValue(getNumberAppropriatePeople(healthStateName));
        }
    }

    public PieChart getPieChart() {
        return pieChart;
    }
}
