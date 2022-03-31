package com.agh.miss.mapElements.person;

import com.agh.miss.map.World;
import com.agh.miss.mapElements.AbstractMapElement;
import com.agh.miss.parametersObject.MapDirection;
import com.agh.miss.parametersObject.Point;

import java.util.Objects;
import java.util.Random;

public class Person extends AbstractMapElement {

    private MapDirection direction;
    private final World world;
    private boolean isInfected;
    private boolean isCured;
    private int infectionTime;

    private static final Random random = new Random();

    public Person(Point startPosition, World world, boolean isInfected) {
        super(startPosition);
        direction = MapDirection.getRandomDirection();
        this.world = world;
        this.isInfected = isInfected;
        this.isCured = false;
        this.infectionTime = 0;
    }

    public void changeDirection(){
        int numberToTurn = random.nextInt(8);
        for(int i = 0; i < numberToTurn; i++){
            this.direction = this.direction.next();
        }
    }

    public void move(){

        //If the person goes beyond the map, it appears from the other side
        Point newPosition = this.getPosition().add(this.direction.toUnitVector());
        newPosition = World.repairPositionOnMap(newPosition, world);

        //Change position
        Point oldPosition = this.getPosition();
        this.setActualPosition(newPosition);
        world.positionChanged(oldPosition, this);
    }

    public boolean isInfected() {
        return isInfected;
    }

    public boolean isCured() {
        return isCured;
    }

    public int infectionTime() {
        return infectionTime;
    }

    public void infect(){
        this.isInfected = true;
    }

    public boolean canInfect(){
        return isInfected;
    }

    public void incInfectionTime() {
        infectionTime++;
    }

    public void cure() {
        isInfected = false;
        isCured = true;
        infectionTime = 0;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return direction == person.direction &&
                Objects.equals(world, person.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, world);
    }
}