package com.agh.miss.gui.menu;

import com.agh.miss.Simulation;
import com.agh.miss.gui.Visualization;
import com.agh.miss.gui.menu.leftColumn.Legend;
import com.agh.miss.gui.menu.leftColumn.Parameters;
import com.agh.miss.gui.menu.leftColumn.Statistics;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends HBox {

    private final Simulation simulation;

    private final Statistics stats;
    private final Parameters parameters;
    private final Legend legend;

    public Menu(Simulation simulation, Visualization visualization){
        this.simulation = simulation;

        this.stats = new Statistics(visualization);
        this.parameters = new Parameters(visualization);
        this.legend = new Legend();

        VBox vBoxColumn = new VBox();
        vBoxColumn.getChildren().addAll(stats, parameters, legend);

        getChildren().addAll(vBoxColumn);
    }

    public void onUpdate(){
        stats.onUpdate();
    }

    public boolean isApplicationRun(){
        return parameters.isApplicationRun();
    }

    public Parameters getParameters() {
        return parameters;
    }
}
