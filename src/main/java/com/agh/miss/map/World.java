package com.agh.miss.map;

import com.agh.miss.mapElements.person.Person;
import com.agh.miss.mapElements.trace.Trace;
import com.agh.miss.parametersObject.Point;

import java.util.*;
import java.util.function.Predicate;

public class World implements IWorldMap {
    public final int MAP_WIDTH;
    public final int MAP_HEIGHT;
    private final int startPeopleNumber;
    private final double infectionChance;
    private final double recoveryChance;
    private final int recoveryTime;
    private final int resistanceTime;
    private final double deathChance;
    private final Point leftBottomCorner;
    private final Point rightTopCorner;

    private static final Random random = new Random();

    private final HashMap<Point, LinkedList<Person>> people = new HashMap<>();
    private final HashMap<Point, Trace> traces = new HashMap<>();

    private int initialSusceptible;
    private int infections = 0;
    private int recovers = 0;

    public World(
            int width,
            int height,
            int startPeopleNumber,
            double infectionChance,
            double recoveryChance,
            int recoveryTime,
            int resistanceTime,
            double deathChance
    ) {
        this.MAP_WIDTH = width;
        this.MAP_HEIGHT = height;
        this.startPeopleNumber = startPeopleNumber;
        this.infectionChance = infectionChance;
        this.recoveryChance = recoveryChance;
        this.recoveryTime = recoveryTime;
        this.resistanceTime = resistanceTime;
        this.deathChance = deathChance;
        this.leftBottomCorner = new Point(0, 0);
        this.rightTopCorner = new Point(width, height);
    }

    public static Point repairPositionOnMap(Point position, World world) {
        int newXPosition;
        int newYPosition;
        int width = world.rightTopCorner.x;
        int height = world.rightTopCorner.y;

        //Set x position
        if (position.x < world.leftBottomCorner.x)
            newXPosition = (width - Math.abs(position.x % width)) % width;
        else
            newXPosition = Math.abs(position.x % width);

        //Set y position
        if (position.y < world.leftBottomCorner.x)
            newYPosition = (height - Math.abs(position.x % height)) % height;
        else
            newYPosition = Math.abs(position.y % height);

        return new Point(newXPosition, newYPosition);
    }

    @Override
    public boolean canMoveTo(Point position) {
        return position.precedes(rightTopCorner) && position.follows(leftBottomCorner);
    }

    @Override
    public boolean place(Person person) {
        if (canMoveTo(person.getPosition())) {
            if (!people.containsKey(person.getPosition())) {
                people.put(person.getPosition(), new LinkedList<>());
            }
            people.get(person.getPosition()).add(person);

            if (person.canInfect()) placeTrace(person);
            return true;
        }
        return false;
    }

    public void placeTrace(Person person) {
        if (traces.containsKey(person.getPosition()))
            traces.get(person.getPosition()).resetTrace();
        else
            traces.put(person.getPosition(), new Trace(person.getPosition()));
    }

    @Override
    public void run() {
        List<Person> allPeople = people.values().stream()
                .flatMap(Collection::stream).toList();

        allPeople.forEach(Person::changeDirection);  //Every person must turn
        allPeople.forEach(Person::move);             //Every person must move
    }

    @Override
    public boolean isOccupied(Point position) {
        if (people.get(position) != null) return true;
        return traces.get(position) != null;
    }

    @Override
    public Object objectAt(Point position) {
        if (people.get(position) != null)
            return people.get(position).get(0);
        return traces.get(position);
    }

    public int numberPeopleOnMap() {
        return (int) people.values().stream()
                .flatMap(Collection::stream)
                .filter(Predicate.not(Person::isDead))
                .count();
    }

    public int numberHealthyPeopleOnMap() {
        return (int) people.values().stream()
                .flatMap(Collection::stream)
                .filter(Person::isHealthy)
                .count();
    }

    public int numberInfectedPeopleOnMap() {
        return (int) people.values().stream()
                .flatMap(Collection::stream)
                .filter(Person::isInfected)
                .count();
    }

