package com.example.battleship.dto;

public class StopGameResponseDto {
    private String message;

    public StopGameResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
