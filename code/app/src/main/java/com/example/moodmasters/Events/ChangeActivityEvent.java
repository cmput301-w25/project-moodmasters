package com.example.moodmasters.Events;

import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.LogOutEvent;
import com.example.moodmasters.Events.MoodHistoryScreenShowMapEvent;
import com.example.moodmasters.Events.UserSearchEvent;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.FollowRequestsActivity;
import com.example.moodmasters.Views.MoodEventsMapActivity;
import com.example.moodmasters.Views.MoodFollowingListActivity;
import com.example.moodmasters.Views.MoodHistoryListActivity;
import com.example.moodmasters.Views.UserSearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.moodmasters.MVC.MVCView.controller;

/*
* Doesn't enable removing MVCViews from backend on activity switch but this is sufficient for now
* */
public class ChangeActivityEvent extends AppCompatActivity {
    private ArrayList<MoodEvent> moodEventList;

    public void setMoodEventList(ArrayList<MoodEvent> moodEventList) {
        this.moodEventList = moodEventList;
    }

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
                ConnectivityManager connectivityManager = this.getSystemService(ConnectivityManager.class);
                Network currentNetwork = connectivityManager.getActiveNetwork();
                if (currentNetwork == null || !Objects.requireNonNull(connectivityManager.getNetworkCapabilities(currentNetwork)).hasCapability(NET_CAPABILITY_VALIDATED)) {
                    Toast.makeText(this, "You're offline! Please connect to the internet", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (moodEventList != null) {
                    controller.execute(new MoodHistoryScreenShowMapEvent(moodEventList), this);
                } else {
                    controller.execute(new MoodHistoryScreenShowMapEvent(new ArrayList<>()), this);
                }
                return true;
            }

            if (itemId == R.id.home_button) {
                startActivity(new Intent(this, MoodHistoryListActivity.class));
                return true;
            }

            if (itemId == R.id.options_follow_requests_button) {
                ConnectivityManager connectivityManager = this.getSystemService(ConnectivityManager.class);
                Network currentNetwork = connectivityManager.getActiveNetwork();
                if (currentNetwork == null || !Objects.requireNonNull(connectivityManager.getNetworkCapabilities(currentNetwork)).hasCapability(NET_CAPABILITY_VALIDATED)) {
                    Toast.makeText(this, "You're offline! Please connect to the internet", Toast.LENGTH_SHORT).show();
                    return false;
                }
                startActivity(new Intent(this, FollowRequestsActivity.class));
                return true;
            }

            if (itemId == R.id.mood_following_list_button) {
                ConnectivityManager connectivityManager = this.getSystemService(ConnectivityManager.class);
                Network currentNetwork = connectivityManager.getActiveNetwork();
                if (currentNetwork == null || !Objects.requireNonNull(connectivityManager.getNetworkCapabilities(currentNetwork)).hasCapability(NET_CAPABILITY_VALIDATED)) {
                    Toast.makeText(this, "You're offline! Please connect to the internet", Toast.LENGTH_SHORT).show();
                    return false;
                }
                startActivity(new Intent(this, MoodFollowingListActivity.class));
                return true;
            }

            return false;
        });

        bottomNav.setSelectedItemId(currentItemId); // Set active button *after* setting listener
        is_nav_setup = false;
    }
}