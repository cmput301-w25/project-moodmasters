package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.MoodEventsMapBackEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsMisc.MoodEventList;
import com.example.moodmasters.Objects.ObjectsMisc.MoodMap;
import com.example.moodmasters.R;

public class MoodEventsMapActivity extends AppCompatActivity implements MVCView {
    private MoodEventList mood_events;
    private MoodMap mood_map;

    @Override
    public void update(MVCModel model) {

    }

    @Override
    public void initialize(MVCModel model) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mood_events_map_screen);

        Button back_button = findViewById(R.id.map_screen_back_button);
        back_button.setOnClickListener(v -> {
            controller.execute(new MoodEventsMapBackEvent(), this);
        });
    }
}
