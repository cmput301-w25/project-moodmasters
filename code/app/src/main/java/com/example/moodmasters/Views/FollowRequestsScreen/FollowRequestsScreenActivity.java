package com.example.moodmasters.Views.FollowRequestsScreen;

import android.os.Bundle;
import android.widget.ListView;

import com.example.moodmasters.Events.LoginSignupScreen.LoginSignupScreenLogOutEvent;
import com.example.moodmasters.Events.MoodEventMapScreen.MoodEventMapScreenShowEvent;
import com.example.moodmasters.Events.MoodFollowingListScreen.MoodFollowingListScreenShowEvent;
import com.example.moodmasters.Events.MoodHistoryListScreen.MoodHistoryListScreenShowEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Objects.ObjectsBackend.FollowRequestList;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
/**
 * Activity that is responsible for giving a UI for viewing the follow requests a user recieves
 * */
public class FollowRequestsScreenActivity extends AppCompatActivity implements MVCView {
    private List<String> follow_requests_list;
    private FollowRequestsScreenAdapter adapter;
    private boolean is_nav_setup = false;

    public FollowRequestsScreenActivity() {
        super();
        controller.addBackendView(this, BackendObject.State.FOLLOWREQUESTLIST);
    }

    @Override
    public void initialize(MVCModel model) {
        FollowRequestList following_request_list = (FollowRequestList) model.getBackendObject(BackendObject.State.FOLLOWREQUESTLIST);
        follow_requests_list = following_request_list.getFollowRequestsString();
    }

    @Override
    public void update(MVCModel model) {
        // not needed for now
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);

        BottomNavigationView nav = findViewById(R.id.bottom_navigation_view);
        setupBottomNav(nav, R.id.options_follow_requests_button);

        ListView requests_list_view = findViewById(R.id.follow_requests_list);

        adapter = new FollowRequestsScreenAdapter(this, follow_requests_list);
        requests_list_view.setAdapter(adapter);
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
                controller.removeBackendObject(BackendObject.State.FOLLOWREQUESTLIST);
                controller.removeBackendView(this);
                controller.removeBackendView(adapter);
                controller.execute(new LoginSignupScreenLogOutEvent(), this);
                return true;
            }

            if (itemId == R.id.user_mood_history_show_map_button) {
                controller.removeBackendObject(BackendObject.State.FOLLOWREQUESTLIST);
                controller.removeBackendView(this);
                controller.removeBackendView(adapter);
                controller.execute(new MoodEventMapScreenShowEvent(), this);
                return true;
            }

            if (itemId == R.id.home_button) {
                controller.removeBackendObject(BackendObject.State.FOLLOWREQUESTLIST);
                controller.removeBackendView(this);
                controller.removeBackendView(adapter);
                controller.execute(new MoodHistoryListScreenShowEvent(), this);
                return true;
            }

            if (itemId == R.id.mood_following_list_button) {
                controller.removeBackendObject(BackendObject.State.FOLLOWREQUESTLIST);
                controller.removeBackendView(this);
                controller.removeBackendView(adapter);
                controller.execute(new MoodFollowingListScreenShowEvent(), this);
                return true;
            }

            return false;
        });

        bottomNav.setSelectedItemId(currentItemId); // Set active button *after* setting listener
        is_nav_setup = false;
    }
}