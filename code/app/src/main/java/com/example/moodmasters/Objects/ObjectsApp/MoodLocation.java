package com.example.moodmasters.Objects.ObjectsApp;

import com.google.type.LatLng;

public class MoodLocation {
    private LatLng location;

    public MoodLocation(LatLng location) {
        this.location = location;
    }

    // Getter methods
    public LatLng getLocation() {
        return location;
    }

    // Setter methods
    public void setLocation(LatLng new_location) {
        location = new_location;
    }
}
