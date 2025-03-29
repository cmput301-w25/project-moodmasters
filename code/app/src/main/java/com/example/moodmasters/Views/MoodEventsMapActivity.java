package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.ChangeActivityEvent;
import com.example.moodmasters.Events.LogOutEvent;
import com.example.moodmasters.Events.MoodEventMapShowFilterEvent;
import com.example.moodmasters.Events.ShowMapEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsMisc.MoodMap;
import com.example.moodmasters.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class MoodEventsMapActivity extends AppCompatActivity implements OnMapReadyCallback, MVCView {
    private ArrayList<MoodEvent> mood_events;
    List<MoodEvent> mood_following_list;
    List<MoodEvent> mood_history_list;
    private GoogleMap google_map;
    private boolean is_nav_setup = false;
    public MoodEventsMapActivity() {
        super();
        mood_events = new ArrayList<MoodEvent>();
    }

    @Override
    public void update(MVCModel model) {
        MoodMap mood_map = (MoodMap) model.getBackendObject(BackendObject.State.MOODMAP);
        mood_map.clearMap();
        mood_map.displayMoodEvents(this);
    }

    @Override
    public void initialize(MVCModel model) {
        MoodMap mood_map = (MoodMap) model.getBackendObject(BackendObject.State.MOODMAP);
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mood_map.setGoogleMap(google_map);
        mood_map.displayMoodEvents(this);
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

        ImageButton filter_button = findViewById(R.id.map_screen_filter_button);
        filter_button.setOnClickListener(v -> {
            controller.execute(new MoodEventMapShowFilterEvent(), this);
        });

        map_view.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap google_map) {
        controller.createBackendObject(BackendObject.State.MOODMAP);
        this.google_map = google_map;
        controller.addBackendView(this, BackendObject.State.MOODMAP);
        /*
        TextPaint paint = new TextPaint();
        StaticLayout lsLayout = new StaticLayout("😐", paint, 80, Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        Bitmap bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        lsLayout.draw(canvas);
        canvas.save();
        canvas.restore();
         */
    }

    protected void setupBottomNav(BottomNavigationView bottomNav, int currentItemId) {
        is_nav_setup = true;
        bottomNav.setOnItemSelectedListener(item -> {
            if (is_nav_setup && item.getItemId() == currentItemId) {
                // Already on this screen, do nothing
                return true;
            }

            int itemId = item.getItemId();
            if (itemId == R.id.options_logout_button) {
                controller.removeBackendObject(BackendObject.State.MOODMAP);
                controller.removeBackendView(this);
                controller.execute(new LogOutEvent(), this);
                return true;
            }

            if (itemId == R.id.user_mood_history_show_map_button) {
                controller.execute(new ShowMapEvent(), this);
                return true;
            }

            if (itemId == R.id.home_button) {
                controller.removeBackendObject(BackendObject.State.MOODMAP);
                controller.removeBackendView(this);
                startActivity(new Intent(this, MoodHistoryListActivity.class));
                return true;
            }

            if (itemId == R.id.options_follow_requests_button) {
                controller.removeBackendObject(BackendObject.State.MOODMAP);
                controller.removeBackendView(this);
                startActivity(new Intent(this, FollowRequestsActivity.class));
                return true;
            }

            if (itemId == R.id.mood_following_list_button) {
                controller.removeBackendObject(BackendObject.State.MOODMAP);
                controller.removeBackendView(this);
                startActivity(new Intent(this, MoodFollowingListActivity.class));
                return true;
            }

            return false;
        });

        bottomNav.setSelectedItemId(currentItemId); // Set active button *after* setting listener
        is_nav_setup = false;
    }
}
