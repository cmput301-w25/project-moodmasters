package com.example.moodmasters.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.FollowRequest;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewProfileActivity extends AppCompatActivity implements MVCView {
    private TextView usernameTextView;
    private Button followButton;
    private FirebaseFirestore db;
    private String targetUsername;
    private String currentUsername;
    private ListView moodListView;
    private ViewProfileAdapter adapter;

    @Override
    public void update(MVCModel model) {
        // Updates can implement changes based on model notifications
    }

    @Override
    public void initialize(MVCModel model) {
        // Initialize current user details
        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));
        currentUsername = user.getUsername();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        usernameTextView = findViewById(R.id.usernameTextView);
        followButton = findViewById(R.id.followButton);
        moodListView = findViewById(R.id.mood_list_view);
        db = FirebaseFirestore.getInstance();
        targetUsername = getIntent().getStringExtra("selectedUser");
        currentUsername = LoginScreenOkEvent.getUsername();


        adapter = new ViewProfileAdapter(this);
        moodListView.setAdapter(adapter);

        if (targetUsername != null) {
            usernameTextView.setText(targetUsername);
            adapter.fetchMoodEvents(targetUsername);
            checkFollowState();
        }

        SwipeRefreshLayout swipeContainer = findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(() -> {
            if (targetUsername != null) {
                adapter.fetchMoodEvents(targetUsername);
            }
        });

        followButton.setOnClickListener(v -> sendFollowRequest());
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void sendFollowRequest() {
        String currentText = followButton.getText().toString();
        if (targetUsername == null || currentUsername == null || currentUsername.equals(targetUsername)) {
            Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentText.equals("Requested")) {
            Toast.makeText(this, "Follow request already sent", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentText.equals("Following")) {
            Toast.makeText(this, "Already following", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference followRequestRef = db.collection("participants")
                .document(targetUsername)
                .collection("followRequests")
                .document(currentUsername);

        followRequestRef.set(new FollowRequest(currentUsername))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Follow request sent!", Toast.LENGTH_SHORT).show();
                    followButton.setText("Requested");
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error sending request", Toast.LENGTH_SHORT).show());
    }
    private void checkFollowState() {
        if (currentUsername.equals(targetUsername)) {
            followButton.setVisibility(View.GONE);
            return;
        }

        // 1. Check if current user is following the target
        db.collection("participants")
                .document(currentUsername)
                .collection("following")
                .document(targetUsername)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        followButton.setText("Following");
                    } else {
                        // 2. If not following, check if follow request was already sent
                        db.collection("participants")
                                .document(targetUsername)
                                .collection("followRequests")
                                .document(currentUsername)
                                .get()
                                .addOnSuccessListener(requestSnap -> {
                                    if (requestSnap.exists()) {
                                        followButton.setText("Requested");
                                    } else {
                                        followButton.setText("Follow");
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error checking follow status", Toast.LENGTH_SHORT).show();
                });
    }


}