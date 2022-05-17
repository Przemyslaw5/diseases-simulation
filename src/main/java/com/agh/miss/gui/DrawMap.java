package com.agh.miss.gui;

import com.agh.miss.Simulation;
import com.agh.miss.gui.menu.Menu;
import com.agh.miss.mapElements.person.Person;
import com.agh.miss.mapElements.trace.Trace;
import com.agh.miss.parametersObject.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawMap {

    private Shape selectedShape = null;
    private Person selectedPerson = null;

    public void draw(Simulation simulation, Pane pane, int gridSize, Menu menu){

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

                //If is person or trace
                if(simulation.world.isOccupied(objectPoint)){

                    Shape shape;

                    //If is person
                    if(simulation.world.objectAt(objectPoint) instanceof Person){

                        //Set Shape, position and color
                        shape = new Circle(1.5 * gridSize + gridSize * i, 1.5 * gridSize + gridSize * j, gridSize * 0.5);
                        shape.setFill(Configuration.setColorPerson((Person) simulation.world.objectAt(objectPoint), simulation.world));

                        //Set selected person
                        shape.setOnMouseClicked(event -> {
                            if(selectedShape != null) selectedShape.setFill(Configuration.setColorPerson(selectedPerson, simulation.world));
                            shape.setFill(Configuration.setColorSelectedPerson());
                            menu.setSelectedPerson((Person)simulation.world.objectAt(objectPoint));
                            menu.update();
                            selectedShape = shape;
                            selectedPerson = (Person)simulation.world.objectAt(objectPoint);
                        });

                        pane.getChildren().add(shape);
                    }

                    //If is trace
                    else {

                        //Set Shape, position and color
                        shape = new Rectangle(gridSize, gridSize);
                        shape.setTranslateX(gridSize + gridSize * i);
                        shape.setTranslateY(gridSize + gridSize * j);
                        shape.setFill(Configuration.setColorTrace((Trace) simulation.world.objectAt(objectPoint)));

                        pane.getChildren().add(shape);
                    }
                }

                //Change the color of field where is selected person and is still live
                if(selectedPerson != null && selectedPerson.getDeathDay() == -1){
                    Shape selectedPersonShape = new Circle(1.5 * gridSize + gridSize * selectedPerson.getPosition().x, 1.5 * gridSize + gridSize * selectedPerson.getPosition().y, gridSize * 0.5);
                    selectedPersonShape.setFill(Configuration.setColorSelectedPerson());
                    selectedShape = selectedPersonShape;

                    pane.getChildren().add(selectedPersonShape);
                }
            }
        }
    }

    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
}
