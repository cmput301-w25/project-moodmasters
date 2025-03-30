package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.LogOutEvent;
import com.example.moodmasters.Events.MoodHistoryListShowFilterEvent;
import com.example.moodmasters.Events.MoodHistoryListAddEvent;
import com.example.moodmasters.Events.ShowFollowRequestsEvent;
import com.example.moodmasters.Events.ShowMapEvent;
import com.example.moodmasters.Events.ShowMoodFollowingEvent;
import com.example.moodmasters.Events.ShowProfileStatisticsEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MoodHistoryListActivity extends AppCompatActivity implements MVCView {
    private MoodHistoryListView mood_history_view;
    private String username;
    private List<MoodEvent> mood_list;
    private boolean is_nav_setup = false;

    public MoodHistoryListActivity(){
        super();
        controller.addBackendView(this, BackendObject.State.USER);
    }
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));
        username = user.getUsername();
        mood_list = user.getMoodHistoryList().getList();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_mood_history_screen);
        TextView username_view = findViewById(R.id.user_mood_history_label);
        username_view.setText(username);
        controller.createBackendObject(BackendObject.State.MOODHISTORYLIST);

        mood_history_view = new MoodHistoryListView(this);

        BottomNavigationView nav = findViewById(R.id.bottom_navigation_view);
        setupBottomNav(nav, R.id.home_button);

        ImageButton add_button = findViewById(R.id.user_mood_history_add_button);
        add_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListAddEvent(), this);
        });

        ImageButton sort_button = findViewById(R.id.user_mood_following_sort_button);
        sort_button.setOnClickListener(v -> {
            mood_history_view.toggleSort();
        });

        ImageButton filter_button = findViewById(R.id.user_mood_following_filter_button);
        filter_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListShowFilterEvent(), this);
        });

        ImageButton stats_button = findViewById(R.id.user_mood_history_stats_button);
        stats_button.setOnClickListener(v -> {
            controller.execute(new ShowProfileStatisticsEvent(mood_list, username), this);
        });

        mood_history_view.setListElementClicker();
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
                controller.execute(new LogOutEvent(), this);
                return true;
            }

            if (itemId == R.id.user_mood_history_show_map_button) {
                controller.execute(new ShowMapEvent(), this);
                return true;
            }

            if (itemId == R.id.home_button) {
                startActivity(new Intent(this, MoodHistoryListActivity.class));
                return true;
            }

            if (itemId == R.id.options_follow_requests_button) {
                controller.execute(new ShowFollowRequestsEvent(), this);
                return true;
            }

            if (itemId == R.id.mood_following_list_button) {
                controller.execute(new ShowMoodFollowingEvent(), this);
                return true;
            }

            return false;
        });

        bottomNav.setSelectedItemId(currentItemId); // Set active button *after* setting listener
        is_nav_setup = false;
    }

    public MoodHistoryListView getMoodHistoryListView(){
        return mood_history_view;
    }
}
