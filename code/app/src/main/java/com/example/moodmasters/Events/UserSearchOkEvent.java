package com.example.moodmasters.Events;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Locale;
/**
 * A class that will search firebase participants based on username
 * */

public class UserSearchOkEvent {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("participants"); // Firestore Collection
    private String currentUsername; // Store logged-in participant's username

    public UserSearchOkEvent(Participant user) {
        this.currentUsername = user.getUsername().trim(); // Keep exact username from Firestore
    }

    public void executeSearch(Activity activity, String inputQuery, ListView listView, ArrayAdapter<String> adapter) {
        if (inputQuery.trim().isEmpty()) {
            Toast.makeText(activity, "Enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        final String searchQuery = inputQuery.trim().toLowerCase(Locale.ROOT); // Case-insensitive search

        usersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<String> searchResults = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    String firestoreUsername = document.getId().trim(); // Get username from Firestore document ID

                    // If username starts with the search query, add it
                    if (firestoreUsername.toLowerCase(Locale.ROOT).startsWith(searchQuery)) {
                        searchResults.add(firestoreUsername);
                    }
                }

                // Remove the current user from the results
                searchResults.remove(currentUsername);

                adapter.clear();
                adapter.addAll(searchResults);
                adapter.notifyDataSetChanged();

                if (searchResults.isEmpty()) {
                    Toast.makeText(activity, "No matching users found.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(activity, "Error searching users.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}