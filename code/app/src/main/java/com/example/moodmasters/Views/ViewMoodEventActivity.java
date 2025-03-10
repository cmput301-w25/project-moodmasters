package com.example.moodmasters.Views;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.moodmasters.Events.ViewMoodEventEditEvent;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.MVC.MVCView;
import com.example.moodmasters.Objects.ObjectsApp.MoodEvent;
import com.example.moodmasters.Objects.ObjectsApp.SocialSituation;
import com.example.moodmasters.Objects.ObjectsMisc.BackendObject;
import com.example.moodmasters.R;

import java.util.List;

public class ViewMoodEventActivity extends AppCompatActivity implements MVCView {
    private Context context;
    private List<MoodEvent> mood_list;
    private MoodEvent mood_event;
    private ViewMoodEventActivity viewMoodEventActivity;

    public ViewMoodEventActivity(){
        super();
    }

    @Override
    public void update(MVCModel model) {

    }

    @Override
    public void initialize(MVCModel model) {
        mood_list = model.getBackendList(BackendObject.State.MOODHISTORYLIST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_mood_screen);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int index = bundle.getInt("index");
        mood_event = mood_list.get(index);

        Toolbar toolbar = findViewById(R.id.view_mood_toolbar);
        TextView emotion = findViewById(R.id.view_mood_emotion_label);
        TextView emoji = findViewById(R.id.view_mood_emoji_label);
        TextView date_time = findViewById(R.id.view_mood_date_time_label);
        TextView trigger = findViewById(R.id.view_mood_trigger_text);
        TextView situation = findViewById(R.id.view_mood_situation_text);
        TextView reason = findViewById(R.id.view_mood_reason_text);
        Button edit = findViewById(R.id.view_mood_edit_button);
        Button delete = findViewById(R.id.view_mood_ok_button);
        ImageButton x = findViewById(R.id.view_mood_x_button);

        toolbar.setBackgroundColor(mood_event.getMood().getColor());
        emotion.setText(mood_event.getMood().getEmotionString());
        emoji.setText(mood_event.getMood().getEmoticon());
        date_time.setText(mood_event.getDatetime());
        trigger.setText(mood_event.getTrigger());
        situation.setText(SocialSituation.getString(mood_event.getSituation()));
        reason.setText(mood_event.getReason());

        edit.setOnClickListener(v -> {
            controller.execute(new ViewMoodEventEditEvent(), this, null);
        });
    }


}
