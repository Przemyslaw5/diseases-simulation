package com.agh.miss;

import com.agh.miss.map.World;

public class Simulation {

    public World world;

    public Simulation() {

        world = new World(50, 50, 66.6);
        world.putStartPeople(300, 20.0);
    }

    public void simulateDay() {
        world.run();
        world.infectPeoples();
    }

}