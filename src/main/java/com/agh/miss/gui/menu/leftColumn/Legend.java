package com.agh.miss.gui.menu.leftColumn;

import com.agh.miss.gui.Configuration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Legend extends VBox {

    public Legend() {

        Label legend = new Label("Legend of map");
        legend.setFont(Font.font(20));
        HBox labelLegend = new HBox(legend);
        labelLegend.setAlignment(Pos.BASELINE_CENTER);

        Label healthyPerson = new Label("    - Healthy person");
        Circle circle1 = new Circle(10);
        circle1.setFill(Configuration.HEALTHY_PERSON_COLOR);
        HBox firstElement = new HBox(circle1, healthyPerson);

        Label infectedPerson  = new Label("    - Infected person");
        Circle circle2 = new Circle(10);
        circle2.setFill(Configuration.INFECTED_PERSON_COLOR);
        HBox secondElement = new HBox(circle2, infectedPerson);

        Label curedPerson  = new Label("    - Cured person");
        Circle circle3 = new Circle(10);
        circle3.setFill(Configuration.CURED_PERSON_COLOR);
        HBox thirdElement = new HBox(circle3, curedPerson);

        getChildren().addAll(labelLegend, firstElement, secondElement, thirdElement);

        // up right down left
        setPadding(new Insets(10, 0, 30, 10));
    }
}
