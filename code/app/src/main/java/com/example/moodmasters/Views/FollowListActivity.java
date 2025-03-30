package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FollowListActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseFirestore db;
    private FollowListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_list_screen);  // Make sure this layout exists

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Get intent extras
        String username = getIntent().getStringExtra("username");

        // Initialize Firebase and ListView
        db = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.follow_list_view);
        adapter = new FollowListAdapter(this, new ArrayList<>(), username);
        listView.setAdapter(adapter);



        if (username == null) {
            Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch the followers or following list from Firestore
        db.collection("participants").document(username).collection("following")
                .get().addOnSuccessListener(snapshot -> {
                    List<String> users = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        users.add(doc.getId()); // Each document ID is a user
                    }
                    adapter.clear();
                    adapter.addAll(users);
                    adapter.notifyDataSetChanged();
                }).addOnFailureListener(e -> {
                    Toast.makeText(FollowListActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });
    }
}