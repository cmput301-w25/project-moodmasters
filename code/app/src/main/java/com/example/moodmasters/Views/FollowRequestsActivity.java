package com.example.moodmasters.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
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

    public void update(MVCModel model){
        // not necessary, nothing to update
    }

    public void initialize(MVCModel model){
        Participant currentUser = (Participant) model.getBackendObject(BackendObject.State.USER);
        currentUsername = currentUser.getUsername();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);
        controller.addBackendView(this, BackendObject.State.USER);

        requestsListView = findViewById(R.id.follow_requests_list);
        db = FirebaseFirestore.getInstance();

        followRequestsList = new ArrayList<>();
        adapter = new FollowRequestsAdapter(this, followRequestsList, db);
        requestsListView.setAdapter(adapter);

        loadFollowRequests();

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            controller.removeBackendView(this); // sufficient for now
            finish();
        });
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