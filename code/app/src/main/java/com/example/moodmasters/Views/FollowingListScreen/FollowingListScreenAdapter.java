package com.example.moodmasters.Views.FollowingListScreen;

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

import com.example.moodmasters.Events.FollowingListScreen.FollowingListScreenRemoveEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
/**
 * Adapter class that will control what is displayed on the FollowingListScreen listview
 * */
public class FollowingListScreenAdapter extends ArrayAdapter<String> implements MVCView {
    private final Context context;
    public FollowingListScreenAdapter(Context context, List<String> users) {
        super(context, 0, users);
        this.context = context;
    }

    @Override
    public void update(MVCModel model) {
        notifyDataSetChanged();
    }

    @Override
    public void initialize(MVCModel model) {
        // Not used
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_list_item, parent, false);
        }
        controller.addBackendView(this, BackendObject.State.FOLLOWINGLIST);

        TextView usernameText = convertView.findViewById(R.id.username_text);
        Button actionButton = convertView.findViewById(R.id.action_button);

        usernameText.setText(user);
        actionButton.setText("Unfollow");

        actionButton.setOnClickListener(v -> {
            controller.execute(new FollowingListScreenRemoveEvent(user), context);
        });

        return convertView;
    }
}