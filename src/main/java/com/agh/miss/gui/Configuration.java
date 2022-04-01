package com.agh.miss.gui;

import com.agh.miss.map.World;
import com.agh.miss.mapElements.person.Person;
import javafx.scene.paint.Color;

public class Configuration {

    public final static Color HEALTHY_PERSON_COLOR = Color.rgb(255, 215, 150);
    public final static Color INFECTED_PERSON_COLOR = Color.rgb(255,0,0);
    public final static Color CURED_PERSON_COLOR = Color.rgb(78,235,0);

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
}
