package com.example.battleship.model;

import java.util.Objects;

public class Coordinate {
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private final int row;
    private final int col;

    public Coordinate()
    {
        this.row = 0;
        this.col = 0;
    }

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Coordinate fromFormattedCoordinate(String formattedCoordinate) {
        int col = formattedCoordinate.charAt(0) - 'A';
        int row = Integer.parseInt(formattedCoordinate.substring(1)) - 1;
        return new Coordinate(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return Boolean.TRUE;
        if (o == null || getClass() != o.getClass()) return Boolean.FALSE;
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
