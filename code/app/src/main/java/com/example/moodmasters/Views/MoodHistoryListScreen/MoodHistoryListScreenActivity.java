package com.example.moodmasters.Views.MoodHistoryListScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.LoginSignupScreen.LoginSignupScreenLogOutEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenFollowersEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenFollowingEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenShowEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenShowFilterEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenAddEvent;
import com.example.moodmasters.Events.FollowRequestsScreen.FollowRequestsScreenShowEvent;
import com.example.moodmasters.Events.MoodEventMapScreen.MoodEventMapScreenShowEvent;
import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenShowEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenStatisticsEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Counters;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.FollowersListScreen.FollowersListScreenActivity;
import com.example.moodmasters.Views.FollowingListScreen.FollowingListScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoodHistoryListScreenActivity extends AppCompatActivity implements MVCView {
    private MoodHistoryListScreenListView mood_history_view;
    private String username;
    private String followers;
    private String following;
    private List<MoodEvent> mood_list;
    private boolean is_nav_setup = false;

    public MoodHistoryListScreenActivity(){
        super();
        List<BackendObject.State> dependencies = new ArrayList<BackendObject.State>(Arrays.asList(new BackendObject.State[] {
                                                    BackendObject.State.USER, BackendObject.State.COUNTERS}));
        controller.addBackendView(this, dependencies);
    }
    public void update(MVCModel model){
        Counters counters = (Counters) model.getBackendObject(BackendObject.State.COUNTERS);
        followers = counters.getFollowers();
        following = counters.getFollowing();
        TextView followers_number = findViewById(R.id.followers_number);
        TextView following_number = findViewById(R.id.following_number);
        followers_number.setText(followers);
        following_number.setText(following);
    }
    public void initialize(MVCModel model){
        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));
        Counters counters = (Counters) model.getBackendObject(BackendObject.State.COUNTERS);
        followers = counters.getFollowers();
        following = counters.getFollowing();
        username = user.getUsername();
        mood_list = user.getMoodHistoryList().getList();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_mood_history_screen);
        TextView username_view = findViewById(R.id.user_mood_history_label);
        LinearLayout followers_container = findViewById(R.id.followers_container);
        LinearLayout following_container = findViewById(R.id.following_container);

        TextView followers_number = findViewById(R.id.followers_number);
        TextView following_number = findViewById(R.id.following_number);

        followers_number.setText(followers);
        following_number.setText(following);
        username_view.setText(username);
        
        controller.createBackendObject(BackendObject.State.MOODHISTORYLIST);

        mood_history_view = new MoodHistoryListScreenListView(this);

        BottomNavigationView nav = findViewById(R.id.bottom_navigation_view);
        setupBottomNav(nav, R.id.home_button);

        ImageButton add_button = findViewById(R.id.user_mood_history_add_button);
        add_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListScreenAddEvent(), this);
        });

        ImageButton sort_button = findViewById(R.id.user_mood_following_sort_button);
        sort_button.setOnClickListener(v -> {
            mood_history_view.toggleSort();
        });

        ImageButton filter_button = findViewById(R.id.user_mood_following_filter_button);
        filter_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListScreenShowFilterEvent(), this);
        });

        ImageButton stats_button = findViewById(R.id.user_mood_history_stats_button);
        stats_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListScreenStatisticsEvent(mood_list, username), this);
        });

        followers_container.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListScreenFollowersEvent(), this);
            //openFollowList("followers", username);
        });
        following_container.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListScreenFollowingEvent(), this);
            //openFollowList("following", username);
        });

        mood_history_view.setListElementClicker();
    }
    private void openFollowList(String listType, String username) {
        Intent intent;

        if (listType.equals("followers")) {
            intent = new Intent(this, FollowersListScreenActivity.class);
        }
        else {
            intent = new Intent(this, FollowingListScreenActivity.class);
        }

        intent.putExtra("username", username);
        startActivity(intent);
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
