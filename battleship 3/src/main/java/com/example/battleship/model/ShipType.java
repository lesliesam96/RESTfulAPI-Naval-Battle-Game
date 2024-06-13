package com.example.battleship.model;

public enum ShipType {
    AIRCRAFT_CARRIER("A",4),
    CRUISER("C",3),
    DESTROYER("D",2),
    TORPEDO_BOAT("T",1);

    private final int size;

    public String getSymbol() {
        return symbol;
    }

    private final String symbol;

    ShipType(String symbol, int size) {
        this.symbol = symbol;
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
