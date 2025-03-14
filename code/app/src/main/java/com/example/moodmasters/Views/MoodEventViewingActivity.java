package com.example.moodmasters.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.moodmasters.Events.DeleteMoodEventConfirmEvent;
import com.example.moodmasters.Events.ExitMoodEventViewingEvent;
import com.example.moodmasters.Events.MoodEventViewingEditEvent;
import com.example.moodmasters.Events.MoodHistoryListClickMoodEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.Mood;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

public class MoodEventViewingActivity extends AppCompatActivity implements MVCView {
    private MoodEvent displayed_mood_event;
    private int position;
    private boolean edit_clicked;
    public MoodEventViewingActivity(){
        super();
        edit_clicked = false;
        displayed_mood_event = MoodHistoryListClickMoodEvent.getMoodEvent();            /* while this is not ideal this is fine for now */
        position = MoodHistoryListClickMoodEvent.getPosition();
        controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
    }
    public void update(MVCModel model){
        if (edit_clicked){
            displayed_mood_event = model.getFromBackendList(BackendObject.State.MOODHISTORYLIST, position);
            setScreen();
        }
    }
    public void initialize(MVCModel model){
        // skip for now
    }
    public void setScreen(){
        TextView emoji_view = findViewById(R.id.view_mood_emoji_label);
        TextView mood_view = findViewById(R.id.view_mood_emotion_label);
        TextView date_view = findViewById(R.id.view_mood_date_time_label);
        Toolbar layout_view = findViewById(R.id.view_mood_toolbar);
        TextView social_situation_view = findViewById(R.id.view_mood_situation_text);
        TextView trigger_view = findViewById(R.id.view_mood_trigger_text);          /* get rid of this after demo since not needed for final checkpoint */
        TextView reason_view = findViewById(R.id.view_mood_reason_text);
        Mood displayed_mood = displayed_mood_event.getMood();

        emoji_view.setText(displayed_mood.getEmoticon());
        mood_view.setText(displayed_mood.getEmotionString());
        date_view.setText(displayed_mood_event.getDatetime());
        int background_color = ContextCompat.getColor(this, displayed_mood.getColor());
        layout_view.setBackgroundColor(background_color);
        social_situation_view.setText(SocialSituation.getString(displayed_mood_event.getSituation()));
        trigger_view.setText(displayed_mood_event.getTrigger());
        reason_view.setText(displayed_mood_event.getReason());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_mood_screen);
        setScreen();

        ImageButton exit_button = findViewById(R.id.view_mood_x_button);
        Button edit_button = findViewById(R.id.view_mood_edit_button);
        Button delete_button = findViewById(R.id.view_mood_ok_button);

        exit_button.setOnClickListener(v -> {
            controller.execute(new ExitMoodEventViewingEvent(), this);
        });

        edit_button.setOnClickListener(v -> {
            edit_clicked = true;
            controller.execute(new MoodEventViewingEditEvent(displayed_mood_event, position), this);
        });

        delete_button.setOnClickListener(v -> {
            edit_clicked = false;
            controller.execute(new DeleteMoodEventConfirmEvent(displayed_mood_event, position), this);
        });
    }
}
