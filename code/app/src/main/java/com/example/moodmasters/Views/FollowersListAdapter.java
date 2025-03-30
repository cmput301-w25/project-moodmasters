package com.example.moodmasters.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FollowersListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String currentUsername;

    public FollowersListAdapter(Context context, List<String> followers, String currentUsername) {
        super(context, 0, followers);
        this.context = context;
        this.currentUsername = currentUsername;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String follower = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.followers_list_item, parent, false);
        }

        TextView usernameText = convertView.findViewById(R.id.follower_username);
        Button removeButton = convertView.findViewById(R.id.remove_button);

        usernameText.setText(follower);

        removeButton.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // 1. Remove mona from lisa's followers
            db.collection("participants")
                    .document(currentUsername) // lisa
                    .collection("followers")
                    .document(follower)        // mona
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // 2. Remove lisa from mona's following
                        db.collection("participants")
                                .document(follower)        // mona
                                .collection("following")
                                .document(currentUsername) // lisa
                                .delete();

                        remove(follower);
                        notifyDataSetChanged();
                        Toast.makeText(context, follower + " removed from your followers", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to remove " + follower, Toast.LENGTH_SHORT).show();
                    });
        });

        return convertView;
    }
}
