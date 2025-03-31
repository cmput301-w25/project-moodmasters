package com.example.moodmasters.Views.FollowersListScreen;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.FollowersListScreen.FollowersListScreenBackEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.FollowerList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FollowersListScreenActivity extends AppCompatActivity implements MVCView {
    private FirebaseFirestore db;
    private ListView list_view;
    private FollowersListScreenAdapter adapter;
    private ArrayList<String> follower_list;

    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        follower_list = ((FollowerList) model.getBackendObject(BackendObject.State.FOLLOWERLIST)).getFollowerList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_list_screen);
        controller.addBackendView(this, BackendObject.State.FOLLOWERLIST);
        list_view = findViewById(R.id.followers_list_view);
        adapter = new FollowersListScreenAdapter(this, follower_list);
        list_view.setAdapter(adapter);

        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> {
            controller.execute(new FollowersListScreenBackEvent(), this);
        });
    }
}