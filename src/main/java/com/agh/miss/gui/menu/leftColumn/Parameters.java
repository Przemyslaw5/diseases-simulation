package com.agh.miss.gui.menu.leftColumn;

import com.agh.miss.Simulation;
import com.agh.miss.gui.Visualization;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Parameters extends VBox {

    private final Button buttonPausePlay;
    private boolean applicationRun = true;

    public Parameters(Visualization visualization) {

        Label parametersLabel = new Label("Parameters of simulation");
        parametersLabel.setFont(Font.font(20));
        HBox labelParameters = new HBox(parametersLabel);
        labelParameters.setAlignment(Pos.BASELINE_CENTER);

        Label peopleLabel = new Label("Number of people: ");
        peopleLabel.setPrefWidth(210);
        TextField peopleTextField = new TextField(String.valueOf(Simulation.PEOPLE_NUMBER));
        peopleTextField.setPrefColumnCount(5);
        HBox hBoxPeople = new HBox();
        hBoxPeople.getChildren().addAll(peopleLabel, peopleTextField);

        Label percentageOfInfectedPeopleLabel = new Label("Percentage of infected people: ");
        percentageOfInfectedPeopleLabel.setPrefWidth(210);
        TextField percentageOfInfectedPeopleTextField = new TextField(String.valueOf(Simulation.PERCENTAGE_OF_INFECTED_PEOPLE));
        percentageOfInfectedPeopleTextField.setPrefColumnCount(5);
        Label percentageLabel1 = new Label("%");
        HBox hBoxPercentageOfInfectedPeople = new HBox();
        hBoxPercentageOfInfectedPeople.getChildren().addAll(percentageOfInfectedPeopleLabel, percentageOfInfectedPeopleTextField, percentageLabel1);

        Label infectionChanceLabel = new Label("Infection chance: ");
        infectionChanceLabel.setPrefWidth(210);
        TextField infectionChanceTextField = new TextField(String.valueOf(Simulation.INFECTION_CHANCE));
        infectionChanceTextField.setPrefColumnCount(5);
        Label percentageLabel2 = new Label("%");
        HBox hBoxInfectionChance = new HBox();
        hBoxInfectionChance.getChildren().addAll(infectionChanceLabel, infectionChanceTextField, percentageLabel2);

        Button submitButton = new Button("Run simulation with given parameters");

        //Setting an action for the Submit button
        submitButton.setOnAction(e -> {
            int peopleNumber = Integer.parseInt(peopleTextField.getText());
            double percentageOfInfectedPeople = Double.parseDouble(percentageOfInfectedPeopleTextField.getText());
            double infectionChance = Double.parseDouble(infectionChanceTextField.getText());
            visualization.startWithGivenParams(
                    peopleNumber,
                    percentageOfInfectedPeople,
                    infectionChance
            );
        });

        Button clearButton = new Button("Restore default parameters");
        submitButton.setPrefWidth(300);
        clearButton.setPrefWidth(300);

        //Setting an action for the Clear button
        clearButton.setOnAction(e -> {
            peopleTextField.setText(String.valueOf(Simulation.PEOPLE_NUMBER));
            percentageOfInfectedPeopleTextField.setText(String.valueOf(Simulation.PERCENTAGE_OF_INFECTED_PEOPLE));
            infectionChanceTextField.setText(String.valueOf(Simulation.INFECTION_CHANCE));
        });

        buttonPausePlay = new Button("Pause");
        buttonPausePlay.setPrefWidth(300);

        //Setting an action for the PausePlay button
        buttonPausePlay.setOnAction(e -> {
            this.applicationRun = !this.applicationRun;

            if(!applicationRun)
                this.buttonPausePlay.setText("Play");
            else
                this.buttonPausePlay.setText("Pause");
        });

        getChildren().addAll(parametersLabel, hBoxPeople, hBoxPercentageOfInfectedPeople, hBoxInfectionChance, submitButton, clearButton, buttonPausePlay);
        setSpacing(5);
        // up right down left
        setPadding(new Insets(10, 0, 30, 10));
    }

    public void resetPausePlayButton() {
        this.applicationRun = true;
        this.buttonPausePlay.setText("Pause");
    }

    public boolean isApplicationRun() {
        return applicationRun;
    }
}