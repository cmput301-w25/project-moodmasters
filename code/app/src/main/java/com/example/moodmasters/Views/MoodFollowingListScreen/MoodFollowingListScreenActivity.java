package com.example.moodmasters.Views.MoodFollowingListScreen;

import android.content.Intent;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.Events.LoginSignupScreen.LoginSignupScreenLogOutEvent;
import com.example.moodmasters.Events.FollowRequestsScreen.FollowRequestsScreenShowEvent;
import com.example.moodmasters.Events.MoodEventMapScreen.MoodEventMapScreenShowEvent;
import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenShowEvent;

import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenUserSearchEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenShowEvent;
import com.example.moodmasters.Views.MoodHistoryListScreen.MoodHistoryListScreenActivity;
import com.example.moodmasters.Views.UserSearchScreen.UserSearchScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenRefreshEvent;
import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenShowFilterEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
/**
 * Activity that is responsible for giving a UI for viewing the mood events for the participants a user
 * is following
 * */
public class MoodFollowingListScreenActivity extends AppCompatActivity implements MVCView {
    private MoodFollowingListScreenListView mood_following_view;
    private String username;
    private boolean is_nav_setup = false;
    public MoodFollowingListScreenActivity(){
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

        ImageButton user_search = findViewById(R.id.user_search_button);
        user_search.setOnClickListener(v -> {
            controller.execute(new MoodFollowingListScreenUserSearchEvent(), this);
        });

        TextView username_view = findViewById(R.id.user_mood_following_label);
        username_view.setText(username);

        if (!controller.existsBackendObject(BackendObject.State.MOODFOLLOWINGLIST)){
            controller.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        }
        mood_following_view = new MoodFollowingListScreenListView(this);

        ImageButton sort_button = findViewById(R.id.user_mood_following_sort_button);
        sort_button.setOnClickListener(v -> {
            mood_following_view.toggleSort();
        });

        ImageButton filter_button = findViewById(R.id.user_mood_following_filter_button);
        filter_button.setOnClickListener(v -> {
            controller.execute(new MoodFollowingListScreenShowFilterEvent(), this);
        });

        SwipeRefreshLayout swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(() -> {
            controller.execute(new MoodFollowingListScreenRefreshEvent(), this);
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
                controller.execute(new LoginSignupScreenLogOutEvent(), this);
                return true;
            }

            if (itemId == R.id.user_mood_history_show_map_button) {
                controller.execute(new MoodEventMapScreenShowEvent(), this);
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

            return false;
        });

        bottomNav.setSelectedItemId(currentItemId); // Set active button *after* setting listener
        is_nav_setup = false;
    }

    public MoodFollowingListScreenListView getMoodFollowingListView(){
        return mood_following_view;
    }


}
