package com.example.moodmasters.Views.MoodEventMapScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.moodmasters.Events.LoginSignupScreen.LoginSignupScreenLogOutEvent;
import com.example.moodmasters.Events.MoodEventMapScreen.MoodEventMapScreenShowFilterEvent;
import com.example.moodmasters.Events.FollowRequestsScreen.FollowRequestsScreenShowEvent;
import com.example.moodmasters.Events.MoodEventMapScreen.MoodEventMapScreenShowEvent;
import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenShowEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenShowEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.Objects.ObjectsBackend.MoodMap;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
/**
 * Activity that is responsible for giving a UI for viewing a map showing the mood events of the user and
 * participants the user is following
 * */
public class MoodEventMapScreenActivity extends AppCompatActivity implements OnMapReadyCallback, MVCView {
    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap google_map;
    private Location location;
    private boolean is_nav_setup = false;

    @Override
    public void update(MVCModel model) {
        MoodMap mood_map = (MoodMap) model.getBackendObject(BackendObject.State.MOODMAP);
        mood_map.clearMap();
        mood_map.displayMoodEvents(this);
    }

    @Override
    public void initialize(MVCModel model) {
        MoodMap mood_map = (MoodMap) model.getBackendObject(BackendObject.State.MOODMAP);
        mood_map.setGoogleMap(google_map);
        if (location != null){
            mood_map.setUserLocation(location);
            mood_map.filterCreateLocationRecencyList();
        }
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
        map_view.getMapAsync(MoodEventMapScreenActivity.this);

        ImageButton filter_button = findViewById(R.id.map_screen_filter_button);
        filter_button.setOnClickListener(v -> {
            if (location == null){
                controller.execute(new MoodEventMapScreenShowFilterEvent(false), this);
            }
            else {
                controller.execute(new MoodEventMapScreenShowFilterEvent(true), this);
            }
        });
        getLastLocation();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap google_map) {
        controller.createBackendObject(BackendObject.State.MOODMAP);
        this.google_map = google_map;
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
    private void getLastLocation(){
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>(){
            @Override
            public void onSuccess(Location location){
                MoodEventMapScreenActivity.this.location = location;
                controller.addBackendView(MoodEventMapScreenActivity.this, BackendObject.State.MOODMAP);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int request_code, @NonNull String[] permissions, @NonNull int[] granted_results){
        super.onRequestPermissionsResult(request_code, permissions, granted_results);
        if (request_code == FINE_PERMISSION_CODE){
            if (granted_results.length > 0 && granted_results[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else{
                Toast.makeText(this, "Location Permissions Denied, Your Location and Location Filtering will be turned off", Toast.LENGTH_SHORT).show();
                location = null;
                controller.addBackendView(MoodEventMapScreenActivity.this, BackendObject.State.MOODMAP);

            }
        }

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
                controller.execute(new LoginSignupScreenLogOutEvent(), this);
                return true;
            }

            if (itemId == R.id.home_button) {
                controller.execute(new MoodHistoryListScreenShowEvent(), this);
                return true;
            }

            if (itemId == R.id.options_follow_requests_button) {
                controller.execute(new FollowRequestsScreenShowEvent(), this);
                return true;
            }

            if (itemId == R.id.mood_following_list_button) {
                controller.execute(new MoodFollowingListScreenShowEvent(), this);
                return true;
            }

            return false;
        });

        bottomNav.setSelectedItemId(currentItemId); // Set active button *after* setting listener
        is_nav_setup = false;
    }
}
