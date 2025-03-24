package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.ChangeActivityEvent;
import com.example.moodmasters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MoodFollowingListActivity extends ChangeActivityEvent {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_following_screen);
        BottomNavigationView nav = findViewById(R.id.bottom_navigation_view);
        setupBottomNav(nav, R.id.mood_following_list_button);

        ImageButton userSearch = findViewById(R.id.user_search_button);
        userSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserSearchActivity.class);
            startActivity(intent);
        });
    }
}
