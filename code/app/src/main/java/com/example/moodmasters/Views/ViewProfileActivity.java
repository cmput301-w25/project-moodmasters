package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Objects.ObjectsApp.FollowRequest;
import com.example.moodmasters.R;
import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private Button followButton;
    private FirebaseFirestore db;
    private String targetUsername;
    private String currentUsername; // Dynamically retrieved

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        usernameTextView = findViewById(R.id.usernameTextView);
        followButton = findViewById(R.id.followButton);
        db = FirebaseFirestore.getInstance();

        // Retrieve the selected username from UserSearchActivity
        targetUsername = getIntent().getStringExtra("selectedUser");

        // Get the currently logged-in user's username
        currentUsername = LoginScreenOkEvent.getUsername();

        // Display username
        if (targetUsername != null) {
            usernameTextView.setText(targetUsername);
        }

        // Follow button logic
        followButton.setOnClickListener(v -> sendFollowRequest());

        // Back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void sendFollowRequest() {
        if (targetUsername == null || currentUsername == null || currentUsername.equals(targetUsername)) {
            Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to the target user's followRequests collection
        DocumentReference followRequestRef = db.collection("participants")
                .document(targetUsername)
                .collection("followRequests")
                .document(currentUsername);

        // Add the follow request, storing the requester's username
        followRequestRef.set(new FollowRequest(currentUsername))
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Follow request sent!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error sending request", Toast.LENGTH_SHORT).show());
    }
}
