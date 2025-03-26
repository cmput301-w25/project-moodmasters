package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moodmasters.Events.ChangeActivityEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Events.LoginScreenOkEvent;
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
        controller.addBackendView(this, BackendObject.State.USER);

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
}