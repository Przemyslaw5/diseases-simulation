package com.agh.miss.gui.menu.leftColumn;

import com.agh.miss.gui.Visualization;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Statistics extends VBox {

    private final Label dayLabel;
    private final Label peopleLabel;
    private final Label healthyPeopleLabel;
    private final Label infectedPeopleLabel;
    private final Label curedPeopleLabel;

    private final Visualization visualization;

    public Statistics(Visualization visualization) {

        this.visualization = visualization;

        Label stats = new Label("Statistics of simluation");
        stats.setFont(Font.font(20));
        HBox labelStats = new HBox(stats);
        labelStats.setAlignment(Pos.BASELINE_CENTER);

        this.dayLabel = new Label("Day: " + visualization.simulation.dayOfSimulation);
        this.peopleLabel = new Label("People: " + visualization.simulation.world.numberPeopleOnMap());
        this.healthyPeopleLabel = new Label("Healthy people: " + visualization.simulation.world.numberHealthyPeopleOnMap());
        this.infectedPeopleLabel = new Label("Infected people: " + visualization.simulation.world.numberInfectedPeopleOnMap());
        this.curedPeopleLabel = new Label("Cured people: " + visualization.simulation.world.numberCuredPeopleOnMap());

        getChildren().addAll(labelStats, dayLabel, peopleLabel, healthyPeopleLabel, infectedPeopleLabel, curedPeopleLabel);

        // up right down left
        setPadding(new Insets(10, 0, 30, 10));
    }

    //Each day statistics must be update
    public void onUpdate(){
        this.dayLabel.setText("Day: " + visualization.simulation.dayOfSimulation);
        this.peopleLabel.setText("People: " + visualization.simulation.world.numberPeopleOnMap());
        this.healthyPeopleLabel.setText("Healthy people: " + visualization.simulation.world.numberHealthyPeopleOnMap());
        this.infectedPeopleLabel.setText("Infected people: " + visualization.simulation.world.numberInfectedPeopleOnMap());
        this.curedPeopleLabel.setText("Cured people: " + visualization.simulation.world.numberCuredPeopleOnMap());
    }
}
