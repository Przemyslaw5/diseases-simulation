package com.agh.miss.gui;

import com.agh.miss.map.World;
import com.agh.miss.mapElements.person.Person;
import com.agh.miss.mapElements.trace.Trace;
import javafx.scene.paint.Color;

public class Configuration {

    public final static Color HEALTHY_PERSON_COLOR = Color.rgb(255, 215, 150);
    public final static Color INFECTED_PERSON_COLOR = Color.rgb(255, 0, 0);
    public final static Color CURED_PERSON_COLOR = Color.rgb(78, 235, 0);
    public final static Color TRACE_BIGGEST_CHANCE = Color.rgb(99, 51, 9);

    public static int setGridSize(int availableWidth, int availableHeight, int mapWidth, int mapHeight){
        int size = availableWidth / (mapWidth + 2);
        size = Math.min(size, availableHeight / (mapHeight + 2));
        return size;
    }

    //Set color based if person is infected
    public static Color setColorPerson(Person person, World world) {
        return switch (person.getHealthState()) {
            case INFECTED -> INFECTED_PERSON_COLOR;
            case CURED -> CURED_PERSON_COLOR;
            // death people are removed before setting the color, so they won't be considered in this case
            default -> HEALTHY_PERSON_COLOR;
        };
    }

    //Set color based on powerTrace of trace
    public static Color setColorTrace (Trace trace){
        return switch (trace.getColorNumber()) {
            case 1 -> Color.rgb(99, 51, 9);
            case 2 -> Color.rgb(124, 63, 6);
            case 3 -> Color.rgb(150, 75, 0);
            case 4 -> Color.rgb(172, 103, 48);
            case 5 -> Color.rgb(192, 132, 87);
            case 6 -> Color.rgb(211, 162, 127);
            default -> throw new IllegalStateException("Unexpected value: " + trace.getColorNumber());
        };
    }
}
