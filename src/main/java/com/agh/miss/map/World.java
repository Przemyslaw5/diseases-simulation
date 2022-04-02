package com.agh.miss.map;

import com.agh.miss.mapElements.person.Person;
import com.agh.miss.parametersObject.Point;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class World implements IWorldMap {
    public final int MAP_WIDTH;
    public final int MAP_HEIGHT;
    public final double infectionChance;
    public final double recoveryChance;
    public final int recoveryTime;
    public final double deathChance;
    private final Point leftBottomCorner;
    private final Point rightTopCorner;
    private int startPeopleNumber;

    private static final Random random = new Random();

    private final HashMap<Point, LinkedList<Person>> people = new HashMap<>();

    public World(
            int width,
            int height,
            double infectionChance,
            double recoveryChance,
            int recoveryTime,
            double deathChance,
            int startPeopleNumber
    ) {
        this.MAP_WIDTH = width;
        this.MAP_HEIGHT = height;
        this.infectionChance = infectionChance;
        this.recoveryChance = recoveryChance;
        this.recoveryTime = recoveryTime;
        this.deathChance = deathChance;
        this.leftBottomCorner = new Point(0, 0);
        this.rightTopCorner = new Point(width, height);
        this.startPeopleNumber = startPeopleNumber;
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
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        List<Person> allPeople = people.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        allPeople.forEach(Person::changeDirection);           //Every person must turn
        allPeople.forEach(Person::move);           //Every person must move
    }

    @Override
    public boolean isOccupied(Point position) {
        return people.get(position) != null;
    }

    @Override
    public Object objectAt(Point position) {
        if (people.get(position) != null)
            return people.get(position).get(0);
        return null;
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

    public void infectPeople() {
        people.forEach((position, listOfPeople) -> {
            if (listOfPeople.size() > 1 && listOfPeople.stream().anyMatch(Person::isInfected)) {
                listOfPeople.stream()
                        .filter(Person::isHealthy)
                        .filter(person -> random.nextDouble() * 100 <= infectionChance)
                        .forEach(Person::infect);
            }
        });
    }

    public void recoverPeople() {
        people.forEach((position, listOfPeople) -> listOfPeople.stream()
                .filter(Person::isInfected)
                .forEach(person -> {
                    if (person.infectionTime() >= recoveryTime && random.nextDouble() * 100 <= recoveryChance)
                        person.cure();
                    else
                        person.incInfectionTime();
                })
        );
    }

    public void killPeople() {
        people.forEach((position, listOfPeople) -> listOfPeople.stream()
                .filter(Person::isInfected)
                .filter(person -> (person.infectionTime() >= recoveryTime && random.nextDouble() * 100 <= deathChance))
                .forEach(Person::die)
        );
    }

    public void removeDeadPeople() {
        List<Person> allPeople = people.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        allPeople.stream()
                .filter(Person::isDead)
                .forEach(person -> {
                    this.people.get(person.getPosition()).remove(person);
                    if (this.people.get(person.getPosition()).isEmpty()) {
                        this.people.remove(person.getPosition());
                    }
                });
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

            if (random.nextDouble() * 100 <= percentageOfInfectedPeople)
                healthState = Person.HealthState.INFECTED;
            else
                healthState = Person.HealthState.HEALTHY;

            person = new Person(new Point(x, y), this, healthState);
            place(person);
        }
    }

    public void positionChanged(Point oldPosition, Person person) {
        people.get(oldPosition).remove(person);

        if (people.get(oldPosition).isEmpty()) people.remove(oldPosition);
        this.place(person);
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(leftBottomCorner, rightTopCorner);
    }
}