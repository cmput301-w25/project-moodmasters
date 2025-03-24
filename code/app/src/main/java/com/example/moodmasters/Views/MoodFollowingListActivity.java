package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moodmasters.Events.MoodFollowingListRefreshEvent;
import com.example.moodmasters.Events.MoodFollowingListShowFilterEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Events.MoodFollowingListBackEvent;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.ArrayList;

public class MoodFollowingListActivity extends AppCompatActivity implements MVCView {
    private MoodFollowingListView mood_following_view;
    private String username;

    // mock MoodEvent ArrayList for testing
    private ArrayList<MoodEvent> mock_mood_events;

    public MoodFollowingListActivity(){
        super();
        controller.addBackendView(this, BackendObject.State.USER);
    }
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));
        username = user.getUsername();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_mood_following_screen);
        TextView username_view = findViewById(R.id.user_mood_following_label);
        username_view.setText(username);
        /*
        if (!controller.existsBackendObject(BackendObject.State.MOODFOLLOWINGLIST)){
            controller.createBackendObject(BackendObject.State.MOODFOLLOWINGLIST);
        }
         */
        mood_following_view = new MoodFollowingListView(this);

        Button sort_button = findViewById(R.id.user_mood_following_sort_button);
        sort_button.setOnClickListener(v -> {
            mood_following_view.toggleSort();
        });

        Button filter_button = findViewById(R.id.user_mood_following_filter_button);
        filter_button.setOnClickListener(v -> {
            controller.execute(new MoodFollowingListShowFilterEvent(), this);
        });

        Button back_button = findViewById(R.id.user_mood_following_back_button);
        back_button.setOnClickListener(v -> {
            controller.execute(new MoodFollowingListBackEvent(), this);
        });

        SwipeRefreshLayout swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(() -> {
            controller.execute(new MoodFollowingListRefreshEvent(), this);
        });
        mood_following_view.setListElementClicker();
    }

    public MoodFollowingListView getMoodFollowingListView(){
        return mood_following_view;
    }
}
