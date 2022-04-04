package com.agh.miss.mapElements.trace;

import com.agh.miss.Simulation;
import com.agh.miss.mapElements.AbstractMapElement;
import com.agh.miss.parametersObject.Point;

public class Trace extends AbstractMapElement {

    private double tracePower;
    private int currentTraceDay;

    private static int MAX_TRACE_TIME = Simulation.TRACE_TIME + 1;

    public Trace(Point startPosition) {
        super(startPosition);
        resetTrace();
    }

    public void updateTrace() {
        currentTraceDay++;
        if (currentTraceDay > MAX_TRACE_TIME)
            tracePower = 0.0;
        else tracePower = 100.0 / MAX_TRACE_TIME * (MAX_TRACE_TIME - currentTraceDay);
    }

    public void resetTrace() {
        currentTraceDay = 0;
        tracePower = 100.0;
    }

    public static void setMaxTraceTime(int maxTraceTime) {
        MAX_TRACE_TIME = maxTraceTime + 1;
    }

    public int getColorNumber() {
        return currentTraceDay + 1;
    }

    public double getTracePower() {
        return tracePower;
    }
}
