package com.example.moodmasters.Objects.ObjectsMisc;

import androidx.annotation.NonNull;

import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

// Not needed for halfway checkpoint, these can also go in ObjectsApp or ObjectsBackend not sure yet
public class MoodMap {
    private GoogleMap google_map;
    private ArrayList<MoodEvent> mood_events;

    public MoodMap(@NonNull GoogleMap init_map, ArrayList<MoodEvent> init_events) {
        google_map = init_map;
        mood_events = init_events;
    }

    public void clearMap() {
        google_map.clear();
    }

    public void displayMoodEvents() {
        for (int i = 0; i < mood_events.size(); i++) {
            MoodEvent event = mood_events.get(i);
            google_map.addMarker(new MarkerOptions()
                    .position(event.getLocation())
                    .title(event.getUsername()));
        }
    }

    public GoogleMap getGoogle_map() {
        return google_map;
    }

    public void setGoogle_map(GoogleMap google_map) {
        this.google_map = google_map;
    }

    public ArrayList<MoodEvent> getMood_events() {
        return mood_events;
    }

    public void setMood_events(ArrayList<MoodEvent> mood_events) {
        this.mood_events = mood_events;
    }
}
