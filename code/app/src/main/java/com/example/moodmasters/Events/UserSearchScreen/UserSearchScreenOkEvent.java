package com.example.moodmasters.Events.UserSearchScreen;

import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * A class that will search firebase participants based on username
 * */

public class UserSearchScreenOkEvent implements MVCController.MVCEvent{

    private CollectionReference users_ref; // Firestore Collection
    private String current_username; // Store logged-in participant's username
    private String inputQuery;
    private ArrayAdapter<String> adapter;

    public UserSearchScreenOkEvent(Participant user, String inputQuery, ArrayAdapter<String> adapter) {
        this.current_username = user.getUsername().trim(); // Keep exact username from Firestore
        this.inputQuery = inputQuery;
        this.adapter = adapter;
    }
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        Activity activity = (Activity) context;
        users_ref = model.getDatabase().getCollection();
        ConnectivityManager connectivityManager = activity.getSystemService(ConnectivityManager.class);
        Network currentNetwork = connectivityManager.getActiveNetwork();
        if (currentNetwork == null || !Objects.requireNonNull(connectivityManager.getNetworkCapabilities(currentNetwork)).hasCapability(NET_CAPABILITY_VALIDATED)) {
            Toast.makeText(activity.getBaseContext(), "You're offline! Please connect to the internet", Toast.LENGTH_SHORT).show();
            return;
        }

        if (inputQuery.trim().isEmpty()) {
            Toast.makeText(activity, "Enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        final String searchQuery = inputQuery.trim().toLowerCase(Locale.ROOT); // Case-insensitive search

        users_ref.get().addOnCompleteListener(task -> {
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
                searchResults.remove(current_username);

                adapter.clear();
                adapter.addAll(searchResults);
                adapter.notifyDataSetChanged();

                if (searchResults.isEmpty()) {
                    Toast.makeText(activity, "No matching users found.", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(activity, "Error searching users.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}