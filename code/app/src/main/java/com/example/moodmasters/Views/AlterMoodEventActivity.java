package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.AddMoodEventConfirmEvent;
import com.example.moodmasters.Events.AlterMoodEventCancelEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.R;

import java.util.List;

public class AlterMoodEventActivity extends AppCompatActivity implements MVCView {
    private final List<String> emotions_list;
    private final List<String> social_situations_list;

    public AlterMoodEventActivity(){
        super();
        emotions_list = Emotion.getStringList();
        social_situations_list = SocialSituation.getStringList();
    }
    public void update(MVCModel model){
        // skip for now
    }
    public void initialize(MVCModel model){
        // skip for now
    }

    public void addMoodEventCode() {
        Spinner emotions_spinner = findViewById(R.id.alter_mood_emotion_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emotions_list);
        emotions_spinner.setAdapter(emotions_adapter);

        Spinner social_situations_spinner = findViewById(R.id.alter_social_situation_spinner);
        ArrayAdapter<String> social_situations_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, social_situations_list);
        emotions_spinner.setAdapter(social_situations_adapter);

        EditText trigger_text = findViewById(R.id.alter_mood_enter_trigger);

        EditText reason_text = findViewById(R.id.alter_mood_enter_reason);

        Button confirm_button = findViewById(R.id.confirm_button);

        confirm_button.setOnClickListener(v -> {
            controller.execute(new AddMoodEventConfirmEvent(), this);
        });
    }

    public void editMoodEventCode(){
        // TODO: Add code for editing mood event
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.alter_mood_screen);
        // TODO: Add view to model via controller if it is found necessary
        Intent i = getIntent();
        String event = i.getStringExtra("Event");           /* Will return type of event (add or edit) */
        if (event.equals("AddMoodEvent")) {
            addMoodEventCode();
        }
        else if (event.equals("EditMoodEvent")){
            editMoodEventCode();
        }
        else{
            throw new IllegalArgumentException("Error: Didn't provide the proper intent extra on activity creation");
        }

        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventCancelEvent(), this);
        });
    }
}
