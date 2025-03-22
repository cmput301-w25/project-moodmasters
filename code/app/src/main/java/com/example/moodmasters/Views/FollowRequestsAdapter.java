package com.example.moodmasters.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.R;
import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class FollowRequestsAdapter extends BaseAdapter{
    private Context context;
    private List<String> requests;
    private FirebaseFirestore db;
    private String currentUsername;

    public FollowRequestsAdapter(Context context, List<String> requests, FirebaseFirestore db) {
        this.context = context;
        this.requests = requests;
        this.db = db;

        // Retrieve the logged-in user's username correctly
        this.currentUsername = LoginScreenOkEvent.getUsername();

        if (this.currentUsername == null || this.currentUsername.isEmpty()) {
            Toast.makeText(context, "Error: Could not retrieve current user!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int position) {
        return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_request_item, parent, false);
        }

        TextView usernameText = convertView.findViewById(R.id.request_username);
        Button acceptButton = convertView.findViewById(R.id.accept_button);
        Button ignoreButton = convertView.findViewById(R.id.ignore_button);

        String requesterUsername = requests.get(position);

        usernameText.setText(requesterUsername);

        acceptButton.setOnClickListener(v -> acceptRequest(requesterUsername, position));
        ignoreButton.setOnClickListener(v -> ignoreRequest(requesterUsername, position));

        return convertView;
    }

    private void acceptRequest(String requesterUsername, int position) {
        if (currentUsername == null || currentUsername.isEmpty()) {
            Toast.makeText(context, "Error: Current user not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requesterUsername == null || requesterUsername.isEmpty()) {
            Toast.makeText(context, "Error: Invalid requester!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the requester to the current user's "followers" list
        db.collection("participants").document(currentUsername)
                .collection("followers").document(requesterUsername)
                .set(new HashMap<>()) // Ensures Firestore initializes the collection

                .addOnSuccessListener(aVoid -> {
                    // Add the current user to the requester's "following" list
                    db.collection("participants").document(requesterUsername)
                            .collection("following").document(currentUsername)
                            .set(new HashMap<>()) // Ensures Firestore initializes the collection

                            .addOnSuccessListener(aVoid2 -> {
                                // Remove from follow requests after both operations succeed
                                db.collection("participants").document(currentUsername)
                                        .collection("followRequests").document(requesterUsername)
                                        .delete()
                                        .addOnSuccessListener(aVoid3 -> {
                                            requests.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Follow request accepted!", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(context, "Error removing request", Toast.LENGTH_SHORT).show());
                            })
                            .addOnFailureListener(e -> Toast.makeText(context, "Error adding following", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Error adding follower", Toast.LENGTH_SHORT).show());
    }

    private void ignoreRequest(String requesterUsername, int position) {
        // Just remove the request
        db.collection("participants").document(currentUsername)
                .collection("followRequests").document(requesterUsername)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    requests.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Follow request ignored!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Error removing request", Toast.LENGTH_SHORT).show());
    }
}