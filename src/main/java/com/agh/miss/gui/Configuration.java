package com.agh.miss.gui;

import com.agh.miss.map.World;
import com.agh.miss.mapElements.person.Person;
import com.agh.miss.mapElements.trace.Trace;
import javafx.scene.paint.Color;

public class Configuration {

    public final static Color HEALTHY_PERSON_COLOR = Color.rgb(255, 215, 150);
    public final static Color INFECTED_PERSON_COLOR = Color.rgb(255,0,0);
    public final static Color CURED_PERSON_COLOR = Color.rgb(78,235,0);

    public final static Color TRACE_BIGGEST_CHANCE = Color.rgb(99, 51, 9);

    public static int setGridSize(int availableWidth, int availableHeight, int mapWidth, int mapHeight){
        int size = availableWidth / (mapWidth + 2);
        size = Math.min(size, availableHeight / (mapHeight + 2));
        return size;
    }

    //Set color based if person is infected
    public static Color setColorPerson (Person person, World world){
        if (person.isInfected())
            return INFECTED_PERSON_COLOR;
        else if (person.isCured())
            return CURED_PERSON_COLOR;
        else
            return HEALTHY_PERSON_COLOR;
    }

    //Set color based on powerTrace of trace
    public static Color setColorTrace (Trace trace){
        if(trace.getTracePower() >= 90.0)
            return TRACE_BIGGEST_CHANCE;
        else if(trace.getTracePower() >= 70.0)
            return Color.rgb(124, 63, 6);
        else if(trace.getTracePower() >= 50.0)
            return Color.rgb(150, 75, 0);
        else if(trace.getTracePower() >= 30.0)
            return Color.rgb(172, 103, 48);
        else if(trace.getTracePower() >= 10.0)
            return Color.rgb(192, 132, 87);
        else
            return Color.rgb(211, 162, 127);
    }
}
