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
    private final Point leftBottomCorner;
    private final Point rightTopCorner;

    private static final Random random = new Random();

    private final HashMap<Point, LinkedList<Person>> people = new HashMap<>();

    public World(int width, int height, double infectionChance) {
        this.MAP_WIDTH = width;
        this.MAP_HEIGHT = height;
        this.infectionChance = infectionChance;
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
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        List<Person> allPeople = people.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());

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

    public int numberPeopleOnMap(){
        return people.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .size();
    }

    public int numberHealthyPeopleOnMap(){
        return people.values().stream()
                .flatMap(Collection::stream)
                .filter(Person::isInfected)
                .collect(Collectors.toList())
                .size();
    }

    public int numberInfectedPeopleOnMap() {
        return people.values().stream()
                .flatMap(Collection::stream)
                .filter(Predicate.not(Person::isInfected))
                .collect(Collectors.toList())
                .size();
    }

    public void infectPeople(){
        people.forEach((position, listOfPeople) -> {
            if(listOfPeople.size() == 2 && listOfPeople.get(0).isInfected() != listOfPeople.get(1).isInfected()){
                listOfPeople.sort(Comparator.comparing(Person::isInfected).reversed());

                if (listOfPeople.get(0).canInfect() && random.nextDouble() * 100 <= infectionChance) {
                    listOfPeople.get(1).infect();
                }
            }
        });
    }

    public void putStartPeople(int peopleNumber, double percentageOfInfectedPeople) {
        Person person;
        for (int i = 0; i < peopleNumber; i++) {
            int x, y;
            do {
                x = random.nextInt(rightTopCorner.x);
                y = random.nextInt(rightTopCorner.y);
            } while (isOccupied(new Point(x, y)));
            person = new Person(new Point(x, y), this, random.nextDouble() * 100 <= percentageOfInfectedPeople);
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