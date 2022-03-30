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

        Label percentageInfectedPeopleLabel = new Label("Percentage of infected people: ");
        percentageInfectedPeopleLabel.setPrefWidth(210);
        TextField percentageInfectedPeopleTextField = new TextField(String.valueOf(Simulation.PERCENTAGE_OF_INFECTION));
        percentageInfectedPeopleTextField.setPrefColumnCount(5);
        Label percentageLabel1 = new Label("%");
        HBox hBoxPercentageInfectedPeople = new HBox();
        hBoxPercentageInfectedPeople.getChildren().addAll(percentageInfectedPeopleLabel, percentageInfectedPeopleTextField, percentageLabel1);

        Label percentageChanceOfInfectLabel = new Label("Percentage chance of infect: ");
        percentageChanceOfInfectLabel.setPrefWidth(210);
        TextField percentageChanceOfInfectTextField = new TextField(String.valueOf(Simulation.PERCENTAGE_CHANCE_OF_INFECTED_PEOPLE));
        percentageChanceOfInfectTextField.setPrefColumnCount(5);
        Label percentageLabel2 = new Label("%");
        HBox hBoxPercentageChanceOfInfect = new HBox();
        hBoxPercentageChanceOfInfect.getChildren().addAll(percentageChanceOfInfectLabel, percentageChanceOfInfectTextField, percentageLabel2);

        Button submitButton = new Button("Run simulation with given parameters");

        //Setting an action for the Submit button
        submitButton.setOnAction(e -> {
            int peopleNumber = Integer.parseInt(peopleTextField.getText());
            double percentageOfInfection = Double.parseDouble(percentageInfectedPeopleTextField.getText());
            double percentageOfInfectedPeople = Double.parseDouble(percentageChanceOfInfectTextField.getText());
            visualization.startWithGivenParams(
                    peopleNumber,
                    percentageOfInfection,
                    percentageOfInfectedPeople
            );
        });

        Button clearButton = new Button("Restore default parameters");
        submitButton.setPrefWidth(300);
        clearButton.setPrefWidth(300);

        //Setting an action for the Clear button
        clearButton.setOnAction(e -> {
            System.out.println("Wczytaj defaultowe wartosci");
            peopleTextField.setText(String.valueOf(Simulation.PEOPLE_NUMBER));
            percentageInfectedPeopleTextField.setText(String.valueOf(Simulation.PERCENTAGE_OF_INFECTION));
            percentageChanceOfInfectTextField.setText(String.valueOf(Simulation.PERCENTAGE_CHANCE_OF_INFECTED_PEOPLE));
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

        getChildren().addAll(parametersLabel, hBoxPeople, hBoxPercentageInfectedPeople, hBoxPercentageChanceOfInfect, submitButton, clearButton, buttonPausePlay);
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