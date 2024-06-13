package com.example.battleship.dto;

public class FireResponseDto {
    private String consequence;
    private boolean shipLeft;

    public FireResponseDto(String consequence, boolean shipLeft) {
        this.consequence = consequence;
        this.shipLeft = shipLeft;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public boolean isShipLeft() {
        return shipLeft;
    }

    public void setShipLeft(boolean shipLeft) {
        this.shipLeft = shipLeft;
    }
}
