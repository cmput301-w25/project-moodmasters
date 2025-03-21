package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodmasters.Events.AddMoodEventConfirmEvent;
import com.example.moodmasters.Events.AlterMoodEventCancelEvent;
import com.example.moodmasters.Events.EditMoodEventConfirmEvent;
import com.example.moodmasters.Events.MoodEventViewingEditEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Emotion;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
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
        TextView label_view = findViewById(R.id.alter_mood_main_label);
        label_view.setText("Add MoodEvent");

        Spinner emotions_spinner = findViewById(R.id.alter_mood_emotion_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emotions_list);
        emotions_spinner.setAdapter(emotions_adapter);

        Spinner social_situations_spinner = findViewById(R.id.alter_mood_situation_spinner);
        ArrayAdapter<String> social_situations_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, social_situations_list);
        social_situations_spinner.setAdapter(social_situations_adapter);

        EditText reason_text = findViewById(R.id.alter_mood_enter_reason);

        Button confirm_button = findViewById(R.id.alter_mood_ok_button);

        confirm_button.setOnClickListener(v -> {
            controller.execute(new AddMoodEventConfirmEvent(), this);
        });
    }

    public void editMoodEventCode(){
        TextView label_view = findViewById(R.id.alter_mood_main_label);
        label_view.setText("Edit MoodEvent");

        MoodEvent current_mood_event = MoodEventViewingEditEvent.getMoodEvent();
        int position = MoodEventViewingEditEvent.getPosition();

        Spinner emotions_spinner = findViewById(R.id.alter_mood_emotion_spinner);
        ArrayAdapter<String> emotions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emotions_list);
        emotions_spinner.setAdapter(emotions_adapter);
        int emotion_default_pos = emotions_adapter.getPosition(current_mood_event.getMood().getEmotionString());
        emotions_spinner.setSelection(emotion_default_pos);

        Spinner social_situations_spinner = findViewById(R.id.alter_mood_situation_spinner);
        ArrayAdapter<String> social_situations_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, social_situations_list);
        social_situations_spinner.setAdapter(social_situations_adapter);
        int social_default_pos = social_situations_adapter.getPosition(SocialSituation.getString(current_mood_event.getSituation()));
        social_situations_spinner.setSelection(social_default_pos);

        EditText reason_text = findViewById(R.id.alter_mood_enter_reason);
        reason_text.setText(current_mood_event.getReason());

        Button confirm_button = findViewById(R.id.alter_mood_ok_button);

        confirm_button.setOnClickListener(v -> {
            controller.execute(new EditMoodEventConfirmEvent(current_mood_event, position), this);
        });
    }

    public boolean addDataVerification(String reason_string){
        if (reason_string.length() > 20){
            return false;
        }
        String regex = "\\W+";
        String[] words = reason_string.split(regex);
        if (words.length > 3){
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.alter_mood_screen);
        // TODO: Add view to model via controller if it is found necessary
        /* Will return type of event (add or edit) */

        Intent i = getIntent();
        String event = i.getStringExtra("Event");
        if (event.equals("AddMoodEvent")) {
            addMoodEventCode();
        }
        else if (event.equals("EditMoodEvent")){
            editMoodEventCode();
        }
        else{
            throw new IllegalArgumentException("Error: Didn't provide the proper intent extra on activity creation");
        }

        Button cancel_button = findViewById(R.id.alter_mood_cancel_button);
        cancel_button.setOnClickListener(v -> {
            controller.execute(new AlterMoodEventCancelEvent(), this);
        });

    }
}
