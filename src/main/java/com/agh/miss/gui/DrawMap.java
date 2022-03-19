package com.agh.miss.gui;

import com.agh.miss.Simulation;
import com.agh.miss.mapElements.person.Person;
import com.agh.miss.parametersObject.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawMap {

    public void draw(Simulation simulation, Pane pane, int gridSize){

        for(int i = 0; i <= simulation.world.MAP_WIDTH + 1; i++){
            for(int j = 0; j <= simulation.world.MAP_HEIGHT + 1; j++){
                Point objectPoint = new Point(i, j);

                //Creating a black frame around the map
                if((i == 0 || i == simulation.world.MAP_WIDTH + 1 || j == 0 || j == simulation.world.MAP_HEIGHT + 1)){
                    Rectangle rectangle = new Rectangle(gridSize, gridSize);
                    rectangle.setX(gridSize * i);
                    rectangle.setY(gridSize * j);
                    rectangle.setFill(Color.rgb(0,0,0));
                    pane.getChildren().add(rectangle);
                }

                //If is person
                if(simulation.world.isOccupied(objectPoint)){

                    Shape shape;

                    //If is person
                    if(simulation.world.objectAt(objectPoint) instanceof Person){

                            //Set Shape, position and color
                            shape = new Circle(1.5 * gridSize + gridSize * i, 1.5 * gridSize + gridSize * j, gridSize * 0.5);
                            shape.setFill(SetConfiguration.setColorPerson((Person) simulation.world.objectAt(objectPoint), simulation.world));

                            pane.getChildren().add(shape);
                    }
                }
            }
        }
    }
}
