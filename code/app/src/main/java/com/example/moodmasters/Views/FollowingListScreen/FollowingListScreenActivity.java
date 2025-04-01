package com.example.moodmasters.Views.FollowingListScreen;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.FollowingListScreen.FollowingListScreenBackEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.FollowingList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
/**
 * Activity that is responsible for giving a UI for viewing the participants the user is following
 * */
public class FollowingListScreenActivity extends AppCompatActivity implements MVCView {
    private ListView listView;
    private FollowingListScreenAdapter adapter;
    private ArrayList<String> following_list;
    @Override
    public void initialize(MVCModel model) {
        following_list = ((FollowingList) model.getBackendObject(BackendObject.State.FOLLOWINGLIST)).getFollowingListUsernames();
    }

    @Override
    public void update(MVCModel model) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.following_list_screen);  // Make sure this layout exists
        controller.addBackendView(this, BackendObject.State.FOLLOWINGLIST);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            controller.execute(new FollowingListScreenBackEvent(), this);
        });

        listView = findViewById(R.id.follow_list_view);
        adapter = new FollowingListScreenAdapter(this, following_list);
        listView.setAdapter(adapter);
    }

}