package com.example.moodmasters.Events;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.moodmasters.MVC.MVCController;
import com.example.moodmasters.MVC.MVCModel;
import com.example.moodmasters.R;
import com.example.moodmasters.Views.AlterMoodEventActivity;

public class AlterMoodEventScreenUnsetLocationEvent implements MVCController.MVCEvent {
    @Override
    public void executeEvent(Context context, MVCModel model, MVCController controller) {
        AlterMoodEventActivity alter_mood_event = (AlterMoodEventActivity) context;
        TextView location_text = alter_mood_event.findViewById(R.id.alter_mood_location_text);
        Button location_button = alter_mood_event.findViewById(R.id.alter_mood_location_button);
        location_text.setText("Not Specified");
        location_button.setText("Get Location");
    }
}
