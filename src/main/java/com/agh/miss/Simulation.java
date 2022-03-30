package com.agh.miss;

import com.agh.miss.map.World;

public class Simulation {

    // Default parameters
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int PEOPLE_NUMBER = 300;
    public static final double PERCENTAGE_OF_INFECTED_PEOPLE = 20.0;
    public static final double INFECTION_CHANCE = 66.6;

    public World world;
    public int dayOfSimulation = 1;

    public static Simulation startWithDefaultParams() {
        return new Simulation(
                WIDTH,
                HEIGHT,
                PEOPLE_NUMBER,
                PERCENTAGE_OF_INFECTED_PEOPLE,
                INFECTION_CHANCE
        );
    }

    public static Simulation startWithGivenParams(int peopleNumber,
                                                  double percentageOfInfectedPeople,
                                                  double infectionChance
    ) {
        return new Simulation(
                WIDTH,
                HEIGHT,
                peopleNumber,
                percentageOfInfectedPeople,
                infectionChance
        );
    }

    public Simulation(
            int width,
            int height,
            int peopleNumber,
            double percentageOfInfectedPeople,
            double infectionChance
    ) {
        world = new World(width, height, infectionChance);
        world.putStartPeople(peopleNumber, percentageOfInfectedPeople);
    }

    public void simulateDay() {
        dayOfSimulation++;
        world.run();
        world.infectPeople();
    }

}