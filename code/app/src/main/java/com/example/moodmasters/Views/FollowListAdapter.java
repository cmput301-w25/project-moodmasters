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

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FollowListAdapter extends ArrayAdapter<String> implements MVCView {
    private final Context context;
    private final String currentUsername;

    public FollowListAdapter(Context context, List<String> users, String currentUsername) {
        super(context, 0, users);
        this.context = context;
        this.currentUsername = currentUsername;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_list_item, parent, false);
        }

        TextView usernameText = convertView.findViewById(R.id.username_text);
        Button actionButton = convertView.findViewById(R.id.action_button);

        usernameText.setText(user);
        actionButton.setText("Unfollow");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        actionButton.setOnClickListener(v -> {
            db.collection("participants").document(currentUsername).collection("following").document(user)
                    .delete().addOnSuccessListener(aVoid -> {
                        db.collection("participants").document(user).collection("followers").document(currentUsername).delete();

                        remove(user);
                        notifyDataSetChanged();

                        // Refresh backend objects
                        controller.addBackendView(this, BackendObject.State.FOLLOWINGLIST);

                        controller.createBackendObject(BackendObject.State.FOLLOWINGLIST);
                        controller.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);

                        Toast.makeText(context, "Unfollowed " + user, Toast.LENGTH_SHORT).show();
                    });
        });

        return convertView;
    }

    @Override
    public void update(MVCModel model) {
        // Not used
    }

    @Override
    public void initialize(MVCModel model) {
        // Not used
    }
}