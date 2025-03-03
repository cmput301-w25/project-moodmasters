package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.MoodHistoryListAddEvent;
import com.example.moodmasters.Events.MoodHistoryListMenuEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.R;

public class MoodHistoryListActivity extends AppCompatActivity implements MVCView {
    private MoodHistoryListView mood_history_view;
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        // skip for now
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_mood_history_screen);
        // TODO: Add view to model via controller if it is found necessary
        controller.createBackendObject(MVCModel.BackendObject.MOODLIST);
        controller.createBackendObject(MVCModel.BackendObject.MOODHISTORYLIST);

        mood_history_view = new MoodHistoryListView(this);

        ImageButton menu_button = findViewById(R.id.user_mood_history_menu_button);
        menu_button.setOnClickListener(v ->{
            controller.execute(new MoodHistoryListMenuEvent(), this);
        });

        Button add_button = findViewById(R.id.user_mood_history_add_button);
        add_button.setOnClickListener(v ->{
            controller.execute(new MoodHistoryListAddEvent(), this);
        });

        // uncomment when you want to implement mood event viewing
        //mood_history_view.setListElementClicker();
    }
}
