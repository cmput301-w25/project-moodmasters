package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.MoodHistoryListShowFilterEvent;
import com.example.moodmasters.Events.MoodHistoryListAddEvent;
import com.example.moodmasters.Events.MoodHistoryListMenuEvent;
import com.example.moodmasters.Events.MoodHistoryScreenShowMapEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsBackend.MoodList;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MoodHistoryListActivity extends AppCompatActivity implements MVCView {
    private MoodHistoryListView mood_history_view;
    private String username;

    // mock MoodEvent ArrayList for testing
    private ArrayList<MoodEvent> mock_mood_events;

    public MoodHistoryListActivity(){
        super();
        controller.addBackendView(this, BackendObject.State.USER);
    }
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        Participant user = ((Participant) model.getBackendObject(BackendObject.State.USER));
        username = user.getUsername();

        // mock MoodEvent ArrayList for testing
        mock_mood_events = new ArrayList<>();
        mock_mood_events.add(new MoodEvent("Mar 15 2025 | 21:30",
                1742099444745L,
                ((MoodList) model.getBackendObject(BackendObject.State.MOODLIST)).getMood(Emotion.State.ANGRY),
                true,
                "",
                SocialSituation.State.NONE,
                new LatLng(20, 20),
                "user_1"));
        mock_mood_events.add(new MoodEvent("Mar 15 2025 | 21:30",
                1742099444745L,
                ((MoodList) model.getBackendObject(BackendObject.State.MOODLIST)).getMood(Emotion.State.ANGRY),
                true,
                "",
                SocialSituation.State.NONE,
                new LatLng(25, 25),
                "user_2"));
        mock_mood_events.add(new MoodEvent("Mar 15 2025 | 21:30",
                1742099444745L,
                ((MoodList) model.getBackendObject(BackendObject.State.MOODLIST)).getMood(Emotion.State.ANGRY),
                true,
                "",
                SocialSituation.State.NONE,
                new LatLng(30, 30),
                "user_3"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_mood_history_screen);
        TextView username_view = findViewById(R.id.user_mood_history_label);
        username_view.setText(username);
        controller.createBackendObject(BackendObject.State.MOODHISTORYLIST);

        mood_history_view = new MoodHistoryListView(this);

        ImageButton menu_button = findViewById(R.id.user_mood_history_menu_button);
        menu_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListMenuEvent(), this);
        });

        Button add_button = findViewById(R.id.user_mood_history_add_button);
        add_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListAddEvent(), this);
        });

        Button sort_button = findViewById(R.id.user_mood_history_sort_button);
        sort_button.setOnClickListener(v -> {
            mood_history_view.toggleSort();
        });

        Button filter_button = findViewById(R.id.user_mood_history_filter_button);
        filter_button.setOnClickListener(v -> {
            controller.execute(new MoodHistoryListShowFilterEvent(), this);
        });

        Button map_button = findViewById(R.id.user_mood_history_show_map_button);
        map_button.setOnClickListener(v -> {
            // TODO: Replace mock_mood_events with appropriate MoodEvent ArrayList
            controller.execute(new MoodHistoryScreenShowMapEvent(mock_mood_events), this);
        });

        mood_history_view.setListElementClicker();
    }

    public MoodHistoryListView getMoodHistoryListView(){
        return mood_history_view;
    }
}
