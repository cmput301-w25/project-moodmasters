package com.example.moodmasters.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewManager;
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
import com.example.moodmasters.Events.MoodFollowingListClickMoodEvent;
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
    private String mood_event_list;
    public MoodEventViewingActivity(){
        super();
        edit_clicked = false;
    }
    public void update(MVCModel model){
        if (edit_clicked && mood_event_list.equals("MoodHistoryList")){
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
        TextView reason_view = findViewById(R.id.view_mood_reason_text);
        TextView public_view = findViewById(R.id.view_mood_publicity_label);
        Mood displayed_mood = displayed_mood_event.getMood();

        emoji_view.setText(displayed_mood.getEmoticon());
        mood_view.setText(displayed_mood.getEmotionString());
        date_view.setText(displayed_mood_event.getDatetime());
        int background_color = ContextCompat.getColor(this, displayed_mood.getColor());
        layout_view.setBackgroundColor(background_color);
        social_situation_view.setText(SocialSituation.getString(displayed_mood_event.getSituation()));
        reason_view.setText(displayed_mood_event.getReason());
        if (displayed_mood_event.isIs_public()) {
            public_view.setText("☑ Public");
        } else {
            public_view.setText("☒ Private");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_mood_screen);

        Intent i = getIntent();
        mood_event_list = i.getStringExtra("MoodEventList");
        if (mood_event_list.equals("MoodHistoryList")){
            displayed_mood_event = MoodHistoryListClickMoodEvent.getMoodEvent();            /* while this is not ideal this is fine for now */
            position = MoodHistoryListClickMoodEvent.getPosition();
            controller.addBackendView(this, BackendObject.State.MOODHISTORYLIST);
        }
        else if (mood_event_list.equals("MoodFollowingList")){
            displayed_mood_event = MoodFollowingListClickMoodEvent.getMoodEvent();            /* while this is not ideal this is fine for now */
            position = MoodFollowingListClickMoodEvent.getPosition();
            controller.addBackendView(this, BackendObject.State.MOODFOLLOWINGLIST);
        }
        else{
            throw new IllegalArgumentException("Error: invalid string extra given to MoodEventViewingActivity");
        }

        setScreen();

        ImageButton exit_button = findViewById(R.id.view_mood_x_button);
        Button edit_button = findViewById(R.id.view_mood_edit_button);
        Button delete_button = findViewById(R.id.view_mood_delete_button);

        exit_button.setOnClickListener(v -> {
            controller.execute(new ExitMoodEventViewingEvent(), this);
        });
        if (mood_event_list.equals("MoodHistoryList")){
            edit_button.setOnClickListener(v -> {
                edit_clicked = true;
                controller.execute(new MoodEventViewingEditEvent(displayed_mood_event, position), this);
            });
            delete_button.setOnClickListener(v -> {
                edit_clicked = false;
                controller.execute(new DeleteMoodEventConfirmEvent(displayed_mood_event, position), this);
            });
        }
        else if (mood_event_list.equals("MoodFollowingList")){
            ((ViewManager) edit_button.getParent()).removeView(edit_button);
            ((ViewManager) delete_button.getParent()).removeView(delete_button);
        }
    }
}
