package com.agh.miss.mapElements.person;

import com.agh.miss.Simulation;
import com.agh.miss.map.World;
import com.agh.miss.mapElements.AbstractMapElement;
import com.agh.miss.mapElements.trace.Trace;
import com.agh.miss.parametersObject.MapDirection;
import com.agh.miss.parametersObject.Point;

import java.util.ArrayList;
import java.util.List;
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
    private int numberOfInfections = 0;
    private int deathDay = -1;

    private final List<String> informationHistory;

    private static final Random random = new Random();

    public Person(Point startPosition, World world, HealthState healthState) {
        super(startPosition);
        direction = MapDirection.getRandomDirection();
        this.world = world;
        this.infectionTime = 0;
        this.resistanceTime = 0;
        this.healthState = healthState;
        if (healthState == HealthState.INFECTED) {
            numberOfInfections++;
        }
        informationHistory = new ArrayList<>();
        informationHistory.add("Person start simulation in state: " + healthState + ".");
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

    public int getDeathDay() {
        return deathDay;
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

    public double getResistanceChance() {
        return Simulation.INFECTION_CHANCE * ((isCured()) ? (float) (getResistanceTime() / resistanceTime) : 1);
    }

    public int getNumberOfInfections() {
        return numberOfInfections;
    }

    public List<String> getInformationHistory() {
        return informationHistory;
    }

    public boolean willBeInfected(Trace trace) {
        return canBeInfected() && random.nextDouble() * 100 <= getResistanceChance() * (trace.getTracePower() / 100);
    }

    public void infect(int dayOfSimulation) {
        this.healthState = HealthState.INFECTED;
        resistanceTime = 0;
        numberOfInfections++;
        informationHistory.add("Person was infected on day: " + dayOfSimulation + ".");
    }

    public void cure(int dayOfSimulation) {
        this.healthState = HealthState.CURED;
        infectionTime = 0;
        informationHistory.add("Recovery occurred on day: " + dayOfSimulation + ".");
    }

    public void die(int deathDay) {
        this.healthState = HealthState.DEAD;
        this.deathDay = deathDay;
        infectionTime = 0;
        informationHistory.add("Person died of the disease on day: " + deathDay + ".");
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