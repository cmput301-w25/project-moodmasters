package com.example.moodmasters.Views;

import android.content.Intent;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.Events.ChangeActivityEvent;
import com.example.moodmasters.Events.LogOutEvent;
import com.example.moodmasters.Events.ShowMapEvent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.moodmasters.Events.MoodFollowingListRefreshEvent;
import com.example.moodmasters.Events.MoodFollowingListShowFilterEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Events.MoodFollowingListBackEvent;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.ArrayList;

public class MoodFollowingListActivity extends AppCompatActivity implements MVCView {
    private MoodFollowingListView mood_following_view;
    private String username;
    private boolean is_nav_setup = false;
    public MoodFollowingListActivity(){
        super();
        controller.addBackendView(this, BackendObject.State.USER);
    }
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));
        username = user.getUsername();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_mood_following_screen);
        BottomNavigationView nav = findViewById(R.id.bottom_navigation_view);
        setupBottomNav(nav, R.id.mood_following_list_button);

        ImageButton userSearch = findViewById(R.id.user_search_button);
        userSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserSearchActivity.class);
            startActivity(intent);
        });

        TextView username_view = findViewById(R.id.user_mood_following_label);
        username_view.setText(username);

        if (!controller.existsBackendObject(BackendObject.State.MOODFOLLOWINGLIST)){
            controller.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        }
        mood_following_view = new MoodFollowingListView(this);

        ImageButton sort_button = findViewById(R.id.user_mood_following_sort_button);
        sort_button.setOnClickListener(v -> {
            mood_following_view.toggleSort();
        });

        ImageButton filter_button = findViewById(R.id.user_mood_following_filter_button);
        filter_button.setOnClickListener(v -> {
            controller.execute(new MoodFollowingListShowFilterEvent(), this);
        });

        SwipeRefreshLayout swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(() -> {
            controller.execute(new MoodFollowingListRefreshEvent(), this);
        });
        mood_following_view.setListElementClicker();

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
                startActivity(new Intent(this, FollowRequestsActivity.class));
                return true;
            }

            if (itemId == R.id.mood_following_list_button) {
                startActivity(new Intent(this, MoodFollowingListActivity.class));
                return true;
            }

            return false;
        });

        bottomNav.setSelectedItemId(currentItemId); // Set active button *after* setting listener
        is_nav_setup = false;
    }

    public MoodFollowingListView getMoodFollowingListView(){
        return mood_following_view;
    }
}
