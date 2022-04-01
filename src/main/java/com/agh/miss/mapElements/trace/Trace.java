package com.agh.miss.mapElements.trace;

import com.agh.miss.Simulation;
import com.agh.miss.mapElements.AbstractMapElement;
import com.agh.miss.parametersObject.Point;

public class Trace extends AbstractMapElement {

    private double tracePower;
    private int tracePowerIndex;

    public Trace(Point startPosition) {
        super(startPosition);
        resetTrace();
    }

    public void updateTraceAfterDay() {
        tracePowerIndex++;
        if (tracePowerIndex >= Simulation.TRACE_POWERS.size())
            tracePower = 0.0;
        else tracePower = Simulation.TRACE_POWERS.get(tracePowerIndex);
    }

    public void resetTrace() {
        tracePowerIndex = 0;
        tracePower = Simulation.TRACE_POWERS.get(tracePowerIndex);
    }

    public double getTracePower() {
        return tracePower;
    }
}
