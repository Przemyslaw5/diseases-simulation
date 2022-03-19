package com.agh.miss.mapElements;

import com.agh.miss.parametersObject.Point;

public abstract class AbstractMapElement implements IMapElement{

    private Point actualPosition;

    public AbstractMapElement(Point startPosition){
        actualPosition = startPosition;
    }

    @Override
    public Point getPosition() {
        return actualPosition;
    }

    public void setActualPosition(Point actualPosition) {
        this.actualPosition = actualPosition;
    }
}