package com.agh.miss.map;

import com.agh.miss.mapElements.person.Person;
import com.agh.miss.parametersObject.Point;

public interface IWorldMap {

    boolean canMoveTo(Point position);

    boolean place(Person person);

    void run();

    boolean isOccupied(Point position);

    Object objectAt(Point position);
}