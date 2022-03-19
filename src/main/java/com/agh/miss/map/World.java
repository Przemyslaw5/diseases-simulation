package com.agh.miss.map;

import com.agh.miss.mapElements.person.Person;
import com.agh.miss.parametersObject.Point;

import java.util.*;
import java.util.stream.Collectors;

public class World implements IWorldMap {
    public final int MAP_WIDTH;
    public final int MAP_HEIGHT;
    private final Point leftBottomCorner;
    private final Point rightTopCorner;

    private static final Random random = new Random();

    private HashMap<Point, LinkedList<Person>> people = new HashMap<>();

    public World(int width, int height){
        this.MAP_WIDTH = width;
        this.MAP_HEIGHT = height;
        this.leftBottomCorner = new Point(0,0);
        this.rightTopCorner = new Point(width, height);
    }

    public static Point repairPositionOnMap(Point position, World world){
        int newXPosition;
        int newYPosition;
        int width = world.rightTopCorner.x;
        int height = world.rightTopCorner.y;

        //Set x position
        if(position.x < world.leftBottomCorner.x)
            newXPosition = (width - Math.abs(position.x % width)) % width;
        else
            newXPosition = Math.abs(position.x % width);

        //Set y position
        if(position.y < world.leftBottomCorner.x)
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
        if(canMoveTo(person.getPosition())){
            if(!people.containsKey(person.getPosition())){
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
        allPeople.forEach(person -> person.move());           //Every person must move
    }

    @Override
    public boolean isOccupied(Point position) {
        if(people.get(position) != null) return true;
        return false;
    }

    @Override
    public Object objectAt(Point position) {
        if(people.get(position) != null)
            return people.get(position).get(0);
        return null;
    }


    public int numberAllPeopleOnMap(){
        List<Person> allPeople = people.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
        return allPeople.size();
    }

    private Point getChildField(Point adult){
        LinkedList<Point> ListOfFreeFields = new LinkedList<>();
        if(!isOccupied(repairPositionOnMap(new Point(adult.x, adult.y + 1), this))) ListOfFreeFields.add(new Point(adult.x, adult.y + 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x + 1, adult.y + 1), this))) ListOfFreeFields.add(new Point(adult.x + 1, adult.y + 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x + 1, adult.y), this))) ListOfFreeFields.add(new Point(adult.x + 1, adult.y));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x + 1, adult.y - 1), this))) ListOfFreeFields.add(new Point(adult.x + 1, adult.y - 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x, adult.y - 1), this))) ListOfFreeFields.add(new Point(adult.x, adult.y - 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x - 1, adult.y - 1), this))) ListOfFreeFields.add(new Point(adult.x - 1, adult.y - 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x - 1, adult.y), this))) ListOfFreeFields.add(new Point(adult.x - 1, adult.y));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x - 1, adult.y + 1), this))) ListOfFreeFields.add(new Point(adult.x - 1, adult.y + 1));
        if(ListOfFreeFields.size() == 0) {
            ListOfFreeFields.add(new Point(adult.x, adult.y + 1)); ListOfFreeFields.add(new Point(adult.x + 1, adult.y + 1)); ListOfFreeFields.add(new Point(adult.x + 1, adult.y)); ListOfFreeFields.add(new Point(adult.x + 1, adult.y - 1));
            ListOfFreeFields.add(new Point(adult.x, adult.y - 1)); ListOfFreeFields.add(new Point(adult.x - 1, adult.y - 1)); ListOfFreeFields.add(new Point(adult.x - 1, adult.y)); ListOfFreeFields.add(new Point(adult.x - 1, adult.y + 1));
        }
        int randomIndex = random.nextInt(ListOfFreeFields.size());
        return ListOfFreeFields.get(randomIndex);
    }

    public void putStartPeople(int number){
        Person person;
        for(int i = 0; i < number; i++){
            int x, y;
            do{
                x = random.nextInt(rightTopCorner.x);
                y = random.nextInt(rightTopCorner.y);
            } while (isOccupied(new Point(x, y)));
            person = new Person(new Point(x, y), this);
            place(person);
        }
    }

    public void positionChanged(Point oldPosition, Person person){
        people.get(oldPosition).remove(person);

        if(people.get(oldPosition).isEmpty()) people.remove(oldPosition);
        this.place(person);
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(leftBottomCorner, rightTopCorner);
    }
}