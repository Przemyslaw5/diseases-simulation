package com.agh.miss.mapElements.person;

import com.agh.miss.Simulation;
import com.agh.miss.map.World;
import com.agh.miss.mapElements.AbstractMapElement;
import com.agh.miss.mapElements.trace.Trace;
import com.agh.miss.parametersObject.MapDirection;
import com.agh.miss.parametersObject.Point;

import java.util.Objects;
import java.util.Random;

public class Person extends AbstractMapElement {

    public enum HealthState {
        HEALTHY,
        INFECTED,
        CURED,
        DEAD;

        @Override
        public String toString() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase();
        }
    }

    private MapDirection direction;
    private final World world;
    private int infectionTime;
    private int resistanceTime;
    private HealthState healthState;


    private static final Random random = new Random();

    public Person(Point startPosition, World world, HealthState healthState) {
        super(startPosition);
        direction = MapDirection.getRandomDirection();
        this.world = world;
        this.infectionTime = 0;
        this.resistanceTime = 0;
        this.healthState = healthState;
    }

    public void changeDirection() {
        int numberToTurn = random.nextInt(8);
        for (int i = 0; i < numberToTurn; i++) {
            this.direction = this.direction.next();
        }
    }

    public void move() {

        //If the person goes beyond the map, it appears from the other side
        Point newPosition = this.getPosition().add(this.direction.toUnitVector());
        newPosition = World.repairPositionOnMap(newPosition, world);

        //Change position
        Point oldPosition = this.getPosition();
        this.setActualPosition(newPosition);
        world.positionChanged(oldPosition, this);
    }

    public boolean isHealthy() {
        return this.healthState == HealthState.HEALTHY;
    }

    public boolean isInfected() {
        return this.healthState == HealthState.INFECTED;
    }

    public boolean isCured() {
        return this.healthState == HealthState.CURED;
    }

    public boolean isDead() {
        return this.healthState == HealthState.DEAD;
    }

    public HealthState getHealthState() {
        return this.healthState;
    }

    public int getInfectionTime() {
        return infectionTime;
    }

    public int getResistanceTime() {
        return resistanceTime;
    }

    public boolean canInfect() {
        return this.healthState == HealthState.INFECTED;
    }

    public void incInfectionTime() {
        infectionTime++;
    }

    public void incResistanceTime() {
        resistanceTime++;
    }

    public boolean canBeInfected() {
        if (isHealthy()) {
            return true;
        } else return isCured();
    }

    public boolean willBeInfected(Trace trace) {
        if (canBeInfected()) {
            return random.nextDouble() * 100 <=
                    Simulation.INFECTION_CHANCE
                            * (trace.getTracePower() / 100)
                            * ((isCured()) ? (float) (getResistanceTime() / resistanceTime) : 1);
        } else return false;
    }

    public void infect() {
        this.healthState = HealthState.INFECTED;
        resistanceTime = 0;
    }

    public void cure() {
        this.healthState = HealthState.CURED;
        infectionTime = 0;
    }

    public void die() {
        this.healthState = HealthState.DEAD;
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