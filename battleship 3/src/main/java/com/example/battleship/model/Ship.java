package com.example.battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private final ShipType type;
    private final int size;
    private boolean sunk;
    private List<Coordinate> coordinates;

    public Ship(ShipType type) {
        this.type = type;
        this.size = type.getSize();
        this.coordinates = new ArrayList<>(type.getSize());
    }

    public ShipType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
