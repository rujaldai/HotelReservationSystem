package com.rujal.hotelreservationsystem;

enum RoomType {
    NONE(" -- Select Room Type -- ", 0.0F),
    DELUXE("Deluxe", 1000.0F),
    PREMIUM("Premium", 1500.0F),
    PRESIDENTIAL("Presidential", 2000.0F);

    private String displayText;
    private  Float cost;

    RoomType(String displayText, Float cost) {
        this.displayText = displayText;
        this.cost = cost;
    }

    public static RoomType valueOfLabel(String label) {
        for (RoomType roomType : values()) {
            if (roomType.getDisplayText().equals(label)) {
                return roomType;
            }
        }
        return null;
    }

    public String getDisplayText() {
        return displayText;
    }

    public Float getCost() {
        return cost;
    }
}
