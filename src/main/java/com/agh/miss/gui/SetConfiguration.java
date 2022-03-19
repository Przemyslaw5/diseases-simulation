package com.agh.miss.gui;

import com.agh.miss.map.World;
import com.agh.miss.mapElements.person.Person;
import javafx.scene.paint.Color;


public class SetConfiguration {

    public static int setGridSize(int availableWidth, int availableHeight, int mapWidth, int mapHeight){
        int size = availableWidth / (mapWidth + 2);
        size = Math.min(size, availableHeight / (mapHeight + 2));
        return size;
    }

    //Set color based on actualEnergy
    public static Color setColorPerson (Person person, World world){
        return Color.rgb(31, 18, 4);
    }
}