    public int numberCuredPeopleOnMap() {
        return (int) people.values().stream()
                .flatMap(Collection::stream)
                .filter(Person::isCured)
                .count();
    }

    public int numberDeadPeople() {
        return startPeopleNumber - numberPeopleOnMap();
    }

    public void infectPeople(int dayOfSimulation) {
        traces.forEach((position, trace) -> {
            if (people.get(position) != null) {
                people.get(position).stream()
                        .filter(person -> person.willBeInfected(trace))
                        .forEach(p -> p.infect(dayOfSimulation));
            }
        });
    }

    public void recoverPeople(int dayOfSimulation) {
        people.forEach((position, listOfPeople) -> listOfPeople.stream()
                .filter(Person::isInfected)
                .forEach(person -> {
                    if (person.getInfectionTime() >= recoveryTime && random.nextDouble() * 100 <= recoveryChance)
                        person.cure(dayOfSimulation);
                    else
                        person.incInfectionTime();
                })
        );
    }

    public void reduceCuredPeopleResistance() {
        people.forEach((position, listOfPeople) -> listOfPeople.stream()
                .filter(Person::isCured)
                .filter(person -> person.getResistanceTime() < resistanceTime)
                .forEach(Person::incResistanceTime)
        );
    }

    public void killPeople(int deathDay) {
        people.forEach((position, listOfPeople) -> listOfPeople.stream()
                .filter(Person::isInfected)
                .filter(person -> (person.getInfectionTime() >= recoveryTime && random.nextDouble() * 100 <= deathChance))
                .forEach(person -> person.die(deathDay))
        );
    }

    public void removeDeadPeople() {
        List<Person> allPeople = people.values().stream()
                .flatMap(Collection::stream).toList();

        allPeople.stream()
                .filter(Person::isDead)
                .forEach(person -> {
                    this.people.get(person.getPosition()).remove(person);
                    if (this.people.get(person.getPosition()).isEmpty()) {
                        this.people.remove(person.getPosition());
                    }
                }
        );
    }

    public void putStartPeople(double percentageOfInfectedPeople) {
        Person person;
        Person.HealthState healthState;
        for (int i = 0; i < startPeopleNumber; i++) {
            int x, y;
            do {
                x = random.nextInt(rightTopCorner.x);
                y = random.nextInt(rightTopCorner.y);
            } while (isOccupied(new Point(x, y)));

            if (i < startPeopleNumber * percentageOfInfectedPeople / 100)
                healthState = Person.HealthState.INFECTED;
            else {
                healthState = Person.HealthState.HEALTHY;
                initialSusceptible++;
            }

            person = new Person(new Point(x, y), this, healthState);
            place(person);
        }
    }

    public void positionChanged(Point oldPosition, Person person) {
        people.get(oldPosition).remove(person);

        if (people.get(oldPosition).isEmpty()) people.remove(oldPosition);
        this.place(person);
    }

    public void updateAndRemoveOldTraces() {
        List<Trace> tmpTraces = traces.values().stream().toList();

        tmpTraces.forEach(trace -> {
            trace.updateTrace();
            if (trace.getTracePower() == 0.0) {
                traces.remove(trace.getPosition());
            }
        });
    }

    public double calculateR0() {
        double r0 = 0;

        int previousInfections = this.infections;
        int previousRecovers = this.recovers;

        int susceptible = numberHealthyPeopleOnMap() + numberCuredPeopleOnMap();

        this.infections = numberInfectedPeopleOnMap() - previousInfections;
        if (this.infections < 0) this.infections = 0;

        this.recovers = numberCuredPeopleOnMap() - previousRecovers;
        if (this.recovers < 0) this.recovers = 0;

        if (startPeopleNumber - susceptible != 0 && susceptible != 0) {
            r0 = (Math.log((double) initialSusceptible / susceptible) / (startPeopleNumber - susceptible));
            r0 = r0 * initialSusceptible;
        }

        return r0;
    }

    public int getStartPeopleNumber() {
        return startPeopleNumber;
    }

    public int getResistanceTime() {
        return resistanceTime;
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(leftBottomCorner, rightTopCorner);
    }
}