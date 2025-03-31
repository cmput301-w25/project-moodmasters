package com.example.moodmasters.Views.FollowersListScreen;

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

import com.example.moodmasters.Events.FollowersListScreen.FollowerListScreenRemoveEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FollowersListScreenAdapter extends ArrayAdapter<String> implements MVCView {
    private final Context context;

    public FollowersListScreenAdapter(Context context, List<String> followers) {
        super(context, 0, followers);
        this.context = context;
    }

    @Override
    public void update(MVCModel model) {
        notifyDataSetChanged();
    }

    @Override
    public void initialize(MVCModel model) {
        // not needed
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convert_view, @NonNull ViewGroup parent) {
        String follower = getItem(position);

        if (convert_view == null) {
            convert_view = LayoutInflater.from(context).inflate(R.layout.followers_list_item, parent, false);
        }
        controller.addBackendView(this, BackendObject.State.FOLLOWERLIST);
        TextView username_text = convert_view.findViewById(R.id.follower_username);
        Button remove_button = convert_view.findViewById(R.id.remove_button);

        username_text.setText(follower);

        remove_button.setOnClickListener(v -> {
            controller.execute(new FollowerListScreenRemoveEvent(follower), context);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
        });

        return convert_view;
    }
}
