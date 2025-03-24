package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moodmasters.Events.ChangeActivityEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FollowRequestsActivity extends ChangeActivityEvent implements MVCView {
    private ListView requestsListView;
    private FirebaseFirestore db;
    private List<String> followRequestsList;
    private FollowRequestsAdapter adapter;
    private String currentUsername;

    public FollowRequestsActivity() {
        super();
        controller.addBackendView(this, BackendObject.State.USER);
    }

    @Override
    public void initialize(MVCModel model) {
        Participant user = (Participant) model.getBackendObject(BackendObject.State.USER);
        if (user == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
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
        if (currentUsername == null) {
            Toast.makeText(this, "Username is missing", Toast.LENGTH_SHORT).show();
            return;
        }

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
}