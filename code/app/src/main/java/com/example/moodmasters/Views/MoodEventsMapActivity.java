package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.ChangeActivityEvent;
import com.example.moodmasters.Events.MoodEventsMapBackEvent;
import com.example.moodmasters.Events.MoodHistoryScreenShowMapEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.MoodMap;
import com.example.moodmasters.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MoodEventsMapActivity extends ChangeActivityEvent implements OnMapReadyCallback, MVCView {
    private ArrayList<MoodEvent> mood_events;
    private MoodMap mood_map;

    public MoodEventsMapActivity() {
        super();
        mood_events = MoodHistoryScreenShowMapEvent.getMoodEvents();
    }

    @Override
    public void update(MVCModel model) {

    }

    @Override
    public void initialize(MVCModel model) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mood_events_map_screen);

        BottomNavigationView nav = findViewById(R.id.bottom_navigation_view);
        setupBottomNav(nav, R.id.user_mood_history_show_map_button);

        MapView map_view = findViewById(R.id.map_screen_map_view);
        map_view.onCreate(savedInstanceState);

        map_view.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mood_map = new MoodMap(googleMap, mood_events);
        mood_map.displayMoodEvents();
    }
}
