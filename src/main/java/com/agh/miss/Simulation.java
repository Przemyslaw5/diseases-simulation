package com.agh.miss;

import com.agh.miss.map.World;

import java.util.List;

public class Simulation {

    // Default parameters
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int PEOPLE_NUMBER = 300;
    public static final double PERCENTAGE_OF_INFECTED_PEOPLE = 5.0;
    public static final double INFECTION_CHANCE = 30.0;
    public static final double RECOVERY_CHANCE = 1.0;
    public static final int RECOVERY_TIME = 210;
    public static final List<Double> TRACE_POWERS = List.of(100.0, 80.0, 60.0, 40.0, 20.0, 5.0);

    public World world;
    public int dayOfSimulation = 1;

    public static Simulation startWithDefaultParams() {
        return new Simulation(
                WIDTH,
                HEIGHT,
                PEOPLE_NUMBER,
                PERCENTAGE_OF_INFECTED_PEOPLE,
                INFECTION_CHANCE,
                RECOVERY_CHANCE,
                RECOVERY_TIME
        );
    }

    public static Simulation startWithGivenParams(int peopleNumber,
                                                  double percentageOfInfectedPeople,
                                                  double infectionChance,
                                                  double recoveryChance,
                                                  int recoveryTime
    ) {
        return new Simulation(
                WIDTH,
                HEIGHT,
                peopleNumber,
                percentageOfInfectedPeople,
                infectionChance,
                recoveryChance,
                recoveryTime
        );
    }

    public Simulation(
            int width,
            int height,
            int peopleNumber,
            double percentageOfInfectedPeople,
            double infectionChance,
            double recoveryChance,
            int recoveryTime
    ) {
        world = new World(width, height, infectionChance, recoveryChance, recoveryTime);
        world.putStartPeople(peopleNumber, percentageOfInfectedPeople);
    }

    public void simulateDay() {
        dayOfSimulation++;
        world.run();
        world.infectPeople();
        world.recoverPeople();
        world.updateAndRemoveOldTraces();
    }

}