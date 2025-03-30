package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.moodmasters.Events.LogOutEvent;
import com.example.moodmasters.Events.ShowFollowRequestsEvent;
import com.example.moodmasters.Events.ShowMapEvent;
import com.example.moodmasters.Events.ShowMoodFollowingEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FollowRequestsActivity extends AppCompatActivity implements MVCView {
    private ListView requestsListView;
    private FirebaseFirestore db;
    private List<String> followRequestsList;
    private FollowRequestsAdapter adapter;
    private String currentUsername;
    private boolean is_nav_setup = false;

    public FollowRequestsActivity() {
        super();
        controller.addBackendView(this, BackendObject.State.USER);
    }

    @Override
    public void initialize(MVCModel model) {
        Participant user = (Participant) model.getBackendObject(BackendObject.State.USER);
        currentUsername = user.getUsername();
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

        requestsListView = findViewById(R.id.follow_requests_list);
        db = FirebaseFirestore.getInstance();

        followRequestsList = new ArrayList<>();
        adapter = new FollowRequestsAdapter(this, followRequestsList, db);
        requestsListView.setAdapter(adapter);

        loadFollowRequests();
    }

    private void loadFollowRequests() {
        db.collection("participants")
                .document(currentUsername)
                .collection("followRequests")
                .get()
                .addOnCompleteListener(task -> {
                    followRequestsList.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            followRequestsList.add(document.getId());
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
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
}