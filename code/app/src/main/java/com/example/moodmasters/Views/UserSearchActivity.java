package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.ChangeActivityEvent;
import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.Events.UserSearchOkEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class UserSearchActivity extends AppCompatActivity {
    private EditText searchInput;
    private ListView searchResultsListView;


    private Button searchButton;
    private ImageButton backButton;
    private ArrayAdapter<String> adapter;
    private UserSearchOkEvent searchEvent;
    private Participant currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_search_screen);
        searchInput = findViewById(R.id.searchInput);
        searchResultsListView = findViewById(R.id.searchResultsListView);
        backButton = findViewById(R.id.backButton);
        searchButton = findViewById(R.id.searchButton);
        db = FirebaseFirestore.getInstance();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        searchResultsListView.setAdapter(adapter);

        // Retrieve the current participant's username
        currentUser = new Participant(LoginScreenOkEvent.getUsername());
        searchEvent = new UserSearchOkEvent(currentUser);
        backButton.setOnClickListener(v -> finish());
        // Handle Search Button
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString();
            searchEvent.executeSearch(this, query, searchResultsListView, adapter);
        });

        searchResultsListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedUser = (String) parent.getItemAtPosition(position);
            checkIfAlreadyFollowing(selectedUser);
        });
    }

    private void checkIfAlreadyFollowing(String targetUser) {
        String currentUsername = currentUser.getUsername();

        db.collection("participants")
                .document(currentUsername)
                .collection("following")
                .document(targetUser)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Show Toast: Already following
                        Toast.makeText(UserSearchActivity.this, "You already follow " + targetUser, Toast.LENGTH_SHORT).show();
                    } else {
                        navigateToProfile(targetUser);
                    }
                })
                .addOnFailureListener(e -> {
                    // If Firestore fails (collection missing), assume empty and allow navigation
                    navigateToProfile(targetUser);
                });
    }

    private void navigateToProfile(String targetUser) {
        Intent intent = new Intent(UserSearchActivity.this, ViewProfileActivity.class);
        intent.putExtra("selectedUser", targetUser);
        startActivity(intent);
    }
}