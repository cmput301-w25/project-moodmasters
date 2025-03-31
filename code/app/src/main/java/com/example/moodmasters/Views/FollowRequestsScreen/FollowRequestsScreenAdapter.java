package com.example.moodmasters.Views.FollowRequestsScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.moodmasters.Events.FollowRequestsScreen.FollowRequestsScreenAcceptEvent;
import com.example.moodmasters.Events.FollowRequestsScreen.FollowRequestsScreenIgnoreEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.example.moodmasters.Events.LoginSignupScreen.LoginSignupScreenOkEvent;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class FollowRequestsScreenAdapter extends ArrayAdapter<String> implements MVCView {
    private Context context;
    private List<String> requests;

    public FollowRequestsScreenAdapter(Context context, List<String> requests) {
        super(context, 0, requests);
        this.context = context;
        this.requests = requests;
        controller.addBackendView(this, BackendObject.State.FOLLOWREQUESTLIST);
    }

    @Override
    public void update(MVCModel model) {
        notifyDataSetChanged();
    }

    @Override
    public void initialize(MVCModel model) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_request_item, parent, false);
        }

        TextView username_text = convertView.findViewById(R.id.request_username);
        Button accept_button = convertView.findViewById(R.id.accept_button);
        Button ignore_button = convertView.findViewById(R.id.ignore_button);

        String requester_username = requests.get(position);

        username_text.setText(requester_username);

        accept_button.setOnClickListener(v -> {
            controller.execute(new FollowRequestsScreenAcceptEvent(position), context);
            //acceptRequest(requester_username, position);
        });
        ignore_button.setOnClickListener(v -> {
            controller.execute(new FollowRequestsScreenIgnoreEvent(position), context);
            //ignoreRequest(requester_username, position);
        });

        return convertView;
    }

}