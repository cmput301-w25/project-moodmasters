package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.MoodHistoryListAddEvent;
import com.example.moodmasters.Events.MoodHistoryListMenuEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsBackend.Participant;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

public class MoodHistoryListActivity extends AppCompatActivity implements MVCView {
    private MoodHistoryListView mood_history_view;
    private String username;

    // Removed the controller call from the constructor
    public MoodHistoryListActivity(){
        super();
        // Avoid calling controller.addBackendView here; do it in onCreate instead.
    }

    @Override
    public void update(MVCModel model){
        // Skip for now.
    }

    @Override
    public void initialize(MVCModel model){
        Participant user = (Participant) model.getBackendObject(BackendObject.State.USER);
        if(user != null){
            username = user.getUsername();
        } else {
            username = "Unknown"; // Fallback if user is null.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_mood_history_screen);

        // Add backend view for USER if the controller is available.
        if(controller != null) {
            controller.addBackendView(this, BackendObject.State.USER);
        }

        // Set the username text; if username is still null, use a fallback message.
        TextView username_view = findViewById(R.id.user_mood_history_label);
        if(username != null) {
            username_view.setText(username);
        } else {
            username_view.setText("No username");
        }

        // Create the backend object for the mood history list.
        if(controller != null) {
            controller.createBackendObject(BackendObject.State.MOODHISTORYLIST);
        }

        // Initialize the mood history view.
        mood_history_view = new MoodHistoryListView(this);

        // Set listener for the menu button.
        ImageButton menu_button = findViewById(R.id.user_mood_history_menu_button);
        if(menu_button != null && controller != null) {
            menu_button.setOnClickListener(v -> {
                controller.execute(new MoodHistoryListMenuEvent(), this);
            });
        }

        // Set listener for the add button.
        Button add_button = findViewById(R.id.user_mood_history_add_button);
        if(add_button != null && controller != null) {
            add_button.setOnClickListener(v -> {
                controller.execute(new MoodHistoryListAddEvent(), this);
            });
        }

        // Uncomment when you want to implement mood event viewing:
        // mood_history_view.setListElementClicker();
    }
}