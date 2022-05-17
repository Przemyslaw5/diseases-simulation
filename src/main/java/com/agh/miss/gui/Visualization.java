package com.agh.miss.gui;

import com.agh.miss.Simulation;
import com.agh.miss.gui.menu.Menu;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class Visualization extends Application {

    private static final int AVAILABLE_WIDTH  = 1800;
    private static final int AVAILABLE_HEIGHT = 800;
    private static final int WIDTH_STATISTICS = 950;
    private static final int HEIGHT_STATISTICS = 750;
    private static int GRID_SIZE;
    private static int WIDTH;
    private static int HEIGHT;
    private static final long TIME_STEP_ANIMATION_MAP = 100_000_000;
    private static final String TITLE = "Diseases simulation";
    private final List<Stage> stages = new LinkedList<>();
    public Simulation simulation;
    private Menu menu;
    private DrawMap drawMap;

    @Override
    public void start(Stage stage) {

        stages.add(stage);
        stage.setTitle(TITLE);
        simulation = Simulation.startWithDefaultParams();
        this.drawMap = new DrawMap();

        //Set size of one element on map, width and height of window
        GRID_SIZE = Configuration.setGridSize(AVAILABLE_WIDTH - WIDTH_STATISTICS, AVAILABLE_HEIGHT, simulation.world.MAP_WIDTH, simulation.world.MAP_HEIGHT);
        WIDTH = GRID_SIZE * (simulation.world.MAP_WIDTH + 2) + WIDTH_STATISTICS;
        HEIGHT = Math.max(HEIGHT_STATISTICS, GRID_SIZE * (simulation.world.MAP_HEIGHT + 2));

        //Start simulation
        evolutionAnimation(stage);
    }

    public void evolutionAnimation(Stage stage) {

        HBox hBox = new HBox();

        //Creating a scene for map
        Scene scene = new Scene(hBox, WIDTH, HEIGHT);

        //Creating a platform for object on the map
        Pane mapElementsPane = new Pane();

        //Creating a menu of the application
        menu = new Menu(simulation, this);

        hBox.getChildren().addAll(mapElementsPane, menu);

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            @Override
            public void handle(long now) {
                if (menu.isApplicationRun() && (now - lastUpdate >= TIME_STEP_ANIMATION_MAP)) {
                    lastUpdate = now;
                    simulationStep(simulation, mapElementsPane, menu);
                }
            }
        };
        animationTimer.start();

        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            stages.forEach(Stage::close);
        });
    }

    public void startWithGivenParams(int peopleNumber,
                                     double percentageOfInfectedPeople,
                                     double infectionChance,
                                     double recoveryChance,
                                     int recoveryTime,
                                     int curedPeopleResistanceTime,
                                     double deathChance
    ) {
        this.simulation = Simulation.startWithGivenParams(
                peopleNumber,
                percentageOfInfectedPeople,
                infectionChance,
                recoveryChance,
                recoveryTime,
                curedPeopleResistanceTime,
                deathChance
        );
        drawMap.setSelectedShape(null);
        drawMap.setSelectedPerson(null);
        menu.reset(simulation);
    }

    private void simulationStep(Simulation simulation, Pane mapElementsPane, Menu menu) {
        mapElementsPane.getChildren().clear();
        simulation.simulateDay();
        drawMap.draw(simulation, mapElementsPane, GRID_SIZE, menu);
        simulation.world.updateAndRemoveOldTraces();
        menu.update();
    }

    public static void main(String[] args) {
        launch(args);
    }
}