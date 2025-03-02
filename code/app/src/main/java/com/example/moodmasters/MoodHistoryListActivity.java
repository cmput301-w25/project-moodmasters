package com.example.moodmasters;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.LoginScreenOkEvent;
import com.example.moodmasters.Events.MoodHistoryListMenuEvent;
import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;

public class MoodHistoryListActivity extends AppCompatActivity implements MVCView {
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
        ImageButton menu_button = findViewById(R.id.user_mood_history_menu_button);
        menu_button.setOnClickListener(v ->{
            controller.execute(new MoodHistoryListMenuEvent(), this);
        });
    }
}
