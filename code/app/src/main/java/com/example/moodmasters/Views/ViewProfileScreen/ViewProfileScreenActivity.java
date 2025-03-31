package com.example.moodmasters.Views.ViewProfileScreen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.Events.ViewProfileScreen.ViewProfileScreenBackEvent;
import com.example.moodmasters.Events.ViewProfileScreen.ViewProfileScreenFollowEvent;
import com.example.moodmasters.Events.ViewProfileScreen.ViewProfileScreenRefreshEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.FollowRequest;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsBackend.UserSearch;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Activity that is responsible for giving a UI for viewing another participants profile
 * */
public class ViewProfileScreenActivity extends AppCompatActivity implements MVCView {
    private String target_username;
    private String status;
    private ViewProfileScreenAdapter adapter;
    private ArrayList<MoodEvent> target_mood_events;
    private Participant selected_participant;

    @Override
    public void update(MVCModel model) {
        // Updates can implement changes based on model notifications
    }

    @Override
    public void initialize(MVCModel model) {
        selected_participant = ((UserSearch) model.getBackendObject(BackendObject.State.USERSEARCH)).getParticipant(target_username);
        target_mood_events = selected_participant.getMoodHistoryList().getList();
        target_mood_events.sort((a, b) -> Long.compare(a.getEpochTime(), b.getEpochTime()));
        ArrayList<MoodEvent> removables = new ArrayList<MoodEvent>();
        int amount = 0;
        for (MoodEvent mood_event: target_mood_events){
            if (mood_event.getIsPublic() && amount < 3){
                amount++;
                continue;
            }
            removables.add(mood_event);
        }
        target_mood_events.removeAll(removables);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        target_username = getIntent().getStringExtra("selected_user");
        status = getIntent().getStringExtra("follow_request_status");
        controller.addBackendView(this, BackendObject.State.USERSEARCH);

        TextView username_text_view = findViewById(R.id.usernameTextView);
        Button follow_button = findViewById(R.id.followButton);
        ListView mood_list_view = findViewById(R.id.mood_list_view);


        adapter = new ViewProfileScreenAdapter(this, target_mood_events);
        mood_list_view.setAdapter(adapter);

        username_text_view.setText(target_username);
        follow_button.setText(status);

        SwipeRefreshLayout swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(() -> {
            controller.execute(new ViewProfileScreenRefreshEvent(selected_participant), this);
        });

        follow_button.setOnClickListener(v -> {
            controller.execute(new ViewProfileScreenFollowEvent(status),this);
        });
        findViewById(R.id.backButton).setOnClickListener(v -> {
            controller.execute(new ViewProfileScreenBackEvent(),this);
        });
    }
    public ViewProfileScreenAdapter getAdapter(){
        return adapter;
    }
}