package com.agh.miss.mapElements.person;

import com.agh.miss.map.World;
import com.agh.miss.mapElements.AbstractMapElement;
import com.agh.miss.parametersObject.MapDirection;
import com.agh.miss.parametersObject.Point;

import java.util.Objects;
import java.util.Random;

public class Person extends AbstractMapElement {

    public enum HealthState {
        HEALTHY,
        INFECTED,
        CURED,
        DEAD
    }

    private MapDirection direction;
    private final World world;
    private int infectionTime;
    private HealthState healthState;


    private static final Random random = new Random();

    public Person(Point startPosition, World world, HealthState healthState) {
        super(startPosition);
        direction = MapDirection.getRandomDirection();
        this.world = world;
        this.infectionTime = 0;
        this.healthState = healthState;
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
        return healthState.equals(HealthState.INFECTED);
    }

    public boolean isCured() {
        return healthState.equals(HealthState.CURED);
    }

    public int infectionTime() {
        return infectionTime;
    }

    public void infect(){
        healthState = HealthState.INFECTED;
    }

    public boolean canInfect(){
        return healthState.equals(HealthState.INFECTED);
    }

    public void incInfectionTime() {
        infectionTime++;
    }

    public void cure() {
        healthState = HealthState.CURED;
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