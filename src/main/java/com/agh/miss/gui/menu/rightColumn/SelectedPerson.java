package com.agh.miss.gui.menu.rightColumn;

import com.agh.miss.Simulation;
import com.agh.miss.mapElements.person.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SelectedPerson extends VBox {

    private final Label healthState;
    private final Label detailedInformation;
    private final Label position;
    private final Label numberOfInfections;

    public SelectedPerson() {

        Label selectedPerson = new Label("Parameters selected person");
        selectedPerson.setFont(Font.font(20));
        HBox labelSelectedPerson = new HBox(selectedPerson);
        labelSelectedPerson.setAlignment(Pos.BASELINE_CENTER);

        this.healthState = new Label();
        this.detailedInformation = new Label();
        this.position = new Label();
        this.numberOfInfections = new Label();

        getChildren().addAll(labelSelectedPerson, healthState, detailedInformation, position, numberOfInfections);

        setPadding(new Insets(10, 0, 60, 10));
    }

    public void reset() {
        this.healthState.setText("");
        this.detailedInformation.setText("");
        this.position.setText("");
        this.numberOfInfections.setText("");
    }

    public void update(Person person, Simulation simulation){

        this.healthState.setText("Health state: " + person.getHealthState());

        if (person.getHealthState() == Person.HealthState.INFECTED) {
            this.detailedInformation.setText("Infection time: " + person.getInfectionTime());
            this.position.setText("Position: " + person.getPosition());
        }

        else if (person.getHealthState() == Person.HealthState.CURED) {
            this.detailedInformation.setText("Resistance time remain: " + (simulation.world.getResistanceTime() - person.getResistanceTime()));
            this.position.setText("Position: " + person.getPosition());
        }

        else if (person.getHealthState() == Person.HealthState.DEAD) {
            this.detailedInformation.setText("Day of death: " + person.getDeathDay());
            this.position.setText("");
        }

        else {
            this.detailedInformation.setText("");
            this.position.setText("Position: " + person.getPosition());
        }

        this.numberOfInfections.setText("Number of infections: " + person.getNumberOfInfections());
    }
}
