package com.example.moodmasters.Events;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.FollowRequestsActivity;
import com.example.moodmasters.Views.MoodEventsMapActivity;
import com.example.moodmasters.Views.MoodFollowingListActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.moodmasters.MVC.MVCView.controller;

/*
* Doesn't enable removing MVCViews from backend on activity switch but this is sufficient for now
* */
public class ChangeActivityEvent extends AppCompatActivity {
    private boolean is_nav_setup = false;

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
}