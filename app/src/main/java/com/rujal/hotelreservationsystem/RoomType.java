package com.rujal.hotelreservationsystem;

import java.util.Arrays;

enum RoomType {
    NONE(" -- Select Room Type -- ", 0.0F),
    DELUXE("Deluxe", 2000.0F),
    PREMIUM("Premium", 4000.0F),
    PRESIDENTIAL("Presidential", 5000.0F);

    private String displayText;
    private  Float cost;

    RoomType(String displayText, Float cost) {
        this.displayText = displayText;
        this.cost = cost;
    }

    public static RoomType valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(roomType -> roomType.getDisplayText().equals(label))
                .findFirst()
                .orElse(null);
    }

    public String getDisplayText() {
        return displayText;
    }

    public Float getCost() {
        return cost;
    }
}
